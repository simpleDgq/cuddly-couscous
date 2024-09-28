package class38;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

// https://leetcode.cn/problems/daily-temperatures
// 739. 每日温度
public class Problem_0739_DailyTemperatures {
    /**
     * 这题实际上要求的就是，每个位置的数，要求右边第一个比它大的数的下标
     * 很明显是单调栈
     * 有重复数，单调栈中放的是链表，相同的数放在同一个链表里面
     * 
     * 单调栈流程:
     * 对于每一个数，如果栈不为空，且栈顶元素比当前数小，那么就一直出栈，
     * 出栈的过程中搜集答案；使栈顶元素出栈的元素，一定是右边第一个比它大的元素，算好答案，记录下来
     * 然后将当前元素的下标入栈
     * 遍历完数组，如果栈不为空，说明栈里面的元素的右边没有比它大的元素。不用搜集。而且压着的是它左边比它大的第一个数
     * 所以可以不处理。
     */
    public int[] dailyTemperatures(int[] temperatures) {
        if(temperatures == null || temperatures.length == 0) {
            return null;
        }
        int N = temperatures.length;
        int ans[] = new int[N]; // 搜集每个数的答案，是右边比它大的第一个数的下标 减去 当前元素的下标
        Stack<List<Integer>> stack = new Stack<List<Integer>>();
        // 遍历每一个数，如果比栈顶元素大，则一直弹出栈顶元素，收集好答案
        for(int i = 0; i <= N - 1; i++) {
            int cur = temperatures[i];
            while((!stack.isEmpty()) && (temperatures[stack.peek().get(0)] < cur)) { // 栈里面是链表，每个链表存储的是相等的元素的下标，所以直接取第0个就行
                List<Integer> list = stack.pop();// 弹出栈顶元素
                for(int popI : list) {// 搜集答案
                    ans[popI] = i - popI; // 弹出的元素的下标是popI,对应的答案是i - popI
                }
            }
            // 将新的元素的下标放入到栈中
            if((!stack.isEmpty()) && (temperatures[stack.peek().get(0)] == cur)) {
                stack.peek().add(i); // 如果栈顶的元素和当前要放入的元素相同，直接放入当前元素的下标
            } else { // 不存在，new 一个然后放入
                List<Integer> list = new ArrayList<Integer>();
                list.add(i); // 下标放入单调栈
                stack.push(list);
            }
        }
        // 栈中剩余的元素，不用处理，每个数右边都没有比它大的数.直接返回答案
        return ans;
    }
}
