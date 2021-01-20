package AST;

import Util.position;

public class binaryExpr extends ExprNode {
    public ExprNode lhs, rhs;
    public String opcode;

    public binaryExpr(ExprNode lhs, ExprNode rhs, String opcode, position pos) {
        super(pos);
        this.lhs = lhs;
        this.rhs = rhs;
        this.opcode = opcode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
