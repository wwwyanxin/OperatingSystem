package 设备管理;

/**
 * Created by wyx11 on 2017-4-13.
 */
public class Process {
    private String processName;

    Process() {
        processName = null;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessName() {
        return processName;
    }

    @Override
    public String toString() {
        return processName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == null && obj == null) return true;
        Process other = (Process) obj;
        if (this.processName == null && other.processName == null) return true;
        return this.processName.equals(other.processName);
    }

    public boolean isEmpty() {
        return processName==null?true:false;
    }

    public static void main(String[] args) {
        Process p=new Process();
        System.out.println(p);
    }
}
