<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:param name="p_resourcePath"/>
        
    <xsl:output method="html" indent="yes"/>

    <xsl:include href="content.xsl"/>

    <xsl:template match="document">
        <html>
            <body>
                <h1>
                    <xsl:value-of select="@title"/>
                </h1>
                <xsl:apply-templates/>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>