package 文件管理;

import java.util.Scanner;

/**
 * Created by wyx11 on 2017-4-29.
 */
public class Demo {


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("请输入磁盘的位数图大小");
        File.init(sc.nextInt());
        Dir dir = new Dir();
        dir.init();
        System.out.println(File.bitFatToString());
        System.out.println("MD:创建子目录\n" +
                "CD:切换工作目录\n" +
                "RD:删除子目录\n" +
                "MK:创建空文件\n" +
                "DEL:删除文件\n" +
                "DIR:列出当前目录的所有目录项\n" +
                "TREE树形结构显示\n" +
                "SHOW显示位视图及FAT表");
        while (true) {
            System.out.print(dir.getCurrentPosition()+">");
            String cmd = sc.next().toLowerCase();
            String name;
            int size;
            switch (cmd) {
                case "md":
                    try {
                        System.out.println("请输入目录名");
                        System.out.print(dir.getCurrentPosition()+">");
                        name = sc.next();
                        dir.addFile(name, File.BLOCK_SIZE, File.DIRECTORY_TYPE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "cd":
                    try {
                        System.out.print(dir.getCurrentPosition()+">");
                        name = sc.next();
                        dir = dir.changeDirectory(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "rd":
                    try {
                        System.out.print(dir.getCurrentPosition()+">");
                        name = sc.next();
                        dir.removeDirectory(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "mk":
                    try {
                        System.out.println("请输入文件名及大小");
                        System.out.print(dir.getCurrentPosition()+">");
                        name = sc.next();
                        System.out.print(dir.getCurrentPosition()+">");
                        size = sc.nextInt();
                        dir.addFile(name, size, File.FILE_TYPE);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "del":
                    try {
                        System.out.print(dir.getCurrentPosition()+">");
                        name = sc.next();
                        dir.deleteFile(name);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "dir":
                    try {
                        dir.showDir();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "tree":
                    try {
                        dir.showTree();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case "show":
                    System.out.println(File.bitFatToString());
                    break;
                default:
                    System.out.println("输入有误!");
                    break;
            }
        }
    }
}