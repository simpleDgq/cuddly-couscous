package class06;

public class Code05_Nim {
    
    /*
     * 我们有3堆石子，有A和B两个人轮流从其中的一堆取石子。规定每个人每次最少取1颗，最多可以取完当前堆，
     * 无法继续拿取石子的人落败。请问如果你是先手，你有必胜策略吗？
     */
    
    /*
     * 结论: 如果异或和为0，后手赢，
     * 如果异或和不是0，先手赢。  ==> 记住完事。 笔试。
     * 
     * 先手每次拿的时候，都将所有数的异或和变成0。
     */

    // 保证arr是正数数组
    public static void printWinner(int[] arr) {
        int eor = 0;
        for (int num : arr) {
            eor ^= num;
        }
        if (eor == 0) {
            System.out.println("后手赢");
        } else {
            System.out.println("先手赢");
        }
    }

    

}
