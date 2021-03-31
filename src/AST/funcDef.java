package AST;

import Util.position;

import java.util.ArrayList;

public class funcDef extends DefNode {
    public TypeNode returntype;
    public String id, id2;
    public blockStmt body;
    public ArrayList<varDefSubStmt> List;

    public funcDef(TypeNode returntype, String id, blockStmt body, ArrayList<varDefSubStmt> List, position pos) {
        super(pos);
        this.returntype = returntype;
        this.id = id;
        this.body = body;
        this.List = List;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
