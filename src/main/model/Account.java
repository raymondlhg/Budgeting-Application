package model;

// Represents an account with an account balance and a budget
public class Account {
    private double account;
    private double budget;

    // EFFECTS: Creates a chequing account
    public Account(double account, double budget) {
        this.account = account;
        this.budget = budget;
    }

    public double getBudget() {
        return budget;
    }

    public double getAccount() {
        return account;
    }

    public void setBudget(Double budget) {
        this.budget = budget;
    }
}
