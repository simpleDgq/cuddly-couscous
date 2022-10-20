package class05;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.xml.bind.DatatypeConverter;

public class Code02_LeftRightSameTreeNumber {
    
 // 如果一个节点X，它左树结构和右树结构完全一样，那么我们说以X为头的子树是相等子树
 // 给定一棵二叉树的头节点head，返回head整棵树上有多少棵相等子树
    
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int v) {
            value = v;
        }
    }
    
    //O(N*logN) ==> T(N) = 2*T(N/2) + O(N)  ==> master公式得出时间复杂度
    // 递归去做，计算左边有多少个相等 + 计算右边有多少个相等 + 整棵树是不是相等的，相等就加1，不相等加0
    public static int sameTreeNumber(Node head) {
        if(head == null) {
            return 0;
        }
        return sameTreeNumber(head.left) + sameTreeNumber(head.right) + (same(head.left, head.right) ? 1 : 0);
    }
    
    public static boolean same(Node h1, Node h2) {
        if(h1 == null ^ h2 == null) { // 一个等于null，一个不等于null
            return false;
        }
        if(h1 == null && h2 == null) { // 都等于null
            return true;
        }
        // 都不等于null
        return h1.value == h2.value && same(h1.left, h2.left) && same(h1.right, h2.right);
    }
    
    
    /*
     * 左树和右树对比，判断是否是相同的，最差情况，每个节点都要比较一次，时间复杂度O(N)
     * 
     * 能不能将O(N) 优化成O(1) --> 二叉树的先序序列，能够表示一颗二叉树。可以比较先序序列是否一样，来判断树是否相同。
     * --> 但是当树比较大的时候，先序序列也会趋近于N，时间复杂度还是O(N) --> 进一步想到hash，将不管多长的字符串都变成
     * 固定长度的，只需要判断Hash 值是否相等，时间复杂度变成了O(1)
     * 
     * 二叉树递归套路：
     * 以x为头的树, 
     *  需要左树, 右树一共有多少个相同子树
        还需要左树,右树的结构, 由一个hashcode表示
        对左树, 右树的要求是: 返回你相等子树的数量以及你结构的hashcode
     * 
     */
    
    // 搜集的信息
    public static class Info {
        public int ans;
        public String str; // hash
    
          public Info(int a, String s) {
              ans = a;
              str = s;
          }
    }
    // O(N)
    public static int sameTreeNum(Node head) {
        if(head == null) {
            return 0;
        }
        return process(head).ans;
    }
    
    public static Info process(Node X) {
        if(X == null) {
            return new Info(0, hashCode("#,")); // null的时候，先序序列中是#, 生成hashCode
        }
        // 左子树搜集
        Info leftInfo = process(X.left);
        // 右子树搜集
        Info rightInfo = process(X.right);
        // 以X为头结点的整棵树生成信息
        int ans = leftInfo.ans + rightInfo.ans + (leftInfo.str.equals(rightInfo.str )? 1 : 0);
        String str = String.valueOf(X.value) + "," + leftInfo.str + rightInfo.str;    // value生成先序序列, 头左右
        return new Info(ans, hashCode(str)); //先序序列变成hash code
    }
    
    // hash code 生成
    public static String hashCode(String input) {
        MessageDigest hash = null;
        String hashCodeString = "";
        try {
            hash = MessageDigest.getInstance("SHA-256");
            hashCodeString = DatatypeConverter.printHexBinary(hash.digest(input.getBytes())).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashCodeString;
    }

    public static Node randomBinaryTree(int restLevel, int maxValue) {
        if (restLevel == 0) {
            return null;
        }
        Node head = Math.random() < 0.2 ? null : new Node((int) (Math.random() * maxValue));
        if (head != null) {
            head.left = randomBinaryTree(restLevel - 1, maxValue);
            head.right = randomBinaryTree(restLevel - 1, maxValue);
        }
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 8;
        int maxValue = 4;
        int testTime = 100000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            Node head = randomBinaryTree(maxLevel, maxValue);
            int ans1 = sameTreeNumber(head);
            int ans2 = sameTreeNum(head);
            if (ans1 != ans2) {
                System.out.println("出错了！");
                System.out.println(ans1);
                System.out.println(ans2);
            }
        }
        System.out.println("测试结束");

    }


}
