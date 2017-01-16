/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.learn.rmi;

import PO41.Koval.wdad.data.managers.PreferencesManager;
import PO41.Koval.wdad.utils.PreferencesConstantManager;
import java.io.IOException;
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
    
   public static void main (String[] args) throws IOException{
        try {
            preferencesManager = PreferencesManager.getInstance();
        } catch (ParserConfigurationException | IOException | SAXException ex) {
            ex.printStackTrace();
        }
        
        System.setProperty("java.rmi.server.useCodebaseOnly", preferencesManager.getProperty(PreferencesConstantManager.USE_CODE_BASE_ONLY));
        System.setProperty("java.rmi.server.codebase", PreferencesConstantManager.CLASS_PROVIDER);
        System.setProperty("java.security.policy", preferencesManager.getProperty(PreferencesConstantManager.POLICY_PATH));
        System.setSecurityManager(new SecurityManager());
        
        Registry registry;
        registry = LocateRegistry.getRegistry(
                   preferencesManager.getProperty(PreferencesConstantManager.REGISTRY_ADDRESS),
                   Integer.parseInt(preferencesManager.getProperty(PreferencesConstantManager.REGISTRY_PORT)));
        
        try{
                XmlDataManager xmlDataManager = (XmlDataManager)registry.lookup(XML_DATA_MANAGER);
                workXmlManager(xmlDataManager);
            }catch (NotBoundException e){
                System.err.println("Your code don't work");
                e.printStackTrace();
            }
   }
   
   private static void workXmlManager(XmlDataManager xmlDataManager ){
       Building b = new Building("michurina",20);
        System.out.println(xmlDataManager.getBill(b, 1));
        
       Flat flat = xmlDataManager.getFlat(b, 3);
       System.out.println("flat info: number - " + flat.getNumber() + ", area - " + flat.getArea() +
               ", personsQuantity" + flat.getPersonsQuantity());
       System.out.println("Registration : ");
       List<Registration> regs = flat.getRegistration();
        for (int i = 0; i < regs.size(); i++) {
            
            Registration reg = regs.get(i);
            System.out.println("Registration "+ i + ":");
            System.out.println("date: year -" + reg.getData().getYear() + 
                    ", month" + reg.getData().getMonth());
            System.out.println("coldwater:" + reg.getColdwater());
            System.out.println("hotwater:" + reg.getHotwater());
            System.out.println("el:" + reg.getElectricity());
            System.out.println("gas:" + reg.getGas());
            
        }
        
        Date registrationDate = null;
        registrationDate.setYear(2011);
        registrationDate.setMonth(4);
        
        Registration registration = new Registration(registrationDate, 351, 224, 166, 131);
        xmlDataManager.setTariff("gas", 110);
        xmlDataManager.addRegistration(b, 3, registration);
   }
}
