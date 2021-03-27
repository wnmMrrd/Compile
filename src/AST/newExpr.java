package AST;

import Util.position;

import java.util.ArrayList;

public class newExpr extends ExprNode {
    public TypeNode subtype;
    public ArrayList<ExprNode> sizeList;

    public newExpr(TypeNode type, int dim, ArrayList<ExprNode> sizeList, position pos) {
        super(pos);
        this.subtype = type;
        this.subtype.dim = dim;
        this.sizeList = sizeList;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
