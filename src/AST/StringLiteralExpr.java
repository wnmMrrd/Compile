package AST;

import Util.position;

public class StringLiteralExpr extends ExprNode {
    public String str;

    public StringLiteralExpr(String str, position pos) {
        super(pos);
        this.str = str;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
