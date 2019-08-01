package enterpriseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.mysql.jdbc.Statement;

import datebase.ConnectDatabase;

public class Resume {

	private String jobName="";
	private int salary;
	private String e_Name;
	private String property;
	private String scope;
	private int jobNeedNum;
	private String acaQualification;
	private String province;
	private String city;
	private String jobDescription;
	private int benefits[];
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	
	public Resume(JSONObject resume) throws JSONException
	{
		jobName=resume.getString("JobName");
		salary=Integer.parseInt(resume.getString("Salary"));
		e_Name=resume.getString("E_Name");
		property=resume.getString("Property");
		scope=resume.getString("Scope");
		jobNeedNum=Integer.parseInt(resume.getString("JobNeedNum"));
		acaQualification=resume.getString("AcaQualification");
		province=resume.getString("province");
		city=resume.getString("city");
		jobDescription=resume.getString("JobDescription");
		benefits=new int[6];
		int length=resume.getJSONArray("Benefits").length();
		for(int i=0;i<length;i++)
		{
			benefits[i]=resume.getJSONArray("Benefits").getInt(i);
		}
	}
	public String CreatResume()
	{
		conn=new ConnectDatabase().connect();
		String sql1="insert into resume (jobName,salary,e_Name,property,scope,jobNeedNumber,acaQualification,province,city,jobDescription,time)"
				+ "values (?,?,?,?,?,?,?,?,?,?,?)";
		String sql2="insert into jobbenefits (resumeId,benefit1,benefit2,benefit3,benefit4,benefit5,benefit6) values (?,?,?,?,?,?,?)";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String time=sdf.format(new Date());
		int id=0;
		try {//创建招聘信息（向resume和benefits表中插入数据
			stmt=conn.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS); 
			stmt.setString(1, jobName);
			stmt.setInt(2, salary);
			stmt.setString(3, e_Name);
			stmt.setString(4, property);
			stmt.setString(5, scope);
			stmt.setInt(6, jobNeedNum);
			stmt.setString(7, acaQualification);
			stmt.setString(8, province);
			stmt.setString(9, city);
			stmt.setString(10, jobDescription);
			stmt.setString(11, time); 
			int count=stmt.executeUpdate();
			if(count>0) {//记录保存成功  
                set=stmt.getGeneratedKeys();  
                if(set.next())  id=set.getInt(1);  
        }  
			set.close();
			stmt.close();
			
			stmt=conn.prepareStatement(sql2); 
			stmt.setInt(1,id);
			for(int i=0;i<6;i++)
			{
				stmt.setInt(2+i,benefits[i]);
			}
			stmt.executeUpdate();
			stmt.close();
			conn.close();
			return "success";
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "fail";
		}
	}
}
