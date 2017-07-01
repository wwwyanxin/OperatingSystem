package 文件管理;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wyx11 on 2017-4-28.
 */
public class FCB {
    private String name;//文件名
    private int size;//大小
    private int firstBlock;//第一块的位置
    private int type;//类型 1为文件,2为目录,0为已删除
    private String dateTime;//创建时间

    public FCB() {
        name = null;
        size = 0;
        firstBlock = -1;
        type = -1;
    }

    public FCB(String name, int type) {
        this.name = name;
        this.type = type;
    }

    public void setAttribute(String name, int size, int firstBlock, int type) {
        this.name = name;
        this.size = size;
        this.firstBlock = firstBlock;
        this.type = type;
        dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());//获得当前系统时间
    }

    public boolean equals(FCB otherFcb) {
        return this.name.equals(otherFcb.getName());
    }

    public String getSimInf() {//获得不包括时间和类型的简单信息
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append("\n");

        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(dateTime);//时间
        sb.append("\t\t");
        if (type == 1) {
            sb.append("\t\t");//类型为文件
        } else if (type == 2) {
            sb.append("<DIR>\t");//类型为目录
        } else {
            sb.append("<已删除>");
        }

        sb.append(name);
        sb.append("\n");

        return sb.toString();
    }

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getFirstBlock() {
        return firstBlock;
    }

    public void setFirstBlock(int firstBlock) {
        this.firstBlock = firstBlock;
    }

    public void setType(int type) {
        this.type = type;
    }

}