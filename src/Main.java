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

        //InputStream input = System.in;
        String name = "test.mx";
        InputStream input = new FileInputStream(name);

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
            //System.out.println(1);
            new SymbolCollector(global).visit(ASTRoot);
            //System.out.println(2);
            new TypeCollector(global).visit(ASTRoot);
            //System.out.println(3);
            global.varmap.clear();
            IRBlockList bkList = new IRBlockList();
            new SemanticChecker(global, bkList).visit(ASTRoot);
            new IRBuilder(global, bkList).visit(ASTRoot);
            //bkList.print();
            bkList.initASM();
            bkList.printASM();
        } catch (Error er) {
            System.err.println(er.toString());
            throw new RuntimeException();
        }
    }
}