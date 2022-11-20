package class14;

//本题测试链接 : https://leetcode.cn/problems/count-complete-tree-nodes/
public class Code03_CompleteTreeNodeNumber {
    /**
     * 给定一个棵完全二叉树，返回这棵树的节点个数，要求时间复杂度小于O(树的节点数)
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }
    /**
     * 思路:
     * 
     * 来到一个节点，看它右树上的最左节点到达了第几层，如果没有到达最后一层，说明当前节点的右树是满二叉树，计算节点数量，
     * 左树去搞递归；
     * 如果到达了最后一层，说明当前节点的左树是满二叉树，计算节点数量，右树去搞递归。
     * 
     * 例子: 
     * 1）完全二叉树的层数是10，当前来到了一个节点，在第5层，如果它右树上的最左节点到达了第9层
     * , 没有到达最后一层第10层; 说明当前节点的右树是满二叉树，总共有10 - 5 - 1 = 4层， 满二叉树的节点数就是2^4 - 1, 要加上当前节点本身1，所以是2^4
     * 当前节点的左树的节点数，因为左树也是完全二叉树，调用递归直接去算；
     * 
     * 2) 如果来到了第9层，到达了第10层，说明当前节点的左树是满二叉树，总共有10 - 5 = 5层，节点数就是2^5 - 1, 要加上当前节点本身1，所以是2^5
     * 当前节点的右树的节点数，因为右树也是完全二叉树，调用递归直接去算。
     * 
     * 
     * 时间复杂度分析: O(logN^2)
     * 来到一个节点，不管往左走还是往右走，都得去遍历它右树的左边界的，遍历的次数在层数上是一个等差数量 --> O(L^2)
     * 
     * 层数 = longN
     * L = logN , 所以时间复杂度是O(logN^2)
     * 
     * 最差情况： 比如，总共右7层的一颗满二叉树，从第一层出发，右树上要遍历6个节点，算出左树的所有节点数之后;
     *         递归接着往下，继续来到右树节点，又遍历当前右树节点的右树的左边界，5个..
     *         依次类推，就是一个等差数列
     *         是层数的O(L^2)
     */
    public int countNodes(TreeNode root) {
        if(root == null) {
            return 0;
        }
        if(root.left == null && root.right == null) {
            return 1;
        }
        // 从root节点开始，所在的层是第1层，总共的层数是mostLeftLevel(root, 1)
        return bs(root, 1, mostLeftLevel(root, 1));
    }
    // node所在的层数是level, 整颗数的最大层数是h
    // 以node为头的整颗完全二叉树，求节点数量
    public int bs(TreeNode node, int level, int h) {
        if(level == h) { //  已经来到了最后一层，叶子节点，节点数就是1
            return 1;
        }
        // 没有来到最后一层，求当前节点的右树上的最左节点所在的层数
        int mostLeftLev = mostLeftLevel(node.right, level + 1);
        if(mostLeftLev != h) { // 如果没有到达最后一层，说明右树是满二叉树
            return (1 << (h - level - 1)) + bs(node.left, level + 1, h); // 注意这里是直接return
        } else { // 到达了最后一层，左树是满二叉树
            return ((1 << (h - level))) + bs(node.right, level + 1, h);
        }
    }  
    // node 在第level层，求node为头的整棵树，最大层数是多少
    // 一定是完全二叉树
    public int mostLeftLevel(TreeNode node, int level) {
        while(node != null) { // 不是null，一直往左，level++
            level++;
            node = node.left;
        }
        return level - 1; // 叶子节点的时候， 多加了一层，要减掉
    }
}
