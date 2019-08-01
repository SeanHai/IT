package personManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;
import login.Check_Regist;

public class SetInfoManagerDB {

	private String username,phoneNumber,email,university,job,major,oldPass,newPass;
	private String P_E="";
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	private boolean type;
	private String which;
	private JSONObject object;
	public SetInfoManagerDB(JSONObject object)
	{
		try {
			this.object=object;
			which=object.getString("which");
			P_E=object.getString("P_E");
			type=object.getBoolean("type");
			username=object.getString("name");
			if(type)
			{
				oldPass=object.getString("oldPassword");
				newPass=object.getString("newPassword");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int Update()
	{
		Check_Regist demo=new Check_Regist();
		String sql="";
		conn=new ConnectDatabase().connect();
		if(which.equals("phone"))
		{
			try {
				phoneNumber=object.getString("phone");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(demo.TelNumMatch (phoneNumber)==4)//检查电话号码是否符合格式
				return 2;
			if(demo.check_Dou_phone(phoneNumber, 0)==0)//检查电话号码是否已被使用
				return 3;
			if(P_E.equals("P"))
				sql="update pregist set phoneNumber=? where username=?";
			else sql="update eregist set phoneNumber=? where Ename=?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1,phoneNumber);
				stmt.setString(2, username);
				stmt.executeUpdate();
				stmt.close();
				return 4;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;
			}
			
		}
		else if(which.equals("email"))
		{
			try {
				email=object.getString("email");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(demo.checkEmail(email)==0)//检查邮箱地址是否符合格式
				return 2;
			if(demo.check_Dou_email(email, 0)==0)//检查邮箱是否已被使用
				return 3;
			if(P_E.equals("P"))
				sql="update pregist set email=? where username=?";
			else sql="update eregist set email=? where Ename=?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, email);
				stmt.setString(2, username);
				stmt.executeUpdate();
				stmt.close();
				return 4;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;
			}
		}
		else if(which.equals("job"))
		{
			sql="update pregist set job=? where username=?";
			try {
				job=object.getString("job");
				try {
					stmt=conn.prepareStatement(sql);
					stmt.setString(1, job);
					stmt.setString(2, username);
					stmt.executeUpdate();
					stmt.close();
					return 4;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 6;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;
			}
		}
		else if(which.equals("university"))
		{
			sql="update pregist set university=? where username=?";
			try {
				university=object.getString("university");
				try {
					stmt=conn.prepareStatement(sql);
					stmt.setString(1, university);
					stmt.setString(2, username);
					stmt.executeUpdate();
					stmt.close();
					return 4;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 6;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;
			}
		}
		else if(which.equals("major"))
		{
			sql="update pregist set major=? where username=?";
			try {
				major=object.getString("major");
				try {
					stmt=conn.prepareStatement(sql);
					stmt.setString(1, major);
					stmt.setString(2, username);
					stmt.executeUpdate();
					stmt.close();
					return 4;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 6;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;
			}
		}
		else if(which.equals("headPicture"))
		{
			if(P_E.equals("P"))
				sql="update pregist set headPicture=? where username=?";
			else sql="update eregist set headpicture=? where Ename=?";
			try {
				try {
					stmt=conn.prepareStatement(sql);
					stmt.setString(1, object.getString("headPicture"));
					stmt.setString(2, username);
					stmt.executeUpdate();
					stmt.close();
					return 4;
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return 6;
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;
			}
		}
		else if(which.equals("password"))
		{
			try {
				oldPass=object.getString("oldPassword");
				newPass=object.getString("newPassword");
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if(P_E.equals("P"))
				sql="select password from pregist where username=?";
			else sql="select password from eregist where Ename=?";
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, username);
				set=stmt.executeQuery();
				while(set.next())
				{
					if(!(demo.MD5(oldPass).equals(set.getString(1))))
							return 5;
				}
				stmt.close();
				if(P_E.equals("P"))
					sql="update pregist set password=? where username=?";
				else sql="update eregist set password=? where Ename=?";
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, demo.MD5(newPass));
				stmt.setString(2, username);
				stmt.executeUpdate();
				stmt.close();
				return 4;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 6;
			}
		}
		return 6;
	}
}
