package class33;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;
import java.util.LinkedList;

// 269 火星词典 
public class Problem_0269_AlienDictionary {
    /**
     * 现有一种使用英语字母的火星语言，这门语言的字母顺序与英语顺序不同。
     * 给你一个字符串列表 words ，作为这门语言的词典，words 中的字符串已经 按这门新语言的字母顺序进行了排序 。
     * 
     * 请你根据该词典还原出此语言中已知的字母顺序，并 按字母递增顺序 排列。若不存在合法字母顺序，返回 "" 。
     * 若存在多种可能的合法字母顺序，返回其中 任意一种 顺序即可。
     * 
     * 字符串 s 字典顺序小于 字符串 t 有两种情况：
     * 在第一个不同字母处，如果 s 中的字母在这门外星语言的字母顺序中位于 t 中字母之前，那么 s 的字典顺序小于 t 。
     * 如果前面 min(s.length, t.length) 字母都相同，那么 s.length < t.length 时，s 的字典顺序也小于 t 。
     * 
     * 示例 1：
     * 输入：words = ["wrt","wrf","er","ett","rftt"]
     * 输出："wertf"
     * 
     * 示例 2：
     * 输入：words = ["z","x"]
     * 输出："zx"
     * 
     * 示例 3：
     * 输入：words = ["z","x","z"]
     * 输出：""
     * 解释：不存在合法字母顺序，因此返回 "" 。
     * 
     * 提示：
     * 
     * 1 <= words.length <= 100
     * 1 <= words[i].length <= 100
     * words[i] 仅由小写英文字母组成
     */
    
    /**
     * 思路: 拓扑排序
     * 
     * 两个word，可以确定一对字母的顺序，例如[xyct, xybe] --> 可以确定c 在 b之前 --> c --> b
     * 
     * ==============
     * 两两word，确定一对字母的顺序，遍历所有的word，建立好字母顺序图
     * 
     * 然后所有入度为0的点进入队列，
     * 
     * 出队列，看图是否能够遍历完，遍历一个放入一个到结果中，返回。
     * 
     */
    public String alienOrder(String[] words) {
        if(words == null || words.length == 0) {
            return "";
        }
        // 入度表，key: 字母 value: 入度是多少
        HashMap<Character, Integer> indegree = new HashMap<Character, Integer>();
        for(String word : words) {
            for(char c : word.toCharArray()) {
                indegree.put(c, 0);
            }
        }
        // 每个字母的邻居表 key: 字母 value: 邻居，hashSet，无重复值
        HashMap<Character, HashSet<Character>> nexts = new  HashMap<Character, HashSet<Character>>();
        int N = words.length;
        for(int i = 0; i <= N - 2; i++) {
            char cur[] = words[i].toCharArray();
            char next[] = words[i + 1].toCharArray();
            // 相邻的两个字符串进行比较，找到一个顺序就加入到邻居表中
            int len = Math.min(cur.length, next.length); // 只需要比较到最短的位置就可以停止了 例如xyc 和xyzt , 比较到c，后面的就不用比较了
            int j = 0;
            for(; j <= len - 1; j++) {
                if(cur[j] != next[j]) {// 如果不相等，找到一对，from在cur中，to在next中
                    // 加入邻居表
                    if(!nexts.containsKey(cur[j])) { // 没有就新建一个，后面再加
                        nexts.put(cur[j], new HashSet<Character>());
                    }
                    nexts.get(cur[j]).add(next[j]);
                    // 更新入度
                    indegree.put(next[j], indegree.get(next[j]) + 1);
                    // 找到一个，这对单词就搞完了，就可以去搞下一对相邻的单词了
                    break;
                }
            }
            // 如果j一直++，超过了最短的长度，还没有找到相等的，
            // 例如xyz xyzt这种case，这种case表示就没有个顺序，直接返回空
            // 还有例如xyzt xyz这种case
            if(j == len) {
                return "";
            }
        }
        
        // 找入度为0的字母，加入队列
        Queue<Character> queue = new LinkedList<Character>();
        for(char ch : indegree.keySet()) {
            if(indegree.get(ch) == 0) {
                queue.add(ch);
            }
        }
        
        // 入度为零的点出队列，
        StringBuilder ans = new StringBuilder();
        int count = 0;
        while(!queue.isEmpty()) {
            char ch = queue.poll(); // 出队列
            ans.append(ch);// 记录答案
            count++; // 统计从入队为0的队列出去的字符数
            // 所有的邻居节点的入度减减
            for(char c : nexts.get(ch)) {
                int in = indegree.get(c) - 1;
                indegree.put(c, in);
                if(in == 0) { // 新的入度为0的点，加入队列
                    queue.offer(c);
                }
            }
        }
        // 出队列的数量等于总共的字符种类，说明有答案，返回
        return count == indegree.size() ? ans.toString() : ""; 
    }
}
