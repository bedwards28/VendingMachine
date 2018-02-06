/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachineAuditDaoStubImpl;
import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoStubImpl;
import com.sg.vendingmachine.dto.VmItem;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author blake
 */
public class VendingMachineServiceLayerTest {
    
    private VendingMachineServiceLayer service;
    
    public VendingMachineServiceLayerTest() {
//        VendingMachineDao dao = new VendingMachineDaoStubImpl();
//        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
//        service = new VendingMachineServiceLayerImpl(dao, auditDao);

        ApplicationContext ctx = 
                new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("service", VendingMachineServiceLayer.class);
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of createItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testCreateItem() throws Exception {
        VmItem item = new VmItem("Skittles");
        item.setCost("1.75");
        item.setNumInInventory(100);
        service.createItem(item);
    }
    
    @Test
    public void testCreateItemDuplicateName() throws Exception {
        VmItem item = new VmItem("KitKat");
        item.setCost(BigDecimal.ONE);
        item.setNumInInventory(200);
        
        try {
            service.createItem(item);
            fail("Expected VendingMachineDuplicateItemException was not thrown");
        } catch (VendingMachineDuplicateItemException e) {
            return;
        }
    }
    
    @Test
    public void testCreateItemInvalidData() {
        VmItem item = new VmItem("KitKat");
        item.setCost(BigDecimal.ONE);
        item.setNumInInventory(-1);
        
        try {
            service.createItem(item);
            fail("Expected VendingMachineInvalidDataException");
        } catch (Exception e) {
            return;
        }
    }

    /**
     * Test of getAllInventory method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetAllInventory() throws Exception {
        assertEquals(1, service.getAllInventory().size());
    }

    /**
     * Test of getItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testGetItem() throws Exception {
        VmItem item = service.getItem("KitKat");
        assertNotNull(item);
        item = service.getItem("Twix");
        assertNull(item);
    }

    /**
     * Test of removeItem method, of class VendingMachineServiceLayer.
     */
    @Test
    public void testRemoveItem() throws Exception {
        VmItem item = service.removeItem("KitKat");
        assertNotNull(item);
        item = service.removeItem("Twix");
        assertNull(item);
    }
    
    @Test
    public void testDecrementItemInventory() throws Exception {
        VmItem editItem = service.getItem("KitKat");
        assertEquals(100, editItem.getNumInInventory());
        service.decrementItemInventory(editItem.getName());
        assertEquals(99, editItem.getNumInInventory());
    }
    
}
