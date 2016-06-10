;MBR type by bblu @ 201
;LOADER_BASE_ADDR equ 0xA000
;LOADER_START_SECTOR equ 0x2
;--------------------------------------
%include "3boot.inc"
SECTION MBR vstart=0x7c00
	mov ax,cs
	mov ds,ax
	mov es,ax
	mov ss,ax
	mov fs,ax
	mov sp,0x7c00
	mov ax,0xb800			;graphic memory text mode begin with 0xb800
	mov gs,ax

;use 0x06 to clear screen
	mov ax, 0x600
	mov bx, 0x700
	mov cx, 0		;(0,0)
	mov dx, 0x184f	;(80,25)

	int 0x10

;writer 1 MBR
	mov byte [gs:0x00],'1'
	mov byte [gs:0x01],0xA4	;A:background green and flash, 4:forecolor is red
	;mov byte [gs:0x02],' '
	;mov byte [gs:0x03],0xA4
	mov byte [gs:0x04],'M'
	mov byte [gs:0x05],0xA4
	mov byte [gs:0x06],'B'
	mov byte [gs:0x07],0xA4
	mov byte [gs:0x08],'R'
	mov byte [gs:0x09],0xA4
;load LBA address
	mov eax,LOADER_START_ADDR	;very begin sector of disk
	mov bx,LOADER_BASE_ADDR		;addr for write
	mov cx,1					;counter the sectors to write
	call rd_disk_m_16			;enter to read a sector

	jmp LOADER_BASE_ADDR
;------------------------------------------------
;function read sector
rd_disk_m_16:
;------------------------------------------------
	mov esi,eax		;backup eax
	mov di,cx		;backup cx
;1.set the count of sector
	mov dx,0x1f2
	mov al,cl
	out dx,al		;sector amount

	mov eax,esi		;recover eax
;2.Load LBA addr to 0x1f3~0x1f6
	mov dx,0x1f3
	out dx,al
	
	mov cl,8		;LBA:15~8
	shr eax,cl
	mov dx,0x1f4
	out dx,al

	shr eax,cl		;LBA:23~16
	mov dx,0x1f5
	out dx,al

	shr eax,cl
	and al,0x0f
	or al,0xe0		;set 7~4 to 1110,stand for LBA
	mov dx,0x1f6
	out dx,al
;3.set 0x1f7 to read order 0x20
	mov dx,0x1f7
	mov al,0x20
	out dx,al
;4.check disk status
  .not_ready:
	nop
	in al,dx
	and al,0x88		;the 4th bit is 1:ready to transmit data
					;the 7th bit is 1:disk is busy
	cmp al,0x80
	jnz .not_ready
;5.read data from 0x1f0
	mov ax,di	;di is the sector number for reading
	mov dx,256	;a sector has 256B
	mul dx		;di*256/2
	mov cx,ax
	mov dx,0x1f0
  .go_on_read:
  	in ax,dx
	mov [bx],ax
	add bx,2
	loop .go_on_read
	ret

;pause the program
;	jmp $

	times 510-($-$$) db 0
	db 0x55,0xaa
