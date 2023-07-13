package class30;

import java.util.ArrayList;
import java.util.List;

// 119. 杨辉三角 II
public class Problem_0119_PascalTriangleII {
    /**
     * 给定一个非负索引 rowIndex，返回「杨辉三角」的第 rowIndex 行。
     * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
     * 示例 1:
     * 输入: rowIndex = 3
     * 输出: [1,3,3,1]
     * 
     * 示例 2:
     * 输入: rowIndex = 0
     * 输出: [1]
     */
    
    /**
     * 思路: 
     * 1          0
     * 1 1        1
     * 1 2 1      2
     * 1 3 3 1    3
     * ...
     * 
     * 能不能自我更新搞定一行的每一个数
     * 
     * 比如要求第3行，第二行是[1,2,1] 第3行肯定知道有4个元素
     * 最后一个元素是1
     * 
     * [1, 2, 1, 1] -> 从右往左推
     * 
     * 要求第3行的数，倒数第二个位置就是它上面的数加右上的数，也就是1 + 2
     * 
     * 每一行最后一个元素都填1，然后从倒数第二个位置开始自我更新，一直到第二个元素
     * 
     * 
     * 
     * 先求第0行 是 [1]
     * 求第1行 是 [1, 1]
     * 求第2行 是 [1, 2, 1]
     * 求第2行的时候，从右往左自我更新，现在数组里面是[1, 1]
     * 
     * 求第二行1位置的数, 是它上和右上相加，也就是1 + 1 ->数组变成 [1, 2]
     * 然后数组最后加上1，得[1, 2, 1] 搞定
     * 
     */
    public List<Integer> getRow(int rowIndex) {
        List<Integer> ans = new ArrayList<Integer>();
        // 每一行自我更新，一行行求，一直到要求的rowIndex
        for(int i = 0; i <= rowIndex; i++) {
            // 从第2行开始求，i = 2 从右往左推
            // 每个数都是当前数加前一个数
            for(int j = i - 1; j > 0; j--) {
                ans.set(j, ans.get(j) + ans.get(j - 1));
            }
            // 最后一个数是1
            ans.add(1);
        }
        return ans;
    }
}
