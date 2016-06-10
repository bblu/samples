;source code			;ndisasm section
section code			;
mov ax,$$				;00000000  B80000    mov ax,0x0
mov ax,section.data.start	;00000003  B81000    mov ax,0x10
mov ax,[var1]			;00000006  A11000    mov ax,[0x10]
mov ax,[var2]			;00000009  A11400    mov ax,[0x14]
label:jmp label			;0000000C  EBFE      jmp short 0xc
						;0000000E  0000      add [bx+si],al
section data			;
var1 dd 0x4				;00000010  0400      add al,0x0
						;00000012  0000      add [bx+si],al
var2 dw 0x99 			;00000014  99        cwd
						;00000015  00        db 0x00











