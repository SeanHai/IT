package enterpriseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class ViewConsultDB {

	private String name;
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	
	public ViewConsultDB(String e_Name)
	{
		name=e_Name;
	}
	
	public JSONArray Select()
	{
		JSONArray Array=new JSONArray();
		conn=new ConnectDatabase().connect();
		String sql="select * from resume natural join jobbenefits where e_Name=?";
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, name);
			set=stmt.executeQuery();
			while(set.next())
			{
				JSONObject object=new JSONObject();
				PreparedStatement stmt1;
				ResultSet st;
				String answer="None";
				int resumeId=set.getInt(1);
				String sql1="select * from consult where resumeId=? and answer=?";
				stmt1=conn.prepareStatement(sql1);
				stmt1.setInt(1, resumeId);
				stmt1.setString(2, answer);
				st=stmt1.executeQuery();
				JSONArray consultArray=new JSONArray();
				int flag=1;
				while(st.next())
				{
					JSONObject unitConsult=new JSONObject();
					flag=0;
					try {
						unitConsult.put("consultId", st.getInt(1));
						unitConsult.put("username", st.getString(3));
						unitConsult.put("time", st.getDate(4));
						unitConsult.put("question", st.getString(5));
						consultArray.put(unitConsult);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				if(flag==1){}
				else
				{
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
						object.put("consult", consultArray);
						Array.put(object);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				st.close();
				stmt1.close();
			}
			set.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Array;
	}
}
