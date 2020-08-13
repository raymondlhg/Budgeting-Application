# CPSC210 Personal Project

## Budgeting Application

###### Raymond Hung, Viper - y8i2b


This application will help users properly manage their money based on their monthly income (**budget**). 
The intended users are *university students*, young adults or anyone looking to better manage their money to 
avoid going broke. This project is of particular interest to me as I find myself constantly thinking things I need to
pay off, such as bills, groceries, rent, hobby items, etc. Having this application consolidate all those items into
once place will help me better visualize my spending and reallocate how I spend if necessary.

#### User Stories

- As a user, I want to be able to input my own monthly income (budget)
- As a user, I want to be able to create my own spending categories and add items
- As a user, I want to be able to view my spending categories and amount spent in each
- As a user, I want to be able to remove spending category(s)

##### Phase 2 User Stories

- As a user, I want to be able to save my account and budget, spending categories and items to file
- As a user, I want to be able to load my saved file of the above when the program starts

##### Phase 3 Instructions for Grader

- Two required events related to user story of "multiple Xs to Y": Type in item name, amount and category and click
- "add item" button to add the item to the budget manager
- Click the "display budget" tab to display all the current items
- In the display panel, select a row (which is an item) and click the "delete" button to delete the item
- NOTE: the delete button is the second component to the two required events
- To trigger the audiovisual component, each of the four buttons creates their own unique sound
- Click the "save" button to save the current list of items to the budget manager
- Click the "load" button to load the state of the application from file

##### Phase 4 - Task 2
- I have chosen to use integrate the Map interface in my BudgetManager class
- Most of the methods in BudgetManager interact with this HashMap, either adding, removing or modifying it
- The BudgetManager class has a HashMap (named budgetList) "HashMap<String, List<Item>> budgetList = new HashMap<>();"
- The String represents a spending category as the Key, and the List<Item> is a list of items as the values

##### Phase 4 - Task 3

Problem 1 (Coupling)

- In the first phase of my project, I had a separate class named "Category"
- After completing my design, I decided to redesign my project to remove this class as it created unnecessary coupling
- The "Category" class was dependent on BudgetManager, which created many problems
- One problem I had when running the program and taking the user's input was that the system could not convert
- the user's string input to a category, which complicated things
- Thus, I refactored to remove the "Category" class and put it as the key in my HashMap

Problem 2 (Cohesion)

- In the first phase of my project, when I was designing my BudgetManager class, it originally included
- a field "account" which was responsible for being an account and calculating a budget (% of the account)
- I realized that my BudgetManager class was too messy (now discovering it had poor cohesion), thus I refactored
- to create a separate class "Account", which handled the process of setting the account balance, budget, etc.

