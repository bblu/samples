nasm -o mbr.bin $1 && dd if=./mbr.bin of=/opt/bochs/60M.img bs=512 count=1 conv=notrunc
