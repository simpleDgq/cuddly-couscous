package class35;

// 687. 最长同值路径
// https://leetcode.cn/problems/longest-univalue-path/description/
public class Problem_0687_LongestUnivaluePath {
    /**
     * 给定一个二叉树的 root ，返回 最长的路径的长度 ，这个路径中的 每个节点具有相同值 。 这条路径可以经过也可以不经过根节点。
     * 
     * 两个节点之间的路径长度 由它们之间的边数表示。
     */
    
    /**
     * 思路:
     * 二叉树递归套路
     * 
     * 1. 和X无关
     *      1) 最长同值路径在左子树上
     *      2) 最长同值路径在右子树上
     *      哪个大，哪个就是答案
     * 2. 和X有关
     *      1) X自己
     *      2) 路径从x开始往左延伸
     *      3) 路径从x开始往右延伸
     *      4) 路径从x往左右两边延伸
     *      第4种情况时，其实2和3情况都是满足的，直接用2和3的长度相加就能得到4的答案。
     * 
     *  需要的info:
     *  左树上不要求必须从根结点出发的情况下最大路径（路径可以过根节点，也可以不过根节点）
     *  右树上不要求必须从根结点出发的情况下最大路径
     *  +
     *  左树上必须从根结点出发的最大路径（路径必须过根节点，并且起点是当前的根节点）
     *  右树上必须从根结点出发的最大路径
     *  
     *  所以:
     *  搜集
     *  1.不从X出发的情况下最大路径
     *  2.必须从X出发的情况下最大路径
     *  
     */
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int v) {
            val = v;
        }
    }
    
    public class Info {
        int len; //路径必须从x出发且只能往下走的情况下，路径的最大距离
        int max; //路径不要求必须从x出发的情况下，整棵树的合法路径最大距离
        
        Info(int len, int max) {
            this.len = len;
            this.max = max;
        }
    }
    
    public int longestUnivaluePath(TreeNode root) {
        if(root == null) {
            return 0;
        }
        Info info = process(root);
        return info.max - 1;
    }
    
    public Info process(TreeNode X) {
       if(X == null) { // 空树
           return new Info(0, 0);
       }
       
       TreeNode leftNode = X.left;
       TreeNode rightNode = X.right;
       // 左树上，不要求从左孩子出发，最大路径
       // 左树上，必须从左孩子出发，往下的最大路径
       Info leftInfo = process(leftNode);
       // 右树上，不要求从右孩子出发，最大路径
       // 右树上，必须从右孩子出发，往下的最大路径
       Info rightInfo = process(rightNode);
       // 以X为头的整棵树，搞出Info
       // 1、以X为头的整棵树，必须从x出发的情况下，往下的最大路径
       // 初始路径只有x一个节点，len初始化为0
       int len = 1;
       // 先去看x的左子树的根节点，val是不是和x一样，如果一样就可以连成一条路径，更新len
       if(leftNode != null && X.val == leftNode.val) {
           len = leftInfo.len + 1;
       }
       // 如果有右子树，且右子树的节点和X节点值一样
       if(rightNode != null && X.val == rightNode.val) {
           len = Math.max(len, rightInfo.len + 1);
       }
       
       // 2、以X为头的整棵树，不要求必须从x出发（路径可以路过x，也可以不路过x），求最大路径
       // len：从x出发，只往一侧向下延伸的最大同值路径长度
       // Math.max(leftInfo.max, rightInfo.max)：以x为根的树上，不路过x节点的最大同值路径长度
       // 上面两者取最大值
       int max = Math.max(len, Math.max(leftInfo.max, rightInfo.max));
       // 然后再去看是不是可以左右两个子树的路径都可以连接到x，这样整个路径就是从左子树到x再到右子树，计算这种情况的路径长度
       if (leftNode != null && rightNode != null && leftNode.val == X.val && rightNode.val == X.val) {
           // 与max比较，尝试推高max
           max = Math.max(max, leftInfo.len + rightInfo.len + 1);
       }
       return new Info(len, max);
    }
}
