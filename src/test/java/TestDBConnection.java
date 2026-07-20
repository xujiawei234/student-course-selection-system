import java.sql.Connection;
import java.sql.SQLException;

import com.cs.util.DBUtil;

public class TestDBConnection {
    public static void main(String[] args) {

        Connection conn = null;

        try {
            conn = DBUtil.getConnection();

            if(conn != null) {
                System.out.println("🎉 恭喜！数据库连接成功！");
                System.out.println("当前连接的数据库: " + conn.getCatalog());
            }
        }
        catch(SQLException e) {
            System.err.println("❌ 数据库连接失败，请检查：1. 密码是否正确 2. MySQL 服务是否启动 3. 数据库名是否为 course_select");
            e.printStackTrace();
        }
        finally {
            DBUtil.closeConnection(conn);
        }
    }
}
