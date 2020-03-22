grammar robo;

program     :(strategy 
            | function_decl 
            | event_decl 
            | assignment 
            | variable_decl 
            | WS 
            | '\n' 
            | '\r')* strategy (strategy 
                | function_decl 
                | event_decl 
                | assignment 
                | variable_decl 
                | WS 
                | '\n' 
                | '\r')* EOF
            ;

strategy: 'strategy'  WS IDENT WS+ LCURL  behavior* RCURL;
behavior: 'behavior' WS IDENT LPAREN RPAREN WS+ LCURL stat* RCURL;

function_decl: 'func' WS type WS IDENT WS? LPAREN WS* formal_params? WS* RPAREN WS* block;
event_decl: 'event' WS IDENT WS block;

// if_decl: 'if' WS* if_wrapper (WS | '\n')* (('else if' WS* if_wrapper)* (WS | '\n')* ((WS | '\n')* 'else' (WS | '\n')* block)?);
// if_wrapper: LPAREN WS* expr* WS* RPAREN WS* '\n'? WS* block;

STRING_DECL: '"'[^"]'"';
variable_decl: type WS IDENT (WS ASSIGN_OPERATOR WS? expr)? SEMI;
assignment  : IDENT WS (ASSIGN_OPERATOR 
            | PLUSEQ_OPERATOR 
            | MINUSEQ_OPERATOR) WS expr SEMI
            ;

stat        : block
            | variable_decl 
            | 'if' expr 'then' stat ('else' stat)?
            | assignment 
            | function_call 
            | return_stat 
            | for_loop 
            | do_while_loop 
            | while_loop
            ;

function_call: IDENT LPAREN params RPAREN SEMI;
for_loop: 'for' LPAREN assignment ';' WS expr ';' WS expr RPAREN '\n'block;
while_loop: 'while' LPAREN expr RPAREN ('\n' | WS)? block;
do_while_loop: 'do' ( '\n' | WS)? block WS 'while' LPAREN expr RPAREN;
block: LCURL (stat | '\n' | WS)* RCURL;

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
            | STRING_DECL
            | DIGIT_DOT+
            | DIGIT+
            | IDENT
            | function_call
            ;

LPAREN: '(';
RPAREN: ')';
LCURL: '{';
RCURL: '}';
SEMI: '\n' | '\r' | '\r\n';
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
DIGIT: [0-9];
DIGIT_DOT: ('0'? | [1-9]*)'.'[0-9]*;
type: 'num' | 'text' | 'bool' | 'void';
return_stat: 'return'  WS expr SEMI;
decrement_operator: IDENT '--';
increment_operator: IDENT '++';
