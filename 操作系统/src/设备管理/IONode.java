package 设备管理;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Created by wyx11 on 2017-4-11.
 */
public class IONode {
    private String name;
    private IONode next;
    private IONode parent;
    private Process process;
    private Deque<Process> waitingDeque;

    public IONode() {
        name = null;
        next = null;
        parent = null;
        process = new Process();
        waitingDeque = new LinkedList<Process>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public IONode getNext() {
        return next;
    }

    public void setNext(IONode next) {
        this.next = next;
    }

    public IONode getParent() {
        return parent;
    }

    public void setParent(IONode parent) {
        this.parent = parent;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Deque<Process> getWaitingDeque() {
        return waitingDeque;
    }

    public void setWaitingDeque(Deque<Process> waitingDeque) {
        this.waitingDeque = waitingDeque;
    }

    @Override
    public boolean equals(Object obj) {


        IONode other = (IONode) obj;

        return other.getName() == this.getName()
                && other.getProcess().equals(this.getProcess())
                && other.getWaitingDeque().equals(this.getWaitingDeque());
    }

    public boolean processIsEmpty() {
        return this.process.isEmpty();
    }

    public void addIONode(IONode newNode, IONode parentNode) {//添加设备或控制器
        IONode node = this;
        while (node.next != null) {//遍历到最后一个节点
            node = node.next;
        }
        node.next = newNode;
        newNode.parent = parentNode;//与上一级相连
    }

    public void deleteIONode(String deleteName) {//删除设备或控制器
        IONode pNode = this;//pNode为删除节点的前节点
        IONode node;//node为要删除的节点
        while (pNode.next != null && !pNode.next.name.equals(deleteName)) {
            pNode = pNode.next;
        }
        //找到节点,将node指向pNode的next
        node = pNode.next;
        if (node == null) {//如果node为null说明删除的是末尾的节点
            pNode.next = null;
        } else {
            pNode.next = node.next;//将前节点指向删除后面的节点
        }
        node = null;
    }


    public IONode valueOfName(String otherName) {//在链表中查找是否有此名称的设备、控制器或通道,如果有返回此节点
        IONode node = this.next;
        while (node != null) {
            if (node.name.equals(otherName)) {
                return node;
            } else node = node.next;
        }
        return null;//未找到
    }


    public boolean isEmptyOfName(String otherName) {//在链表中查找是否有此名称的设备、控制器或通道,如果有返回false
        IONode node = this.next;
        while (node != null) {
            if (node.name.equals(otherName)) {
                return false;
            } else node = node.next;
        }
        return true;//未找到
    }

    public boolean isEmptyOfParent(IONode parentNode) {//遍历链表,返回是否有链接此节点
        IONode node = this.next;
        while (node != null && node.parent.equals(parentNode)) {//遍历比较是否有链接的节点
            return false;//找到,不为空
        }
        return true;//未找到,返回空
    }

    public void processToRun() {//将进程由阻塞态变为执行态
        try {
            if (!processIsEmpty()) {//检查当前节点进程是否为空
                throw new Exception("当前已有执行的进程");
            }
            if (waitingDeque.isEmpty()) {//检查阻塞队列是否为空
                throw new Exception("等待队列没有进程可用");
            }

            this.setProcess(waitingDeque.removeFirst());//将阻塞态出队,变为执行
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean processAutoToRun() {//检测当前节点为空时,且有等待队列,自动执行程序,成功return true;
        if (processIsEmpty() && !waitingDeque.isEmpty()) {
            //如果当前进程为空,且等待队列不为空
            processToRun();
            return true;
        }else
            return false;
    }

    public static void applyIONode(IONode node, Process process) {//申请
        node.waitingDeque.addLast(process);//将进程加入等待队列
        if (node.parent!= null && node.processIsEmpty()) {//如果node是通道parent=null,且不为空
            node.applyIONode(node.parent, process);//递归
        }
        node.processAutoToRun();//当前节点是否为空则执行
    }

    public static void releaseIONode(IONode node) {//释放
        try {
            if (node.getProcess()!=node.parent.parent.getProcess()) {//如果通道的进程和设备进程不同,则不释放
                throw new Exception("释放失败:该设备未启动");
            }
            while (node != null) {
                node.getProcess().setProcessName(null);//进程=null
                if(node.processAutoToRun()&&node.parent!=null)//如果成功将等待队列出队加入当前进程,且不是通道节点
                {
                    node.parent.waitingDeque.addLast(node.getProcess());//将出队的进程加到parent的队列
                }
                node=node.parent;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
   
    public String toString() {
        IONode node = this.next;//等于首元结点
        String str = "";
        while (node != null) {
            if (node.parent == null) {
                str += "   " + node.name + "(-null)" + "[" + node.process + "]{" + node.waitingDeque + "}";

            } else {

                str += "   " + node.name + "(-" + node.parent.name + ")" + "[" + node.process + "]{" + node.waitingDeque + "}";
            }
            node = node.next;
        }
        //str+="\n";
        return str;
    }
}