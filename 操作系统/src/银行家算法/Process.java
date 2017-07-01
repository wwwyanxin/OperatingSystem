package 银行家算法;

/**
 * Created by wyx11 on 2017-5-5.
 */
public class Process {
    private int[] Request;//进程请求资源
    private boolean Finish;//是否完成

    Process(int ResNum) {
        Request=new int[ResNum];
        Finish=false;
    }

    public int getRequestOfIndex(int index) {//返回所请求的资源大小
        return Request[index];
    }

    public void setResOfIndex(int index,int res) {
        Request[index]=res;
    }

    public int[] getRequest() {
        return Request;
    }

    public void setRequest(int[] request) {
        Request = request;
    }

    public boolean isFinish() {
        return Finish;
    }

    public void setFinish(boolean finish) {
        Finish = finish;
    }
}
