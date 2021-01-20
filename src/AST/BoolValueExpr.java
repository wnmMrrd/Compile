package AST;

import Util.position;

public class BoolValueExpr extends ExprNode {
    public boolean value;

    public BoolValueExpr(boolean value, position pos) {
        super(pos);
        this.value = value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
