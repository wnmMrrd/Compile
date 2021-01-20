package AST;

import Util.position;

import java.util.ArrayList;

public class classDef extends DefNode {
    public String classname;
    public ArrayList<varDefSubStmt> varList = new ArrayList<>();
    public ArrayList<funcDef> funcList = new ArrayList<>();
    public funcDef constructor = null;

    public classDef(String classname, position pos) {
        super(pos);
        this.classname = classname;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
