package enterpriseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class CompanyViewDB {
	protected String e_Name="";
	protected String property="";
	protected String scope="";
	protected String address="";
	protected String companyInfo="";
	protected String headPicture="";
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	
	public void Find(String username)
	{
		conn=new ConnectDatabase().connect();
		String sql="select e_Name,property,scope,address,companyView,headpicture from companyview inner join eregist on companyview.e_Name=eregist.Ename where companyview.e_Name=?";
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, username);
			set=stmt.executeQuery();
			while(set.next())
			{
				e_Name=set.getString(1);
				property=set.getString(2);
				scope=set.getString(3);
				address=set.getString(4);
				companyInfo=set.getString(5);
				headPicture=set.getString(6)==null?"":set.getString(6);
			}
			set.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public String Creat(JSONObject view)
	{
		try {
			e_Name=view.getString("E_Name");
			property=view.getString("Property");
			scope=view.getString("Scope");
			address=view.getString("CompanyAddress");
			companyInfo=view.getString("CompanyInfo");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conn=new ConnectDatabase().connect();
		String sql1="select * from companyview where e_Name=?";
		String sql2="insert into companyview values (?,?,?,?,?)";
		String sql3="update companyview set property=?,scope=?,address=?,companyView=?";
		try {
			stmt=conn.prepareStatement(sql1);
			stmt.setString(1, e_Name);
			set=stmt.executeQuery();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			if(set.next())
			{
				stmt.close();
				stmt=conn.prepareStatement(sql3);
				stmt.setString(1, property);
				stmt.setString(2, scope);
				stmt.setString(3, address);
				stmt.setString(4, companyInfo);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
				return "success";
			}
			else{
					stmt=conn.prepareStatement(sql2);
					stmt.setString(1, e_Name);
					stmt.setString(2, property);
					stmt.setString(3, scope);
					stmt.setString(4, address);
					stmt.setString(5, companyInfo);
					stmt.executeUpdate();
					stmt.close();
					conn.close();
					return "success";
				}
			} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					return "fail";
			}
		}
  }
