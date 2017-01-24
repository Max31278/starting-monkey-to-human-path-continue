/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.learn.rmi;
 
import PO41.Koval.wdad.data.managers.DataManager;
import PO41.Koval.wdad.learn.xml.XmlTask;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 000
 */
public class XmlDataManagerImpl  implements DataManager  {
    
    private XmlTask xmlTask = new XmlTask();
    
    public double getBill (Building building, int flatNumber){
        Building c = building;
        String a = building.getStreet();
        int b = building.getNumber();
        return xmlTask.getBill(building.getStreet(), building.getNumber(), flatNumber);
    }
    public Flat getFlat (Building building, int flatNumber){
        return xmlTask.getFlat(building, flatNumber);
    }
    public void setTariff (String tariffName, double newValue){
        try {
            xmlTask.setTariffs(tariffName, newValue);
        } catch (IOException ex) {
            Logger.getLogger(XmlDataManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void addRegistration (Building building, int flatNumber, Registration registrations) {
        try {
            xmlTask.addRegistration(building.getStreet(), building.getNumber(), flatNumber, registrations.getData().getYear(), registrations.getData().getMonth(), 
                    registrations.getColdwater(), registrations.getHotwater(), registrations.getElectricity(), registrations.getGas());
        } catch (IOException ex) {
            Logger.getLogger(XmlDataManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
