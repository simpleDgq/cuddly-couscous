package class28;

//12. 整数转罗马数字
public class Problem_0012_IntegerToRoman {
    /**
     * 给你一个整数，将其转为罗马数字。
     * 
     * 
     * 思路: 罗马数字没有0，题目说数字不会超过3999
     * 搞一张表，表示到3000
     * 
     * String[][] c = { 
     *           { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" },
     *           { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" },
     *           { "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" },
     *           { "", "M", "MM", "MMM" } };
     * 
     * 然后看数字的每一位，直接从表里取出来
     */
    public String intToRoman(int num) {
        // 罗马数字没有0, 所以0位置都用空字符串表示
        String[][] c = { 
                { "", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX" }, // 1 2 3 4 5 ... 9
                { "", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC" }, // 10 20 30 40 50 60 ...  90
                { "", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM" }, // 100 200 300 400 500 600 .... 900
                { "", "M", "MM", "MMM" } }; // 1000 2000 3000 
        // 为什么上面的表没有类似，13， 133 这种中间的数的表示？因为转换成罗马数字，罗马数字是前面的数加后面的数，得到结果，
        // 同理这些中间的数，也就可以通过相加的形式表示出来，表里面只需要这些基础的表示形式
        StringBuilder roman = new StringBuilder();
        roman.append(c[3][num / 1000 % 10])
        .append(c[2][num / 100 % 10])
        .append(c[1][num / 10 % 10])
        .append(c[0][num % 10]);
        return roman.toString();
    }

}
