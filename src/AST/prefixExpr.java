package AST;

import Util.position;

public class prefixExpr extends ExprNode {
    public ExprNode rhs;
    public String opcode;

    public prefixExpr(ExprNode rhs, String opcode, position pos) {
        super(pos);
        this.rhs = rhs;
        this.opcode = opcode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
