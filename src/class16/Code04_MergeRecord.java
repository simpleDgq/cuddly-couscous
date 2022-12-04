package class16;

public class Code04_MergeRecord {
    /**
     * 腾讯原题
     * 
     * 给定整数power，给定一个数组arr，给定一个数组reverse。含义如下：
     * arr的长度一定是2的power次方，reverse中的每个值一定都在0~power范围。
     * 例如power = 2, arr = {3, 1, 4, 2}，reverse = {0, 1, 0, 2}
     * 任何一个在前的数字可以和任何一个在后的数组，构成一对数。可能是升序关系、相等关系或者降序关系。
     * 比如arr开始时有如下的降序对：(3,1)、(3,2)、(4,2)，一共3个。
     * 接下来根据reverse对arr进行调整：
     * reverse[0] = 0, 表示在arr中，划分每1(2的0次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [3,1,4,2]，此时有3个逆序对。
     * reverse[1] = 1, 表示在arr中，划分每2(2的1次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [1,3,2,4]，此时有1个逆序对
     * reverse[2] = 0, 表示在arr中，划分每1(2的0次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [1,3,2,4]，此时有1个逆序对。
     * reverse[3] = 2, 表示在arr中，划分每4(2的2次方)个数一组，然后每个小组内部逆序，那么arr变成
     * [4,2,3,1]，此时有5个逆序对。
     * 所以返回[3,1,1,5]，表示每次调整之后的逆序对数量。
     * 
     * 输入数据状况：
     * power的范围[0,20]
     * arr长度范围[1,10的7次方]
     * reverse长度范围[1,10的6次方]
     * 
     * */
    
    /**
     * 思路: 
     * 求逆序对: 
     * 可能的想法: 可以看数组中每一个数，和它后面的数比较，如果后面的数比它小，就是逆序对；所有的结果累加，就是答案。
     * 
     * 采用新的视角做
     *   逆序对的拆分
     *   [6 ,4 , 2, 5, 1, 3, 0, 7]
     *   1) 2^1个数一组, 组内有几个逆序对. [6 ,4] [2, 5] [1, 3] [0, 7] -> 答案1
     *   2) 2^2一组, 不重复计算2^1的, 组内有几个逆序对。[6 ,4 , 2, 5]  [1, 3, 0, 7], 
     *   分成左组2个元素，右组2个元素, 因为需要不重复计算2^1的, 所以6要和2比较，然后和5比较，这样去求逆序对数量
     *   3) 2^3一组, 不包括1),2)的, 有几个逆序对
     *   4) 所有逆序对数加起来就是8个数的时候的逆序对的数量。
     * =======  
     * =======  
     * 1) 假设数组有8个数, 记录这些数值
     *  记录2个数一组, 正序对, 逆序对的数量
     *  记录4个数一组, 正序对, 逆序对的数量
     *  记录8个数一组, 正序对, 逆序对的数量
     * 当reverse=2, 2^2=4, 4个数一组的时候, 组内逆序
     *   不影响8个数一组的正序对, 逆序对数。 只影响4个数一组和2个数一组的正序对, 逆序对数。因为求8的答案的时候，是分成了左组4个元素、右组4个
     *   又不重复计算2^2和2^1的，所以是左组的元素和右组的元素进行比较，你逆序了前4个元素，或者右组的4个元素，在求8的答案的时候，是不影响的
     *   这时候: 逆序之前的正序对数，就是现在的逆序数。之前的逆序数，就是正序数。
     *   4个数的正序对数, 逆序对数交换
     *   2个数的正序对数, 逆序对数交换
     * 那么reverse=2的时候，求每次逆序之后的答案: 其实就是交换逆序对数，和正序对之后，再将所有的2个数一组，4个数一组，8个数一组所有的逆序对数加起来。
     *
     * 2) revese数值决定了你能够影响的范围
     *    2^3=8, 16跟32的不用动, 2,4,8的交换
     *   
     * 3) 利用MergeSort里面的Merge行为就可以把逆序对和正序对信息统计好。每次交换完成之后，对应的逆序对数加起来就是答案。
     * 
     * 大的思路:
     * 先求原始数组的正序对数和逆序对数，存在数组里面，然后对应每一个reverse, 交换受影响的正序对数和逆序对数，最后累加1~power对应的逆序对数，
     * 就是当前reverse对应的答案，放入数组中
     */
    public static int[] reversePair2(int[] originArr, int[] reverseArr, int power) {
        if(originArr == null || originArr.length == 0 || reverseArr == null || reverseArr.length == 0 || power < 0) {
            return null;
        }
        // 将原始数组逆序copy一份
        int reverse[] = copyArray(originArr);
        // reverseArr里面的数不会超过power。 有可能是0，所以数组长度是power+1
        int recordDown[] = new int[power + 1]; // 记录power对应的逆序对的数量
        int recordUp[] = new int[power + 1];// 记录power对应的正序对的数量
        // 求原始数组的每一个power下的逆序对数量，记录在对应的recordDown数组中
        process(0, originArr.length - 1, power, recordDown, originArr);
        // 求原始数组的每一个power下的正序对数量，记录在对应的recordUp数组中
        // 原始数组的逆序数组，再求逆序对数，就是原始数组的正序对数
        process(0, reverse.length - 1, power, recordUp, reverse);
        // 对于每一个，逆序对应的组，然后求答案
        int ans[] = new int[reverseArr.length];
        for (int i = 0; i <= reverseArr.length - 1; i++) { 
            int curPower = reverseArr[i];
            // 每次逆序之后，只会影响数字个数小于等于2^curPower次方的组的答案
            // 这些组的正序对，逆序对数需要交换
            for(int j = 1; j <= curPower; j++) {
                int temp = recordDown[j];
                recordDown[j] = recordUp[j];
                recordUp[j] = temp;
            }
            // 交换完之后累加得到答案
            for(int j = 1; j <= power; j++) {
                ans[i] += recordDown[j];
            } 
        }
        return ans;
    }
    
    // L到R范围进行归并排序
    // L~M M+1~R都拍好序了，这两部分去merge
    // L~R长度是2^power次方
    // record记录对应的power逆序对数量
    public static void process(int L, int R, int power, int record[], int arr[]) {
        if(L == R) { // 只有一个元素了，不用排序，直接返回
            return;
        }
        int M = L + ((R - L) >> 1);
        // 左边排好序
        // 左边的元素个数是2^(power - 1) 次方
        process(L, M, power - 1, record, arr);
        // 右边排好序
        process(M + 1, R, power - 1, record, arr);
        // 合并左右部分，求左右部分能够产生的逆序对数，累加
        // 左右拍好序了，左右部分能够产生多少逆序对，放到merge里去搞
        // 2的power次方为一组的时候，逆序对数 --> 搜集的信息填在power位置，如果是8个数，就填在3位置，4个数就填在2位置....
        // 为什么是累加?而不是等于. 加上大范围是8个数，先回切成左边4个数，右边4个数，又会切成左2，右2; 左2，右2两个部分
        // 每个部分的答案累加起来才是8的答案啊
        record[power] += merge(L, R, M, arr);
    }
    public static int merge(int L, int R, int M, int arr[]) {
        int N = R - L + 1;
        int p1 = L;
        int p2 = M + 1;
        int ans = 0; // 搜集答案
        int help[] = new int[N];
        int i = 0;
        while(p1 <= M && p2 <= R) {
          // 为什么是M - p1 + 1？ 因为左边是排好序的，当前p1位置的数大于p2位置的数，那么左边p1后面的数也一定是大于p2位置的数的，这时候
          // p1后面的每一个数都能和p2位置的数搞出一个逆序对
          ans +=  arr[p1] > arr[p2]? (M - p1 + 1): 0;
          // 谁小拷贝谁，归并排序
          help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        // 左边还有数
        while(p1 <= M) {
            help[i++] = arr[p1++]; 
        }
        // 右边还有数
        while(p2 <= R) {
            help[i++] = arr[p2++]; 
        }
        // 将help数组拷贝到原始数组中
        for(int j = 0; j <= help.length - 1; j++) {
            arr[L + j] = help[j];
        }
        return ans;
    }
    
    public static int[] copyArray(int[] arr) {
        if (arr == null) {
            return null;
        }
        int[] res = new int[arr.length];
        for (int i = arr.length - 1, j = 0; i >= 0; i--, j++) {
            res[j] = arr[i];
        }
        return res;
    }
}
