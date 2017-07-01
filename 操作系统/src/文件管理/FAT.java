package 文件管理;

import java.util.Random;

/**
 * Created by wyx11 on 2017-4-29.
 */
public class FAT {
    private int[] fat;

    public FAT(int n) {
        fat = new int[n];
    }

    public boolean isEmptyOfIndex(int index) {
        if (fat[index] == 0) {
            return true;
        }else
            return false;
    }

    public void setFatOfIndex(int index,int value) {
        fat[index]=value;
    }

    public int getValueOfIndex(int index) {
        return fat[index];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-------------\n");
        for (int i = 0; i < fat.length; i++) {
            sb.append("|[").append(i).append("]\t").append(fat[i]).append("\t|");
            sb.append("\n-------------\n");
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.println(new FAT(16));
    }
}
