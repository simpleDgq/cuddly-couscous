package class28;

// 13. 罗马数字转整数
public class Problem_0013_RomanToInteger {
    /**
    * 思路:
    * 
    * 罗马数字的表示，题目给出:
    * 
    *   字符          数值
    *   I             1
    *   V             5
    *   X             10
    *   L             50
    *   C             100
    *   D             500
    *   M             1000
    *   
    *   
    *  相加得出表示的整数，例如，12 写做 XII，就是X + I + I = 12
    *  如果左边比右边的小，则左边就是负数，例如IV， 就是 -1 + 5 = 4 表示的是4
    */
    public int romanToInt(String s) {
        if(s == null || s.length() == 0) {
            return Integer.MAX_VALUE;
        }
        int nums[] = new int[s.length()];
        for(int i = 0; i <= s.length() - 1; i++) {
            switch (s.charAt(i)) {
                case 'I':
                    nums[i] = 1;
                    break;
                case 'V':
                    nums[i] = 5;
                    break;
                case 'X':
                    nums[i] = 10;
                    break;
                case 'L':
                    nums[i] = 50;
                    break;
                case 'C':
                    nums[i] = 100;
                    break;
                case 'D':
                    nums[i] = 500;
                    break;
                case 'M':
                    nums[i] = 1000;
                    break;
                default:
                    break;
            }
        }
        // 遍历每一个数，如果比后面的小，则以负数的形式加入到最后的结果中
        int sum = 0;
        
        for(int i = 0; i <= nums.length - 2; i++) {
            if(nums[i] < nums[i + 1]) {
                sum += -nums[i];
            } else {
                sum += nums[i];
            }
        }
        sum += nums[nums.length - 1];
        return sum;
    }
}
