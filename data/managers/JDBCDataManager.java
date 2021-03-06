/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.data.managers;

import PO41.Koval.wdad.data.storage.DataSourceFactory;
import PO41.Koval.wdad.learn.rmi.Building;
import PO41.Koval.wdad.learn.rmi.Flat;
import PO41.Koval.wdad.learn.rmi.Registration;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author 000
 */
public class JDBCDataManager implements DataManager {
       private class RegistrationValues  {
        public double coldwaterReg = 0;
        public double hotwaterReg = 0;
        public double electricityReg = 0;
        public double gasReg = 0;
    }
    
    private final DataSource dataSource;
    
    Connection con;
    public JDBCDataManager(DataSource ds, Connection con){
        this.dataSource = ds;
        this.con = con;
    }
    
    @Override
    public double getBill(Building building, int flatNumber) {
        double bill = 0;
        try{
            /*SELECT registrations.id, registrations.date,  `registrations-tariffs`.id,  `registrations-tariffs`.amount, tariffs.name
FROM registrations,  `registrations-tariffs` , tariffs, street, buildings, flats
WHERE street.name =  "Московское шоссе"
AND buildings.number =
AND flats.number =13
AND flats.id = flats_id
AND registrations.id = registrations_id
AND tariffs_name = tariffs.name
LIMIT 0 , 30*/
            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                StringBuilder query = new StringBuilder(
                        "SELECT registrations.id, registrations.date,  `registrations-tariffs`.id,  "
                                + "`registrations-tariffs`.amount, tariffs.name, tariffs.cost\n" +
                        "FROM registrations,  `registrations-tariffs` , tariffs, street, buildings, flats\n" +
                        "WHERE street.name='" + building.getStreet() + "' AND buildings.number='"+ building.getNumber() +"' AND flats.number='" +
                                flatNumber + "' AND flats.id = flats_id\n AND registrations.id = registrations_id\n" + 
                        "AND tariffs_name = tariffs.name ORDER BY registrations.date DESC");
                            
                ResultSet result = statement.executeQuery(query.toString());
                result.next();
                String date = result.getDate("date").toString();
                String[] dat = date.split("-");
                Calendar calendar = Calendar.getInstance();
                calendar.set(
                    Integer.parseInt(dat[0]),
                    Integer.parseInt(dat[1]),
                    Integer.parseInt(dat[2]));
                RegistrationValues reg = new RegistrationValues();
                reg.coldwaterReg =result.getInt("amount") * result.getFloat("cost");
                result.next();
                reg.hotwaterReg =result.getInt("amount") * result.getFloat("cost");
                result.next();
                reg.electricityReg =result.getInt("amount") * result.getFloat("cost");
                result.next();
                reg.gasReg =result.getInt("amount") * result.getFloat("cost");
                
                result.next();
                String Prevdate = result.getDate("date").toString();
                String[] pdat = date.split("-");
                Calendar prevCalendar = Calendar.getInstance();
                calendar.set(
                    Integer.parseInt(dat[0]),
                    Integer.parseInt(dat[1]),
                    Integer.parseInt(dat[2]));
                
                RegistrationValues prevReg = new RegistrationValues();
                prevReg.coldwaterReg =result.getInt("amount") * result.getFloat("cost");
                result.next();
                prevReg.hotwaterReg =result.getInt("amount") * result.getFloat("cost");
                result.next();
                prevReg.electricityReg =result.getInt("amount") * result.getFloat("cost");
                result.next();
                prevReg.gasReg =result.getInt("amount") * result.getFloat("cost");
                
                bill = (reg.coldwaterReg - prevReg.coldwaterReg)+
                        (reg.hotwaterReg - prevReg.hotwaterReg)+
                        (reg.electricityReg - prevReg.electricityReg)+
                        (reg.gasReg - prevReg.gasReg);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return bill;
    }

    @Override
    public Flat getFlat(Building building, int flatNumber) {
        Flat flat = null;
        try{
            /*
            WHERE street.name =  "Московское шоссе"
AND buildings.number =18
AND flats.number =13
                            street.name='" + building.getStreet() + "' ")
                            .append("AND buildings.number='"+ building.getNumber() +"' ")
                            .append("AND number='" + flatNumber + "' "
            */
            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                StringBuilder query = new StringBuilder(
                        "SELECT flats.number, flats.persons_quantity, flats.area ")
                            .append("FROM flats,street,buildings ")
                            .append("WHERE street.name='"+ building.getStreet()+"'")
                            .append("AND buildings.number='"+ building.getNumber() +"' ")
                            .append("AND flats.number='" + flatNumber + "' ");
                ResultSet result = statement.executeQuery(query.toString());
                if(result.next());
                flat = new Flat(
                        result.getInt("number"), 
                        result.getInt("persons_quantity"),
                        result.getDouble("area"), 
                        new LinkedList<Registration>());
            }
        }catch (SQLException e){e.printStackTrace();}
        return flat;
    }

    @Override
    public void setTariff(String tariffName, double newValue) {
        try{
            /*
            UPDATE tariffs SET cost='" + newValue + "' ")
                            .append("WHERE tariffs.name='" + tariffName + "'")
            */
            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                StringBuilder query = new StringBuilder(
                        "UPDATE tariffs SET cost='" + newValue + "' ")
                            .append("WHERE tariffs.name='" + tariffName + "'");
                statement.executeUpdate(query.toString());
            }
        }catch (SQLException e){}
    }

    @Override
    public void addRegistration(Building building, int flatNumber, Registration registration) {
        try{
            try (Connection connection = dataSource.getConnection()) {
                Statement statement = connection.createStatement();
                StringBuilder getFlatIdQuery = new StringBuilder(
                        "SELECT * ")
                            .append("FROM flats,street,buildings ")
                            .append("WHERE street.name='" + building.getStreet() + "' ")
                            .append("AND buildings.number='"+ building.getNumber() +"' ")
                            .append("AND flats.number='" + flatNumber + "' ");       
                ResultSet result = statement.executeQuery(getFlatIdQuery.toString());
                result.next();
                int flatId = result.getInt("id");
                
                StringBuilder query = new StringBuilder(
                        "INSERT INTO registrations (id, date, flats_id) ")
                            .append("VALUES( NULL, '" + parseDate(registration.getData()) + "', ")
                            .append("'"+flatId+"'" + ")");                
                statement.executeUpdate(query.toString());
                
                StringBuilder getlastRegistrationIdQuery = new StringBuilder(
                        "SELECT * ")
                            .append("FROM registrations ")
                            .append("WHERE flats_id='" + flatId + "'"); 
                ResultSet result1 = statement.executeQuery(getlastRegistrationIdQuery.toString());
                
                result1.last();
                 int registrationId= result1.getInt("id");
                

                StringBuilder addColdwater = new StringBuilder(
                        "INSERT INTO `registrations-tariffs` (id, amount, registrations_id,tariffs_name) ")
                            .append("VALUES(NULL, '" + registration.getColdwater() + "',")
                            .append("'"+registrationId+"'" + ",")
                            .append("\"coldwater\")");
                StringBuilder addHotwater = new StringBuilder(
                        "INSERT INTO `registrations-tariffs` (id,amount, registrations_id,tariffs_name) ")
                            .append("VALUES(NULL,'" + registration.getHotwater()+ "',")
                            .append("'"+registrationId+"'" + ",")
                            .append("\"hotwater\")");
                StringBuilder addElectrocity = new StringBuilder(
                        "INSERT INTO `registrations-tariffs` (id, amount, registrations_id,tariffs_name) ")
                            .append("VALUES(NULL,'" + registration.getElectricity()+ "',")
                            .append("'"+registrationId+"'" + ",")
                            .append("\"electrisity\")");
                StringBuilder addGas = new StringBuilder(
                        "INSERT INTO `registrations-tariffs` (id, amount, registrations_id,tariffs_name) ")
                            .append("VALUES(NULL,'" + registration.getGas()+ "',")
                            .append("'"+registrationId+"'" + ",")
                            .append("\"gas\")");                
                statement.executeUpdate(addColdwater.toString());
                statement.executeUpdate(addHotwater.toString());
                statement.executeUpdate(addElectrocity.toString());
                statement.executeUpdate(addGas.toString());
                
            }
        }catch (SQLException e){e.printStackTrace();}
    }
    
    private String parseDate(Date date){
        StringBuilder dateStr = new StringBuilder();
        dateStr.append(date.getYear()).append("-");
        
        if(date.getMonth() < 10 ) 
            dateStr.append("0");
        
        dateStr.append(date.getMonth()).append("-");
        if(date.getDay() < 10) 
            dateStr.append("0");
        
        dateStr.append(date.getDay());
        return dateStr.toString();
    }  
    
    
}
