package rs.raf.ast;

public class Argument extends Statement {
    private final Type type;
    private final String identifier;

    public Argument(Location loc, Type type, String identifier) {
        super(loc);
        this.type = type;
        this.identifier = identifier;

    }

    @Override
    public void prettyPrint(ASTPrettyPrinter pp) {

    }
}
