package personManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;
import datebase.ConnectDatabase;

public class CollectionDB {

	private String type,e_Name,username;
	private int resumeId;
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	private String collect="";//collect表示收藏，Uncollect表示取消收藏
	public CollectionDB(JSONObject object)
	{
		try {
			type=object.getString("type");
			if(type.equals("enterprise"))
				{
					e_Name=object.getString("e_Name");
					collect=object.getString("collect");
				}
			else resumeId=object.getInt("resumeId");
			username=object.getString("username");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int Create()
	{
		String sql="";
		if(type.equals("enterprise"))
		{
			if(collect.equals("collect"))
				sql="select * from collectcompany where username=? and e_Name=?";
			else sql="delete from collectcompany where username=? and e_Name=?";
		}
		else sql="select * from collectjob where username=? and resumeId=?";
		conn=new ConnectDatabase().connect();
		if(type.equals("enterprise")&&collect.equals("collect"))
		{
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, username);
				stmt.setString(2, e_Name);
				set=stmt.executeQuery();
				set.last();
				if(set.getRow()==1)
					return 4;
				set.close();
				stmt.close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String sql1="insert into collectcompany values(?,?)";
			try {
				stmt=conn.prepareStatement(sql1);
				stmt.setString(1, username);
				stmt.setString(2, e_Name);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
				return 3;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else if(type.equals("enterprise")&&collect.equals("Uncollect"))
		{
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, username);
				stmt.setString(2, e_Name);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
				return 5;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;
			}
		}
		else
		{
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, username);
				stmt.setInt(2, resumeId);
				set=stmt.executeQuery();
				set.last();
				if(set.getRow()==1)
					return 4;
				set.close();
				stmt.close();	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 5;
			}
			String sql1="insert into collectjob values(?,?)";
			try {
				stmt=conn.prepareStatement(sql1);
				stmt.setString(1, username);
				stmt.setInt(2, resumeId);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
				return 3;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 5;
			}
		}
		return 5;
	}
}
