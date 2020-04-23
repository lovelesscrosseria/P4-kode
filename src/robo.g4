grammar robo;

program : (strategy | function_decl | event_decl | assignment | variable_decl | list_decl | dictionary_decl | COMMENT | NEWLINE )* EOF;

strategy: 'strategy'  ID LCURL NEWLINE (behavior | NEWLINE)* RCURL;
behavior: 'behavior' ID LPAREN formal_params? RPAREN block;

function_decl: 'func' funcType=type funcId=ID LPAREN funcParams=formal_params? RPAREN funcBlock=block;
event_decl: 'event' ID block;

STRING: '"' ~["]* '"' ;
variable_decl: varType=type varId=ID (ASSIGN_OP value=expr)?;

list_decl: 'list' LESS_OP listType=type GREATER_OP id=ID (ASSIGN_OP LCURL listExpr=expr? (',' expr)* RCURL)?;
dictionary_decl: 'dictionary' LESS_OP type COMMA type GREATER_OP ID (ASSIGN_OP LCURL (LCURL expr COMMA expr RCURL)? (',' LCURL expr COMMA expr RCURL)* RCURL);
collection_expr: ID DOT ('get' LPAREN expr RPAREN | 'length');
collection_statement: ID DOT 'push' LPAREN (expr | expr COMMA expr) RPAREN;
roboCode_method: 'robot' DOT function_call;

assignment  : id=ID (ASSIGN_OP
            | PLUSEQ_OP 
            | MINUSEQ_OP) value=expr
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
            | loop
            | COMMENT
            | ID DOT function_call NEWLINE
            | roboCode_method NEWLINE
            | decrement_operator NEWLINE
            | increment_operator NEWLINE
            ;

function_call: ID LPAREN params? RPAREN;
loop: loopType='for' LPAREN (variable_decl | assignment) ';' expr ';' expr RPAREN block
    | loopType='while' LPAREN expr RPAREN (NEWLINE)? block
    | loopType='do' ( NEWLINE )? block 'while' LPAREN expr RPAREN
    ;
//for_loop: 'for' LPAREN (variable_decl | assignment) ';' expr ';' expr RPAREN block;
//while_loop: 'while' LPAREN expr RPAREN (NEWLINE)? block;
//do_while_loop: 'do' ( NEWLINE )? block 'while' LPAREN expr RPAREN;
block: LCURL (stat | NEWLINE )* RCURL;

formal_params: paramType=type paramId=ID (',' formal_params)*;
params: expr (',' params)*;

expr        : decrement_operator                                                # decrExpr
            | increment_operator                                                # incrExpr
            | LPAREN value=expr RPAREN                                          # parensExpr
            | NOT_OP value=expr                                                 # notExpr
            | <assoc=right> left=expr '^' right=expr                            # caretExpr
            | <assoc=left> left=expr op=(MUL_OP | DIV_OP | MOD_OP) right=expr      # infixExpr
            | <assoc=left> left=expr op=(ADD_OP | SUB_OP) right=expr               # infixExpr
            | left=expr (GEQ_OP | LEQ_OP | LESS_OP | GREATER_OP) right=expr     # boolExpr
            | left=expr (NOTEQ_OP | EQUAL_OP) right=expr                        # boolExpr
            | left=expr AND_OP right=expr                                       # boolExpr
            | left=expr OR_OP right=expr                                        # boolExpr
            | value='true'                                                      # trueExpr
            | value='false'                                                     # falseExpr
            | value=STRING                                                      # stringExpr
            | value=DIGIT+                                                      # digitExpr
            | id=ID                                                             # idExpr
            | function_call                                                     # funcExpr
            | collection_expr                                                   # collExpr
            | ID DOT function_call                                              # roboEventCallExpr
            | roboCode_method                                                   # roboMethodExpr
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
return_stat: 'return'  value=expr;
decrement_operator: ID '--';
increment_operator: ID '++';
