package class06;

import java.util.ArrayList;
import java.util.HashMap;

public class Code04_MostXorZero {
    /*
     * 数组中所有数都异或起来的结果，叫做异或和
     *   给定一个数组ar,可以任意切分成若干个不相交的子数组
     *   其中一定存在一种最优方案，使得切出异或和为0的子数组最多
     *   返回这个最多数量
     */
    
    /*
     * 暴力方法 O(2^N) 每个位置都可以切或者不切
     * 你现在来到i位置，那为什么跳过0来到1呢？它的决策是这样的，你之前有一个部分，你来到i的时候决不决定让你
        之前的部分打隔断了就切了，所以来到i位置之后就两种可能性: 第一种可能性。你前面的部分切了，第二种可能是你
        前面的部分跟你是一坨, 不切, 留着你i+1位置决定你切不切
     */
    private static int comparator(int[] arr ) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        
        int eor[] = new int[N];
        eor[0] = arr[0];
        for(int i = 1; i <= N -1 ; i++) {
            eor[i] = arr[i] ^ eor[i - 1];
        }
        ArrayList<Integer> parts = new ArrayList<Integer>();
        return process(1, eor, parts); // 那为什么跳过0来到1呢？它的决策是这样的，你之前有一个部分，你来到i的时候决不决定让你切还是不切
    }
    /*
     * 当前来到index位置，决定前面的数切不切，切的话，加入parts中
     * 给我返回异或和为0最多的种数
     */
    public static int process(int index, int eor[], ArrayList<Integer> parts) {
        int ans = Integer.MIN_VALUE;
        if(index == eor.length) { // 没有数了，只能切了
            parts.add(eor.length);
            // 计算答案
            ans = eorZeorParts(eor, parts);
            // 从parts里面remove，因为这个分支返回之后，别的分支也要用这个parts，需要删掉脏数据
            parts.remove(parts.size() - 1);
        } else {
           // index位置不切
           int p1 = process(index + 1, eor, parts);
           // index位置切
           parts.add(index);
           int p2 = process(index + 1, eor, parts);
           parts.remove(parts.size() - 1); // 恢复现场
           ans = Math.max(p1, p2);
        }
        return ans;
    }
    public static int eorZeorParts(int eor[],  ArrayList<Integer> parts) {
        int ans = 0;
        int start = 0;
        for(int end : parts) { // 遍历所有切割的情况，计算异或和为0的zero part的数量
            if((eor[end - 1] ^ (start == 0 ? 0 : eor[start - 1])) == 0) { // 划分的时候，part是不包含尾部的，所以减1
                ans++;
            }
            start = end; // 计算下一块
        }
        return ans;
    }
    
   /*
    * 动态规划
    * 从左往右模型-
    * O(N)
    * 
    * 
    * 
    * dp[i]: arr从0...i能够最多切出多少个异或和为0的部分, 不要求必须以i结尾
        假设arr，长度是10. index0..9
        这这表最后dp[9]: 代表arr 从0..9能切出几个部分能让异或和部分数最多
        所以只要我们能够顺利求完这张表，最后一个位置就是答案。
    */
    
    
    /*
     * 可能性分类:
        arr 0...i上切, 一定有两种可能性
        客观上来讲一定有一个最好划分，使得它异或和为0的部分数最多
        关注最后一个部分, 两种可能:
        1) 最后一个部分异或和不是0
        2) 最后一个部分异或和是0
        ====
        1) 最后一个部分不是0, 和i位置没关系，dp[i] 由dp[i - 1]决定
           ==> dp[i] = dp[i-1]
        2) 0..i上最优划分的最后一个部分是异或和位0
            假设答案法：看最后一部分具有什么性质
            0~17是整个数组的长度。
            假设从0~17整体所有异或和是100, 最后一部分的异或和是0，那么怎么找到最后一部分的左边界在哪？
            ==> 之前某一个前缀或和是100出现的最晚位置，就是它最优的最后一块异或和为零的部分左边界.假设为j，则最后一部分的开始位置是j + 1
            dp[i] 就取决于dp[j] 加上最后一部分1
            ==> dp[i] = dp[j] + 1
     */
    public static int mostXor(int arr[]) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        // 记录异或和最近出现的位置
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, -1); // 一个数也没有的时候，异或和是0，在-1位置，一定要有
        
        int N = arr.length;
        int dp[] = new int[N];
        int eor = 0; // 记录0 ~ i的整体异或和
        for(int i = 0; i <= N - 1; i++) {
            eor ^= arr[i];
            if(map.containsKey(eor)) { // map中有当前异或和
                int pre = map.get(eor);
                // 说明pre + 1 .... i是最后一部分，且异或和为0
                dp[i] = pre == -1 ? 1 : dp[pre] + 1; // 当pre = -1的时候，因为pre + 1 = 0，说明0 ~ i就是异或和为0的一个整体，答案是1。 
            }
            if(i > 0) { // 可能性1，最后一部分异或和不是0，取决于dp[i - 1]
                dp[i] = Math.max(dp[i], dp[i - 1]); 
            }
            map.put(eor, i); // 记录异或和最近出现的位置
        }
        return dp[N -1];
    }
    
    // for test
    public static int[] generateRandomArray(int maxSize, int maxValue) {
        int[] arr = new int[(int) ((maxSize + 1) * Math.random())];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) ((maxValue + 1) * Math.random());
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    // for test
    public static void main(String[] args) {
        int testTime = 150000;
        int maxSize = 12;
        int maxValue = 5;
        boolean succeed = true;
        for (int i = 0; i < testTime; i++) {
            int[] arr = generateRandomArray(maxSize, maxValue);
            int res = mostXor(arr);
            int comp = comparator(arr);
            if (res != comp) {
                succeed = false;
                printArray(arr);
                System.out.println(res);
                System.out.println(comp);
                break;
            }
        }
        System.out.println(succeed ? "Nice!" : "Fucking fucked!");
    }
}
