package jobInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class ApplyJobDB {

	private String username,e_Name;
	private int resumeId;
	private String type;
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	
	public ApplyJobDB(JSONObject object)
	{
		try {
			type=object.getString("type");
			username=object.getString("username");
			if(type.equals("write"))
				{
					resumeId=object.getInt("resumeId");
					e_Name=object.getString("e_Name");
				}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int Check()
	{
		String sql="";
		if(type.equals("read"))
			sql="select * from record where 用户名=?";
		else sql="insert into resume_record (用户名,职位ID,公司,申请时间) values (?,?,?,?)";
		int result;
		conn=new ConnectDatabase().connect();
		if(type.equals("read"))
		{
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, username);
				set=stmt.executeQuery();
				set.last();
				result=set.getRow();
				set.close();
				stmt.close();
				conn.close();
				return result+7;//handler的case值已经到6了，result=0,表示没有简历，result=1，表示有简历。
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;
			}
		}
		else
		{
			String sql1="select * from resume_record where 用户名=? and 职位ID=?";
			try {
				stmt=conn.prepareStatement(sql1);
				stmt.setString(1, username);
				stmt.setInt(2, resumeId);
				set=stmt.executeQuery();
				set.last();
				if(set.getRow()==1)
					return 10;//不能重复申请
				set.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;
			}
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time=sdf.format(new Date());
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, username);
				stmt.setInt(2, resumeId);
				stmt.setString(3,e_Name);
				stmt.setString(4, time);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
				return 9;//申请成功
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;//申请失败
			}
			
		}
	}
}
