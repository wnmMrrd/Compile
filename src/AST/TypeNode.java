package AST;

import Util.position;

public class TypeNode extends ASTNode {
    public String origintype;
    public int dim;

    public TypeNode(String origintype, int dim, position pos) {
        super(pos);
        this.origintype = origintype;
        this.dim = dim;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
