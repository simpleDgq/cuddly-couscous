package class04;

public class Code06_MakeNo {
    /*
     * 生成长度为size的达标数组
     * 
     * 生成长度为size的达标数组，什么叫达标？
        达标：对于任意的i< k < j,满足[i]+[j]=[k]*2
        给定一个正数size,返回长度为size的达标数组
     */
    
    /*
     * 这题有点数学的意思在里面:
     * [a,b,c] 如果满足a+c!=2b -> 那么变成[2a, 2b, 2c] 也一定满足这个条件
     * 再扩出来[2a + 1, 2b + 1, 2c + 1] 也一定满足
     * 
     * 最终合成[2a, 2b, 2c, 2a + 1, 2b + 1, 2c + 1] 也一定满足
     * 
     * 因为左边是个偶数域，右边是个奇数域, 一个偶数加一个奇数，不可能是某一个数的两倍
     */
    
    public static int[] makeNo(int size) {
        if(size <= 0) {
            return null;
        }
        
        // base case
        if(size == 1) {
            return new int[] {1}; // 1, 2或者其它的数都行
        }
        
        int halfSize = (size + 1) / 2; // 除2向上取整
        // 先搞定一半
        int base[] = makeNo(halfSize); 
        int ans[] = new int[size];
        int index = 0;
        
        // 根据这一半，base*2搞出左边
        for(; index <= halfSize - 1; index++) {
            ans[index] = base[index] * 2;
        }
        // 根据这一半，base*2搞出右边
        for(int i = 0; i <= halfSize - 1 && index <= size - 1; i++) {
            ans[index++] = base[i] * 2 + 1;
        }
        return ans;
    }
    
    // 检验函数
    public static boolean isValid(int[] arr) {
        int N = arr.length;
        for (int i = 0; i < N; i++) {
            for (int k = i + 1; k < N; k++) {
                for (int j = k + 1; j < N; j++) {
                    if (arr[i] + arr[j] == 2 * arr[k]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        for (int N = 1; N < 1000; N++) {
            int[] arr = makeNo(N);
            if (!isValid(arr)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
        System.out.println(isValid(makeNo(1042)));
        System.out.println(isValid(makeNo(2981)));
    }

}
