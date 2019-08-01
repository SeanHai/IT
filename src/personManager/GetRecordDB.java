package personManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class GetRecordDB {

	private String username="";
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	
	public GetRecordDB(String str)
	{
		username=str;
	}
	
	public JSONObject getRecord()
	{
		String sql="select * from record where ÓÃ»§Ãû=?";
		JSONObject record=new JSONObject();
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, username);
			set=stmt.executeQuery();
			while(set.next())
			{
				try {
					record.put("name", set.getString(2));
					record.put("gender", set.getString(3));
					record.put("birthYear", set.getString(4));
					record.put("workedTime", set.getString(5));
					record.put("livingCity", set.getString(6));
					record.put("homeCity", set.getString(7));
					record.put("phoneNumber", set.getString(8));
					record.put("email", set.getString(9));
					record.put("degree", set.getString(10));
					record.put("introduction", set.getString(11));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			set.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return record;
	}
	
}
