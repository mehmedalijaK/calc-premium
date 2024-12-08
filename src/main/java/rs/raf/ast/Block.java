package rs.raf.ast;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/** A block of code with local variables.  */
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class Block extends Tree
{
    private Map<String, DeclarationStatement> environment = new HashMap<>();
    private List<Statement> statements = new ArrayList<>();

    public Block (Location location)
    {
        super (location);
    }

    @Override
    public void prettyPrint(ASTPrettyPrinter pp) {

    }
}
