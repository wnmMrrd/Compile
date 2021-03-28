import AST.ProgramNode;
import Frontend.ASTBuilder;
import Frontend.SemanticChecker;
import Frontend.SymbolCollector;
import Frontend.TypeCollector;
import IR.IRBlockList;
import IR.IRBuilder;
import Parser.MxLexer;
import Parser.MxParser;
import Util.MxErrorListener;
import Util.error.Error;
import Util.symbol.Scope;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.io.InputStream;

import java.io.FileInputStream;

public class Main {
    public static void main(String[] args) throws Exception {

        boolean onlySemantic = false, onlyIR = false;
        InputStream input = System.in;
        for (String arg : args) {
            switch (arg) {
                case "-semantic":
                    onlySemantic = true;
                    break;
                case "-IR":
                    onlyIR = true;
                    break;
                case "-test":
                    String name = "test.mx";
                    input = new FileInputStream(name);
                    break;
            }
        }

        try {
            ProgramNode ASTRoot;
            Scope global = new Scope(null);

            MxLexer lexer = new MxLexer(CharStreams.fromStream(input));
            lexer.removeErrorListeners();
            lexer.addErrorListener(new MxErrorListener());
            MxParser parser = new MxParser(new CommonTokenStream(lexer));
            parser.removeErrorListeners();
            parser.addErrorListener(new MxErrorListener());
            ParseTree parseTreeRoot = parser.program();
            ASTBuilder astBuilder = new ASTBuilder();
            ASTRoot = (ProgramNode) astBuilder.visit(parseTreeRoot);
            new SymbolCollector(global).visit(ASTRoot);
            new TypeCollector(global).visit(ASTRoot);
            global.varmap.clear();
            IRBlockList bkList = new IRBlockList();
            new SemanticChecker(global, bkList).visit(ASTRoot);
            if (!onlySemantic){
                new IRBuilder(global, bkList).visit(ASTRoot);
                if (onlyIR){
                    bkList.print();
                }else{
                    bkList.initASM();
                    bkList.printASM();
                }
            }
        } catch (Error er) {
            System.err.println(er.toString());
            throw new RuntimeException();
        }
    }
}