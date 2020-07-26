package model;

public class Account {
    private double account;
    private double budget;

    // EFFECTS: Creates a chequing account
    public Account(double account, double percent) {
        this.account = account;
        budget = percent * account;
    }

    public double getBudget() {
        return budget;
    }
}
