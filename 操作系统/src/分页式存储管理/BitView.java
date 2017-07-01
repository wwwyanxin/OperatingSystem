package 分页式存储管理;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by wyx11 on 2017-4-2.
 */
public class BitView {
    int[][] bit;

    public  BitView() {
        Scanner in = new Scanner(System.in);
        int n;//n是位视图的大小
        System.out.println("输入位视图共多少位:");
        n=in.nextInt();
        bit=new int[n][8];

        Random random = new Random();
        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < bit[i].length; j++) {
                bit[i][j]=random.nextInt(2);//随机产生一个大于等于0小于2的整数
            }

        }
    }

    public BitView(int n) {
        bit=new int[n][8];

        Random random = new Random();
        for (int i = 0; i < bit.length; i++) {
            for (int j = 0; j < bit[i].length; j++) {
                bit[i][j]=random.nextInt(2);//随机产生一个大于等于0小于2的整数
            }

        }
    }

    public String toString() {
        String str = "";
        for (int i = 0; i < bit.length; i++) {
            str+="[ ";
            for (int j = 0; j < bit[i].length; j++) {
                str+=bit[i][j]+" ";
            }
            str+="]\n";
        }

        return str;
    }

    public int getFirstEmptyBit() {
        int num=-1;

        Label:
        for(int i=0;i<bit.length;i++) {
            for(int j=0;j<bit[i].length;j++) {
                if (bit[i][j] == 0) {
                    num=i*8+j;//找到空内存地址赋值给num
                    break Label;//跳出循环
                    }
                }
            }
        return num;
    }
    public int[] getEmptyBit(int n) {
        /*Scanner in = new Scanner(System.in);
        System.out.println("分配的物理块数:");
        int n=in.nextInt();*/
        int[] memory = new int[n];//查找位视图得到空物理内存
        int sum=0;//当前查找到空内存的个数

        Label://Label标签
        for(int i=0;i<bit.length;i++) {
            for(int j=0;j<bit[i].length;j++) {
                if (bit[i][j] == 0) {
                    memory[sum]=i*8+j;//找到空内存地址赋值给memory
                    sum++;//计数+1
                    if (sum == n) {//已装满memory
                        //System.out.println("物理块分配完毕!");
                        break Label;//跳出Label循环
                    }
                }
            }
        }

        return memory;

    }

    public void setBit(int memory, int a) {//设置memory的占用情况
        int i=memory/8;
        int j=memory%8;

        bit[i][j]=a;
    }

    public boolean isEmptyOfIndex(int memory) {//判断此位是否为空
        int i=memory/8;
        int j=memory%8;

        if(bit[i][j]==0)
            return true;
        else
            return false;
    }

    /*public static void main(String[] args) {
        BitView bit=new BitView();
        System.out.println(bit);
        int[] memory=bit.getEmptyBit();
        System.out.println(memory[0]+" "+memory[1]+" "+memory[2]);
        bit.setBit(memory[0], 1);
        System.out.println(bit);
    }*/
}
