package IR;

import java.util.ArrayList;

public class IRBlockList {

    public ArrayList<IRBlock> blocks = new ArrayList<>();
    public ArrayList<String> strings = new ArrayList<>();
    public ArrayList<Integer> globals = new ArrayList<>();
    public boolean haveNoReturn = false;

    public IRBlockList() {

    }

    public int addString(String str) {
        strings.add(str);
        return strings.size()-1;
    }

    public void print(){
        for (int i = 0; i < strings.size(); i++){
            System.out.print("STRING(");
            System.out.print(i);
            System.out.print(") ");
            System.out.println(strings.get(i));
        }
        for (int i = 0; i < globals.size(); i++){
            System.out.print("GLOBAL(");
            System.out.print(i);
            System.out.print(") ");
            System.out.println(globals.get(i));
        }
        blocks.forEach(b -> b.print());
    }

}