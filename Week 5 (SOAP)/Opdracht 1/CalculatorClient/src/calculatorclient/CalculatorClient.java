/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculatorclient;

import webservice.NegativeNumberException_Exception;

/**
 *
 * @author Martijn
 */
public class CalculatorClient 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {

        // Add
        int addArg0 = 6;
        int addArg1 = 12;
        int addTotal;
        
        try 
        {
            addTotal = add(addArg0, addArg1);
            System.out.println("Het totaal van " + addArg0 + " + " + addArg1 + " is: " + addTotal);
        } 
        
        catch (NegativeNumberException_Exception ex) 
        {
            System.out.println(ex.getMessage());
        }    
        
        // Minus
        int minusArg0 = 12;
        int minusArg1 = 6;
        int minusTotal;
        
        try 
        {
            minusTotal = minus(minusArg0, minusArg1);
            System.out.println("Het totaal van " + minusArg0 + " - " + minusArg1 + " is: " + minusTotal);
        } 
        
        catch (NegativeNumberException_Exception ex) 
        {
            System.out.println(ex.getMessage());
        }     
        
        // Times
        int timesArg0 = 5;
        int timesArg1 = 4;
        int timesTotal;
        
        try 
        {
            timesTotal = times(timesArg0, timesArg1);
            System.out.println("Het totaal van " + timesArg0 + " * " + timesArg1 + " is: " + timesTotal);
        } 
        
        catch (NegativeNumberException_Exception ex) 
        {
            System.out.println(ex.getMessage());
        }        
               
    }

    private static int add(int arg0, int arg1) throws NegativeNumberException_Exception 
    {
        webservice.WebCalculatorService service = new webservice.WebCalculatorService();
        webservice.WebCalculator port = service.getWebCalculatorPort();
        return port.add(arg0, arg1);
    }

    private static int minus(int arg0, int arg1) throws NegativeNumberException_Exception 
    {
        webservice.WebCalculatorService service = new webservice.WebCalculatorService();
        webservice.WebCalculator port = service.getWebCalculatorPort();
        return port.minus(arg0, arg1);
    }

    private static int times(int arg0, int arg1) throws NegativeNumberException_Exception 
    {
        webservice.WebCalculatorService service = new webservice.WebCalculatorService();
        webservice.WebCalculator port = service.getWebCalculatorPort();
        return port.times(arg0, arg1);
    }
    
}
