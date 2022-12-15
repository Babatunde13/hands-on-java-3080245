package bank;

public class Account {
  private int id;
  private AccountType type;
  private double balance;

  public Account(int id, AccountType type, double balance) {
    setId(id);
    setType(type);
    setBalance(balance);
  }

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public AccountType getType() {
    return this.type;
  }

  public void setType(AccountType type) {
    this.type = type;
  }

  public double getBalance() {
    return this.balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

  public void deposit(double amount) throws AmountException {
    if (amount < 0) {
      throw new AmountException("Amount must be positive");
    }
    setBalance(getBalance() + amount);
    DataSource.updateAccount(this);
  }

  public void withdraw(double amount) throws AmountException {
    if (amount < 0) {
      throw new AmountException("Amount must be positive");
    }
    if (amount > getBalance()) {
      throw new AmountException("Insufficient funds");
    }
    setBalance(getBalance() - amount);
    DataSource.updateAccount(this);
  }


  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", type='" + getType() + "'" +
      ", balance='" + getBalance() + "'" +
      "}";
  }

}
