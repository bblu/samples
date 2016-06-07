section .bss
resb 2*32

section hellodata

strHello db "Hello,world!",0Ah
STRLEN equ $-strHello

section hellotext
extern print

global _start

_start:
	push STRLEN
	push strHello
	call print

;return to os
mov ebx, 0
mov eax, 1
int 0x80

