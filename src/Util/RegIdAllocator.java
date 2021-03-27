package Util;

import IR.IRRegIdentifier;

public class RegIdAllocator {

    public int[] idArray = new int[20];

    public RegIdAllocator() {

    }

    public IRRegIdentifier RegIdAlloca(int tp) {
        return new IRRegIdentifier(idArray[tp]++, tp, false);
    }

}