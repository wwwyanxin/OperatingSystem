package 文件管理;

import 分页式存储管理.LinkedList;

/**
 * Created by wyx11 on 2017-4-29.
 */
public class MTreeNode<E> {//多叉树
    private E data;
    private MTreeNode parent;
    private int depth;//当前节点的深度
    private LinkedList<MTreeNode> childList;

    public MTreeNode() {
        data = null;
        parent = null;
        childList = new LinkedList<>();
        depth = 0;
    }

    public void setParent(MTreeNode parent) {
        this.parent = parent;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public void setChildList(LinkedList<MTreeNode> childList) {
        this.childList = childList;
    }

    public void addChild(E data) {//添加孩子
        MTreeNode newNode = new MTreeNode();
        newNode.data = data;

        newNode.parent = this;
        newNode.depth = this.depth+1;//深度比父节点深1度
        this.childList.addNode(newNode);
    }

    public <E> void deleteChildOfValue(E data) throws Exception {
        int index = childList.getIndex(data);
        if (index == -1) {
            throw new Exception("删除出错,未找到该节点");
        } else {
            childList.removeNode(index);//删除此节点
        }
    }

    public  <E extends File>MTreeNode getNodeOfChildValue(E data) {
        return childList.getElementOFValue(data);
    }


    public <E extends File> boolean isEmptyOfValue(E data) {
        return childList.isEmptyOfValue(data);
    }

    public boolean equals(MTreeNode otherNode) {
        return this.data.equals(otherNode.data);
    }

    public E getData() {
        return data;
    }

    /*    public String toString() {
                StringBuilder sb = new StringBuilder();
                MTreeNode node=this;
                sb.append(data).append("\n");
                for (int i = 0; i < node.depth; i++) {
                    sb.append("└").append()
                }
            }*/

    public MTreeNode getParent() {
        return parent;
    }

    public LinkedList<MTreeNode> getChildList() {
        return childList;
    }

    public MTreeNode getChildOfIndex(int index) throws Exception {
        return childList.getElementOfIndex(index);
    }

    public void setData(E data) {
        this.data = data;
    }

}
