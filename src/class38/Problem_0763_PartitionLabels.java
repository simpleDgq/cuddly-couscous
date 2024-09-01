package class38;

import java.util.ArrayList;
import java.util.List;

// 763. 划分字母区间
// https://leetcode.cn/problems/partition-labels/description/
public class Problem_0763_PartitionLabels {
    /**
     * 给你一个字符串 s 。我们要把这个字符串划分为尽可能多的片段，同一字母最多出现在一个片段中。
     * 注意，划分结果需要满足：将所有划分结果按顺序连接，得到的字符串仍然是 s 。
     * 返回一个表示每个字符串片段的长度的列表。
     */
    /**
     * 思路:
     * 如果第一个字符是a，应该被划分到什么范围？ --> 应该看a最右出现的位置是在哪，那么切的位置可能是a出现最右的位置，也有可能更加往后
     * 
     * 例子: abcdafckuki
     *012345678910
     * 第一个a，应该怎么划分？a最右出现的位置是4，所以第一块至少到4这，搞一个R记录这个位置，R不回退，只会一直被推高
     * 然后看b，b最右出现的位置1，第一块位置能满足b只出现在同一个片段中，所以不能推高R
     * 看c，c最右的位置是6，要想将c包括在同一个片段中，必须在6或者之后切，6比当前的R大，推高R，R来的6位置
     * d不能推高，a不能推高，f不能推高，c不能推高
     * 再选继续往下的话，位置已经超过R了，所以直接在R位置切，形成第一块， 同时计算0到R的长度
     * 然后到了k，k最右的位置是9，R来到9，需要一个变量记录每块的起始位置，便于计算整个区间的长度
     * u不能推高R，k不能推高R，再继续，指针超过R了，所以在R位置再切一次，形成第二块
     * 继续来到i，i没有最右位置了，来到了字符串结尾，直接形成最后一块。
     * 
     * =====
     * i一直往后，如果i超过R了，那么left到R就是一个区间，记录答案
     * i没有超过R，看i位置的字符， 能不能推高R，
     */
    public List<Integer> partitionLabels(String s) {
        List<Integer> result = new ArrayList<Integer>();
        if (s == null || s.length() == 0) {
            return result;
        }
        // 算好每个字符出现的最右位置
        // str[i]如果是a，减去a之后就是0，表示的是far数组中0位置记录的是a最右的位置
        // str[i]如果是b，减去a之后就是1，表示的是far数组中1位置记录的是b最右的位置
        // ....
        int far[] = new int[26];
        int N = s.length();
        char str[] = s.toCharArray();
        for (int i = 0; i <= N - 1; i++) {
            // str[i]如果是a，减去a之后就是0，表示的是far数组中0位置记录的是a最右的位置
            far[str[i] - 'a'] = i;
        }
        // 从0位置开始搞
//        int right = 0;
//        for (int i = 0; i <= N - 1; i++) {
//            // i位置的字符出现的最右位置
//            right = far[str[i] - 'a'];
//            if(i != 0) {
//                
//            }
//        }
        // 上面发现从0位置开始搞的话，代码不好写，可以先再外面形成0位置的R，再从1开始看能不能推高
        int right = far[str[0] - 'a']; // 0 位置的字符出现的最右位置
        int left = 0; // 记录每个区间的起始位置，便于计算整个区间的长度
        for (int i = 1; i <= N - 1; i++) {
            // 如果i位置超出right了，left到right的区间就是一块，搜集答案
            // abcsbsdf 这种case，a独立成一块，需要将这个if放前面
            if(i > right) {
                result.add(right - left + 1);
                // 更新下一个区间的left和right
                left = i;
                right = far[str[i] - 'a'];
                continue;
            }
            //i 位置的字符能推高right吗？
            // 如果能，更新right
            if(far[str[i] - 'a'] > right) {
                // 更新right
                right = far[str[i] - 'a'];
            }
        }
        // 加入最后一块.
        // 例如abcabcf
        result.add(right - left + 1);
        return result;
    }
}
