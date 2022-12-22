package class18;

public class Code01_HanoiProblem {
 /**
  * 给定数组是汉诺塔问题中的第几步
  * 
  * 给定一个数组arr，长度为N，arr中的值只有1，2，3三种
  *  arr[i] == 1，代表汉诺塔问题中，从上往下第i个圆盘目前在左
  *  arr[i] == 2，代表汉诺塔问题中，从上往下第i个圆盘目前在中
  *  arr[i] == 3，代表汉诺塔问题中，从上往下第i个圆盘目前在右
  *  那么arr整体就代表汉诺塔游戏过程中的一个状况
  *  如果这个状况不是汉诺塔最优解运动过程中的状况，返回-1
  *  如果这个状况是汉诺塔最优解运动过程中的状况，返回它是第几个状况
  */
    
   /**
    * 思路:
    * 定义一个函数: f(i, F, T, Other) 
    *   i: 1~i的圆盘需要移动
        F: 1~i的圆盘现在处在什么圆盘上, 可能是左, 中, 右
        t: 需要去的位置, 可能是左, 中, 右
        other: 除了from, to的另外一个位置
        
        base case:
        1. 如果没有圆盘需要移动了，index == -1, 说明是第0步，直接返回
        2. i层的圆盘没有任何道理是在other上。 
             如果0~i - 1都在other上，那么i层的圆盘直接移动到to。
             如果0~i - 1不在other上，说明还没有移动完，继续移动
             所以i层的圆盘不可能在other上，如果在other上，直接返回-1
             
             
        3. 整个移动的过程分为3大步
             1) 0~i-1 从from到other
             2) i 从from到to
             3) 0 ~ i - 1从other 到to
         1)i层的圆盘在From上，说明0~i-1层圆盘还没有移动完，继续从from到other移动
         2)i层的圆盘在To上，说明0~i-1层的圆盘移从from到other移动完了，i也从from到了to，接下来要继续0~i-1从other到to
         结论: N层汉诺塔问题，从from到to，需要移动的次数是2^N - 1
         
         时间复杂度分析: O(N)
         因为是单决策递归，每次都只走了一个分支。
    */
    public static int Kth(int arr[]) {
        if(arr == null || arr.length == 0) {
            return 0;
        }
        int N = arr.length;
        // N层汉罗塔问题 0 ~ N -1 从左到右
        return step(arr, N - 1, 1, 3, 2);
    }
    // 0...index这些圆盘，arr[0..index]   index+1层塔
    // 在哪？from 去哪？to 另一个是啥？other
    // arr[0..index]这些状态，是index+1层汉诺塔问题的，最优解第几步
    public static int step(int[] arr, int index, int from, int to, int other) {
       // 没有圆盘了
        if(index == -1) {
            return 0;
        }
        // index层圆盘在other上
        if(arr[index] == other) {
            return - 1;
        }
        // arr[index]  只可能在from或者to上
        // 整个数组的状态就是0~i-1从from到other中的一个状态
        if(arr[index] == from) { // 说明0~i-1层圆盘还没有移动完，继续从from到other移动.   
            return step(arr, index - 1, from, other, to);
        } else { 
            // 说明0~i-1层的圆盘移从from到other移动完了，i也从from到了to，接下来要继续0~i-1从other到to
            // 整个数组的状态就是0~i-1从other到to中的一个状态
            int p1 = (1 << index) - 1;
            int p2 = 1;
            int p3 = step(arr, index - 1, other, to, from);
            if(p3 == -1) {
                return -1;
            }
            return p1 + p2 + p3;
        }
    }
}
