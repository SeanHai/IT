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
	private String P_E;//�������ͣ���˾����ˣ�
	private String type;//�ѻش�Ļ�δ�ش��
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
		if(P_E.equals("E"))//��˾
		{
			if(type.equals("unanswer"))
				sql="select ����,�Ա�,�������,��������,�־�ס����,����,�ֻ�����,����,���ѧ��,���ҽ���,jobName,resume_record.�û���,ְλID,����ʱ�� "
					+ "from (resume_record inner join resume on resume_record.ְλID=resume.resumeId) "
					+ "inner join record on resume_record.�û���=record.�û���  where ��˾=? and �ظ�='���ظ�����'";
			else 
				sql="select ����,�Ա�,�������,��������,�־�ס����,����,�ֻ�����,����,���ѧ��,���ҽ���,jobName,resume_record.�û���,ְλID,����ʱ�� ,�ظ�,�ظ�ʱ�� "
					+ "from (resume_record inner join resume on resume_record.ְλID=resume.resumeId) "
					+ "inner join record on resume_record.�û���=record.�û���  where ��˾=? and �ظ�<>'���ظ�����'";
			sql1="select headPicture from pregist where username=?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, name);
				set=stmt.executeQuery();
				while(set.next())
				{
					username=set.getString(12);//��ȡ�û����Ի�ȡ�û�ͷ��
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
				sql="select e_Name,jobName,salary,province,city,����ʱ��,�ظ�,�ظ�ʱ�� from resume_record "
						+ "inner join resume on resume_record.ְλID=resume.resumeId where �û���=? and �ظ�<>'���ظ�����'";
			else
				sql="select e_Name,jobName,salary,province,city,����ʱ��  from resume_record "
						+ "inner join resume on resume_record.ְλID=resume.resumeId where �û���=? and �ظ�='���ظ�����'";
			sql1="select headpicture from eregist where Ename=?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1,name);
				set=stmt.executeQuery();
				while(set.next())
				{
					username=set.getString(1);//��ȡ�û����Ի�ȡ�û�ͷ��
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
