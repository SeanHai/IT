package jobInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class ConsultDB {
	private String username;
	private int resumeId;
	private String question;
	private Connection conn;
	private PreparedStatement stmt;
	
	public ConsultDB(JSONObject object)
	{
		try {
			resumeId=object.getInt("resumeId");
			username=object.getString("username");
			question=object.getString("question");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean CreateConsult()
	{
		String answer="None";
		conn=new ConnectDatabase().connect();
		String sql="insert into consult (resumeId,username,time,question,answer) values (?,?,?,?,?)";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=sdf.format(new Date());
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setInt(1, resumeId);
			stmt.setString(2, username);
			stmt.setString(3, time);
			stmt.setString(4, question);
			stmt.setString(5, answer);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
}
