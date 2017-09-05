section .data
str_c_lib: db "c library says: helo world!", 0xa ;0xa stands for LF ASCII code
str_c_lib_len equ $-str_c_lib

str_syscall: db "syscall says: hello world!", 0xa
str_syscall_len equ $-str_syscall

section .text
global _start
_start:
;1. c lang call write;;;;;;;;;;;;;
	push str_c_lib_len
	push str_c_lib
	push 1

	call simu_write
	add esp, 12		;reuse stack

;2. use system call directly;;;;;;
	mov eax, 4
	mov ebx, 1
	mov ecx, str_syscall
	mov edx, str_syscall_len
	int 0x80

;;;;;;;;;;;;;;exit ;;;;;;;;;;;;;;;;
	mov eax, 1
	int 0x80

	;;;;;
simu_write:
	push ebp
	mov ebp, esp
	mov eax, 4 		;function num4 is write system call
	mov ebx, [ebp+8]
	mov ecx, [ebp+12]
	mov edx, [ebp+16]
	int 0x80
	pop ebp
	ret


;$nasm -f elf -o a.o system_call.S
;$ld -m  elf_i386 -o a.out a.o
;$ ./a.out
;$
