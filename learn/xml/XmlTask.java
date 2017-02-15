/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.learn.xml;

import PO41.Koval.wdad.learn.rmi.Building;
import PO41.Koval.wdad.learn.rmi.Flat;
import PO41.Koval.wdad.learn.rmi.Registration;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author 000
 */
public class XmlTask {
    private class RegValues {
        public double coldwaterRegistration = 0;
        public double hotwaterRegistration = 0;
        public double electricityRegistration = 0;
        public double gasRegistration = 0;
    }
    
    private Document doc;
    private final String path ="src/PO41/Koval/wdad/learn/xml/building.xml";
    
    public XmlTask()  {
        try{
        generateDocument();
        }
        catch( IOException ex){
            System.out.println(ex);
        }
        catch(ParserConfigurationException ex){
            System.out.println(ex);
        }
        catch( SAXException ex){
            System.out.println(ex);
        }
    }
    
    private void generateDocument() throws IOException, ParserConfigurationException, SAXException{
        File xmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(xmlFile);
    }
    
    private void updateDocument() throws IOException{
        DOMImplementationLS domImplementationLS =
            (DOMImplementationLS)doc.getImplementation().getFeature("LS", "3.0");
        LSOutput lsOutput = domImplementationLS.createLSOutput();
        try (FileOutputStream outputStream = new FileOutputStream(path)) {
            lsOutput.setByteStream(outputStream);
            LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
            lsSerializer.write(doc, lsOutput);
        }
    }
    
    private NodeList getFlatsBuild(NodeList buildings, String street, int buildingNumber){
        NodeList flats = null;
        NamedNodeMap buildingAttributes;
        if (buildings != null) {
            for (int i=0; i < buildings.getLength(); i++) {
                buildingAttributes = buildings.item(i).getAttributes();
                if (buildingAttributes.getNamedItem("street").getTextContent().equals(street)&&
                        Integer.valueOf(buildingAttributes.getNamedItem("number").getTextContent()) == buildingNumber){
                    flats = buildings.item(i).getChildNodes();
                }
            }
        }
        return flats;
    }
    
    private NodeList getRegistrationFlat(NodeList flats, int numberFlat){
        NodeList registration = null;
        NamedNodeMap flatsAttributes;
        if (flats != null){
            for (int i=0; i< flats.getLength(); i++){
                flatsAttributes = flats.item(i).getAttributes();
                if (Integer.valueOf(flatsAttributes.getNamedItem("number").getTextContent()) == numberFlat) {
                    registration =((Element) flats.item(i)).getElementsByTagName("registration");
                }
            }
        }
        return registration;
    }
    
    private Node getLastRegistration(NodeList registrations){
        Node registration = null;
        NamedNodeMap registrationsAttributes;
        if (registrations != null){
            int lastYearRegistration=0;
            int lastMonthRegistration = 0;
            for (int i=0; i<registrations.getLength(); i++){
                registrationsAttributes = registrations.item(i).getAttributes();
                if(Integer.valueOf(registrationsAttributes.getNamedItem("year").getTextContent()) >= lastYearRegistration &&
                   Integer.valueOf(registrationsAttributes.getNamedItem("month").getTextContent()) >= lastMonthRegistration){
                    lastYearRegistration = Integer.valueOf(registrationsAttributes.getNamedItem("year").getTextContent());
                    lastMonthRegistration = Integer.valueOf(registrationsAttributes.getNamedItem("month").getTextContent());
                    registration = registrations.item(i);
                }
            }
        }
        System.out.println();
        return registration;
    }
    
    private Node getPrevRegistration(NodeList registrations, int lastYearRegistration, int lastMonthRegistration){
        Node registration = null;
        NamedNodeMap registrationsAttributes;
        if (registrations != null){
            for (int i=0; i<registrations.getLength(); i++){
                registrationsAttributes = registrations.item(i).getAttributes();
                if(Integer.valueOf(registrationsAttributes.getNamedItem("year").getTextContent()) >= lastYearRegistration &&
                   Integer.valueOf(registrationsAttributes.getNamedItem("month").getTextContent()) == (lastMonthRegistration-1)){
                    registration = registrations.item(i);
                }
            }
        }
        if (registration == null){
            for (int i=0; i<registrations.getLength(); i++){
                registrationsAttributes = registrations.item(i).getAttributes();
                if(Integer.valueOf(registrationsAttributes.getNamedItem("year").getTextContent()) >= lastYearRegistration - 1 &&
                   Integer.valueOf(registrationsAttributes.getNamedItem("month").getTextContent()) >= 12){
                    registration = registrations.item(i);
                }
            }
        }
        return registration;
    }
     
    private RegValues getRegistration(Node registrations){
        RegValues reg = new RegValues();
            NodeList coldwater = ((Element) registrations).getElementsByTagName("coldwater");
            NodeList hotwater = ((Element) registrations).getElementsByTagName("hotwater");
            NodeList electricity = ((Element) registrations).getElementsByTagName("electricity");
            NodeList gas = ((Element) registrations).getElementsByTagName("gas");
            
            reg.coldwaterRegistration = Integer.valueOf(coldwater.item(0).getTextContent());
            reg.hotwaterRegistration = Integer.valueOf(hotwater.item(0).getTextContent());
            reg.electricityRegistration = Integer.valueOf(electricity.item(0).getTextContent());
            reg.gasRegistration = Integer.valueOf(gas.item(0).getTextContent());

        return reg;            
    }
    
    public double getBill(String street, int buildingNumber, int flatNumber){
        double sum=0;
        NodeList tariffs = doc.getElementsByTagName("tariffs");       
        NodeList buildings = doc.getElementsByTagName("buildings");

        NodeList flats = getFlatsBuild(buildings, street, buildingNumber);
        NodeList registrations = getRegistrationFlat(flats, flatNumber);
        
        Node lastRegistrations = getLastRegistration(registrations);
        Node prevRegistrations = null;
        
        int lastYearRegistration = 0;
        int lastMonthRegistration = 0;
         if (lastRegistrations != null) {
             NamedNodeMap attributesRegistration = lastRegistrations.getAttributes();
             lastYearRegistration = Integer.valueOf(attributesRegistration.getNamedItem("year").getTextContent());
             lastMonthRegistration = Integer.valueOf(attributesRegistration.getNamedItem("month").getTextContent());
             prevRegistrations = getPrevRegistration(registrations, lastYearRegistration, lastMonthRegistration);
         }
         
         RegValues reg = new RegValues();
         if (lastRegistrations != null && prevRegistrations !=null){
             RegValues lastRegistration = getRegistration(lastRegistrations);
             RegValues prevRegistration = getRegistration(prevRegistrations);
             reg.coldwaterRegistration = lastRegistration.coldwaterRegistration-prevRegistration.coldwaterRegistration;
             reg.hotwaterRegistration = lastRegistration.hotwaterRegistration - prevRegistration.hotwaterRegistration;
             reg.electricityRegistration = lastRegistration.electricityRegistration - prevRegistration.electricityRegistration;
             reg.gasRegistration = lastRegistration.gasRegistration - prevRegistration.gasRegistration;             
         }
         
         NamedNodeMap tariffsAttributes = tariffs.item(0).getAttributes();
         sum = reg.coldwaterRegistration * Double.parseDouble(tariffsAttributes.getNamedItem("coldwater").getTextContent())+
                 reg.hotwaterRegistration * Double.parseDouble(tariffsAttributes.getNamedItem("hotwater").getTextContent())+
                 reg.electricityRegistration * Double.parseDouble(tariffsAttributes.getNamedItem("electricity").getTextContent())+
                 reg.gasRegistration * Double.parseDouble(tariffsAttributes.getNamedItem("gas").getTextContent());
         double a = sum;
         return sum;
    }
    
    public void setTariffs(String tariffName, double newValue) throws IOException{
        NodeList tariffs = doc.getElementsByTagName("tariffs");
        NamedNodeMap tariffsAttributes = tariffs.item(0).getAttributes();
        tariffsAttributes.getNamedItem(tariffName).setNodeValue(String.valueOf(newValue));
        updateDocument();
    }
    
    public void addRegistration (String street, int buildingNumber, int flatNumber, int year, int month,
                                 double coldWater, double hotWater, double electricity, double gas) throws IOException {
        NodeList buildings = doc.getElementsByTagName("buildings");
        NodeList flats = getFlatsBuild(buildings, street, buildingNumber);
        NamedNodeMap flatsAttributes;
        if (flats != null){
        for (int i = 0; i < flats.getLength(); i++) {
            flatsAttributes = flats.item(i).getAttributes();
            if(Integer.valueOf(flatsAttributes.getNamedItem("number").getTextContent()) == flatNumber){
                Node newRegestration = doc.createElement("registration");
                
                ((Element)newRegestration).setAttribute("year", String.valueOf(year));
                ((Element)newRegestration).setAttribute("month", String.valueOf(month));
                
                Node coldwater = doc.createElement("coldwater");
                coldwater.setTextContent(String.valueOf(coldWater));
                
                Node hotwater = doc.createElement("hotwater");
                hotwater.setTextContent(String.valueOf(hotWater));
                
                Node el = doc.createElement("electricity");
                el.setTextContent(String.valueOf(electricity));
                
                Node g = doc.createElement("gas");
                g.setTextContent(String.valueOf(gas));
                
                newRegestration.appendChild(coldwater);
                newRegestration.appendChild(hotwater);
                newRegestration.appendChild(el);
                newRegestration.appendChild(g);
                 
                flats.item(i).appendChild(newRegestration);
                updateDocument();
            }
        }
        }
    }
    
    public Flat getFlat(Building building, int flatNumber){
        NodeList buildings = doc.getElementsByTagName("buildings");
        NodeList flats = getFlatsBuild(buildings, building.getStreet(), building.getNumber());
        NodeList registrations = getRegistrationFlat(flats, flatNumber);
        int personsQuantity = 0;
        double area = 0.0;
        ArrayList<Registration> regs = new ArrayList();
        
        for (int i=0; i< flats.getLength(); i++){
            if (flats.item(i).getAttributes().getNamedItem("number").getTextContent().equals(flatNumber)){
                personsQuantity = Integer.valueOf(flats.item(i).getAttributes().getNamedItem("personsquantity").getTextContent());
                area = Double.valueOf(flats.item(i).getAttributes().getNamedItem("area").getTextContent());
            } 
        }
         
        
        for (int i =0; i< registrations.getLength(); i++){
            NamedNodeMap regAttr = registrations.item(i).getAttributes();
            int year = Integer.valueOf(regAttr.getNamedItem("year").getTextContent());
            int month = Integer.valueOf(regAttr.getNamedItem("month").getTextContent());
            
            Date registrationDate = null;
            registrationDate.setYear(year);
            registrationDate.setMonth(month);
            
            RegValues regValue = getRegistration(registrations.item(i));
            
            Registration reg = new Registration(registrationDate, regValue.coldwaterRegistration, regValue.hotwaterRegistration, regValue.electricityRegistration, regValue.gasRegistration);
            regs.add(reg);
        }
        Flat flat = new Flat(flatNumber, personsQuantity, area, regs);
        return flat;
    }
}

