<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:include href="common.xsl"/>
    <xsl:include href="definitions.xsl"/>
  
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
</xsl:stylesheet>