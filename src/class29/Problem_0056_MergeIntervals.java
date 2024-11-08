package class29;

import java.util.Arrays;

// 56. 合并区间
public class Problem_0056_MergeIntervals {
    /**
     * 以数组 intervals 表示若干个区间的集合，其中单个区间为 intervals[i] = [starti, endi] 。
     * 请你合并所有重叠的区间，并返回 一个不重叠的区间数组，该数组需恰好覆盖输入中的所有区间 。
     */
    
    /**
     * 思路:
     * 从第一个区间开始，记录start和end，然后遍历剩下的区间，如果当前区间的左端点比上一个区间的end小或者等于，则看当前区间的右端点
     * 能不能将end推高得到一个更好的区间，能就记录；如果当前区间的左端点比上一个区间的end大，则说明要开辟新的区间了，记录原始的start和
     * end到数组中，然后设置新的区间的左端点和右端点为start和end
     * 例子:
     * [[1,3], [1,4], [2, 5], [3, 5]]
     * 将intervals数组按照第一维数据从小到大进行排序，将第0号数据作为初始区间，记录start和end，
     * 然后从第一个数据开始，如果该数据的start大于当前记录的范围的end, 说明应该开始一个新的区间 ->
     * 将当前区间记录到原始数组中对应的位置，然后开始新的区间
     * 如果该数据的start小于等于当前记录的范围的end, 则看当前该数据的end能不能将当前记录的end推高 --> 例如数组中的[ [1,3],
     * [1,4], [2, 5]],
     * [1,4], [2, 5]看4和5能不能将当前记录的3推高，能则更新区间
     * 
     * 区间结果都存放在了原始数组中，搞一个size，记录原始数组的前size个元素是记录的答案，后面直接返回这段数组的copy就是答案 --> 节省空间
     */
    public int[][] merge(int[][] intervals) {
        if(intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return null;
        }
        // 按第一维数据将原始数组进行排序 a, b相当于数组里面的对象，0位置就是第一维数据
        Arrays.sort(intervals, (a, b) ->  a[0] - b[0]);
        // 记录0号元素为初始区间
        int start = intervals[0][0];
        int end = intervals[0][1];
        // 标记答案记录到了原始数组的哪个位置
        int size = 0;
        // 后面的每一个元素去pk
        for(int i = 1; i <= intervals.length - 1; i++) {
            // 如果当前元素的start 大于 当前区间的end, 说明应该新开一个区间了
            if (intervals[i][0] > end) {
                // 将答案记录到原始数组中
                intervals[size][0] = start;
                intervals[size][1] = end;
                size++; // 跳到下一个位置
                // 开始新的区间
                start = intervals[i][0];
                end = intervals[i][1];     
            } else { //如果当前元素的start 小于等于 当前记录的区间的end, 看当前元素的end能不能推高区间
                end = Math.max(end, intervals[i][1]);
            }
        }
        // 因为上面是先记录答案到原始数组中，然后才开始的新区间，所以最后一个新区间要在这里记录
        intervals[size][0] = start;
        intervals[size][1] = end;
        size++;
        // copyOf 是复制size长度的数字，size是从0开始，所以上面size还是要++
        return Arrays.copyOf(intervals, size);
    }
    
    /**
     * 省掉start和end的写法，更加的简单
     */
    public int[][] merge2(int[][] intervals) {
        // 判断输入是否合法
        if (intervals == null || intervals.length == 0 || intervals[0] == null || intervals[0].length == 0) {
            return null;
        }

        // 按照区间的起始端点升序排序
        Arrays.sort(intervals, (a, b) -> a[0] - b[0]);

        int size = 0; // 用于记录合并后的区间数
        int N = intervals.length;

        for (int i = 1; i < N; i++) { // 从第二个区间开始遍历
            // 如果当前区间不重叠，直接保存当前区间
            if (intervals[i][0] > intervals[size][1]) {
                size++;
                intervals[size] = intervals[i]; // 将当前区间保存到原数组
            } else {
                // 如果当前区间重叠，更新当前区间的end
                intervals[size][1] = Math.max(intervals[size][1], intervals[i][1]);
            }
        }

        // 返回合并后的区间
        return Arrays.copyOf(intervals, size + 1);
    }
}
