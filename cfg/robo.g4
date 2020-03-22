grammar robo;

program : (strategy | function_decl | event_decl | assignment | variable_decl | list_decl | WS | NEWLINE)* EOF;

strategy: 'strategy'  WS IDENT WS+ LCURL NEWLINE (behavior | WS | NEWLINE)* RCURL;
behavior: 'behavior' WS IDENT LPAREN RPAREN WS+ LCURL (stat | WS | NEWLINE)* RCURL;

function_decl: 'func' WS type WS IDENT WS? LPAREN WS* formal_params? WS* RPAREN WS* block;
event_decl: 'event' WS IDENT WS block;

STRING: '"' ~["]* '"' ;
variable_decl: type WS IDENT (WS ASSIGN_OPERATOR WS? expr)?;
list_decl: 'list' LESS_OPERATOR type GREATER_OPERATOR WS IDENT WS ASSIGN_OPERATOR WS LCURL (expr (',' WS? expr)*)? RCURL;
assignment  : IDENT WS (ASSIGN_OPERATOR 
            | PLUSEQ_OPERATOR 
            | MINUSEQ_OPERATOR) WS expr NEWLINE
            ;

stat        : block
            | variable_decl NEWLINE
            | list_decl NEWLINE
            | 'if' WS? expr (WS | NEWLINE)? block (WS 'else if' WS? expr (WS | NEWLINE)? block )* (WS 'else' (WS | NEWLINE)? block )?
            | assignment 
            | function_call NEWLINE
            | return_stat NEWLINE
            | for_loop 
            | do_while_loop 
            | while_loop
            ;

function_call: IDENT LPAREN params RPAREN;
for_loop: 'for' LPAREN assignment ';' WS expr ';' WS expr RPAREN NEWLINE block;
while_loop: 'while' LPAREN expr RPAREN (NEWLINE | WS)? block;
do_while_loop: 'do' ( NEWLINE | WS)? block WS 'while' LPAREN expr RPAREN;
block: LCURL (stat | NEWLINE | WS)* RCURL;

formal_params: type WS IDENT WS? (',' WS? formal_params)*;
params: expr WS? (',' WS? params)*;

expr        : decrement_operator
            | increment_operator
            | LPAREN WS* expr WS* RPAREN
            | NOT_OPERATOR WS? expr
            | <assoc=right> expr '^' expr
            | <assoc=left> expr WS? (TIMES_OPERATOR | DIVISION_OPERATOR | MODULO_OPERATOR) WS? expr
            | <assoc=left> expr WS? (PLUS_OPERATOR | MINUS_OPERATOR) WS? expr
            | expr WS? (GEQ_OPERATOR | LEQ_OPEATOR | LESS_OPERATOR | GREATER_OPERATOR) WS? expr
            | expr WS? (NOTEQ_OPERATOR | EQUAL_OPERATOR) WS? expr
            | expr WS? AND_OPERATOR WS? expr
            | expr WS? OR_OPERATOR WS? expr
            | 'true'
            | 'false'
            | STRING
            | DIGIT+
            | IDENT
            | function_call
            ;


LPAREN: '(';
RPAREN: ')';
LCURL: '{';
RCURL: '}';
NEWLINE: '\n' | '\r' | '\r\n';
WS: ' ' | '\t';


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
return_stat: 'return'  WS expr;
decrement_operator: IDENT '--';
increment_operator: IDENT '++';
