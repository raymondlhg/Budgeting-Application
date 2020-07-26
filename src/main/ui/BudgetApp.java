package ui;

import model.Account;
import model.BudgetManager;
import model.Item;

import java.util.Scanner;

// Budgeting application
public class BudgetApp {
    BudgetManager budgetManager = new BudgetManager();
    Scanner scanner = new Scanner(System.in);
    String userRequest;

    //EFFECTS: runs the budgeting application
    public BudgetApp() {
        runApp();
    }

    // EFFECTS: displays menu of options and processes user input
    public void runApp() {
        System.out.println("Welcome to the Budget Manager");
        do {
            System.out.println("Please select an option: 1-set account and "
                    + "budget 2-add category, 3-add item, 4-remove category, 5-View Budget, 6-Exit");
            userRequest = scanner.nextLine();
            enterUserRequest(userRequest);
        } while (!userRequest.equals("6"));
    }


    // EFFECTS: processes user input
    public void enterUserRequest(String userRequest) {
        switch (userRequest) {
            case "1":
                setAccountAndBudget();
                break;
            case "2":
                addCategory();
                break;
            case "3":
                addItem();
                break;
            case "4":
                removeCategory();
                break;
            case "5":
                displayManager();
                break;
            case "6":
                System.out.println("Exiting system");
                return;
            default:
                System.out.println("Invalid input");
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a category
    public void addCategory() {
        System.out.println("add new category");
        String name = scanner.nextLine();
        budgetManager.addCategory(name);
    }

    // MODIFIES: this
    // EFFECTS: adds a item
    public void addItem() {
        System.out.println("add new item");
        String item = scanner.nextLine();
        System.out.println("enter item price");
        Double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("enter item category");
        String category = scanner.nextLine();
        if (budgetManager.addItem(item, price, category)) {
            System.out.println("Item added");
            System.out.format("New budget balance: $%.2f%n", budgetManager.getAccount().getBudget());
        } else {
            System.out.println("Item not added: out of budget");
        }
    }

    // EFFECTS: displays current budget balance, categories and items with their costs and total spent
    public void displayManager() {
        System.out.format("Current budget balance: $%.2f%n", budgetManager.getAccount().getBudget());
        for (String i : budgetManager.budgetList.keySet()) {
            double totalSpent = 0;
            System.out.println("Category: " + i);
            for (Item j : budgetManager.budgetList.get(i)) {
                System.out.format("\t Item: %s $%.2f%n", j.getItemName(), j.getItemAmount());
                totalSpent += j.getItemAmount();
            }
            System.out.format("\t Total spent: $%.2f%n", totalSpent);
        }
    }

    // MODIFIES: this
    // EFFECTS: sets the account and budget balance
    public void setAccountAndBudget() {
        System.out.println("Please enter your chequing account balance");
        Double acc = scanner.nextDouble();
        System.out.println("Please enter the percentage of your account as the monthly budget");
        System.out.println("i.e. 0.1 is 10%");
        Double bgt = scanner.nextDouble();
        scanner.nextLine();
        budgetManager.setAccountAndBudget(acc, bgt);
    }

    // MODIFIES: this
    // EFFECTS: removes a category
    public void removeCategory() {
        System.out.println("Please enter the category you wish to remove");
        String name = scanner.nextLine();
        budgetManager.removeCategory(name);
    }

}