package AST;

import Util.position;

public class pureExprStmt extends StmtNode {
    public ExprNode expr;

    public pureExprStmt(ExprNode expr, position pos) {
        super(pos);
        this.expr = expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
