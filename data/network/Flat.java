/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.data.network;

import java.util.Set;

/**
 *
 * @author 000
 */
public class Flat {
    private int id;
    private int number;
    private Building building;
    private int personsQuantity;
    private double area;
    private  Set<Registration> registrations;

     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getPersonsQuantity(){
        return personsQuantity;
    }
    
    public void setPersonsQuantity(int personsQuantity){
        this.personsQuantity = personsQuantity;
    }
    
    public double getArea(){
        return area;
    }
    
    public void setArea(double area){
        this.area = area;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Set<Registration> getRegistrations() {
        return registrations;
    }

    public void setRegistrations(Set<Registration> registrations) {
        this.registrations = registrations;
    }
}

