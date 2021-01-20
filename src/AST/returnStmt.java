package AST;

import Util.position;

public class returnStmt extends StmtNode {
    public ExprNode value;

    public returnStmt(ExprNode value, position pos) {
        super(pos);
        this.value = value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
