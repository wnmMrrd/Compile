package Util.symbol;

import java.util.ArrayList;

public class funcType extends Type {
    public Type returntype;
    public String funcname;
    public ArrayList<varType> List;

    public funcType(String funcname) {
        this.funcname = funcname;
        List = new ArrayList<>();
    }

    public funcType(Type returntype, String funcname) {
        this.returntype = returntype;
        this.funcname = funcname;
        this.List = new ArrayList<>();
    }
}
