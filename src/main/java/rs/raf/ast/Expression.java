package rs.raf.ast;

import java.util.Objects;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper=true)
public class Expression extends Tree {
    public enum Operation {
        ADD("+"),
        SUB("-"),

        MUL("*"),
        DIV("/"),

        POW("^"),

        /** A vector or a number or a variable.  */
        VALUE(null),
        LOGICAL_OR("||"), LOGICAL_AND("&&"), EQUALS("=="), NOT_EQUALS("!="), LESS_THAN("<"), LESS_THAN_EQUAL("<="), GREATER_THAN(">"), GREATER_THAN_EQUAL(">="), MOD("%"), NEGATE("-"), LOGICAL_NOT("!"), ASSIGN("=");

        public final String label;

        Operation(String label) {
            this.label = label;
        }
    }

    private Operation operation;
    private Expression lhs;
    private Expression rhs;

    public Expression(Location location, Operation operation, Expression lhs, Expression rhs) {
        super(location);
//        if (operation == Operation.VALUE)
//            throw new IllegalArgumentException("cannot construct a value like that");
        this.operation = operation;
        this.lhs = Objects.requireNonNull(lhs);
        this.rhs = Objects.requireNonNull(rhs);
    }

    protected Expression(Location location)
    {
        super(location);
        this.operation = Operation.VALUE;
    }

    @Override
    public void prettyPrint(ASTPrettyPrinter pp) {
        pp.node(operation.label,
                () -> {
                    lhs.prettyPrint(pp);
                    rhs.prettyPrint(pp);
                });
    }
}