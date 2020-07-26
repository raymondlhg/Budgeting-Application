package model;

public class Account {
    private double account;
    private double budget;

    // EFFECTS: Creates a chequing account
    public Account(double account, double percent) {
        this.account = account;
        this.budget = percent * account;
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
