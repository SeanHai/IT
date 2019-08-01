package enterpriseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class AnswerConsultDB {

	private int consultId;
	private String answer="";
	private Connection conn;
	private PreparedStatement stmt;
	
	public AnswerConsultDB(JSONObject object)
	{
		try {
			consultId=object.getInt("consultId");
			answer=object.getString("answer");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String Create()
	{
		conn=new ConnectDatabase().connect();
		String sql="update consult set answer=? where consultId=?";
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, answer);
			stmt.setInt(2, consultId);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
			return "success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
		
	}
}
