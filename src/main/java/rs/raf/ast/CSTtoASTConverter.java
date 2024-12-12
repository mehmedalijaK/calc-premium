package rs.raf.ast;

import calcpremium.parser.CalcPremiumParser;
import calcpremium.parser.CalcPremiumVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class CSTtoASTConverter extends AbstractParseTreeVisitor<Tree> implements CalcPremiumVisitor<Tree> {


    @Override
    public Tree visitStart(CalcPremiumParser.StartContext ctx) {
        var funDeclarations = ctx.topLevel()
                .stream()
                .map(this::visit)
                .map(x -> (FunctionDeclaration) x)
                .collect(Collectors.toCollection(ArrayList::new));

        return new FunctionDeclarationList(getLocation(ctx), funDeclarations);
    }

    @Override
    public Tree visitTopLevel(CalcPremiumParser.TopLevelContext ctx) {
        if (ctx.defineFunction() != null) {
            return visit(ctx.defineFunction());
        } else {
            throw new AssertionError ("Define function expected");
        }
    }

    @Override
    public Tree visitDefineFunction(CalcPremiumParser.DefineFunctionContext ctx) {
        var name = ctx.IDENTIFIER().getText();
        var args = (Arguments) visitArgumentList(ctx.argumentList());
        var returnType = typeBuilder(visit(ctx.returnType).toString());
        var declLoc = getLocation(ctx.start).span(getLocation(ctx.returnType.start));

        StatementList body = null;
        if (ctx.body != null) {
            body = (StatementList) visit(ctx.body);
        }

        return new FunctionDeclaration(declLoc, args, name, returnType, body);
    }

    @Override
    public Tree visitArgumentList(CalcPremiumParser.ArgumentListContext ctx) {
        // Visit each argument in the list and collect the results into a list
        var arguments = ctx.argument()
                .stream()
                .map(this::visit)
                .map(x -> (Argument) x) // Cast each visited result to an Argument
                .collect(Collectors.toCollection(ArrayList::new));

        // Create an ArgumentList node from the collected arguments
        return new Arguments(getLocation(ctx), arguments);
    }

    @Override
    public Tree visitArgument(CalcPremiumParser.ArgumentContext ctx) {
        // Extract the type and identifier for the argument
        String type = ctx.typeId().getText();
        String identifier = ctx.IDENTIFIER().getText();

        // Create and return an Argument node
        return new Argument(getLocation(ctx), typeBuilder(type), identifier);
    }

    @Override
    public Tree visitStatementList(CalcPremiumParser.StatementListContext ctx) {
        // Collect all declaring statements into a list
        var stmts = ctx.statement()
                .stream()
                .map(this::visit)
                .map(x -> (Statement) x) // Assuming each `declaringStmt` maps to a `Statement`
                .toList();

        // Return a StatementList node
        return new StatementList(getLocation(ctx), stmts);
    }

    @Override
    public Tree visitStatement(CalcPremiumParser.StatementContext ctx) {
        return visit(ctx.getChild(0));
    }

    @Override
    public Tree visitDeclareStatement(CalcPremiumParser.DeclareStatementContext ctx) {
        var type =  ctx.typeId().getText();
        var name = ctx.IDENTIFIER().getText();
        var value = (Expression) visit(ctx.value);

        return new DeclarationStatement(getLocation(ctx), type, name, value);
    }

    @Override
    public Tree visitReturnStatement(CalcPremiumParser.ReturnStatementContext ctx) {
        // Collect return expression
        var exp = (Expression) visit(ctx.expression());
        return new ReturnStatement(getLocation(ctx.RETURN()), exp);
    }

    @Override
    public Tree visitLoopControlStatement(CalcPremiumParser.LoopControlStatementContext ctx) {
        return null;
    }

    @Override
    public Tree visitNullStatement(CalcPremiumParser.NullStatementContext ctx) {
        return null;
    }

    @Override
    public Tree visitIfStatement(CalcPremiumParser.IfStatementContext ctx) {
        Expression condition = (Expression) visit(ctx.expression());
        StatementList ifBlock = (StatementList) visit(ctx.then);
        StatementList elseBlock = (StatementList) visit(ctx.otherwise);
        return new IfStatement(getLocation(ctx), condition, ifBlock, elseBlock);
    }

    @Override
    public Tree visitWhileStatement(CalcPremiumParser.WhileStatementContext ctx) {
        Expression condition = (Expression) visit(ctx.expression());
        StatementList block = (StatementList) visit(ctx.body);
        return new WhileStatement(getLocation(ctx), condition, block);
    }

    @Override
    public Tree visitForStatement(CalcPremiumParser.ForStatementContext ctx) {
        return null;
    }

    @Override
    public Tree visitAssignment(CalcPremiumParser.AssignmentContext ctx) {
        return null;
    }

    @Override
    public Tree visitExpression(CalcPremiumParser.ExpressionContext ctx) {
        return null;
    }

    @Override
    public Tree visitOrExpression(CalcPremiumParser.OrExpressionContext ctx) {
        return null;
    }

    @Override
    public Tree visitAndExpression(CalcPremiumParser.AndExpressionContext ctx) {
        return null;
    }

    @Override
    public Tree visitCompareExpression(CalcPremiumParser.CompareExpressionContext ctx) {
        return null;
    }

    @Override
    public Tree visitRelationalExpression(CalcPremiumParser.RelationalExpressionContext ctx) {
        return null;
    }

    @Override
    public Tree visitAdditionExpression(CalcPremiumParser.AdditionExpressionContext ctx) {
        return null;
    }

    @Override
    public Tree visitMultiplicationExpression(CalcPremiumParser.MultiplicationExpressionContext ctx) {
        return null;
    }

    @Override
    public Tree visitUnaryExpression(CalcPremiumParser.UnaryExpressionContext ctx) {
        return null;
    }

    @Override
    public Tree visitUnarySuffix(CalcPremiumParser.UnarySuffixContext ctx) {
        return null;
    }

    @Override
    public Tree visitFuncall(CalcPremiumParser.FuncallContext ctx) {
        return null;
    }

    @Override
    public Tree visitArrIdx(CalcPremiumParser.ArrIdxContext ctx) {
        return null;
    }

    @Override
    public Tree visitArrayLen(CalcPremiumParser.ArrayLenContext ctx) {
        return null;
    }

    @Override
    public Tree visitArrayPush(CalcPremiumParser.ArrayPushContext ctx) {
        return null;
    }

    @Override
    public Tree visitTerm(CalcPremiumParser.TermContext ctx) {
        return null;
    }

    @Override
    public Tree visitVarRef(CalcPremiumParser.VarRefContext ctx) {
        return null;
    }

    @Override
    public Tree visitExpressionList(CalcPremiumParser.ExpressionListContext ctx) {
        return null;
    }

    @Override
    public Tree visitArrayType(CalcPremiumParser.ArrayTypeContext ctx) {
        return null;
    }

    @Override
    public Tree visitArrayCollect(CalcPremiumParser.ArrayCollectContext ctx) {
        return null;
    }

    @Override
    public Tree visitTypeId(CalcPremiumParser.TypeIdContext ctx) {
        return null;
    }

    @Override
    public Tree visitLiteral(CalcPremiumParser.LiteralContext ctx) {
        return null;
    }

    /* Helpers.  */
    /** Returns the range that this subtree is in.  */
    private static Location getLocation(ParserRuleContext context) {
        return getLocation(context.getStart())
                .span(getLocation(context.getStop ()));
    }

    /** Returns the location this terminal is in.  */
    private static Location getLocation(TerminalNode term) {
        return getLocation(term.getSymbol());
    }

    /** Returns the location this token is in.  */
    private static Location getLocation(Token token) {
        /* The token starts at the position ANTLR provides us.  */
        var start = new Position(token.getLine(), token.getCharPositionInLine());

        /* But it does not provide a convenient way to get where it ends, so we
           have to calculate it based on length.  */
        assert !token.getText ().contains ("\n")
                : "CSTtoASTConverter assumes single-line tokens";
        var length = token.getText ().length ();
        assert length > 0;

        /* And then put it together.  */
        var end = new Position (start.line (), start.column () + length - 1);
        return new Location (start, end);
    }

    private Type typeBuilder(String type) {
        return switch (type.toLowerCase()) {
            case "int" -> Type.INT;
            case "bool" -> Type.BOOL;
            case "void" -> Type.VOID;
            case "char" -> Type.CHAR;
            case "string" -> Type.STRING;
            case "arr" -> Type.ARR;
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };
    }
}