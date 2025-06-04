import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.sql.*; // JDBC相关导入

public class BookManager {
    // 数据库连接配置（请根据实际环境修改）
    private static final String DB_URL = "jdbc:mysql://localhost:3306/library?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "YOUR_PASSWORD"; // 确认密码是否正确
    private Connection conn; // 数据库连接对象

    public BookManager() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("数据库连接成功！本项目来自https://github.com/luminous-ChenXi/ASimpleBookManager");
        } catch (SQLException e) {
            System.out.println("数据库连接失败：" + e.getMessage());
            // 连接失败时抛出异常，阻止程序继续执行
            throw new RuntimeException("数据库连接失败，程序终止");
        }
    }

    // ====================== 核心功能方法（仅保留数据库版） ======================

    // 1. 添加图书（自动去重，ISBN唯一）
    public void addBook(Book book) {
        // 新增：检查数据库连接是否有效
        if (conn == null) {
            System.out.println("错误：数据库连接未成功建立，无法执行添加操作！");
            return;
        }
        String sql = "INSERT INTO books (isbn, title, author, stock, borrow_count) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (findBookByIsbn(book.getIsbn()) != null) {
                System.out.println("错误：ISBN已存在，图书添加失败！");
                return;
            }
            pstmt.setString(1, book.getIsbn());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getAuthor());
            pstmt.setInt(4, book.getStock());
            pstmt.setInt(5, book.getBorrowCount());
            pstmt.executeUpdate();
            System.out.println("图书添加成功！");
        } catch (SQLException e) {
            System.out.println("添加图书失败：" + e.getMessage());
        }
    }

    /**
     * 处理图书借阅逻辑
     * @param isbn 要借阅的图书ISBN
     * @return boolean 借阅是否成功
     */
    public boolean borrowBook(String isbn) {
        String sql = "UPDATE books SET stock = stock - 1, borrow_count = borrow_count + 1 WHERE isbn = ? AND stock > 0";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("借阅图书失败：" + e.getMessage());
            return false;
        }
    }

    // 3. 归还图书（根据ISBN归还，库存加1）
    public boolean returnBook(String isbn) {
        String sql = "UPDATE books SET stock = stock + 1 WHERE isbn = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            int rows = pstmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("归还图书失败：" + e.getMessage());
            return false;
        }
    }

    /**
     * 获取所有图书列表
     * @return List<Book> 图书列表
     */
    public List<Book> searchBooks(String keyword) {
        List<Book> result = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "%" + keyword + "%");
            pstmt.setString(2, "%" + keyword + "%");
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Book book = new Book(
                    rs.getString("isbn"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("stock")
                );
                book.incrementBorrowCount(rs.getInt("borrow_count")); // 需确保Book类有此方法
                result.add(book);
            }
        } catch (SQLException e) {
            System.out.println("查询图书失败：" + e.getMessage());
        }
        return result;
    }

    /**
     * 根据ISBN精确查询图书
     * @param isbn 要查询的图书ISBN
     * @return Book 找到的图书对象，未找到返回null
     */
    public Book getMostBorrowedBook() {
        String sql = "SELECT * FROM books ORDER BY borrow_count DESC LIMIT 1";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                Book book = new Book(
                    rs.getString("isbn"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("stock")
                );
                book.incrementBorrowCount(rs.getInt("borrow_count"));
                return book;
            }
        } catch (SQLException e) {
            System.out.println("统计最热图书失败：" + e.getMessage());
        }
        return null;
    }

    // 辅助方法：根据ISBN查找图书（从数据库查询）
    private Book findBookByIsbn(String isbn) {
        String sql = "SELECT * FROM books WHERE isbn = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, isbn);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Book book = new Book(
                    rs.getString("isbn"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getInt("stock")
                );
                book.incrementBorrowCount(rs.getInt("borrow_count"));
                return book;
            }
        } catch (SQLException e) {
            System.out.println("查找图书失败：" + e.getMessage());
        }
        return null;
    }

    // 建议添加：关闭数据库连接方法（在应用退出时调用）
    public void closeConnection() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("数据库连接已关闭");
            } catch (SQLException e) {
                System.out.println("关闭数据库连接失败：" + e.getMessage());
            }
        }
    }
}
