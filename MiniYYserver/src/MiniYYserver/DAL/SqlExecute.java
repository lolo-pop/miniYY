package MiniYYserver.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;

public class SqlExecute {
	
		
	private Connection conn;  	//���ݿ����Ӷ���
	private Statement stmt;   	//���ִ�ж���
	private ResultSet rset;		//�����
	public JTable table;

	private void getStatement() throws Exception {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			//����Ҫ�����Լ�ʵ�����ݿ��������޸�����������ַ���
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://localhost:1434;DatabaseName=MiniYY;user=sa;password=saas;");
			stmt = conn.createStatement();
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	//�ر�ʹ��������ݿ����ӣ���������ݿ������Ӧ�õ��ô˷����ر�����
	private void closeAll() throws SQLException {
		try {
			//��������Ƿ��ѹرգ����û�йرգ���ر�
			//û��ʹ�� isClosed()�����жϣ�ʹ��������
			//����Ѷ����ӵ��� close �����������һЩ���������ӹرա����ڵ����� close ��������ô˷���ʱ�����ŷ��� true��
			
			if( stmt != null ) {
				stmt.close();
				stmt = null;				
			}
			if( conn != null ) { 
				conn.close();
				conn = null;
			}
		}
		catch(SQLException se) {
			throw se;
		}
	}
	public String getPassWord(String id) throws Exception{
		
		String sql = "select password from users where userid ='"+id+"'";
		getStatement();
		//ִ�����ݿ��ѯ����ý����
		String c = new String();
		rset = stmt.executeQuery(sql);
		rset.next();
		c = rset.getString(1);
		rset.close();
		closeAll();
		return c;
	}
	
	// �����˺�sql
	public void createID(String id,String nickn,String kw,String sex,String pro,String cit,String ques,String ans,String img) throws Exception{
		String sql = "insert into Users(UserID,NickName,Password,Sex,Province,City,Question,Answer,Image)" 
				+ " values ('"+id+"','"+nickn+"','"+kw+"','"+sex+"','"+pro+"','"+cit+"','"+ques+"','"+ans+"','"+img+"')";
		
		getStatement();
		//ִ�����ݿ��ѯ����ý����
		stmt.execute(sql);
		closeAll();

	}
	
	public String getNickName(String id) throws Exception{
		
		String sql = "select NickName from users where userid ='"+id+"'";
		getStatement();
		//ִ�����ݿ��ѯ����ý����
		String c = new String();
		rset = stmt.executeQuery(sql);
		rset.next();
		c = rset.getString(1);
		rset.close();
		closeAll();
		return c;
	}
}
