package activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class FocusDB {
	private String username,one,type;
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	public FocusDB(JSONObject object)
	{
		try {
			type=object.getString("type");
			username=object.getString("username");
			one=object.getString("one");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int setInformation()
	{
		String sql;
		conn=new ConnectDatabase().connect();
		if(type.equals("focus"))//关注
		{
			sql="select * from focus where username=? and one=?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, username);
				stmt.setString(2, one);
				set=stmt.executeQuery();
				set.last();
				if(set.getRow()!=0)
					{
					    set.close();
						return 2;
					}
				else
				{
					sql="insert into focus values (?,?)";
					stmt=conn.prepareStatement(sql);
					stmt.setString(1, username);
					stmt.setString(2, one);
					stmt.executeUpdate();
					set.close();
					stmt.close();
					conn.close();
					return 3;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 4;
			}
		}
		else//取消关注
		{
			sql="delete from focus where username=? and one=?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, username);
				stmt.setString(2, one);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
				return -2;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 4;
		}
		
	}
}
