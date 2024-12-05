grammar CalcPremium;

start: statement* EOF;

statement
    : ifStatement
    | whileStatement
    | statementList
    | assignment ';'
    ;

statementList: '{' declaringStmt* '}';

declaringStmt
    : statement #AAA
    | declareStatement ';' #BA
    ;

declareStatement: typeId IDENTIFIER assignOperators value = expression;
ifStatement: IF '(' expression ')' then = statement (ELSE otherwise = statement)? ;
whileStatement: WHILE '(' expression ')' body = statement ;

assignment: expression (ASSIGN expression);

expression: orExpression ;
orExpression: andExpression (LOGICAL_OR andExpression)* ;
andExpression: compareExpression (LOGICAL_AND compareExpression)* ;
compareExpression: relationalExpression ((EQ | NEQ) relationalExpression)* ;
relationalExpression: additionExpression ((LT | LTE | GT | GTE) additionExpression)* ;
additionExpression: multiplicationExpression ((PLUS | MINUS) multiplicationExpression)* ;
multiplicationExpression: unaryExpression ((STAR | DIVIDE | MODUO) unaryExpression)* ;
unaryExpression: (MINUS | LOGICAL_NOT)? unarySuffix (INC | DEC)? ;
unarySuffix: term unarySuffixOperations*;

unarySuffixOperations
    : '(' args=expressionList ')' #Funcall
    | '[' index=expression ']' #ArrIdx
    | '?len' #ArrayLen
    | '?new' #ArrayPush
    ;

term
    : literal
    | varRef
    | arrayCollect
    | '(' expression ')'
    ;

varRef: IDENTIFIER;
expressionList: (expression (',' expression)*)?;

arrayType
    : ARR '[' typeId ']'
    ;

arrayCollect
    : NEW typeId '{' collectedElems=expressionList '}'
    ;

typeId:
    | INT_TYPE
    | BOOL_TYPE
    | VOID_TYPE
    | IDENTIFIER
    | arrayType
    ;

literal
    : NUMBER
    | TRUE
    | FALSE
    ;

assignOperators
    : ASSIGN
    | DIVIDE_ASSIGN
    | MINUS_ASSIGN
    | PLUS_ASSIGN
    | STAR_ASSIGN
    | MODUO_ASSIGN
    ;

// Lexical Grammar

// LOGICAL OPERATOR
LOGICAL_NOT: '!';
LOGICAL_OR: '||';
LOGICAL_AND: '&&';

// Arithmetic Operators
PLUS: '+';
MINUS: '-';
STAR: '*';
DIVIDE: '/';
MODUO: '%';

// Rational operators
GT: '>';
GTE: '>=';
LT: '<';
LTE: '<=';
EQ: '==';
NEQ: '!=';

// Unary operators
INC: '++';
DEC: '--';

// Assignment operators
ASSIGN: '=';
PLUS_ASSIGN: '+=';
MINUS_ASSIGN: '-=';
STAR_ASSIGN: '*=';
DIVIDE_ASSIGN: '/=';
MODUO_ASSIGN: '%=';

// Numbers
NUMBER: '-'? (WHOLE | '0');

// Types
BOOL_TYPE: 'bool';
INT_TYPE: 'int';
CHAR_TYPE: 'char';
VOID_TYPE: 'void';
ARR: 'arr';

FOR: 'for';
WHILE: 'while';
BREAK: 'break';
CONTINUE: 'continue';

FUNCTION: 'fun';
RETURN: 'return';
NEW: 'new';

IF: 'if';
ELSE: 'else';
TRUE: 'true';
FALSE: 'false';

IDENTIFIER: [a-zA-Z_][a-zA-Z0-9_]*;

// Helper fragments
fragment WHOLE: [1-9] [0-9]*;

// Skipp following
SPACES: [ \u000B\t\r\n\p{White_Space}] -> skip;
COMMENT: '/*' .*? '*/' -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;