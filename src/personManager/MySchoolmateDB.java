package personManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class MySchoolmateDB {

	private String username="",university="",type="";
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	
	public MySchoolmateDB(JSONObject object)
	{
		try {
			username=object.getString("username");
			type=object.getString("type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getFriends()
	{
		if(type.equals("focusFriends"))
			return getFocus();
		else return getSchoolmate();
	}
	public String getSchoolmate()
	{
		String sql="select university from pregist where username=?";
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, username);
			set=stmt.executeQuery();
			if(set.next())
			{
				if(set.getString(1).equals("暂无信息"))//表示信息不完善
					{
						set.close();
						stmt.close();
						conn.close();
						return "none";
					}
				else 
					{
						String sql1="select * from pregist where university=? and username<>?";
						university=set.getString(1);
						JSONArray array=new JSONArray();
						stmt=conn.prepareStatement(sql1);
						stmt.setString(1, university);
						stmt.setString(2, username);
						set=stmt.executeQuery();
						while(set.next())
						{
							JSONObject object=new JSONObject();
							try {
								object.put("username", set.getString(1));
								object.put("job", set.getString(5));
								object.put("university", set.getString(6));
								object.put("major", set.getString(7));
								object.put("headPicture", set.getString(8));
								array.put(object);
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						set.close();
						stmt.close();
						conn.close();
						return array.toString();
					}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public String getFocus()
	{
		String sql="select one from focus where username=?";
		JSONArray array=new JSONArray();
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, username);
			set=stmt.executeQuery();
			while(set.next())
			{
				JSONObject object=new JSONObject();
				ResultSet set1;
				sql="select job,university,major,headPicture from pregist where username=?";
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, set.getString(1));
				set1=stmt.executeQuery();
				if(set1.next())
				{
					try {
						object.put("username", set.getString(1));
						object.put("job", set1.getString(1));
						object.put("university", set1.getString(2));
						object.put("major", set1.getString(3));
						object.put("headPicture", set1.getString(4));
						array.put(object);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				set1.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array.toString();
	}
}
