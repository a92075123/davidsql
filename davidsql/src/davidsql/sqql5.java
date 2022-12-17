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

public class sqql5 {

	public static void main(String[] args) {
		// URL
		// HttpURLConnection
		// InputStrem => JSON String
		// parse JSON String
		// connction DB => eeit53 => food table
		// PreparedStatement
		
		try {
			String json = getJSONString();
		
			parseJSON(json);
			System.out.println("OK");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	static String getJSONString() throws Exception {
		URL url = new URL(
			"https://media.taiwan.net.tw/XMLReleaseALL_public/scenic_spot_C_f.json");
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.connect();
		
		StringBuffer sb = new StringBuffer();
		try(BufferedReader reader = new BufferedReader(
			new InputStreamReader(conn.getInputStream()))){
			String line; 
			while ( (line = reader.readLine()) != null) {
				sb.append(line);
			}
			sb.delete(0, 1);		
		};
		return sb.toString();
	}
	
	static void parseJSON(String json) throws Exception {
		Properties prop = new Properties();
		prop.put("user", "root");
		prop.put("password", "root");
		
		Connection conn = 
				DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/travel", prop);
				
		JSONObject j = new JSONObject(json);
		JSONObject jsonOb = j.getJSONObject("XML_Head");
		JSONObject infos = jsonOb.getJSONObject("Infos");
		JSONArray info=infos.getJSONArray("Info");
						
		Statement stmt = conn.createStatement();
		stmt.execute("DELETE FROM pic");
		stmt.execute("ALTER TABLE pic AUTO_INCREMENT = 1");
		
		String sql = "INSERT INTO pic (picture1,picture2,picture3)" + 
					" VALUES (?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
	
	
//		
		System.out.println("size: " + info.length());
		for (int i=0; i<info.length(); i++) {			
				JSONObject row = info.getJSONObject(i);
				try {
					pstmt.setString(1, row.getString("Picture1"));
				}catch (Exception e) {					
					pstmt.setString(1,"123A");
				}
				try {
					pstmt.setString(2, row.getString("Picture2"));
				}catch (Exception e) {					
					pstmt.setString(2,"BBB");
				}
				try {
					pstmt.setString(3, row.getString("Picture3"));
				}catch (Exception e) {					
					pstmt.setString(3,"CCC");
				}						
				pstmt.executeUpdate();
		}
		System.out.println("ok");
		pstmt.close();
		conn.close();
	}
}
	


	
