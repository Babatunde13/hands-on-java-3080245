package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  public static Connection connect() {
    Connection conn = null;
    try {
      String db_file = "jdbc:sqlite:resources/bank.db";
      conn = DriverManager.getConnection(db_file);
      System.out.println("Connection to SQLite has been established.");
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  
    return conn;
  }

  public static void close(Connection conn) {
    try {
      if (conn != null) {
        conn.close();
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }

  public static void test() {
    Connection conn = connect();
    close(conn);
  }

  public static Customer getCustomer(String username) {
   String sql = "SELECT * FROM customers WHERE username = ?";
    Customer customer = null;
    try (Connection conn = connect();
         PreparedStatement statement = conn.prepareStatement(sql)
    ) {
      statement.setString(1, username);
      ResultSet rs = statement.executeQuery();
      if (rs.next()) {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        int accountId = rs.getInt("account_id");
        String passwordHash = rs.getString("password");
        customer = new Customer(id, name, username, passwordHash, accountId);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return customer;
  }

  public static Account getAccount(int id) {
    String sql = "SELECT * FROM accounts WHERE id = ?";
    Account account = null;
    try (Connection conn = connect();
        PreparedStatement statement = conn.prepareStatement(sql)) {
      statement.setInt(1, id);
      ResultSet rs = statement.executeQuery();
      if (rs.next()) {
        AccountType type = AccountType.valueOf(rs.getString("type"));

        double balance = rs.getDouble("balance");
        account = new Account(id, type, balance);
      }
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }

    return account;
  }

  public static void updateAccount(Account account) {
    String sql = "UPDATE accounts SET balance = ? WHERE id = ?";
    try (Connection conn = connect();
         PreparedStatement statement = conn.prepareStatement(sql)
    ) {
      statement.setDouble(1, account.getBalance());
      statement.setInt(2, account.getId());
      statement.executeUpdate();
    } catch (SQLException e) {
      System.out.println(e.getMessage());
    }
  }
}
