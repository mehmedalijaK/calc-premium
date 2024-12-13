package rs.raf.ast;

import java.util.List;

public class FunctionCallExpression extends Expression {

    private Expression base;
    private List<Expression> arguments;

    public FunctionCallExpression(Location location, Expression base, List<Expression> args) {
        super(location);
        this.base = base;
        this.arguments = args;
    }

    @Override
    public void prettyPrint(ASTPrettyPrinter pp) {
        pp.node("FunctionCall", () -> {
            pp.node("Base", () -> base.prettyPrint(pp));

            pp.node("Arguments", () -> {
                for (Expression arg : arguments) {
                    arg.prettyPrint(pp);
                }
            });
        });
    }
}
