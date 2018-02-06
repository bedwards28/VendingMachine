/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.VmItem;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author blake
 */
public interface VendingMachineServiceLayer {
    
    void createItem(VmItem item) throws 
            VendingMachineDuplicateItemException,
            VendingMachineDataValidationException,
            VendingMachinePersistenceException;
    
    List<VmItem> getAllInventory() throws 
            VendingMachinePersistenceException;
    
    VmItem getItem(String name) throws 
            VendingMachinePersistenceException;
    
    VmItem removeItem(String name) throws 
            VendingMachinePersistenceException;
    
    VmItem decrementItemInventory(String name) throws 
            VendingMachinePersistenceException;
    
    VmItem checkForItemInventory(VmItem item) throws 
            NoItemInventoryException;
    
    boolean checkForEnoughFunds(VmItem item, BigDecimal fundsEntered) throws 
            InsufficientFundsException;
}
