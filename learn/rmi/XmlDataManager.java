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
public interface XmlDataManager {
    public double getBill (Building building, int flatNumber);
    public Flat getFlat (Building building, int flatNumber);
    public void setTariff (String tariffName, double newValue);
    public void addRegistration (Building building, int flatNumber, Registration registrations);
}
