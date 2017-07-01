package 分页式存储管理;

import java.util.Scanner;

/**
 * Created by wyx11 on 2017-4-2.
 */
public class Paging {//分页
    private BitView bit;//位视图
    private LinkedList<Integer> memoryStack;//内存栈,物理块;
    private int blockLength;//块长,单位k
    private int missing;//缺页数
    private int hit;//命中数;
    private int[] memorys;//空内存的下标
    private PageTable[] pages;

    class PageTable {
        private int pageNum;//页号
        private int phyAddress;//物理页号
        private int status;//页表使用状态

        public PageTable() {

            phyAddress = -1;//-1代表未分配地址
            status = 0;//0为未使用,1使用


        }

        public void setPagNum(int num) {
            pageNum = num;//设置页号
        }

        public String toString() {

            return pageNum + " " + phyAddress + " " + status + "\n";
        }

        public void setPhyAddress(int memory) {//修改物理地址
            phyAddress = memory;
        }

        public void setStatus(int status) {//修改状态位
            this.status = status;
        }



    }

    public Paging() {
        bit = new BitView();
        memoryStack = new LinkedList<>();

        Scanner in = new Scanner(System.in);

        System.out.println("输入块长(k):");
        int length = in.nextInt();
        blockLength = length;

        System.out.println("输入页表长度:");
        int n = in.nextInt();//n为页表长度
        pages = new PageTable[n];//初始化页表
        for (int i = 0; i < n; i++) {
            pages[i] = new PageTable();
            pages[i].setPagNum(i);//i为页号
        }

        //初始化内存栈链表
        System.out.println("输入可用内存大小:");
        n = in.nextInt();
        for (int i = 0; i < n; i++) {
            memoryStack.addNode(-1);//将未使用的节点赋值-1
        }
        //获得空内存下标
        memorys = bit.getEmptyBit(n);


        hit = 0;//命中0
        missing = 0;//缺页0
    }

    public int getPageSize() {
        return pages.length;
    }


    public String toString() {
        String str = "";
        str += "位视图:\n" + bit.toString() + "\n";
        str += "分页:\n";
        for (PageTable page : pages) {
            str += page.toString();
        }
        str += "\n内存栈:\n" + memoryStack.toString() + "\n";

        //计算缺页率
        double missingRate;
        if ((hit + missing) == 0) {
            missingRate = 0;
        } else {
            missingRate = (double)missing / (hit + missing);//将missing转为double以得到double型的结果
        }
        str += "命中:" + hit + " 缺页:" + missing + " 缺页率:" + missingRate * 100 + "%\n";
        return str;
    }

    public int push(int pageNum) throws Exception {//返回使用的物理页号

        int bottom = memoryStack.removeNode(memoryStack.getLinkedListSize() - 1).getNodeElement();//弹出栈底元素
        memoryStack.insertNode(0, pageNum);//将页号加到内存栈顶

        int phyPage = -1;

        if (bit.isEmptyOfIndex(memorys[memorys.length - 1])) {////获得的内存是否全被占用,(检查最后一个内存是否为空)

            for (int m : memorys) {
                if (bit.isEmptyOfIndex(m)) {//如果有没被占用的,则直接占用
                    bit.setBit(m, 1);//设置为占用
                    pages[pageNum].setPhyAddress(m);//物理地址=m
                    phyPage = m;
                    break;
                }
            }
        } else {
            phyPage = pages[bottom].phyAddress;
            pages[pageNum].setPhyAddress(phyPage);//内存全被占用的情况下,将出栈的物理页号给新入栈的页

            //pages[pageNum].phyAddress=memorys[memorys.length-1];//如果没有空的,则物理页号=栈底弹出的物理页号
        }

        if (bottom != -1) {//如果出栈的元素不是空元素,则将之前使用的页表清空
            pages[bottom].setPhyAddress(-1);
            pages[bottom].setStatus(0);
        }

        return phyPage;

    }

    public int FIFO(int pageNum) throws Exception {//返回使用的物理页号

        if (pages[pageNum].status == 1) {//命中
            hit++;
            return pages[pageNum].phyAddress;
        } else {//未命中
            missing++;
            //pages[pageNum].setPageAddress(pageAddress);//设置页内地址
            pages[pageNum].setStatus(1);   //设置状态位
            return push(pageNum);//压栈
        }
    }

    public int LRU(int pageNum) throws Exception {//返回使用的物理页号
        if (pages[pageNum].status == 1) {//命中
            hit++;

            memoryStack.removeNode(memoryStack.getIndex(pageNum));//将命中节点弹出
            memoryStack.insertNode(0, pageNum);//将弹出的节点重新压入栈顶

            return pages[pageNum].phyAddress;
        } else {//未命中
            missing++;
            //pages[pageNum].setPageAddress(pageAddress);//设置页内地址
            pages[pageNum].setStatus(1);   //设置状态位
            return push(pageNum);//压栈
        }
    }

    public void applyPage(String alg) throws Exception {
        Scanner in = new Scanner(System.in);
        System.out.println("输入逻辑地址:");
        String logicHex = in.next();//输入16进制的地址
        int logic = Integer.parseInt(logicHex, 16);//将地址转换为int型
        try {
            int pageNum = logic / (blockLength * 1024);//地址除块长得到页号
            if (pageNum > getPageSize() - 1) {
                throw new Exception("地址越界!");
            }
            int pageAddress = logic % (blockLength * 1024);//地址对块长求余得到业内地址

            int phyPage;//得到真实使用的物理页号
            if (alg.equals("F")|| alg.equals("f") ) {
                phyPage = this.FIFO(pageNum);//
            } else {
                phyPage = this.LRU(pageNum);
            }

            int actualAddress = phyPage * (blockLength * 1024) + pageAddress;
            System.out.println("物理地址为:0x" + Integer.toHexString(actualAddress));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
