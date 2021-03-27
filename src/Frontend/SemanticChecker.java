package Frontend;

import AST.*;
import Util.error.semanticError;
import Util.symbol.*;
import Util.RegIdAllocator;
import IR.*;

public class SemanticChecker implements ASTVisitor {
    public Scope global,current;
    public classType currentclass;
    public Type currentreturntype;
    public boolean returndone;
    public int depth = 0;
    public IRBlockList bkList;

    public SemanticChecker(Scope global, IRBlockList bkList) {
        this.global = global;
        global.idSet = new RegIdAllocator();
        this.bkList = bkList;
    }

    @Override public void visit(ProgramNode it) {
        funcType mainfunc = global.getFunction("main", false, it.pos);
        if (!mainfunc.returntype.isInt()) throw new semanticError("ProgramNode:main function must return int", it.pos);
        if (mainfunc.List.size() != 0) throw new semanticError("ProgramNode:main function contains parameters", it.pos);
        current = global;
        it.subList.forEach(x -> x.accept(this));
    }

    @Override public void visit(TypeNode it) {

    }

    @Override public void visit(classDef it) {
        current = new Scope(current);
        current.idSet = new RegIdAllocator();
        it.scope = current;
        currentclass = (classType)global.typemap.get(it.classname);
        currentclass.varmap.forEach((key, value) -> current.defineVariable(key, value, it.pos));
        currentclass.funcmap.forEach((key, value) -> current.defineFunction(key, value, it.pos));
        if (it.constructor != null) {
            if (!it.constructor.id.equals(it.classname)) throw new semanticError("classDef:constructor name wrong", it.pos);
            it.constructor.accept(this);
        }
        it.varList.forEach( x -> {
            x.scope=current;
            x.scope.defineVarId(x.id, 11);
                });
        it.funcList.forEach(x -> x.accept(this));
        current = current.parentScope;
        currentclass = null;
    }

    @Override public void visit(funcDef it) {
        if (it.returntype == null) currentreturntype = new simpleType("void");
        else currentreturntype = global.getType(it.returntype);
        returndone = false;
        current = new Scope(current);
        current.idSet = new RegIdAllocator();
        it.scope = current;
        if (currentclass != null) {
            current.defineVariable("!this", new varType(currentclass, "!this"), it.pos);
            current.defineVarId("!this", 1);
        }
        it.List.forEach(
                x -> {
                    x.scope = current;
                    x.regId = current.defineVarId(x.id, 1);
                });
        it.body.accept(this);
        current = current.parentScope;
        if (it.id.equals("main")) {
            if (!returndone) bkList.haveNoReturn = true;
            returndone = true;
        }
        if(it.returntype != null && !it.returntype.origintype.equals("void") && !returndone) {
            throw new semanticError("funcDef:no return", it.pos);
        }
    }

    @Override public void visit(blockStmt it) {
        it.scope = current;
        it.stmtList.forEach(x -> {
            if (x instanceof blockStmt) {
                current = new Scope(current);
                x.accept(this);
                current = current.parentScope;
            } else {
                x.accept(this);
            }
        });
    }

    @Override public void visit(varDefSubStmt it) {
        it.scope = current;
        Type vartype = global.getType(it.type);
        if (vartype.isVoid()) throw new semanticError("varDefSubStmt:void variable", it.pos);
        if (it.expr != null) {
            it.expr.accept(this);
            if (!it.expr.type.equals(vartype)) throw new semanticError("varDefSubStmt:variable not match", it.pos);
        }
        current.defineVariable(it.id, new varType(vartype, it.id), it.pos);
        if (current == global) {
            bkList.globals.add(0);
            current.defineVarId(it.id, 2);
        } else current.defineVarId(it.id, 1);
    }

    @Override public void visit(varDefStmt it) {
        it.scope = current;
        it.varList.forEach(x -> x.accept(this));
    }

    @Override public void visit(ifStmt it) {
        it.scope = current;
        it.cond.accept(this);
        if (!it.cond.type.isBool()) throw new semanticError("ifStmt:if condition not bool", it.pos);
        current = new Scope(current);
        it.thenStmt.accept(this);
        current = current.parentScope;
        if (it.elseStmt != null) {
            current = new Scope(current);
            it.elseStmt.accept(this);
            current = current.parentScope;
        }
    }

    @Override public void visit(forStmt it) {
        it.scope = current;
        if (it.init != null) it.init.accept(this);
        if (it.cond != null) {
            it.cond.accept(this);
            if (!it.cond.type.isBool()) throw new semanticError("forStmt:for condition not bool", it.pos);
        }
        if (it.incr != null) it.incr.accept(this);
        depth++;
        current = new Scope(current);
        it.body.accept(this);
        depth--;
        current = current.parentScope;
    }

    @Override public void visit(whileStmt it) {
        it.scope = current;
        it.cond.accept(this);
        if (!it.cond.type.isBool()) throw new semanticError("whileStmt:while condition not bool", it.pos);
        depth++;
        current = new Scope(current);
        it.body.accept(this);
        depth--;
        current = current.parentScope;
    }

    @Override public void visit(breakStmt it) {
        it.scope = current;
        if (depth == 0) throw new semanticError("breakStmt:break outside loop", it.pos);
    }

    @Override public void visit(continueStmt it) {
        it.scope = current;
        if (depth == 0) throw new semanticError("continueStmt:continue outside loop", it.pos);
    }

    @Override public void visit(returnStmt it) {
        it.scope = current;
        returndone = true;
        if (it.value == null) {
            if (!currentreturntype.isVoid()) throw new semanticError("returnStmt:void contains return", it.pos);
        } else {
            it.value.accept(this);
            if (!currentreturntype.equals(it.value.type)) throw new semanticError("returnStmt:not current return type", it.pos);
        }
    }

    @Override public void visit(pureExprStmt it) {
        it.scope = current;
        it.expr.accept(this);
    }

    @Override public void visit(emptyStmt it) {
    }

    @Override public void visit(varExpr it) {
        it.scope = current;
        it.regId = current.getRegId(it.id ,true);
        it.type = current.getVariable(it.id, true, it.pos).origintype;
    }

    @Override public void visit(thisExpr it) {
        if (currentclass == null) throw new semanticError("thisExpr:this outside class", it.pos);
        else it.type = currentclass;
    }

    @Override public void visit(DecimalIntegerExpr it) {
        it.scope = current;
        it.type = new simpleType("int");
    }

    @Override public void visit(BoolValueExpr it) {
        it.scope = current;
        it.type = new simpleType("bool");
    }

    @Override public void visit(StringLiteralExpr it) {
        it.scope = current;
        it.type = new simpleType("string");
    }

    @Override public void visit(NullExpr it) {
        it.scope = current;
        it.type = new simpleType("null");
    }

    @Override public void visit(memberExpr it) {
        it.firstExpr.accept(this);
        if (it.firstExpr.type instanceof arrayType && it.id.equals("size")) {
            it.type = new funcType(new simpleType("int"), "size");
            it.From = it.firstExpr.type;
        } else if (it.firstExpr.type.isString() && it.id.equals("length")) {
            it.type = new funcType(new simpleType("int"), "length");
            it.From = it.firstExpr.type;
        } else if (it.firstExpr.type.isString() && it.id.equals("substring")) {
            it.type = new funcType(new simpleType("string"), "substring");
            ((funcType)it.type).List.add(new varType(new simpleType("int"), "left"));
            ((funcType)it.type).List.add(new varType(new simpleType("int"), "right"));
            it.From = it.firstExpr.type;
        } else if (it.firstExpr.type.isString() && it.id.equals("parseInt")) {
            it.type = new funcType(new simpleType("int"), "parseInt");
            it.From = it.firstExpr.type;
        } else if (it.firstExpr.type.isString() && it.id.equals("ord")) {
            it.type = new funcType(new simpleType("int"), "ord");
            ((funcType)it.type).List.add(new varType(new simpleType("int"), "pos"));
            it.From = it.firstExpr.type;
        } else if (it.firstExpr.type instanceof classType) {
            classType originclass = (classType)it.firstExpr.type;
            if (it.isfunc) {
                if(originclass.funcmap.containsKey(it.id)) it.type = originclass.funcmap.get(it.id);
                else throw new semanticError("memberExpr:function not included in class", it.pos);
                it.From = it.firstExpr.type;
            } else {
                if(originclass.varmap.containsKey(it.id)) it.type = originclass.varmap.get(it.id).origintype;
                else throw new semanticError("memberExpr:variable not included in class", it.pos);
            }
        } else {
            throw new semanticError("memberExpr:undefined member", it.pos);
        }
    }

    @Override public void visit(newExpr it) {
        if (it.sizeList != null) {
            it.sizeList.forEach(x -> {
                x.accept(this);
                if (!x.type.isInt()) throw new semanticError("newExpr:size not int", x.pos);
            });
        }
        it.type = global.getType(it.subtype);
    }

    @Override public void visit(subscriptExpr it) {
        it.id.accept(this);
        if (!(it.id.type instanceof arrayType)) throw new semanticError("subscriptExpr:id not array", it.pos);
        it.index.accept(this);
        if (!it.index.type.isInt()) throw new semanticError("subscriptExpr:index not int", it.pos);
        arrayType type = (arrayType)it.id.type;
        if (type.dim == 1) it.type = type.origintype;
        else it.type = new arrayType(type.origintype, type.dim-1);
    }

    @Override public void visit(funcCallExpr it) {
        if (it.firstExpr instanceof varExpr) {
            it.firstExpr.type = current.getFunction(((varExpr)it.firstExpr).id, true, it.pos);
            if (currentclass != null && currentclass.funcmap.containsKey(((varExpr)it.firstExpr).id)) it.firstExpr.From = currentclass;
        } else {
            it.firstExpr.accept(this);
            if (!(it.firstExpr.type instanceof funcType)) throw new semanticError("funcCallExpr:not a function", it.pos);
        }
        it.List.forEach(x -> x.accept(this));
        it.scope = current;
        funcType functype = (funcType)it.firstExpr.type;
        if(functype.List.size() != it.List.size()) throw new semanticError("funcCallExpr:size not match", it.pos);
        for (int i=0; i<functype.List.size(); i++) {
            if (!functype.List.get(i).origintype.equals(it.List.get(i).type)) throw new semanticError("funcCallExpr:type not match", it.pos);
        }
        it.type = functype.returntype;
    }

    @Override public void visit(suffixExpr it) {
        it.lhs.accept(this);
        if (!it.lhs.type.isInt()) throw new semanticError("suffixExpr:lhs not int", it.pos);
        if (!it.lhs.assignable) throw new semanticError("suffixExpr:lhs not assignable", it.pos);
        it.type = it.lhs.type;
    }

    @Override public void visit(prefixExpr it) {
        it.rhs.accept(this);
        it.type = it.rhs.type;
        if (it.opcode == "++" || it.opcode == "--") {
            if (!it.rhs.type.isInt()) throw new semanticError("prefixExpr:rhs not int", it.pos);
            if (!it.rhs.assignable) throw new semanticError("prefixExpr:rhs not assignable", it.pos);
            it.assignable = true;
        } else if (it.opcode == "+" || it.opcode == "-" || it.opcode == "~") {
            if (!it.rhs.type.isInt()) throw new semanticError("prefixExpr:rhs not int", it.pos);
        } else if (it.opcode == "!") {
            if (!it.rhs.type.isBool()) throw new semanticError("prefixExpr:rhs not bool", it.pos);
        }
        it.assignable = true;
    }

    @Override public void visit(binaryExpr it) {
        it.lhs.accept(this);
        it.rhs.accept(this);
        if (!it.lhs.type.equals(it.rhs.type)) throw new semanticError("binaryExpr:not same type", it.pos);
        switch (it.opcode) {
            case "==":
            case "!=":
                it.type = new simpleType("bool");
                break;
            case "<":
            case ">":
            case "<=":
            case ">=":
                if (!it.lhs.type.isInt() && !it.lhs.type.isString()) throw new semanticError("binaryExpr:cmp not int or string", it.pos);
                it.type = new simpleType("bool");
                break;
            case "+":
                if (!it.lhs.type.isInt() && !it.lhs.type.isString()) throw new semanticError("binaryExpr:add not int or string", it.pos);
                it.type = it.lhs.type;
                break;
            case "-":
            case "*":
            case "/":
            case "%":
            case "&":
            case "^":
            case "|":
            case "<<":
            case ">>":
                if (!it.lhs.type.isInt()) throw new semanticError("binaryExpr:not int", it.pos);
                it.type = new simpleType("int");
                break;
            case "&&":
            case "||":
                if (!it.lhs.type.isBool()) throw new semanticError("binaryExpr:not bool", it.pos);
                it.type = new simpleType("bool");
                break;
            case "=":
                if (!it.lhs.assignable) throw new semanticError("binaryExpr:lhs not assignable", it.pos);
                it.type = it.lhs.type;
                it.assignable = true;
                break;
            default:
                break;
        }
    }

    @Override public void visit(expressionListExpr it) {

    }

}
