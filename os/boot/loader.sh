nasm -o loader.bin $1 && dd if=./loader.bin of=/opt/bochs/60M.img bs=512 count=3 seek=2 conv=notrunc
