package class09;

import java.util.Arrays;
import java.util.Comparator;

//本题测试链接 : https://leetcode.com/problems/russian-doll-envelopes/
public class Code05_EnvelopesProblem {
    /*
     给你一个二维整数数组 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度。

    当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
    
    请计算 最多能有多少个 信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
    
    注意：不允许旋转信封。
    
    示例 1：
    ```
    输入：envelopes = [[5,4],[6,4],[6,7],[2,3]]
    输出：3
    解释：最多信封的个数为 3, 组合为: [2,3] => [5,4] => [6,7]。
    示例 2：
    
    输入：envelopes = [[1,1],[1,1],[1,1]]
    输出：1
    ```
    
    提示：
    
    1 <= envelopes.length <= 5000
    envelopes[i].length == 2
    1 <= wi, hi <= 104
    
    ref: [[给你一批信封，返回最大的嵌套层数]]
     */
    
    /**
     * 排序: 长度有小到大，长度一样的时候，高度由大到小
     * 排好序之后，高度取出来，形成的数组上做最长递增子序列的长度，就是答案
     * 
     * 这样做的理由: 长度跟我一样的东西高度不如我的在我后面
        那么只要左侧高度小于我，它长度必须小于我，我所形成的递增子序列必能套进去 ==> 这样一来，高度形成的数组，最长递增子序列长度就是答案
     */
    public static class Envelope {
        int l;
        int h;
        public Envelope(int l, int h) {
            this.l = l;
            this.h = h;
        }
    }
    
    // 比较器，长度从小到大，长度一样的情况，高度从大到小
    public static class EnvelopeComparator implements Comparator<Envelope> {
        @Override
        public int compare(Envelope o1, Envelope o2) { // 返回正数，o2在前面；返回负数，o1在前面
            return o1.l != o2.l ? o1.l - o2.l : o2.h - o1.h;
        }
    }
    // matrix转对象，并且排序
    public static Envelope[] sort(int matrix[][]) {
        Envelope res[] = new Envelope[matrix.length];
        for(int i = 0; i <= matrix.length - 1; i++) {
            res[i] = new Envelope(matrix[i][0], matrix[i][1]);
        }
        Arrays.sort(res, new EnvelopeComparator());
        return res;
    }
    
    // 排好序的数组，高度上玩最长递增子序列
    public static int maxEnvelopes(int[][] matrix) {
        if(matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return 0;
        }
        Envelope res[] = sort(matrix);
        
        int max = 1;
        int right = 0;
        int ends[] = new int[matrix.length];
        ends[0] = res[0].h;
        for(int i = 0; i <= matrix.length - 1; i++) {
            int l = 0;
            int r = right;
            while(l <= r) {
                int mid = l + ((r - l) >> 1);
                if(ends[mid] >= res[i].h) { // 想上一题的例子来理解
                    r = mid - 1;
                } else {
                    l = mid + 1;
                }
            }  
            right = Math.max(right, l);
            ends[l] = res[i].h;
            max = Math.max(max, l + 1);
        }
        return max;
    }
}
