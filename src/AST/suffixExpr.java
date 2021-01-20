package AST;

import Util.position;

public class suffixExpr extends ExprNode {
    public ExprNode lhs;
    public String opcode;

    public suffixExpr(ExprNode lhs, String opcode, position pos) {
        super(pos);
        this.lhs = lhs;
        this.opcode = opcode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
