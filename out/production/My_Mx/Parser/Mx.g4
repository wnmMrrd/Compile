grammar Mx;

program: programpart* EOF;

programpart: varDef | funcDef | classDef;

singleUnit : Identifier ('=' expression)?;
varDef : type singleUnit (',' singleUnit)* ';';

parameter : type Identifier;
parameterList : parameter (',' parameter)*;
funcDef : returntype? Identifier '(' parameterList? ')' suite;

classDef : Class Identifier '{' (varDef | funcDef)* '}' ';';

basictype : Bool | Int | String | Identifier;
type : basictype ('[' ']')*;
returntype : type | Void;

suite : '{' statement* '}';

statement
    : suite                                                              #blockStmt
    | varDef                                                             #vardefStmt
    | If '(' expression ')' trueStmt=statement
        (Else falseStmt=statement)?                                      #ifStmt
    | For '(' init=expression? ';' cond=expression? ';'
                    incr=expression? ')' statement                       #forStmt
    | While '(' expression ')' statement                                 #whileStmt
    | Break ';'                                                          #breakStmt
    | Continue ';'                                                       #continueStmt
    | Return expression? ';'                                             #returnStmt
    | expression ';'                                                     #pureExprStmt
    | ';'                                                                #emptyStmt
    ;

expressionList : expression (',' expression)*;

expression
    : primary                                                            #atomExpr
    | expression '.' Identifier                                          #memberExpr
    | <assoc=right> New creator                                          #newExpr
    | name=expression '[' index=expression ']'                           #subscriptExpr
    | expression '(' expressionList? ')'                                 #funccallExpr
    | expression op=('++' | '--')                                        #suffixExpr
    | <assoc=right> op=('++' | '--') expression                          #prefixExpr
    | <assoc=right> op=('+' | '-') expression                            #prefixExpr
    | <assoc=right> op=('!' | '~') expression                            #prefixExpr
    | src1=expression op=('*' | '/' | '%') src2=expression               #binaryExpr
    | src1=expression op=('+' | '-') src2=expression                     #binaryExpr
    | src1=expression op=('<<' | '>>') src2=expression                   #binaryExpr
    | src1=expression op=('<' | '>' | '>=' | '<=') src2=expression       #binaryExpr
    | src1=expression op=('==' | '!=' ) src2=expression                  #binaryExpr
    | src1=expression op='&' src2=expression                             #binaryExpr
    | src1=expression op='^' src2=expression                             #binaryExpr
    | src1=expression op='|' src2=expression                             #binaryExpr
    | src1=expression op='&&' src2=expression                            #binaryExpr
    | src1=expression op='||' src2=expression                            #binaryExpr
    | <assoc=right> src1=expression op='=' src2=expression               #binaryExpr
    ;

primary
    : This
    | '(' expression ')'
    | literal
    | Identifier
    ;

creator
    : basictype ('[' expression ']')+ ('[' ']')+ ('[' expression ']')+   #errorCreator
    | basictype ('[' expression ']')+ ('[' ']')*                         #arrayCreator
    | basictype '(' ')'                                                  #classCreator
    | basictype                                                          #basicCreator
    ;

literal
    : DecimalInteger
    | BoolValue
    | StringLiteral
    | Null
    ;

BoolValue : True | False;

Bool : 'bool';
Int : 'int';
Void : 'void';
String : 'string';
Null: 'null';
If : 'if';
Else : 'else';
Continue : 'continue';
Break : 'break';
For : 'for';
Switch : 'switch';
While : 'while';
Return : 'return';
Struct: 'struct';
This : 'this';
True : 'true';
False : 'false';
New : 'new';
Class : 'class';

LeftParen : '(';
RightParen : ')';
LeftBracket : '[';
RightBracket : ']';
LeftBrace : '{';
RightBrace : '}';

Less : '<';
LessEqual : '<=';
Greater : '>';
GreaterEqual : '>=';
LeftShift : '<<';
RightShift : '>>';

Plus : '+';
PlusPlus : '++';
Minus : '-';
MinusMinus : '--';
Mul : '*';
Div : '/';
Mod : '%';

And : '&';
Or : '|';
AndAnd : '&&';
OrOr : '||';
Caret : '^';
Not : '!';
Tilde : '~';

Dot : '.';
Question : '?';
Colon : ':';
Semi : ';';
Comma : ',';

Assign : '=';
Equal : '==';
NotEqual : '!=';

StringLiteral : '"' (~["\\\n\r] | '\\' ["\\nr])* '"';

Identifier
    : [a-zA-Z] [a-zA-Z_0-9]*
    ;

DecimalInteger
    : [1-9] [0-9]*
    | '0'
    ;

Whitespace
    :   [ \t]+
        -> skip
    ;

Newline
    :   (   '\r' '\n'?
        |   '\n'
        )
        -> skip
    ;

BlockComment
    :   '/*' .*? '*/'
        -> skip
    ;

LineComment
    :   '//' ~[\r\n]*
        -> skip
    ;