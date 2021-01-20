package AST;

import Util.position;

public class subscriptExpr extends ExprNode {
    public ExprNode id, index;

    public subscriptExpr(ExprNode id, ExprNode index, position pos) {
        super(true, pos);
        this.id = id;
        this.index = index;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
