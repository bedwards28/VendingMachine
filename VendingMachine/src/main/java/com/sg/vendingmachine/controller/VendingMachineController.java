/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Change;
import com.sg.vendingmachine.dto.VmItem;
import com.sg.vendingmachine.service.InsufficientFundsException;
import com.sg.vendingmachine.service.NoItemInventoryException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 *
 * @author blake
 */
public class VendingMachineController {

    VendingMachineView view;
    VendingMachineServiceLayer service;

    public void run() {
        boolean keepGoing = true;
        Change userChange;

        try {
            view.displayVendingMachineBanner();
            displayVendingMachineContents();

            keepGoing = getUserContinueChoice(
                    "Would you like to purhcase any candy? Enter (Y)es or (N)o");

            if (!keepGoing) {
                view.displayExitBanner();
                System.exit(0);
            }

            BigDecimal cashInserted = getFunds();
            userChange = new Change(cashInserted);

            while (keepGoing) {

                VmItem candySelection = getCandySelection();
                if (candySelection == null) {
                    issueRefund(userChange);
                    break;
                }
                
                try {
                    candySelection = service.checkForItemInventory(candySelection);
                } catch (NoItemInventoryException e) {
                    view.displayNoInventoryMessage();
                    keepGoing = getUserContinueChoice(
                            "Would you like to select a different product? Enter (Y)es or (N)o");

                    if (keepGoing) {
                        continue;
                    } else {
                        issueRefund(userChange);
                        break;
                    }
                }

                try {
                    service.checkForEnoughFunds(candySelection, cashInserted);
                } catch (InsufficientFundsException e) {
                    view.displayInsufficientFundsMessage(cashInserted);
                    keepGoing = getUserContinueChoice(
                            "Would you like to insert more funds? "
                            + "Enter (y)es to continue or (n)o for a refund");
                    if (keepGoing) {
                        cashInserted = getAdditionalFunds(cashInserted);
                        userChange = new Change(cashInserted);
                        continue;
                    } else {
                        issueRefund(userChange);
                        break;
                    }
                }

                dispenseItemAndChange(candySelection, userChange);
                service.decrementItemInventory(candySelection.getName());
                break;

            } // end main while loop

            view.displayExitBanner();
            
        } catch (VendingMachinePersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }

    } // end run

    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service) {
        this.view = view;
        this.service = service;
    }

    private VmItem getCandySelection() throws VendingMachinePersistenceException {
        while (true) {
            String candySelection = view.displayCandySelectionPrompt();
            if ("exit".equalsIgnoreCase(candySelection)) {
                return null;
            }

            VmItem vmItem = service.getItem(candySelection);
            if (vmItem != null) {
                return vmItem;
            } else {
                view.displayInvalidItemSelectionBanner();
            }
        }
    }

    private BigDecimal getFunds() {
        double payment = view.promptUserForPayment();
        String paymentString = String.valueOf(payment);
        BigDecimal paymentBd = null;

        try {
            paymentBd = new BigDecimal(paymentString).setScale(2, RoundingMode.FLOOR);
        } catch (NumberFormatException e) {
            view.displayErrorMessage("Error creating BigDecimal for payment");
        }

        return paymentBd;
    }

    private void displayVendingMachineContents() throws VendingMachinePersistenceException {
        List<VmItem> itemList = service.getAllInventory();
        view.displayVendingMachineContents(itemList);
    }

    private boolean getUserContinueChoice(String prompt) {
        String choice = view.promptUserForTransactionChoice(prompt).toLowerCase();

        while (true) {
            switch (choice.charAt(0)) {
                case 'y':
                    return true;
                case 'n':
                    return false;
                default:
                    choice = view.displayInvalidContinueChoiceBanner();
                    break;
            }
        }
    } // end getUserContinueChoice

    private BigDecimal getAdditionalFunds(BigDecimal funds) {
        BigDecimal additionalFunds = getFunds();
        return funds.add(additionalFunds);
    }

    private void issueRefund(Change userChange) {
        view.displayRefundBanner();
        userChange.calculateRefund();
        view.displayRefund(userChange);
    }

    private void dispenseItemAndChange(VmItem candySelection, Change userChange) {
        view.displayDispenseItemBanner(candySelection);
        view.displayUserChangeBanner();
        userChange.calculateRefund(candySelection);
        view.displayRefund(userChange);
    }

}
