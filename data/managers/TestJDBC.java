/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.data.managers;

import PO41.Koval.wdad.data.storage.DataSourceFactory;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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
        JDBCDataManager jdbc;
        
        try{
            DataSource  dataSource = DataSourceFactory.createDataSource();
            con = dataSource.getConnection();
            jdbc = new JDBCDataManager(dataSource, con);
            jdbc.setTariff("gas", 150);
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
