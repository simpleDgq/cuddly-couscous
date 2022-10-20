package class05;

import java.util.Stack;

public class Code01_ConstructBinarySearchTreeFromPreorderTraversal {
    
    /**
     * 1008. 前序遍历构造二叉搜索树
     * 
     * https://leetcode.cn/problems/construct-binary-search-tree-from-preorder-traversal/
     */
    // 不用提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode() {
        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
    
    /*
     * 递归
     * 
     * [5,3,1,2,4,7,6,9,8]
     * 
     * 定义函数f(L, R): 从L..R范围是一棵树的先序遍历结果, 请建出整颗树把头部node返回
     * 
     * 上面的例子: 
     * 主函数f(0, 8) --> 5是头节点， 比5小的数都是左子树 -> f(1, 4)建好左子树 -> 剩下的是右子树-> f(5, 8)
     * 
     * 
     * 时间复杂度分析: 在找第一个比pre[L]大的数的时候，最差情况:
     *[5,4,3,2,1]
     *来到5，找比5大的第一个数，比较4次
     *来到4，找比4大的第一个数，比较3次
     *... 比较次数， O(N^2)
     *
     *优化点: 
     *1. 使用二分法找左右子树的分界线。右边第一个比它大的数，N个数，每个数找的时候都是logN，所以可以O(NlogN)做到
     *2. 使用单调栈 -> 寻找一个数的左边或者右边，比它大或者小的第一个数，O(N)可以做到 ==> 最优解法
     */
    public static TreeNode bstFromPreorder(int preorder[]) {
        if(preorder == null || preorder.length == 0) {
            return null;
        }
        return process(preorder, 0, preorder.length - 1);
    }
    
    private static TreeNode process(int preorder[], int L, int R) {
        if(L > R) { // 如果没有左子树，这时候process(preorder, L + 1, firstBig - 1) 建左子树的时候， L + 1 > firstBig - 1。
                        // 刚好没有左子树，不需建，直接返回
            //右子树同理
            return null;
        }
        
        TreeNode head = new TreeNode(preorder[L]);
        // 找到左树的结束下标 (第一个比preorder[L]大的位置)
        int firstBig = L + 1;
        for(;firstBig <= R; firstBig++) {
            if(preorder[firstBig] > preorder[L]) {
                break;
            }
        }
        // 递归去吧!
        head.left = process(preorder, L + 1, firstBig - 1);// 建左子树
        head.right = process(preorder, firstBig, R); // 建右子树
        return head;
    }
    
    // 时间复杂度: O(N)
    // 用单调栈，生成辅助数组 记录每个元素，比它大的，离它最近的元素的位置
    public static TreeNode bstFromPreorder2(int preorder[]) {
        if(preorder == null || preorder.length == 0) {
            return null;
        }
        
        int N = preorder.length;
        int nearBig[] = new int[N];
        
        for(int i = 0; i <= N -1; i++) {
            nearBig[i] = -1;
        }
        
        Stack<Integer> stack = new Stack<Integer>();
        for(int i = 0; i <= N - 1; i++) {
            while(!stack.isEmpty() && preorder[i] > preorder[stack.peek()]) { // 当前元素比栈顶元素大，需要出栈，使栈顶元素出栈的元素，就是栈顶元素右边第一个比它大的元素
                nearBig[stack.pop()] = i;
            }
            stack.push(i); // 入栈是 push 而不是add
        }
        
        return process2(preorder, 0, N - 1, nearBig);
    }
    
    
    public static TreeNode process2(int preorder[], int L, int R, int nearBig[]) {
        if(L > R) {
            return null;
        }
        
        TreeNode head = new TreeNode(preorder[L]);
        int firstBig = (nearBig[L] == -1 || nearBig[L] > R) ? R + 1 :  nearBig[L];
        head.left = process2(preorder, L + 1, firstBig - 1, nearBig);
        head.right = process2(preorder, firstBig, R, nearBig);
        return head;
    }
    
    
    // 空间复杂度，继续优化，使用数组代替栈
    public static TreeNode bstFromPreorder3(int preorder[]) {
        if(preorder == null || preorder.length == 0) {
            return null;
        }
        
        int N = preorder.length;
        int nearBig[] = new int[N];
        
        for(int i = 0; i <= N -1; i++) {
            nearBig[i] = -1;
        }
        
        int stack[] = new int[N];
        int size = -1;
        for(int i = 0; i <= N - 1; i++) {
            while(size != -1 && preorder[i] > preorder[stack[size]]) {
                nearBig[stack[size--]] = i;
            }
            stack[++size] = i; // 入栈是push, 而不是add
        }
        
        return process2(preorder, 0, N - 1, nearBig);
    }
}
