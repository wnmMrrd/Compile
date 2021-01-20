package AST;

import Util.position;

public class whileStmt extends StmtNode {
    public ExprNode cond;
    public StmtNode body;

    public whileStmt(ExprNode cond, StmtNode body, position pos) {
        super(pos);
        this.cond = cond;
        this.body = body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
