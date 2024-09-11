import java.util.*;
import java.lang.*;

class Customer {
    int id;
    String name;
    String address;
    String contactNo;

    public Customer(int id, String name, String address, String contactNo) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.contactNo = contactNo;
    }
}

class Account {
    int id;
    int customerId;
    String type;
    double balance;

    public Account(int id, int customerId, String type, double balance) {
        this.id = id;
        this.customerId = customerId;
        this.type = type;
        this.balance = balance;
    }
}

class Transaction {
    static int counter = 1;
    int id;
    int accountId;
    String type;
    double amount;
    String timestamp;

    public Transaction(int accountId, String type, double amount) {
        this.id = counter++;
        this.accountId = accountId;
        this.type = type;
        this.amount = amount;
        this.timestamp = new Date().toString();
    }
}

class Beneficiary {
    int customerId;
    int id;
    String name;
    String accountNo;
    String bankDetails;

    public Beneficiary(int customerId, int id, String name, String accountNo, String bankDetails) {
        this.customerId = customerId;
        this.id = id;
        this.name = name;
        this.accountNo = accountNo;
        this.bankDetails = bankDetails;
    }
}

public class BankingSystem {
    private static final Map<Integer, Customer> customers = new HashMap<>();
    private static final Map<Integer, Account> accounts = new HashMap<>();
    private static final Map<Integer, List<Transaction>> transactions = new HashMap<>();
    private static final Map<Integer, List<Beneficiary>> beneficiaries = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            System.out.println("Banking System");
            System.out.println("1. Add Customers");
            System.out.println("2. Add Accounts");
            System.out.println("3. Add Beneficiary");
            System.out.println("4. Add Transaction");
            System.out.println("5. Find Customer by Id");
            System.out.println("6. List all Accounts of specific Customer");
            System.out.println("7. List all transactions of specific Account");
            System.out.println("8. List all beneficiaries of specific customer");
            System.out.println("9. Exit");
            System.out.print("Enter your choice : ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    addAccount();
                    break;
                case 3:
                    addBeneficiary();
                    break;
                case 4:
                    addTransaction();
                    break;
                case 5:
                    findCustomerById();
                    break;
                case 6:
                    listAccountsByCustomer();
                    break;
                case 7:
                    listTransactionsByAccount();
                    break;
                case 8:
                    listBeneficiariesByCustomer();
                    break;
                case 9:
                    System.out.println("Thank you!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addCustomer() {
        System.out.println("Enter Customer Details");
        System.out.print("Customer Id : ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Name : ");
        String name = scanner.nextLine();
        System.out.print("Address : ");
        String address = scanner.nextLine();
        System.out.print("Contact No. : ");
        String contactNo = scanner.nextLine();
        customers.put(id, new Customer(id, name, address, contactNo));
    }

    private static void addAccount() {
        System.out.println("Enter Account Details");
        System.out.print("Account Id : ");
        int id = scanner.nextInt();
        System.out.print("Customer Id : ");
        int customerId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Account Type Saving/Current : ");
        String type = scanner.nextLine();
        System.out.print("Balance : ");
        double balance = scanner.nextDouble();
        accounts.put(id, new Account(id, customerId, type, balance));
        transactions.put(id, new ArrayList<>());
    }

    private static void addBeneficiary() {
        System.out.println("Enter Beneficiary Details");
        System.out.print("Customer Id : ");
        int customerId = scanner.nextInt();
        System.out.print("Beneficiary Id : ");
        int id = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Beneficiary Name : ");
        String name = scanner.nextLine();
        System.out.print("Beneficiary Account No. : ");
        String accountNo = scanner.nextLine();
        System.out.print("Beneficiary Bank details : ");
        String bankDetails = scanner.nextLine();
        beneficiaries.computeIfAbsent(customerId, k -> new ArrayList<>())
                .add(new Beneficiary(customerId, id, name, accountNo, bankDetails));
    }

    private static void addTransaction() {
        System.out.println("Enter Transaction Details");
        System.out.print("Account Id : ");
        int accountId = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.print("Type (Deposit/Withdrawal) : ");
        String type = scanner.nextLine();
        System.out.print("Amount : ");
        double amount = scanner.nextDouble();
        transactions.get(accountId).add(new Transaction(accountId, type, amount));
        Account account = accounts.get(accountId);
        if (account != null) {
            account.balance += type.equals("Deposit") ? amount : -amount;
        }
    }

    private static void findCustomerById() {
        System.out.println("Customer ID: " + customers.keySet());
        System.out.print("Customer Id : ");
        int id = scanner.nextInt();
        Customer customer = customers.get(id);
        if (customer != null) {
            System.out.println("Customer: " + customer.name);
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void listAccountsByCustomer() {
        System.out.print("Customer Id : ");
        int customerId = scanner.nextInt();
        System.out.println("Accounts for Customer ID :" + customerId);
        for (Account account : accounts.values()) {
            if (account.customerId == customerId) {
                System.out.println("Account ID: " + account.id + ", Balance: " + account.balance);
            }
        }
    }

    private static void listTransactionsByAccount() {
        System.out.print("Account Id : ");
        int accountId = scanner.nextInt();
        System.out.println("Transactions for Account ID :" + accountId);
        List<Transaction> transList = transactions.get(accountId);
        if (transList != null) {
            for (Transaction t : transList) {
                System.out.println("Transaction ID: " + t.id + ", Type: " + t.type + ", Amount: " + t.amount + ", Timestamp: " + t.timestamp);
            }
        } else {
            System.out.println("No transactions found.");
        }
    }

    private static void listBeneficiariesByCustomer() {
        System.out.print("Customer Id : ");
        int customerId = scanner.nextInt();
        System.out.println("Beneficiaries for Customer ID :" + customerId);
        List<Beneficiary> benList = beneficiaries.get(customerId);
        if (benList != null) {
            for (Beneficiary b : benList) {
                System.out.println("Beneficiary ID: " + b.id + ", Name: " + b.name);
            }
        } else {
            System.out.println("No beneficiaries found.");
}
}
}