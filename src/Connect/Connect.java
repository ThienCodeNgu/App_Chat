package Connect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Connect {
	private PreparedStatement ps;
	private static Connection c;

	public static Connection getConnection() throws SQLException {

		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String connectionURL = "jdbc:sqlserver://LAPTOP-PB07JP04\\MSSQLSERVER22:1433; ;databaseName=AppChat;integratedSecurity=true";
			c = DriverManager.getConnection(connectionURL, "sa", "10102004");
			System.out.println("Kết nối thành công");
		} catch (ClassNotFoundException e) {
			System.out.println("kết nối thất bại");
			System.err.print(e.getMessage() + "/n" + e.getClass() + "/n" + e.getCause());
		}
		return c;

	}

	public Connect() throws SQLException {
		Connection c = Connect.getConnection();
	}

	public static void close() {
		if (c != null) {
			try {
				c.close();
				System.out.println("ngắt kết nối thành công");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public int login(String username, String passwordAccount) {
		try {
			ps = c.prepareStatement("select count(*) as result from account\r\n" + "		where username = (?) \r\n"
					+ "		and pass = (?) ;");
			ps.setString(1, username);
			ps.setString(2, passwordAccount);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("result");
			}
			return -6;
		} catch (SQLException e) {
			e.printStackTrace();
			return -6;
		}
	}

	public int checkUsername(String username) {
		try {
			ps = c.prepareStatement("select count(*) as result\r\n" + "from account\r\n" + "where username = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getInt("result");
			}
			return 3;
		} catch (SQLException e) {
			e.printStackTrace();
			return 3;
		}
	}

	public String getName(String username) {
		try {
			ps = c.prepareStatement("select fullname as ten from account where username = ?");
			ps.setString(1, username);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				return rs.getString("ten");
			}
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}

	}

	public int signup(String username, String fullname, String pass) {
		try {
			ps = c.prepareStatement("insert into account (username, fullname, pass)\r\n"
					+ "				values (?, ?,?)\r\n" + "				select @@rowcount as result; ");
			ps.setString(1, username);
			ps.setString(2, fullname);
			ps.setString(3, pass);
			int rs = ps.executeUpdate();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return 3;
		}

	}

	public int changePass(String password, String username) {
		try {
			ps = c.prepareStatement("update account set pass = ?  where username = ? select @@rowcount as result;");
			ps.setString(1, password);
			ps.setString(2, username);
			int rs = ps.executeUpdate();
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
			return 3;
		}
	}

	public static void main(String[] args) throws SQLException {

		Connect con = new Connect();

		System.out.println(con.getName("vothien"));

	}
}
