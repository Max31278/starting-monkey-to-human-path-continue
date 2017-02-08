/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.data.managers;

import PO41.Koval.wdad.data.network.Building;
import PO41.Koval.wdad.data.network.Flat;
import PO41.Koval.wdad.data.network.Registration;
import PO41.Koval.wdad.data.storage.DataSourceFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Set;
import javax.sql.DataSource;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author 000
 */
public class TestJDBC {
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException, SQLException{
        Connection con = null;
        BuildingsJDBC jdbcB;
        
        try{
            DataSource  dataSource = DataSourceFactory.createDataSource();
            con = dataSource.getConnection();
            jdbcB = new BuildingsJDBC();
            Building bild = jdbcB.findBuilding(4);
            System.out.println(jdbcB.saveOrUpdateBuilding(bild));
            System.out.println(jdbcB.deleteBuilding(bild));
            System.out.println(jdbcB.insertBuilding(bild));
            /*bild.setId(1);
            bild.setNumber(15);
            bild.setStreetName("Набережная");
            flat.setArea(123.5);
            flat.setBuilding(jdbc.findBuilding(bild.getId()));
            flat.setId(1);
            flat.setNumber(13);
            flat.setPersonsQuantity(3);
            flat.setRegistrations(registrations);
            bild.setFlats(flats);
            jdbc.saveOrUpdateBuilding(building);*/
            
            
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        finally{
            try{
                if(con!= null) con.close();
            }
            catch(SQLException e){
                e.printStackTrace();
            }
        }
    }
}
