package class24;

import java.util.Arrays;

public class Code01_KthMinPair {
    /**
     * 长度为N的数组arr，一定可以组成N^2个数值对。
        例如arr = [3,1,2]，
        数值对有(3,3) (3,1) (3,2) (1,3) (1,1) (1,2) (2,3) (2,1) (2,2)，
        也就是任意两个数都有数值对，而且自己和自己也算数值对。
        数值对怎么排序？规定，第一维数据从小到大，第一维数据一样的，第二维数组也从小到大。所以上面的数值对排序的结果为：
        (1,1)(1,2)(1,3)(2,1)(2,2)(2,3)(3,1)(3,2)(3,3)
        
        给定一个数组arr，和整数k，返回第k小的数值对。
     */
    
    /**
     * 思路: 
     * 1. 暴力解，循环生成N^2对这个数，然后排序，取第K对，时间复杂度O(N^2*logN^2)
     * 2. 如果数组本身就是有序的
     * 例如[1,1,1,2,2,3,3,3,3,5,5], 要求的是第70小的数值对？
     * 怎么求?
     * 假设这个数值对是(a,b), 怎么搞定第一位数据a?
     * 
     * 对数据进行分组，数组总共11个数。
     * 
     * 必须以0位置的1作为第一维数据的有11个，   11
     * 必须以1位置的1作为第一维数据的有11个，   22
     * 必须以2位置的1作为第一维数据的有11个，   33
     * 必须以3位置的2作为第一维数据的有11个，   44
     * 必须以4位置的2作为第一维数据的有11个，   55
     * 必须以5位置的3作为第一维数据的有11个，   66
     * 必须以6位置的3作为第一维数据的有11个，   77 --> (a,b)是第70个数，一定落在这个组里面，所以第一维数据a是3
     * 
     * 你想求第70个数怎么定位a? 就看看它是哪一个组里面的，那它第一维的数，就是a
     * 
     * 接下来怎么搞的第二维数据b?
     * b肯定是3组里面的数值对，3之前一共搞定了55个，那(a,b)这个组一定是3作为一维数据的这个组里面的第15个
     * 
     * 一共有4个3, 
     * 必须以0位置的1作为第二维数据的，4个    4
     * 必须以1位置的1作为第二维数据的，4个    8
     * 必须以2位置的1作为第二维数据的，4个    12
     * 必须以3位置的2作为第二维数据的，4个    16  --> 二维是第15个，一定落在这个组里面，所以第二维数据b是2
     * 
     * 
     * 抽象:
     * 假设一共N个数，求第K小的数值对，怎么定位第一维数据a？ 
     * 如果是在有序数组中:
     *   N = 11, K = 70 --> 70 / 11 = 6 --> K/N 对应的下标的值
     *   边界: N=7, K = 7的时候， 7/7 = 1
     *   但是实际上第7小的组的第一维数据是下标为0的位置，所以 是 (K - 1) / N
     * 如果是在无序数组中:  
     *   有序数组中 (K - 1) / N位置的数，其实就是无序数组中第 (K - 1) / N小的数，直接改写快排或者bfprt复杂度可以搞定!
     * 
     * 怎么定位第二维数据?
     *   统计出数组中小于第一维数的有几个，假设lessFirstNumberSize，等于第一维数的有几个，假设firstNumberSize
     *   lessFirstNumberSize * N能知道前面的数帮你搞定了几个，用K - lessFirstNumberSize * N求出第二维数是所属的组里面的第几个，
     *   那么无序数组中第 (rest - 1) / firstNumberSize 小的数就是第二维的数。
     *  
     * 解释为什么(rest - 1) / firstNumberSize 小的数就是第二维的数？
     * 
     * 有序数组中，
     * 例子, 假设第一维的数字是5，等于5的数字有5个，比它小的数有4个
     * 要求的是第一维数字固定是5的所有组中，第23个是多少？第23个的第二维就是答案
     * 
     * 0位置的数搞定5个  5
     * 1位置的数搞定5个  10
     * 2位置的数搞定5个  15
     * 3位置的数搞定5个  20
     * 4位置的数搞定5个  25
     * 
     * 有序数组中，4位置的数就是第二维的答案 --> (23 - 1) / 5 = 4
     * 
     * 也就是在无序数组中求(rest - 1) / firstNumberSize 小的数。
     * 
     */
    
    
    // 直接使用array.sort进行排序
    // O(N*logN)的复杂度，你肯定过了
    public static int[] kthMinPair2(int[] arr, int k) {
        int N = arr.length;
        if (k > N * N) {
            return null;
        }
        // O(N*logN)
        Arrays.sort(arr);
        // 第K小的数值对，第一维数字，是什么 是arr中
        int fristNum = arr[(k - 1) / N];
        int lessFristNumSize = 0;// 数出比fristNum小的数有几个
        int fristNumSize = 0; // 数出==fristNum的数有几个
        // <= fristNum
        for (int i = 0; i < N && arr[i] <= fristNum; i++) {
            if (arr[i] < fristNum) {
                lessFristNumSize++;
            } else {
                fristNumSize++;
            }
        }
        int rest = k - (lessFristNumSize * N);
        return new int[] { fristNum, arr[(rest - 1) / fristNumSize] };
    }
    
    // O(N)
    public static int[] kthMinPair(int[] arr, int k) {
        int N = arr.length;
        if (k > N * N) {
            return null;
        }
        
        int firstNuuber = getKMinth(arr, k);
        int lessFristNumSize = 0;// 数出比fristNum小的数有几个
        int fristNumSize = 0; // 数出==fristNum的数有几个
        // <= fristNum
        for (int i = 0; i < N && arr[i] <= firstNuuber; i++) {
            if (arr[i] < firstNuuber) {
                lessFristNumSize++;
            } else {
                fristNumSize++;
            }
        }
        int rest = k - (lessFristNumSize * N);
        return new int[] {firstNuuber, getKMinth(arr, (rest - 1) / fristNumSize)};        
    }
    
    // 改写快排，非递归，求无序数组中第K小的数，O(N)搞定
    public static int getKMinth(int arr[], int K) {
        int L = 0;
        int R = arr.length - 1;
        int pivot = 0;
        int range[] = null;
        while(L < R) {
            pivot = arr[L + (int)(Math.random() * (R - L + 1 ))];
            range = partition(arr, L, R, pivot);
            if(K < range[0]) { // 去左边机选找
                R = range[0] - 1;
            } else if(K > range[1]) { // 去右边继续找
                L = range[1] + 1;
            } else {
                return pivot;
            }
        }
        // L == R的情况，直接返回
        return arr[L];
    }
    
    public static int[] partition(int[] arr, int L, int R, int pivot) {
        int less = L - 1;
        int more = R + 1;
        int cur = L;
        while (cur < more) {
            if (arr[cur] < pivot) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > pivot) {
                swap(arr, cur, --more);
            } else {
                cur++;
            }
        }
        return new int[] { less + 1, more - 1 };
    }
    
    public static void swap(int arr[], int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

}
