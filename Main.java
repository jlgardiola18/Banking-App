import java.util.*;

class BankUser {
    private int userId;
    private String name;
    private int pin;
    private double balance;

    public BankUser(int userId, String name, int pin, double balance) {
        this.userId = userId;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean validatePin(int inputPin) {
        return this.pin == inputPin;
    }
}

public class Main {

    private static BankUser[] users = {
        new BankUser(412435, "Chris Sandoval", 1234, 32000),
        new BankUser(264863, "Marc Yim", 5678, 1000)
    };

    private static BankUser loggedInUser = null;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        boolean loggedIn = false;
        while (!loggedIn) {
            System.out.println("Welcome to the Banking App!");
            System.out.print("Enter User ID: ");
            int userId = readInt(scanner);

            System.out.print("Enter PIN: ");
            int pin = readInt(scanner);

            for (BankUser user : users) {
                if (user.getUserId() == userId && user.validatePin(pin)) {
                    loggedInUser = user;
                    loggedIn = true;
                    System.out.println("Login successful! Welcome, " + user.getName());
                    break;
                }
            }

            if (!loggedIn) {
                System.out.println("Invalid User ID or PIN. Please try again.");
            }
        }

        boolean appRunning = true;
        while (appRunning) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Cash In");
            System.out.println("3. Money Transfer");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = readInt(scanner);

            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    cashIn(scanner);
                    break;
                case 3:
                    moneyTransfer(scanner);
                    break;
                case 4:
                    appRunning = false;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        scanner.close();
    }

    private static void checkBalance() {
        System.out.println("Your current balance is: $" + loggedInUser.getBalance());
    }

    private static void cashIn(Scanner scanner) {
        System.out.print("Enter amount to deposit: $");
        double amount = readDouble(scanner);
        if (amount > 0) {
            double newBalance = loggedInUser.getBalance() + amount;
            loggedInUser.setBalance(newBalance);
            System.out.println("Deposit successful! Your new balance is: $" + newBalance);
        } else {
            System.out.println("Invalid amount. Please enter a positive value.");
        }
    }

    private static void moneyTransfer(Scanner scanner) {
        System.out.print("Enter recipient's User ID: ");
        int recipientId = readInt(scanner);

        System.out.print("Enter amount to transfer: $");
        double amount = readDouble(scanner);

        if (amount <= 0) {
            System.out.println("Invalid amount. Please enter a positive value.");
            return;
        }

        BankUser recipient = null;
        for (BankUser user : users) {
            if (user.getUserId() == recipientId) {
                recipient = user;
                break;
            }
        }

        if (recipient != null && loggedInUser.getBalance() >= amount) {
            loggedInUser.setBalance(loggedInUser.getBalance() - amount);
            recipient.setBalance(recipient.getBalance() + amount);
            System.out.println("Transfer successful! You transferred $" + amount + " to " + recipient.getName());
        } else if (recipient == null) {
            System.out.println("Recipient with the given User ID not found.");
        } else {
            System.out.println("Insufficient balance for transfer.");
        }
    }

    private static int readInt(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double readDouble(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.next();
        }
        return scanner.nextDouble();
    }
    
}
