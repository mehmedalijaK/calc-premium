package rs.raf.ast;

public class WhileStatement extends Statement {

    private Expression condition;
    private StatementList block;

    public WhileStatement(Location location, Expression condition, StatementList block) {
        super(location);
        this.condition = condition;
        this.block = block;
    }

    @Override
    public void prettyPrint(ASTPrettyPrinter pp) {
        pp.node("whileStatement",
                () -> {
                    condition.prettyPrint(pp);
                    block.prettyPrint(pp);
                });
    }
}
