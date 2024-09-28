package class29;

// https://leetcode.cn/problems/sort-colors
// 75. 颜色分类
public class Problem_0075_SortColors {
    /**
     * 荷兰国旗问题
     * 将小于1的放左边，等于1的放中间，大于1的放右边
     * 用1作为划分值
     * 划分思路：
     * 有一个小于区，从-1位置开始，有一个大于区从arr.length开始。
     * 如果index没有撞上大于区边界：
     * 1. 如果当前元素小于划分值，则当前元素和小于区的下一个元素进行交换，当前元素跳下一个，小于区右扩
     * 2. 如果当前元素等于划分值，当前元素直接调下一个。
     * 3. 如果当前元素大于划分值，当前元素和大于区的前一个数交换，当前数不动(当前数是从后面交换过去的，还没有比较，当前数不能动)，大于区左扩
     * 4.index撞上大于区表示结束。返回等于区的开始和结束位置。开始结束位置就是小于区和大于区的边界less+1，more-1 (因为小于区是从-1开始，大于区是从N开始
     * 扩在less的数是小于区的，less+1就是等于区；同理，扩在more里面的都是大于区的，more-1就是等于区)
     * 
     */
    public void sortColors(int[] nums) {
        if (nums == null || nums.length == 0) {
            return;
        }
        int N = nums.length;
        int less = -1; // 小于区从-1开始
        int more = N; // 大于区从N开始
        int index = 0;
        while (index < more) { // index没有撞上大于区边界
            if (nums[index] == 1) {// 如果当前数等于划分值，直接搞下一个数
                index++;
            } else if (nums[index] > 1) { // 如果当前数大于划分值，当前数和大于区的前一个数交换,大于区左扩，当前数不变
                swap(index, --more, nums);
            } else {// 如果当前数小于划分值，当前数和小于区的下一个数交换,小于区右扩，搞下一个数
                swap(index++, ++less, nums);
            }
        }
    }

    public void swap(int i, int j, int arr[]) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
