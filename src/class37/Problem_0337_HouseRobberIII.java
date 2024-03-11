package class37;

// 337. 打家劫舍 III
// https://leetcode.cn/problems/house-robber-iii/description/
public class Problem_0337_HouseRobberIII {
    /**
     * 小偷又发现了一个新的可行窃的地区。这个地区只有一个入口，我们称之为 root 。
     * 除了 root 之外，每栋房子有且只有一个“父“房子与之相连。一番侦察之后，聪明的小偷意识到“这个地方的所有房屋的排列类似于一棵二叉树”。
     * 如果 两个直接相连的房子在同一天晚上被打劫 ，房屋将自动报警。
     * 给定二叉树的 root 。返回 在不触动警报的情况下 ，小偷能够盗取的最高金额 。
     */
    /**
     * 思路: 二叉树的递归套路
     * 
     * 体系学习班13 第4题
     */
    
    /**
     * 思路: 二叉树的递归套路
     * 以X为头的整棵树
     * 1. 与X无关的情况下(不选X)，能够盗取的最高金额来自于X下面的节点选或者不选，能够得到的最大值相加
     * 比如X下面有三个节点，a，b，c，不选X的情况下，能够获取的最大金额就是: max(不选a获取的最大金额，选a能够获取的最大金额)
     * max(不选a, 选a) + max(不选b, 选b) + max(不选c, 选c)
     * 
     * 2. 与X有关的情况下(选X)，选了X，X下面的第一层节点就不能选，能够获取的最大金额就是，不选第一层节点的情况下，能够获得的最大值相加，再加X
     * X + max(不选a) + max(不选b) + max(不选c)
     * 
     * 需要获取的信息:
     * 和X无关: 不选a能获取的最大金额，选a能获取的最大金额；不选b能获取的最大金额，选b能获取的最大金额；不选c能获取的最大金额，选c能获取的最大金额，
     * 和X有关: 不选a能获取的最大金额，不选b能获取的最大金额，不选c能获取的最大金额
     * 
     * 总结:
     * Info需要搜集:
     * 选某个节点能获取的最大金额
     * 不选某个节点能获取的最大金额
     */
    
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
                this.val = val;
                this.left = left;
                this.right = right;
        }
    }
    
    public class Info {
        public int noXMax; // 不选某个节点能获得的最大值
        public int yesXMax; // 选某个节点能获得的最大值
        
        public Info(int noXMax, int yesXMax) {
           this.noXMax = noXMax;
           this.yesXMax = yesXMax;
        }
    }
    public int rob(TreeNode root) {
        if(root == null) {
            return 0;
        }
        Info info = process(root);
        return Math.max(info.noXMax, info.yesXMax);
    }
    
    public Info process(TreeNode X) {
        // 如果是空树
        if(X == null) {
            return new Info(0, 0);
        }
        // 去左树搜集信息
        Info leftInfo = process(X.left);
        // 去右树搜集信息
        Info rightInfo = process(X.right);
        // 以X为头的正课数，构造出Info
        // 与X无关的情况下, X为头的整棵树，是左树选头结点或者不选头结点，右树选头结点，不选头结点，4者能取到的最大值相加
        int noXMax = Math.max(leftInfo.noXMax, leftInfo.yesXMax) +  Math.max(rightInfo.noXMax, rightInfo.yesXMax);
        // 与X有关的情况下, 选了X，左树和右树的头结点就不能选，分别能够获得的最大值相加
        int yesXMax = X.val + leftInfo.noXMax + rightInfo.noXMax;
        return new Info(noXMax, yesXMax);
    }
}
