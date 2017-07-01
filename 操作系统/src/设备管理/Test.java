package 设备管理;

import java.util.Scanner;

/**
 * Created by wyx11 on 2017-4-15.
 */
public class Test {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        DeviceControl d = new DeviceControl();
        d.init();

        while (true) {
            System.out.println(d);
            System.out.println("ad:添加设备,de:删除设备,ap:申请设备,re:释放设备,id:设备独立性");
            String cmd = in.next().toLowerCase();
            switch (cmd) {
                case "ad":
                    d.addDevice();
                    break;
                case "de":
                    d.deleteDevice();
                    break;
                case "ap":
                    d.applyDevice();
                    break;
                case "re":
                    d.releaseDevice();
                    break;
                case "id":
                    d.deviceIndependent();
                    break;
                default:
                    System.out.println("输入有误!");
                    break;
            }
        }
    }
}
