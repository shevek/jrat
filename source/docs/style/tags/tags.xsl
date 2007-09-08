<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:include href="common.xsl"/>
    <xsl:include href="definitions.xsl"/>
    
    <xsl:template match="code">
        <table border="1" cellpadding="5" cellspaciong="0" width="100%">
            <tr>
                <td bgcolor="#ddddff">
                    <font color="#0000ff">
                        <pre>
                            <xsl:apply-templates/>
                        </pre>
                    </font>
                </td>
            </tr>
        </table>
    </xsl:template>

    <xsl:template match="terminal">
        <table border="1" cellpadding="5" cellspaciong="0" width="100%">
            <tr>
                <td bgcolor="black">
                    <font color="white">
                        <pre>
                            <xsl:apply-templates/>
                        </pre>
                    </font>
                </td>
            </tr>
        </table>
    </xsl:template>

    <xsl:template match="warning">
        <table border="1" cellpadding="5" cellspaciong="0" width="100%">
            <tr>
                <td>
                    <pre>
                        <xsl:apply-templates/>
                    </pre>
                </td>
            </tr>
        </table>
    </xsl:template>

    <xsl:template match="note">
        <table border="1" cellpadding="5" cellspaciong="0" width="100%">
            <tr>
                <td>
                    <pre>
                        <xsl:apply-templates/>
                    </pre>
                </td>
            </tr>
        </table>
    </xsl:template>

    <!-- FIX THIS -->
    <xsl:template match="indent">
        <table border="1" cellpadding="5" cellspaciong="0" width="100%">
            <tr>
                <td>
                    <pre>
                        <xsl:apply-templates/>
                    </pre>
                </td>
            </tr>
        </table>
    </xsl:template>

    <xsl:template match="box">
        <table border="1" cellpadding="5" cellspaciong="0" width="100%">
            <tr>
                <td>
                    <pre>
                        <xsl:apply-templates/>
                    </pre>
                </td>
            </tr>
        </table>
    </xsl:template>
</xsl:stylesheet>