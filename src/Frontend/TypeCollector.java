package Frontend;

import AST.*;
import Util.symbol.Scope;
import Util.symbol.classType;
import Util.symbol.varType;
import Util.symbol.simpleType;

public class TypeCollector implements ASTVisitor {
    Scope global;
    String currentclass = null;

    public TypeCollector(Scope global) {
        this.global = global;
    }

    @Override
    public void visit(ProgramNode it) {
        it.subList.forEach(x -> x.accept(this));
    }

    @Override
    public void visit(TypeNode it) {

    }

    //Def
    @Override
    public void visit(classDef it) {
        currentclass = it.classname;
        it.varList.forEach(x -> x.accept(this));
        it.funcList.forEach(x -> x.accept(this));
        if (it.constructor !=null) ((classType)global.typemap.get(currentclass)).constructor.returntype = new simpleType("void");
        currentclass = null;
    }

    @Override
    public void visit(funcDef it) {
        if (currentclass == null) {
            global.funcmap.get(it.id).returntype = global.getType(it.returntype);
            it.List.forEach(x -> global.funcmap.get(it.id).List.add(new varType(global.getType(x.type), x.id)));
        } else {
            ((classType)global.typemap.get(currentclass)).funcmap.get(it.id).returntype = global.getType(it.returntype);
            it.List.forEach(x -> ((classType)global.typemap.get(currentclass)).funcmap.get(it.id).List.add(new varType(global.getType(x.type), x.id)));
        }
    }

    //Stmt
    @Override
    public void visit(blockStmt it) {

    }

    @Override
    public void visit(varDefSubStmt it) {
        if (currentclass == null) global.varmap.get(it.id).origintype = global.getType(it.type);
        else ((classType)global.typemap.get(currentclass)).varmap.get(it.id).origintype = global.getType(it.type);
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
