grammar robo;

program : (COMMENT | strategy | function_decl | event_decl | assignment | variable_decl | list_decl | dictionary_decl | NEWLINE )* EOF;

strategy: 'strategy'  ID LCURL NEWLINE (behavior | NEWLINE)* RCURL;
behavior: 'behavior' ID LPAREN formal_params? RPAREN block;

function_decl: 'func' type ID LPAREN formal_params? RPAREN block;
event_decl: 'event' ID block;

STRING: '"' ~["]* '"' ;
variable_decl: type ID (ASSIGN_OP expr)?;

list_decl: 'list' LESS_OP type GREATER_OP ID (ASSIGN_OP LCURL (expr)? (',' expr)* RCURL)?;
dictionary_decl: 'dictionary' LESS_OP type COMMA type GREATER_OP ID (ASSIGN_OP LCURL (LCURL expr COMMA expr RCURL)? (',' LCURL expr COMMA expr RCURL)* RCURL);
collection_expr: ID DOT ('get' LPAREN expr RPAREN | 'length');
collection_statement: ID DOT 'push' LPAREN (expr | expr COMMA expr) RPAREN;
roboCode_method: 'robot' DOT function_call;

assignment  : ID (ASSIGN_OP 
            | PLUSEQ_OP 
            | MINUSEQ_OP) expr
            ;

stat        : block
            | variable_decl NEWLINE
            | list_decl NEWLINE
            | dictionary_decl NEWLINE
            | collection_statement NEWLINE
            | 'if' expr (NEWLINE)? block ('else if' expr (NEWLINE)? block )* ('else' ( NEWLINE)? block )?
            | assignment NEWLINE
            | function_call NEWLINE
            | return_stat NEWLINE
            | for_loop
            | do_while_loop 
            | while_loop
            | COMMENT
            | ID DOT function_call NEWLINE
            | roboCode_method NEWLINE
            | decrement_operator NEWLINE
            | increment_operator NEWLINE
            ;

function_call: ID LPAREN params? RPAREN;
for_loop: 'for' LPAREN (variable_decl | assignment) ';' expr ';' expr RPAREN block;
while_loop: 'while' LPAREN expr RPAREN (NEWLINE)? block;
do_while_loop: 'do' ( NEWLINE )? block 'while' LPAREN expr RPAREN;
block: LCURL (stat | NEWLINE )* RCURL;

formal_params: type ID (',' formal_params)*;
params: expr (',' params)*;

expr        : decrement_operator                                    # decrExpr
            | increment_operator                                    # incrExpr
            | LPAREN expr RPAREN                                    # parensExpr
            | NOT_OP expr                                           # notExpr
            | <assoc=right> expr '^' expr                           # caretExpr
            | <assoc=left> expr (MUL_OP | DIV_OP | MOD_OP) expr     # infixExpr
            | <assoc=left> expr (ADD_OP | SUB_OP) expr              # infixExpr
            | expr (GEQ_OP | LEQ_OP | LESS_OP | GREATER_OP) expr    # boolExpr
            | expr (NOTEQ_OP | EQUAL_OP) expr                       # boolExpr
            | expr AND_OP expr                                      # boolExpr
            | expr OR_OP expr                                       # boolExpr
            | 'true'                                                # trueExpr
            | 'false'                                               # falseExpr
            | STRING                                                # stringExpr
            | DIGIT+                                                # digitExpr
            | ID                                                    # idExpr
            | function_call                                         # funcExpr
            | collection_expr                                       # collExpr
            | value=DIGIT                                           # digitExpr
            | ID DOT function_call                                  # roboEventCallExpr
            | roboCode_method                                       # roboMethodExpr
            ;

DOT: '.';
COMMA: ',';
LPAREN: '(';
RPAREN: ')';
LCURL: '{';
RCURL: '}';
NEWLINE: '\n' | '\r' | '\r\n';
WS: [ \t]+ -> skip;
COMMENT: ('//' ~[\r\n]* NEWLINE | '/*' .*? '*/') -> skip;

ASSIGN_OP: '=';
NOT_OP: '!';
MUL_OP: '*';
DIV_OP: '/';
MOD_OP: '%';
ADD_OP: '+';
SUB_OP: '-';
LEQ_OP: '<=';
GEQ_OP: '>=';
LESS_OP: '<';
GREATER_OP: '>';
NOTEQ_OP: '!=';
EQUAL_OP: '==';
AND_OP: '&&';
OR_OP: '||';
PLUSEQ_OP: '+=';
MINUSEQ_OP: '-=';


ID: SINGLE_CHARACTER+ (DIGIT | SINGLE_CHARACTER)*;
SINGLE_CHARACTER: ([a-zA-Z] | '_');
DIGIT: [0-9]+ ('.')? [0-9]*;
type: 'num' | 'text' | 'bool' | 'void' | 'ScannedRobotEvent';
return_stat: 'return'  expr;
decrement_operator: ID '--';
increment_operator: ID '++';
