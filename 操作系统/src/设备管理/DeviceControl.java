package 设备管理;

import java.util.Scanner;

/**
 * Created by wyx11 on 2017-4-11.
 */
public class DeviceControl {
    private CHCT chct;//通道
    private COCT coct;//控制器
    private DCT dct;//设备控制表
    //private enum Type{CHCT,COCT,DCT}

    public DeviceControl() {
        chct = new CHCT();
        coct = new COCT();
        dct = new DCT();

    }

    public CHCT getChct() {
        return chct;
    }

    public void setChct(CHCT chct) {
        this.chct = chct;
    }

    public COCT getCoct() {
        return coct;
    }

    public void setCoct(COCT coct) {
        this.coct = coct;
    }

    public DCT getDct() {
        return dct;
    }

    public void setDct(DCT dct) {
        this.dct = dct;
    }


    public String toString() {
        String str = "";
        str += "系统\n";
        str += "通道:\t" + chct.toString() + "\n";
        str += "控制器:\t" + coct.toString() + "\n";
        str += "设备:\t" + dct.toString();

        return str;
    }

    public void init() {//初始化通道,控制器,设备
        initIONode(chct, "CH1");
        initIONode(chct, "CH2");

        CHCT chTarget;
        chTarget = (CHCT) chct.valueOfName("CH1");
        initIONode(coct, "CO1", chTarget);
        chTarget = (CHCT) chct.valueOfName("CH2");
        initIONode(coct, "CO2", chTarget);
        initIONode(coct, "CO3", chTarget);

        COCT coTarget;
        coTarget = (COCT) coct.valueOfName("CO1");
        initIONode(dct, "K", coTarget,"USB");
        initIONode(dct, "M", coTarget,"USB");
        coTarget = (COCT) coct.valueOfName("CO2");
        initIONode(dct, "T", coTarget,"HDMI");//显示器
        coTarget = (COCT) coct.valueOfName("CO3");
        initIONode(dct, "P", coTarget,"USB");//打印机


    }

    private void initIONode(CHCT Channel, String name) {//初始化通道
        CHCT newChannel = new CHCT();
        newChannel.setName(name);

        Channel.addIONode(newChannel, null);
    }

    private void initIONode(COCT Controller, String name, CHCT parent) {//重载:初始化控制器
        COCT newController = new COCT();
        newController.setName(name);

        Controller.addIONode(newController, parent);
    }

    private void initIONode(DCT Device, String name, COCT parent,String type) {//重载:初始化设备
        DCT newDevice = new DCT();
        newDevice.setName(name);
        newDevice.setType(type);

        Device.addIONode(newDevice, parent);
    }


    public void addDevice() {//添加设备

        Scanner in = new Scanner(System.in);
        System.out.println("输入添加设备的名称:");
        String name = in.next();
        if (!dct.isEmptyOfName(name)) {
            System.out.println("添加失败,已有同名设备");
        } else {
            DCT newDevice = new DCT();//新建设备
            newDevice.setName(name);
            System.out.println("输入设备类型:");
            String type=in.next();
            newDevice.setType(type);

            System.out.println("选择连接到哪个控制器?(若输入的控制器不存在则新建此控制器):");
            String controller = in.next();
            COCT coTarget;//目标控制器
            try {
                if (coct.isEmptyOfName(controller)) {
                    System.out.println("未找到此控制器,建立新的控制器: " + controller);
                    coTarget = addController(controller);
                } else {
                    coTarget = (COCT) coct.valueOfName(controller);//返回目标控制器
                }
                dct.addIONode(newDevice, coTarget);//将设备与控制器相连
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public COCT addController(String controllerName) throws Exception {//添加控制器
        Scanner in = new Scanner(System.in);
        /*System.out.println("输入添加控制器的名称:");
        String name = in.next();*/
        if (!dct.isEmptyOfName(controllerName)) {
            throw new Exception("错误:已有此控制器");
        } else {
            COCT newController = new COCT();
            newController.setName(controllerName);

            System.out.println("选择连接到哪个通道?");
            String channel = in.next();
            CHCT chTarget;//目标通道
            if (chct.isEmptyOfName(channel)) {
                throw new Exception("错误:未找到此通道");
            } else {
                chTarget = (CHCT) chct.valueOfName(channel);//返回目标通道
            }
            coct.addIONode(newController, chTarget);//将控制器与通道相连
            return newController;
        }
    }

    public void deleteDevice() {//删除设备
        Scanner in = new Scanner(System.in);
        System.out.println("输入删除设备的名称:");
        String deviceName = in.next();
        if (dct.isEmptyOfName(deviceName)) {
            System.out.println("错误:未找到要删除的设备");
        } else {
            DCT device = (DCT) dct.valueOfName(deviceName);//返回要删除的设备
            IONode parentNode = device.getParent();//获得控制器节点
            if (!device.processIsEmpty()) {//如果当前设备有进程使用,则不允许删除设备
                System.out.println("删除失败:当前有进程使用此设备");
            }else {
                dct.deleteIONode(deviceName);//将设备删除
                if (dct.isEmptyOfParent(parentNode)) {//如果没有链接此parent的设备,则将设备删除
                    coct.deleteIONode(parentNode.getName());
                }
            }
        }
    }

    public void applyDevice() {
        //进程申请设备
        Scanner in = new Scanner(System.in);
        System.out.println("输入进程名:");
        String processName=in.next();
        Process process=new Process();//创建一个新进程
        process.setProcessName(processName);//设置进程名
        System.out.println("输入申请使用的设备:");
        String deviceName=in.next();
        DCT device = (DCT) dct.valueOfName(deviceName);//查找到申请的设备
        DCT.applyIONode(device,process);//申请
    }

    public void releaseDevice() {//释放设备
        Scanner in = new Scanner(System.in);
        System.out.println("输入释放的设备:");
        String deviceName=in.next();
        DCT device=(DCT)dct.valueOfName(deviceName);
        DCT.releaseIONode(device);
    }

    public void deviceIndependent() {//设备独立性
        //进程申请设备
        Scanner in = new Scanner(System.in);
        System.out.println("输入进程名:");
        String processName=in.next();
        Process process=new Process();//创建一个新进程
        process.setProcessName(processName);//设置进程名
        System.out.println("输入申请使用设备的类型:");
        String deviceType=in.next();
        DCT device = (DCT) dct.valueOfType(deviceType);//查找到申请的同类型设备
        if (device == null) {
            System.out.println("未找到此类型的设备");
        }else {
            DCT.applyIONode(device, process);//申请
        }
    }


}
