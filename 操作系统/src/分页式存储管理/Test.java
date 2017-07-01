package 分页式存储管理;

import java.util.List;
import java.util.Scanner;

/**
 * Created by wyx11 on 2017-3-31.
 */
public class Test {
    public static void main(String[] args) throws Exception {

        Paging p = new Paging();
        System.out.println("输入页面置换算法F/f:FIFO,L/l:LRU");
        Scanner in = new Scanner(System.in);
        String alg = in.next();
        System.out.println(p);
        while (true) {
            p.applyPage(alg);
            System.out.println(p);
        }

    }
}
