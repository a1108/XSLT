<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html" encoding="UTF-8" indent="yes"/>
    
    <xsl:template match="/">
        <html>
            <head>
                <title>ACH Transactions</title>
                <style>
                    body { 
                        font-family: Arial, sans-serif; 
                        margin: 40px; 
                        background-color: #f5f5f5; 
                    }
                    h1 { 
                        color: #333; 
                    }
                    table { 
                        border-collapse: collapse; 
                        width: 100%; 
                        background-color: white; 
                        box-shadow: 0 2px 4px rgba(0,0,0,0.1); 
                    }
                    th, td { 
                        border: 1px solid #ddd; 
                        padding: 12px; 
                        text-align: left; 
                    }
                    th { 
                        background-color: #4CAF50; 
                        color: white; 
                    }
                    tr:nth-child(even) { 
                        background-color: #f9f9f9; 
                    }
                    tr:hover { 
                        background-color: #f5f5f5; 
                    }
                    .high-value { 
                        color: #d32f2f; 
                        font-weight: bold; 
                    }
                    .standard { 
                        color: #388e3c; 
                    }
                </style>
            </head>
            <body>
                <h1>ACH Transactions</h1>
                <table>
                    <thead>
                        <tr>
                            <th>Transaction ID</th>
                            <th>Originating DFI</th>
                            <th>Receiving DFI</th>
                            <th>Amount</th>
                            <th>Effective Entry Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:apply-templates select="achTransactions/transaction"/>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>
    
    <xsl:template match="transaction">
        <tr>
            <td><xsl:value-of select="transactionId"/></td>
            <td><xsl:value-of select="originatingDfi"/></td>
            <td><xsl:value-of select="receivingDfi"/></td>
            <td>$<xsl:value-of select="format-number(amount, '#,##0.00')"/></td>
            <td><xsl:value-of select="effectiveEntryDate"/></td>
            <td>
                <xsl:choose>
                    <xsl:when test="number(amount) &gt; 25000">
                        <span class="high-value">HIGH VALUE / REQUIRES APPROVAL</span>
                    </xsl:when>
                    <xsl:otherwise>
                        <span class="standard">STANDARD / NORMAL</span>
                    </xsl:otherwise>
                </xsl:choose>
            </td>
        </tr>
    </xsl:template>
    
</xsl:stylesheet>
