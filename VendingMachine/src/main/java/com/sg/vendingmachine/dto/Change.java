/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dto;

import java.math.BigDecimal;

/**
 *
 * @author blake
 */
public class Change {

    private int numQuarters;
    private int numDimes;
    private int numNickels;
    private int numPennies;
    private BigDecimal total;

    public Change(BigDecimal total) {
        this.total = total;
        numQuarters = 0;
        numDimes = 0;
        numNickels = 0;
        numPennies = 0;
    }

    public int getNumQuarters() {
        return numQuarters;
    }

    public void setNumQuarters(int numQuarters) {
        this.numQuarters = numQuarters;
    }

    public int getNumDimes() {
        return numDimes;
    }

    public void setNumDimes(int numDimes) {
        this.numDimes = numDimes;
    }

    public int getNumNickels() {
        return numNickels;
    }

    public void setNumNickels(int numNickels) {
        this.numNickels = numNickels;
    }

    public int getNumPennies() {
        return numPennies;
    }

    public void setNumPennies(int numPennies) {
        this.numPennies = numPennies;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void calculateRefund() {
        int intTotal = total.multiply(new BigDecimal("100")).intValue();
        numQuarters = intTotal / 25;
        intTotal = intTotal % 25;
        numDimes = intTotal / 10;
        intTotal = intTotal % 10;
        numNickels = intTotal / 5;
        intTotal = intTotal % 5;
        numPennies = intTotal;
    }
    
    public void calculateRefund(VmItem item) {
        BigDecimal itemCost = item.getCost();
        total = total.subtract(itemCost);
        calculateRefund();
    }

}
