package class07;

//本题测试链接 : https://leetcode.com/problems/binary-tree-cameras/
public class Code02_MinCameraCover {
    
    /*
     * 给定一棵二叉树的头节点head，如果在某一个节点x上放置相机，那么x的父节点、
     * x的所 有子节点以及x都可以被覆盖。返回如果要把所有数都覆盖，至少需要多少个相机。
     */
    
    /*
     * 二叉树递归讨论
     * 可能性分析(背住完事了)
        以x为头的树， 返回值有3中情况：
        1） x上面无相机， 但是x是被覆盖的， 而且x底下所有节点都被覆盖了
              在这种情况下，请问需要几个相机
        2）x上面有相机，x是被覆盖的， 而且x底下所有节点都被覆盖了
              在这种情况下，请问需要几个相机
        3) x既无相机， 也没被覆盖， x底下所有节点都被覆盖了
              在这种情况下，请问需要几个相机
              
              实际想法的问题:
              二叉树递归套路是说，如果我们想以x为头的整棵树都被覆盖了，需要哪些可能性？
                那就两种可能性，
                第1种可能性就是x上面有相机，但是它被覆盖了，
                第2种可能就是x上面无相机，但它被覆盖了
                
                会少一种可能性(图上的例子，头节点没有被覆盖，左右孩子都没有相机，只需要X放一个就行了，这种情况会漏掉)
                这种情况下你的这个两种可能性是不够的，收集信息的时候，我确实需要我的左树告诉我，
                它没被覆盖，但它底下都被覆盖的相机数量，因为我这儿放一个相机是可以补救他们的
     */
    public static class Info {
        // fuck : 用long LeetCode就会报错。数据这垃圾
        public long uncovered; // x既无相机， 也没被覆盖， x底下所有节点都被覆盖了, 在这种情况下，请问需要几个相机
        public long coveredWithCamera; // x上面有相机，x是被覆盖的， 而且x底下所有节点都被覆盖了, 在这种情况下，请问需要几个相机
        public long coveredNoCamera; // x上面无相机，x是被覆盖的， 而且x底下所有节点都被覆盖了, 在这种情况下，请问需要几个相机
        
        public Info(long uc, long cwc, long cnc) {
            uncovered = uc;
            coveredWithCamera = cwc;
            coveredNoCamera = cnc;
        }
    }

    public static int minCameraCover(TreeNode root) {
        if(root == null) {
           return 0; 
        }
        Info info = process(root);
        // X没有被cover，所以要加1，
        // 三中可能性中取最小值
        return (int)Math.min(info.uncovered + 1, Math.min(info.coveredNoCamera, info.coveredWithCamera));
    }
    
    public static Info process(TreeNode X) {
        if(X == null) {
            // 如果X是空树
            // 空树可以认为是直接被覆盖的，也不需要放相机，所以uncovered和coveredWithCamera 都是无穷大
            // coveredNoCamera 是0，不需要相机
           return new Info(Integer.MAX_VALUE, Integer.MAX_VALUE, 0); 
        }
        // 左树搜集信息
        Info left = process(X.left);
        // 右树搜集信息
        Info right = process(X.right);
        // 生成X节点相关信息
        // X节点不被covered，也没放相机，x左右节点都被cover--> 需要左树和右树都coveredNoCamera
        long uncovered = left.coveredNoCamera + right.coveredNoCamera;
        
        // X节点被覆盖，而且放上相机
        // 左节点可以不被覆盖uncovered，也可以coveredWithCamera，也可以coveredNoCamera, 三者取最小
        // 右节点可以不被覆盖uncovered，也可以coveredWithCamera，也可以coveredNoCamera, 三者取最小
        // 1 表示X放上相机
        long coveredWithCamera = 1 + Math.min(left.uncovered, Math.min(left.coveredNoCamera, left.coveredWithCamera))
                        +  Math.min(right.uncovered, Math.min(right.coveredNoCamera, right.coveredWithCamera));
        
        // X节点被覆盖，而且无相机
        // 左右节点中至少有一个放上相机，而且被覆盖
        long coveredNoCamera = 
                Math.min(left.coveredNoCamera + right.coveredWithCamera,
                        Math.min(left.coveredWithCamera + right.coveredNoCamera, 
                                left.coveredWithCamera + right.coveredWithCamera));
        return new Info(uncovered, coveredWithCamera, coveredNoCamera);   
    }
    
    public static class TreeNode {
        public int value;
        public TreeNode left;
        public TreeNode right;
    }
    
    
    /*
     * 上面的解法可以兜底
     * 下面的解法，更加简单
     */
    // 以x为头，x下方的节点都是被covered，x自己的状况，分三种
    public static enum Status {
       UNCOVERED, COVERED_WITH_CAMRRA, COVERED_NO_CAMRRA
    }
    
    // 以x为头，x下方的节点都是被covered，得到的最优解中：
    // x是什么状态，在这种状态下，需要至少几个相机
    public static class Data {
        public Status status;
        public int cameras;

        public Data(Status status, int cameras) {
            this.status = status;
            this.cameras = cameras;
        }
    }
    
    public static int minCameraCover2(TreeNode root) {
        if(root == null) {
           return 0; 
        }
        Data info = process1(root);
        // 如果X节点是没被覆盖的状态，需要放一个相机
        return info.cameras + ((info.status == Status.UNCOVERED) ? 1 : 0);
    }
    
    public static Data process1(TreeNode X) { // 背
        if(X == null) {
          // 空树， 自动被覆盖，不需要相机，相机数是0
          return new Data(Status.COVERED_NO_CAMRRA, 0);  
        }
        // 左树搜集信息
        Data left = process1(X.left);
        // 右树搜集信息
        Data right = process1(X.right);
        
        int cameras = left.cameras + right.cameras;
        // 左孩子和右孩子，哪怕一个UNCOVERED
        // X就需要放上一个相机，状态是COVERED_WITH_CAMRRA
        if(left.status == Status.UNCOVERED || right.status == Status.UNCOVERED) {
            return new Data(Status.COVERED_WITH_CAMRRA, cameras + 1);
        }
        // 左孩子和右孩子，哪怕一个COVERED_WITH_CAMRRA
        if(left.status == Status.COVERED_WITH_CAMRRA || right.status == Status.COVERED_WITH_CAMRRA) {
            return new Data(Status.COVERED_NO_CAMRRA, cameras);
        }
        // 左右孩子，不存在没被覆盖的情况，也都没有相机
        return new Data(Status.UNCOVERED, cameras);
    }
}
