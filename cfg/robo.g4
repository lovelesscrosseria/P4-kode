grammar robo;

program : (strategy | function_decl | event_decl | assignment | variable_decl | list_decl | dictionary_decl | NEWLINE )* EOF;

strategy: 'strategy'  IDENT LCURL NEWLINE (behavior | NEWLINE)* RCURL;
behavior: 'behavior' IDENT LPAREN RPAREN LCURL (stat |  NEWLINE)* RCURL;

function_decl: 'func' type IDENT LPAREN formal_params? RPAREN block;
event_decl: 'event' IDENT block;

STRING: '"' ~["]* '"' ;
variable_decl: type IDENT (ASSIGN_OPERATOR expr)?;

list_decl: 'list' LESS_OPERATOR type GREATER_OPERATOR IDENT;
dictionary_decl: 'dictionary' LESS_OPERATOR type COMMA type GREATER_OPERATOR IDENT;
collection_expr: IDENT DOT ('get' LPAREN expr RPAREN | 'length');
collection_statement: IDENT DOT 'push' LPAREN (expr | expr COMMA expr) RPAREN;
roboCode_method: 'robot' DOT function_call;

assignment  : IDENT   (ASSIGN_OPERATOR 
            | PLUSEQ_OPERATOR 
            | MINUSEQ_OPERATOR) expr NEWLINE
            ;

stat        : block
            | variable_decl
            | list_decl 
            | collection_statement NEWLINE
            | 'if' expr (NEWLINE)? block ('else if' expr (NEWLINE)? block )* ('else' ( NEWLINE)? block )?
            | assignment 
            | function_call NEWLINE
            | return_stat NEWLINE
            | for_loop 
            | do_while_loop 
            | while_loop
            ;

function_call: IDENT LPAREN params RPAREN;
for_loop: 'for' LPAREN assignment ';' expr ';' expr RPAREN NEWLINE block;
while_loop: 'while' LPAREN expr RPAREN (NEWLINE)? block;
do_while_loop: 'do' ( NEWLINE )? block 'while' LPAREN expr RPAREN;
block: LCURL (stat | NEWLINE )* RCURL;

formal_params: type IDENT (',' formal_params)*;
params: expr (',' params)*;

expr        : decrement_operator
            | increment_operator
            | LPAREN expr RPAREN
            | NOT_OPERATOR expr
            | <assoc=right> expr '^' expr
            | <assoc=left> expr (TIMES_OPERATOR | DIVISION_OPERATOR | MODULO_OPERATOR) expr
            | <assoc=left> expr (PLUS_OPERATOR | MINUS_OPERATOR) expr
            | expr (GEQ_OPERATOR | LEQ_OPEATOR | LESS_OPERATOR | GREATER_OPERATOR) expr
            | expr (NOTEQ_OPERATOR | EQUAL_OPERATOR) expr
            | expr AND_OPERATOR expr
            | expr OR_OPERATOR expr
            | 'true'
            | 'false'
            | STRING
            | DIGIT+
            | IDENT
            | function_call
            | collection_expr
            ;

DOT: '.';
COMMA: ',';
LPAREN: '(';
RPAREN: ')';
LCURL: '{';
RCURL: '}';
NEWLINE: '\n' | '\r' | '\r\n';
WS: [ \n\t\r]+ -> skip;

ASSIGN_OPERATOR: '=';
NOT_OPERATOR: '!';
TIMES_OPERATOR: '*';
DIVISION_OPERATOR: '/';
MODULO_OPERATOR: '%';
PLUS_OPERATOR: '+';
MINUS_OPERATOR: '-';
LEQ_OPEATOR: '<=';
GEQ_OPERATOR: '>=';
LESS_OPERATOR: '<';
GREATER_OPERATOR: '>';
NOTEQ_OPERATOR: '!=';
EQUAL_OPERATOR: '==';
AND_OPERATOR: '&&';
OR_OPERATOR: '||';
PLUSEQ_OPERATOR: '+=';
MINUSEQ_OPERATOR: '-=';


IDENT: SINGLE_CHARACTER+ (DIGIT | SINGLE_CHARACTER)*;
SINGLE_CHARACTER: ([a-zA-Z] | '_');
DIGIT: [0-9]+ ('.')? [0-9]*;
type: 'num' | 'text' | 'bool' | 'void' | 'ScannedRobotEvent';
return_stat: 'return'  expr;
decrement_operator: IDENT '--';
increment_operator: IDENT '++';
