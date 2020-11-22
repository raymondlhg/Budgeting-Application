package ui;

import model.Account;
import model.BudgetManager;
import model.Item;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import persistence.Reader;
import persistence.Writer;

// Budgeting application
public class BudgetApp {
    BudgetManager budgetManager = new BudgetManager();
    Scanner scanner = new Scanner(System.in);
    String userRequest;
    private static final String BUDGET_FILE = "./data/budget.txt";

    //EFFECTS: runs the budgeting application
    public BudgetApp() {
        runApp();
    }

    // EFFECTS: displays menu of options and processes user input
    public void runApp() {
        System.out.println("Welcome to the Budget Manager, please press 7 to load previous data");
        do {
            System.out.println("Please select an option: 1-set account and "
                    + "budget 2-add category, 3-add item, 4-remove category, 5-View Budget, 6-Save,"
                    + " 7-Load, 8-Exit");
            userRequest = scanner.nextLine();
            enterUserRequestDirectory(userRequest);
        } while (!userRequest.equals("8"));
    }


    // EFFECTS: processes user input and redirects
    public void enterUserRequestDirectory(String userRequest) {
        if (Integer.parseInt(userRequest) <= 4) {
            enterUserRequest1(userRequest);
        } else {
            enterUserRequest2(userRequest);
        }
    }

    // EFFECTS: processes user input
    public void enterUserRequest1(String userRequest) {
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
            default:
                System.out.println("Invalid input");
        }
    }

    // EFFECTS: processes user input
    public void enterUserRequest2(String userRequest) {
        switch (userRequest) {
            case "5":
                displayManager();
                break;
            case "6":
                saveBudget();
                break;
            case "7":
                loadBudget();
                break;
            case "8":
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
        System.out.println(name + " category added!");
        // SUGGESTION: show categories that already exist, create index of standard categories
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
        Double pct = scanner.nextDouble();
        scanner.nextLine();
        budgetManager.setAccountAndBudget(acc, acc * pct);
        System.out.println("You have set Account: $" + acc + ", Budget: $" + acc * pct);
    }

    // MODIFIES: this
    // EFFECTS: removes a category
    public void removeCategory() {
        System.out.println("Please enter the category you wish to remove");
        String name = scanner.nextLine();
        budgetManager.removeCategory(name);
        // SUGGESTION: show existing categories
    }

    // MODIFIES: BUDGET_FILE
    // EFFECTS: saves the state of the application to file (account, budgetManager)
    public void saveBudget() {
        try {
            Writer writer = new Writer(new File(BUDGET_FILE));
            writer.write(budgetManager);
            writer.close();
            System.out.println("Accounts saved to file " + BUDGET_FILE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to save accounts to " + BUDGET_FILE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            // this is due to a programming error
        }
    }

    // MODIFIES: this
    // EFFECTS: loads account and budgetManager from BUDGET_FILE
    public void loadBudget() {
        try {
            budgetManager = Reader.readBudgetContents(new File(BUDGET_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

// testing
