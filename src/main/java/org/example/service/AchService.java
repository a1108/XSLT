package org.example.service;

import org.example.model.AchTransaction;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AchService {

    public List<AchTransaction> getAchTransactions() {
        List<AchTransaction> transactions = new ArrayList<>();
        
        try {
            ClassPathResource resource = new ClassPathResource("ach-transactions.xml");
            InputStream inputStream = resource.getInputStream();
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(inputStream);
            
            document.getDocumentElement().normalize();
            
            NodeList transactionNodes = document.getElementsByTagName("transaction");
            
            for (int i = 0; i < transactionNodes.getLength(); i++) {
                Element element = (Element) transactionNodes.item(i);
                
                String transactionId = getTagValue("transactionId", element);
                String originatingDfi = getTagValue("originatingDfi", element);
                String receivingDfi = getTagValue("receivingDfi", element);
                BigDecimal amount = new BigDecimal(getTagValue("amount", element));
                String effectiveEntryDate = getTagValue("effectiveEntryDate", element);
                
                AchTransaction transaction = new AchTransaction(
                    transactionId, originatingDfi, receivingDfi, amount, effectiveEntryDate
                );
                transactions.add(transaction);
            }
            
            inputStream.close();
            
        } catch (Exception e) {
            throw new RuntimeException("Error reading ACH transactions XML file", e);
        }
        
        return transactions;
    }
    
    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }
}
