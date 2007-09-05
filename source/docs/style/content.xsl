<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <!--
    <xsl:template match="section">
        <xsl:apply-templates/>
    </xsl:template>
        -->
    <xsl:template match="section">
        <p>
            <xsl:apply-templates/>
        </p>
    </xsl:template>

    <xsl:template match="ul">
        <ul>
            <xsl:apply-templates/>
        </ul>
    </xsl:template>

    <xsl:template match="ol">
        <ol>
            <xsl:apply-templates/>
        </ol>
    </xsl:template>

    <xsl:template match="li">
        <li>
            <xsl:apply-templates/>
        </li>
    </xsl:template>

    <xsl:template match="b">
        <b>
            <xsl:apply-templates/>
        </b>
    </xsl:template>

    <xsl:template match="i">
        <i>
            <xsl:apply-templates/>
        </i>
    </xsl:template>

    <xsl:template match="p">
        <p>
            <xsl:apply-templates/>
        </p>
    </xsl:template>

    <xsl:template match="br">
        <br/>
    </xsl:template>


    <!-- ============================================== -->

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
    <!-- ============================================== -->

    <xsl:template match="//h">
        <h1>
            <xsl:apply-templates/>
        </h1>
    </xsl:template>

    <xsl:template match="//section/h">
        <h2>
            <xsl:apply-templates/>
        </h2>
    </xsl:template>

    <xsl:template match="//section/section/h">
        <h3>
            <xsl:apply-templates/>
        </h3>
    </xsl:template>

    <xsl:template match="//section/section/section/h">
        <h4>
            <xsl:apply-templates/>
        </h4>
    </xsl:template>

    <xsl:template match="//section/section/section/section/h">
        <h5>
            <xsl:apply-templates/>
        </h5>
    </xsl:template>

    <xsl:template match="//section/section/section/section/section/h">
        <h6>
            <xsl:apply-templates/>
        </h6>
    </xsl:template>

    <!-- ============================================== -->

    <xsl:template match="a">
        <a href="{@href}">
            <xsl:apply-templates/>
        </a>
    </xsl:template>

    <xsl:template match="figure">
        <center>
            <img src="{@src}"/>
        </center>
    </xsl:template>

    <xsl:template match="image">
        <img align="{@align}" src="{@src}"/>
    </xsl:template>

    <!-- ============================================== -->

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