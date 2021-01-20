package Util.symbol;

public class varType {
    public Type origintype;
    public String varname;

    public varType(String varname) {
        this.varname = varname;
    }

    public varType(Type origintype, String varname) {
        this.origintype = origintype;
        this.varname = varname;
    }

}
