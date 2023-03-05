package class24;

import java.util.HashMap;

public class Code03_Split4Parts {
    /**
     * 数组能不能分成四个和相等的部分
     * 
     * 给定一个数组arr，长度一定大于6
        一定要选3个数字做分割点，从而分出4个部分，并且每部分都有数
        分割点的数字直接删除，不属于任何4个部分中的任何一个。
        返回有没有可能分出的4个部分累加和一样大
        如：{3,2,3,7,4,4,3,1,1,6,7,1,5,2}
        可以分成{3,2,3}、{4,4}、{1,1,6}、{1,5,2}。分割点是不算的！
     */
    
    /**
     * 思路:
     * 做出前缀和数组, 假设来到i位置, 想问
        i位置做第一刀的情况下有没有可能切出4个部分累加和一样.
        
     *  例子:
     *  假如现在来到的位置是7位置，假设7位置的数是9，前面0~6位置的前缀和是100，问7位置能不能作为第一刀？  
     *  如果7位置能作为第一刀，那么从8位置出发到某一个位置的这一段，必须要能搞出和100，也就是求
     *  0位置到某一个位置能不能搞出209(100 + 9 + 100)，
     *  
     *  假设说能搞出来，位置是13位置，它的下一个位置就是第二刀位置，是14位置，假设这个位置的数是6，继续问这个位置能不能作为第2刀？
     *  如果能，则这个位置的后面某一段要能搞出和100，也就是求前缀合数组中有没有前缀和315(209 + 6 + 100)，
     *  
     *  假设说能搞出来，位置是29位置，那么它的下一个30位置，假设这个位置的数是5，继续问这个位置能不能作为第3刀？
     *  也就是求剩下的位置，从31开始到数组最后(整个数组的和arr[length-1] - arr[30])，和是不是100? 如果是，就能搞定。
     *  
     *  时间复杂度: O(N) 验证每一个位置能不能作为第一刀，共有N个位置，每个位置的验证，时间复杂度都是O(1)的，直接从map中取，所以是O(N)
     */
    
    public static boolean canSplit(int arr[]) {
        if(arr == null || arr.length < 7) { // 题目说了，长度大于6
            return false;
        }
        // 记录前缀和出现的位置
        // key: 前缀和，value: 这个前缀和在前缀和数组中出现的位置
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int N = arr.length;
//        int sum[] = new int[N];
//        sum[0] = arr[0];
//        map.put(sum[0], 0);
//        for(int i = 1; i <= N - 1; i++) {
//            sum[i] = sum[i - 1] + arr[i];
//            map.put(sum[i], i);
//        }
        // 优化空间复杂度，没必要真正的搞一个辅助数组，优化之后空间复杂度从O(N) --> O(1)
        int sum = 0;
        sum = arr[0];
        map.put(sum, 0);
        for(int i = 1; i <= N - 1; i++) {
          sum += arr[i]; // 最终sum表示的是整个数组的和
          map.put(sum, i);
        }
        
        // 每一个位置都去尝试作为数组的第一刀
        // 第0个位置不能作为第1刀，因为左边没有数，白白浪费一刀机会，就不能切成四份
        // 作为第1刀的位置不能超过N - 5, 如果超过了，就不能切出4份了
        // 例子: 假设N=7，那么第一刀的位置不能超过2，假设就是2，那么后面剩下4个数，两刀的机会，怎么也切不出3份
        int lsum = arr[0]; // 从1位置开始，左边第一份初始的时候就只有arr[0]
        for(int st1 = 1; st1 < N - 5; st1++) {
            // 如果第一刀的位置是st1，那么要求map中有累加和lsum * 2 + arr[st1]
            int checkSum = lsum * 2 + arr[st1];
            // 如果map中有lsum * 2 + arr[st1]
            if(map.containsKey(checkSum)) {
                // st2 + 1是第2刀位置
                int st2 = map.get(checkSum); // st2 + 1是第2刀位置
                if(st2 + 1 >= N - 3) { // 如果第2刀的位置>=N-3位置，那么后面剩下的位置不能通过1到搞成2部分。直接返回false
                    return false;
                }
                // 第2刀的位置，要求map中有lsum * 3 + arr[st1] + arr[st2 + 1]
                checkSum += arr[st2 + 1] + lsum; // lsum * 3 + arr[st1] + arr[st2 + 1]
                if(map.containsKey(checkSum)) {
                    int st3 = map.get(checkSum); // st3 + 1是第3刀位置
                    if(st3 + 1 >= N - 1) { // 第3刀的位置，不能超过N-1，因为第4部分必须有数
                        return false;
                    }
                    checkSum += arr[st3 + 1];// 3 * lsum +  arr[st1] + arr[st2 + 1] + arr[st3 + 1]
                    // 第3刀的位置, 要求从st3+1位置开始，到数组最后位置的和是lsum
                    if(lsum == sum - checkSum) {
                        return true;
                    }
                }
               
            }
            // st1位置不能作为第一刀，左边部分的累加和变大
            lsum += arr[st1];
        }
        return false;
    }
}
