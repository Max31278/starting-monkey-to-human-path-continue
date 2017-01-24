/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.data.storage;

import PO41.Koval.wdad.data.managers.PreferencesManager;
import PO41.Koval.wdad.utils.PreferencesConstantManager;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author 000
 */
public class DataSourceFactory {
    
    private static PreferencesManager preferencesManager;
    public static javax.sql.DataSource createDataSource()
            throws IOException, SAXException, ParserConfigurationException, ClassNotFoundException {
        preferencesManager = PreferencesManager.getInstance();
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName(preferencesManager.getProperty(PreferencesConstantManager.HOST_NAME));
        dataSource.setPort(Integer.valueOf(preferencesManager.getProperty(PreferencesConstantManager.PORT)));
        dataSource.setDatabaseName(preferencesManager.getProperty(PreferencesConstantManager.DATA_BASE_NAME));
        dataSource.setUser(preferencesManager.getProperty(PreferencesConstantManager.USER_NAME));
        dataSource.setPassword(preferencesManager.getProperty(PreferencesConstantManager.PASSWORD));
        return dataSource;
    }

    public static javax.sql.DataSource createDataSource(String host, int port, String dbName, String user, String password) {
        MysqlDataSource result = new MysqlDataSource();
        result.setServerName(host);
        result.setDatabaseName(dbName);
        result.setPort(port);
        result.setUser(user);
        if(password == null)
            result.setPassword("");
        else result.setPassword(password);
        return result;
    }
}
