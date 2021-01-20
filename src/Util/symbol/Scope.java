package Util.symbol;

import AST.TypeNode;
import Util.error.semanticError;
import Util.position;

import java.util.HashMap;

public class Scope {
    public HashMap<String, Type> typemap = new HashMap<>();
    public HashMap<String, varType> varmap = new HashMap<>();
    public HashMap<String, funcType> funcmap = new HashMap<>();
    public Scope parentScope;

    public Scope(Scope parentScope) {
        this.parentScope = parentScope;
    }

    public boolean containsType(String name, boolean lookUpon) {
        if (typemap.containsKey(name)) return true;
        else if (parentScope != null && lookUpon) return parentScope.containsType(name, true);
        else return false;
    }

    public Type getType(TypeNode type) {
        if (type.dim == 0) return typemap.get(type.origintype);
        else return new arrayType(typemap.get(type.origintype), type.dim);
    }

    public void defineType(String name, Type value, position pos) {
        if (typemap.containsKey(name)) throw new semanticError("class redefine", pos);
        typemap.put(name, value);
    }

    public void defineVariable(String name, varType value, position pos) {
        if (this.containsType(name, true)) throw new semanticError("duplicated with type name", pos);
        if (varmap.containsKey(name)) throw new semanticError("variable redefine", pos);
        varmap.put(name, value);
    }

    public varType getVariable(String name, boolean lookUpon, position pos) {
        if (varmap.containsKey(name)) return varmap.get(name);
        else if (parentScope != null && lookUpon) return parentScope.getVariable(name, true, pos);
        else throw new semanticError("variable not define", pos);
    }

    public void defineFunction(String name, funcType value, position pos) {
        if (this.containsType(name, true)) throw new semanticError("duplicated with type name", pos);
        if (funcmap.containsKey(name)) throw new semanticError("function redefine", pos);
        funcmap.put(name, value);
    }

    public funcType getFunction(String name, boolean lookUpon, position pos) {
        if (funcmap.containsKey(name)) return funcmap.get(name);
        else if (parentScope != null && lookUpon) return parentScope.getFunction(name, true, pos);
        else throw new semanticError("function not define", pos);
    }
}
