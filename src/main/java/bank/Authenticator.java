package bank;

import javax.security.auth.login.LoginException;

public class Authenticator {
  public static Customer login(String username, String password)  throws LoginException{
    Customer customer = DataSource.getCustomer(username);
    if (customer == null) {
      throw new LoginException("Invalid credentials");
    }

    if (!customer.getPassword().equals(password)) {
      throw new LoginException("Invalid credentials");
    }

    customer.setAuthenticated(true);
    return customer;
  }

  public static void logout(Customer customer) {
    customer.setAuthenticated(false);
  }
}
