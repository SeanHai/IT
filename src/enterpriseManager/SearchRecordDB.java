package enterpriseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class SearchRecordDB {
	private String P_E;//访问类型（公司或个人）
	private String type;//已回答的或未回答的
	private String name,username;
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set,set1;
	public SearchRecordDB(JSONObject object)
	{
		try {
			P_E=object.getString("P_E");
			type=object.getString("type");
			name=object.getString("name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public JSONArray SearchRecord()
	{
		String sql="",sql1="";
		conn=new ConnectDatabase().connect();
		JSONArray array=new JSONArray();
		if(P_E.equals("E"))//公司
		{
			if(type.equals("unanswer"))
				sql="select 姓名,性别,出生年份,工作年限,现居住城市,籍贯,手机号码,邮箱,最高学历,自我介绍,jobName,resume_record.用户名,职位ID,申请时间 "
					+ "from (resume_record inner join resume on resume_record.职位ID=resume.resumeId) "
					+ "inner join record on resume_record.用户名=record.用户名  where 公司=? and 回复='待回复··'";
			else 
				sql="select 姓名,性别,出生年份,工作年限,现居住城市,籍贯,手机号码,邮箱,最高学历,自我介绍,jobName,resume_record.用户名,职位ID,申请时间 ,回复,回复时间 "
					+ "from (resume_record inner join resume on resume_record.职位ID=resume.resumeId) "
					+ "inner join record on resume_record.用户名=record.用户名  where 公司=? and 回复<>'待回复··'";
			sql1="select headPicture from pregist where username=?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, name);
				set=stmt.executeQuery();
				while(set.next())
				{
					username=set.getString(12);//获取用户名以获取用户头像
					stmt=conn.prepareStatement(sql1);
					stmt.setString(1, username);
					set1=stmt.executeQuery();
					JSONObject object=new JSONObject();
					try {
						if(set1.next())
							object.put("headPicture", set1.getString(1)==null?"":set1.getString(1));
						object.put("name", set.getString(1));
						object.put("gender", set.getString(2));
						object.put("birthYear", set.getInt(3));
						object.put("workTime", set.getInt(4));
						object.put("livingCity", set.getString(5));
						object.put("homeCity", set.getString(6));
						object.put("phone", set.getString(7));
						object.put("email", set.getString(8));
						object.put("acaQualification", set.getString(9));
						object.put("description", set.getString(10));
						object.put("jobName", set.getString(11));
						object.put("username", set.getString(12));
						object.put("resumeId", set.getInt(13));
						object.put("applyTime", set.getString(14));
						if(type.equals("answer"))
						{
							object.put("answer", set.getString(15));
							object.put("answerTime", set.getString(16));
						}
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
		}
		else 
		{
			if(type.equals("answered"))
				sql="select e_Name,jobName,salary,province,city,申请时间,回复,回复时间 from resume_record "
						+ "inner join resume on resume_record.职位ID=resume.resumeId where 用户名=? and 回复<>'待回复··'";
			else
				sql="select e_Name,jobName,salary,province,city,申请时间  from resume_record "
						+ "inner join resume on resume_record.职位ID=resume.resumeId where 用户名=? and 回复='待回复··'";
			sql1="select headpicture from eregist where Ename=?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1,name);
				set=stmt.executeQuery();
				while(set.next())
				{
					username=set.getString(1);//获取用户名以获取用户头像
					stmt=conn.prepareStatement(sql1);
					stmt.setString(1, username);
					set1=stmt.executeQuery();
					JSONObject object=new JSONObject();
					try {
						if(set1.next())
							object.put("headPicture", set1.getString(1)==null?"":set1.getString(1));
						object.put("eName", set.getString(1));
						object.put("jobName", set.getString(2));
						object.put("salary", set.getInt(3));
						object.put("province", set.getString(4));
						object.put("city", set.getString(5));
						object.put("applyTime", set.getString(6));
						if(type.equals("answered"))
						{
							object.put("answer", set.getString(7));
							object.put("answerTime", set.getString(8));
						}
						array.put(object);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return array;
	}
}
