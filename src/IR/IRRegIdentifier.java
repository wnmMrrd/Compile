package IR;

public class IRRegIdentifier {

    public int type, id;
    public boolean isPointer;

    public IRRegIdentifier(int id, int type, boolean isPointer) {
        this.type = type;
        this.id = id;
        this.isPointer = isPointer;
    }

    public void print(){
        //if (pointer) System.out.print("*");
        switch (type){
            case 1: System.out.print("L"); break;//local
            case 2: System.out.print("G"); break;
            case 3: System.out.print("P"); break;
            case 4: System.out.print("F"); break;
            case 5: System.out.print("T"); break;//temp
            case 6: System.out.print("*T"); break;
            case 7: System.out.print("LP"); break;
            case 8: System.out.print("i"); break;//integer
            case 9: System.out.print("S"); break;
            case 10: System.out.print("Q"); break;
            case 11: System.out.print("C"); break;//class
        }
        System.out.print(id);
    }

}