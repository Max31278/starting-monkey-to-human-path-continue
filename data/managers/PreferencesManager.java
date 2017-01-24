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
import java.util.Properties;
import javax.xml.xpath.*;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 *
 * @author 000
 */
public class PreferencesManager {
    private static PreferencesManager instance;
    private final static String path = "src/PO41/Koval/wdad/resources/configuration/appconfig.xml";
    private Document doc;
    
    private PreferencesManager() throws IOException, ParserConfigurationException, SAXException{
        generateDocument();
    }
    
        public boolean isCreateRegistry() {
        NodeList nodeList = doc.getElementsByTagName("createregistry");
        if (nodeList.item(0).getTextContent().equals("yes"))
            return true;
        return false;
    }
        
    public void setProperty(String key, String value) throws IOException{
        String[] tags = key.split("\\.");
        NodeList node = doc.getElementsByTagName(tags[tags.length-1]);
        node.item(0).setTextContent(value);
        updateDoc();
    }
    
    public String getProperty(String key) throws IOException{
        String[] tags = key.split("\\.");
        NodeList node = doc.getElementsByTagName(tags[tags.length-1]);
        return node.item(0).getTextContent();
    }
    
    public void setProperties(Properties prop)throws IOException{
        for (String key: prop.stringPropertyNames()){
            setProperty(key, prop.getProperty(key));
        }
    }
    
    public Properties getProperties() throws IOException, XPathExpressionException{
        Properties properties = new Properties();
        String key, value;
        XPath xPath = XPathFactory.newInstance().newXPath();
        String expression = "//*[not(*)]";
        
        NodeList node = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);
        for (int i = 0; i < node.getLength(); i++){
            key = getNodePath(node.item(i));
            value = getProperty(key);
            properties.put(key, value);
        }
        return properties;  
    }
    
    public void addBindedObject(String name, String className)throws IOException{
         Element bindedObjectNode = doc.createElement("bindedobject");
         bindedObjectNode.setAttribute("name", name);
         bindedObjectNode.setAttribute("class", className);
         doc.getElementsByTagName("server").item(0).appendChild(bindedObjectNode);
         updateDoc();
    }
    
    public void removeBindedObject(String name) throws IOException {
         NodeList bindedObjectList = doc.getElementsByTagName("bindedobject");
         NamedNodeMap bindedObjectListAttributs;
         for (int i = 0; i < bindedObjectList.getLength(); i++) {
             bindedObjectListAttributs = bindedObjectList.item(i).getAttributes();
             if (bindedObjectListAttributs.getNamedItem("name").getNodeValue().equals(name)) {
                 bindedObjectList.item(i).getParentNode().removeChild(bindedObjectList.item(i));
             }
         }
         updateDoc();
     }
    
    private static String getNodePath(Node node) {
        Node parent = node.getParentNode();
        
        if (parent == null || parent.getNodeName().equals("#document")) {
            return node.getNodeName();
        }
        return getNodePath(parent) + '.' + node.getNodeName();
    }
    private void generateDocument() throws IOException, ParserConfigurationException, SAXException {
        File xmlFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        dbFactory.setIgnoringElementContentWhitespace(true);
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
    
    @Deprecated
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
    
    @Deprecated
    public String getRegistryAddress() {
        NodeList nodeList = doc.getElementsByTagName("registryaddress");
        return nodeList.item(0).getTextContent();
    }

    @Deprecated
    public void setRegistryAddress(String s) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("registryaddress");
        nodeList.item(0).setTextContent(s);
        updateDoc();
    }

    @Deprecated
    public int getRegistryPort() {
        NodeList nodeList = doc.getElementsByTagName("registryport");
        return Integer.parseInt(nodeList.item(0).getTextContent());
    }

    @Deprecated
    public void setRegistryPort(int registryPort) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("registryport");
        nodeList.item(0).setTextContent(String.valueOf(registryPort));
        updateDoc();
    }
    
    @Deprecated
    public String getPolicyPath() {
        NodeList nodeList = doc.getElementsByTagName("policypath");
        return nodeList.item(0).getTextContent();
    }

    @Deprecated
    public void setPolicyPath(String s) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("policypath");
        nodeList.item(0).setTextContent(s);
        updateDoc();
    }

    @Deprecated
    public boolean getUseCodeBaseOnly() {
        NodeList nodeList = doc.getElementsByTagName("usecodebaseonly");
        if (nodeList.item(0).getTextContent().equals("yes")) {
            return true;
        } else {
            return false;
        }
    }

    @Deprecated
    public void setUseCodeBaseOnly(boolean useCodeBaseOnly) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("usecodebaseonly");
        if (useCodeBaseOnly) {
            nodeList.item(0).setTextContent("yes");
        } else {
            nodeList.item(0).setTextContent("no");
        }
        updateDoc();
    }

    @Deprecated
    public String getClassProvider() {
        NodeList nodeList = doc.getElementsByTagName("classprovider");
        return nodeList.item(0).getTextContent();
    }

    @Deprecated
    public void setClassProvider(String classproviderURL) throws IOException {
        NodeList nodeList = doc.getElementsByTagName("classprovider");
        nodeList.item(0).setTextContent(classproviderURL);
        updateDoc();
    }
}
