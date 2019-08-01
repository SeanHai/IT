package schoolAndEnterprise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class PublishRequireInfoDB {

	private Connection conn;
	private PreparedStatement stmt;
	private String EName;
	private String position;
	private String school;
	private String type;//区分发布校招还是职位要求
	private String content;
	public PublishRequireInfoDB(JSONObject object)
	{
		try {
			type=object.getString("type");
			EName=object.getString("EName");
			content=object.getString("content");
			if(type.equals("requireInfo"))
				position=object.getString("position");
			else school=object.getString("school");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	public String Publish()
	{
		String sql;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time=sdf.format(new Date());
		conn=new ConnectDatabase().connect();
		if(type.equals("requireInfo"))
			{
				sql="insert into requireinfo values(?,?,?,?)";
				try {
					stmt=conn.prepareStatement(sql);
					stmt.setString(1, EName);
					stmt.setString(2, position);
					stmt.setString(3, content);
					stmt.setString(4, time);
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
		else
		{
			sql="insert into schoolemploy (Ename,School,content,time) values(?,?,?,?)";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, EName);
				stmt.setString(2, school);
				stmt.setString(3, content);
				stmt.setString(4, time);
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
}
