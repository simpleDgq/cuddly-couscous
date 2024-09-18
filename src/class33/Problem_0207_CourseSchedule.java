package class33;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;
import java.util.List;

// 207. 课程表
public class Problem_0207_CourseSchedule {
    /**
     * 你这个学期必须选修 numCourses 门课程，记为 0 到 numCourses - 1 。
     * 在选修某些课程之前需要一些先修课程。 先修课程按数组 prerequisites 给出，其中 prerequisites[i] = [ai, bi] 
     * ，表示如果要学习课程 ai 则 必须 先学习课程  bi 。
     * 
     * 例如，先修课程对 [0, 1] 表示：想要学习课程 0 ，你需要先完成课程 1 。
     * 请你判断是否可能完成所有课程的学习？如果可以，返回 true ；否则，返回 false 。
     * 
     * 示例 1：
     * 
     * 输入：numCourses = 2, prerequisites = [[1,0]] 
     * 输出：true
     * 解释：总共有 2 门课程。学习课程 1 之前，你需要完成课程 0 。这是可能的。
     * 
     * 示例 2：
     * 
     * 输入：numCourses = 2, prerequisites = [[1,0],[0,1]]
     * 输出：false
     * 解释：总共有 2 门课程。学习课程 1 之前，你需要先完成​课程 0 ；并且学习课程 0 之前，你还应先完成课程 1 。这是不可能的。
     */
    

    
    // 方法1: 不使用Course类   掌握
    /**
     * nexts 用ArrayList表示
     * in 也用数组，下标表示课程，值表示入度
     * 
     * 给定的prerequisites数组，相当于是一张图，问题相当于是能不能按照某个顺序把这个图遍历完
     * 
     * 其实就是拓扑排序。
     * 
     * 找入度为0的节点，如果能一直擦掉所有的节点，就能拓扑排序，否则就不能。
     * 步骤：
     * 1. 遍历数组，建立好每个节点的入度表以及邻居表
     * 2. 将入度为0的节点入队列
     * 3. 然后入度为0的节点出队列，出队列的同时统计出队列的节点的数量，
     * 以及入度为0的节点的邻居节点的入度都减1，如果过程中有新的入度为0的节点，加入队列
     * 4. 如果出队列的节点数正好是课程的数量。则说明能够遍历完，没有环
     */
    public boolean canFinish2(int numCourses, int[][] prerequisites) {
        if(numCourses <= 0) {
            return false;
        }
        if(prerequisites == null || prerequisites.length == 0) {
            return true;
        }
        // 初始化邻居表
        List<List<Integer>> nexts = new ArrayList<List<Integer>>();
        for(int i = 0; i <= numCourses - 1; i++) { // 都先初始化为空。因为有的节点可能没有邻居(下一层节点)
            nexts.add(new ArrayList<Integer>());
        }
        // 遍历整个数组，建立好每个节点的入度表以及邻居表
        int in[] = new int[numCourses];
        for(int [] pre : prerequisites) {
            // 图上的边是从from到to
            int from = pre[1];
            int to = pre[0];
            // to节点的入度+1
            in[to]++;
            // from节点的邻居表要加上to
            nexts.get(from).add(to);
        }
        // 入度为0的节点放入队列
        int queue[] = new int[numCourses]; // 用数组代替队列
        int l = 0;
        int r = 0;
        for(int i = 0; i <= numCourses - 1; i++) {
            // i这个节点入度为0
            if(in[i] == 0) {
                //i节点应该被放入队列
                queue[r++] = i;
            }
        }
        // 入度为0的节点出队列，记录出队列的节点个数。同时入度为0的节点的邻居节点的入度减1
        // 如果有入度为0的节点，加入队列
        int count = 0;
        while(l != r) {
            int zeroIn = queue[l++];
            count++;
            // 入度为0的节点的邻居节点入度减1
            for(int next : nexts.get(zeroIn)) {
                in[next]--;
                if(in[next] == 0) {
                    queue[r++] = next;
                }
            }
        }
        return count == numCourses;
    }
    
    
    /**
     * 思路:
     * 
     * 给定的prerequisites数组，相当于是一张图，问题相当于是能不能按照某个顺序把这个图遍历完
     * 
     * 其实就是拓扑排序。
     * 
     * 找入度为0的节点，如果能一直擦掉所有的节点，就能拓扑排序，否则就不能。
     * 
     * 1. 搞Node节点表示一个课程，name就是课程名，in就是这个课程的入度，nexts就是该节点的邻居节点，也就是完成它之后，接下来能够
     * 完成的其它课程
     */
    
    /*
     * 一个Course节点表示课程
     */
    public class Course {
        int name; // 课程名
        int in; // 入度
        ArrayList<Course> nexts; // 邻居课程
        
        public Course(int n) {
            name = n;
            in = 0;
            nexts = new ArrayList<Course>();
        }
    }
    // 方法1: 使用Course类
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        if(prerequisites == null || prerequisites.length == 0) {
          return true; // 如果prerequisites为空，表示课程没有依赖，直接返回true
          /*
           * 例如: 
           * numCourses = 5
           * prerequisites = []
           * 5门课程不相互依赖，肯定能修完
           */
        }
        
        // 存放所有的课程信息，key是课程名，value对应的就是课程对象
        HashMap<Integer, Course> map = new HashMap<Integer, Course>();
        // 遍历每个元素，将每个课程的入度以及邻居课程列表建立好
        // [后做，先做]
        for(int[]prereq : prerequisites) {
            int from = prereq[1]; // 先做
            int to = prereq[0]; // 才能后做它
            // 如果是新的课程加入到map中
            if(!map.containsKey(from)) {
                map.put(from, new Course(from));
            }
            if(!map.containsKey(to)) {
                map.put(to, new Course(to));
            }
            Course t = map.get(to);
            Course f = map.get(from);
            // 从map中取出from，设置好它的邻居to
            f.nexts.add(t);
            // 从map中取出to，设置好它的入度
            t.in++;
        }
        // map中所有节点数
        int allNodes = map.size();
        
        // 现在有了每个课程的入度以及邻居，可以判断是否能够进行拓扑排序了
        // map中所有的课程，入度为0的，都放入到队列中
        Queue<Course> queue = new LinkedList<Course>();
        for(Course course: map.values()) {
            if(course.in == 0) {
                queue.add(course);
            }
        }
        
        // 遍历所有入度为0的节点，出队列，同时下一级节点的入度减减，如果有新的入度为0的节点，
        // 加入队列，记录出队列的元素个数，如果出队列的元素个数和map中的节点个数相同，表示
        // 所有的节点都能遍历完，否则表示遍历不完
        int count = 0;
        while(!queue.isEmpty()) {
            Course course = queue.poll();
            count++;
            // 所有邻居的入度减1
            for(Course next: course.nexts) {
                next.in--;
                if(next.in == 0) {
                    queue.add(next);
                }
            }
        }
        // 这里不能是numCourses，因为prerequisites中不一定保护所有的numCourses
        /*
         * 例如:
         * numCourses = 5
         * prerequisites = [[1,4],[2,4],[3,1],[3,2]]
         */
        return count == allNodes; 
    }
}
