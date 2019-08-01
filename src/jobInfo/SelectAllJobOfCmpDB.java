package jobInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class SelectAllJobOfCmpDB {

	private String e_Name="";
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	
	public SelectAllJobOfCmpDB(JSONObject object)
	{
		try {
			e_Name=object.getString("e_Name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JSONArray SelectJobInfo()
	{
		JSONArray array=new JSONArray();
		conn=new ConnectDatabase().connect();
		String sql="select * from resume natural join jobbenefits where e_Name=?";
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, e_Name);
			set=stmt.executeQuery();
			while(set.next())
			{
				JSONObject object=new JSONObject();
				try {
					object.put("resumeId", set.getInt(1));
					object.put("sendTime", set.getDate(2));
					object.put("jobName", set.getString(3));
					object.put("salary", set.getInt(4));
					object.put("e_Name", set.getString(5));
					object.put("property", set.getString(6));
					object.put("scope", set.getInt(7));
					object.put("jobNeedNumber", set.getInt(8));
					object.put("acaQualification", set.getString(9));
					object.put("province", set.getString(10));
					object.put("city", set.getString(11));
					object.put("jobDescription", set.getString(12));
					object.put("benefit1", set.getInt(13));
					object.put("benefit2", set.getInt(14));
					object.put("benefit3", set.getInt(15));
					object.put("benefit4", set.getInt(16));
					object.put("benefit5", set.getInt(17));
					object.put("benefit6", set.getInt(18));
					array.put(object);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
