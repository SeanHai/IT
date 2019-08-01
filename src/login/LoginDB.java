package login;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.json.JSONException;
import org.json.JSONObject;
import datebase.ConnectDatabase;
public class LoginDB {

	private String username;
	private String password;
	private String type;//登录类型，"0"   个人，"1" 公司
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	private String result="";
	public LoginDB(JSONObject object)
	{
		try {
			username=object.getString("userName");
			password=object.getString("passWord");
			type=object.getString("type");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String MD5(String str) {//密码加密MD5算法
		MessageDigest md5 = null;
		try {
		md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
		e.printStackTrace();
		return "";
		}
		 
		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];
		 
		for (int i = 0; i < charArray.length; i++) {
		byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);
		 
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
		int val = ((int) md5Bytes[i]) & 0xff;
		if (val < 16) {
		hexValue.append("0");
		}
		hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
		}
	
	
	public String login()
	{
		password=MD5(password);
		String sql[]=new String[2];
		sql[0]="select headPicture from pregist where (username=? or phoneNumber=? or email=?) and password=?";
		sql[1]="select Ename from eregist where (Ename=? or phoneNumber=? or email=?) and password=?";
		try{
		conn=new ConnectDatabase().connect();
		int Type=Integer.parseInt(type);
		stmt=conn.prepareStatement(sql[Type]);
		stmt.setString(1, username);
		stmt.setString(2, username);
		stmt.setString(3, username);
		stmt.setString(4, password);
		set=stmt.executeQuery();
		set.last();
		if(set.getRow()==0)
			{
				set.close();
				stmt.close();
				conn.close();
				return "fail";
			}
		else {
//			set.next();
			if(Type==0)//个人
			{
				result=set.getString(1)==null?"":set.getString(1);
				set.close();
				stmt.close();
				conn.close();
				return result;
			}
			else//公司
			{
				String s="select headPicture from eregist where Ename=?";
				stmt=conn.prepareStatement(s);
				stmt.setString(1,set.getString(1));
				set=stmt.executeQuery();
				set.next();
				result=set.getString(1)==null?"":set.getString(1);
//				result=set.getString(1);
				set.close();
				stmt.close();
				conn.close();
				return result;
			}
			}
		}catch(Exception e){e.printStackTrace();return "error";}
	}
}
