package personManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class personCollectionDB {

	private String type="";
	private String name="";
	private Connection conn;
	private PreparedStatement stmt,stmt1;
	private ResultSet set,set1;
	
	public personCollectionDB(JSONObject object)
	{
		try {
			type=object.getString("type");
			name=object.getString("name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public JSONArray SelectCollection()
	{
		String sql="";
		String sql1="";
		JSONArray array=new JSONArray();
		if(type.equals("job"))
			sql="select * from collectjob natural join resume natural join jobbenefits where username=?";
		else if(type.equals("company"))
			sql="select e_Name,headpicture from collectcompany innner join eregist on e_Name=Ename  where username=?";
		else if(type.equals("shared"))
		{
		sql="select * from collectsharedinformation inner join sharedinformation on collectsharedinformation.sharedid="
					+ "sharedinformation.id where collectsharedinformation.username=?";
		sql1="select * from collectsharedques inner join sharedquestions on collectsharedques.id=sharedquestions.id where collectsharedques.username=?";
		}
		conn=new ConnectDatabase().connect();
		try {
			if(type.equals("shared"))
				{
					stmt1=conn.prepareStatement(sql1);
					stmt1.setString(1, name);
					set1=stmt1.executeQuery();
				}
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, name);
			set=stmt.executeQuery();
			while(set.next())
			{
				JSONObject object=new JSONObject();
				if(type.equals("job"))
				{
					try {
						object.put("resumeId", set.getInt(1));
						object.put("sendTime", set.getDate(3));
						object.put("jobName", set.getString(4));
						object.put("salary", set.getInt(5));
						object.put("e_Name", set.getString(6));
						object.put("property", set.getString(7));
						object.put("scope", set.getInt(8));
						object.put("jobNeedNumber", set.getInt(9));
						object.put("acaQualification", set.getString(10));
						object.put("province", set.getString(11));
						object.put("city", set.getString(12));
						object.put("jobDescription", set.getString(13));
						object.put("benefit1", set.getInt(14));
						object.put("benefit2", set.getInt(15));
						object.put("benefit3", set.getInt(16));
						object.put("benefit4", set.getInt(17));
						object.put("benefit5", set.getInt(18));
						object.put("benefit6", set.getInt(19));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else if(type.equals("company"))
				{
					try {
						object.put("e_Name", set.getString(1));
						object.put("headPicture", set.getString(2)==null?"":set.getString(2));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else
				{
					try {
						object.put("username", set.getString(4));
						object.put("content", set.getString(5));
						object.put("time", set.getString(6));
						object.put("prisedTimes", set.getInt(7));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				array.put(object);
			}
			set.close();
			stmt.close();
			if(type.equals("shared"))
			{
				while(set1.next())
				{
					JSONObject object=new JSONObject();
					try {
						object.put("username", set1.getString(4));
						object.put("content", set1.getString(5));
						object.put("time", set1.getString(6));
						object.put("prisedTimes", set1.getInt(7));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					array.put(object);
				}
				set1.close();
				stmt1.close();
			}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
}
