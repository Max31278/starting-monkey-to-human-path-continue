/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.data.network;

import java.util.Date;
import java.util.HashMap;

/**
 *
 * @author 000
 */
public class Registration {
    private int id;
    private Date date;
    private Flat flat;
    private HashMap<Tariff, Double> amounts;
    
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Flat getFlat(){
        return flat;
    }
    
    public void setFlat(Flat flat){
        this.flat = flat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public HashMap<Tariff, Double> getAmounts() {
        return amounts;
    }

    public void setAmounts(HashMap<Tariff, Double> amounts) {
        this.amounts = amounts;
    }
}
