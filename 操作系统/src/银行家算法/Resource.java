package 银行家算法;

/**
 * Created by wyx11 on 2017-5-5.
 */
public class Resource {
    private int[] Available;//可用资源
    private int[][] Max;//最大需求资源
    private int[][] Allocation;//分配资源
    private int[][] Need;//需求资源
    private Process processes[];//进程

    Resource(int[] Available, int[][] max, int[][] Allocation) {
        this.Available=Available;
        this.Max = max;
        this.Allocation=Allocation;
        Need=new int[max.length][max[0].length];
        for (int i = 0; i < max.length; i++) {  //Need[i][j]=Max[i][j]-Allocation[i][j];
            for (int j = 0; j < max[i].length; j++) {
                Need[i][j]= max[i][j]-Allocation[i][j];
                Available[j] -= Allocation[i][j];
            }
        }
        processes=new Process[max.length];
        for (int i = 0; i < processes.length; i++) {
            processes[i]=new Process(Available.length);
        }

    }

    public Process[] getProcesses() {
        return processes;
    }

    public void rollback(int i) {
        for (int j = 0; j < processes[i].getRequest().length; j++) {
            processes[i].setResOfIndex(j,0);
        }
    }

    public boolean AllocationRes(int i) throws Exception {//申请资源
            for (int j = 0; j < processes[i].getRequest().length; j++) {
                if (processes[i].getRequest()[j] > Need[i][j]) {
                    rollback(i);//回滚
                    throw new Exception("所请求的资源数已超出Need数");
                } else if (processes[i].getRequest()[j] > Available[j]) {
                    rollback(i);//回滚
                    return false;//没有足够的资源分配
                }else {
                    Available[j] -= processes[i].getRequest()[j];
                    Allocation[i][j] += processes[i].getRequest()[j];
                    Need[i][j] = Need[i][j] - processes[i].getRequest()[j];
                    processes[i].setResOfIndex(j,0);
                    
                }
            }
        boolean allFinish=true;
        for (int j = 0; j < processes[i].getRequest().length; j++) {
            if (Need[i][j] != 0) {
                allFinish=false;
                break;
            }
        }
        if(allFinish){//已分配所需全部资源
            for (int j = 0; j < processes[i].getRequest().length; j++) {
                Available[j]+=Allocation[i][j];
            }
            processes[i].setFinish(true);
            return true;
        }
        return false;
    }

    public void BanAlg() throws Exception {
        int flag=getProcessUnFinishedLength();//每完成一进程flag--;出现安全序列之后=0;
        int sum;//sum最多每个进程请求次数
        for(sum=processes.length;sum>0&&flag>0;sum--) {//sum最多每个进程请求次数
            for (int i = 0; i < processes.length; i++) {
                if(!processes[i].isFinish()){//未完成
                    for (int j = 0; j < processes[i].getRequest().length; j++) {
                        processes[i].setResOfIndex(j,Need[i][j]);//将Need的所需全部请求
                    }
                    if (AllocationRes(i)) {
                        flag--;
                        System.out.println("P["+i+"]");
                        //show(i);
                    }
                }
            }
        }
        if (sum == 0 && flag > 0) {
            throw new Exception("未获得安全序列");
        } else {
            System.out.println("已获得安全序列");
        }
    }

    public int getProcessUnFinishedLength() {
        int unFinish=0;
        for (int i = 0; i < processes.length; i++) {
            if (!processes[i].isFinish()) {
                unFinish++;
            }
        }
        return unFinish;
    }


    public int[] getAvailable() {
        return Available;
    }

    public void setAvailable(int[] available) {
        Available = available;
    }

    public int[][] getMax() {
        return Max;
    }

    public int[][] getAllocation() {
        return Allocation;
    }

    public void setAllocation(int[][] allocation) {
        Allocation = allocation;
    }

    public int[][] getNeed() {
        return Need;
    }

    public void setNeed(int[][] need) {
        Need = need;
    }
}
