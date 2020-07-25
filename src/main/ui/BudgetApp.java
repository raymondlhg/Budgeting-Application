package ui;

import model.BudgetManager;
import model.Item;

import java.util.Scanner;

// Budgeting application
public class BudgetApp {
    BudgetManager budgetManager = new BudgetManager();
    Scanner scanner = new Scanner(System.in);
    String userRequest;


    public BudgetApp() {
        runApp();
    }

    public void runApp() {
        System.out.println("Welcome to the Budget Manager");
        do {
            System.out.println("Please select an option: 1-add category, 2-add item, 3-View Budget, 4-Exit");
            userRequest = scanner.nextLine();
            enterUserRequest(userRequest);
        } while (!userRequest.equals("4"));
    }

    public void enterUserRequest(String userRequest) {
        switch (userRequest) {
            case "1":
                addCategory();
                break;
            case "2":
                addItem();
                break;
            case "3":
                displayManager();
                break;
            case "4":
                System.out.println("Exiting system");
                return;
            default:
                System.out.println("Invalid input");
        }
    }

    public void addCategory() {
        System.out.println("add new category");
        String name = scanner.nextLine();
        budgetManager.addCategory(name);
    }

    public void addItem() {
        System.out.println("add new item");
        String item = scanner.nextLine();
        System.out.println("enter item price");
        Double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("enter item category");
        String category = scanner.nextLine();
        if (!budgetManager.budgetList.containsKey(category)) {
            budgetManager.addCategory(category);
        }
        budgetManager.addItem(item, price, category);
        System.out.println("Item added");
    }

    public void displayManager() {
        for (String i : budgetManager.budgetList.keySet()) {
            System.out.println("Category: " + i);
            for (Item j : budgetManager.budgetList.get(i)) {
                System.out.println("\t Item: " + j.getItemName() + " " + "$" + j.getItemAmount());
            }
        }
    }

}

//System.out.println(Arrays.asList(budgetManager))

//        if (userRequest.equals("1")) {
//                addCategory();
//                } else if (userRequest.equals("2")) {
//                addItem();
//                } else if (userRequest.equals("3")) {
//                displayManager();
//                }