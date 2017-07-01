package 设备管理;

/**
 * Created by wyx11 on 2017-4-11.
 */
public class CHCT extends IONode{
    public CHCT() {
        super();
    }

    public String toString() {
        IONode node=this.getNext();//等于首元结点
        String str="";
        while (node != null) {
            str += "   " + node.getName() + "[" + node.getProcess() + "]{" + node.getWaitingDeque() + "}";
            node=node.getNext();
        }
        //str+="\n";
        return str;
    }
}
