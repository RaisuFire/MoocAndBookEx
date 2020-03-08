@SCREEN
D=A
@addr
M=D //addr=16384


@32
D=A
@m
M=D //m = 255

@255
D=A
@n
M=D //n = 255

@j
M=0 //j=0

(COLOOP)
    @j
    D=M
    @m
    D=D-M
    @END
    D;JGT

    @i
    M=0 //i = 0

    @j
    D=M
    @addr
    D=D+M
    @caddr
    M=D

    @j
    M=M+1

(LOOP)
    @i
    D=M
    @n
    D=D-M
    @COLOOP
    D;JGT

    @caddr
    A=M
    M=-1

    @i
    M=M+1   //i = i + 1
    @32
    D=A
    @caddr
    M=D+M   //addr = addr + 32
    @LOOP
    0;JMP   //goto loop

(END)
    @END
    0;JMP
