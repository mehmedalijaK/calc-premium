grammar CalcPremium;

start: topLevel* EOF;

topLevel
    : defineFunction ;

defineFunction: FUNCTION IDENTIFIER '(' argumentList ')' ':' returnType = typeId (body = statementList) ;
argumentList: (argument (',' argument)*)? ;
argument: typeId IDENTIFIER ;

statementList: '{' declaringStmt* '}' ;
declaringStmt: statement | declareStatement ';' ;

statement
    : ifStatement
    | whileStatement
    | forStatement
    | statementList
    | assignment ';'
    | returnStatement ';'
    | loopControlStatement ';'
    | nullStatement
    ;

declareStatement: typeId IDENTIFIER assignOperators value = expression ;
returnStatement: RETURN expression? ;
loopControlStatement: BREAK | CONTINUE ;
nullStatement: ';' ;
ifStatement: IF '(' expression ')' then = statement (ELSE otherwise = statement)? ;
whileStatement: WHILE '(' expression ')' body = statement ;
forStatement: FOR '(' declareStatement ';' expression ';' expression ')' body = statement;

assignment: expression (assignOperators expression)? ;

expression: orExpression ;
orExpression: andExpression (op+=LOGICAL_OR rest+=andExpression)* ;
andExpression: compareExpression (op+=LOGICAL_AND rest+=compareExpression)* ;
compareExpression: relationalExpression (op+=(EQ | NEQ) rest+=relationalExpression)* ;
relationalExpression: additionExpression (op+=(LT | LTE | GT | GTE) rest+=additionExpression)* ;
additionExpression: multiplicationExpression (op+=(PLUS | MINUS) rest+=multiplicationExpression)* ;
multiplicationExpression: unaryExpression (op+=(STAR | DIVIDE | MODUO) rest+=unaryExpression)* ;
unaryExpression: (MINUS | LOGICAL_NOT)? unarySuffix (INC | DEC)? ;
unarySuffix: term unarySuffixOperations*;

unarySuffixOperations
    : '(' args=expressionList ')' #Funcall
    | '[' index=expression ']' #ArrIdx
    | '.len' #ArrayLen
    | '.new' #ArrayPush
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
    | CHAR_TYPE
    | STRING_TYPE
    | arrayType
    ;

literal
    : NUMBER
    | TRUE
    | FALSE
    | CHAR
    | STRING
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
STRING_TYPE: 'string';
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
STRING: '"' STRELEM* '"';
CHAR: '\'' STRELEM '\'';


// Helper fragments
fragment STRELEM: (~[\\'"\n] | '\\' ~[\n]);
fragment WHOLE: [1-9] [0-9]*;

// Skipp following
SPACES: [ \u000B\t\r\n\p{White_Space}] -> skip;
COMMENT: '/*' .*? '*/' -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;