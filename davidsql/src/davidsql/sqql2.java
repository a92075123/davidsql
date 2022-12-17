package davidsql;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
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

public class sqql2 {

	public static void main(String[] args) {		
		
		Scanner scanner = new Scanner(System.in);
		System.out.print("Keyword =  ");
		String key = scanner.next();
		
		Properties prop = new Properties();
		prop.put("user", "root");
		prop.put("password", "root");
		
		try {
			Connection conn = 
					DriverManager.getConnection(
							"jdbc:mysql://localhost:3306/travel", prop);
			String sql = "SELECT * FROM user WHERE name LIKE ? OR addr LIKE ?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + key + "%");
			pstmt.setString(2, "%" + key + "%");
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				String name = rs.getString("name");
				String addr = rs.getString("addr");
				System.out.printf("%s : %s\n", name, addr);
			}

			rs.close();
			
			conn.close();
		} catch (Exception e) {
			
		}

	}

}
