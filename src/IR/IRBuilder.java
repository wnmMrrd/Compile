package IR;

import AST.*;
import IR.IRLine.lineType;
import Util.symbol.*;

import java.util.ArrayList;

public class IRBuilder implements ASTVisitor {

    //public boolean Flag=false;

    public Scope global;
    public classType currentclass = null;
    public ArrayList<varDefSubStmt> globalVar;

    public int labelNum = 0;
    public int loopstLab, loopedLab, loopconLab;
    public int endLab, elseLab;

    public IRBlockList bkList = null;
    public IRBlock currentBlock = null;

    public IRBuilder(Scope global, IRBlockList bkList) {
        this.global = global;
        this.bkList = bkList;
    }

    @Override
    public void visit(ProgramNode it) {
        globalVar = new ArrayList<>();
        for (int i=0; i<it.subList.size(); i++) {
            if (it.subList.get(i) instanceof varDefSubStmt) globalVar.add((varDefSubStmt)it.subList.get(i));
        }

        for (int i=0; i<it.subList.size(); i++) {
            if (!(it.subList.get(i) instanceof varDefSubStmt)) it.subList.get(i).accept(this);
        }
    }

    @Override
    public void visit(TypeNode it) {
    }

    //Def
    @Override
    public void visit(classDef it) {
        currentclass = (classType)global.typemap.get(it.classname);
        if (it.constructor != null) it.constructor.accept(this);
        it.funcList.forEach(x -> x.accept(this));
        currentclass = null;
    }

    @Override
    public void visit(funcDef it) {
        currentBlock = new IRBlock(it.scope.idSet, it.id2, ++labelNum);
        bkList.blocks.add(currentBlock);
        IRLine line = new IRLine(lineType.FUNC);
        line.func = it.id2;
        currentBlock.lines.add(line);

        if (currentclass == null) {
            for (int i=0; i<it.List.size(); i++) {
                line = new IRLine(lineType.MOVE);
                line.args.add(it.List.get(i).regId);
                if (i<6) line.args.add(new IRRegIdentifier(i+10, 0, false));
                else line.args.add(new IRRegIdentifier(i-6, 4, false));
                currentBlock.lines.add(line);
            }

            if (it.id.equals("main")) globalVar.forEach(x -> x.accept(this));

            it.body.accept(this);

            if (it.id.equals("main") && bkList.haveNoReturn) {
                line = new IRLine(lineType.MOVE);
                line.args.add(new IRRegIdentifier(10, 0, false));
                line.args.add(new IRRegIdentifier(0, 0, false));
                currentBlock.lines.add(line);
                line = new IRLine(lineType.JUMP);
                line.label = currentBlock.retLab;
                currentBlock.lines.add(line);
            }
        } else {
            IRRegIdentifier reg1 = new IRRegIdentifier(0, 1, false);
            line = new IRLine(lineType.MOVE);
            line.args.add(reg1);
            line.args.add(new IRRegIdentifier(10, 0, false));
            currentBlock.lines.add(line);

            for (int i=0; i<it.List.size(); i++) {
                line = new IRLine(lineType.MOVE);
                line.args.add(it.List.get(i).regId);
                if (i < 5) line.args.add(new IRRegIdentifier(i+11, 0, false));
                else line.args.add(new IRRegIdentifier(i-5, 4, false));
                currentBlock.lines.add(line);
            }

            it.body.accept(this);
        }
    }

    //Stmt
    @Override
    public void visit(blockStmt it) {
        it.stmtList.forEach(x -> x.accept(this));
    }

    @Override
    public void visit(varDefSubStmt it) {
        if (it.expr != null) {
            it.expr.accept(this);

            IRLine line =new IRLine(lineType.MOVE);
            line.args.add(it.scope.getRegId(it.id, false));
            line.args.add(it.expr.regId);
            currentBlock.lines.add(line);
        }
    }

    @Override
    public void visit(varDefStmt it) {
        it.varList.forEach(x -> x.accept(this));
    }

    @Override
    public void visit(ifStmt it) {
        int bef_endLab = endLab, bef_elseLab = elseLab;
        endLab = ++labelNum;
        if (it.elseStmt != null) elseLab = ++labelNum;

        it.cond.accept(this);
        IRLine line = new IRLine(lineType.BNEQ);
        line.label = it.elseStmt == null ? endLab : elseLab;
        line.args.add(it.cond.regId);
        line.args.add(new IRRegIdentifier(0, 0, false));
        currentBlock.lines.add(line);

        if (it.thenStmt != null) {
            it.thenStmt.accept(this);
            if (it.elseStmt != null) {
                line = new IRLine(lineType.JUMP);
                line.label = endLab;
                currentBlock.lines.add(line);
            }
        }

        if (it.elseStmt != null) {
            line = new IRLine(lineType.LABEL);
            line.label = elseLab;
            currentBlock.lines.add(line);
            it.elseStmt.accept(this);
        }

        line = new IRLine(lineType.LABEL);
        line.label = endLab;
        currentBlock.lines.add(line);

        endLab = bef_endLab; elseLab = bef_elseLab;
    }

    @Override
    public void visit(forStmt it) {
        int bef_loopstLab = loopstLab, bef_loopedLab = loopedLab, bef_loopconLab = loopconLab;
        loopstLab = ++labelNum; loopedLab = ++labelNum; loopconLab = ++labelNum;

        if (it.init != null) it.init.accept(this);

        IRLine line = new IRLine(lineType.LABEL);
        line.label = loopstLab;
        currentBlock.lines.add(line);

        if (it.cond != null) {
            it.cond.accept(this);
            line = new IRLine(lineType.BNEQ);
            line.label = loopedLab;
            line.args.add(it.cond.regId);
            line.args.add(new IRRegIdentifier(0, 0, false));
            currentBlock.lines.add(line);
        }

        if( it.body != null) it.body.accept(this);

        line = new IRLine(lineType.LABEL);
        line.label = loopconLab;
        currentBlock.lines.add(line);

        if(it.incr != null) it.incr.accept(this);

        line = new IRLine(lineType.JUMP);
        line.label = loopstLab;
        currentBlock.lines.add(line);

        line = new IRLine(lineType.LABEL);
        line.label = loopedLab;
        currentBlock.lines.add(line);

        loopstLab = bef_loopstLab; loopedLab = bef_loopedLab; loopconLab = bef_loopconLab;
    }

    @Override
    public void visit(whileStmt it) {
        int bef_loopstLab = loopstLab, bef_loopedLab = loopedLab, bef_loopconLab = loopconLab;
        loopstLab = ++labelNum; loopedLab = ++labelNum; loopconLab = loopstLab;

        IRLine line = new IRLine(lineType.LABEL);
        line.label = loopstLab;
        currentBlock.lines.add(line);

        if (it.cond != null) {
            it.cond.accept(this);
            line = new IRLine(lineType.BNEQ);
            line.label = loopedLab;
            line.args.add(it.cond.regId);
            line.args.add(new IRRegIdentifier(0, 0, false));
            currentBlock.lines.add(line);
        }

        if( it.body != null) it.body.accept(this);

        line = new IRLine(lineType.JUMP);
        line.label = loopstLab;
        currentBlock.lines.add(line);

        line = new IRLine(lineType.LABEL);
        line.label = loopedLab;
        currentBlock.lines.add(line);

        loopstLab = bef_loopstLab; loopedLab = bef_loopedLab; loopconLab = bef_loopconLab;
    }

    @Override
    public void visit(breakStmt it) {
        IRLine line = new IRLine(lineType.JUMP);
        line.label = loopedLab;
        currentBlock.lines.add(line);
    }

    @Override
    public void visit(continueStmt it) {
        IRLine line = new IRLine(lineType.JUMP);
        line.label = loopconLab;
        currentBlock.lines.add(line);
    }

    @Override
    public void visit(returnStmt it) {
        if (it.value != null) {
            it.value.accept(this);
            IRLine line = new IRLine(lineType.MOVE);
            line.args.add(new IRRegIdentifier(10, 0, false));
            line.args.add(it.value.regId);
            currentBlock.lines.add(line);
        }
        IRLine line = new IRLine(lineType.JUMP);
        line.label = currentBlock.retLab;
        currentBlock.lines.add(line);
    }

    @Override
    public void visit(pureExprStmt it) {
        it.expr.accept(this);
    }

    @Override
    public void visit(emptyStmt it) {
    }

    //Expr
    @Override
    public void visit(varExpr it) {
        //if (it.regId != null) System.out.println("shit");
        if (it.regId != null && it.regId.type == 11) {
            IRRegIdentifier tmp = currentBlock.idSet.RegIdAlloca(5);
            IRLine line = new IRLine(lineType.LOAD);
            line.args.add(tmp);
            line.args.add(new IRRegIdentifier(it.regId.id, 8, false));
            currentBlock.lines.add(line);

            IRRegIdentifier regId = currentBlock.idSet.RegIdAlloca(5);
            line = new IRLine(lineType.INDEX);
            line.args.add(regId);
            line.args.add(new IRRegIdentifier(0, 1, false));
            line.args.add(tmp);
            currentBlock.lines.add(line);

            it.regId = new IRRegIdentifier(regId.id, regId.type, true);
        }
    }

    @Override
    public void visit(thisExpr it) {
        it.regId = new IRRegIdentifier(0, 1, false);
    }

    @Override
    public void visit(DecimalIntegerExpr it) {
        IRLine line = new IRLine(lineType.LOAD);
        line.args.add(it.regId = currentBlock.idSet.RegIdAlloca(5));
        line.args.add(new IRRegIdentifier(it.value, 8, false));
        currentBlock.lines.add(line);
    }

    @Override
    public void visit(BoolValueExpr it) {
        IRLine line = new IRLine(lineType.LOAD);
        line.args.add(it.regId = currentBlock.idSet.RegIdAlloca(5));
        line.args.add(new IRRegIdentifier(it.value == true ? 1 : 0, 8, false));
        currentBlock.lines.add(line);
    }

    @Override
    public void visit(StringLiteralExpr it) {
        IRLine line = new IRLine(lineType.LOADSTRING);
        line.args.add(it.regId = currentBlock.idSet.RegIdAlloca(5));
        line.args.add(new IRRegIdentifier(bkList.addString(it.str.substring(1, it.str.length()-1)), 9, false));
        currentBlock.lines.add(line);
    }

    @Override
    public void visit(NullExpr it) {
        IRLine line = new IRLine(lineType.LOAD);
        line.args.add(it.regId = currentBlock.idSet.RegIdAlloca(5));
        line.args.add(new IRRegIdentifier(0, 8, false));
        currentBlock.lines.add(line);
    }

    @Override
    public void visit(memberExpr it) {
        it.firstExpr.accept(this);
        if (it.isfunc) {
            it.regId = it.firstExpr.regId;
        } else {
            IRRegIdentifier memberId = currentBlock.idSet.RegIdAlloca(5);
            IRLine line = new IRLine(lineType.LOAD);
            line.args.add(memberId);
            line.args.add(new IRRegIdentifier(it.scope.getIdVariable(it.id, true), 8, false));
            currentBlock.lines.add(line);

            IRRegIdentifier regId = currentBlock.idSet.RegIdAlloca(5);
            line = new IRLine(lineType.INDEX);
            line.args.add(regId);
            line.args.add(it.firstExpr.regId);
            line.args.add(memberId);
            currentBlock.lines.add(line);
            it.regId = new IRRegIdentifier(regId.id, regId.type, true);
        }
    }

    public IRRegIdentifier newMalloc(newExpr it, int i) {
        IRLine line;
        if (i < it.sizeList.size()) {
            IRRegIdentifier to = currentBlock.idSet.RegIdAlloca(1),
                    regid = currentBlock.idSet.RegIdAlloca(1),
                    tmp = currentBlock.idSet.RegIdAlloca(5);
            line = new IRLine(lineType.MOVE);
            line.args.add(to);
            line.args.add(it.sizeList.get(i).regId);
            currentBlock.lines.add(line);

            line = new IRLine(lineType.MOVE);
            line.args.add(tmp);
            line.args.add(it.sizeList.get(i).regId);
            currentBlock.lines.add(line);

            line = new IRLine(lineType.MOVE);
            line.args.add(new IRRegIdentifier(10, 0, false));
            line.args.add(tmp);
            currentBlock.lines.add(line);

            line = new IRLine(lineType.CALL);
            line.func = "my_array_alloc";
            currentBlock.lines.add(line);
            line = new IRLine(lineType.MOVE);
            line.args.add(regid);
            line.args.add(new IRRegIdentifier(10, 0, false));
            currentBlock.lines.add(line);

            int loopStart = ++labelNum, loopEnd = ++labelNum;
            line = new IRLine(lineType.LABEL);
            line.label = loopStart;
            currentBlock.lines.add(line);

            line = new IRLine(lineType.BNEQ);
            line.label = loopEnd;
            line.args.add(to);
            line.args.add(new IRRegIdentifier(0, 0, false));
            currentBlock.lines.add(line);

            IRRegIdentifier next_result = newMalloc(it, i+1);
            IRRegIdentifier result = currentBlock.idSet.RegIdAlloca(5);
            line = new IRLine(lineType.INDEX);
            line.args.add(result);
            line.args.add(regid);
            line.args.add(to);
            currentBlock.lines.add(line);

            line = new IRLine(lineType.MOVE);
            line.args.add(new IRRegIdentifier(result.id, result.type, true));
            line.args.add(next_result);
            currentBlock.lines.add(line);

            line = new IRLine(lineType.ADDI);
            line.args.add(to);
            line.args.add(to);
            line.args.add(new IRRegIdentifier(-1, 8, false));
            currentBlock.lines.add(line);

            line = new IRLine(lineType.JUMP);
            line.label = loopStart;
            currentBlock.lines.add(line);

            line = new IRLine(lineType.LABEL);
            line.label = loopEnd;
            currentBlock.lines.add(line);

            return regid;
        } else if (it.type instanceof classType) {
            line = new IRLine(lineType.LOAD);
            line.args.add(new IRRegIdentifier(10, 0, false));
            line.args.add(new IRRegIdentifier(((classType)it.type).varmap.size()<<2, 8, false));
            currentBlock.lines.add(line);

            line = new IRLine(lineType.CALL);
            line.func = "malloc";
            currentBlock.lines.add(line);

            IRRegIdentifier regid = currentBlock.idSet.RegIdAlloca(5);
            line = new IRLine(lineType.MOVE);
            line.args.add(regid);
            line.args.add(new IRRegIdentifier(10, 0, false));
            currentBlock.lines.add(line);

            if (((classType)it.type).constructor != null) {
                line = new IRLine(lineType.CALL);
                line.func = "my_c_"+((classType)it.type).classname+"_"+((classType)it.type).classname;
                currentBlock.lines.add(line);
            }

            return regid;
        } else {
            return new IRRegIdentifier(0, 0, false);
        }
    }

    @Override
    public void visit(newExpr it) {
        if(it.sizeList==null) it.sizeList = new ArrayList<>();
        it.sizeList.forEach(x -> x.accept(this));
        it.regId = newMalloc(it, 0);
    }

    @Override
    public void visit(subscriptExpr it) {
        it.id.accept(this);
        it.index.accept(this);

        IRRegIdentifier rd1 = currentBlock.idSet.RegIdAlloca(5), rd2 = currentBlock.idSet.RegIdAlloca(5);
        IRLine line = new IRLine(lineType.ADDI);
        line.args.add(rd1);
        line.args.add(it.index.regId);
        line.args.add(new IRRegIdentifier(1, 8, false));
        currentBlock.lines.add(line);

        line = new IRLine(lineType.INDEX);
        line.args.add(rd2);
        line.args.add(it.id.regId);
        line.args.add(rd1);
        currentBlock.lines.add(line);

        it.regId = new IRRegIdentifier(rd2.id, rd2.type, true);
    }

    @Override
    public void visit(funcCallExpr it) {
        it.firstExpr.accept(this);

        it.List.forEach(x -> x.accept(this));
        int have_ptr = 1;
        if (it.firstExpr instanceof varExpr && it.From == null) have_ptr = 0;
        for (int i=it.List.size()-1; i>=0; i--) {
            IRLine line = new IRLine(lineType.MOVE);
            line.args.add(new IRRegIdentifier(i+have_ptr, 3, false));
            line.args.add(it.List.get(i).regId);
            currentBlock.lines.add(line);
        }

        if (have_ptr == 1){
            if (it.firstExpr.From != null){
                IRLine line = new IRLine(lineType.MOVE);
                line.args.add(new IRRegIdentifier(0, 3, false));
                line.args.add(it.firstExpr.regId);
                currentBlock.lines.add(line);
            }else{
                IRLine line = new IRLine(lineType.MOVE);
                line.args.add(new IRRegIdentifier(0, 3, false));
                line.args.add(new IRRegIdentifier(0, 1, false));
                currentBlock.lines.add(line);
            }
        }

        IRLine line = new IRLine(lineType.CALL);
        if (have_ptr == 0) line.func = ((varExpr)it.firstExpr).id;
        else {
            if (it.firstExpr instanceof varExpr) line.func = ((classType)it.From).funcmap.get(((varExpr)it.firstExpr).id).funcname;
            else if (it.firstExpr.From instanceof arrayType) line.func = "my_array_size";
            else if (it.firstExpr.From.isString()) line.func = "my_c_string_"+((memberExpr)it.firstExpr).id;
            else line.func = ((classType)((memberExpr)it.firstExpr).From).funcmap.get(((memberExpr)it.firstExpr).id).funcname;
        }
        currentBlock.lines.add(line);

        it.regId = currentBlock.idSet.RegIdAlloca(5);
        line = new IRLine(lineType.MOVE);
        line.args.add(it.regId);
        line.args.add(new IRRegIdentifier(10, 0, false));
        currentBlock.lines.add(line);
    }

    @Override
    public void visit(suffixExpr it) {
        it.lhs.accept(this);

        it.regId = currentBlock.idSet.RegIdAlloca(5);
        IRLine line = new IRLine(lineType.MOVE);
        line.args.add(it.regId);
        line.args.add(it.lhs.regId);
        currentBlock.lines.add(line);

        line = new IRLine(lineType.ADDI);
        line.args.add(it.lhs.regId);
        line.args.add(it.lhs.regId);
        if (it.opcode.equals("++")) {
            line.args.add(new IRRegIdentifier(1, 8, false));
        } else {
            line.args.add(new IRRegIdentifier(-1, 8, false));
        }
        currentBlock.lines.add(line);
    }

    @Override
    public void visit(prefixExpr it) {
        it.rhs.accept(this);
        IRLine line;
        switch (it.opcode) {
            case "++":
                it.regId = it.rhs.regId;
                line = new IRLine(lineType.ADDI);
                line.args.add(it.regId);
                line.args.add(it.regId);
                line.args.add(new IRRegIdentifier(1, 8, false));
                currentBlock.lines.add(line);
                break;
            case"--":
                it.regId = it.rhs.regId;
                line = new IRLine(lineType.ADDI);
                line.args.add(it.regId);
                line.args.add(it.regId);
                line.args.add(new IRRegIdentifier(-1, 8, false));
                currentBlock.lines.add(line);
                break;
            case "+":
                it.regId = it.rhs.regId;
                break;
            case "-":
                it.regId = currentBlock.idSet.RegIdAlloca(5);
                line = new IRLine(lineType.NEG);
                line.args.add(it.regId);
                line.args.add(it.rhs.regId);
                currentBlock.lines.add(line);
                break;
            case "~":
                it.regId = currentBlock.idSet.RegIdAlloca(5);
                line = new IRLine(lineType.NOT);
                line.args.add(it.regId);
                line.args.add(it.rhs.regId);
                currentBlock.lines.add(line);
                break;
            case "!":
                it.regId = currentBlock.idSet.RegIdAlloca(5);
                line = new IRLine(lineType.LOGICNOT);
                line.args.add(it.regId);
                line.args.add(it.rhs.regId);
                currentBlock.lines.add(line);
                break;
        }
    }

    @Override
    public void visit(binaryExpr it) {
        IRLine line;
        if (it.opcode.equals("&&")) {
            it.regId = currentBlock.idSet.RegIdAlloca(5);
            int cond_fail = ++labelNum, cond_suc = ++labelNum;

            it.lhs.accept(this);
            line = new IRLine(lineType.BNEQ);
            line.label = cond_fail;
            line.args.add(it.lhs.regId);
            line.args.add(new IRRegIdentifier(0, 0, false));
            currentBlock.lines.add(line);

            it.rhs.accept(this);
            line = new IRLine(lineType.BNEQ);
            line.label = cond_fail;
            line.args.add(it.rhs.regId);
            line.args.add(new IRRegIdentifier(0, 0, false));
            currentBlock.lines.add(line);

            line = new IRLine(lineType.LOAD);
            line.args.add(it.regId);
            line.args.add(new IRRegIdentifier(1, 8, false));
            currentBlock.lines.add(line);

            line = new IRLine(lineType.JUMP);
            line.label = cond_suc;
            currentBlock.lines.add(line);

            line = new IRLine(lineType.LABEL);
            line.label = cond_fail;
            currentBlock.lines.add(line);

            line = new IRLine(lineType.LOAD);
            line.args.add(it.regId);
            line.args.add(new IRRegIdentifier(0, 8, false));
            currentBlock.lines.add(line);

            line = new IRLine(lineType.LABEL);
            line.label = cond_suc;
            currentBlock.lines.add(line);
        } else if (it.opcode.equals("||")) {
            it.regId = currentBlock.idSet.RegIdAlloca(5);
            int cond_suc = ++labelNum, cond_fail = ++labelNum;

            it.lhs.accept(this);
            line = new IRLine(lineType.BEQ);
            line.label = cond_suc;
            line.args.add(it.lhs.regId);
            line.args.add(new IRRegIdentifier(0, 0, false));
            currentBlock.lines.add(line);

            it.rhs.accept(this);
            line = new IRLine(lineType.BEQ);
            line.label = cond_suc;
            line.args.add(it.rhs.regId);
            line.args.add(new IRRegIdentifier(0, 0, false));
            currentBlock.lines.add(line);

            line = new IRLine(lineType.LOAD);
            line.args.add(it.regId);
            line.args.add(new IRRegIdentifier(0, 8, false));
            currentBlock.lines.add(line);

            line = new IRLine(lineType.JUMP);
            line.label = cond_fail;
            currentBlock.lines.add(line);

            line = new IRLine(lineType.LABEL);
            line.label = cond_suc;
            currentBlock.lines.add(line);

            line = new IRLine(lineType.LOAD);
            line.args.add(it.regId);
            line.args.add(new IRRegIdentifier(1, 8, false));
            currentBlock.lines.add(line);

            line = new IRLine(lineType.LABEL);
            line.label = cond_fail;//actually it reachs the end.
            currentBlock.lines.add(line);
        } else if (it.opcode.equals("=")) {
            it.rhs.accept(this);
            //Flag = true;
            it.lhs.accept(this);
            //Flag = false;
            //if (it.lhs.regId == null) System.out.println("fuck");
            line = new IRLine(lineType.MOVE);
            line.args.add(it.lhs.regId);
            line.args.add(it.rhs.regId);
            currentBlock.lines.add(line);
            it.regId = it.lhs.regId;
        } else {
            it.regId = currentBlock.idSet.RegIdAlloca(5);
            it.lhs.accept(this);
            it.rhs.accept(this);
            if (it.lhs.type.isString() && it.rhs.type.isString()) {
                line = new IRLine(lineType.MOVE);
                line.args.add(new IRRegIdentifier(1, 3, false));
                line.args.add(it.rhs.regId);
                currentBlock.lines.add(line);
                line = new IRLine(lineType.MOVE);
                line.args.add(new IRRegIdentifier(0, 3, false));
                line.args.add(it.lhs.regId);
                currentBlock.lines.add(line);
                switch (it.opcode) {
                    case "==":
                        line = new IRLine(lineType.CALL);
                        line.func = "my_string_eq";
                        break;
                    case "!=":
                        line = new IRLine(lineType.CALL);
                        line.func = "my_string_neq";
                        break;
                    case "<":
                        line = new IRLine(lineType.CALL);
                        line.func = "my_string_le";
                        break;
                    case ">":
                        line = new IRLine(lineType.CALL);
                        line.func = "my_string_ge";
                        break;
                    case "<=":
                        line = new IRLine(lineType.CALL);
                        line.func = "my_string_leq";
                        break;
                    case ">=":
                        line = new IRLine(lineType.CALL);
                        line.func = "my_string_geq";
                        break;
                    case "+":
                        line = new IRLine(lineType.CALL);
                        line.func = "my_string_plus";
                        break;
                }
                currentBlock.lines.add(line);

                line = new IRLine(lineType.MOVE);
                line.args.add(it.regId);
                line.args.add(new IRRegIdentifier(10, 0, false));
                currentBlock.lines.add(line);
            } else {
                switch (it.opcode) {
                    case "==":
                        line = new IRLine(lineType.EQ);
                        break;
                    case "!=":
                        line = new IRLine(lineType.NEQ);
                        break;
                    case "<":
                        line = new IRLine(lineType.LE);
                        break;
                    case ">":
                        line = new IRLine(lineType.GE);
                        break;
                    case "<=":
                        line = new IRLine(lineType.LEQ);
                        break;
                    case ">=":
                        line = new IRLine(lineType.GEQ);
                        break;
                    case "+":
                        line = new IRLine(lineType.ADD);
                        break;
                    case "-":
                        line = new IRLine(lineType.SUB);
                        break;
                    case "*":
                        line = new IRLine(lineType.MUL);
                        break;
                    case "/":
                        line = new IRLine(lineType.DIV);
                        break;
                    case "%":
                        line = new IRLine(lineType.MOD);
                        break;
                    case "&":
                        line = new IRLine(lineType.AND);
                        break;
                    case "^":
                        line = new IRLine(lineType.XOR);
                        break;
                    case "|":
                        line = new IRLine(lineType.OR);
                        break;
                    case "<<":
                        line = new IRLine(lineType.SHL);
                        break;
                    default:
                        line = new IRLine(lineType.SHR);
                        break;
                }
                line.args.add(it.regId);
                line.args.add(it.lhs.regId);
                line.args.add(it.rhs.regId);
                currentBlock.lines.add(line);
            }
        }
    }

    @Override
    public void visit(expressionListExpr it) {
    }

}