package persistence;

import model.Account;
import model.BudgetManager;
import model.Item;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

// SKELETON TAKE FROM TELLER APP AND MODIFIED
// A reader that can read budget data from a file
public class Reader {
    public static final String DELIMITER = ",";

    // EFFECTS:
    //IOException if an exception is raised when opening/reading from file
    public static BudgetManager readBudgetContents(File file) throws IOException {
        List<String> fileContent = readFile(file);
        return parseContent(fileContent);
    }


    // EFFECTS: returns content of file as a list of strings, each string
    // containing the content of one row of the file
    private static List<String> readFile(File file) throws IOException {
        return Files.readAllLines(file.toPath());
    }

    // EFFECTS: returns BudgetManager parsed from a list of strings
    // where each string contains data of either account/budget, category or item
    private static BudgetManager parseContent(List<String> fileContent) {
        String line1 = fileContent.get(0);
        ArrayList<String> lineComponents1 = splitString(line1);
        Account account1 = parseAccount(lineComponents1);

        HashMap<String, List<Item>> budgetList = new HashMap<>();
        for (int i = 1; i < fileContent.size(); i++) {
            String line = fileContent.get(i);
            List<Item> itemList = parseCategory(splitString(line));
            budgetList.put(splitString(line).get(0), itemList);
        }
        BudgetManager budgetManager = new BudgetManager(account1, budgetList);
        return budgetManager;
    }

    // EFFECTS: returns a list of strings obtained by splitting line on DELIMITER
    private static ArrayList<String> splitString(String line) {
        String[] splits = line.split(DELIMITER);
        return new ArrayList<>(Arrays.asList(splits));
    }


    // REQUIRES: components has size 2 where element 0 represents
    // the account balance, elements 1 represents the budget
    // EFFECTS: returns an account constructed from components
    private static Account parseAccount(List<String> components) {
        double account = Double.parseDouble(components.get(0));
        double budget = Double.parseDouble(components.get(1));
        // System.out.println(components.get(1));
        return new Account(account, budget);
    }

    // REQUIRES: components has size i, containing category, item name and item amount;
    // category may have an item list of items
    // EFFECTS: returns an itemList constructed from components
    private static List<Item> parseCategory(List<String> components) {
        ArrayList<Item> itemList = new ArrayList<Item>();

        for (int i = 1; i < components.size(); i++) {
            String itemName = components.get(i);
            double itemAmount = Double.parseDouble(components.get(++i)); //increments then reads
            Item item = new Item(itemName, itemAmount);
            itemList.add(item);
        }
        return itemList;
    }
}

