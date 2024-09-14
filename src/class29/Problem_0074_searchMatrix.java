package class29;

// https://leetcode.cn/problems/search-a-2d-matrix
public class Problem_0074_searchMatrix {
    /**
     * 思路1：每一行都进行二分   N * O(logM)
     * 思路2：右上角开始搞。看下面的解法  O(M * N)
     * 思路3：将每一行拼凑起来，第一行连接上第二行。。。成为一个数组，整个数组时有序的，进行二分   O(logM*N)
     * 
     */
    /* 思路3：将每一行拼凑起来，第一行连接上第二行。。。成为一个数组，整个数组时有序的，进行二分   O(logM*N)
    * 由于矩阵的每一行是递增的，且每行的第一个数大于前一行的最后一个数，如果把矩阵每一行拼在一起，
    我们可以得到一个递增数组。
    */
   public boolean searchMatrix(int[][] matrix, int target) {
       if (matrix == null || matrix.length == 0) {
           return false;
       }
       int N = matrix.length;
       int M = matrix[0].length;
       int L = 0;
       int R = M * N - 1;
       while (L <= R) {
           int mid = L + ((R - L) >> 1);
           // 中点对应的在matrix中的位置、
           // 给定一维数组中的一个元素下标（假设从0开始），为了找到该元素在原始矩阵中的行下标，
           // 你需要知道每一行包含多少元素。这个数量就是矩阵的列数。
           // 对于合并之后的数组中的下标i，在原矩阵中对应的下标是[i/M][i % M]
           int cur = matrix[mid /M ][mid % M];
           if(cur == target) {
               return true;
           } else if(cur < target) {
               L = mid + 1;
           } else {
               R = mid - 1;
           }
       }
       return false;
   }
    
    
    /**
     * 思路2解法:  将这个矩阵逆时针选择45度之后，可以发现和二叉搜索树很像，每一个节点左边的值都比它小，右边的值都比它大。
     * 右上角的数就是这个二叉搜索树的根节点。所以能联想到从右上角出发。
     * 
     * 从矩阵的右上角出发，看当前是是不是等于num的，如果是直接返回，
     * 如果当前数大于num，当前数下边都是大于它的，左边都是小于它的，说明要往左边走，
     * 如果当前数小于num，当前数左边都是小于它的，不可能存在等于num的数，当前数下边都是大于它的，
     * 可能存在等于num的数，要往下走
     * 
     * 时间复杂度: 加上矩阵规模是n*m， 那么需要走过的最长路径就是n+m(走第一行和第一列), 时间复杂度就是O(n + m)
     */ 
    public boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        // N行M列
        int N = matrix.length;
        int M = matrix[0].length;
        // 右上角开始
        int row = 0;
        int col = M - 1;
        // 要么向下，要么向左
        // 列号col要大于等于0
        // 行号要小于等于
        while(row <= N - 1 && col >= 0) {
            // 如果等于，直接返回true
            int cur = matrix[row][col];
            if(cur == target) {
                return true;
            } else if(cur > target) { 
                // 如果当前元素大于target
                // 当前元素下面的元素都比它大，左边的元素都比它小。
                // 所以左边有可能存在target。应该去左边找
                col--;
            } else {
                // 如果当前元素小于target
                // 当前元素下面的元素都比它大，左边的元素都比它小。
                // 所以下边有可能存在target。应该去下边找
                row++;
            }
        }
        return false;
    }
}
