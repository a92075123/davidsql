package davidsql;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import api.Member;

public class sqql4 {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Account: ");
		String account = scanner.next();
		
		System.out.print("Password: ");
		String passwd = scanner.next();
		
		Properties prop = new Properties();
		prop.put("user", "root");
		prop.put("password", "root");
		
		String url = "jdbc:mysql://localhost:3306/travelq";
		String sql = "SELECT * FROM member WHERE account = ? AND passwd = ?";
		try(Connection conn = DriverManager.getConnection(url, prop);
			PreparedStatement pstmt = conn.prepareStatement(sql)){
			
			pstmt.setString(1, account);
			pstmt.setString(2, passwd);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Member member = new Member(rs.getInt("id"), 
						rs.getString("account"), 
						rs.getString("passwd"),
						rs.getString("realname"));
				System.out.printf("Welcome, %s", member.getRealname());
			}else {
				System.out.println("get out here");
			}
			
			rs.close();
			
		}catch(Exception e) {
			
		}
		

	}
}
