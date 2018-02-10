/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VmItem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author blake
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {

    VmItem onlyItem;
    List<VmItem> itemList = new ArrayList<>();
    Map<String, VmItem> items = new HashMap<>();

    public VendingMachineDaoStubImpl() {
        onlyItem = new VmItem("KitKat");
        onlyItem.setCost("2.00");
        onlyItem.setNumInInventory(100);
        items.put(onlyItem.getName().toUpperCase(), onlyItem);

    }

    @Override
    public VmItem addItem(String name, VmItem item) throws VendingMachinePersistenceException {
        return items.put(name.toUpperCase(), item);
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
        return new ArrayList<VmItem>(items.values());
    }

    @Override
    public VmItem getItem(String name) throws VendingMachinePersistenceException {
        return items.get(name.toUpperCase());
    }

    @Override
    public VmItem editItem(String name, int field, String update) throws VendingMachinePersistenceException {
        if (name.equals(onlyItem.getName())) {
            switch (field) {
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
