package 文件管理;

/**
 * Created by wyx11 on 2017-4-29.
 */
public class Dir extends MTreeNode<File> {

    private static StringBuilder currentPosition;

    static {
        currentPosition=new StringBuilder("root:");
    }

    public void init() {
        File newFile = new File();
        newFile.setAttribute("root", File.BLOCK_SIZE, newFile.getFirstEmptyBlock(), 2);//设置根目录属性
        newFile.setFatBitView();//设置fat表

        this.setData(newFile);
    }


    public void addFile(String name, int size, int type) throws Exception {
        //if()
        File newFile = new File();
        newFile.setAttribute(name, size, newFile.getFirstEmptyBlock(), type);//设置属性
        if (!this.isEmptyOfValue(newFile)) {
            throw new Exception("添加失败,已有同名文件");
        }
        newFile.setFatBitView();//设置fat表

        this.addChild(newFile);
    }

    public void addChild(File data) {//添加孩子
        Dir newNode = new Dir();
        newNode.setData(data);
        newNode.setParent(this);
        newNode.setDepth(this.getDepth() + 1);//深度比父节点深1度
        this.getChildList().addNode(newNode);
    }

    public void removeDirectory(String name) throws Exception {
        Dir dir = this;
        File tempFile = new File(name, -1);
        if (this.isEmptyOfValue(tempFile)) {
            throw new Exception("未找到此目录");
        } else {
            dir = (Dir) this.getNodeOfChildValue(tempFile);
            if (dir.getData().getType() != 2) {
                throw new Exception(name + "不是一个目录文件");
            } else if (!dir.getChildList().isEmpty()) {
                throw new Exception(name + "此目录不为空");
            } else {
                dir.getData().releaseFatBitView();
                this.deleteChildOfValue(dir);
            }
        }
    }

    public void deleteFile(String name) throws Exception {
        Dir dir = this;
        File tempFile = new File(name, -1);
        if (this.isEmptyOfValue(tempFile)) {
            throw new Exception("未找到此文件");
        } else {
            dir = (Dir) this.getNodeOfChildValue(tempFile);
            if (dir.getData().getType() != 1) {
                throw new Exception(name + "不是一个文件");
            } else {
                dir.getData().releaseFatBitView();
                this.deleteChildOfValue(dir);
            }
        }
    }

    public Dir changeDirectory(String name) throws Exception {
        Dir dir = this;
        if (name.equals(".") || name.equals("..") && this.getParent() == null) {
            return dir;//本目录
        } else if (name.equals("..") && this.getParent() != null) {
            //上级目录
            dir = (Dir) this.getParent();
            if (dir.getParent() == null) {//如果是根目录
                currentPosition.replace(currentPosition.length() - dir.getData().getName().length(), currentPosition.length(), "");

            } else {
                currentPosition.replace(currentPosition.length() - dir.getData().getName().length()-1, currentPosition.length(), "");
            }
        } else {
            File tempFile = new File(name, -1);
            if (this.isEmptyOfValue(tempFile)) {
                throw new Exception("未找到此目录");
            } else {
                dir = (Dir) this.getNodeOfChildValue(tempFile);
                if (dir.getData().getType() != 2) {
                    throw new Exception(name + "不是一个目录文件");
                }
                currentPosition.append("\\").append(dir.getData().getName());
            }
        }
        return dir;
    }

    public void showTree() throws Exception {
        String preStr = "";
        for (int i = 0; i < this.getDepth(); i++) {
            preStr += "\t";
        }

        for (int i = 0; i < this.getChildList().getSize(); i++) {
            Dir t = (Dir) this.getChildOfIndex(i);
            System.out.print(preStr + "├" + t.getData().getSimInf());

            if (!t.getChildList().isEmpty()) {
                t.showTree();
            }
        }
    }

    public void showDir() throws Exception {
        int directorySum=0;
        int fileSum=0;
        if (this.getParent() != null) {//如果不是根目录
            directorySum+=2;
            StringBuilder sb = new StringBuilder();
            sb.append(this.getData().getDateTime());//时间
            sb.append("\t\t");
            sb.append("<DIR>\t");//类型为目录
            sb.append(".");
            sb.append("\n");

            sb.append(((Dir)this.getParent()).getData().getDateTime());//时间
            sb.append("\t\t");
            sb.append("<DIR>\t");//类型为目录
            sb.append("..");
            sb.append("\n");
            System.out.println(sb);
        }
        for (int i = 0; i < this.getChildList().getSize(); i++) {
            Dir t = (Dir) this.getChildOfIndex(i);
            if (t.getData().getType() == File.DIRECTORY_TYPE) {
                directorySum++;
            } else {
                fileSum++;
            }
            System.out.print(t.getData());
        }
        System.out.println("\t\t\t" + fileSum+"个文件\n" +
                "\t\t\t" + directorySum + "个目录");
    }

    public StringBuilder getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(StringBuilder currentPosition) {
        this.currentPosition = currentPosition;
    }
}
