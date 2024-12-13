grammar CalcPremium;

options { visitor = true; }

start: topLevel* EOF; //DONE

topLevel
    : defineFunction ; //DONE

defineFunction: FUNCTION IDENTIFIER '(' argumentList ')' ':' returnType = typeId (body = statementList) ; //DONE
argumentList: (argument (',' argument)*)? ; //DONE
argument: typeId IDENTIFIER ; //DONE

statementList: '{' statement* '}' ; //DONE

statement //DONE
    : ifStatement
    | whileStatement
    | forStatement
    | statementList
    | assignment ';'
    | returnStatement ';'
    | loopControlStatement ';'
    | nullStatement
    | declareStatement ';'
    ;

declareStatement: typeId IDENTIFIER '=' value = expression ; //DONE
returnStatement: RETURN expression? ; //DONE
loopControlStatement: BREAK | CONTINUE ; //DONE
nullStatement: ';' ; //DONE
ifStatement: IF '(' expression ')' then = statement (ELSE otherwise = statement)? ; //DONE
whileStatement: WHILE '(' expression ')' body = statement ; //DONE
forStatement: FOR '(' declareStatement ';' expression ';' expression ')' body = statement; //DONE

assignment: expression ('=' expression)? ; //DONE

expression: orExpression ; //DONE
orExpression: initial=andExpression (op+=LOGICAL_OR rest+=andExpression)* ;
andExpression: initial=compareExpression (op+=LOGICAL_AND rest+=compareExpression)* ;
compareExpression: initial=relationalExpression (op+=(EQ | NEQ) rest+=relationalExpression)* ;
relationalExpression: initial=additionExpression (op+=(LT | LTE | GT | GTE) rest+=additionExpression)* ;
additionExpression: initial=multiplicationExpression (op+=(PLUS | MINUS) rest+=multiplicationExpression)* ;
multiplicationExpression: initial=unaryExpression (op+=(STAR | DIVIDE | MODUO) rest+=unaryExpression)* ;
unaryExpression: lhs=unarySuffixOperations (op=(LOGICAL_NOT | MINUS) rhs=unaryExpression)?;

unarySuffixOperations
    : term ('(' args=expressionList ')')* #Funcall
    | term ('[' index=expression ']')* #ArrIdx
    | term ('.len')* #ArrayLen
    | term ('.new')* #ArrayPush
//    | term '++' #PostInc
//    | term '--' #PostDec
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
//PLUS_ASSIGN: '+=';
//MINUS_ASSIGN: '-=';
//STAR_ASSIGN: '*=';
//DIVIDE_ASSIGN: '/=';
//MODUO_ASSIGN: '%=';

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