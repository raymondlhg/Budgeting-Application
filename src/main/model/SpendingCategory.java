package model;

// Represents a SpendingCategory having a name and an amount (in dollars)
public class SpendingCategory {
    private String name;
    private double totalAmount;

    // EFFECTS: creates a new spending category, with name and total amount spent
    public SpendingCategory(String name) {
        this.name = name;
        this.totalAmount = totalAmount;

    }

    public double getTotalAmount() {
        return totalAmount;
    }


}
