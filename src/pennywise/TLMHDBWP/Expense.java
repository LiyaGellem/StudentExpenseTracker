/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pennywise.TLMHDBWP;

/**
 *
 * @author liyal
 */
import java.io.File;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Expense {
    private double monthlyAllowance;
    private ArrayList<String> descriptions;
    private ArrayList<Double> amounts;
    private ArrayList<String> dates;
    
    // Singleton instance
    private static Expense instance;

    public Expense() {
        this.descriptions = new ArrayList<>();
        this.amounts = new ArrayList<>();
        this.dates = new ArrayList<>();
    }
    
    // Public method to get the instance
    public static Expense getInstance() {
        if (instance == null) {
            instance = new Expense();
        }
        return instance;
    }

    public void setMonthlyAllowance(double monthlyAllowance) {
        this.monthlyAllowance = monthlyAllowance;
    }
    
    public double getMonthlyAllowance() {
        return this.monthlyAllowance;
    }
    
    public boolean hasAllowance() {
        return this.monthlyAllowance > 0;}

     public boolean addExpense(String description, double amount, String date) {
        if (amount < 0) {
            return false;
        }
        double currentAllowance = calculateRemainingAllowance();
        if (currentAllowance - amount < 0) {
            return false;
        }
        descriptions.add(description);
        amounts.add(amount);
        dates.add(date);
        return true;
    }
/**
     * Edit an existing expense
     * @param index The index of the expense to edit
     * @param description The new description
     * @param amount The new amount
     * @param date The new date
     * @return True if the expense was successfully edited, false otherwise
     */
     
      public boolean editExpense(int index, String description, double amount, String date) {
        // Check if index is valid
        if (index < 0 || index >= descriptions.size()) {
            return false;
        }
        
        // Check if amount is valid
        if (amount < 0) {
            return false;
        }
        
        // Get current amount
        double currentAmount = amounts.get(index);
        
        // Calculate the change in amount
        double amountDifference = amount - currentAmount;
        
        // Check if we have enough allowance for the new amount
        double remainingAllowance = calculateRemainingAllowance();
        if (remainingAllowance - amountDifference < 0) {
            return false;
        }
        
        // Update the expense
        descriptions.set(index, description);
        amounts.set(index, amount);
        dates.set(index, date);
        
        return true;
    }
    public int getExpenseCount() {
        return descriptions.size();
    }
        
   public double calculateTotalExpenses() {
        double total = 0;
        for (int i = 0; i < amounts.size(); i++) {
            total += amounts.get(i);
        }
        return total;
    }

   public double calculateRemainingAllowance() {
        return this.monthlyAllowance - calculateTotalExpenses();
    }
   
    public String getDescription(int index) {
        if (index >= 0 && index < descriptions.size()) {
            return descriptions.get(index);
        }
        return "";
    }
    

   public double getAmount(int index) {
        if (index >= 0 && index < amounts.size()) {
            return amounts.get(index);
        }
        return 0.0;
    }

   public String getDate(int index) {
        if (index >= 0 && index < dates.size()) {
            return dates.get(index);
        }
        return "";
    }
   public ArrayList<String> getDescriptions() {
        return descriptions;
    }
    public ArrayList<Double> getAmounts() {
        return amounts;
    }
    public ArrayList<String> getDates() {
        return dates;
    }

    @Override
    public String toString() {
        String result = "";
        for (int i = 0; i < descriptions.size(); i++) {
            result += "Description: " + descriptions.get(i) +
                      ", Amount: R" + amounts.get(i) +
                      ", Date: " + dates.get(i) + "\n";
        }
        return result;
    }

   
public void saveExpensesToFile(String filePath) throws IOException {
    try (FileWriter writer = new FileWriter(filePath)) {
        writer.write("Monthly Allowance: R" + monthlyAllowance + "\n");
        writer.write("Total Expenses: R" + calculateTotalExpenses() + "\n");
        writer.write("Remaining Allowance: R" + calculateRemainingAllowance() + "\n\n");
        writer.write("Expenses:\n");
        for (int i = 0; i < descriptions.size(); i++) {
            writer.write("Description: " + descriptions.get(i) +
                         ", Amount: R" + amounts.get(i) +
                         ", Date: " + dates.get(i) + "\n");
        }
    }
}


    // Method to load expenses from a text file
    public void loadExpensesFromFile(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length == 3) {
                descriptions.add(parts[0]);
                amounts.add(Double.parseDouble(parts[1]));
                dates.add(parts[2]);
            }
        }
    }
public void appendStudentToFile(String filePath, String name, String surname, 
                              String financialSupport, String date, String advice) throws IOException {
    try (FileWriter writer = new FileWriter(filePath, true)) { // true = append mode
        // Format: "Name#Surname#FinancialSupport#Allowance#Date#Groceries#Transport#SelfCare#Other"
        StringBuilder sb = new StringBuilder();
        
        // Student info
        sb.append(name).append("#");
        sb.append(surname).append("#");
        sb.append(financialSupport).append("#");
        sb.append(monthlyAllowance).append("#");
        sb.append(date).append("#");
        
        // Find expenses by category
        double groceries = 0, transport = 0, selfCare = 0, other = 0;
        for (int i = 0; i < descriptions.size(); i++) {
            String desc = descriptions.get(i);
            double amount = amounts.get(i);
            
            if (desc.equalsIgnoreCase("Groceries")) {
                groceries = amount;
            } else if (desc.equalsIgnoreCase("Transport")) {
                transport = amount;
            } else if (desc.equalsIgnoreCase("Self-care")) {
                selfCare = amount;
            } else if (desc.equalsIgnoreCase("Other")) {
                other = amount;
            }
        }
        
        // Add expenses
        sb.append(groceries).append("#");
        sb.append(transport).append("#");
        sb.append(selfCare).append("#");
        sb.append(other);
        
        // Write the line to the file
        writer.write(sb.toString() + "\n");
    }
}
     void clearAllData() {
        descriptions.clear();
        amounts.clear();
        dates.clear();
        monthlyAllowance = 0;
    }

    boolean editExpense(int expenseIndex, String description, double newAmount) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
}
