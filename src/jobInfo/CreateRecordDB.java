package jobInfo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;
import login.Check_Regist;

public class CreateRecordDB {

	private String username,name,gender,livingCity,homeCity,phoneNumber,email,degree,introduction,birthYear;
	private int workedTime;
	private Connection conn;
	private PreparedStatement stmt;
	public CreateRecordDB(JSONObject object)
	{
		try {
			username=object.getString("username");
			name=object.getString("name");
			gender=object.getString("gender");
			livingCity=object.getString("livingCity");
			homeCity=object.getString("homeCity");
			phoneNumber=object.getString("phoneNumber");
			email=object.getString("email");
			degree=object.getString("degree");
			introduction=object.getString("introduction");
			birthYear=object.getString("birthYear");
			workedTime=object.getInt("workedTime");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public int Create()
	{
		Check_Regist demo=new Check_Regist();
		if(demo.TelNumMatch (phoneNumber)==4)//检查电话号码是否符合格式
			return 2;
		if(!(email.equals(""))&&demo.checkEmail(email)==0)//检查邮箱地址是否符合格式
			return 3;
		String sql="insert into record values (?,?,?,?,?,?,?,?,?,?,?)";
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, name);
			stmt.setString(3, gender);
			stmt.setString(4, birthYear);
			stmt.setInt(5, workedTime);
			stmt.setString(6, livingCity);
			stmt.setString(7, homeCity);
			stmt.setString(8, phoneNumber);
			stmt.setString(9, email);
			stmt.setString(10, degree);
			stmt.setString(11, introduction);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
			return 4;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 5;
	}
	
	public int Update()
	{
		Check_Regist demo=new Check_Regist();
		if(demo.TelNumMatch (phoneNumber)==4)//检查电话号码是否符合格式
			return 2;
		if(!(email.equals(""))&&demo.checkEmail(email)==0)//检查邮箱地址是否符合格式
			return 3;
		String sql="update record set 姓名=?,性别=?,出生年份=?,工作年限=?,现居住城市=?,籍贯=?,手机号码=?,邮箱=?,最高学历=?,自我介绍=? where 用户名=?";
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, name);
			stmt.setString(2, gender);
			stmt.setString(3, birthYear);
			stmt.setInt(4, workedTime);
			stmt.setString(5, livingCity);
			stmt.setString(6, homeCity);
			stmt.setString(7, phoneNumber);
			stmt.setString(8, email);
			stmt.setString(9, degree);
			stmt.setString(10, introduction);
			stmt.setString(11, username);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
			return 4;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 5;
	}
}
