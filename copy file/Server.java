import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.Scanner;

public class Server {

	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);

		System.out.print("input port:");
		int server_port = input.nextInt();		//输入端口号
		
		ServerSocket server = new ServerSocket(server_port); // 创建服务套接字
		System.out.println("waiting for connect...");
		Socket client = server.accept(); // 等待连接

		DataInputStream str_input = new DataInputStream(client.getInputStream()); // 读取字符输入流
		InputStream bin_input = client.getInputStream(); // 获取二进制输入流

		String filename = str_input.readUTF(); // 获取文件名
		System.out.println("获取的文件名为:");
		System.out.println(filename);
		
		String fileLength = str_input.readUTF();	//获取文件长度
		System.out.println("获取的文件长度为:");
		System.out.println(fileLength);
		
		String md5 = str_input.readUTF();		//获取文件MD5
		System.out.println("获取的文件MD5值为:");
		System.out.println(md5);
		
		File file = new File(filename); // 创建文件对象
		FileOutputStream bin_out = new FileOutputStream(file); // 写入二进制流到文件

		byte[] buffer = new byte[8192];
		int len = 0;

		while ((len = bin_input.read(buffer)) != -1) {
			bin_out.write(buffer, 0, len);
		}
		
		bin_out.close();
		bin_input.close();
		server.close();
		input.close();
		
		System.out.println("保存文件成功!");
		
		System.out.println("保存文件的MD5值为:");
		System.out.println(getMD5(filename));
		
	}

	private static String getMD5(String filename) throws Exception {
		File file = new File(filename);
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[8192];
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		int len = 0;
		while ((len = fis.read(buffer)) > 0) {
			md5.update(buffer, 0, len);
		}
		fis.close();
		return new BigInteger(1,md5.digest()).toString();
	}

}
