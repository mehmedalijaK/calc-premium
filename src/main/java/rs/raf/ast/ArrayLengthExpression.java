package rs.raf.ast;

public class ArrayLengthExpression extends Expression {

    private Expression base;

    public ArrayLengthExpression(Location location, Expression base) {
        super(location);
        this.base = base;
    }

    @Override
    public void prettyPrint(ASTPrettyPrinter pp) {
        pp.node("ArrayLength", () -> {
            base.prettyPrint(pp);
        });
    }
}
