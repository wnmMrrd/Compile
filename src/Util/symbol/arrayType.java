package Util.symbol;

public class arrayType extends Type {
    public Type origintype;
    public int dim;

    public arrayType(Type origintype, int dim) {
        this.origintype = origintype;
        this.dim = dim;
    }

    @Override
    public boolean equals(Type x) {
        return x.isNull() || (x instanceof arrayType && (((arrayType)x).origintype.equals(this.origintype) && (((arrayType)x).dim == this.dim)));
    }
}
