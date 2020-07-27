package model;

import model.BudgetManager;
import model.Item;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class BudgetManagerTest {
    BudgetManager budgetManager;


    @BeforeEach
    public void setup() {
        budgetManager = new BudgetManager();

    }

    @Test
    public void testAddCategory() {
        budgetManager.addCategory("food");
        assertEquals("food", budgetManager.getCategory());

    }

    @Test
    public void testAddItemSuccess() {
        budgetManager.setAccountAndBudget(10000.0, 0.1);
        assertEquals(1000, budgetManager.getAccount().getBudget());
        budgetManager.addItem("ramen", 10, "food");
        assertEquals(990, budgetManager.getAccount().getBudget());
        assertEquals("food", budgetManager.getCategory());
        assertTrue(budgetManager.addItem("ramen", 10, "food"));
    }

    @Test
    public void testAddItemFail() {
        budgetManager.setAccountAndBudget(5000.0, 0.1);
        assertEquals(500, budgetManager.getAccount().getBudget());

        assertTrue(budgetManager.addItem("ramen",500, "food"));
        assertFalse(budgetManager.addItem("popsicle",1, "food"));
    }

    @Test
    public void testAddItemCategoryAlreadyExists() {
        budgetManager.setAccountAndBudget(10000.0, 0.1);
        assertEquals(1000, budgetManager.getAccount().getBudget());
        budgetManager.addCategory("food");
        budgetManager.addItem("ramen", 10, "food");
        assertEquals(990, budgetManager.getAccount().getBudget());
        assertEquals("food", budgetManager.getCategory());
        assertTrue(budgetManager.addItem("ramen", 10, "food"));
    }

    @Test
    public void testRemoveCategory() {
        Set<String> testCategories = new HashSet<String>();
        testCategories.add("entertainment");
        testCategories.add("travel");
        budgetManager.addCategory("food");
        budgetManager.addCategory("travel");
        budgetManager.addCategory("entertainment");

        budgetManager.removeCategory("food");

        assertEquals(testCategories, budgetManager.budgetList.keySet());
        //assertEquals(["entertainment, travel"], budgetManager.budgetList.keySet());

    }

    @Test
    public void testSetAccountAndBudget() {
        budgetManager.setAccountAndBudget(9000.0, 0.1);
        assertEquals(900, budgetManager.getAccount().getBudget());
    }

    @Test
    public void testGetItemName() {
        budgetManager.setAccountAndBudget(9000.0, 0.1);
        Item i = new Item("ramen", 10);
        i.getItemName();
        i.getItemAmount();
    }

    @Test
    public void testGetItemAmount() {
        budgetManager.setAccountAndBudget(9000.0, 0.1);
        Item i = new Item("ramen", 10);
        i.getItemAmount();
    }

    @Test
    public void testGetCategoryFail() {
        assertEquals(null, budgetManager.getCategory());
    }

}

