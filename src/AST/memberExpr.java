package AST;

import Util.position;

public class memberExpr extends ExprNode {
    public ExprNode firstExpr;
    public String id;
    public boolean isfunc = false;

    public memberExpr(ExprNode firstExpr, String id, position pos) {
        super(true, pos);
        this.firstExpr = firstExpr;
        this.id = id;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
