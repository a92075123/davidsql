package davidsql;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import api.BCrypt;

public class sqql3 {

	public static void main(String[] args) {
Scanner scanner = new Scanner(System.in);
		
		System.out.print("Account: ");
		String account = scanner.next();
		
		System.out.print("Password: ");
		String passwd = scanner.next();
		
		System.out.print("Realname: ");
		String realname = scanner.next();
		
		System.out.print("Email: ");
		String email = scanner.next();
		
		System.out.print("TWID: ");
		String TWID = scanner.next();
		
		Properties prop = new Properties();
		prop.put("user", "root");
		prop.put("password", "root");
		
		String url = "jdbc:mysql://localhost:3306/travel";
		String sql = "INSERT INTO member (account,passwd,realname,email,twid) VALUES (?,?,?,?,?)";
		try(Connection conn = DriverManager.getConnection(url, prop);
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, account);
			pstmt.setString(2, BCrypt.hashpw(passwd, BCrypt.gensalt()));
			pstmt.setString(3, realname);
			pstmt.setString(4, email);
			pstmt.setString(5, TWID);
			int n = pstmt.executeUpdate();
			
			if (n > 0) {
				System.out.println("Register Success!");
			}else {
				System.out.println("Register Failure!");
			}
			
		}catch(Exception e) {
			
		}
		

	}
}