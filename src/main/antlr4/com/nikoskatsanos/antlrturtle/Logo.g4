grammar Logo ;

@header {
}

prog: logoExpression+ EOF ;

logoExpression: forward
                | back
                | right
                | left
                | clearscreen
                | set
                | penUp
                | penDown
                | resetAngle
                ;

forward: FORWARD INT ;
back: BACK INT ;
right: RIGHT INT;
left: LEFT INT;
clearscreen: CLEARSCREEN;
set: SET POINT;
penUp: PEN_UP;
penDown: PEN_DOWN;
resetAngle: RESET_ANGLE;

// Lexer Rules

fragment FD: ('forward' | 'fd');
fragment BK: ('back' | 'bk');
fragment LT: ('left' | 'lt');
fragment RT: ('right' | 'rt');
fragment CLS: ('clearscreen' | 'cls');
fragment ST: ('set' | 'st');
fragment PU: ('penUp' | 'pu');
fragment PD: ('penDown' | 'pd');

FORWARD: FD;
BACK: BK;
LEFT: LT;
RIGHT: RT;
CLEARSCREEN: CLS;
SET: ST;
PEN_UP: PU;
PEN_DOWN: PD;
RESET_ANGLE: 'resetAngle';

INT: [0-9]+ ;
POINT: INT','INT;

NEWLINE: '\r'? '\n' -> skip;
WS: ' '+ -> skip;
COMMENT: '//' ~[\n]* -> skip;
