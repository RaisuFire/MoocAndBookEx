
// define screen pointer (16384)
@SCREEN
D=A
@POINTER
M=D

(LOOP)
    // Fill in the pixel that POINTER points to
    D=-1
    @POINTER
    A=M
    M=D

    // Iterate pointer by 1
    D=1
    @POINTER
    D=D+M
    @POINTER
    M=D

    @LOOP
    0; JMP
