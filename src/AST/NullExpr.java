package AST;

import Util.position;

public class NullExpr extends ExprNode {
    public NullExpr(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
