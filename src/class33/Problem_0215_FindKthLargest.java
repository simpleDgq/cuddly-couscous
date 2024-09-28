package class33;

// 215. 数组中的第K个最大元素
// https://leetcode.cn/problems/kth-largest-element-in-an-array
public class Problem_0215_FindKthLargest {
    /**
     * 思路1： 改写快排
     * 第K大，那么就是从大到小排序。课上讲的是第k小，是从小到大排序
     * 
     * 在快排的过程中，做完一次partition，大于v的放左边，小于v的放右边，等于v的放中间
     * 然后看k在不在等于区，在的话就命中了，直接返回
     * 如果不在的话，如果k-1比等于区的左边界小，那么就去左边找
     * 否则去右边找，递归下去
     * 
     * 掌握下面的非递归写法 O(N)
     * 
     * 思路2：可以用小根堆
     * 先放k个元素到堆中，然后剩下的元素和堆顶比较，
     * 如果大于堆顶元素，则放入堆。最终堆顶元素就是第k大的元素
     */
    
    /**
     * 非递归写法
     */
    public int findKthLargest(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k > nums.length) {
            return Integer.MIN_VALUE;
        }
        // L到R范围上做划分
        int L = 0;
        int R = nums.length - 1;
        int index = k - 1;
        while (L < R) {
            int X = nums[L + (int) (Math.random() * (R - L + 1))];
            int equals[] = partition(L, R, nums, X);
            if (index < equals[0]) { // 如果index比等于区左边界还小，继续去大于区找
                R = equals[0] - 1;
            } else if (index > equals[1]) { // 如果index比等于区右边界还大，继续去小于区找
                L = equals[1] + 1;
            } else {
                return X;
            }
        }
        return nums[L];
    }

    public int[] partition(int L, int R, int nums[], int X) {
        int more = L - 1; // 大于区在左边
        int less = R + 1; // 小于区在右边
        int index = L;
        while (index < less) { // 没有和小于区边界撞上
            if (nums[index] < X) { // 如果当前数小于划分值
                swap(nums, index, --less); // 当前数和小于区的前一个数交换，当前数不变，小于区左扩
            } else if (nums[index] > X) {// 如果当前数大于划分值
                swap(nums, index++, ++more);// 当前数和大于区的下一个数交换，当前数跳下一个，大于区右扩
            } else {// 相等，直接跳下一个
                index++; 
            }
        }
        return new int[] { more + 1, less - 1 };
    }
    
    /**
     * 递归写法
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return Integer.MIN_VALUE;
        }
        return process(0, nums.length - 1, nums, k - 1);
    }

    // index是要找的数的index。第k个数的下标是k-1，所以主函数里面应该用k-1
    public int process(int L, int R, int[] nums, int index) {
        if (L > R) {
            return -1;
        }
        if (L == R) {
            return nums[L];
        }
        int ans = Integer.MIN_VALUE;
        // [L,R]范围上随机选一个数作为划分值
        int X = nums[L + (int) (Math.random() * (R - L + 1))];
        // 划分椅子
        int equal[] = partition(L, R, nums, X);
        if (index >= equal[0] && index <= equal[1]) { // 如果index在小于区，命中直接返回
            ans = nums[index];
        } else if (index < equal[0]) { // 如果index比小于区的左边界还小，继续去小于区找
            ans = process(L, equal[0] - 1, nums, index);
        } else { // 否则去大于区找
            ans = process(equal[1] + 1, R, nums, index);
        }
        return ans;
    }

    public void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
