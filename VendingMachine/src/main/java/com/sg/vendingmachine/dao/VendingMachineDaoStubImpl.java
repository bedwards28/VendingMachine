/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VmItem;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author blake
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {
    
    VmItem onlyItem;
    List<VmItem> itemList = new ArrayList<>();

    public VendingMachineDaoStubImpl() {
        onlyItem = new VmItem("KitKat");
        onlyItem.setCost("2.00");
        onlyItem.setNumInInventory(100);
        itemList.add(onlyItem);
    }

    @Override
    public VmItem addItem(String name, VmItem item) throws VendingMachinePersistenceException {
        if (name.equals(onlyItem.getName())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public VmItem removeItem(String name) throws VendingMachinePersistenceException {
        if (name.equals(onlyItem.getName())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public List<VmItem> getAllInventory() throws VendingMachinePersistenceException {
        return itemList;
    }

    @Override
    public VmItem getItem(String name) throws VendingMachinePersistenceException {
        if (name.equals(onlyItem.getName())) {
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public VmItem editItem(String name, int field, String update) throws VendingMachinePersistenceException {
        if (name.equals(onlyItem.getName())) {
            switch(field){
                case 1:
                    onlyItem.setCost(update);
                    break;
                case 2:
                    onlyItem.setNumInInventory(Integer.parseInt(update));
                    break;
                default:
                    return null;
            }
            return onlyItem;
        } else {
            return null;
        }
    }
    
}
