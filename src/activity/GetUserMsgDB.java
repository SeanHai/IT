package activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class GetUserMsgDB {

	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	private String username;
	public GetUserMsgDB(JSONObject object)
	{
		try {
			username=object.getString("username");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public JSONObject getInformation()
	{
		JSONObject object=new JSONObject();
		String sql="select * from pregist where username=?";
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, username);
			set=stmt.executeQuery();
			if(set.next())
			{
				try {
					object.put("job", set.getString(5));
					object.put("university", set.getString(6));
					object.put("major", set.getString(7));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
}
