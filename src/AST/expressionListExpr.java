package AST;

import Util.position;

import java.util.ArrayList;

public class expressionListExpr extends ExprNode {
    public ArrayList<ExprNode> expressionList = new ArrayList<>();

    public expressionListExpr(position pos) {
        super(pos);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
