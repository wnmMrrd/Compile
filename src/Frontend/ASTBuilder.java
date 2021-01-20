package Frontend;

import AST.*;
import Parser.MxBaseVisitor;
import Parser.MxParser;
import Util.error.syntaxError;
import Util.position;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.ArrayList;

public class ASTBuilder extends MxBaseVisitor<ASTNode> {

    @Override public ASTNode visitProgram(MxParser.ProgramContext ctx) {
        ProgramNode it = new ProgramNode(new position(ctx));
        if (ctx.programpart() != null) {
            for (ParserRuleContext x:ctx.programpart()) {
                ASTNode y = visit(x);
                if (y instanceof varDefStmt) {
                    it.subList.addAll(((varDefStmt)y).varList);
                } else {
                    it.subList.add(y);
                }
            }
        }
        return it;
    }

    @Override public ASTNode visitProgrampart(MxParser.ProgrampartContext ctx) {
        if (ctx.varDef() != null) return visit(ctx.varDef());
        else if (ctx.funcDef() != null) return visit(ctx.funcDef());
        else return visit(ctx.classDef());
    }

    @Override public ASTNode visitSingleUnit(MxParser.SingleUnitContext ctx) {
        return new varDefSubStmt(ctx.Identifier().getText(), ctx.expression() == null ? null : (ExprNode)visit(ctx.expression()), new position(ctx));
    }

    @Override public ASTNode visitVarDef(MxParser.VarDefContext ctx) {
        varDefStmt it = new varDefStmt(new position(ctx));
        TypeNode type = (TypeNode)visit(ctx.type());
        for (ParserRuleContext x:ctx.singleUnit()) {
            varDefSubStmt y = (varDefSubStmt)visit(x);
            y.type = type;
            it.varList.add(y);
        }
        return it;
    }

    @Override public ASTNode visitParameter(MxParser.ParameterContext ctx) {
        return new varDefSubStmt((TypeNode)visit(ctx.type()), ctx.Identifier().getText(), new position(ctx));
    }

    @Override public ASTNode visitParameterList(MxParser.ParameterListContext ctx) {
        varDefStmt it = new varDefStmt(new position(ctx));
        for (ParserRuleContext x:ctx.parameter()) {
            it.varList.add((varDefSubStmt)visit(x));
        }
        return it;
    }

    @Override public ASTNode visitFuncDef(MxParser.FuncDefContext ctx) {
        return new funcDef(ctx.returntype() == null ? null : (TypeNode)visit(ctx.returntype()), ctx.Identifier().getText(), (blockStmt)visit(ctx.suite()), ctx.parameterList() == null ? new ArrayList<>() : ((varDefStmt)visit(ctx.parameterList())).varList, new position(ctx));
    }

    @Override public ASTNode visitClassDef(MxParser.ClassDefContext ctx) {
        classDef it = new classDef(ctx.Identifier().getText(), new position(ctx));
        if (ctx.varDef() != null) {
            for (ParserRuleContext x : ctx.varDef()) {
                it.varList.addAll(((varDefStmt)visit(x)).varList);
            }
        }
        if (ctx.funcDef() != null) {
            for (ParserRuleContext x : ctx.funcDef()) {
                funcDef y = (funcDef)visit(x);
                if (y.returntype == null) it.constructor = y;
                else it.funcList.add(y);
            }
        }
        return it;
    }

    @Override public ASTNode visitBasictype(MxParser.BasictypeContext ctx) {
        return new TypeNode(ctx.getText(), 0, new position(ctx));
    }

    @Override public ASTNode visitType(MxParser.TypeContext ctx) {
        return new TypeNode(ctx.basictype().getText(), (ctx.getChildCount()-1)/2, new position(ctx));
    }

    @Override public ASTNode visitReturntype(MxParser.ReturntypeContext ctx) {
        return ctx.type() == null ? new TypeNode(ctx.Void().getText(), 0, new position(ctx)) : visit(ctx.type());
    }

    @Override public ASTNode visitSuite(MxParser.SuiteContext ctx) {
        blockStmt it = new blockStmt(new position(ctx));
        if (ctx.statement() != null) {
            for (ParserRuleContext x : ctx.statement()) {
                it.stmtList.add((StmtNode)visit(x));
            }
        }
        return it;
    }

    @Override public ASTNode visitBlockStmt(MxParser.BlockStmtContext ctx) {
        return visit(ctx.suite());
    }

    @Override public ASTNode visitVardefStmt(MxParser.VardefStmtContext ctx) {
        return visit(ctx.varDef());
    }

    @Override public ASTNode visitIfStmt(MxParser.IfStmtContext ctx) {
        return new ifStmt((ExprNode)visit(ctx.expression()), (StmtNode)visit(ctx.trueStmt), ctx.falseStmt == null ? null : (StmtNode)visit(ctx.falseStmt), new position(ctx));
    }

    @Override public ASTNode visitForStmt(MxParser.ForStmtContext ctx) {
        return new forStmt(ctx.init == null ? null :(ExprNode)visit(ctx.init), ctx.cond == null ? null : (ExprNode)visit(ctx.cond), ctx.incr == null ? null : (ExprNode)visit(ctx.incr), (StmtNode)visit(ctx.statement()), new position(ctx));
    }

    @Override public ASTNode visitWhileStmt(MxParser.WhileStmtContext ctx) {
        return new whileStmt((ExprNode)visit(ctx.expression()), (StmtNode)visit(ctx.statement()), new position(ctx));
    }

    @Override public ASTNode visitBreakStmt(MxParser.BreakStmtContext ctx) {
        return new breakStmt(new position(ctx));
    }

    @Override public ASTNode visitContinueStmt(MxParser.ContinueStmtContext ctx) {
        return new continueStmt(new position(ctx));
    }

    @Override public ASTNode visitReturnStmt(MxParser.ReturnStmtContext ctx) {
        return new returnStmt(ctx.expression() == null ? null : (ExprNode)visit(ctx.expression()), new position(ctx));
    }

    @Override public ASTNode visitPureExprStmt(MxParser.PureExprStmtContext ctx) {
        return new pureExprStmt((ExprNode)visit(ctx.expression()), new position(ctx));
    }

    @Override public ASTNode visitEmptyStmt(MxParser.EmptyStmtContext ctx) {
        return new emptyStmt(new position(ctx));
    }

    @Override public ASTNode visitExpressionList(MxParser.ExpressionListContext ctx) {
        expressionListExpr it = new expressionListExpr(new position(ctx));
        for (ParserRuleContext x : ctx.expression()) {
            it.expressionList.add((ExprNode)visit(x));
        }
        return it;
    }

    @Override public ASTNode visitNewExpr(MxParser.NewExprContext ctx) {
        return visit(ctx.creator());
    }

    @Override public ASTNode visitPrefixExpr(MxParser.PrefixExprContext ctx) {
        return new prefixExpr((ExprNode)visit(ctx.expression()), ctx.op.getText(), new position(ctx));
    }

    @Override public ASTNode visitSubscriptExpr(MxParser.SubscriptExprContext ctx) {
        return new subscriptExpr((ExprNode)visit(ctx.name), (ExprNode)visit(ctx.index), new position(ctx));
    }

    @Override public ASTNode visitFunccallExpr(MxParser.FunccallExprContext ctx) {
        ExprNode firstExpr = (ExprNode)visit(ctx.expression());
        if (firstExpr instanceof memberExpr) {
            firstExpr.assignable = false;
            ((memberExpr)firstExpr).isfunc = true;
        }
        return new funcCallExpr(firstExpr, ctx.expressionList() == null ? new expressionListExpr(new position(ctx)) : (expressionListExpr)visit(ctx.expressionList()), new position(ctx));
    }

    @Override public ASTNode visitMemberExpr(MxParser.MemberExprContext ctx) {
        return new memberExpr((ExprNode)visit(ctx.expression()), ctx.Identifier().getText(), new position(ctx));
    }

    @Override public ASTNode visitSuffixExpr(MxParser.SuffixExprContext ctx) {
        return new suffixExpr((ExprNode)visit(ctx.expression()), ctx.op.getText(), new position(ctx));
    }

    @Override public ASTNode visitAtomExpr(MxParser.AtomExprContext ctx) {
        return visit(ctx.primary());
    }

    @Override public ASTNode visitBinaryExpr(MxParser.BinaryExprContext ctx) {
        return new binaryExpr((ExprNode)visit(ctx.src1), (ExprNode)visit(ctx.src2), ctx.op.getText(), new position(ctx));
    }

    @Override public ASTNode visitPrimary(MxParser.PrimaryContext ctx) {
        if (ctx.This() != null) return new thisExpr(new position(ctx));
        else if (ctx.expression() != null) return visit(ctx.expression());
        else if (ctx.literal() != null) return visit(ctx.literal());
        else return new varExpr(ctx.Identifier().getText(), new position(ctx));
    }

    @Override public ASTNode visitErrorCreator(MxParser.ErrorCreatorContext ctx) {
        throw new syntaxError("ErrorCreator", new position(ctx));
    }

    @Override public ASTNode visitArrayCreator(MxParser.ArrayCreatorContext ctx) {
        ArrayList<ExprNode>sizeList = new ArrayList<>();
        for (ParserRuleContext x : ctx.expression()) {
            sizeList.add((ExprNode) visit(x));
        }
        return new newExpr((TypeNode)visit(ctx.basictype()), (ctx.getChildCount()-ctx.expression().size()-1)/2, sizeList, new position(ctx));
    }

    @Override public ASTNode visitClassCreator(MxParser.ClassCreatorContext ctx) {
        return new newExpr((TypeNode)visit(ctx.basictype()), 0, null, new position(ctx));
    }

    @Override public ASTNode visitBasicCreator(MxParser.BasicCreatorContext ctx) {
        return new newExpr((TypeNode)visit(ctx.basictype()), 0, null, new position(ctx));
    }

    @Override public ASTNode visitLiteral(MxParser.LiteralContext ctx) {
        if (ctx.DecimalInteger() != null) return new DecimalIntegerExpr(Integer.parseInt(ctx.DecimalInteger().getText()), new position(ctx));
        else if (ctx.BoolValue() != null) return new BoolValueExpr(Boolean.parseBoolean(ctx.BoolValue().getText()), new position(ctx));
        else if (ctx.StringLiteral() != null) return new StringLiteralExpr(ctx.StringLiteral().getText(), new position(ctx));
        else return new NullExpr(new position(ctx));
    }

}
