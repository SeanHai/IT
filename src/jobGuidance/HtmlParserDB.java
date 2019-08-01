package jobGuidance;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.LinkRegexFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class HtmlParserDB {

	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	private String type,url;
	
	public HtmlParserDB(String t)
	{
		type=t;
	}
	
	public void Start()
	{
		conn=new ConnectDatabase().connect();
		String sql="select * from originurl";
		try {
			stmt=conn.prepareStatement(sql);
			set=stmt.executeQuery();
			while(set.next())
			{
				String url=set.getString(1);
				String pattern=set.getString(2);
				LinkRegexFilter filter=new LinkRegexFilter(pattern);
				extracLinks(url,filter);
			}
			set.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public  void extracLinks(String url,LinkRegexFilter filter)
	{
		PreparedStatement stmt1;
		String sql1="insert into lawgetlink values(?,?)";
		Parser parser;
		try {
			parser = new Parser(url);
			parser.setEncoding(parser.getEncoding());
			NodeFilter Filter1=new TagNameFilter("span");
			NodeList list;
			list = parser.extractAllNodesThatMatch(Filter1);
				for(int i=0;i<list.size();i++)
				 {
					 Node tag = list.elementAt(i);
					 if (tag.getParent() instanceof LinkTag)
					 {
						 LinkTag link = (LinkTag) tag.getParent();
						 if(filter.accept(link))
						 {
							 try {
								 stmt1=conn.prepareStatement(sql1);
								 stmt1.setString(1,tag.toPlainTextString());
								 stmt1.setString(2,link.getLink());
								 stmt1.executeUpdate();
								 stmt1.close();
							} catch (SQLException e) {}//插入失败表示获得的连接重复了
						 }
					 }
				 }
		} catch (ParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public JSONArray Select()
	{
		conn=new ConnectDatabase().connect();
		String sql="select * from lawgetlink where title=?";
		try {
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, type);
			set=stmt.executeQuery();
			while(set.next())
			{
				url=set.getString(2);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getContent();
	}
	
	public JSONArray getContent()
	{
		Parser parser;
		PreparedStatement stmt1;
		String sql1="";
		JSONArray array=new JSONArray();//返回的信息组（包括标题和内容）
		if(type.equals("劳动法苑"))
			sql1="insert into laodongfayuan values(?,?,?)";
		else if(type.equals("简历攻略"))
			sql1="insert into jianligonglue values(?,?,?)";
		else if(type.equals("面试指南"))
			sql1="insert into mianshigonglue values(?,?,?)";
		else sql1="insert into xinzifuli values(?,?,?)";
		try {
			parser = new Parser(url);
			parser.setEncoding(parser.getEncoding());
			NodeFilter Filter1=new TagNameFilter("strong");
			NodeList list;
			list = parser.extractAllNodesThatMatch(Filter1);
			for(int i=0;i<list.size();i++)
			 {
				 JSONObject object=new JSONObject();
				 Node tag = list.elementAt(i);
				 if (tag.getParent() instanceof LinkTag)
				 {
					 LinkTag link = (LinkTag) tag.getParent();
					 String content=Content(link.getLink());
					 try {
						 stmt1=conn.prepareStatement(sql1);
						 stmt1.setString(1,link.toPlainTextString());
						 stmt1.setString(2, content);
						 stmt1.setString(3,link.getLink());
						 stmt1.executeUpdate();
						 stmt1.close();
					} catch (SQLException e) {}//插入失败表示获得的连接重复了
					try {
						object.put("title", link.toPlainTextString());
						object.put("content", content);
						array.put(object);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
			 }
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
	
	public String Content(String url)
	{
		Parser parser;
		String content="";
		try {
			parser = new Parser(url);
			parser.setEncoding(parser.getEncoding());
			NodeFilter Filter1=new TagNameFilter("table");
			NodeList list1;
			list1 = parser.extractAllNodesThatMatch(Filter1);
			for(int i=0;i<list1.size();i++)
			 {
				 Node tag = list1.elementAt(i);
				 String html=tag.toHtml();
				 parser=Parser.createParser(html, "htf-8");
				 NodeFilter Filter2=new TagNameFilter("p");
				 NodeList list2=parser.extractAllNodesThatMatch(Filter2);
				 for(int j=0;j<list2.size();j++)
				 {
					 Node node=list2.elementAt(j);
					 if(node.getChildren().size()==1&&node.getFirstChild().getFirstChild()==null&&!(node.getFirstChild().toPlainTextString().equals("精彩文章推荐：")))
						 content+=node.toPlainTextString();
				 }
			 }
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return  content;
	}
	
	public JSONArray getContentDir()//直接获取信息
	{
		conn=new ConnectDatabase().connect();
		String sql="";
		if(type.equals("劳动法苑"))
			 sql="select * from laodongfayuan";
		else if(type.equals("简历攻略"))
			sql="select * from jianligonglue";
		else if(type.equals("面试指南"))
			sql="select * from mianshigonglue";
		else sql="select * from xinzifuli";
		JSONArray array=new JSONArray();
		try {
			stmt=conn.prepareStatement(sql);
			set=stmt.executeQuery();
			while(set.next())
			{
				JSONObject object=new JSONObject();
				try {
					object.put("title", set.getString(1));
					object.put("content", set.getString(2));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				array.put(object);
			}
			if(array.length()==0)//数据库中的劳动法苑相关内容为空则从网络上重新获取信息
			{
				Start();
				return Select();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return array;
	}
}
