package jobInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class SelectJobInfoDB {
	private String province="";
	private String city="";
	private String salary="";
	private String property="";
	private String scope="";
	private String acaQualification="";
	private String sendTime="";
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	
	public SelectJobInfoDB(JSONObject object)
	{
		try {
			province=object.getString("province");
			city=object.getString("city");
			property=object.getString("property");
			scope=object.getString("scope");
			acaQualification=object.getString("acaQualification");
			sendTime=object.getString("sendTime");
			salary=object.getString("salary");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public JSONArray SelectJobInfo()
	{
		
		conn=new ConnectDatabase().connect();
		if(province.equals("不限"))
		{
			String sql="create view N_WorkAddress as select * from resume natural join jobbenefits";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			String sql="";
			try {
					if(city.equals("不限"))
					{
						sql="create view N_WorkAddress as select * from resume natural join jobbenefits where province=?";
						stmt=conn.prepareStatement(sql);
						stmt.setString(1, province);
					} 
					else
					{
						sql="create view N_WorkAddress as select * from resume natural join jobbenefits where province=? and city=?";
						stmt=conn.prepareStatement(sql);
						stmt.setString(1, province);
						stmt.setString(2, city);
					}
					stmt.execute();
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
		}
		
		if(salary.equals("不限"))
		{			
			String sql="create view N_Salary as select * from N_WorkAddress";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(salary.equals("2000以下"))
		{
			String sql="create view N_Salary as select * from N_WorkAddress where salary<2000";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(salary.equals("30000以上"))
		{
			String sql="create view N_Salary as select * from N_WorkAddress where salary>30000";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			String str[]=salary.split("~");
			int LowSalary=Integer.parseInt(str[0]);
			int UpSalary=Integer.parseInt(str[1]);
			String sql="create view N_Salary as select * from N_WorkAddress where salary>=? and salary<=?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setInt(1, LowSalary);
				stmt.setInt(2, UpSalary);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(property.equals("不限"))
		{
			String sql="create view N_Property as select * from N_Salary";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			String sql="create view N_Property as select * from N_Salary where property=?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, property);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(scope.equals("不限"))
		{
			String sql="create view N_Scope as select * from N_Property";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(scope.equals("1000以下"))
		{
			String sql="create view N_Scope as select * from N_Property where scope<1000";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if(scope.equals("10000以上"))
		{
			String sql="create view N_Scope as select * from N_Property where scope>10000";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			String str[]=scope.split("~");
			int lowScope=Integer.parseInt(str[0]);
			int upScope=Integer.parseInt(str[1]);
			String sql="create view N_Scope as select * from N_Property where scope>=? and scope<=?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setInt(1, lowScope);
				stmt.setInt(2, upScope);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(acaQualification.equals("不限"))
		{
			String sql="create view N_AcaQualification as select * from N_Scope";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			String sql="create view N_AcaQualification as select * from N_Scope where acaQualification=?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, acaQualification);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(sendTime.equals("不限"))
		{
			String sql="create view N_SendTime as select * from N_AcaQualification";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.execute();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			if(sendTime.equals("当日"))
				sendTime="0";
			else if(sendTime.equals("近三天"))
				sendTime="3";
			else if(sendTime.equals("近一周"))
				sendTime="7";
			else if(sendTime.equals("近一月"))
				sendTime="30";
			int time=Integer.parseInt(sendTime);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String t=sdf.format(new Date());
			java.sql.Date date=java.sql.Date.valueOf(t);
			String sql="create view N_SendTime as select * from N_AcaQualification where datediff(?,time)<?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setDate(1, date);
				stmt.setInt(2, time);
				stmt.execute(); 
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		String sql="select * from N_SendTime order by time desc";
		JSONArray array=new JSONArray();
		try {
			stmt=conn.prepareStatement(sql);
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
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String delete="drop view N_WorkAddress,N_Salary,N_Property,N_Scope,N_AcaQualification,N_SendTime";
		try {
			stmt=conn.prepareStatement(delete);
			stmt.execute();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
}
