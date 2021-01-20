package AST;

import Util.position;

public class forStmt extends StmtNode {
    public ExprNode init, cond, incr;
    public StmtNode body;

    public forStmt(ExprNode init, ExprNode cond, ExprNode incr, StmtNode body, position pos) {
        super(pos);
        this.init = init;
        this.cond = cond;
        this.incr = incr;
        this.body = body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
