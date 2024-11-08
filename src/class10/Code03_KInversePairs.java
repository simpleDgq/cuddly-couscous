package class10;

//测试链接 : https://leetcode.com/problems/k-inverse-pairs-array/
public class Code03_KInversePairs {
    /*
     * 给出两个整数 n 和 k，找出所有包含从 1 到 n 的数字，且恰好拥有 k 个逆序对的不同的数组的个数。

        逆序对的定义如下：对于数组的第i个和第 j个元素，如果满i < j且 a[i] > a[j]，则其为一个逆序对；否则不是。
        
        由于答案可能很大，只需要返回 答案 mod 109 + 7 的值。
        
        示例 1:
        
        输入: n = 3, k = 0
        输出: 1
        解释: 
        只有数组 [1,2,3] 包含了从1到3的整数并且正好拥有 0 个逆序对。
        示例 2:
        
        输入: n = 3, k = 1
        输出: 2
        解释: 
        数组 [1,3,2] 和 [2,1,3] 都有 1 个逆序对。
        说明:
        
         n 的范围是 [1, 1000] 并且 k 的范围是 [0, 1000]。
     */
    
    /**
     * 看数据量，n * k是 10^6次方，说明搞一个n*k的算法就能过。
     * 
     * 动态规划，正好搞一张n * k的表。 dp[n][k]
     * 
     * dp[i][j]:
        当我用1,2,3...一直到i这些数字玩排列的情况下, 正好逆序对数量有j个的排列有几个.
        
     * 第一列: 
     *  用1-1玩排列，搞出0个逆序对的排列有1个。 就是1
     *  用1-2玩排列，搞出1个逆序对的排列有1个。 就是1，2
     *  ... 
     *  第一列就是1
     * 第一行:
     *   用1-1玩排列，搞出0个逆序对的排列有1个。 就是1
     *   用1-1玩排列，搞出1个逆序对的排列有0个。 一个数不可能搞出逆序对
     *   ...
     *   第一行除了第一个位置，其它全是0
     *   
     * 普遍位置: 举例子去分析
     * 1. j<i的时候:
     * dp[5][3]: 1,2,3,4,5这些数字去排列, 降序对的数量是3个的排列有几个?
        根据样本对应模型往往可能性的划分和结尾有关
        看这个5 怎么摆放
        假设1,2,3,4 4个数形成排列的逆序对数的排列我知道, 即dp[4][3]已知
        样本对应模型，往往就是最后的字符做文章:
        1) 5摆放的位置
            abcd5 ,需要剩下的4个数搞出3个，dp[4][3]
            abc5d ,(5,d)是逆序对了，需要剩下的4个数搞出2个，dp[4][2]
            ab5cd ,(5,c)和(5,d)是2个逆序对了，需要剩下的4个数搞出2个，dp[4][1]
            a5bcd ,(5,b)、(5,c)(5,d)是3个逆序对了，需要剩下的4个数搞出0个，dp[4][0]
            5abcd ,这种情况和5结合已经有4对逆序对了，所以忽略。
       dp[5][3] = dp[4][3] + dp[4][2] + dp[4][1] + dp[4][0]
       ==> j < i的时候，dp[i][j] = dp[i - 1][j...0]
       
每个格子都要去遍历它上一行的一些格子，才能得到答案。有枚举行为，需要优化。再举个例子:
       dp[5][4]: 1,2,3,4,5这些数字去排列, 降序对的数量是4个的排列有几个?
       1) 5摆放的位置
            abcd5 ,需要剩下的4个数搞出4个，dp[4][4]
            abc5d ,(5,d)是逆序对了，需要剩下的4个数搞出3个，dp[4][3]
            ab5cd ,(5,c)和(5,d)是2个逆序对了，需要剩下的4个数搞出2个，dp[4][2]
            a5bcd ,(5,b)、(5,c)(5,d)是3个逆序对了，需要剩下的4个数搞出1个，dp[4][1]
            5abcd ,这种情况和5结合已经有4对逆序对了，需要剩下的4个数搞出0个。dp[4][0]
       dp[5][4] = dp[4][4] + dp[4][3] + dp[4][2] + dp[4][1] + dp[4][0]
       
       ==>  j < i的时候， 推出: dp[i][j] = dp[i - 1][j] + dp[i][j - 1]
       
       
       2. j>=i的时候:
       dp[5][7]
        1) 5摆放的位置
            abcd5 ,需要剩下的4个数搞出7个，dp[4][7]
            abc5d ,(5,d)是逆序对了，需要剩下的4个数搞出6个，dp[4][6]
            ab5cd ,(5,c)和(5,d)是2个逆序对了，需要剩下的4个数搞出2个，dp[4][5]
            a5bcd ,(5,b)、(5,c)(5,d)是3个逆序对了，需要剩下的4个数搞出4个，dp[4][4]
            5abcd ,这种情况和5结合已经有4对逆序对了，需要剩下的4个数搞出3个。dp[4][3]
        dp[5][7] = dp[4][7] + dp[4][6] + dp[4][5] + dp[4][4] + dp[4][3]  
          
        dp[5][8]
        1) 5摆放的位置
            abcd5 ,需要剩下的4个数搞出8个，dp[4][8]
            abc5d ,(5,d)是逆序对了，需要剩下的4个数搞出7个，dp[4][7]
            ab5cd ,(5,c)和(5,d)是2个逆序对了，需要剩下的4个数搞出6个，dp[4][6]
            a5bcd ,(5,b)、(5,c)(5,d)是3个逆序对了，需要剩下的4个数搞出5个，dp[4][5]
            5abcd ,这种情况和5结合已经有4对逆序对了，需要剩下的4个数搞出4个。dp[4][4]
        dp[5][8] = dp[4][8] + dp[4][7] + dp[4][6] + dp[4][5] + dp[4][4]
        ==>  结合这两个例子:
            j >= i的时候， 推出: dp[i][j] = dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - i]
       */    
    public int kInversePairs(int n, int k) {
        if(n <= 0 || k < 0) {
            return 0;
        }
        int dp[][] = new int[n + 1][k + 1];
        dp[0][0] = 1; // 0搞出0个逆序对，1种
        int mod = 1000000007;
        // 从上往下，从左往右填表
        for (int i = 1; i <= n; i++) {
            dp[i][0] = 1;
            for(int j = 1; j <= k; j++) {
//                if(j < i) {
//                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
//                } else {
//                    dp[i][j] = dp[i - 1][j] + dp[i][j - 1] - dp[i - 1][j - i];
//                }
                dp[i][j] = (dp[i - 1][j] + dp[i][j - 1])  % mod;
                if(j >= i) {
                    dp[i][j] = (dp[i][j] - dp[i - 1][j - i] + mod) % mod;;
                }
            }
        }
        return dp[n][k];
    }
    
}
