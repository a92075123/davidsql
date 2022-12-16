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

import org.json.JSONArray;
import org.json.JSONObject;

public class sqql {

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
		stmt.execute("DELETE FROM user");
		stmt.execute("ALTER TABLE user AUTO_INCREMENT = 1");
		
		String sql = "INSERT INTO user (name,tel,addr,region,town)" + 
					" VALUES (?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
	
	
//		
		System.out.println("size: " + info.length());
		for (int i=0; i<info.length(); i++) {			
				JSONObject row = info.getJSONObject(i);
				pstmt.setString(1, row.getString("Name"));
				pstmt.setString(2, row.getString("Tel"));
				try {
					pstmt.setString(3, row.getString("Add"));
				}catch (Exception e) {
					
					pstmt.setString(3, " ");
				}
			
				try {
					pstmt.setString(4, row.getString("Region"));
				}catch (Exception e) {
					
					pstmt.setString(4, " ");
				}
				
				try {
					pstmt.setString(5, row.getString("Town"));
				}catch (Exception e) {
					pstmt.setString(5, " ");
				}
////		
//	
//		
				pstmt.executeUpdate();
		}
		System.out.println("ok");
		pstmt.close();
		conn.close();
	}
}
	


	
