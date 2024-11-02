package class28;

// 27. 移除元素
public class Problem_0027_removeElement {
    /**
     * 思路：双指针。
     * 一个L指针指向开始，一个指针R指向最后，如果L位置的数等于val，则交换L位置和R位置的数
     * R--，因为L位置是从后面交换过去的数，还没有看，所以L不变；如果L位置的数不等于val，则L++
     */
    public int removeElement2(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int L = 0;
        int R = nums.length - 1;
        while (L <= R) {
            if (nums[L] == val) {
                swap(L, R--, nums);
            } else {
                L++;
            }
        }
        return L;
    }

    public void swap(int i, int j, int nums[]) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    /**
     * 不使用交换的思路
     * 两个指针，一个L指向0位置，一个R指向数组的最后一个元素
     * 如果L位置的元素等于val，那么将R位置的元素直接赋值给L位置，R--
     * L位置是刚交换过来的元素，还没看，L不变；
     * 如果L位置的元素不等于val，那么L++
     */
    public int removeElement(int[] nums, int val) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int L = 0;
        int R = nums.length - 1;
        while (L <= R) {
            if (nums[L] == val) {
                nums[L] = nums[R--];
            } else {
                L++;
            }
        }
        return L;
    }
}
