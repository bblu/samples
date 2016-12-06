/*-----------------------------------------------
  stepperbykey.c
  编写：bblu
  日期：2016.12
  内容：本程序用于测试4相步进电机常规驱动 
        使用1-2相励磁
        1-2相激励功率增倍，步进角度减半，抖动减少
        顺序如下 a-ab-b-bc-c-cd-d-da   又称4相8拍
		P3.2口外部中断0作为按键检测端，对应023板子上的S1按键
 ------------------------------------------------*/

#include <reg52.h>

sbit KEY_1=P3^2;
sbit KEY_2=P3^3;
sbit KEY_3=P3^4;
sbit KEY_4=P3^5;

sbit A1=P1^0; //定义步进电机连接端口
sbit B1=P1^1;
sbit C1=P1^2;
sbit D1=P1^3;


#define Coil_AB1 {A1=1;B1=1;C1=0;D1=0;}//AB相通电，其他相断电
#define Coil_BC1 {A1=0;B1=1;C1=1;D1=0;}//BC相通电，其他相断电
#define Coil_CD1 {A1=0;B1=0;C1=1;D1=1;}//CD相通电，其他相断电
#define Coil_DA1 {A1=1;B1=0;C1=0;D1=1;}//D相通电，其他相断电
#define Coil_A1 {A1=1;B1=0;C1=0;D1=0;}//A相通电，其他相断电
#define Coil_B1 {A1=0;B1=1;C1=0;D1=0;}//B相通电，其他相断电
#define Coil_C1 {A1=0;B1=0;C1=1;D1=0;}//C相通电，其他相断电
#define Coil_D1 {A1=0;B1=0;C1=0;D1=1;}//D相通电，其他相断电
#define Coil_OFF {A1=0;B1=0;C1=0;D1=0;}//全部断电

unsigned char Speed;
bit Flag;
/*------------------------------------------------
 uS延时函数，含有输入参数 unsigned char t，无返回值
 unsigned char 是定义无符号字符变量，其值的范围是
 0~255 这里使用晶振12M，精确延时请使用汇编,大致延时
 长度如下 T=tx2+5 uS 
------------------------------------------------*/
void DelayUs2x(unsigned char t)
{   
 while(--t);
}
/*------------------------------------------------
 mS延时函数，含有输入参数 unsigned char t，无返回值
 unsigned char 是定义无符号字符变量，其值的范围是
 0~255 这里使用晶振12M，精确延时请使用汇编
------------------------------------------------*/
void DelayMs(unsigned char t)
{
 while(t--)
 {
     //大致延时1mS
     DelayUs2x(245);
	 DelayUs2x(245);
 }
}
//
unsigned char code DuanMa[]={0x3f,0x06,0x5b,0x4f,0x66,0x6d,0x7d,0x07,0x7f,0x6f,	//0~9
		                  	         0x77,0x7c,0x39,0x5e,0x79,0x71};//A~F


sbit units=P2^3;     //
sbit tens=P2^4;	     //
sbit hundreds=P2^5;  //
sbit thousands=P2^6; //

//ms?????
void delay_ms(int i)
{
	unsigned char temp;
	for(;i>0;i--)
		for(temp=125;temp>0;temp--);
}

void display(int i)
{
	P2=P2|0x78;
	if(i>=0)
	{
		P0=DuanMa[i/1000];
		thousands=0;delay_ms(10);thousands=1;
		P0=DuanMa[i%1000/100];
		hundreds=0;delay_ms(10);hundreds=1;
		P0=DuanMa[i%100/10];
		tens=0;delay_ms(10);tens=1;
		P0=DuanMa[i%10];
		units=0;delay_ms(10);units=1;
	}
	else
	{
		i=0-i;
		P0=0x80;
		thousands=0;delay_ms(10);thousands=1;
		P0=DuanMa[i%1000/100];
		hundreds=0;delay_ms(10);hundreds=1;
		P0=DuanMa[i%100/10];
		tens=0;delay_ms(10);tens=1;
		P0=DuanMa[i%10];
		units=0;delay_ms(10);units=1;
	}
}

void pause(int i,int idx)
{ 
 for(;i>0;i--)
 { 
	display(idx);
	DelayMs(100);
 }
}
/*------------------------------------------------
                    主函数
------------------------------------------------*/
 unsigned int idx=513;
 unsigned int deg=0;
 unsigned int fan=8;  //64 devide
main()
{

 
 EA=1;          //全局中断开
 EX0=1;         //外部中断0开
 IT0=1;         //1表示边沿触发

 Speed=10;


while(1){
 Coil_OFF
 while(idx<513)  //正向
  {  
  	 Coil_A1             
     DelayMs(Speed);
     Coil_DA1                
     DelayMs(Speed);         
	 Coil_D1       
     DelayMs(Speed);
     Coil_CD1
     DelayMs(Speed);


	 Coil_C1       
     DelayMs(Speed);
     Coil_BC1
     DelayMs(Speed);
	 Coil_B1       
     DelayMs(Speed);
     Coil_AB1
     DelayMs(Speed); 
	 deg = (int)(idx*7.03125);
	 if(idx%fan==0)
		pause(20,deg);
	 display(deg);
	 idx++;
  }
   display(deg);
  Coil_OFF
 }
}

/*------------------------------------------------
                 外部中断程序
------------------------------------------------*/
void ISR_INT0(void) interrupt 0 
{

  if(!KEY_1)  
    {
	 DelayMs(10);
     if(!KEY_1) 
     {
        fan=8;  //64 devide arc=5.625 
	    idx=0;//旋转一周时间 
     }
   }
   if(!KEY_2)  
    {
	 DelayMs(10);
     if(!KEY_2) 
     {
        fan=16;  //32 devide arc=8.4375 
	    idx=0;//旋转一周时间 
     }
   }
   if(!KEY_3)  
    {
	 DelayMs(10);
     if(!KEY_3) 
     {
        fan=32;  //32 devide arc=11.25  
	    idx=0;//旋转一周时间 
     }
   }
   if(!KEY_4)  
    {
	 DelayMs(10);
     if(!KEY_4) 
     {
        fan=64;  //16 devide arc= 22.5 
	    idx=0;//旋转一周时间 
     }
   }
}
