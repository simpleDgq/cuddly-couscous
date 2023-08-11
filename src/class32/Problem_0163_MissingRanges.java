package class32;

import java.util.ArrayList;
import java.util.List;

// 163. 缺失的区间
public class Problem_0163_MissingRanges {
    /**
     * 给定一个排序的整数数组 nums ，其中元素的范围在 闭区间 [lower, upper] 当中，返回不包含在数组中的缺失区间。
     * 示例：
     * 输入: nums = [0, 1, 3, 50, 75], lower = 0 和 upper = 99
     * 输出: ["2", "4->49", "51->74", "76->99"]
     */
    
    /**
     * 考察整理边界的能力:
     * 
     * 例子: [0, 1, 3, 50, 75], lower = -5 和 upper = 99
     * 0 > -5, -5 到 -1 需要补齐
     * 
     * 1. 如果数组中的num大于lower，说明lower到num-1是缺失的数，需要补齐
     * 2. 如果num等于upper，说明搞完了，返回结果
     * 3. lower往下走
     * 4. 数组遍历完成，lower都没有到达upper，说明数组最后的lower到upper是要补齐的 --> 例子76 到99要补齐
     */
    public List<String> findMissingRanges(int nums[], int lower, int upper) {
        List<String> ans = new ArrayList<String>();
        if(nums == null || nums.length == 0) {
            return ans;
        }
        
        for(int num : nums) {
            if(num > lower) {
                ans.add(miss(lower, num - 1));
            }
            if(num == upper) {
                return ans;
            }
            lower = num + 1;
        }
        // 补齐缺失的
        if (lower < upper) {
            ans.add(miss(lower, upper));
        }
        return ans;
    }
    
    // 生成"lower->upper"的字符串，如果lower==upper，只用生成"lower"
    public static String miss(int lower, int upper) {
        String left = String.valueOf(lower);
        String right = "";
        if (upper > lower) {
            right = "->" + String.valueOf(upper);
        }
        return left + right;
    }
    
}
