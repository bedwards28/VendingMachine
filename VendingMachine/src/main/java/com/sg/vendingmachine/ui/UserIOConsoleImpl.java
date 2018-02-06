/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import java.text.NumberFormat;
import java.util.Scanner;

/**
 *
 * @author blake
 */
public class UserIOConsoleImpl implements UserIO {
    
    private Scanner sc = new Scanner(System.in);
    private String userInputString;

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        double userInput = 0;
        print(prompt);

        while (true) {
            userInputString = sc.nextLine();
            
            try {
                userInput = Double.parseDouble(userInputString);
                break;
            } catch (NumberFormatException e) {
                print(userInputString + " is not a valid entry. Please enter a valid number:");
            }
        }

        return userInput;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double userInput;
        
        // Add currency formatter for vending machine project
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        
        userInput = readDouble(prompt + " between " 
                + formatter.format(min) + " and " 
                + formatter.format(max));
        
        while(userInput < min || userInput > max){
            userInput = readDouble("Please enter a valid number between " + min + " and " + max);
        }
        return userInput;
    }

    @Override
    public float readFloat(String prompt) {
        float userInput = 0;
        print(prompt);

        while (true) {
            userInputString = sc.nextLine();
            
            try {
                userInput = Float.parseFloat(userInputString);
                break;
            } catch (NumberFormatException e) {
                print(userInputString + " is not a valid entry. Please enter a valid number:");
            }
        }

        return userInput;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float userInput;
        
        userInput = readFloat(prompt + " between " + min + " and " + max);
        
        while(userInput < min || userInput > max){
            userInput = readFloat("Please enter a valid number between " + min + " and " + max);
        }
        return userInput;
    }

    @Override
    public int readInt(String prompt) {
        int userInput = 0;
        print(prompt);
        

        while (true) {
            userInputString = sc.nextLine();
            
            try {
                userInput = Integer.parseInt(userInputString);
                break;
            } catch (NumberFormatException e) {
                print(userInputString + " is not a valid entry. Please enter a valid number:");
            }
        }

        return userInput;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int userInput;
        
        userInput = readInt(prompt + " between " + min + " and " + max);
        
        while(userInput < min || userInput > max){
            userInput = readInt("Please enter a valid number between " + min + " and " + max);
        }
        return userInput;
    }

    @Override
    public long readLong(String prompt) {
        long userInput = 0;
        print(prompt);
        

        while (true) {
            userInputString = sc.nextLine();
            
            try {
                userInput = Long.parseLong(userInputString);
                break;
            } catch (NumberFormatException e) {
                print(userInputString + " is not a valid entry. Please enter a valid number:");
            }
        }

        return userInput;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long userInput;
        
        userInput = readLong(prompt + " between " + min + " and " + max);
        
        while(userInput < min || userInput > max){
            userInput = readLong("Please enter a valid number between " + min + " and " + max);
        }
        return userInput;
    }

    @Override
    public String readString(String prompt) {
        print(prompt);
        userInputString = sc.nextLine();
        return userInputString;
    }
    
}
