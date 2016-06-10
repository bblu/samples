;source code	;ndisasm address
mov ax,$$		;00000000  B80000	mov ax,0x0
mov ds,ax		;00000003  8ED8		mov ds,ax
mov ax,[var]	;00000005  A10D00	mov ax,[0xd]
label:mov ax,$	;00000008  B80800	mov ax,0x8
jmp label		;0000000B  EBFB		jmp short 0x8
var dw 0x99		;0000000D  99		cwd
				;0000000E  00		db 0x00	//小端字序高位00







