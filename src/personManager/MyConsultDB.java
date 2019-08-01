package personManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class MyConsultDB {

	private String username="";
	private String type="";
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	public MyConsultDB(JSONObject object)
	{
		try {
			username=object.getString("username");
			type=object.getString("type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JSONArray getConsult()//还得考虑该咨询针对的招聘信息的获取
	{
		String sql="";
		JSONArray array=new JSONArray();
		if(type.equals("unanswered"))
			sql="select * from consult inner join resume on consult.resumeId=resume.resumeId where username=? and answer=?";
		else sql="select * from consult inner join resume on consult.resumeId=resume.resumeId where username=? and answer <> ?";
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, "None");
			set=stmt.executeQuery();
			while(set.next())
			{
				JSONObject object=new JSONObject();
				try {
					object.put("consultTime", set.getDate(4));
					object.put("question", set.getString(5));
					object.put("answer", set.getString(6));
					object.put("resumeTime", set.getString(8));
					object.put("jobName", set.getString(9));
					object.put("salary", set.getInt(10));
					object.put("e_Name", set.getString(11));
					object.put("property", set.getString(12));
					object.put("scope", set.getInt(13));
					object.put("jobNeedNumber", set.getInt(14));
					object.put("acaQualification", set.getString(15));
					object.put("workAddress", set.getString(16));
					object.put("jobDescription", set.getString(17));
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
