section .text
mov eax, 0x10
jmp $

section worlddata

file2var db 3

section worldtext

global print

print:
	mov edx,[esp+8]	;str length
	mov ecx,[esp+4]	;str

	mov ebx,1
	mov eax,4	;sys_write
	int 0x80 ;
	ret


