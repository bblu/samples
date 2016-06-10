;MBR type by bblu @ 2016
;--------------------------------------
SECTION MBR vstart=0x7c00
	mov ax,cs
	mov ds,ax
	mov es,ax
	mov ss,ax
	mov fs,ax
	mov sp,0x7c00

;use 0x06 to clear screen
	mov ax, 0x600
	mov bx, 0x700
	mov cx, 0		;(0,0)
	mov dx, 0x184f	;(80,25)

	int 0x10

;get_cursor
	mov ah, 3
	mov bh, 0

	int 0x10
;print string
	mov ax, message
	mov bp, ax

	mov cx, 20
	mov ax, 0x1301

	mov bx, 0x2

	int 0x10
;pause the program
	jmp $

	message db "1 MBR by bblu @ 2016"
	times 510-($-$$) db 0
	db 0x55,0xaa
