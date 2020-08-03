package persistence;

import model.Account;
import model.BudgetManager;
import model.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class WriterTest {
    private static final String TEST_FILE = "./data/testBudgetFile2.txt";
    private Writer testWriter;
    private BudgetManager budgetManager;

    @BeforeEach
    void runBefore() throws FileNotFoundException, UnsupportedEncodingException {
        testWriter = new Writer(new File(TEST_FILE));
        budgetManager = new BudgetManager();
        budgetManager.setAccountAndBudget(10000.0, 810.0);
        budgetManager.addItem("dog", 100.0, "pet");
        budgetManager.addItem("movie", 10.0, "entertainment");

    }

    @Test
    void testWriteBudget() {
        testWriter.write(budgetManager);
        testWriter.close();

        try {
            BudgetManager budgetManager = Reader.readBudgetContents(new File(TEST_FILE));
            assertEquals(10000.0, budgetManager.getAccount().getAccount());
            assertEquals(700.0, budgetManager.getAccount().getBudget());

            Set<String> testCategories = new HashSet<String>();
            testCategories.add("pet");
            testCategories.add("entertainment");
            assertEquals(testCategories, budgetManager.budgetList.keySet());

            ArrayList<Item> testItems = new ArrayList<Item>();
            Item item1 = new Item("dog", 100.0);
            testItems.add(item1);

            assertEquals(testItems.get(0).getItemName(), budgetManager.budgetList.get("pet").get(0).getItemName());
            assertEquals(testItems.get(0).getItemAmount(), budgetManager.budgetList.get("pet").get(0).getItemAmount());

            ArrayList<Item> testItems2 = new ArrayList<Item>();
            Item item2 = new Item("movie", 10.0);
            testItems.add(item2);

            assertEquals(testItems.get(0).getItemName()
                    , budgetManager.budgetList.get("entertainment").get(0).getItemName());
            assertEquals(testItems.get(0).getItemAmount()
                    , budgetManager.budgetList.get("entertainment").get(0).getItemAmount());

        } catch (IOException e) {
            fail("IOException should not have been thrown");
        }

    }

}
