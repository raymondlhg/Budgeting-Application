package model;

import java.util.*;

import model.Account;

// Manage items and categories
public class BudgetManager {
    public HashMap<String, List<Item>> budgetList = new HashMap<>();
    Account account1;


    // REQUIRES: Category does not already exist
    // MODIFIES: budgetManager
    // EFFECTS: add a new category to budgetManager
    public void addCategory(String name) {
        //Category c = new Category(name);
        ArrayList<Item> itemList = new ArrayList<Item>();
        budgetList.put(name, itemList);
    }

    // MODIFIES:
    // EFFECTS:
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

    public void removeCategory(String name) {
        for (String c : budgetList.keySet()) {
            if (name.equals(c)) {
                budgetList.remove(c);
                return; //ends the method after removing category
            }
        }
    }

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
}




