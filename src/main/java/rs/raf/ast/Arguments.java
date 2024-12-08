package rs.raf.ast;

import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.List;

public class Arguments extends Statement{
    private final List<Argument> arguments;

    public Arguments(Location location, List<Argument> arguments) {
        super(location);
        this.arguments = new ArrayList<>(arguments);
    }

    @Override
    public void prettyPrint(ASTPrettyPrinter pp) {
        for (Argument p : arguments) {
            System.out.println(p);
        }
    }
}
