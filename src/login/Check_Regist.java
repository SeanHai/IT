package login;

import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.json.JSONException;
import org.json.JSONObject;

import datebase.ConnectDatabase;

public class Check_Regist {

	private String username;
	private String password;
	private int type;//�û�ע�����ͣ�0 ���ˣ�1 ��˾
	private String phoneNumber;
	private String email;
	private String EName;//��˾��
	private String ENumber;//Ӫҵִ�պ�
	private Connection conn;
	private PreparedStatement stmt;
	private ResultSet set;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEName() {
		return EName;
	}
	public void setEName(String eName) {
		EName = eName;
	}
	public String getENumber() {
		return ENumber;
	}
	public void setENumber(String eNumber) {
		ENumber = eNumber;
	}
	
	public static String MD5(String str) {//�������MD5�㷨
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
	
	public int TelNumMatch (String phone){  //�жϵ绰�����Ƿ������ȷ��ʽ
		  
	    /* 
	     * �ƶ�: 2G�Ŷ�(GSM����)��139,138,137,136,135,134,159,158,152,151,150, 
	     * 3G�Ŷ�(TD-SCDMA����)��157,182,183,188,187 147���ƶ�TD������ר�úŶ�. ��ͨ: 
	     * 2G�Ŷ�(GSM����)��130,131,132,155,156 3G�Ŷ�(WCDMA����)��186,185 ����: 
	     * 2G�Ŷ�(CDMA����)��133,153 3G�Ŷ�(CDMA����)��189,180 
	     */  
	    final String YD = "^[1]{1}(([3]{1}[4-9]{1})|([5]{1}[012789]{1})|([8]{1}[2378]{1})|([4]{1}[7]{1}))[0-9]{8}$";  
	    final String LT = "^[1]{1}(([3]{1}[0-2]{1})|([5]{1}[56]{1})|([8]{1}[56]{1}))[0-9]{8}$";  
	    final String DX = "^[1]{1}(([3]{1}[3]{1})|([5]{1}[3]{1})|([8]{1}[09]{1}))[0-9]{8}$";  
	  
	        int flag;// �洢ƥ����  
	        // �ж��ֻ������Ƿ���11λ  
	        if (phone.length() == 11) {  
	            // �ж��ֻ������Ƿ�����й��ƶ��ĺ������  
	            if (phone.matches(YD)) {  
	                flag = 1;  
	            }  
	            // �ж��ֻ������Ƿ�����й���ͨ�ĺ������  
	            else if (phone.matches(LT)) {  
	                flag = 2;  
	            }  
	            // �ж��ֻ������Ƿ�����й����ŵĺ������  
	            else if (phone.matches(DX)) {  
	                flag = 3;  
	            }  
	            // �������� δ֪  
	            else {  
	                flag = 4;  
	            }  
	        }  
	        // ����11λ  
	        else {  
	            flag = 4;  
	        }  
	        return flag;  
	    }  
	
	public int checkEmail(String email)//�ж������ʽ�Ƿ���ȷ
	{
		int answer;
		final String str="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		if(email.matches(str))
			answer=1;
		else answer=0;
		return answer;
	}
	
	public int check_Dou_username(String str,int type)//����û����Ƿ���ע��,type�����û�����
	{
		String sql[]=new String[2];
		sql[0]="select * from pregist where username=?";
		sql[1]="select * from eregist where username=?";
		int flag;//0 �ѱ�ע�ᣬ1 δ��ע��
		conn=new ConnectDatabase().connect();
		try{
			stmt=conn.prepareStatement(sql[type]);
			stmt.setString(1, str);
			set=stmt.executeQuery();
			set.last();
			if(set.getRow()==0)
				flag=1;
			else flag=0;
			set.close();
			stmt.close();
			conn.close();
			return flag;
		}catch(Exception e){e.printStackTrace();return 2;}
	}
	
	public int check_Dou_phone(String str,int type)//���绰�Ƿ���ע��
	{
		String sql[]=new String[2];
		sql[0]="select * from pregist where phoneNumber=?";
		sql[1]="select * from eregist where phoneNumber=?";
		int flag;//0 �ѱ�ע�ᣬ1 δ��ע��
		conn=new ConnectDatabase().connect();
		try{
			stmt=conn.prepareStatement(sql[type]);
			stmt.setString(1, str);
			set=stmt.executeQuery();
			set.last();
			if(set.getRow()==0)
				flag=1;
			else flag=0;
			set.close();
			stmt.close();
			conn.close();
			return flag;
		}catch(Exception e){e.printStackTrace();return 0;}
	}

	public int check_Dou_email(String str,int type)//��������Ƿ���ע��
	{
		String sql[]=new String[2];
		sql[0]="select * from pregist where email=?";
		sql[1]="select * from eregist where email=?";
		int flag;//0 �ѱ�ע�ᣬ1 δ��ע��
		conn=new ConnectDatabase().connect();
		try{
			stmt=conn.prepareStatement(sql[type]);
			stmt.setString(1, str);
			set=stmt.executeQuery();
			set.last();
			if(set.getRow()==0)
				flag=1;
			else flag=0;
			set.close();
			stmt.close();
			conn.close();
			return flag;
		}catch(Exception e){e.printStackTrace();return 0;}
	}

	public int check_Dou_EName(String str)//��鹫˾���Ƿ���ע��
	{
		String sql="select * from eregist where Ename=?";
		int flag;//0 �ѱ�ע�ᣬ1 δ��ע��
		conn=new ConnectDatabase().connect();
		try{
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, str);
			set=stmt.executeQuery();
			set.last();
			if(set.getRow()==0)
				flag=1;
			else flag=0;
			set.close();
			stmt.close();
			conn.close();
			return flag;
		}catch(Exception e){e.printStackTrace();return 0;}
	}

	public int check_Dou_ENumber(String str)//���Ӫҵִ�պ��Ƿ���ע��
	{
		String sql="select * from eregist where Enumber=?";
		int flag;//0 �ѱ�ע�ᣬ1 δ��ע��
		conn=new ConnectDatabase().connect();
		try{
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, str);
			set=stmt.executeQuery();
			set.last();
			if(set.getRow()==0)
				flag=1;
			else flag=0;
			set.close();
			stmt.close();
			conn.close();
			return flag;
		}catch(Exception e){e.printStackTrace();return 0;}
	}
	
	public int checkPhone(JSONObject object)
	{
		int flag=0;
		int t=0;
		try {
			String type=object.getString("PE");
			if(type.equals("person"))
				t=0;
			else t=1;
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			String phone=object.getString("phoneNumber");
			if(TelNumMatch(phone)==4)
				flag=2;	
			
			if(check_Dou_phone(phone,t)==0)
				flag=3;
			return flag;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}
	
	public int P_register(JSONObject object)//����ע��
	{
		int flag=0;//������� 1���û����ѱ�ע�᣻2�����볤�Ȳ�����3���绰���벻����4���绰�����ѱ�ע�᣻5�������ʽ����ȷ��6�������ѱ�ע��
		try {
			username=object.getString("username");
			password=object.getString("password");
			phoneNumber=object.getString("phoneNumber");
			email=object.getString("email");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(check_Dou_username(username,0)==0)
			flag=1;
		if(password.length()<6||password.length()>12)
			flag=2;
		else password=MD5(password);
		if(checkEmail(email)==0)
				flag=5;
		if(check_Dou_email(email,0)==0)
			flag=6;
		
		String sql="insert into pregist (username,password,phoneNumber,email) values(?,?,?,?)";
		if(flag==0){
					conn=new ConnectDatabase().connect();
					try{
						stmt=conn.prepareStatement(sql);
						stmt.setString(1, username);
						stmt.setString(2, password);
						stmt.setString(3, phoneNumber);
						stmt.setString(4, email);
						stmt.executeUpdate();
						stmt.close();
						conn.close();
					}catch(Exception e){e.printStackTrace();return 7;}
		}
		return flag;
	}

	public int E_register(JSONObject object)//��˾ע��
	{
		int flag=0;//�������1�������ʽ����ȷ��2�������ѱ�ע��;3:��˾���ѱ�ע�᣻4��Ӫҵִ�պŸ�ʽ������5��Ӫҵִ�պ��ѱ�ע��
		
		try {
			password=object.getString("password");
			phoneNumber=object.getString("phoneNumber");
			email=object.getString("email");
			EName=object.getString("e_Name");
			ENumber=object.getString("e_Number");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		password=MD5(password);
		
		if(checkEmail(email)==0)
				flag=1;
			
		if(check_Dou_email(email,1)==0)
			flag=2;
		
		if(check_Dou_EName(EName)==0)
			flag=3;
	
		if(ENumber.length()!=15)
			flag=4;

		if(check_Dou_ENumber(ENumber)==0)
			flag=5;
		
		String sql="insert into eregist values(?,?,?,?,?)";
		if(flag==0){
					conn=new ConnectDatabase().connect();
					try{
						stmt=conn.prepareStatement(sql);
						stmt.setString(1, password);
						stmt.setString(2, phoneNumber);
						stmt.setString(3, email);
						stmt.setString(4, EName);
						stmt.setString(5, ENumber);
						stmt.executeUpdate();
						stmt.close();
						conn.close();
					}catch(Exception e){e.printStackTrace();}
		}
		return flag;
	}
}
