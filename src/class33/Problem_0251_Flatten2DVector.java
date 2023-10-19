package class33;

// 251 展开二维向量
public class Problem_0251_Flatten2DVector {
    /**
     * 请设计并实现一个能够展开二维向量的迭代器。该迭代器需要支持 next 和 hasNext 两种操作。
     *
     * 示例：
     * Vector2D iterator = new Vector2D([[1,2],[3],[4]]);
     * iterator.next(); // 返回 1
     * iterator.next(); // 返回 2
     * iterator.next(); // 返回 3
     * iterator.hasNext(); // 返回 true
     * iterator.hasNext(); // 返回 true
     * iterator.next(); // 返回 4
     * iterator.hasNext(); // 返回 false
     * 注意：
     *  请记得 重置 在 Vector2D 中声明的类变量（静态变量），因为类变量会 在多个测试用例中保持不变，影响判题准确。请 查阅 这里。
     *  
     *  你可以假定 next() 的调用总是合法的，即当 next() 被调用时，二维向量总是存在至少一个后续元素。
     * 
     *  进阶：尝试在代码中仅使用 C++ 提供的迭代器 或 Java 提供的迭代器。
     */
    
    /**
     * 
     * 思路: 
     *  curUse: 来到的位置有没有使用过，false表示没有使用过
     *  当多次调用hasNext的时候，如果curUse是false，表示没有使用过，直接返回true，表示还有next元素;
     *  如果curUse是true，则计算下一个可用的位置，找到了就将curUse设置成false
     */
    public static class Vector2D {
        private int[][] matrix;
        private int row; // 光标所在的行
        private int col; // 光标所在的列
        private boolean curUse; // 来到的位置是否使用过
        
        public Vector2D(int v[][]) {
            matrix = v;
            row = 0;
            col = -1; // 列从-1位置开始
            curUse = true; // 认为0，-1使用过
            // 调用hasNext计算下一个可用位置
            hasNext();
        }
        
        public boolean hasNext() {
            if(row == matrix.length) { // 行已经超过 matrix.length，没有了
                return false;
            }
            // curUse是false，表示可用
            // 多次调用haseNext的时候curUse一直是false，直接返回
            if(!curUse) {
                return true;
            }
            // row 没有到达最后，当前元素用过
            // 需要计算下一个可用位置
            if(col != matrix[row].length - 1) {
                col++;
            } else {
                // 到达了最后一列，跳到下一个新的行
                col = 0;
                do {
                   row++;
                } while(row != matrix.length && matrix[row].length == 0); // 没有到达最后一行，如果是空的行，则需要row++，跳到新行
            }
            // 新的(row, col)
            if(row != matrix.length) {
                curUse = false;
                return true;
            } else {
                return false;
            }
        }
        
        public int next() {
            // 返回当前的值
            int ans = matrix[row][col];
            curUse = true; // 标记使用过
            hasNext(); // 重新计算新的可用的位置
            return ans;
        }
    }
}
