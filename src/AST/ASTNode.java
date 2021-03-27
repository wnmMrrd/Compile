package AST;

import IR.IRRegIdentifier;
import Util.position;
import Util.symbol.Scope;

abstract public class ASTNode {
    public position pos;

    public Scope scope;
    public IRRegIdentifier regId;

    public ASTNode(position pos) {
        this.pos = pos;
        this.scope = null;
    }

    abstract public void accept(ASTVisitor visitor);
}
