package rs.raf.ast;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class DeclarationStatement extends Statement {

    private String type;
    private String name;
    private Expression initializer;

    public DeclarationStatement (Location location, String type, String name, Expression initializer)
    {
        super (location);
        this.type = type;
        this.name = name;
        this.initializer = initializer;
    }

    @Override
    public void prettyPrint(ASTPrettyPrinter pp) {
        pp.node("declaration of %s".formatted(type),
                () -> {
                    initializer.prettyPrint(pp);
                });
    }
}
