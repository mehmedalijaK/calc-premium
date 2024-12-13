package rs.raf.ast;

public class PrimitiveType extends Expression {

    private Type type;

    public PrimitiveType(Location location, Type type) {
        super(location);
        this.type = type;
    }

    @Override
    public void prettyPrint(ASTPrettyPrinter pp) {
        pp.node("PrimitiveType", () -> {
            pp.terminal("Type: " + type.toString());
        });
    }
}
