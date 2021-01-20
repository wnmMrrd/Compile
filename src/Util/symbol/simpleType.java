package Util.symbol;

public class simpleType extends Type {
    public String simplename;

    public simpleType(String simplename) {
        this.simplename = simplename;
    }

    @Override
    public boolean equals(Type x) {
        return (this.isNull() && (x instanceof arrayType || x instanceof classType)) || ((x instanceof simpleType) && (this.simplename.equals(((simpleType) x).simplename)));
    }

    @Override
    public boolean isInt() {
        return simplename.equals("int");
    }

    @Override
    public boolean isBool() {
        return simplename.equals("bool");
    }

    @Override
    public boolean isString() {
        return simplename.equals("string");
    }

    @Override
    public boolean isVoid() {
        return simplename.equals("void");
    }

    @Override
    public boolean isNull() {
        return simplename.equals("null");
    }
}

