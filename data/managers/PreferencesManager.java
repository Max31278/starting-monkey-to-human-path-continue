/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PO41.Koval.wdad.data.managers;

import java.io.File;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 *
 * @author 000
 */
public class PreferencesManager {
    private static PreferencesManager instance;
    private final static String path = "src/PO41/Koval/wdad/resources/configurations/appconfig.xml";
    private Document doc;
    
    private PreferencesManager() throws IOException, ParserConfigurationException, SAXException{
        generateDocument();
    }
    
        public boolean isCreateRegistry() {
        NodeList nodeList = doc.getElementsByTagName("createregistry");
        if (nodeList.item(0).getTextContent().equals("yes")) {
            return true;
        } 
        else {
            return false;
        }
    }
    
    private void generateDocument() throws IOException, ParserConfigurationException, SAXException {
        File xmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        doc = dBuilder.parse(xmlFile);
    }
        
    private void updateDoc() throws IOException {
        DOMImplementationLS domImplementationLS =
                (DOMImplementationLS) doc.getImplementation().getFeature("LS", "3.0");
        LSOutput lsOutput = domImplementationLS.createLSOutput();
        FileOutputStream outputStream = new FileOutputStream(path);
        lsOutput.setByteStream(outputStream);
        LSSerializer lsSerializer = domImplementationLS.createLSSerializer();
        lsSerializer.write(doc, lsOutput);
        outputStream.close();
    }
    
    public static PreferencesManager getInstance() throws ParserConfigurationException, IOException, SAXException {
        if (instance == null) {
            instance = new PreferencesManager();
        }
        return instance;
    }
    
    public void setCreateRegistry(boolean createRegistry) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("createregistry");
        if (createRegistry) {
            nodeList.item(0).setTextContent("yes");
        } 
        else {
            nodeList.item(0).setTextContent("no");
        }
        updateDoc();
    }
    
    public String getRegistryAddress() {
        NodeList nodeList = doc.getElementsByTagName("registryaddress");
        return nodeList.item(0).getTextContent();
    }

    public void setRegistryAddress(String s) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("registryaddress");
        nodeList.item(0).setTextContent(s);
        updateDoc();
    }

    public int getRegistryPort() {
        NodeList nodeList = doc.getElementsByTagName("registryport");
        return Integer.parseInt(nodeList.item(0).getTextContent());
    }

    public void setRegistryPort(int registryPort) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("registryport");
        nodeList.item(0).setTextContent(String.valueOf(registryPort));
        updateDoc();
    }
    
    public String getPolicyPath() {
        NodeList nodeList = doc.getElementsByTagName("policypath");
        return nodeList.item(0).getTextContent();
    }

    public void setPolicyPath(String s) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("policypath");
        nodeList.item(0).setTextContent(s);
        updateDoc();
    }

    public boolean getUseCodeBaseOnly() {
        NodeList nodeList = doc.getElementsByTagName("usecodebaseonly");
        if (nodeList.item(0).getTextContent().equals("yes")) {
            return true;
        } else {
            return false;
        }
    }

    public void setUseCodeBaseOnly(boolean useCodeBaseOnly) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("usecodebaseonly");
        if (useCodeBaseOnly) {
            nodeList.item(0).setTextContent("yes");
        } else {
            nodeList.item(0).setTextContent("no");
        }
        updateDoc();
    }

    public String getClassProvider() {
        NodeList nodeList = doc.getElementsByTagName("classprovider");
        return nodeList.item(0).getTextContent();
    }

    public void setClassProvider(String classproviderURL) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("classprovider");
        nodeList.item(0).setTextContent(classproviderURL);
        updateDoc();
    }
}
