<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:include href="common.xsl"/> 

    <xsl:template match="code">
        <pre class="box">
            <xsl:apply-templates/>
        </pre>
    </xsl:template>

    <xsl:template match="terminal">
        <pre class="box terminal">
            <xsl:apply-templates/>
        </pre>
    </xsl:template>

    <xsl:template match="warning">
        <div class="box warning">
            <xsl:apply-templates/>
        </div>
    </xsl:template>

    <xsl:template match="note">
        <div class="box note">
            <xsl:apply-templates/>
        </div>
    </xsl:template>

    <xsl:template match="indent">
        <div class="indent">
            <xsl:apply-templates/>
        </div>
    </xsl:template>

    <xsl:template match="box">
        <div class="box">
            <xsl:apply-templates/>
        </div>
    </xsl:template>

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