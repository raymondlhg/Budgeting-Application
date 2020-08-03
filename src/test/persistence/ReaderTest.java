package persistence;

import model.BudgetManager;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class ReaderTest {


    @Test
    void testParseBudgetFile1() {
        try {
            BudgetManager budgetManager = Reader.readBudgetContents(new File("./data/testBudgetFile1.txt"));
            assertEquals(10000.0, budgetManager.getAccount().getAccount());
            assertEquals(810.0, budgetManager.getAccount().getBudget());

            Set<String> testCategories = new HashSet<String>();
            testCategories.add("travel");
            testCategories.add("food");
            testCategories.add("pet");
            testCategories.add("entertainment");
            assertEquals(testCategories, budgetManager.budgetList.keySet());

            ArrayList<Item> testItems = new ArrayList<Item>();
            Item item1 = new Item("plane ticket", 90.0);
            testItems.add(item1);

            assertEquals(testItems.get(0).getItemName(), budgetManager.budgetList.get("travel").get(0).getItemName());
            assertEquals(testItems.get(0).getItemAmount(), budgetManager.budgetList.get("travel").get(0).getItemAmount());


        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }
    }

    @Test
    void testIOException() {
        try {
            Reader.readBudgetContents(new File("./path/does/not/exist/testBudget.txt"));
        } catch (IOException e) {
            // expected
        }
    }
}
