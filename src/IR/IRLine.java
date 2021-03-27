package IR;

import java.util.ArrayList;

public class IRLine {

    public enum lineType {
        FUNC, LABEL, MOVE, JUMP, CALL,
        BNEQ, BEQ,
        NEG, NOT, LOGICNOT,
        EQ, NEQ, GE, GEQ, LE, LEQ,
        ADD, SUB, MUL, DIV, MOD,
        OR, AND, XOR, SHL, SHR,
        INDEX, LOAD, LOADSTRING, RETURN,
        ADDI, LW, SW
    }

    public lineType type;
    public ArrayList<IRRegIdentifier>args = new ArrayList<>();
    public String func;
    public int label;

    public IRLine(lineType type) {
        this.type = type;
    }

    public void print(){
        switch (type){
            case FUNC: System.out.print("FUNC"); break;
            case LABEL: System.out.print("LABEL"); break;
            case MOVE: System.out.print("\tMOVE"); break;
            case JUMP: System.out.print("\tJUMP"); break;
            case CALL: System.out.print("\tCALL"); break;
            case BNEQ: System.out.print("\tBNEQ"); break;
            case BEQ: System.out.print("\tBEQ"); break;
            case NEG: System.out.print("\tNEG"); break;
            case NOT: System.out.print("\tNOT"); break;
            case LOGICNOT: System.out.print("\tLOGICNOT"); break;
            case NEQ: System.out.print("\tNEQ"); break;
            case GE: System.out.print("\tGE"); break;
            case GEQ: System.out.print("\tGEQ"); break;
            case EQ: System.out.print("\tEQ"); break;
            case LE: System.out.print("\tLE"); break;
            case LEQ: System.out.print("\tLEQ"); break;
            case ADD: System.out.print("\tADD"); break;
            case SUB: System.out.print("\tSUB"); break;
            case MUL: System.out.print("\tMUL"); break;
            case DIV: System.out.print("\tDIV"); break;
            case MOD: System.out.print("\tMOD"); break;
            case OR: System.out.print("\tOR"); break;
            case AND: System.out.print("\tAND"); break;
            case XOR: System.out.print("\tXOR"); break;
            case SHL: System.out.print("\tSHL"); break;
            case SHR: System.out.print("\tSHR"); break;
            case INDEX: System.out.print("\tINDEX"); break;
            case LOAD: System.out.print("\tLOAD"); break;
            case LOADSTRING: System.out.print("\tLOADSTRING"); break;
            case RETURN: System.out.print("\tRETURN"); break;
            case ADDI: System.out.print("\tADDI"); break;
            case LW: System.out.print("\tLW"); break;
            case SW: System.out.print("\tSW"); break;
        }
        if (label > 0){
            System.out.print("(");
            System.out.print(label);
            System.out.print(")");
        }
        if (func != null){
            System.out.print(" ");
            System.out.print(func);
        }
        for (IRRegIdentifier arg : args){
            System.out.print(" ");
            arg.print();
        }
        System.out.println();
    }

}