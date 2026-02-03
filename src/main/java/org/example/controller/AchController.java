package org.example.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.InputStream;
import java.io.StringWriter;

@RestController
public class AchController {

    @GetMapping(value = "/ach", produces = MediaType.TEXT_HTML_VALUE)
    public String getAchTransactions() {
        try {
            // Load XML and XSLT files from classpath
            ClassPathResource xmlResource = new ClassPathResource("ach-transactions.xml");
            ClassPathResource xslResource = new ClassPathResource("ach-transform.xsl");
            
            InputStream xmlInputStream = xmlResource.getInputStream();
            InputStream xslInputStream = xslResource.getInputStream();
            
            // Create transformer
            TransformerFactory factory = TransformerFactory.newInstance();
            StreamSource xslSource = new StreamSource(xslInputStream);
            Transformer transformer = factory.newTransformer(xslSource);
            
            // Transform XML to HTML
            StreamSource xmlSource = new StreamSource(xmlInputStream);
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            
            transformer.transform(xmlSource, result);
            
            // Close streams
            xmlInputStream.close();
            xslInputStream.close();
            
            return writer.toString();
            
        } catch (Exception e) {
            return "<html><body><h1>Error rendering ACH transactions</h1><p>" + 
                   e.getMessage() + "</p></body></html>";
        }
    }
}
