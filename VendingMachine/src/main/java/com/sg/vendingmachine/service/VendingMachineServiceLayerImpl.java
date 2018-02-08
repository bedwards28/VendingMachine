/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.VmItem;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author blake
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    @Override
    public void createItem(VmItem item) throws
            VendingMachineDuplicateItemException,
            VendingMachineDataValidationException,
            VendingMachinePersistenceException {

        if (dao.getItem(item.getName()) != null) {
            throw new VendingMachineDuplicateItemException(
                    "ERROR: Could not create item. Item "
                    + "\'" + item.getName() + "\'"
                    + " already exists in inventory");
        }

        validateItemData(item);

        dao.addItem(item.getName(), item);

//        auditDao.writeAuditEntry(
//                "Item \'" + item.getName() + "\' CREATED");

    } // end createItem

    @Override
    public List<VmItem> getAllInventory() throws
            VendingMachinePersistenceException {
        return dao.getAllInventory();
    }

    @Override
    public VmItem getItem(String name) throws
            VendingMachinePersistenceException {
        return dao.getItem(name);
    }

    @Override
    public VmItem removeItem(String name) throws
            VendingMachinePersistenceException {
        VmItem removedItem = dao.removeItem(name);
//        auditDao.writeAuditEntry("Item \'" + name + "\' REMOVED");
        return removedItem;
    }

    @Override
    public VmItem decrementItemInventory(String name) throws VendingMachinePersistenceException {
        int currentInventory = dao.getItem(name).getNumInInventory();
        currentInventory--;
        String updatedInventoryString = Integer.toString(currentInventory);
        dao.editItem(name, 2, updatedInventoryString);
//        auditDao.writeAuditEntry(dao.getItem(name).getName() + " inventory DECREASED BY 1");
        return dao.getItem(name);
    }

    private void validateItemData(VmItem item) throws
            VendingMachineDataValidationException {

        if (item.getName() == null
                || item.getName().trim().length() == 0
                || item.getCost() == null
                || item.getCost().toString().trim().length() == 0
                || item.getCost().compareTo(BigDecimal.ZERO) < 0
                || item.getNumInInventory() < 0) {

            throw new VendingMachineDataValidationException(
                    "ERROR: All fields (Name, Cost, Inventory) are required. "
                    + "Cost and Inventory must be greater than 0.");
        }
    }

    @Override
    public VmItem checkForItemInventory(VmItem item) throws NoItemInventoryException {
        int count = item.getNumInInventory();

        if (count > 0) {
            return item;
        } else {
            throw new NoItemInventoryException("ERROR: No inventory for selected item");
        }
    }

    @Override
    public boolean checkForEnoughFunds(VmItem item, BigDecimal fundsEntered) throws
            InsufficientFundsException {
        
        if (fundsEntered.compareTo(item.getCost()) >= 0) {
            return true;
        } else {
            throw new InsufficientFundsException(
                    "Insufficient Funds");
        }
    }
    
}
    
