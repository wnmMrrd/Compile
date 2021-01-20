package AST;

import Util.position;

public class DecimalIntegerExpr extends ExprNode {
    public int value;

    public DecimalIntegerExpr(int value, position pos) {
        super(pos);
        this.value = value;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
