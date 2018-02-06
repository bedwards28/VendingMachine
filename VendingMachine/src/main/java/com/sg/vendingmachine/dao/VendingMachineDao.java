/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VmItem;
import java.util.List;

/**
 *
 * @author blake
 */
public interface VendingMachineDao {
    
    VmItem addItem(String name, VmItem item) throws VendingMachinePersistenceException;
    
    VmItem removeItem(String name) throws VendingMachinePersistenceException;
    
    List<VmItem> getAllInventory() throws VendingMachinePersistenceException;
    
    VmItem getItem(String name) throws VendingMachinePersistenceException;
    
    VmItem editItem(String name, int field, String update) throws VendingMachinePersistenceException;
    
}
