package class06;

public class Code01_MaxXOR {
    
    /*  最大子数组异或和
     *  数组中所有数都异或起来的结果，叫做异或和
     *  给定一个数组ar,返回ar的最大子数组异或和
     */
    
    /**
     * 子数组经常是考虑每个位置结尾情况下它的答案是什么?
     * 
     * 因为异或这个运算非常的魔性，
        一个大数加一个大数，一定能够得到一个累加和的大数，但是异或运算他没有这个性质，
        一个大数抑或一个大数很可能超小，一个小数一异或一个小树也可能很大，它们没有这种向累加和那样明确的单调性
        
     * 暴力解法:
     * 以i位置结尾的子数组：
     *  怎么选择一个前缀的异或和, 让它最大
        1) 一个数也不选
        2) 前缀和选0~0
        3) 0~1
        ...
        1~1...
        既然不知道选谁, 都试一遍，总有对的时候，选出一个max，这就是我们的暴力解
       ======
       [0,1,2,3,4,5]  ==> 以5位置结尾的子数组有0~5， 1~5，2~5，3~5，4~5，5~5
               假设0~5前缀和是a，
               前缀和一个都不选的时候: 假设前缀和就是0， a^0这时候表示的就是0~5的前缀和;
               前缀和选0~0的时候: 假设前缀和就是b， a^b这时候表示的就是1~5的前缀和;  
               前缀和选0~1的时候: 假设前缀和就是c， a^c这时候表示的就是2~5的前缀和;
               ...
               所有的情况取最大值
       ======
       把前缀和记到map里, 可以优化为N^2
       
       ===时间复杂度分析===
       以i位置结尾的时候，需要讨论的情况有i种，
       以i + 1位置结尾的时候，需要讨论的情况有i + 1种
       ...
       总共N^2种，所以时间复杂度为O(N^2)
       
       
==========
        优化到O(N)：
        把所有前缀和按某种方式组织, 帮助a选择可能最大的异或和
        
        使用前缀树
        假设都是正数, 无符号位
        大体思路:
        每一个位置的整体前缀异或和 和 前缀树上所有可能的前缀异或和，去匹配，求一个最大值
     *
     */
    
    // O(N^2)
    public static int maxXorSubAarry(int arr[]) {
        if(arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        int eor[] = new int[N]; // 记录i位置结尾的异或和
        eor[0] = arr[0]; // 0位置的异或和就是自己
        for(int i = 1; i <= N - 1; i++) { // 生成每一个位置的整体前缀异或和数组
            eor[i] = eor[i - 1] ^ arr[i];
        }
        
        // 每一个位置的整体前缀异或和和它前面可能的异或和去匹配，求出一个最大的
        int ans = Integer.MIN_VALUE;
        for(int i = 0; i <= N - 1; i++) {
            for(int j = 0; j <= i; j++) { // j == 0的时候，表示前面的一个也不选，异或和就是eor[i]，表示的是0~i的异或和
                                            // j == 1的时候, 表示的是0~1的异或和，eor[0]异或上eor[i] 表示的就是1~i的异或和
                                            // ...
                                           // 取最大值
                ans = Math.max(ans,  j == 0 ? eor[i] : eor[i] ^ eor[j - 1]);
            }
        }
        return ans;
    }
    
    // O(N)
    // 前缀树的Node结构
    // nexts[0] -> 0方向的路
    // nexts[1] -> 1方向的路
    // nexts[0] == null 0方向上没路！
    // nexts[0] != null 0方向有路，可以跳下一个节点
    // nexts[1] == null 1方向上没路！
    // nexts[1] != null 1方向有路，可以跳下一个节点
    public static class Node {
        public Node[] nexts = new Node[2];
    }
    public static class NumTrie {
        Node head = new Node();
        // 给定一个数，加入到前缀树上
        public void add(int num) {
            Node cur = head;
            // int 有32位，每一位分别加到前缀树上
            for(int i = 31; i >= 0; i--) {
                int path = (num >> i) & 1; // 分别提取i位置上的1
                // 无路新建，有就复用
                cur.nexts[path] = cur.nexts[path] == null ? new Node() : cur.nexts[path];
                // 跳下一个
                cur = cur.nexts[path];
            }
        }
        // 该结构之前收集了一票数字，并且建好了前缀树
        // 给定一个数，返回与前缀树上的路径，异或之后的最大值
        public int maxXor(int num) {
            Node cur = head;
            // int 有32位，每一位分别加到前缀树上
            int ans = 0;
            for(int i = 31; i >= 0; i--) {
               int path = (num >> i) & 1; // 分别提取i位置上的1
               // 期望的路，
               // 希望遇到相反的。0希望遇到1，1希望遇到0
               // 最高位是符号位，1代表负数，1希望遇到1，搞成0，变成正数。0也希望遇到0。==> 符号位希望遇到相同的。
               int best = i == 31 ? path : (path ^ 1);
               // 实际遇到的路, 没有希望的路，那么只能走向相反。否则走向希望的路
               best = cur.nexts[best] == null ?(best ^ 1): best;
               ans |= ((path ^ best) << i); // 异或完，收集答案
               // 走向实际遇到的路
               cur = cur.nexts[best];
            } 
            return ans;
        }
    }
    // 利用前缀树，将前面可能的前缀异或和放到树上，每次只需要去找一条最优的路径
    public static int maxXorSubAarry1(int arr[]) {
        if(arr == null || arr.length == 0) {
            return 0;
        }
        NumTrie numTrie = new NumTrie();
        numTrie.add(0); // 一个数也没有的时候，异或和是0
        int xor = 0; // 0 ~i的整体异或和
        
        int max = Integer.MIN_VALUE;
        //每一个位置的整体前缀异或和 和 前缀树上之前所有可能的前缀异或和，去匹配，求一个最大值
        for(int i = 0; i <= arr.length - 1; i++) {
            xor ^= arr[i]; // 0 ~i的整体异或和
            max = Math.max(max, numTrie.maxXor(xor)); // 0 ~i的整体异或和 和 前缀树上所有可能的前缀异或和去匹配，求得最大异或和
            // 加入前缀树
            numTrie.add(xor);
        }
        return max;
    }
}
