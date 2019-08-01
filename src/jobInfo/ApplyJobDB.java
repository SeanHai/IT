package jobInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class ApplyJobDB {

	private String username,e_Name;
	private int resumeId;
	private String type;
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	
	public ApplyJobDB(JSONObject object)
	{
		try {
			type=object.getString("type");
			username=object.getString("username");
			if(type.equals("write"))
				{
					resumeId=object.getInt("resumeId");
					e_Name=object.getString("e_Name");
				}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int Check()
	{
		String sql="";
		if(type.equals("read"))
			sql="select * from record where �û���=?";
		else sql="insert into resume_record (�û���,ְλID,��˾,����ʱ��) values (?,?,?,?)";
		int result;
		conn=new ConnectDatabase().connect();
		if(type.equals("read"))
		{
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, username);
				set=stmt.executeQuery();
				set.last();
				result=set.getRow();
				set.close();
				stmt.close();
				conn.close();
				return result+7;//handler��caseֵ�Ѿ���6�ˣ�result=0,��ʾû�м�����result=1����ʾ�м�����
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;
			}
		}
		else
		{
			String sql1="select * from resume_record where �û���=? and ְλID=?";
			try {
				stmt=conn.prepareStatement(sql1);
				stmt.setString(1, username);
				stmt.setInt(2, resumeId);
				set=stmt.executeQuery();
				set.last();
				if(set.getRow()==1)
					return 10;//�����ظ�����
				set.close();
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;
			}
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String time=sdf.format(new Date());
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, username);
				stmt.setInt(2, resumeId);
				stmt.setString(3,e_Name);
				stmt.setString(4, time);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
				return 9;//����ɹ�
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;//����ʧ��
			}
			
		}
	}
}
