import java.io.Serializable;
import java.util.Objects;

/**
 * 图书实体类，实现序列化接口用于文件存储和网络传输
 * 包含图书基本信息：ISBN、书名、作者、库存和借阅次数统计
 */
class Book implements Serializable {
    private static final long serialVersionUID = 1L;
    private String isbn;          // ISBN（唯一标识）
    private String title;         // 书名
    private String author;        // 作者
    private int stock;            // 库存数量
    private int borrowCount;      // 借阅次数（用于统计）

    /**
     * 图书构造函数
     * @param isbn 国际标准书号（唯一标识）
     * @param title 书名
     * @param author 作者
     * @param stock 初始库存数量
     */
    public Book(String isbn, String title, String author, int stock) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.stock = stock;
        this.borrowCount = 0;
    }

    // =============== Getter和Setter方法 ===============
    public String getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public int getBorrowCount() { return borrowCount; }
    // 新增方法：直接设置借阅次数（用于从数据库恢复数据）
    public void incrementBorrowCount(int count) {
        this.borrowCount = count;
    }
    public void incrementBorrowCount() { this.borrowCount++; }

    // 重写equals和hashCode用于集合去重
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(isbn, book.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    @Override
    public String toString() {
        return "ISBN: " + isbn +
                ", 书名: " + title +
                ", 作者: " + author +
                ", 库存: " + stock +
                ", 借阅次数: " + borrowCount;
    }
}