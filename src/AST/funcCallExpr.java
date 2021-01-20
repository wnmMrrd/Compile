package AST;

import Util.position;

import java.util.ArrayList;

public class funcCallExpr extends ExprNode {
    public ExprNode firstExpr;
    public ArrayList<ExprNode> List;

    public funcCallExpr(ExprNode firstExpr, expressionListExpr exprssionList, position pos) {
        super(pos);
        this.firstExpr = firstExpr;
        this.List = exprssionList.expressionList;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
