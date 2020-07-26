package model;

import java.util.*;

// Manage items and categories
public class BudgetManager {
    public HashMap<String, List<Item>> budgetList = new HashMap<>();


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
    public void addItem(String itemName, double itemAmount, String category) {
        Item i = new Item(itemName, itemAmount); //creates new item
        List<Item> itemsOfCategory = budgetList.get(category); //gets empty list of specified category
        itemsOfCategory.add(i);
    }

    public void removeCategory(String name) {
        for (String i : budgetList.keySet()) {
            if (name.equals(i)) {
                budgetList.remove(i);
            }
        }
    }

    public double getPriceOfItem(Item i) {
        return i.getItemAmount();
    }

    public void getItem() {
        System.out.println(Arrays.asList(budgetList));
    }



}




