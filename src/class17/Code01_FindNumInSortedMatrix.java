package class17;

// https://leetcode.cn/problems/search-a-2d-matrix-ii/
public class Code01_FindNumInSortedMatrix {
    
    /**
     * 给定一个每一行有序、每一列也有序，整体可能无序的二维数组
        再给定一个数num，
        返回二维数组中有没有num这个数
     */
    
    /**
     * 思路:  将这个矩阵逆时针选择45度之后，可以发现和二叉搜索树很像，每一个节点左边的值都比它小，右边的值都比它大。
     * 右上角的数就是这个二叉搜索树的根节点。所以能联想到从右上角出发。
     * 
     * 从矩阵的右上角出发，看当前是是不是等于num的，如果是直接返回，
     * 如果当前数大于num，当前数下边都是大于它的，左边都是小于它的，说明要往左边走，
     * 如果当前数小于num，当前数左边都是小于它的，不可能存在等于num的数，当前数下边都是大于它的，
     * 可能存在等于num的数，要往下走
     * 
     * 时间复杂度: 加上矩阵规模是n*m， 那么需要走过的最长路径就是n+m(走第一行和第一列), 时间复杂度就是O(n + m)
     */ 
    public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix == null || matrix.length == 0 || 
                matrix[0] == null || matrix[0].length == 0) {
            return false;
        }
        int N = matrix.length;
        int M = matrix[0].length;
        // 最开始从右上角开始走
        int row = 0;
        int col = M - 1;
        int cur = 0;
        while(row <= N - 1 && col >= 0) {
            cur = matrix[row][col];
            if(cur == target) {
                return true;
            } else if(cur > target) {
                col--;
            } else {
               row++; 
            }
        }
        return false;
    }

}
