package com.team7.java_2022b;

import javafx.scene.control.Alert;
import javafx.stage.Modality;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/*
 * 需求功能
 * 1. 連接資料庫
 * 2. 初始化資料庫
 *   2.1 使用者資料表：id, roll, name, email, password, status, allowed_borrow, remained, fine_Rate
 *   2.2 書籍資料表：id, name, author, publisher, price, status
 *   2.3 借閱資料表：tx, user_id, book_id, borrow_date, return_date
 * 3. 資料庫功能
 *   3.1 查找使用者
 *       3.1.1 以id查找, 回傳roll, status, allowed_borrow, remained, fine_Rate
 *       3.1.2 以username, password查找, 回傳roll, status, allowed_borrow, remained, fine_Rate
 *   3.2 查找書籍
 *       3.2.1 以書籍名稱查找:回傳id, name, author, publisher, price, status
 *       3.2.2 以書籍id查找:回傳id, name, author, publisher, price, status
 *  3.3 查找借閱資料
 *      3.3.1 以使用者id查找:回傳tx, user_id, book_id, borrow_date, return_date
 *      3.3.2 以書籍id查找:回傳tx, user_id, book_id, borrow_date, return_date
 *
 */
public class SQLMGR {
    // Book has: id(auto), name, author, publisher, price, status
    // User has: id(auto), roll, name, email, password, status, allowed_borrow,
    // remained, fine_Rate
    // History has: tx(auto), user_id, book_id, borrow_date, return_date
    private static final String url = "jdbc:mysql://<your_database_url>";
    private static final String sqlname = "<your_database_username>";
    private static final String sqlpassword = "<your_database_password>";
    // 插入項目 sql
    private static final String AdminLogOn = "Select * from USER where roll = 0 and name = ? and password = ?";
    private static final String UserLogOn = "Select * from USER where name = ? and password = ?";
    private static final String InsertBook = "INSERT INTO BOOK(name, author, publisher, price, status) VALUES (?,?,?,?,?);";
    private static final String InsertUser = "INSERT INTO USER(roll,name, email, password, status, allowed_borrow, remained, fine_Rate) VALUES (?,?,?,?,1,?,?,?);";
    private static final String InsertHistory = "INSERT INTO HISTORY(user_id, book_id, borrow_date) VALUES (?,?,now());";
    // 更新資料sql
    private static final String UpdateBookStatusToUnavailable = "UPDATE BOOK SET status=0 WHERE id=?;";
    private static final String UpdateBookStatusToAvailable = "UPDATE BOOK SET status=1 WHERE id=?;";
    private static final String writeTodayDateToHistory = "UPDATE HISTORY SET return_date=now() WHERE tx=?;";
    private static final String UpdateBook = "UPDATE BOOK SET name=?, author=?, publisher=?, price=?, status=? WHERE id=?;";
    private static final String deleteBook = "UPDATE BOOK SET name='DELETED', author='DELETED', publisher='DELETED', price=0, status=0 WHERE id=?;";
    // 查詢項目 sql
    private static final String FindLastHistoryOfBookID = "select * from HISTORY where book_id=? ORDER BY tx DESC Limit 1;";
    private static final String FindUserByID = "SELECT * FROM USER WHERE id=?;";
    private static final String FindUserByName = "SELECT * FROM USER WHERE name=?;";
    private static final String getBookNameByID = "SELECT name FROM BOOK WHERE id=?;";
    private static final String getHistoryofUserID = "SELECT * FROM HISTORY WHERE user_id=?;";
    private static final String FindLastIDOfUser = "select id from USER ORDER BY id DESC LIMIT 1;";
    private static final String FindUserFine = "SELECT fine_Rate FROM USER WHERE id=?;";
    private static final String resetPW = "UPDATE USER SET password = 'p455w07d' WHERE id =?;";
    private static final String UpdateUser = "UPDATE USER SET name=?, roll=?, status=?, allowed_borrow=?, remained=?, fine_Rate=? WHERE id=?;";
    private static final String DisaUser = "UPDATE USER SET status=0 WHERE id=?;";
    private static final String getUser = "SELECT * FROM USER WHERE id=?;";
    private static final String getAllUser = "SELECT * FROM USER;";
    private static final String getAllBook = "SELECT * FROM BOOK;";
    private static final String getAllHistory = "SELECT * FROM HISTORY;";

    // add new user

    private static Connection c = null;
    private static Alert alert = null;
    private PreparedStatement pstmt = null;
    private Statement stmt = null;
    private ResultSet rs = null;
    private String sql = "";

    public SQLMGR() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setHeaderText(null);
        alert.initModality(Modality.APPLICATION_MODAL);
    }

    public final static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection(url, sqlname, sqlpassword);
            return c;
        } catch (Exception e) {
            alert.setContentText(e.toString());
            alert.showAndWait();
            // createUsername();
        }
        return null;
    }

    // 連接到資料庫，並將c 設值，此段程式碼必須置於所有其餘sql操作前執行，故將其至於建構子
    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("url");
            System.out.println("SQL connect successfully");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("SQL connect failed");
            throw new RuntimeException(e);
        }
    }

    public void close() {
        try {
            c.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 執行sql語句
    public void exec(String sql) throws SQLException {
        c = DriverManager.getConnection(url);
        stmt = c.createStatement();
        stmt.executeUpdate(sql);
        close();
    }

    public boolean adminLogin(String username, String password) throws SQLException {
        c = getConnection();
        try {
            pstmt = c.prepareStatement(AdminLogOn);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // c.close();
                c.close();
                return true;
            } else {
                c.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int userLogin(String username, String password) throws SQLException {
        c = getConnection();
        int user_id = 0;
        try {
            pstmt = c.prepareStatement(UserLogOn);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // c.close();
                user_id = rs.getInt("id");
                c.close();
                return user_id;
            } else {
                c.close();
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    // 資料庫的初始化，會建立三個資料表：user, book, history以及其schema
    // 註意執行前須確保三資料表不存在，否則會跳出錯誤，估將此部分放置於reset後
    public void init() {
        c = getConnection();
        try {
            // create table USER
            sql = "create table USER(" +
                    "id integer primary key auto_increment," +
                    "roll int not null," +
                    "name text not null," +
                    "email text," +
                    "password text not null," +
                    "status int not null," +
                    "allowed_borrow int not null," +
                    "remained int not null," +
                    "fine_Rate real not null" +
                    ");";
            stmt = c.createStatement();
            stmt.execute(sql);
            // create table BOOK;
            sql = "create table BOOK(" +
                    "id integer primary key auto_increment," +
                    "name text not null," +
                    "author text," +
                    "publisher text," +
                    "price int not null," +
                    "status int not null);";
            stmt.execute(sql);
            // create table HISTORY
            sql = "create table HISTORY(" +
                    "tx integer primary key auto_increment," +
                    "user_id int not null," +
                    "book_id int not null," +
                    "borrow_date text not null," +
                    "return_date text);";
            stmt.execute(sql);
            // create user: admin
            sql = "insert into USER(roll, name, email, password, status, allowed_borrow, remained, fine_Rate) VALUES (0, 'admin', '','admin',1,0,0,0)";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // find history
    public void findHistoryByBookID(int book_id) throws SQLException {
        c = getConnection();
        try {
            pstmt = c.prepareStatement(FindLastHistoryOfBookID);
            pstmt.setInt(1, book_id);
            this.rs = pstmt.executeQuery();
            c.close();
        } catch (SQLException e) {
            c.close();
            e.printStackTrace();
        }
        c.close();
    }

    public String getBookName(int bookid) throws SQLException {
        c = getConnection();
        String bookname = "";
        try {
            pstmt = c.prepareStatement(getBookNameByID);
            pstmt.setInt(1, bookid);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // c.close();
                bookname = rs.getString("name");
                c.close();
                return bookname;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        c.close();
        return bookname;
    }

    public int getBorrowerData(int book_id) throws SQLException {
        c = getConnection();
        // find history data
        int user_id = 0;
        try {
            pstmt = c.prepareStatement(FindLastHistoryOfBookID);
            pstmt.setInt(1, book_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user_id = rs.getInt("user_id");
            }
            c.close();
        } catch (SQLException e) {
            c.close();
            e.printStackTrace();
        }
        c.close();
        return user_id;
    }

    public String getBorrowDate(int book_id) throws SQLException {
        c = getConnection();
        // find history data

        String borrow_date = "";
        try {
            pstmt = c.prepareStatement(FindLastHistoryOfBookID);
            pstmt.setInt(1, book_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                borrow_date = rs.getString("borrow_date");
            }
            c.close();
        } catch (SQLException e) {
            c.close();
            e.printStackTrace();
        }
        c.close();
        return borrow_date;
    }

    public ResultSet findUserById(int id) {
        // sql = "select * from USER where id = " + id + ";";
        try {
            pstmt = c.prepareStatement(FindUserByID);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            System.out.println(rs.getInt("id"));
            // ResultSet rs = stmt.executeQuery(sql);
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean canUserBorrow(int id) throws SQLException {
        c = getConnection();
        try {
            pstmt = c.prepareStatement(FindUserByID);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            int remained;
            if (rs.next()) {
                remained = rs.getInt("remained");
                if (remained > 0) {
                    c.close();
                    return true;
                }

            } else {
                c.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public boolean checkUserExist(String username) throws SQLException {
        c = getConnection();
        try {
            pstmt = c.prepareStatement(FindUserByName);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;

            } else {
                c.close();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    public int calculateFine(int user_id, int days) throws SQLException {
        c = getConnection();
        int rate = 0;
        int fine = 0;
        int role = 1;
        try {
            pstmt = c.prepareStatement(FindUserByID);
            pstmt.setInt(1, user_id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                rate = rs.getInt("fine_Rate");
                role = rs.getInt("roll");

            } else {
                c.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(role);
        if (role == 1) {
            fine = 10;
        } else if (role == 2) {
            fine = rate * (days - 7);
        } else if (role == 3) {
            fine = rate * (days - 7);
        } else {
            fine = rate * (days - 7) * 2;
        }
        return fine;
    }

    public boolean isbookAvailable(int book_id) throws SQLException {
        c = getConnection();
        sql = "select status from BOOK where id = " + book_id + ";";
        try {
            System.out.println(sql);
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            rs.next();
            if (rs.getInt("status") == 1) {
                c.close();
                return true;
            }
        } catch (SQLException e) {
            // e.printStackTrace();
            System.out.println("book not found in db");
        }
        c.close();
        return false;
    }

    public void writeHistory(int USERID, int BOOKID) throws SQLException {
        c = getConnection();
        try {
            pstmt = c.prepareStatement(InsertHistory);
            pstmt.setInt(1, USERID);
            pstmt.setInt(2, BOOKID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // System.out.println("insert history: " + row);
        c.close();
    }

    public void updateHistory(int BOOKID) throws SQLException {
        c = getConnection();
        int tx = 0;
        try {
            pstmt = c.prepareStatement(FindLastHistoryOfBookID);
            pstmt.setInt(1, BOOKID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                tx = rs.getInt("tx");
            }
            // c.close();
        } catch (SQLException e) {
            // c.close();
            e.printStackTrace();
        }

        // c = DriverManager.getConnection("jdbc:sqlite:db.sqlite");
        try {
            pstmt = c.prepareStatement(writeTodayDateToHistory);
            pstmt.setInt(1, tx);
            pstmt.executeUpdate();
            c.close();
        } catch (SQLException e) {
            c.close();
            e.printStackTrace();
        }
        // System.out.println("insert history: " + row);
        c.close();
    }

    public void incUserRemained(int userid) throws SQLException {
        c = getConnection();
        sql = "select remained from USER WHERE id=" + userid + ";";
        // sql = "SELECT tx FROM HISTORY;";
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int remained = rs.getInt("remained") + 1;
        // sql = "insert into HISTORY values(, " + tx + " , " + USERID + "," + BOOKID +
        // ",datetime('now'),null);";
        sql = "update USER set remained = " + remained + " where id = " + userid + ";";
        try {
            System.out.println(sql);
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            c.close();
        } catch (SQLException e) {
            c.close();
            e.printStackTrace();
        }
        c.close();
    }

    public void decrUserRemained(int userid) throws SQLException {
        c = getConnection();
        sql = "select remained from USER WHERE id=" + userid + ";";
        // sql = "SELECT tx FROM HISTORY;";
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int remained = rs.getInt("remained") - 1;
        // sql = "insert into HISTORY values(, " + tx + " , " + USERID + "," + BOOKID +
        // ",datetime('now'),null);";
        sql = "update USER set remained = " + remained + " where id = " + userid + ";";
        try {
            System.out.println(sql);
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            c.close();
        } catch (SQLException e) {
            c.close();
            e.printStackTrace();
        }
        c.close();
    }

    // set book status to 0
    public void updateBookStatusToUnavailable(int bookid) throws SQLException {
        c = getConnection();
        pstmt = c.prepareStatement(UpdateBookStatusToUnavailable);
        pstmt.setInt(1, bookid);
        pstmt.executeUpdate();
        c.close();
    }

    // an inverse function of updateBookStatusToUnavailable
    // set book status to 1
    public void updateBookStatusToAvailable(int bookid) throws SQLException {
        c = getConnection();
        pstmt = c.prepareStatement(UpdateBookStatusToAvailable);
        pstmt.setInt(1, bookid);
        pstmt.executeUpdate();
        c.close();
    }

    // search book by text
    public ArrayList searchBookByText(String text) throws SQLException {
        c = getConnection();
        sql = "select * from BOOK where name like '%" + text + "%' or author like '%" + text + "%' or publisher like '%"
                + text + "%';";
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        ArrayList<Book> books = new ArrayList<Book>();
        while (rs.next()) {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            System.out.println(book.getId());
            book.setName(rs.getString("name"));
            System.out.println(book.getName());
            book.setAuthor(rs.getString("author"));
            book.setPublisher(rs.getString("publisher"));
            book.setStatus(rs.getInt("status"));
            // book.setImgPath(rs.getString("img_path"));
            books.add(book);
        }
        c.close();
        return books;
    }

    public void addBook(String bookname, String author, String publisher, int bookprice, int bookstatus)
            throws SQLException {
        c = getConnection();
        pstmt = c.prepareStatement(InsertBook);
        pstmt.setString(1, bookname);
        pstmt.setString(2, author);
        pstmt.setString(3, publisher);
        pstmt.setInt(4, bookprice);
        pstmt.setInt(5, bookstatus);
        pstmt.executeUpdate();
        c.close();
    }

    public void editBook(int bookid, String bookname, String author, String publisher, int bookprice, int bookstatus)
            throws SQLException {
        c = getConnection();
        pstmt = c.prepareStatement(UpdateBook);
        pstmt.setString(1, bookname);
        pstmt.setString(2, author);
        pstmt.setString(3, publisher);
        pstmt.setInt(4, bookprice);
        pstmt.setInt(5, bookstatus);
        pstmt.setInt(6, bookid);
        pstmt.executeUpdate();
        c.close();
    }

    public void deleteBook(int bookid) throws SQLException {
        c = getConnection();
        pstmt = c.prepareStatement(deleteBook);
        pstmt.setInt(1, bookid);
        pstmt.executeUpdate();
        c.close();
    }

    public int findLastUserID() throws SQLException {
        c = getConnection();
        sql = "select id from USER ORDER BY id DESC Limit 1;";
        stmt = c.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        rs.next();
        int id = rs.getInt("id");
        c.close();
        System.out.println("last user id: " + id);
        return id;
    }

    public int register(String username, String password, String Email, int group) throws SQLException {
        try {
            c = getConnection();
            pstmt = c.prepareStatement(InsertUser);

            pstmt.setString(2, username);
            pstmt.setString(3, Email);
            pstmt.setString(4, password);
            switch (group) {
                case 1: // 1:student
                    pstmt.setInt(1, 1);// role
                    pstmt.setInt(5, 15);// allowed
                    pstmt.setInt(6, 15);// remained
                    pstmt.setDouble(7, 0.5);// fine
                    break;
                case 2: // 2:teacher
                    pstmt.setInt(1, 2);// role
                    pstmt.setInt(5, 15);// allowed
                    pstmt.setInt(6, 15);// remained
                    pstmt.setInt(7, 1);// fine
                    break;
                case 3: // 3:staff
                    pstmt.setInt(1, 3);// role
                    pstmt.setInt(5, 10);// allowed
                    pstmt.setInt(6, 10);// remained
                    pstmt.setInt(7, 1);// fine
                    break;
                case 4: // other
                    pstmt.setInt(1, 4);// role
                    pstmt.setInt(5, 5);// allowed
                    pstmt.setInt(6, 5);// remained
                    pstmt.setInt(7, 1);// fine
                    break;
            }
            pstmt.executeUpdate();
        } catch (SQLException e) {
            c.close();
            e.printStackTrace();
        }
        // get last row id
        int newid = 0;
        try {
            c = getConnection();
            pstmt = c.prepareStatement(FindLastIDOfUser);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                newid = rs.getInt("id");
            }

        } catch (SQLException e) {
            c.close();
            e.printStackTrace();
        }
        c.close();
        System.out.println("register success");
        return newid;
    }

    // Set User Role
    public void setUserRole(int userId, int role, int operator) throws SQLException {
        String log = "Set User" + userId + " Role to " + role + " by " + operator;
        System.out.println(log);
        sql = "UPDATE user SET status = " + role + " WHERE id = " + userId + ";";
        exec(sql);
    }

    // reset User Password
    public void resetPassword(int userId) throws SQLException {
        c = getConnection();
        try {
            pstmt = c.prepareStatement(resetPW);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editUser(int userId, String username, int role, int status, int userallowed, int userremained,
            double finerate) throws SQLException {
        c = getConnection();
        try {
            pstmt = c.prepareStatement(UpdateUser);
            pstmt.setString(1, username);
            pstmt.setInt(2, role);
            pstmt.setInt(3, status);
            pstmt.setInt(4, userallowed);
            pstmt.setInt(5, userremained);
            pstmt.setDouble(6, finerate);
            pstmt.setInt(7, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // delete User
    public void disableUser(int userId) throws SQLException {
        c = getConnection();
        try {
            pstmt = c.prepareStatement(DisaUser);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int Login(String Name, String Password) {
        sql = "SELECT id FROM user WHERE name = '" + Name + "' AND password = '" + Password + "'";
        try {
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                return rs.getInt("id");
            } else {
                System.out.println("Login failed");
                return -1;
            }
        } catch (SQLException e) {
            System.out.println("SQL login failed");
            throw new RuntimeException(e);
        }
    }

    // database reset
    public void reset(Boolean backup) throws SQLException {
        if (backup) {
        } else {
            // drop user table;
            sql = "drop table user;";
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            // drop book table;
            sql = "drop table book;";
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            // drop history table;
            sql = "drop table history;";
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            init();
        }
    }

    public List getAllUsers() throws SQLException {
        c = getConnection();
        List users = new ArrayList();
        try {
            pstmt = c.prepareStatement(getAllUser);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                // modified
                user.setRole(rs.getInt("roll"));
                user.setAllowed(rs.getInt("allowed_borrow"));
                user.setRemained(rs.getInt("remained"));
                user.setFine(rs.getInt("fine_Rate"));
                user.setStatus(rs.getInt("status"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User getUserInfo(int userid) throws SQLException {
        c = getConnection();
        User user = new User();
        try {
            pstmt = c.prepareStatement(getUser);
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            if (rs.next()) {
                user.setId(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getInt("roll"));
                user.setAllowed(rs.getInt("allowed_borrow"));
                user.setRemained(rs.getInt("remained"));
                user.setFine(rs.getInt("fine_Rate"));
                user.setStatus(rs.getInt("status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List getAllBooks() throws SQLException {
        c = getConnection();
        List books = new ArrayList();
        try {
            pstmt = c.prepareStatement(getAllBook);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setPrice(rs.getInt("price"));
                book.setStatus(rs.getInt("status"));

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List getAllHistory() throws SQLException {
        c = getConnection();
        List history = new ArrayList();
        try {
            pstmt = c.prepareStatement(getAllHistory);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                History his = new History();
                his.setTx(rs.getInt("tx"));
                his.setUserid(rs.getInt("user_id"));
                his.setBookid(rs.getInt("book_id"));
                his.setBorrow_date(rs.getString("borrow_date"));
                his.setReturn_date(rs.getString("return_date"));
                history.add(his);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }

    public List getHistoryofUser(int userid) throws SQLException {
        c = getConnection();
        List history = new ArrayList();
        try {
            pstmt = c.prepareStatement(getHistoryofUserID);
            pstmt.setInt(1, userid);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                History his = new History();
                his.setTx(rs.getInt("tx"));
                his.setUserid(rs.getInt("user_id"));
                his.setBookid(rs.getInt("book_id"));
                his.setBorrow_date(rs.getString("borrow_date"));
                his.setReturn_date(rs.getString("return_date"));
                history.add(his);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }

    public List searchBooks(String text, boolean name, boolean author, boolean pub) throws SQLException {
        c = getConnection();
        List books = new ArrayList();
        try {
            sql = "SELECT * FROM BOOK WHERE";
            if (name) {
                sql += " name LIKE '%" + text + "%'";
            }
            if (author) {
                if (name) {
                    sql += " OR";
                }
                sql += " author LIKE '%" + text + "%'";
            }
            if (pub) {
                if (name || author) {
                    sql += " OR";
                }
                sql += " publisher LIKE '%" + text + "%'";
            }
            sql += ";";
            stmt = c.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setName(rs.getString("name"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setPrice(rs.getInt("price"));
                book.setStatus(rs.getInt("status"));

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

}
