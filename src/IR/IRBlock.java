package IR;

import Util.RegIdAllocator;
import IR.IRLine.lineType;

import java.util.ArrayList;

public class IRBlock {
    public RegIdAllocator idSet = null;
    public ArrayList<IRLine> lines = new ArrayList<>();
    public String name;
    public int retLab;

    public boolean containsCALL = false;
    public int localSz = 0;

    public IRBlock(RegIdAllocator idSet, String name, int retLab) {
        this.idSet = idSet;
        this.name = name;
        this.retLab = retLab;
    }

    public void print(){
        lines.forEach(l -> l.print());
    }
    public void expand(){
        ArrayList<IRLine> new_lines = new ArrayList<>();
        lines.forEach(line -> {
            boolean flag = false;
            if (line.type == lineType.CALL)  containsCALL = true;
            else if (line.type != lineType.JUMP && line.type != lineType.RETURN && line.type != lineType.FUNC && line.type != lineType.LABEL) {
                int i = 0;
                if (line.type != lineType.BNEQ && line.type != lineType.BEQ && line.type != lineType.SW) {
                    i = 1;
                    flag = true;
                }
                for (; i<line.args.size(); i++) {
                    IRRegIdentifier rd = line.args.get(i);
                    if (rd.type == 6) {
                        IRRegIdentifier temp = idSet.RegIdAlloca(5);
                        IRLine new_line = new IRLine(lineType.LW);
                        new_line.args.add(temp);
                        new_line.args.add(new IRRegIdentifier(rd.id, 5, false));
                        new_lines.add(new_line);
                        line.args.set(i, temp);
                    } else if (rd.type == 1 || rd.type == 4) {
                        IRRegIdentifier temp = idSet.RegIdAlloca(5);
                        IRLine new_line = new IRLine(lineType.LW);
                        new_line.args.add(temp);
                        new_line.args.add(rd);
                        new_lines.add(new_line);
                        line.args.set(i, temp);
                    } else if (rd.type == 2) {
                        IRRegIdentifier temp = idSet.RegIdAlloca(5);
                        IRLine new_line = new IRLine(lineType.LOAD);
                        new_line.args.add(temp);
                        new_line.args.add(rd);
                        new_lines.add(new_line);
                        new_line = new IRLine(lineType.LW);
                        new_line.args.add(temp);
                        new_line.args.add(rd);
                        new_line.args.add(temp);
                        new_lines.add(new_line);
                        line.args.set(i, temp);
                    }
                }
            }
            new_lines.add(line);
            if (flag) {
                IRRegIdentifier rd = line.args.get(0);
                IRLine new_line;
                IRRegIdentifier temp1, temp2;
                switch (rd.type){
                    case 3:
                        if (rd.id >= 6) {
                            temp1 = idSet.RegIdAlloca(5);
                            new_line = new IRLine(lineType.SW);
                            new_line.args.add(temp1);
                            IRRegIdentifier an_temp;
                            if (idSet.idArray[7] < rd.id-5) an_temp = idSet.RegIdAlloca(7);
                            else an_temp = new IRRegIdentifier(rd.id-6, 7, false);
                            new_line.args.add(an_temp);
                            new_lines.add(new_line);
                            line.args.set(0, temp1);
                        }
                        break;
                    case 6:
                        temp1 = idSet.RegIdAlloca(5);
                        new_line = new IRLine(lineType.SW);
                        new_line.args.add(temp1);
                        new_line.args.add(new IRRegIdentifier(rd.id, 5, false));
                        new_lines.add(new_line);
                        line.args.set(0, temp1);
                        break;
                    case 1:
                    case 4:
                        temp1 = idSet.RegIdAlloca(5);
                        new_line = new IRLine(lineType.SW);
                        new_line.args.add(temp1);
                        new_line.args.add(rd);
                        new_lines.add(new_line);
                        line.args.set(0, temp1);
                        break;
                    case 2:
                        temp1 = idSet.RegIdAlloca(5);
                        temp2 = idSet.RegIdAlloca(5);
                        new_line = new IRLine(lineType.LOAD);
                        new_line.args.add(temp2);
                        new_line.args.add(rd);
                        new_lines.add(new_line);
                        new_line = new IRLine(lineType.SW);
                        new_line.args.add(temp1);
                        new_line.args.add(rd);
                        new_line.args.add(temp2);
                        new_lines.add(new_line);
                        line.args.set(0, temp1);
                        break;
                }
            }
        });
        lines = new_lines;
        localSz = idSet.idArray[1];
    }

    public void expandLocal() {
        ArrayList new_lines = new ArrayList<>();
        lines.forEach(line -> {
            boolean flag = false;
            if (line.type != lineType.CALL && line.type != lineType.JUMP && line.type != lineType.RETURN && line.type != lineType.FUNC && line.type != lineType.LABEL) {
                int i = 0;
                if (line.type != lineType.BNEQ && line.type != lineType.BEQ && line.type != lineType.SW) {
                    i = 1;
                    flag = true;
                }
                for (; i<line.args.size(); i++) {
                    IRRegIdentifier rd = line.args.get(i);
                    if (rd.type == 1 && rd.id >= localSz) {
                        IRRegIdentifier temp = idSet.RegIdAlloca(5);
                        IRLine new_line = new IRLine(lineType.LW);
                        new_line.args.add(temp);
                        new_line.args.add(rd);
                        new_lines.add(new_line);
                        line.args.set(i, temp);
                    }
                }
            }
            new_lines.add(line);
            if (flag) {
                IRRegIdentifier rd = line.args.get(0), temp1;
                IRLine new_line;
                switch (rd.type){
                    case 1:
                        if (rd.id < localSz) break;
                        temp1 = idSet.RegIdAlloca(5);
                        new_line = new IRLine(lineType.SW);
                        new_line.args.add(temp1);
                        new_line.args.add(rd);
                        new_lines.add(new_line);
                        line.args.set(0, temp1);
                        break;
                }
            }
        });
        lines = new_lines;
    }

    public int[] reg_free = new int [32];
    public int[] used, first_used, last_used;
    public IRRegIdentifier[] used_reg, used_l_reg;

    public int getFreeReg(int id) {
        for (int i=0; i<32; i++) {
            if (reg_free[i] == 1) {
                boolean flag = false;
                for (int j=first_used[id]; j<last_used[id] && !flag ; j++) {
                    IRLine line = lines.get(j);
                    if (line.args.size()>0 && line.args.get(0).type == 3 && line.args.get(0).id == i-10) flag = true;
                }
                if (!flag) return i;
            }
        }
        return 0;
    }

    public void easyAlloc(IRLine line, int l,int r) {
        for (int i=l; i<r; i++) {
            IRRegIdentifier rd = line.args.get(i), temp;
            if (rd.type == 5) {
                if (used_reg[rd.id] == null) {
                    if (used_l_reg[rd.id] != null) temp = used_l_reg[rd.id];
                    else {
                        int reg = getFreeReg(rd.id);
                        if (reg == 0) temp = used_l_reg[rd.id] = idSet.RegIdAlloca(1);
                        else {
                            reg_free[reg] = 0;
                            temp = used_reg[rd.id] = new IRRegIdentifier(reg, 0, false);
                        }
                    }
                } else temp = used_reg[rd.id];
                temp.useId =rd.id;
                line.args.set(i, temp);
            }
        }
    }


    public void easyRelease(IRLine line, int l, int r) {
        for (int i=l; i<r; i++) {
            IRRegIdentifier rd = line.args.get(i), temp;
            if (rd.useId != null) {
                if (used_l_reg[rd.useId] == null) {
                    used[rd.useId]--;
                    if (used[rd.useId] == 0) reg_free[used_reg[rd.useId].id] = 1;
                }
            }
        }
    }

    public void alloc() {
        int tempSz = idSet.idArray[5];
        used = new int[tempSz];
        first_used = new int[tempSz];
        last_used = new int[tempSz];
        used_reg = new IRRegIdentifier[tempSz];
        used_l_reg = new IRRegIdentifier[tempSz];
        for (int i=0; i<32; i++)
            reg_free[i] = (10<=i&&i<=15) ? 1:0;

        for (int i=0; i<lines.size(); i++) {
            IRLine line = lines.get(i);
            for (int j=0; j<line.args.size() ;j++) {
                IRRegIdentifier rd = line.args.get(j);
                if (rd.type == 5) {
                    used[rd.id]++;
                    if (first_used[rd.id] == 0) first_used[rd.id] = i;
                    last_used[rd.id] = i;
                }
            }
        }

        lines.forEach(line -> {
            switch (line.type) {
                default:
                case LW:
                    easyAlloc(line, 1, line.args.size());
                    easyRelease(line, 1, line.args.size());
                    IRRegIdentifier temp = line.args.get(0);
                    if (temp.type == 3) {
                        if (temp.id < 6) {
                            reg_free[temp.id+10] = 0;
                            line.args.set(0, new IRRegIdentifier(temp.id+10, 0, false));
                        } else line.args.set(0, idSet.RegIdAlloca(7));
                    } else {
                        easyAlloc(line, 0, 1);
                        easyRelease(line, 0, 1);
                    }
                    break;
                case BNEQ:
                case BEQ:
                case SW:
                    easyAlloc(line, 0, line.args.size());
                    easyRelease(line, 0, line.args.size());
                    break;
                case CALL:
                    for (int i=0; i<tempSz; i++) {
                        if (used[i] > 0) {
                            if (used_reg[i] != null){
                                IRRegIdentifier new_reg = idSet.RegIdAlloca(1);
                                used_reg[i].type = new_reg.type;
                                used_reg[i].id = new_reg.id;
                                used_reg[i].isPointer = new_reg.isPointer;
                                used[i] = 0;
                            }
                        }
                    }
                    for (int i=10; i<=15; i++)
                        reg_free[i] = 1;
                    break;
                case FUNC:
                case LABEL:
                case JUMP:
                case RETURN:
            }
        });
    }

    public void allocLocal() {
        int tempSz = idSet.idArray[5];
        used = new int[tempSz];
        first_used = new int[tempSz];
        last_used = new int[tempSz];
        used_reg = new IRRegIdentifier[tempSz];
        used_l_reg = new IRRegIdentifier[tempSz];
        for (int i=0; i<32; i++)
            reg_free[i] = (16<=i&&i<=17) ? 1:0;

        for (int i=0; i<lines.size(); i++) {
            IRLine line = lines.get(i);
            for (int j=0; j<line.args.size() ;j++) {
                IRRegIdentifier rd = line.args.get(j);
                if (rd.type == 5) {
                    used[rd.id]++;
                    if (first_used[rd.id] == 0) first_used[rd.id] = i;
                    last_used[rd.id] = i;
                }
            }
        }

        lines.forEach(line -> {
            switch (line.type) {
                default:
                case LW:
                    easyAlloc(line, 1, line.args.size());
                    easyRelease(line, 1, line.args.size());
                    easyAlloc(line, 0, 1);
                    easyRelease(line, 0, 1);
                    break;
                case BNEQ:
                case BEQ:
                case SW:
                    easyAlloc(line, 0, line.args.size());
                    easyRelease(line, 0, line.args.size());
                    break;
                case CALL:
                    for (int i=16; i<=17; i++)
                        reg_free[i] = 1;
                    break;
                case FUNC:
                case LABEL:
                case JUMP:
                case RETURN:
            }
        });
    }

    public int realRAM, addrStartLocal;
    public void calcRAM() {
        int totalRAM = idSet.idArray[1]+idSet.idArray[7];
        addrStartLocal = -4;
        if (containsCALL) {
            totalRAM++;
            addrStartLocal -= 4;
        }
        realRAM = (totalRAM/4+1)*16;
    }

    public int addrLocal(int id){
        return addrStartLocal+(id+1)*-4;
    }

    public int addrParam(int id){
        return id*4;
    }

    public void cutMove() {
        ArrayList new_lines = new ArrayList<>();
        lines.forEach(line -> {
            if (line.type != lineType.MOVE || line.args.get(0).id != line.args.get(1).id) new_lines.add(line);
        });
        lines = new_lines;
    }

    public void printASM(){
        System.out.println("\t.text");
        System.out.println("\t.align\t2");
        System.out.println("\t.globl\t"+name);
        System.out.println("\t.type\t"+name+", @function");
        System.out.println(name+":");
        System.out.println("\taddi\tsp,sp,-"+String.valueOf(realRAM));
        System.out.println("\tsw\ts0,"+String.valueOf(realRAM-4)+"(sp)");
        if (containsCALL) System.out.println("\tsw\tra,"+String.valueOf(realRAM-8)+"(sp)");
        System.out.println("\taddi\ts0,sp,"+String.valueOf(realRAM));
        lines.forEach(l -> l.printASM(this));
        System.out.println(".LAB"+String.valueOf(retLab)+":");
        System.out.println("\tlw\ts0,"+String.valueOf(realRAM-4)+"(sp)");
        if (containsCALL) System.out.println("\tlw\tra,"+String.valueOf(realRAM-8)+"(sp)");
        System.out.println("\taddi\tsp,sp,"+String.valueOf(realRAM));
        System.out.println("\tjr\tra");
        System.out.println("\t.size\t"+name+", .-"+name);
    }

}