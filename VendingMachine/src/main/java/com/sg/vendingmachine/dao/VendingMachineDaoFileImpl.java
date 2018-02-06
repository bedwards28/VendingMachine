/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VmItem;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author blake
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {

    public static final String INVENTORY_FILE = "inventory.txt";
    public static final String DELIMITER = "::";

    private Map<String, VmItem> inventory = new HashMap<>();

    @Override
    public VmItem addItem(String name, VmItem item) throws VendingMachinePersistenceException {
        loadInventory();
        VmItem newItem = inventory.put(name.toUpperCase(), item);
        writeInventory();
        return newItem;
    }

    @Override
    public VmItem removeItem(String name) throws VendingMachinePersistenceException {
        loadInventory();
        VmItem removedItem = inventory.remove(name.toUpperCase());
        writeInventory();
        return removedItem;
    }

    @Override
    public List<VmItem> getAllInventory() throws VendingMachinePersistenceException {
        loadInventory();
        return new ArrayList<>(inventory.values());
    }

    @Override
    public VmItem getItem(String name) throws VendingMachinePersistenceException {
        loadInventory();
        return inventory.get(name.toUpperCase());
    }

    @Override
    public VmItem editItem(String name, int field, String update) 
            throws VendingMachinePersistenceException {
        
        loadInventory();
        VmItem editItem = getItem(name.toUpperCase());
        
        if (editItem != null) {
            switch (field) {
                case 1:
                    editItem.setCost(update);
                    break;
                case 2:
                    editItem.setNumInInventory(Integer.parseInt(update));
                    break;
                default:
                    throw new UnsupportedOperationException("Invalid edit field selected ");
            }
            
            inventory.put(name.toUpperCase(), editItem);
            writeInventory();
        }
        
        return editItem;
    }    
    
    private void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(new File(INVENTORY_FILE));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "Could not load inventory into memory.", e);
        }

        String currentLine;
        String[] currentTokens;

        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            VmItem currentItem = new VmItem(currentTokens[0]);
            currentItem.setCost(currentTokens[1]);
            currentItem.setNumInInventory(Integer.parseInt(currentTokens[2]));

            inventory.put(currentItem.getName().toUpperCase(), currentItem);
        }

        scanner.close();
    } // end loadInventory

    private void writeInventory() throws VendingMachinePersistenceException {
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save item data.", e);
        }

        List<VmItem> itemList = new ArrayList<>(inventory.values());

        for (VmItem currentItem : itemList) {
            out.println(currentItem.getName() + DELIMITER
                    + currentItem.getCost().toString() + DELIMITER
                    + currentItem.getNumInInventory());
            out.flush();
        }
        out.close();
    } // end writeLibrary

}
