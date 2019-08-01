package schoolAndEnterprise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class ShowSchoolEmployInfoDB {

	private String type;//用户类型
	private String name;//用户标识
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	public ShowSchoolEmployInfoDB(JSONObject object)
	{
		try {
			type=object.getString("type");
			name=object.getString("name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public JSONArray getInformation()
	{
		JSONArray array=new JSONArray();
		String sql="";
		if(type.equals("enterprise"))
			sql="select School,content,time from schoolemploy where Ename=?";
		else sql="select * from schoolemploy";
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(sql);
			if(type.equals("enterprise"))
				stmt.setString(1, name);
			set=stmt.executeQuery();
			while(set.next())
			{
				JSONObject object=new JSONObject();
				if(type.equals("enterprise"))
				{
					try {
						object.put("school", set.getString(1));
						object.put("content", set.getString(2));
						object.put("time", set.getString(3));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					try {
						object.put("EName", set.getString(2));
						object.put("school", set.getString(3));
						object.put("content", set.getString(4));
						object.put("time", set.getString(5));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				array.put(object);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
}
