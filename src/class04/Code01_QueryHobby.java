package class04;

import java.util.ArrayList;
import java.util.HashMap;

public class Code01_QueryHobby {

    /*
     * 今日头条原题
     * 
     * 数组为{3, 2, 2, 3, 1}，查询为(0, 3, 2)。意思是在数组里下标0~3这个范围上，有几个2？返回2。
     * 假设给你一个数组arr，对这个数组的查询非常频繁，请返回所有查询的结果
     * 
     */
    
    /**
     * 查询频繁 - 想到预处理数组
     * 
     * 生成一个map，存储数组中的每个数都存在于哪些位置， 比如说1，出现在(1, 4, 5， 7)这些位置
     * 查询的时候，就是二分。比如查询(2, 6 ,1),查询2到6位置上1出现了多少次，其实就是找到大于等于2的最左位置，和小于等于6的最右位置。
     */
    public static int queryBox(int nums[], int L, int R, int value) {
        if(nums == null || nums.length == 0 || L < 0 || R < 0) {
            return 0;
        }
        int N = nums.length;
        HashMap<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
        for(int i = 0; i <= N - 1; i++) {
            ArrayList<Integer> list = null;
            if(!map.containsKey(nums[i])) {
                list = new ArrayList<Integer>();
            } else {
               list = map.get(nums[i]);
            }
            list.add(i);
            map.put(nums[i], list);
        }
        
        // 根本没有value这个值，返回0
        if(!map.containsKey(value)) {
           return 0; 
        }

        ArrayList<Integer> arr = map.get(value);
        
        // 这里得边界扣不清楚
        int a = binarySearch(arr, L); // 查找小于L的最右位置
        int b = binarySearch(arr, R + 1); // 查找小于R + 1的最右位置
       
        return b - a;
    }
    
    /*
     * 查找小于value的最右位置
     */
    public static int binarySearch(ArrayList<Integer> arr, int value) {
        int L = 0;
        int R = arr.size() - 1;
        int ans = -1;
        while(L <= R) {
            int mid = L + ((R - L) >> 1);
            if(arr.get(mid) < value) {
                L = mid + 1;
                ans = mid;
            } else {
                R = mid - 1;
            }
        }
        return ans + 1; // 这里得边界扣不清楚
    }    
}
