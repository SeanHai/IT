package activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class SetActivityDB {
	private String type,username,content,time;
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	private int id;
	private int praiseTimes;
	public SetActivityDB(JSONObject object)
	{
		try {
			type=object.getString("type");
			if(type.equals("share"))
			{
				username=object.getString("username");
				content=object.getString("content");
			}
			else if(type.equals("prise")||type.equals("priseQuestion"))
				{
					id=object.getInt("id");
					username=object.getString("username");
					praiseTimes=object.getInt("times");
				}
			else if(type.equals("collect")||type.equals("collectques"))
			{
				id=object.getInt("id");
				username=object.getString("username");
			}
			else if(type.equals("ques_answer"))
			{
				username=object.getString("username");
				content=object.getString("content");
			}
			else if(type.equals("answer"))
			{
				username=object.getString("username");
				content=object.getString("content");
				id=object.getInt("id");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int setInformation()
	{
		String sql="";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time=sdf.format(new Date());//分享时间
		if(type.equals("share"))
		{
			sql="insert into sharedinformation (username,content,sharedTime) values (?,?,?)";
			conn=new ConnectDatabase().connect();
			try {
				stmt=conn.prepareStatement(sql);
				stmt.setString(1, username);
				stmt.setString(2, content);
				stmt.setString(3, time);
				stmt.executeUpdate();
				stmt.close();
				conn.close();
				return 3;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 4;
			}
		}
		else if(type.equals("ques_answer"))
			return ShareQues();
		else if(type.equals("prise")||type.equals("priseQuestion"))
			return Prise();
		else if(type.equals("collect")||type.equals("collectques"))
			return Collect();
		else if(type.equals("answer"))
			return Answer();
		else return -1;
	}
	public int Prise()//点赞
	{
		String check;
		String sql;
		String sql1;
		String insertSql;
		if(type.equals("prise"))
			{
				check="select * from prise where id=? and username=?";
				sql="select prisedTimes from sharedinformation where id=?";
				sql1="update sharedinformation set prisedTimes=? where id=?";
				insertSql="insert into prise values (?,?)";
			}
		else 
			{
				sql1="update sharedquestions set prisedTimes=? where id=?";
			}
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(sql1);
			stmt.setInt(1, praiseTimes);
			stmt.setInt(2, id);
			stmt.executeUpdate();
//			stmt=conn.prepareStatement(insertSql);
//			stmt.setInt(1, id);
//			stmt.setString(2, username);
//			stmt.executeUpdate();
			stmt.close();
			conn.close();
			return 5;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 6;
		}
		
	}
	public int Collect()//收藏
	{
		String check;
		if(type.equals("collect"))
			check="select * from collectsharedinformation where sharedid=? and username=?";
		else check="select * from collectsharedques where id=? and username=?";
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(check);
			stmt.setInt(1, id);
			stmt.setString(2, username);
			set=stmt.executeQuery();
			set.last();
			if(set.getRow()!=0)
				{
					set.close();
					stmt.close();
					conn.close();
					return 8;
				}
			else
			{
				set.close();
				if(type.equals("collect"))
					check="select username from sharedinformation where id=?";
				else check="select username from sharedquestions where id=?";
				stmt=conn.prepareStatement(check);
				stmt.setInt(1, id);
				set=stmt.executeQuery();
				if(set.next())
				{
					if(username.equals(set.getString(1)))
					{
						set.close();
						stmt.close();
						conn.close();
						return 11;
					}
					else
					{
						set.close();
						String sql;
						if(type.equals("collect"))
							sql="insert into collectsharedinformation values (?,?)";
						else sql="insert into collectsharedques values (?,?)";
						stmt=conn.prepareStatement(sql);
						stmt.setInt(1, id);
						stmt.setString(2, username);
						stmt.executeUpdate();
						stmt.close();
						conn.close();
						return 9;
					}
				}
				else return 10;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 10;
		}
		
	}
	
	public int ShareQues()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time=sdf.format(new Date());//分享时间
		String sql="insert into sharedquestions (username,content,time) values (?,?,?)";
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, username);
			stmt.setString(2, content);
			stmt.setString(3, time);
			stmt.executeUpdate();
			stmt.close();
			conn.close();
			return 3;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 4;
		}
	}
	
	public int Answer()
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		time=sdf.format(new Date());//回复时间
		String sql="insert into answersharedques values (?,?,?,?)";
		conn=new ConnectDatabase().connect();
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.setString(2, username);
			stmt.setString(3, content);
			stmt.setString(4, time);
			stmt.executeUpdate();
			return 12;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 13;
		}
	}
}
