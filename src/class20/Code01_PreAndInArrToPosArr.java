package class20;

import java.util.HashMap;

public class Code01_PreAndInArrToPosArr {
    /**
     * 根据先序和中序数组，搞出后序数组
     * 
     * 如果只给定一个二叉树前序遍历数组pre和中序遍历数组in，
     *   能否不重建树，而直接生成这个二叉树的后序数组并返回
     *   已知二叉树中没有重复值
        
     *   已知一棵二叉树中没有重复节点，并且给定了这棵树的中序遍历数组和先序遍历 数组，返回后序遍历数组。
            比如给定:
            int[] pre = { 1, 2, 4, 5, 3, 6, 7 };
            int[] in = { 4, 2, 5, 1, 6, 3, 7 }; 返回:
            {4,5,2,6,7,3,1}
        
        要求没有重复值
        如果有重复值, 先序跟中序可能生成不是唯一的后序
     */
    
    /**
     * 思路:
     * 定义一个函数f(pre[], L1,R1, in[],L2,R2, pos[],L3,R3)
     * 告诉先序数组的范围是L1到R1, 中序遍历数组的范围是L2到R2, 给我填好后序数组pos
     * L3到R3这个范围
     * L1~R1, L2~R2, L3~R3是等长的
     */
    public static int[] solution(int pre[], int in[]) {
        if(pre == null || pre.length == 0 || in == null 
                || in.length == 0 || pre.length != in.length) {
            return null;
        }
        int N = pre.length;
        int pos[] = new int[N];
        // 存储中序数组中，每个元素所在的位置，为了省掉for循环
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i <= N - 1; i++) {
            map.put(in[i], i);
        }
        // 0~N-1范围上递归去搞吧
        process(pre, 0, N- 1, in, 0, N - 1, pos, 0, N - 1, map);
        return pos;
    }
    public static void process(int pre[], int L1, int R1, int in[], 
            int L2, int R2, int pos[], int L3, int R3, HashMap<Integer, Integer> map) {
        if(L1 > R1) { // 没有左树或者右树的情况，前面的递归让范围走到了一个不合理的位置，直接返回
            return;
        }
        // base case 如果只有一个数了，直接填到pos中
        if(L1 == R1) {
            pos[L3] = pre[L1];
        } else { // 还剩多个数的时候
            // 先序遍历的第一个数，就是后序遍历的最后一个数, 填好
            pos[R3] = pre[L1];
            // 在中序数组中找到先序遍历的第一个数X，所在的位置
            // 中序遍历中，该位置X的左边就是左树的所有节点
            // 该位置X的右边就是右树的所有节点
            int index = map.get(pre[L1]);
//            for(int i = L2; i <= R2; i++) { // 可以用HashMap省掉for循环
//                if(in[i] == pre[L1]) {
//                    index = i;
//                    break;
//                }
//            }
            // 递归去填好左树和右树
            //先序数组中的第一个节点，已经填在了后序遍历的数组中
            // 所有左树的第一个节点是从L1+1开始的
            // 根据中序遍历中X的位置，可以知道它左边节点的个数，可以求得先序遍历中左树的结束位置
            /**
             * 例子
             * pre下标: 2  3   4     5    6
             *         L1                R1
             * in下标:  5  6   7     8    9
             *         L2    index       R2
             * 假设中序中X的下标index是7，左子树的规模就是index - L2,
             * 那么先序中左子树的长度也是index-L2, 那么先序左树的结束位置就是L1+index-L2
             * 
             * 中序遍历中左树的开始位置是L2，结束位置就是index - 1
             * 
             * 后序遍历中，左树的开始位置是L3，结束位置是L3 + index-L2 -1 ,L3包含进去了，所以要减1，因为左树总共就index-L2个节点
             */
            // 搞左子树
            process(pre, L1 + 1, L1 + index - L2, in, L2, index - 1, pos, L3, L3 + index-L2 - 1, map);
            // 搞右子树
            /**
             * 例子
             * pre下标: 2  3   4     5    6
             *         L1                R1
             * in下标:  5  6   7     8    9
             *         L2    index       R2
             * 上面知道了先序中，左子树到了 L1 + index - L2, 那么先序遍历中右子树开始位置就是 L1 + index - L2 + 1，结束位置就是R1
             * 
             * 中序遍历中右树的开始位置是index + 1，结束位置就是R2
             * 
             * 后序遍历中，右树的开始位置，就是左树的结束位置+1，L3 + index-L2 - 1 + 1，因为后序是左右头遍历;
             * 结束位置就是R3 - 1, 因为R3已经填好了
             */
            process(pre, L1 + index - L2 + 1, R1, in, index + 1, R2, pos, L3 + index-L2 , R3 - 1, map);
        }
    }
}
