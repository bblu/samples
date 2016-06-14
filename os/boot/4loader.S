;loader type by bblu @ 2016
%include "4boot.inc"
SECTION loader vstart=LOADER_BASE_ADDR
LOADER_STACK_TOP equ LOADER_BASE_ADDR
jmp loader_start
;generate gdt
GDT_BASE:	dd	0x00000000
			dd	0x00000000

CODE_DESC:	dd	0x0000FFFF
			dd 	DESC_CODE_HIGH4

DATA_STACK_DESC: dd 0x0000FFFF
			dd	DESC_DATA_HIGH4

VIDEO_DESC:	dd	0x80000007	;limit=(0xbffff-0xb8000)/4k=0x7
			dd	DESC_VIDEO_HIGH4

GDT_SIZE 	equ	$ - GDT_BASE
GDT_LIMIT	equ GDT_SIZE - 1
times 60 dq 0
SELECTOR_CODE 	equ (0x001 << 3) + TI_GDT + RPL0
SELECTOR_DATA 	equ (0x002 << 3) + TI_GDT + RPL0
SELECTOR_VIDEO	equ (0x003 << 3) + TI_GDT + RPL0


;pointer to gdt
gdt_ptr 	dw GDT_LIMIT
			dd GDT_BASE
loadermsg	db '4 loader in real.'
;INT 0x10 : print string
loader_start:

















	mov 	sp, LOADER_BASE_ADDR
	mov 	bp, loadermsg		;ES:BP = address of string
	mov 	cx, 17				;cx = len(str)
	mov 	ax, 0x1301			;AH=13,AL=01h
	mov 	bx, 0x001f			;pageNum=0,BL=1f=blue background and red font
	mov 	dx, 0x1800
	int 	0x10 				;INT 0x10






;prepare to safe mode
;	set A20
in al,0x92
or al,00000010b
out 0x92,al

;	load gdt
lgdt [gdt_ptr]


;	cr0
mov eax, cr0
or eax, 0x00000001
mov cr0,eax

jmp dword SELECTOR_CODE:p_mode_start


[bits 32]
p_mode_start:
	mov ax, SELECTOR_DATA
	mov ds, ax
	mov es, ax
	mov ss, ax
	mov esp,LOADER_STACK_TOP
	mov ax, SELECTOR_VIDEO
	mov gs, ax

	mov byte [gs:160], 'p'

	jmp $

