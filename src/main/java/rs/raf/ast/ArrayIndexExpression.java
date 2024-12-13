package rs.raf.ast;

public class ArrayIndexExpression extends Expression {

    private Expression base;
    private Expression index;

    public ArrayIndexExpression(Location location, Expression base, Expression index) {
        super(location);
        this.base = base;
        this.index = index;
    }

    @Override
    public void prettyPrint(ASTPrettyPrinter pp) {
        pp.node("ArrayIndex", () -> {
            base.prettyPrint(pp);
            index.prettyPrint(pp);
        });
    }
}
