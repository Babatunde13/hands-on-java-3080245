package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

public class Menu {
  private Scanner scanner;
  private int menuOption = 1;

  public Menu() {
    this.scanner = new Scanner(System.in);
  }

  private void closeScanner() {
    this.scanner.close();
  }

  private void ensureAuthenticated (Customer customer) throws LoginException {
    if (!customer.getAuthenticated()) {
      throw new LoginException("You must be logged in to do that.");
    }
  }
  private void viewBalance(Customer customer) {
    Account account = DataSource.getAccount(customer.getAccountId());
    System.out.println("Your balance is: $" + account.getBalance());
  }

  private void withdrawFunds(Customer customer) {
    System.out.print("How much would you like to withdraw? ");
    Double amount = scanner.nextDouble();
    Account account = DataSource.getAccount(customer.getAccountId());
    try {
      account.withdraw(amount);
    } catch (AmountException e) {
      System.out.println(e.getMessage());
      System.out.println("Please try again.");
    }
    System.out.println("Your new balance is: $" + account.getBalance());
  }

  private void depositFunds(Customer customer) {
    System.out.print("How much would you like to deposit? ");
    Double amount = scanner.nextDouble();
    Account account = DataSource.getAccount(customer.getAccountId());
    try {
      account.deposit(amount);
    } catch (AmountException e) {
      System.out.println(e.getMessage());
      System.out.println("Please try again.");
    }
    System.out.println("Your new balance is: $" + account.getBalance());
  }

  private String[] getCredentials() {
    System.out.print("Please enter your username: ");
    String username = scanner.nextLine();
    System.out.print("Please enter your password: ");
    String password = scanner.nextLine();
    return new String[] { username, password };
  }

  private void showMenu() {
    System.out.println("==========================");
    System.out.println("Please select an option:");
    System.out.println("1. View Account Balance");
    System.out.println("2. Withdraw Funds");
    System.out.println("3. Deposit Funds");
    System.out.println("4. Logout");
    System.out.println("==========================");
    System.out.print("Your selection: ");

    int option = scanner.nextInt();
    this.menuOption = option;
  }

  private void handleMenuOption(Customer customer) {
    switch (this.menuOption) {
      case 1:
        this.viewBalance(customer);
        break;
      case 2:
        this.withdrawFunds(customer);
        break;
      case 3:
        this.depositFunds(customer);
        break;
      case 4:
        Authenticator.logout(customer);
        System.out.println("Log out successful.");
        break;
      default:
        System.out.println("Invalid option. Please try again.");
        break;
    }
  }

  public void run() throws LoginException {
    String[] credentials = getCredentials();
    String username = credentials[0];
    String password = credentials[1];
    Customer customer = null;
    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println(e.getMessage());
      return;
    }

    ensureAuthenticated(customer);
    while (customer.getAuthenticated()) {
      this.showMenu();
      this.handleMenuOption(customer);
      System.out.println("");
    }

    this.closeScanner();
  }
}
