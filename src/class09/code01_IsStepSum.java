package class09;

import java.util.HashMap;

public class code01_IsStepSum {
    /*
     * 
     * 定义何为step sum？
        比如680，680 + 68 + 6 = 754，680的step sum叫754
        给定一个正数num，判断它是不是某个数的step sum
     */
    
    /*
     * 一个数 X 它的步骤和叫甲, 一个数Y它的步骤和叫乙，
     *   我首先有一个推论，如果Y>X，它的步骤和乙只可能大于甲。
     *   
     *   二分
     *   
     */
    public static boolean isStepSum(int stepSum) {
        int L = 0;
        int R = stepSum;
        int mid = 0;
        int cur = 0;
        while(L <= R) {
            mid = L + ((R - L) >> 1);
            cur = stepSum(mid);
            if(cur == stepSum) { // 如果中点位置的步骤和正好是给定的stepSum，说明stepSum是中点这个数的步骤和
              return true;  
            } else if(cur < stepSum) { // 中点的步骤和小于给定的stepSum, 去右边继续找
                L = mid + 1;
            } else {
                R = mid - 1;
            }
        }
        return false;
    }
    // 求num的步骤和
    // 时间复杂度O(log10N)  log以10为底N
    public static int stepSum(int num) {
        int sum = 0;
        while(num != 0) {
            sum += num;
            num /= 10;
        }
        return sum;
    }
    
    // for test
    public static HashMap<Integer, Integer> generateStepSumNumberMap(int numMax) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i <= numMax; i++) {
            map.put(stepSum(i), i);
        }
        return map;
    }

    // for test
    public static void main(String[] args) {
        int max = 1000000;
        int maxStepSum = stepSum(max);
        HashMap<Integer, Integer> ans = generateStepSumNumberMap(max);
        System.out.println("测试开始");
        for (int i = 0; i <= maxStepSum; i++) {
            if (isStepSum(i) ^ ans.containsKey(i)) {
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");
    }

}
