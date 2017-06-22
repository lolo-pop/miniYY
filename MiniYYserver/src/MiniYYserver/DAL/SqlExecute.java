package MiniYYserver.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;

public class SqlExecute {
	
		
	private Connection conn;  	//数据库连接对象
	private Statement stmt;   	//语句执行对象
	private ResultSet rset;		//结果集
	public JTable table;

	private void getStatement() throws Exception {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			//你需要根据自己实际数据库的情况，修改这里的连接字符串
			conn = DriverManager.getConnection(
					"jdbc:sqlserver://localhost:1434;DatabaseName=MiniYY;user=sa;password=saas;");
			stmt = conn.createStatement();
		}
		catch(Exception e) {
			throw e;
		}
	}
	
	//关闭使用完的数据库连接，当完成数据库操作后应该调用此方法关闭连接
	private void closeAll() throws SQLException {
		try {
			//检查连接是否已关闭，如果没有关闭，则关闭
			//没有使用 isClosed()方法判断，使用受限制
			//如果已对连接调用 close 方法，或出现一些错误，则连接关闭。仅在调用了 close 方法后调用此方法时，它才返回 true。
			
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
		//执行数据库查询，获得结果集
		String c = new String();
		rset = stmt.executeQuery(sql);
		rset.next();
		c = rset.getString(1);
		rset.close();
		closeAll();
		return c;
	}
	
	// 申请账号sql
	public void createID(String id,String nickn,String kw,String sex,String pro,String cit,String ques,String ans,String img) throws Exception{
		String sql = "insert into Users(UserID,NickName,Password,Sex,Province,City,Question,Answer,Image)" 
				+ " values ('"+id+"','"+nickn+"','"+kw+"','"+sex+"','"+pro+"','"+cit+"','"+ques+"','"+ans+"','"+img+"')";
		
		getStatement();
		//执行数据库查询，获得结果集
		stmt.execute(sql);
		closeAll();

	}
	
	public String getNickName(String id) throws Exception{
		
		String sql = "select NickName from users where userid ='"+id+"'";
		getStatement();
		//执行数据库查询，获得结果集
		String c = new String();
		rset = stmt.executeQuery(sql);
		rset.next();
		c = rset.getString(1);
		rset.close();
		closeAll();
		return c;
	}
}
