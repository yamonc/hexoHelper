package com.yamon.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @Author yamon
 * @Date 2023-06-21 09:49
 * @Description
 * @Version 1.0
 */
public class SQLiteUtils {

//    public static final String USER_DIRECTORY = System.getProperty("user.dir");
    public static final String USER_DIRECTORY = Objects.requireNonNull(SQLiteUtils.class.getResource("/db")).getPath();
    private static final String CLASS_NAME = "org.sqlite.JDBC";
//    private static final String DB_URL = "jdbc:sqlite:" + "D:\\sugon\\code_java\\code\\convert\\src\\main\\resources\\db\\hexoHelper.db";

//  打包环境
//    private static final String DB_URL = "jdbc:sqlite:db/hexoHelper.db";

    // dev环境
    private static final String DB_URL = "jdbc:sqlite:"+ USER_DIRECTORY +"hexoHelper.db";

    //    private static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS tags(id int not null primary key," +
//            "name varchar(20) not null,sex char(2) not null,age int not null," +
//            "rank varchar(30),salary decimal(18,2),party tinyint(1))";
    private static final String CREATE_SQL = "CREATE TABLE IF NOT EXISTS tags(id int not null primary key," +
            "name varchar(20) not null)";

    private static final String CREATE_CATEGORY_SQL = "CREATE TABLE IF NOT EXISTS categories (id int not null primary key, name varchar(20) not null";
    private static Connection conn;

    static {
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean createTable(String createSQL) throws ClassNotFoundException, SQLException {
        Class.forName(CLASS_NAME);
        conn = DriverManager.getConnection(DB_URL);
        Statement stmt = conn.createStatement();
        return stmt.execute(createSQL);
    }

    public static int insert(String tableName, String id, String name) throws SQLException {
        if (conn != null) {
            String sql = "INSERT INTO " + tableName + " VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            return pstmt.executeUpdate();
        } else {
            return 0;
        }
    }

    public static int insert1(String tableName, String item, String content) throws SQLException {
        if (conn != null) {
            String sql = "INSERT INTO " + tableName + " VALUES(?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, item);
            pstmt.setString(2, content);
            return pstmt.executeUpdate();
        } else {
            return 0;
        }
    }


    public static void delete(String condition) throws SQLException {
        if (conn != null) {
            String sql = "DELETE FROM tags WHERE " + condition;
            Statement statement = conn.createStatement();
            statement.executeUpdate(sql);
        }
    }

    public static void update(String tableName, String condition, String value) throws SQLException {
        if (conn != null) {
            String sql = "UPDATE " + tableName + " SET " + value + " WHERE " + condition;
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        }
    }

    public static List<String> query(String tableName, String condition) throws SQLException {
        List<String> result = new ArrayList<>();
        if (conn != null) {
            String sql = "SELECT * FROM " + tableName + " WHERE " + condition;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                result.add(rs.getString(2));
            }
        } else {
            System.out.println("conn is null");
        }
        return result;
    }


    public static void main(String[] args) {
        try {
            Class.forName(CLASS_NAME);
            conn = DriverManager.getConnection(DB_URL);
            System.out.println(conn);
            Statement stmt = conn.createStatement();
            stmt.execute(CREATE_SQL);
//            insert(1, "张三");
//            insert(2, "李四");
//            insert(3, "王五");
//            insert(4, "黄某");
//            insert(5, "冯某");
//            insert(6, "林某");
//            insert(7, "臭某");
//            insert(8, "艾总");
//            insert(9, "钱某");
//            insert(10, "Jerry");
//            insert(11, "Sans");

            System.out.println("查看所有记录：");
            // 查询所有插入记录
            // 删除年龄超过60岁的员工
            delete("age > 60");
            System.out.println("删除年龄超过60岁的员工后，记录如下所示：");
            // 查询剩余员工记录
            // 修改职称为高级且为党员的员工的工资设为8000
            System.out.println("职称为高级且为党员的员工记录如下所示：");
            // 查询职称为高级且为党员的员工的工资
            // 删除所有记录
            delete("1");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
