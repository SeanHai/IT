package personManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class ReadInfoManagerDB {

	private String username;
	private String type;
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set,set1;
	
	public ReadInfoManagerDB(String name,String t)
	{
		username=name;
		type=t;
	}
	public JSONObject Select()
	{
		conn=new ConnectDatabase().connect();
		String sql="";
		if(type.equals("p"))
			sql="select phoneNumber,email,job,university,major,headPicture from pregist where username=?";
		else sql="select phoneNumber,email,headpicture from eregist where Ename=?";
		JSONObject object=new JSONObject();
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, username);
			set=stmt.executeQuery();
			while(set.next())
			{
				if(type.equals("p"))
				{
					try {
						object.put("phoneNumber", set.getString(1));
						object.put("email", set.getString(2));
						object.put("job", set.getString(3));
						object.put("university", set.getString(4));
						object.put("major", set.getString(5));
						object.put("headPicture", set.getString(6)==null?"":set.getString(6));
//						object.put("headPicture", set.getString(6));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					try {
						object.put("phoneNumber", set.getString(1));
						object.put("email", set.getString(2));
						object.put("headPicture", set.getString(3)==null?"":set.getString(3));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			set.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}
}
