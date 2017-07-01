package 银行家算法;

import java.util.Scanner;

/**
 * Created by wyx11 on 2017-5-9.
 */
public class Test {
    public static void main(String[] args) {
        int[] ava = {10,5,7};
        int[][] max = {{7,5,3},{3,2,2},{9,0,2},{2,2,2},{4,3,3}};
        int[][] all = {{0,1,0},{2,0,0},{3,0,2},{2,1,1},{0,0,2}};
        Resource res=new Resource(ava, max, all);
        Scanner scan = new Scanner(System.in);
        while (true) {
            for (int i = 0; i < res.getProcesses().length; i++) {
                System.out.print("  P["+i+"]");
            }
            System.out.println("\n输入申请的进程P[i],若不申请输入exit");
            String in=scan.next().toUpperCase();
            if ("EXIT".equals(in)) {
                break;
            } else {
                for (int i = 0; i < res.getProcesses().length; i++) {
                    if (i == Integer.parseInt(in)) {
                        System.out.println("输入申请资源大小:");
                        for (int j = 0; j < res.getAvailable().length; j++) {
                            int quest=scan.nextInt();
                            res.getProcesses()[i].setResOfIndex(j,quest);
                        }
                        try {
                            res.AllocationRes(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
            }
        }
        try {
            res.BanAlg();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
