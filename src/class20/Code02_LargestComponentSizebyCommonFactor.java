package class20;

import java.util.HashMap;

//https://leetcode.cn/problems/largest-component-size-by-common-factor/
public class Code02_LargestComponentSizebyCommonFactor {
    /**
     * 952. 按公因数计算最大组件大小
     * 
     * 一个数组中，如果两个数的最小公共因子有大于1的，则认为这两个数之间有通路
        返回数组中，有多少个独立的域
        
        leetcod描述:
        给定一个由不同正整数的组成的非空数组 A，考虑下面的图：
        
        有 A.length 个节点，按从 A[0] 到 A[A.length - 1] 标记；
        只有当 A[i] 和 A[j] 共用一个大于 1 的公因数时，A[i] 和 A[j] 之间才有一条边。
        返回图中最大连通组件的大小。
        
        示例 1：
        
        输入：[4,6,15,35]
        输出：4
        示例 2：
        
        输入：[20,50,9,63]
        输出：2
        示例 3：
        
        输入：[2,3,6,7,4,12,21,39]
        输出：8
        提示：
        
        1 <= A.length <= 20000
        1 <= A[i] <= 100000
     */
    
    /**
     * 思路: 
     * 解法1: 如果有公因数能使两个数连在一起，那么
     * 他俩一定有最大公约数这个玩意儿，如果最大公约数不是1，那么两个数可以在并查集中连在一起
     * 
     * 例如数组[3,7,21]
     * 这样一来，从数组的0号元素开始, 求0号元素和1号元素的最大公约数，如果不等于1，那么就将0号元素和1号元素，
     * 在并查集中合并; 
     * 0号元素和2号元素，求最大公约数，不等于1，就合并
     * 0和3号。。
     * 0和4号
     * ....
     * 0和N-1号
     * 
     * 1和2号求最大公约数，合并
     * 1和3号
     * ...
     * 
     * N个元素，并查集合并的时间复杂度是O(1)，但是算法整体的调度的时间复杂度是O(N^2)，所以总的时间复杂度就是O(N^2)
     * 
     * 
     * 解法2: 优化主流程
     * 因子: 假如整数n除以m，结果是无余数的整数，那么我们称m就是n的因子。
     * 
     * 找出数组中的每个数X拥有那些因子，放入因子表HashMap中，一次找一对, 可以在根号X复杂度下拿下
     * 例子:
     * 100这个数有哪些因子？
     * 从1~根号100中找。 --> 这样做就是为了保证时间复杂度是根号X。没有别的理由
     * 1) 100能被1整除，那么1和100是一对因子，放入因子表中
     * 2) 100能被2整除，那么2和50是一对因子，放入因子表中
     * 3) 100不能被3整除，不放入因子表中
     * 4) 100能被4整除，那么4和25是一对因子，放入因子表中
     * 5) 100能被5整除，那么5和20是一对因子，放入因子表中
     * 6) 100不能被7,8,9整除，不放入因子表
     * 7) 100能被10整除，那么10和10是一对因子，放入因子表中
     * 
     * 流程:
     * 例子:
     * [20, 15 ....] 这个数组
     * 遍历数组中的每一个数，求因子
     * 20到来，1~根号20求因子，1,20,  2,10,  4,5 是因子，放入hashMap中(1这个因子不放)
     * hashmap的key是因子，value是该因子对应的数所在的数组下标。  例如20数组下标是0，那么hashmap中存储的就是
     * 20,0 2,0 10,0 4,0 5,0
     * 
     * 当来了一个新的数，例如15，也求它的因子，如果hashmap中已经存在了该因子，则这两个数所在的位置应该在并查集中进行合并
     * 如果不存在，则加入因子表中
     * 15的因子: 1~根号15求   1,15 3,5
     * 1不放人:[key, value] --> [15,1] 放入hashmap [3,1] 放入hashmap 5在hashmap中有，说明应该20和15的位置在并查集中应该合并
     * 
     * 如果数组中有多个数，有相同的因子，只需要在因子表中记录一个即可，因为并查集会将这些数都合并到一个集合中。
     * 
     * 搞完所有的数，返回最大集合数
     * 
     * 时间复杂度: N个数，每个数都要求因子，时间复杂度是根号val，val是数组中的数值
     * 并查集的操作的时间复杂度是O(1) --> 总的时间复杂度就是O(N*根号val)
     * 
     * 看菜下药: 如果N小，val大，就用第一种方法。如果N大，val小，就用这种方法。
     */
    // 解法1: O(N^2)的解法
    public int largestComponentSize1(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        // 建并查集
        Union union = new Union(N);
        // 遍历所有的情况
        for(int i = 0; i <= N - 1; i++) {
            for(int j = i + 1; j <= N - 1; j++) {
                if(gcd(nums[i], nums[j]) != 1) { // 最大公约数不是1
                    union.union(i, j); // 合并，注意并查集中存储的数组下标
                }
            }
        }
        return union.maxSize();
    }
    /**
     * 求最大公约数(背住)  --> 辗转相除法
     */
    public int gcd(int m, int n) {
        return n == 0 ? m : gcd(n, m % n);
    }
    
    // 解法2 O(N*根号val)
    public int largestComponentSize2(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int N = nums.length;
        
        // 建并查集
        Union union = new Union(N);
        // 因子表 key: 因子  value: 因子对应的数在数组中的位置
        HashMap<Integer, Integer> fators = new HashMap<Integer, Integer>();
        // 1~根号val，求因子
        for(int i = 0; i <= N - 1; i++) {
            int cur = nums[i];
            int limit = (int) Math.sqrt(cur);
            for(int j = 1; j <= limit; j++) {
                if(cur % j == 0) { // 如果能被整除, 不能整除，看下一个j
                    if(j != 1) { // 1不能加入到因子表中
                        // 因子表中不存在该因子，则将改因子放入因子表中
                        if(!fators.containsKey(j)) {
                            fators.put(j, i);
                        } else { // 因子表中存在该因子，合并对于的下标
                            union.union(fators.get(j), i); // 从因子表中取出因子对应的下标，然后和当前i合并
                        }
                    }
                    // 另一个因子，因子是成对出现的
                    int other = cur / j;
                    if(other != 1) { // 1不能加入到因子表中
                        // 因子表中不存在该因子，则将改因子放入因子表中
                        if(!fators.containsKey(other)) {
                            fators.put(other, i);
                        } else { // 因子表中存在该因子，合并对于的下标
                            union.union(fators.get(other), i); // 从因子表中取出因子对应的下标，然后和当前i合并
                        }
                    }
                }
            }
        }
        return union.maxSize();
    }
    
    
    /**
     * 并查集
     * 并查集中存储的是节点在数组中的位置下标
     */
    public class Union {
        private int[] parents; // 记录每个节点的父节点是谁。 parents[i] = k 表示的节点i的父节点是k
        private int[] size; // 记录每个代表节点代表的集合的大小。 size[i] = k 表示的是代表节点i，所代表的集合的大小是k
        private int[] helper; // 用数组代替栈, 在找代表节点的过程中，存储经过的节点
        
        public Union(int N) {
            parents = new int[N];
            size = new int[N];
            helper = new int[N];
            // 初始情况所有的节点都必须指向自己，集合大小都是1
            for(int i = 0; i <= N -1; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }
        // 找节点i的代表节点
        public int findFather(int val) {
            int index = 0;
            while(val != parents[val]) { // val不是指向自己的，说明不是代表节点，继续往上
                // 往上的过程中记录下经过过的节点，方便后面直接挂在找到的代表节点上
                helper[index++] = val;
                val = parents[val];
            }
            // 找到了代表节点，将所有经过的节点都挂在代表节点上
            for(int i = 0; i <= index - 1; i++) {
                parents[helper[i]] = val; // 经过的节点的代表节点全部设置为val
            }
            // 返回代表节点
            return val;
        }
        // 合并两个节点所在的集合
        public void union(int val1, int val2) {
            int father1 = findFather(val1);
            int father2 = findFather(val2);
            if(father1 != father2) {
                // 小挂大,将节点数少的集合全部挂到大的集合上。 直接将小集合的代表节点挂在大集合的代表节点下就行
                int small = size[father1] < size[father2] ? father1 : father2; // 小的集合的代表节点
                int big = small == father1 ? father2 : father1;
                // 小挂大
                parents[small] = big; // 将小集合的代表节点的父节点设置成大集合的代表节点
                // 更新大集合的size
                size[big] = size[father1] + size[father2];
                // 从size中删掉小集合，因为已经合并了
                size[small] = 0;
            }
        }
        // 求并查集中，最大的集合的大小是多少，返回
        public int maxSize() {
            int max = 0;
            for(int i = 0; i <= size.length - 1; i++) {
                max = Math.max(max, size[i]);
            }
            return max;
        }
    }
}
