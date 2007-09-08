<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:template match="definitions">
        <table class="definition">
        <xsl:apply-templates select="definition"/>
        </table>
    </xsl:template>

     <xsl:template match="definition">
        <tr class="definition">
            <td class="definition-key"><xsl:value-of select="@title"/></td>
            <td class="definition-value"><xsl:apply-templates/></td>
        </tr>
    </xsl:template>
    
</xsl:stylesheet>