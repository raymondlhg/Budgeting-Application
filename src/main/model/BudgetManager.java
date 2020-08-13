package model;

import java.io.*;
import java.util.*;

import model.Account;
import persistence.*;
import persistence.Reader;

// Manage items and categories
public class BudgetManager implements Saveable {
    public HashMap<String, List<Item>> budgetList = new HashMap<>();
    Account account1;


    public BudgetManager(Account account1, HashMap<String, List<Item>> budgetList) {
        this.budgetList = budgetList;
        this.account1 = account1;
    }

    public BudgetManager() {

    }

    // REQUIRES: Category does not already exist
    // MODIFIES: budgetManager
    // EFFECTS: add a new category to budgetManager
    public void addCategory(String name) {
        //Category c = new Category(name);
        ArrayList<Item> itemList = new ArrayList<Item>();
        budgetList.put(name, itemList);
    }

    // MODIFIES: account1, this
    // EFFECTS: return true if itemAmount < budget and add category if not existing, return false otherwise
    public boolean addItem(String itemName, double itemAmount, String category) {
        if (itemAmount <= account1.getBudget()) {
            account1.setBudget(account1.getBudget() - itemAmount);
            Item i = new Item(itemName, itemAmount); //creates new item
            if (!budgetList.containsKey(category)) {
                this.addCategory(category);
            }
            //List<Item> itemsOfCategory = budgetList.get(category); //gets empty list of specified category
            //itemsOfCategory.add(i);
            budgetList.get(category).add(i);
            return true;
        }
        return false;
    }


    // MODIFIES: account1, this
    // EFFECTS: removes item from category if item exists in that category and refunds amount of item to budget
    // if the category contains no items, remove the category
    public void removeItemGUI(String category, String itemName, double itemAmount) {
        for (String c : budgetList.keySet()) {
            if (category.equals(c)) {
                for (Item i : budgetList.get(c)) {
                    if (i.getItemName().equals(itemName)) {
                        account1.setBudget(account1.getBudget() + itemAmount);
                        budgetList.get(c).remove(i);
                        if (budgetList.get(c).isEmpty()) {
                            removeCategory(category);
                        }
                        return;
                    }
                }
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: remove a category
    public void removeCategory(String name) {
        for (String c : budgetList.keySet()) {
            if (name.equals(c)) {
                budgetList.remove(c);
                return; //ends the method after removing category
            }
        }
    }

    // REQUIRES: account and budget have not yet been set
    // MODIFIES: this
    // EFFECTS: sets the balance of account and budget
    public void setAccountAndBudget(Double acc, Double bgt) {
        account1 = new Account(acc, bgt);
    }

    public Account getAccount() {
        return account1;
    }

    public String getCategory() {
        for (String i : budgetList.keySet()) {
            return i;
        }
        return null;
    }


    @Override
    public void save(PrintWriter printWriter) {
        printWriter.print(account1.getAccount());
        printWriter.print(Reader.DELIMITER);
        printWriter.print(account1.getBudget());
        printWriter.println();
        for (String i : budgetList.keySet()) {
            printWriter.print(i);
            printWriter.print(Reader.DELIMITER);
            for (Item j : budgetList.get(i)) {
                printWriter.print(j.getItemName());
                printWriter.print(Reader.DELIMITER);
                printWriter.print(j.getItemAmount());
                printWriter.print(Reader.DELIMITER);
            }
            printWriter.println();
        }
    }
}




