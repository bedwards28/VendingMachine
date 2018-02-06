/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.VmItem;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author blake
 */
public class VendingMachineDaoTest {
    
    VendingMachineDao dao;
    
    public VendingMachineDaoTest() {
        dao = new VendingMachineDaoFileImpl();
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        List<VmItem> inventory = dao.getAllInventory();
        for (VmItem item : inventory) {
            dao.removeItem(item.getName());
        }
        
        VmItem item = new VmItem("Skittles");
        item.setCost("1.50");
        item.setNumInInventory(20);
        dao.addItem(item.getName(), item);
        
        VmItem item2 = new VmItem("Snickers King Size");
        item2.setCost("2.50");
        item2.setNumInInventory(40);
        dao.addItem(item2.getName(), item2);

        VmItem item3 = new VmItem("Twix");
        item3.setCost("1.75");
        item3.setNumInInventory(40);
        dao.addItem(item3.getName(), item3);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of addItem method, of class VendingMachineDao.
     */
    @Test
    public void testAddGetItem() throws Exception {
        VmItem item = new VmItem("Starburst");
        item.setCost(new BigDecimal(3.50));
        item.setNumInInventory(50);
        dao.addItem(item.getName(), item);
        
        VmItem fromDao = dao.removeItem(item.getName());
        assertEquals(item, fromDao);
        
        VmItem item2 = new VmItem("Rolos");
        item2.setCost("3.50");
        item2.setNumInInventory(100);
        dao.addItem("rOLos", item2);
        
        fromDao = dao.removeItem("roLOs");
        assertEquals(item2, fromDao);
    }

    /**
     * Test of removeItem method, of class VendingMachineDao.
     */
    @Test
    public void testRemoveItem() throws Exception {
        assertEquals(3, dao.getAllInventory().size());
        
        dao.removeItem("skiTTles");
        assertEquals(2, dao.getAllInventory().size());
        
        dao.removeItem("TwIx");
        assertEquals(1, dao.getAllInventory().size());
        
        VmItem item = dao.removeItem("Salad");
        assertNull(item);
    }

    /**
     * Test of getAllInventory method, of class VendingMachineDao.
     */
    @Test
    public void testGetAllInventory() throws Exception {
        assertEquals(3, dao.getAllInventory().size());
        
        VmItem newItem = new VmItem("3 Musketeers");
        newItem.setCost("1.00");
        newItem.setNumInInventory(10);
        dao.addItem(newItem.getName(), newItem);
        assertEquals(4, dao.getAllInventory().size());
        
        dao.removeItem("3 musketEErs");
        dao.removeItem("twix");
        assertEquals(2, dao.getAllInventory().size());
        
        dao.removeItem("skittles");
        dao.removeItem("Snickers king size");
        assertEquals(0, dao.getAllInventory().size());
    }
    
    @Test
    public void testEditItem() throws Exception {
        dao.editItem("skittles", 1, "2.50");
        dao.editItem("skittLEs", 2, "500");
        assertEquals(new BigDecimal("2.50"), dao.getItem("skittles").getCost());
        assertEquals(500, dao.getItem("skiTTlEs").getNumInInventory());
    }
    
}
