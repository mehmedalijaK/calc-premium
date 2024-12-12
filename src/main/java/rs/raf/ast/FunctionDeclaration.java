package rs.raf.ast;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.Pair;

import java.util.List;

public class FunctionDeclaration extends Statement {

    @Getter
    @Setter
    private Arguments args;
    private Type returnType;
    private String name;
    private StatementList body;

    public FunctionDeclaration (Location location, Arguments args, String name, Type returnType, StatementList body) {
        super (location);
        this.args = args;
        this.returnType = returnType;
        this.name = name;
        this.body = body;
    }

    @Override
    public void prettyPrint(ASTPrettyPrinter pp) {

    }
}
