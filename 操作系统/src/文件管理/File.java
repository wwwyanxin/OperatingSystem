package 文件管理;

import 分页式存储管理.BitView;


/**
 * Created by wyx11 on 2017-4-29.
 */
public class File extends FCB {
    //private FCB fcb;
    private int useBlock;//占用的块数
    private static BitView bitView;//位视图
    private static FAT fat;//FAT表
    protected static final int BLOCK_SIZE =1024;//块大小1024byte
    protected static final int FILE_TYPE=1;
    protected static final int DIRECTORY_TYPE=2;
    protected static final int DELETE_TYPE=0;

    public File() {
        super();
    }
    public File(String name,int type) {
        super(name,type);
    }
    public static void init(int sum) {//将静态域初始化
        bitView = new BitView(sum);
        fat = new FAT(sum*8);

        for(int i=0;i<sum*8;i++) {//fat表根据位视图随机结果初始化
            if (bitView.isEmptyOfIndex(i)) {
                fat.setFatOfIndex(i, 0);
            } else {
                fat.setFatOfIndex(i,-2);//-2代表被其他文件所占用
            }
        }
    }

    public int getFirstEmptyBlock() {
        return bitView.getFirstEmptyBit();
    }


    public void setFatBitView() {
        if (this.getSize() % BLOCK_SIZE == 0) {

            useBlock = this.getSize() / BLOCK_SIZE;
        } else {
            useBlock=this.getSize()/ BLOCK_SIZE +1;
        }

        int[] bitBlock=bitView.getEmptyBit(useBlock);//获得空的位视图地址
        for (int i = 0; i < useBlock; i++) {
            if (i == useBlock - 1) {//如果当前块是最后一个
                fat.setFatOfIndex(bitBlock[i], -1);//设置fat表当前为末位-1
            }else {
                fat.setFatOfIndex(bitBlock[i], bitBlock[i + 1]);//设置fat表当前指向下一个节点
            }
                bitView.setBit(bitBlock[i],1);//设置当前位为占用1
        }
    }

    public void releaseFatBitView() {
        int index=this.getFirstBlock();//第一个坐标
        for (int i = 0; i < useBlock; i++) {
            bitView.setBit(index,0);
            int temp = fat.getValueOfIndex(index);
            fat.setFatOfIndex(index, 0);
            index=temp;
        }
    }

    public static String bitFatToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(bitView).append("\n").append(fat);
        return sb.toString();
    }
}
