package bank;

import javax.security.auth.login.LoginException;

public class Main {
  public static void main(String[] args) {
    try {
      Menu menu = new Menu();
      menu.run();
    } catch (LoginException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      System.out.println("Something went wrong.");
    }
  }
}
