import org.junit.Test;

import java.sql.*;

public class JdbcDemo {

    public Connection getConnection() throws SQLException {
        Connection connection = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?characterEncoding=utf-8", "root", "5251.Mysql");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void selectAll() throws SQLException {
        String id = null, name = null;
        Connection connection = getConnection();
        PreparedStatement ps;
        ResultSet rs;
        ps = connection.prepareStatement("select * from student");
        rs = ps.executeQuery();
        while (rs.next()) {
            id = rs.getString(1);
            name = rs.getString("name");
            System.out.println(id + " " + name);
        }
    }

    public String selectById(int id) throws SQLException {
        String name = null;
        Connection connection = getConnection();
        PreparedStatement ps;
        ResultSet rs;
        ps = connection.prepareStatement("select name from student where id = ?");
        ps.setInt(1, id);
        rs = ps.executeQuery();
        while (rs.next()) {
            name = rs.getString(1);
        }
        return name;
    }


    public boolean verify(int id, String password) throws SQLException {
        String name = null, sql = "select name from student where id = ? and password = ?";
        sql = sql.replaceFirst("\\?", String.valueOf(id));
        sql = sql.replaceFirst("\\?", password);
        System.out.println(sql);
        Connection connection = getConnection();
        Statement statement;
        ResultSet rs;
        statement = connection.createStatement();
        rs = statement.executeQuery(sql);
        while (rs.next()) {
            name = rs.getString(1);
            System.out.println(name);
        }
        if (name == null) {
            return false;
        }
        return true;
    }

    @Test
    public void test1() throws SQLException {
        selectAll();
        System.out.println(selectById(1));
        //sql注入攻击
        System.out.println(verify(1,"00 or 1=1"));
    }

}
