/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.learn.rmi;

import PO41.Koval.wdad.data.managers.DataManager;
import PO41.Koval.wdad.data.managers.PreferencesManager;
import PO41.Koval.wdad.utils.PreferencesConstantManager;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author 000
 */
public class Client {
    private static PreferencesManager preferencesManager;
    private static final  String XML_DATA_MANAGER = "XmlDataManager";
    
   public static void main (String[] args) throws IOException, MalformedURLException{
        try {
            preferencesManager = PreferencesManager.getInstance();
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            ex.printStackTrace();
        }
        
        System.setProperty("java.rmi.server.useCodebaseOnly", preferencesManager.getProperty(PreferencesConstantManager.USE_CODE_BASE_ONLY));
        System.setProperty("java.security.policy", preferencesManager.getProperty(PreferencesConstantManager.POLICY_PATH));
        System.setSecurityManager(new SecurityManager());
        
        Registry registry;
        registry = LocateRegistry.getRegistry(
                   preferencesManager.getProperty(PreferencesConstantManager.REGISTRY_ADDRESS).trim(),
                   Integer.parseInt(preferencesManager.getProperty(PreferencesConstantManager.REGISTRY_PORT)));
        
        try{
                DataManager xmlDataManager = (DataManager)registry.lookup(XML_DATA_MANAGER);
                Building b = new Building("Specialistov",11);
                System.out.println(xmlDataManager.getBill(b, 11));
                xmlDataManager.setTariff("gas", 130);
                
            }catch (NotBoundException e){
                System.err.println("Your code don't work");
                e.printStackTrace();
            }
   }
   
 
}
