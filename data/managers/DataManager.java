/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.data.managers;

import PO41.Koval.wdad.learn.rmi.Building;
import PO41.Koval.wdad.learn.rmi.Flat;
import PO41.Koval.wdad.learn.rmi.Registration;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author 000
 */
public interface DataManager  extends Remote{
    public double getBill (Building building, int flatNumber)throws RemoteException;
    public Flat getFlat (Building building, int flatNumber)throws RemoteException;
    public void setTariff (String tariffName, double newValue)throws RemoteException;
    public void addRegistration (Building building, int flatNumber, Registration registrations)throws RemoteException;
}
