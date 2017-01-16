/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.learn.xml;

/**
 *
 * @author 000
 */
public class TestXmlTask {
    public static void main(String[] args){
        try {
            XmlTask test = new XmlTask();
            test.setTariffs("electricity", 10.7);
            System.out.println(test.getBill("Specialistov", 11, 11));
            test.addRegistration("Specialistov", 11, 11, 2016, 4, 339, 226, 159, 125);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
