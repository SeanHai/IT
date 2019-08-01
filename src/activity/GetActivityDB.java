package activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class GetActivityDB {
	private String type;//操作类型
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set,set1;
	public GetActivityDB(JSONObject object)
	{
		try {
			type=object.getString("type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public JSONArray getInformation()
	{
		String sql="",sql1="";
		JSONArray array=new JSONArray();
		if(type.equals("share"))
			sql="select * from sharedinformation";
		else
			sql="select * from sharedquestions";
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(sql);
			set=stmt.executeQuery();
			while(set.next())
			{
				if(set.getString(6).equals("P"))//个人
					sql1="select headPicture from pregist where username=?";
				else sql1="select headpicture from eregist where Ename=?";
				stmt=conn.prepareStatement(sql1);
				stmt.setString(1, set.getString(2));
				set1=stmt.executeQuery();
				JSONObject object=new JSONObject();
				try {
					object.put("id", set.getInt(1));
					object.put("username", set.getString(2));
					object.put("content", set.getString(3));
					object.put("time", set.getString(4));
					object.put("prisedTimes", set.getInt(5));
					if(set1.next())
						object.put("headPicture", set1.getString(1));
					array.put(object);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				set1.close();
			}
			set.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return array;
	}
}
