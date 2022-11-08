// 本题测试链接 : https://leetcode.com/problems/jump-game-ii/
public class Code01_JumpGame {
    /*
     * 给定一个非负整数数组，你最初位于数组的第一个位置。

        数组中的每个元素代表你在该位置可以跳跃的最大长度。
        
        你的目标是使用最少的跳跃次数到达数组的最后一个位置。
        
        假设你总是可以到达数组的最后一个位置。
        
        示例 1:
        
        输入: [2,3,1,1,4]
        输出: 2
        解释: 跳到最后一个位置的最小跳跃数是 2。
             从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
        示例 2:
        
        输入: [2,3,0,1,4]
        输出: 2
        提示:
        
        1 <= nums.length <= 1000
        0 <= nums[i] <= 10^5
     */
    
    /**
     *  三个变量搞定:
     *  step: 来到当前位置跳了多少步
     *  cur: 如果不增加步数，step内，最远能跳到哪
     *  next: 如果允许你多跳1步，最远能跳到哪
     *  
     *  初始状态: 从0位置出发，一步也不用跳
     *  step = 0;
     *  cur = 0; // 0步内，最远只能跳到0
     *  next = 0 + arr[0]; 多跳一步的情况下，最远能够跳到arr[0]
     *  
     *  ---
     *  来到1位置: 必须跳一步了，
     *  step = 1;
     *  cur = next; // 1步内，最远只能跳到上一步的next
     *  next = Math.max(arr[i] + i, next); //step + 1步内: 最远能够跳到的位置
     * 
     */
    public int jump(int[] nums) {
        if(nums == null || nums.length == 0) {
            return 0;
        }
        int step = 0;
        int cur = 0;
        int next = 0;
        for(int i = 0; i <= nums.length - 1; i++) {
            if(i > cur) { // 如果i，超过了当前step内能跳到的最远位置，说明step要++了，同时cur更新成如果多跳一步能够跳到的最远位置。==> 每次都在准备如果多跳一步能够到达的最远位置next
               step++;
               cur = next;
            }
            next = Math.max(next, i + nums[i]); // 每次都在准备如果多跳一步能够到达的最远位置next
        }
        return step;
    }
}
