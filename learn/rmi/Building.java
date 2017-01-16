/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.learn.rmi;

/**
 *
 * @author 000
 */
public class Building {
    private String street;
    private int number;
    
    public Building(String street, int number){
        this.street = street;
        this.number = number;
    }
    
    public String getStreet(){
        return street;
    }
    
    public void setStreet(String street){
        this.street = street;
    }
    
    public int getNumber(){
        return number;
    }
    
    public void setNumber(int number){
        this.number = number;
    }
}
