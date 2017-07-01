package 分页式存储管理;

import java.util.Random;
import java.util.Scanner;

/**
 * Created by wyx11 on 2017-4-2.
 */
public class BitView2 {
    private byte[] bit;

    public BitView2() {
        Scanner in = new Scanner(System.in);
        int n=in.nextInt();
        bit=new byte[n];//输入位视图大小

        Random random=new Random();
        random.nextBytes(bit);
    }

    public String toString() {
        String str="[ ";

        for (byte b : bit) {
            String bin=Integer.toBinaryString(b&0xFF);//把b转为二进制字符串
            //String format=String.format("%04d",bin);//将字符串格式化,补0,长度8,整型
            str+=bin+" ";
            System.out.println(b);
        }
        str += "]";
        return str;
    }

    public static void main(String[] args) {
        BitView2 b=new BitView2();
        System.out.println(b);
        System.out.println(Byte.parseByte("3"));

    }
}
