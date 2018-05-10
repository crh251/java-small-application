import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Scanner;

//客户端
public class Client {

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);

		System.out.print("input port:"); // 输入 端口号 文件名
		int server_port = input.nextInt();
		System.out.print("input file path:");
		String filepath = input.next();
		String md5 = getMD5(filepath); // 文件的MD5值

		String server_ip = "101.132.131.230"; // 服务器地址

		Socket client = new Socket(server_ip, server_port); // 创建与服务器的连接

		File file = new File(filepath); // 文件对象
		FileInputStream bin_input = new FileInputStream(file); // 获取文件二进制流

		OutputStream bin_output = client.getOutputStream(); // 向服务器写二进制流
		DataOutputStream str_output = new DataOutputStream(client.getOutputStream()); // 向服务器写字符流

		String filename = getFileName(filepath);
		System.out.println("发送的文件名为: " + filename);
		str_output.writeUTF(filename); // 输出文件名
		str_output.flush();

		str_output.writeUTF(Long.toString(file.length())); // 输出文件长度，字节为单位
		str_output.flush();
		System.out.println("发送的文件长度为:");
		System.out.println(Long.toString(file.length()));

		str_output.writeUTF(md5);
		str_output.flush();
		System.out.println("发送的文件的MD5值为:");
		System.out.println(md5);

		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = bin_input.read(buffer)) != -1) {
			bin_output.write(buffer, 0, len);
		}
		bin_output.flush();
		bin_input.close();
		bin_output.close();
		client.close();
		input.close();

		System.out.println("发送文件成功!");
	}

	private static String getFileName(String filepath) {
		if (filepath.contains("\\")) {
			for (int i = filepath.length() - 1; i >= 0; i--) {
				if (filepath.charAt(i) == '\\') {
					return filepath.substring(i + 1, filepath.length());
				}
			}
		}
		return "unknow";
	}

	private static String getMD5(String filepath) throws Exception {
		File file = new File(filepath);
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[8192];
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		int len = 0;
		while ((len = fis.read(buffer)) > 0) {
			md5.update(buffer, 0, len);
		}
		fis.close();
		return new BigInteger(1, md5.digest()).toString();
	}

}
