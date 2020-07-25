package model;

// Represents a specific item with a name and its cost (in dollars)
public class Item {
    private String itemName;
    private double itemAmount;

    // EFFECTS: creates an item with a name and its amount (in dollars)
    public Item(String itemName, double itemAmount) {
        this.itemName = itemName;
        this.itemAmount = itemAmount;
    }

    public String getItemName() {
        return itemName;
    }

    public double getItemAmount() {
        return itemAmount;
    }

}
