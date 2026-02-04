package org.example.controller;

import org.example.service.AchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

@RestController
public class AchController {

    private static final Logger logger = LoggerFactory.getLogger(AchController.class);

    private final AchService achService;

    public AchController(AchService achService) {
        this.achService = achService;
    }

    @GetMapping(value = "/ach", produces = MediaType.TEXT_HTML_VALUE)
    public String getAchTransactions() {
        logger.info("Received request to fetch ACH transactions");
        try {
            // Get XML from database
            logger.debug("Fetching transactions from database and converting to XML");
            String xmlFromDb = achService.convertTransactionsToXml();
            logger.debug("XML generated successfully from database, length: {} characters", xmlFromDb.length());
            
            // Load XSLT file from classpath
            logger.debug("Loading XSLT transformation file");
            ClassPathResource xslResource = new ClassPathResource("ach-transform.xsl");
            InputStream xslInputStream = xslResource.getInputStream();
            
            // Create transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            StreamSource xslSource = new StreamSource(xslInputStream);
            Transformer transformer = factory.newTransformer(xslSource);
            
            // Transform XML to HTML
            logger.debug("Applying XSLT transformation to convert XML to HTML");
            StreamSource xmlSource = new StreamSource(new StringReader(xmlFromDb));
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            
            transformer.transform(xmlSource, result);
            logger.info("Successfully transformed ACH transactions to HTML, size: {} characters", writer.toString().length());
            
            // Close stream
            xslInputStream.close();
            
            return writer.toString();
            
        } catch (Exception e) {
            logger.error("Error rendering ACH transactions", e);
            return "<html><body><h1>Error rendering ACH transactions</h1><p>" + 
                   e.getMessage() + "</p></body></html>";
        }
    }

    @GetMapping(value = "/home", produces = MediaType.TEXT_HTML_VALUE)
    public String loadHomePage() throws IOException {
        logger.info("Loading home page");
        ClassPathResource htmlPath = new ClassPathResource("templates/home.html");
        String content = StreamUtils.copyToString(htmlPath.getInputStream(), StandardCharsets.UTF_8);
        logger.debug("Home page loaded successfully");
        return content;
    }

}
