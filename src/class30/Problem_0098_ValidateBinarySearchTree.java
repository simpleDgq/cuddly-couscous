package class30;

// 98. 验证二叉搜索树
public class Problem_0098_ValidateBinarySearchTree {
    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
    }

    // 使用Morris遍历-> 装逼用
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        TreeNode cur = root;
        TreeNode mostRight = null;
        Integer pre = null;
        boolean ans = true;
        while (cur != null) {
            mostRight = cur.left;
            if (mostRight != null) {
                while (mostRight.right != null && mostRight.right != cur) {
                    mostRight = mostRight.right;
                }
                if (mostRight.right == null) {
                    mostRight.right = cur;
                    cur = cur.left;
                    continue;
                } else {
                    mostRight.right = null;
                }
            }
            if (pre != null && pre >= cur.val) {
                ans = false;
            }
            pre = cur.val;
            cur = cur.right;
        }
        return ans;
    }
    
    // 使用二叉树递归套路
    public boolean isValidBST2(TreeNode root) {
        return process(root).isBST;
    }

    // 搜集三个信息，是否是搜索二叉树，左右子树的max值和min值
    public static class Info {
        boolean isBST;
        int max;
        int min;
        public Info(boolean isBST, int max, int min) {
            this.isBST = isBST;
            this.max = max;
            this.min = min;
        }
    }
    
    // 返回Info给X节点
    public static Info process(TreeNode X) {
        if(X == null) {
            // return new Info(true, ?, ?)
            return null; // 如果是空树，空树是搜索二叉树。但是max值和min值不好定义，所以直接返回null
        }
        // 去左子树带回Info
        Info leftInfo = process(X.left);
        // 去右子树带回Info
        Info rightInfo = process(X.right);
        
        // 求左子树的最大值、最小值
        int max = X.val;
        int min = X.val;
        if(leftInfo != null) {
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
        }
        // 求右子树的最大值、最小值
        if(rightInfo != null) {
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }
        
        // 判断整棵树是不是搜索树。 假设整棵树是搜索二叉树，只需要排除掉不是搜索树的情况就行
        boolean isBST = true;
        if(leftInfo != null && !leftInfo.isBST) { // 左子树不是搜索树，那么整棵树一定不是
            isBST = false;
            return new Info(isBST, max, min); // 直接返回
        }
        if(rightInfo != null && !rightInfo.isBST) { // 右子树不是搜索树，那么整棵树一定不是
            isBST = false;
            return new Info(isBST, max, min); // 直接返回
        }
        
        // 左右子树都是搜索二叉树，那么需要继续判断：左树的最大值要比X小，右树的最小值要比X大
        boolean leftMaxLessX = leftInfo == null ? true : (leftInfo.max < X.val); // 当leftInfo为null的时候，表示左子树为空树，当然比X小，直接返回true
        boolean rightMinMoreX = rightInfo == null ? true : (rightInfo.min > X.val);
        isBST = leftMaxLessX && rightMinMoreX;
        
        return new Info(isBST, max, min);
    }
}
