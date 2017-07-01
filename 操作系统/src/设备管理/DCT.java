package 设备管理;

/**
 * Created by wyx11 on 2017-4-11.
 */
public class DCT extends IONode {
    private String type;//设备类型

    public DCT() {
        super();
        type = null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public DCT valueOfType(String otherType) {//在链表中查找是否有此类型的空闲设备,如果有返回此设备,若果都忙,返回其中一个,如果没有返回null
        DCT node = (DCT)this.getNext();
        while (node != null) {
            if (node.getType().equals(otherType)&&node.getProcess().isEmpty()&&node.getParent().getParent().getProcess().isEmpty()) {//找到同类型空闲且可启动(通道空闲)
                return node;
            } else node = (DCT)node.getNext();
        }

        node = (DCT)this.getNext();//若不符合上面条件,重置节点
        while (node != null) {
            if (node.getType().equals(otherType)&&node.getProcess().isEmpty()) {//找到同类型且空闲
                return node;
            } else node = (DCT)node.getNext();
        }

        node = (DCT)this.getNext();//若不符合上面条件,重置节点
        while (node != null) {
            if (node.getType().equals(otherType)) {//找到同类型的第一个
                return node;
            } else node = (DCT)node.getNext();
        }
        return null;//未找到

    }

    public String toString() {
        DCT node = (DCT) this.getNext();//等于首元结点
        String str = "";
        while (node != null) {
            str += "   " + node.getName() + "<" + node.type + ">" + "(-" + node.getParent().getName() + ")" + "[" + node.getProcess() + "]{" + node.getWaitingDeque() + "}";
            node = (DCT) node.getNext();
        }
        return str;
    }
}
