/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.VmItem;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;

/**
 *
 * @author blake
 */
public class VendingMachineView {

    private UserIO io;
    private final String MACHINE_NAME = "Candy Paradise";

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void displayVendingMachineBanner() {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 153; i++) {
            sb.append("=");
        }
        io.print(sb.toString());
        System.out.printf("%-68s%15s%70s\n", "||", MACHINE_NAME, "||");
        io.print(sb.toString());
    }

    public void displayVendingMachineContents(List<VmItem> itemList) {
        String outputFormat = "%-3s%-30s%-5.2f";
        String outputFormatNewLine = "%-3s%-30s%-5.2f%3s";
        String formatter;
        for (int i = 1; i <= itemList.size(); i++) {
            
            if (/*i != 0 && */i % 4 == 0) {
                formatter = outputFormatNewLine;

                if (itemList.get(i - 1).getNumInInventory() == 0) {
                    System.out.printf(formatter,
                            "| ",
                            "",
                            0.0,
                            "|"
                            + "\n---------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                } else {
                    System.out.printf(formatter,
                            "| ",
                            itemList.get(i - 1).getName(),
                            itemList.get(i - 1).getCost().doubleValue(),
                            "|"
                            + "\n---------------------------------------------------------------------------------------------------------------------------------------------------------\n");
                }
            } else {
                formatter = outputFormat;

                if (itemList.get(i - 1).getNumInInventory() == 0) {
                    System.out.printf(formatter,
                            "| ",
                            "",
                            0.0);
                } else {

                    System.out.printf(formatter,
                            "| ",
                            itemList.get(i - 1).getName(),
                            itemList.get(i - 1).getCost().doubleValue());
                }
            }
        }
        io.print("\n---------------------------------------------------------------------------------------------------------------------------------------------------------\n");
    }

    public String promptUserForTransactionChoice(String prompt) {
        return io.readString(prompt);
    }

    public void displayExitBanner() {
        io.print("Good Bye");
    }

    public String displayInvalidContinueChoiceBanner() {
        return io.readString("Invalid selection. Please enter 'yes' to continue or 'no' to exit");
    }

    public double promptUserForPayment() {
        return io.readDouble("Please insert cash amount", .05, 5.00);
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public String displayCandySelectionPrompt() {
        return io.readString("Please enter your candy selection");
    }

    public void displayInvalidItemSelectionBanner() {
        io.print("Invalid item selected.");
        io.print("Please make sure the name entered matches a candy from the list above or enter 'exit' to quit");
    }

    public void displayNoInventoryMessage() {
        io.print("Product is out of stock");
    }

    public void displayInsufficientFundsMessage(BigDecimal funds) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        io.print("Insufficient funds. The current balance avaiable is "
                + formatter.format(funds));
    }

    public void displayRefundBanner() {
        io.print("====  Issuing Refund =====");
    }

    public void displayRefund(Change userChange) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        io.print(userChange.getNumQuarters() + " Quarters");
        io.print(userChange.getNumDimes() + " Dimes");
        io.print(userChange.getNumNickels() + " Nickels");
        io.print(userChange.getNumPennies() + " Pennies");
        io.print("------------------");
        io.print(formatter.format(userChange.getTotal()) + " Total Refund");
    }

    public void displayDispenseItemBanner(VmItem item) {
        io.print("Enjoy your " + item.getName() + "!");
    }

    public void displayUserChangeBanner() {
        io.print("====  Dispensing Change ====");
    }

}
