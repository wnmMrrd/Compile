package AST;

import Util.position;

public class varExpr extends ExprNode {
    public String id;

    public varExpr(String id, position pos) {
        super(true, pos);
        this.id = id;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
