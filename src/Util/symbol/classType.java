package Util.symbol;

import java.util.HashMap;

public class classType extends Type {
    public String classname;
    public HashMap<String, varType> varmap = new HashMap<>();
    public HashMap<String, funcType> funcmap = new HashMap<>();
    public funcType constructor = null;

    public classType(String classname) {
        this.classname = classname;
    }

    @Override
    public boolean equals(Type x) {
        return x.isNull() || (x instanceof classType && ((classType)x).classname.equals(this.classname));
    }
}
