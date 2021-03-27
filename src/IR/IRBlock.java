package IR;

import Util.RegIdAllocator;
import IR.IRLine.lineType;

import java.util.ArrayList;

public class IRBlock {
    public RegIdAllocator idSet = null;
    public ArrayList<IRLine> lines = new ArrayList<>();
    public String name;
    public int retLab;

    public IRBlock(RegIdAllocator idSet, String name, int retLab) {
        this.idSet = idSet;
        this.name = name;
        this.retLab = retLab;
    }

    public void print(){
        lines.forEach(l -> l.print());
    }

}