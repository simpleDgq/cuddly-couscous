package class30;

import java.util.ArrayList;
import java.util.List;

// 118. 杨辉三角
public class Problem_0118_PascalTriangle {
    /**
     * 给定一个非负整数 numRows，生成「杨辉三角」的前 numRows 行。
     * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
     */
    /**
     * 思路:
     * 在「杨辉三角」中，每个数是它左上方和右上方的数的和。
     * 
     * 1
     * 1 1
     * 1 2 1    i = 3  j <= 1   i - 1 = 2    i - 2 = 1
     * 1 3 3 1
     * 
     * 
     */
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> ans = new ArrayList<>();
        // 先生成好每一行，每一行第一个数都是1，先填上
        for(int i = 1; i <= numRows; i++) {
            List<Integer> list = new ArrayList<Integer>();
            list.add(1);
            ans.add(list);
        }
        // 每一行，根据上方和右上方的数，生成对应的位置
        for(int i = 2; i <= numRows; i++) { // 从第2行开始，去搞
            // 每一行从1位置开始(0位置已经填上1了)，一直到倒数第二个元素都有上和右上元素
            for(int j = 1; j <= i - 2; j++) {
                ans.get(i - 1).add(ans.get(i - 2).get(j) + ans.get(i - 2).get(j - 1));
            }
            // 每一行最后一个元素填上1
            ans.get(i - 1).add(1);
        }
        return ans;
    }
}
