package class22;

import java.util.Stack;

public class Code04_VisibleMountains {
    
    /**
     * 一个不含有负数的数组可以代表一圈环形山，每个位置的值代表山的高度。比如， {3,1,2,4,5}、{4,5,3,1,2}或{1,2,4,5,3}都代表同样结构的环形山。 
     * 山峰A和山峰B能够相互看见的条件为: 
     * 1.如果A和B是同一座山，认为不能相互看见。 
     * 2.如果A和B是不同的山，并且在环中相邻，认为可以相互看见。
     * 3.如果A和B是不同的山，并且在环中不相邻，假设两座山高度的最小值为min。
     *   1)如果A通过顺时针方向到B的途中没有高度比min大的山峰，认为A和B可以相互 看见
     *   2)如果A通过逆时针方向到B的途中没有高度比min大的山峰，认为A和B可以相互 看见
     *   3)两个方向只要有一个能看见，就算A和B可以相互看见 给定一个不含有负数且没有重复值的数组 arr，请返回有多少对山峰能够相互看见。
     * 
     * 进阶: 给定一个不含有负数但可能含有重复值的数组arr，返回有多少对山峰能够相互看见。
     */
    
    /**
     * 思路:
     * 
     * 1. 如果数组中没有重复的数，O(1)的时间复杂度就能做到，有公式 -> 2*N - 3
     * 
     * 首先有个贪心: 一定是从小数去找大数，这样能够找全，而且不会重复。
     * 假设数组中没有重复值，那么我在这个环形山中，就一定能够找到一个max和一个次max
     * 假设有一个数x, 如果X它必须是小数姿态，它去小找大能找到几对?
     * 
     * 1）x按顺时针(朝向max的)方向如果找到刚刚大于x的就停, 必存在这么一个数, 最不济这个数就是max
     * 如果还存在别的大于X的数，那其实就是在x跟max中间, 而且不会跨过max到更后方
     * 2）x按逆时针(朝向次max的)方向如果找到刚刚大于x的就停,必存在这么一个数,最不济这个数就是次max
     * 如果还存在别的大于X的数，那其实就是在x跟次max中间,而且不会跨过次max到更后方
     * 从x小找大, 就是x到上面两个找到的两个数, 两对
     * ==>
     * N个值, 除去最大值跟次大值, 还剩N-2个点
     * 这N-2个点中，任何一个 X 秉持小找大的原则都有两对, 共有(N−2)∗2﻿对
     * 再加上max跟次max这1对，加上, 最后公式就是2N-3
     * 只适用于arr中没有重复值的时候, 可以O(1)的时间得到结果
     * 
     * 
     * 2. 有重复数的时候，就需要分析 --> 看思维导图题解
     * 
     * 用单调栈，从上到下，遵循由小到大的原则
     * 从环中找一个最大值，哪一个都可以，加入栈
     * 然后考察最大值的下一个位置，如果下个位置的值比栈顶元素小，加入栈，如果下个位置的元素，等于当前栈顶元素，
     * 栈顶元素的次数times加1;
     * 一直加入，如果遇到一个比当前栈顶元素大的值，破坏了由小到大的规则，不能加入栈，
     * 栈顶元素出栈，结算答案: 对于它压着的元素来说，弹出的元素到压着的元素能够形成一对, 如果栈顶弹出的数有k个，则有k对；
     * 弹出的元素到当前遇到的元素，能够形成一对，如果栈顶弹出的数有k个，则有k对; 同时对于内部，假设弹出的元素有k个，那么就能形成C(k 2)对。
     * 
     * ==> 弹出的过程中，如果栈顶弹出的数有k个: 对外: 2*k 对内: C(k 2) --> 2k + C(k 2)
     * 
     * 遍历完一圈之后，如果栈中还有元素怎么办?
     * 需要清算栈中的元素:
     * 1. 如果整个一圈已经转完了,单独清算的时候, 只要你不是倒数两行数据公式和之前一样. --> 因为栈顶弹出的元素，在顺时针和逆时针方向一定能够遇到两个不同的大于
     * 它的元素（入栈的时候，是逆时针入的，大的值在下面，而且大的值的种类是多余等于两个的，逆时针或者顺时针找，一定能遇到两个不同的值，这样就能形成2*k对）。
     * 2. 如果是栈的第二个元素
     *    1) 如果压着的栈底元素，只出现了一次，那么弹出的栈顶元素到栈底元素只会形成1对，两个方向上会遇到相同的栈底元素，假设弹出的元素有k个，那么就能形成1*k个，对内有C(k 2)对
     *    2) 如果压着的栈底元素，出现的次数大于等于2(这里代码要注意)，那么弹出的栈顶元素到栈底元素只会形成2对，两个方向上会遇到不同的栈底元素，假设弹出的元素有k个，那么就能形成2*k个，对内有C(k 2)对
     * 3. 如果是栈的最后一个元素: 因为是最大值，永远小找大，一对也没有，只有内部的: C(k 2)
     */
    // 栈中放的记录，
    // value就是指，times是收集的个数
    public static class Record {
        public int value;
        public int times;

        public Record(int value) {
            this.value = value;
            this.times = 1;
        }
    }
    
    public static int getVisibleNum(int nums[]) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int size = nums.length;
        // 单调栈，从上到下，遵循由小到大的规则
        Stack<Record> stack = new Stack<Record>();
        // 环上找一个最大值，放入栈中
        int maxIndex = 0;
        int max = nums[0];
        for(int i = 1; i <= size - 1; i++) {
            if(nums[i] > max) {
                max = nums[i];
                maxIndex = i;
            }
        }
        // 最大值放入栈
        stack.push(new Record(max));
        // 获取最大值的下一个位置
        int nextIndex = nextIndex(maxIndex, size);
        // res记录答案
        int res = 0;
        // 从最大值出发，遍历考察每一个元素
        while(nextIndex != maxIndex) { // 如果nextIndex不等于maxIndex，说明一圈没有走完
            // 栈中有元素，而且栈顶元素比当前元素小，一直弹出，结算答案
            while(!stack.isEmpty() && stack.peek().value < nums[nextIndex]) { 
                int k = stack.pop().times;
                res += 2 * k + getInternalSum(k);
            }
            // 取出栈顶元素，和当前元素进行比较
            if(stack.peek().value > nums[nextIndex]) { // 栈顶元素大于当前元素，直接加入栈
                stack.push(new Record(nums[nextIndex]));
            } else if(stack.peek().value == nums[nextIndex]) {// 栈顶元素大于当前元素，栈顶元素的次数加1
                stack.peek().times++;
            }
            // 继续下一个元素
            nextIndex = nextIndex(nextIndex, size);
        }
        // 清算阶段
        // 栈中元素个数大于等于3个, 一直弹出，并进行结算
        while(stack.size() >= 3) {
            int k = stack.pop().times;
            res += 2 * k + getInternalSum(k);
        }
        // 栈中元素个数等于2个
        if(stack.size() == 2) {
            int k = stack.pop().times;
            // 压着的值的个数
            int times = stack.peek().times; // 特别注意这里，是要看栈底的元素有多少个，times是1，则对应弹出的元素，每个只会形成1对，times
                                            // 大于等于2，每个元素就会形成2对
            res += getInternalSum(k) + (times == 1 ? k : 2 * k); // 这里出错了，找了半天。。。
        }
        // 栈中元素个数只有1个
        if(stack.size() == 1) {
            int k = stack.pop().times;
            res += getInternalSum(k);
        }
        return res;
    }
    
    // 如果k==1返回0，如果k>1返回C(2,k)
    public static int getInternalSum(int k) {
        return k == 1 ? 0 : (k * (k - 1) / 2);
    }
    
    // 环形数组中当前位置为i，数组长度为size，返回i的下一个位置
    public static int nextIndex(int i, int size) {
        return (i < (size - 1)) ? (i + 1) : 0;
    }

    
    
    
    
    
    
    
    
    
    // 下面的都是测试代码 -- for test
    public static int getVisibleNum2(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        int N = arr.length;
        int maxIndex = 0;
        // 先在环中找到其中一个最大值的位置，哪一个都行
        for (int i = 0; i < N; i++) {
            maxIndex = arr[maxIndex] < arr[i] ? i : maxIndex;
        }
        Stack<Record> stack = new Stack<Record>();
        // 先把(最大值,1)这个记录放入stack中
        stack.push(new Record(arr[maxIndex]));
        // 从最大值位置的下一个位置开始沿next方向遍历
        int index = nextIndex(maxIndex, N);
        // 用“小找大”的方式统计所有可见山峰对
        int res = 0;
        // 遍历阶段开始，当index再次回到maxIndex的时候，说明转了一圈，遍历阶段就结束
        while (index != maxIndex) {
            // 当前数要进入栈，判断会不会破坏第一维的数字从顶到底依次变大
            // 如果破坏了，就依次弹出栈顶记录，并计算山峰对数量
            while (stack.peek().value < arr[index]) {
                int k = stack.pop().times;
                // 弹出记录为(X,K)，如果K==1，产生2对; 如果K>1，产生2*K + C(2,K)对。
                res += getInternalSum(k) + 2 * k;
            }
            // 当前数字arr[index]要进入栈了，如果和当前栈顶数字一样就合并
            // 不一样就把记录(arr[index],1)放入栈中
            if (stack.peek().value == arr[index]) {
                stack.peek().times++;
            } else { // >
                stack.push(new Record(arr[index]));
            }
            index = nextIndex(index, N);
        }
        // 清算阶段开始了
        // 清算阶段的第1小阶段
        while (stack.size() > 2) {
            int times = stack.pop().times;
            res += getInternalSum(times) + 2 * times;
        }
        // 清算阶段的第2小阶段
        if (stack.size() == 2) {
            int times = stack.pop().times;
            res += getInternalSum(times)
                    + (stack.peek().times == 1 ? times : 2 * times);
        }
        // 清算阶段的第3小阶段
        res += getInternalSum(stack.pop().times);
        return res;
    }
    
    // for test
    public static int[] getRandomArray(int size, int max) {
        int[] arr = new int[(int) (Math.random() * size)];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (int) (Math.random() * max);
        }
        return arr;
    }

    // for test
    public static void printArray(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int size = 10;
        int max = 10;
        int testTimes = 3000000;
//        int arr[] = {2, 7, 2, 5};
//        getVisibleNum(arr);
//        System.out.println("test begin!");
        for (int i = 0; i < testTimes; i++) {
            int[] arr = getRandomArray(size, max);
            if (getVisibleNum(arr) != getVisibleNum2(arr)) {
                printArray(arr);
                System.out.println(getVisibleNum(arr));
                System.out.println(getVisibleNum2(arr));
                break;
            }
        }
        System.out.println("test end!");
    }

}
