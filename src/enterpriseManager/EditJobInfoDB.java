package enterpriseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class EditJobInfoDB {

	private String type,jobName,property,scope,acaQualification,province,city,description;
	private int resumeId,salary,number;
	private int benefits[];
	private Connection conn;
	private PreparedStatement stmt;
	public EditJobInfoDB(JSONObject object)
	{
		try {
			type=object.getString("type");
			if(type.equals("delete"))
				resumeId=object.getInt("resumeId");
			else
			{
				resumeId=object.getInt("resumeId");
				jobName=object.getString("jobName");
				property=object.getString("property");
				scope=object.getString("scope");
				acaQualification=object.getString("acaQualification");
				province=object.getString("province");
				city=object.getString("city");
				description=object.getString("description");
				salary=object.getInt("salary");
				number=object.getInt("number");
				benefits=new int[6];
				int length=object.getJSONArray("benefits").length();
				for(int i=0;i<length;i++)
				{
					benefits[i]=object.getJSONArray("benefits").getInt(i);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int Edit()
	{
		String sql,sql1;
		if(type.equals("delete"))
			{
				sql="delete from resume where resumeId=?";
				sql1="delete from jobbenefits where resumeId=?";
			}
		else
		{
			sql="update resume set  jobName=?,salary=?,property=?,scope=?,jobNeedNumber=?"
					+ ",acaQualification=?,province=?,city=?,jobDescription=? where resumeId=?";
			sql1="update jobbenefits set benefit1=?,benefit2=?,benefit3=?,benefit4=?,benefit5=?"
					+ ",benefit6=? where resumeId=?";
		}
		conn=new ConnectDatabase().connect();
		if(type.equals("delete"))
		{
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setInt(1,resumeId );
				stmt.execute();
				stmt.close();
				stmt=conn.prepareStatement(sql1);
				stmt.setInt(1, resumeId);
				stmt.execute();
				stmt.close();
				conn.close();
				return 2;//É¾³ý³É¹¦
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 3;//É¾³ýÊ§°Ü
			}
			
		}
		else
		{
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, jobName);
				stmt.setInt(2, salary);
				stmt.setString(3, property);
				stmt.setString(4, scope);
				stmt.setInt(5, number);
				stmt.setString(6, acaQualification);
				stmt.setString(7, province);
				stmt.setString(8, city);
				stmt.setString(9, description);
				stmt.setInt(10, resumeId);
				stmt.executeUpdate();
				stmt.close();
				stmt=conn.prepareStatement(sql1);
				stmt.setInt(1, benefits[0]);
				stmt.setInt(2, benefits[1]);
				stmt.setInt(3, benefits[2]);
				stmt.setInt(4, benefits[3]);
				stmt.setInt(5, benefits[4]);
				stmt.setInt(6, benefits[5]);
				stmt.setInt(7, resumeId);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
				return 4;//ÐÞ¸Ä³É¹¦
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 5;//ÐÞ¸ÄÊ§°Ü
			}
		}
	}
	
}
