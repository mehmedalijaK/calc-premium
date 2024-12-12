package rs.raf.ast;

public class ReturnStatement extends Tree{

    private Expression value;

    public ReturnStatement(Location location, Expression expression) {
        super(location);
    }

    @Override
    public void prettyPrint(ASTPrettyPrinter pp) {
        pp.node("ReturnStatement", () -> {
            if (value != null) {
                pp.node("value", () -> value.prettyPrint(pp));
            } else {
                pp.terminal("value: null");
            }
        });
    }
}
