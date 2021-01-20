package Frontend;

import AST.*;
import Util.symbol.*;

public class SymbolCollector implements ASTVisitor {
    Scope global;
    Scope current = null;

    public SymbolCollector(Scope global) {
        this.global = global;
        this.global.typemap.put("int", new simpleType("int"));
        this.global.typemap.put("bool", new simpleType("bool"));
        this.global.typemap.put("string", new simpleType("string"));
        this.global.typemap.put("void", new simpleType("void"));
        this.global.typemap.put("null", new simpleType("null"));

        {
            funcType func = new funcType(new simpleType("void"), "print");
            func.List.add(new varType(new simpleType("string"), "str"));
            this.global.funcmap.put("print", func);
        }

        {
            funcType func = new funcType(new simpleType("void"), "println");
            func.List.add(new varType(new simpleType("string"), "str"));
            this.global.funcmap.put("println", func);
        }

        {
            funcType func = new funcType(new simpleType("void"), "printInt");
            func.List.add(new varType(new simpleType("int"), "n"));
            this.global.funcmap.put("printInt", func);
        }

        {
            funcType func = new funcType(new simpleType("void"), "printlnInt");
            func.List.add(new varType(new simpleType("int"), "n"));
            this.global.funcmap.put("printlnInt", func);
        }

        {
            this.global.funcmap.put("getString", new funcType(new simpleType("string"), "getString"));
        }

        {
            this.global.funcmap.put("getInt", new funcType(new simpleType("int"), "getInt"));
        }

        {
            funcType func = new funcType(new simpleType("string"), "toString");
            func.List.add(new varType(new simpleType("int"), "i"));
            this.global.funcmap.put("toString", func);
        }

    }

    @Override
    public void visit(ProgramNode it) {
        current = global;
        it.subList.forEach(x -> x.accept(this));
    }

    @Override
    public void visit(TypeNode it) {

    }

    //Def
    @Override
    public void visit(classDef it) {
        classType t = new classType(it.classname);
        current = new Scope(current);
        it.varList.forEach(x -> x.accept(this));
        it.funcList.forEach(x -> x.accept(this));
        if (it.constructor != null) t.constructor = new funcType(it.constructor.id);
        t.varmap = current.varmap;
        t.funcmap = current.funcmap;
        current = current.parentScope;
        current.defineType(it.classname, t, it.pos);
    }

    @Override
    public void visit(funcDef it) {
        current.defineFunction(it.id, new funcType(it.id), it.pos);
    }

    //Stmt
    @Override
    public void visit(blockStmt it) {

    }

    @Override
    public void visit(varDefSubStmt it) {
        current.defineVariable(it.id, new varType(it.id), it.pos);
    }

    @Override
    public void visit(varDefStmt it) {

    }

    @Override
    public void visit(ifStmt it) {

    }

    @Override
    public void visit(forStmt it) {

    }

    @Override
    public void visit(whileStmt it) {

    }

    @Override
    public void visit(breakStmt it) {

    }

    @Override
    public void visit(continueStmt it) {

    }

    @Override
    public void visit(returnStmt it) {

    }

    @Override
    public void visit(pureExprStmt it) {

    }

    @Override
    public void visit(emptyStmt it) {

    }

    //Expr
    @Override
    public void visit(varExpr it) {

    }

    @Override
    public void visit(thisExpr it) {

    }

    @Override
    public void visit(DecimalIntegerExpr it) {

    }

    @Override
    public void visit(BoolValueExpr it) {

    }

    @Override
    public void visit(StringLiteralExpr it) {

    }

    @Override
    public void visit(NullExpr it) {

    }

    @Override
    public void visit(memberExpr it) {

    }

    @Override
    public void visit(newExpr it) {

    }

    @Override
    public void visit(subscriptExpr it) {

    }

    @Override
    public void visit(funcCallExpr it) {

    }

    @Override
    public void visit(suffixExpr it) {

    }

    @Override
    public void visit(prefixExpr it) {

    }

    @Override
    public void visit(binaryExpr it) {

    }

    @Override
    public void visit(expressionListExpr it) {

    }

}
