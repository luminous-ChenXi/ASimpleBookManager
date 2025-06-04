import java.util.Scanner;
import java.util.List;        // 用于 List 类型
import java.util.stream.Collectors; // 用于 Collectors.toList()
/**
 * 图书管理系统主入口类
 * 提供控制台交互界面和菜单导航功能
 */
public class Main {
    /**
     * 程序主入口
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        BookManager manager = new BookManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== 图书管理系统 ===");
            System.out.println("1. 添加图书");
            System.out.println("2. 借阅图书");
            System.out.println("3. 归还图书");
            System.out.println("4. 查询图书");
            System.out.println("5. 统计最热图书");
            System.out.println("6. 退出系统");
            System.out.print("请选择操作：");

            int choice = scanner.nextInt();
            scanner.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    System.out.print("请输入ISBN：");
                    String isbn = scanner.nextLine();
                    System.out.print("请输入书名：");
                    String title = scanner.nextLine();
                    System.out.print("请输入作者：");
                    String author = scanner.nextLine();
                    System.out.print("请输入库存：");
                    int stock = scanner.nextInt();
                    manager.addBook(new Book(isbn, title, author, stock));
                    break;

                case 2:
                    System.out.print("请输入要借阅的图书ISBN：");
                    String borrowIsbn = scanner.nextLine();
                    if (manager.borrowBook(borrowIsbn)) {
                        System.out.println("借阅成功！");
                    } else {
                        System.out.println("借阅失败：图书不存在或库存不足！");
                    }
                    break;

                case 3:
                    System.out.print("请输入要归还的图书ISBN：");
                    String returnIsbn = scanner.nextLine();
                    if (manager.returnBook(returnIsbn)) {
                        System.out.println("归还成功！");
                    } else {
                        System.out.println("归还失败：图书不存在或操作异常！");
                    }
                    break;

                case 4:
                    System.out.print("请输入查询关键词：");
                    String keyword = scanner.nextLine();
                    List<Book> results = manager.searchBooks(keyword);
                    if (results.isEmpty()) {
                        System.out.println("未找到相关图书！");
                    } else {
                        results.forEach(System.out::println);
                    }
                    break;

                case 5:
                    Book mostBorrowed = manager.getMostBorrowedBook();
                    if (mostBorrowed != null) {
                        System.out.println("借阅次数最多的图书：");
                        System.out.println(mostBorrowed);
                    } else {
                        System.out.println("暂无借阅数据！");
                    }
                    break;

                case 6:
                    System.out.println("系统退出，数据已自动保存！");
                    scanner.close();
                    return;

                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }
}