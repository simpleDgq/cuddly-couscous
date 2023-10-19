package clss33;

import java.util.ArrayList;

//210. 课程表II
public class Problem_0210_CourseScheduleII {
    /**
     * 207的升级版本，如果真的能够拓扑排序的话，输出拓扑排序
     * 
     * 现在你总共有 numCourses 门课需要选，记为 0 到 numCourses - 1。给你一个数组 prerequisites ，
     * 其中 prerequisites[i] = [ai, bi] ，表示在选修课程 ai 前 必须 先选修 bi 。
     * 
     * 例如，想要学习课程 0 ，你需要先完成课程 1 ，我们用一个匹配来表示：[0,1] 。
     * 返回你为了学完所有课程所安排的学习顺序。可能会有多个正确的顺序，你只要返回 任意一种 就可以了。
     * 如果不可能完成所有课程，返回 一个空数组 。
     * 
        示例 1：
        
        输入：numCourses = 2, prerequisites = [[1,0]]
        输出：[0,1]
        解释：总共有 2 门课程。要学习课程 1，你需要先完成课程 0。因此，正确的课程顺序为 [0,1] 。
        
        示例 2：
        
        输入：numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
        输出：[0,2,1,3]
        解释：总共有 4 门课程。要学习课程 3，你应该先完成课程 1 和课程 2。并且课程 1 和课程 2 都应该排在课程 0 之后。
        因此，一个正确的课程顺序是 [0,1,2,3] 。另一个正确的排序是 [0,2,1,3] 。
        
        示例 3：
        
        输入：numCourses = 1, prerequisites = []
        输出：[0]
     */
    
    /**
     * 思路:
     * 从入度为0的队列里面出来的时候，出来一个搜集一个，搜集好的结果就是拓扑排序。
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        
        // nexts数组
        ArrayList<ArrayList<Integer>> nexts = new ArrayList<ArrayList<Integer>>();
        for(int i = 0; i < numCourses; i++) {
            nexts.add(new ArrayList<Integer>());
        }
        
        // in数组
        int in[] = new int[numCourses];
        
        for(int preq[]: prerequisites) {
          int from = preq[1];
          int to = preq[0];
          // from的邻居中加入to
          nexts.get(from).add(to);
          // to的入度加1
          in[to]++;
        }
        
        // 队列
        int zeroInQueue[] = new int[numCourses];
        int l = 0;
        int r = 0;
        // 入度为0的课，进入队列
        for(int i = 0; i < numCourses; i++) {
            if(in[i] == 0) {
                zeroInQueue[r++] = i;
            }
        }
        
        // 入度为0的课出队列，而且记录在int数组中
        // 其实不用开辟新的数组，zeroInQueue这个队列的元素，实际上并没有真正的弹出，
        // 里面都是入度为0的元素，如果能遍历完，直接返回这个队列就行
        int count = 0;
        while(l != r) {
            int courseInZero = zeroInQueue[l++];
            count++;
            for(int next: nexts.get(courseInZero)) {
                in[next]--;
                if(in[next] == 0) {
                    zeroInQueue[r++] = next;
                }
            }
        }
             
        return count == nexts.size() ? zeroInQueue : new int[0];
    }
}
