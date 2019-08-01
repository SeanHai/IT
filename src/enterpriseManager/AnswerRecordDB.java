package enterpriseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class AnswerRecordDB {

	private String username;
	private String content;
	private int resumeId;
	private Connection conn;
	private PreparedStatement stmt;
	
	public AnswerRecordDB(JSONObject object)
	{
		try {
			username=object.getString("username");
			content=object.getString("answer");
			resumeId=object.getInt("resumeId");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String Answer()
	{
		String str="fail";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=sdf.format(new Date());
		String sql="update resume_record set 回复=?,回复时间=? where 用户名=? and 职位ID=?";
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, content);
			stmt.setString(2, time);
			stmt.setString(3, username);
			stmt.setInt(4, resumeId);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
			str="success";
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}
