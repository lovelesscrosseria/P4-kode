grammar robo;

program : (strategy | function_decl | event_decl | assignment | variable_decl | list_decl | dictionary_decl | COMMENT | NEWLINE )* EOF;

strategy: 'strategy'  id=ID LCURL NEWLINE (behavior | NEWLINE)* RCURL;
behavior: 'behavior' id=ID LPAREN funcParams=formal_params? RPAREN funcBlock=block;

function_decl: 'func' funcType=type funcId=ID LPAREN funcParams=formal_params? RPAREN funcBlock=block;
event_decl: 'event' id=ID eventBlock=block;

STRING: '"' ~["]* '"' ;
variable_decl: varType=type varId=ID (ASSIGN_OP value=expr)?;
list_decl: 'list' LESS_OP listType=type GREATER_OP id=ID (ASSIGN_OP LCURL listExpr=expr? (',' expr)* RCURL)?;
dictionary_decl: 'dictionary' LESS_OP dicKey=type COMMA dicValue=type GREATER_OP id=ID (ASSIGN_OP LCURL defaultValue=dictionaryValue? (',' dictionaryValue)* RCURL)?;
dictionaryValue: LCURL key=expr COMMA value=expr RCURL;
dotOperation: id=ID DOT method=function_call;
roboCode_method: 'robot' DOT method=function_call;
ifStmt: 'if' ifExpr=expr (NEWLINE)? ifBlock=block ('else if' expr (NEWLINE)? block )* ('else' ( NEWLINE)? block )?;

assignment  : id=ID (ASSIGN_OP
            | PLUSEQ_OP 
            | MINUSEQ_OP) value=expr
            ;

stat        : block
            | variable_decl NEWLINE
            | list_decl NEWLINE
            | dictionary_decl NEWLINE
            | dotOperation NEWLINE
            | ifStmt NEWLINE
            | assignment NEWLINE
            | function_call NEWLINE
            | return_stat NEWLINE
            | loop
            | COMMENT
            | roboCode_method NEWLINE
            | decrement_operator NEWLINE
            | increment_operator NEWLINE
            ;

function_call: id=ID LPAREN funcParams=params? RPAREN;
loop: loopType='for' LPAREN (forLoopVarDec=variable_decl | forLoopAssign=assignment) ';' loopCondition=expr ';' loopIncrement=expr RPAREN loopBlock=block
    | loopType='while' LPAREN loopCondition=expr RPAREN (NEWLINE)? loopBlock=block
    | loopType='do' ( NEWLINE )? loopBlock=block 'while' LPAREN loopCondition=expr RPAREN
    ;
block: LCURL (stat | NEWLINE )* RCURL;

formal_params: paramType=type paramId=ID (',' formal_params)*;
params: paramExpr=expr (',' params)*;

expr        : decrement_operator                                                # decrExpr
            | increment_operator                                                # incrExpr
            | LPAREN value=expr RPAREN                                          # parensExpr
            | NOT_OP value=expr                                                 # notExpr
            | <assoc=right> left=expr '^' right=expr                            # caretExpr
            | <assoc=left> left=expr op=(MUL_OP | DIV_OP | MOD_OP) right=expr   # infixExpr
            | <assoc=left> left=expr op=(ADD_OP | SUB_OP) right=expr            # infixExpr
            | left=expr op=(GEQ_OP | LEQ_OP | LESS_OP | GREATER_OP) right=expr  # boolExpr
            | left=expr op=(NOTEQ_OP | EQUAL_OP) right=expr                     # boolExpr
            | left=expr op=AND_OP right=expr                                    # boolExpr
            | left=expr op=OR_OP right=expr                                     # boolExpr
            | value='true'                                                      # trueExpr
            | value='false'                                                     # falseExpr
            | value=STRING                                                      # stringExpr
            | value=DIGIT+                                                      # digitExpr
            | id=ID                                                             # idExpr
            | funcCall=function_call                                            # funcExpr
            | collectionExpr=dotOperation                                       # collExpr
            | roboMethod=roboCode_method                                        # roboMethodExpr
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
