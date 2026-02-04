package org.example.service;

import org.example.model.AchTransaction;
import org.example.repository.AchTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.List;

@Service
public class AchService {

    private static final Logger logger = LoggerFactory.getLogger(AchService.class);

    private final AchTransactionRepository repository;

    public AchService(AchTransactionRepository repository) {
        this.repository = repository;
    }

    public String convertTransactionsToXml() {
        logger.info("Starting conversion of transactions to XML");
        try {
            logger.debug("Fetching all transactions from repository");
            List<AchTransaction> transactions = repository.findAll();
            logger.info("Retrieved {} transactions from database", transactions.size());
            
            logger.debug("Building XML document structure");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            
            Element root = document.createElement("achTransactions");
            document.appendChild(root);
            
            logger.debug("Converting {} transactions to XML elements", transactions.size());
            for (AchTransaction txn : transactions) {
                Element transaction = document.createElement("transaction");
                
                Element transactionId = document.createElement("transactionId");
                transactionId.setTextContent(txn.getTransactionId());
                transaction.appendChild(transactionId);
                
                Element originatingDfi = document.createElement("originatingDfi");
                originatingDfi.setTextContent(txn.getOriginatingDfi());
                transaction.appendChild(originatingDfi);
                
                Element receivingDfi = document.createElement("receivingDfi");
                receivingDfi.setTextContent(txn.getReceivingDfi());
                transaction.appendChild(receivingDfi);
                
                Element amount = document.createElement("amount");
                amount.setTextContent(txn.getAmount().toString());
                transaction.appendChild(amount);
                
                Element effectiveEntryDate = document.createElement("effectiveEntryDate");
                effectiveEntryDate.setTextContent(txn.getEffectiveEntryDate());
                transaction.appendChild(effectiveEntryDate);
                
                root.appendChild(transaction);
            }
            logger.debug("All transactions converted to XML elements successfully");
            
            logger.debug("Transforming XML document to string");
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            
            String xmlResult = writer.toString();
            logger.info("Successfully converted {} transactions to XML, total size: {} characters", 
                       transactions.size(), xmlResult.length());
            return xmlResult;
            
        } catch (Exception e) {
            logger.error("Error converting transactions to XML", e);
            throw new RuntimeException("Error converting transactions to XML", e);
        }
    }
}
