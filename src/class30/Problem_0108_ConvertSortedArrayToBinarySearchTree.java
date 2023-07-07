package class30;

// 108. 将有序数组转换为二叉搜索树
public class Problem_0108_ConvertSortedArrayToBinarySearchTree {
    /**
     * 给你一个整数数组 nums ，其中元素已经按 升序 排列，请你将其转换为一棵 高度平衡 二叉搜索树。
     * 高度平衡 二叉树是一棵满足「每个节点的左右两个子树的高度差的绝对值不超过 1 」的二叉树。
     */
    
    /**
     * 思路:
     * 定义函数f(arr, L, R) 给定数组，在L和R范围上建好二叉搜索树，给我返回
     * 
     * 中点位置直接当做头结点，然后左右子树，分别在(L, M - 1) (M + 1, R)范围上搞
     * 建完挂在头结点上
     */
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }
    }
    
    public TreeNode sortedArrayToBST(int[] nums) {
        if(nums == null || nums.length == 0) {
            return null;   
        }
        return process(nums, 0, nums.length - 1);
    }
    
    public TreeNode process(int[] nums, int L, int R) {
        if(L < 0 || R > nums.length) {
            return null;
        }
        // 越界了
        if(L > R) {
            return null;
        }
        // 只有一个数了，建立好节点直接返回
        if(L == R) {
            return new TreeNode(nums[L]);
        }
        // 求中点
        int M = L + ((R - L) >> 1);
        // 中点建立成头
        TreeNode headNode = new TreeNode(nums[M]);
        // 分别建立左右子树
        headNode.left = process(nums, L, M - 1); 
        headNode.right = process(nums, M + 1, R); 
        return headNode;
    }
}
