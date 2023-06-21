package class28;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// 49. 字母异位词分组
public class Problem_0049_GroupAnagrams {
    /**
     * 给你一个字符串数组，请你将 字母异位词 组合在一起。可以按任意顺序返回结果列表。
     * 字母异位词 是由重新排列源单词的所有字母得到的一个新单词。
     * 
     * 输入: strs = ["eat", "tea", "tan", "ate", "nat", "bat"]
     * 输出: [["bat"],["nat","tan"],["ate","eat","tea"]]
     * 
     * 字母的种类相同，个数相同，就是异位词
     */
    
    /**
     * 思路:
     * 1. 每个字符串把所有字符排序之后放到hash表里面，最后看hash表就是答案
     * 
     * 另外一种解法是，如果字符串比较短的话，可以统计每个字符在字符串里面出现的次数，
     * 加下划线，拼接成字符串，作为key，放到hashmap里面去；每一个字符都搞一遍，如果
     * 字符出现的次数一样，那么根据次数生成的key也是一样的，就能够被放到同一个key下的
     * 集合里面去
     * 
     * 
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        ArrayList<List<String>> res = new ArrayList<List<String>>();
        if(strs == null || strs.length == 0) {
            return res;
        }
        // key: 排序之后的字符串
        // value: 排序之前的异构串
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        for(String str: strs) {
            char chars[] = str.toCharArray();
            Arrays.sort(chars);
            String key = String.valueOf(chars);
            // 如果排序之后的字符串，不在map中，创建新的key，并且将原始字符串加入
            if(!map.containsKey(key)) {
                map.put(key, new ArrayList<String>());
            }
            // 已经加入过或者新的刚刚上面加入，将对应的原始字符串放到List中
            map.get(key).add(str);
        }
        // 返回map里面的list
        for(List<String> list: map.values()) {
            res.add(list);
        }
        return res;
    }
    
    // 统计字符出现的次数，作为key的解法
    public static List<List<String>> groupAnagrams1(String[] strs) {
        HashMap<String, List<String>> map = new HashMap<String, List<String>>();
        for (String str : strs) {
            int[] record = new int[26];
            for (char cha : str.toCharArray()) {
                record[cha - 'a']++;
            }
            StringBuilder builder = new StringBuilder();
            for (int value : record) {
                builder.append(String.valueOf(value)).append("_");
            }
            String key = builder.toString();
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<String>());
            }
            map.get(key).add(str);
        }
        List<List<String>> res = new ArrayList<List<String>>();
        for (List<String> list : map.values()) {
            res.add(list);
        }
        return res;
    }
}
