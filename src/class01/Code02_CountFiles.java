package class01;

import java.io.File;
import java.util.Stack;

public class Code02_CountFiles {
	/**
	 * 统计目录下的文件数量
	 * 
	 * 图的遍历： 深度优先(栈)或者广度优先(队列)
	 */
	
	// 用栈做，根目录入栈，然后出栈，判断它下面的东西是文件还是文件夹，文件就计数，文件夹就入栈
	public static int getFilesNumber(String filePath) {
		File file = new File(filePath);
		if(!file.exists() || (!file.isDirectory() && !file.isFile())) { // 不存在或者既不是文件夹又不是文件
			return 0;
		}
		if(file.isFile()) {
			return 1;
		}
		
		Stack<File> stack = new Stack<File>();
		stack.push(file);
		int files = 0;
		while(!stack.isEmpty()) {
			File cur = stack.pop();
			for(File file1 : cur.listFiles()) { // 统计当前文件夹下面的每一个元素
				if(file1.isFile()) { // 是文件，计数
					files++;
				} else if(file1.isDirectory()) { // 文件夹，入栈
					stack.push(file1);
				} // 既不是文件也不是文件夹，不管
			}
		}
		return files;
	}
	
	public static void main(String[] args) {
		// 你可以自己更改目录
		String path = "/Users/gqdeng/Desktop/test";
		System.out.println(getFilesNumber(path));
	}
}
