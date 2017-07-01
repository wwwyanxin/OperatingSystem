package 分页式存储管理;

import 文件管理.File;
import 文件管理.MTreeNode;

/**
 * Created by wyx11 on 2017-3-30.
 */

public class LinkedList<T> {




    class Node{
        T data;
        Node next;

        public Node(T data, Node next) {//结点初始化
            this.data=data;
            this.next=next;
        }

        public boolean hasNext() {
            if (this.next == null) {
                return false;
            }
            return true;
        }

        public T getNodeElement() {
            return data;
        }
    }

    private Node head;//头结点
    private int size=0;

    public LinkedList(){
        head=new Node(null,null);//初始化头结点
    }



    //在链表末尾添加结点
    public void addNode(T element) {

            Node pNode=this.head;
        while (pNode.hasNext()) {
            pNode=pNode.next;
        }
        Node node = new Node(element, null);
        pNode.next=node;
        size++;


    }


    //通过链表位置索引获取结点
    public Node getNode(int index)throws Exception {
        int k=0;
        Node pNode;
        if (index == -1) {//如果获取-1则返回头结点
            pNode=this.head;
            return pNode;
        } else {
            pNode = this.head.next;//pNode为首元结点
        }
        while (true) {

            if (k == index) {
                return pNode;
            }
            if (!pNode.hasNext()) {
                throw new Exception("索引为" + index + "的节点不存在");
            }
            pNode=pNode.next;
            k++;
        }
    }

    public <T > int getIndex(T emement) {
        Node pNode=head;
        int k=0;
        while (pNode.hasNext()) {//pNode.next.data.equals(emement)
            if (emement.equals(pNode.next.data) ){
                return k;
            } else {
                pNode=pNode.next;
                k++;
            }
        }
        return -1;//未找到
    }


            //返回指定下标元素
    public T getElementOfIndex(int index) throws Exception {
        return getNode(index).data;
    }

    public  <E extends File> T getElementOFValue(E data) {
        return getNodeOfValue(data).getNodeElement();//根据数据的值返回节点的元素
    }




    //在index位置的节点前,添加数据为element的节点
    public void insertNode(int index,T element)throws Exception {
        //`````!!!!!!!!!!!!!!!!!!!!!!!!!
        Node target;
        if (index == 0) {
            target=this.head;//如果要插入首元结点的位置
        }else {
            target = this.getNode(index - 1);//获取在该位置插入的前一个节点
        }
        if (!target.hasNext()) {//如果刚好在链表末尾
            this.addNode(element);//插入末尾

        }else{
            //如果不在链表末尾
            Node node = new Node(element, target.next);//加到target后面
            target.next=node;
            size++;
        }


    }
    //删除在位置index的节点
    public Node removeNode(int index)throws Exception {
        if (this.size == 0) {//只有头结点
            throw new Exception("链表为空,删除失败");
        }else{
            Node removeNode = getNode(index);
            Node previous = this.getNode(index - 1);//目标节点的前一个节点
            if (this.getNode(index).hasNext())//如果要删除的不是最后一个节点
            {
                Node next = this.getNode(index + 1);//目标节点的后一个节点
                previous.next=next;
            } else {//如果要删除的是最后一个节点
                previous.next=null;
            }

            size--;
            return removeNode;
        }
    }
/*    private <E> Node getNodeOfValue(E data) {
        return null;
    }*/
    public <E extends File> Node getNodeOfValue(E otherData) {//根据T的值获取结点
        Node node=this.head.next;//node等于首元结点
        //(MTreeNode)node.data).
        while (node != null) {
            E thisData=(E)((MTreeNode)node.data).getData();

            //node.data.e//((MTreeNode) node.data).getData().equals(otherData)
            if (otherData.equals(thisData)) {
                return node;
            } else {
                node=node.next;
            }
        }

        return null;
    }



    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }else
            return false;
    }

    public <E extends File> boolean isEmptyOfValue(E otherData) {
        Node node = this.head.next;//node等于首元结点
        //(MTreeNode)node.data).
        while (node != null) {
            E thisData=(E)((MTreeNode)node.data).getData();

            //node.data.e//((MTreeNode) node.data).getData().equals(otherData)
            if (otherData.equals(thisData)) {
                return false;
            } else {
                node = node.next;
            }
        }
        return true;
    }

    //遍历显示链表
    public void displayLinkedList() {
        Node pNode=this.head.next;
        while (true) {
            System.out.print(pNode.data+"->");
            if (pNode.hasNext()) {
                pNode=pNode.next;
            }else{
                break;
            }
        }
    }

    //遍历显示链表
    public String toString() {
        String str="--\n";
        Node pNode=this.head.next;
        while (head.hasNext()) {//如果不是空链表才进入循环体
            str+=pNode.data+"\n";//System.out.print(pNode.data+"->");
            if (pNode.hasNext()) {
                pNode=pNode.next;
            }else{
                break;
            }
        }
        str+="--";
        return str;
    }

    //获取链表大小
    public int getLinkedListSize() {
        return size;

    }
    //清空链表
    public void clearLinkedListSize() {
        this.head.next=null;
        size=0;
    }
}
