package model;

// Represents the budget as the amount (in dollars) to be allocated
public class Budget {
    private double budget;

    // EFFECTS: Creates a budget to be allocated
    public Budget(double budget) {
        this.budget = budget;
    }

    public double getBudget() {
        return budget;
    }

}
