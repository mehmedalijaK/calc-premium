package rs.raf.ast;

public class ArrayPushExpression extends Expression {

    private Expression base;

    public ArrayPushExpression(Location location, Expression base) {
        super(location);
        this.base = base;
    }

    @Override
    public void prettyPrint(ASTPrettyPrinter pp) {
        pp.node("ArrayPush", () -> {
            base.prettyPrint(pp);
        });
    }
}
