package AST;

public interface ASTVisitor {
    void visit(ProgramNode it);

    void visit(TypeNode it);

    //Def
    void visit(classDef it);
    void visit(funcDef it);

    //Stmt
    void visit(blockStmt it);
    void visit(varDefSubStmt it);
    void visit(varDefStmt it);
    void visit(ifStmt it);
    void visit(forStmt it);
    void visit(whileStmt it);
    void visit(breakStmt it);
    void visit(continueStmt it);
    void visit(returnStmt it);
    void visit(pureExprStmt it);
    void visit(emptyStmt it);

    //Expr
    void visit(varExpr it);
    void visit(thisExpr it);
    void visit(DecimalIntegerExpr it);
    void visit(BoolValueExpr it);
    void visit(StringLiteralExpr it);
    void visit(NullExpr it);
    void visit(memberExpr it);
    void visit(newExpr it);
    void visit(subscriptExpr it);
    void visit(funcCallExpr it);
    void visit(suffixExpr it);
    void visit(prefixExpr it);
    void visit(binaryExpr it);
    void visit(expressionListExpr it);
}
