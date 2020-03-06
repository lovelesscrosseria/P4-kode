grammar robo;

program: (strategy | function_decl | event_decl | assignment | variable_decl | WHITESPACE | '\n' | '\r')* strategy (strategy | function_decl | event_decl | assignment | variable_decl | WHITESPACE | '\n' | '\r')* EOF;
strategy: 'strategy'  WHITESPACE IDENT WHITESPACE+ LCURL  behavior* RCURL;
behavior: 'behavior' WHITESPACE IDENT LPAREN RPAREN WHITESPACE+ LCURL statement* RCURL;

function_decl: 'func' WHITESPACE type WHITESPACE IDENT WHITESPACE? LPAREN WHITESPACE* formal_params? WHITESPACE* RPAREN WHITESPACE* LCURL block RCURL;
event_decl: 'event' WHITESPACE IDENT WHITESPACE LCURL block RCURL;

if_decl: 'if' WHITESPACE* if_wrapper (WHITESPACE | '\n')* (('else if' WHITESPACE* if_wrapper)* (WHITESPACE | '\n')* ((WHITESPACE | '\n')* 'else' (WHITESPACE | '\n')* LCURL block RCURL)?);
if_wrapper: LPAREN WHITESPACE* expr* WHITESPACE* RPAREN WHITESPACE* '\n'? WHITESPACE* LCURL block RCURL;

STRING_DECL: '"'[^"]'"';
variable_decl: type WHITESPACE IDENT (WHITESPACE ASSIGN_OPERATOR WHITESPACE? expr)? SEMI;
assignment:  IDENT WHITESPACE (ASSIGN_OPERATOR | PLUSEQ_OPERATOR | MINUSEQ_OPERATOR) WHITESPACE expr SEMI;
statement: variable_decl | assignment | function_call | if_decl | return_statement | for_loop | do_while_loop | while_loop;

function_call: IDENT LPAREN params RPAREN SEMI;
for_loop: 'for' LPAREN assignment ';' WHITESPACE expr ';' WHITESPACE expr RPAREN '\n' LCURL block RCURL;
while_loop: 'while' LPAREN expr RPAREN ('\n' | WHITESPACE)? LCURL block RCURL;
do_while_loop: 'do' ( '\n' | WHITESPACE)? LCURL block RCURL WHITESPACE 'while' LPAREN expr RPAREN;
block: (statement | '\n' | WHITESPACE)*;

formal_params: type WHITESPACE IDENT WHITESPACE? (',' WHITESPACE? formal_params)*;
params: expr WHITESPACE? (',' WHITESPACE? params)*;

expr        : decrement_operator
            | increment_operator
            | LPAREN WHITESPACE* expr WHITESPACE* RPAREN
            | NOT_OPERATOR WHITESPACE? expr
            | <assoc=right> expr '^' expr
            | <assoc=left> expr WHITESPACE? (TIMES_OPERATOR | DIVISION_OPERATOR | MODULO_OPERATOR) WHITESPACE? expr
            | <assoc=left> expr WHITESPACE? (PLUS_OPERATOR | MINUS_OPERATOR) WHITESPACE? expr
            | expr WHITESPACE? (GEQ_OPERATOR | LEQ_OPEATOR | LESS_OPERATOR | GREATER_OPERATOR) WHITESPACE? expr
            | expr WHITESPACE? (NOTEQ_OPERATOR | EQUAL_OPERATOR) WHITESPACE? expr
            | expr WHITESPACE? AND_OPERATOR WHITESPACE? expr
            | expr WHITESPACE? OR_OPERATOR WHITESPACE? expr
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
WHITESPACE: ' ' | '\t';


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
return_statement: 'return'  WHITESPACE expr SEMI;
decrement_operator: IDENT '--';
increment_operator: IDENT '++';