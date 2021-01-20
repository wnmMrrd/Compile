package AST;

import Util.position;

public class varDefSubStmt extends StmtNode {
    public TypeNode type;
    public String id;
    public ExprNode expr;

    public varDefSubStmt(String id, ExprNode expr, position pos) {
        super(pos);
        this.id = id;
        this.expr = expr;
    }

    public varDefSubStmt(TypeNode type, String id, position pos) {
        super(pos);
        this.type = type;
        this.id = id;
        this.expr = null;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
