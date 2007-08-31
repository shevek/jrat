<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

	<!-- xsl:output method="html"		indent="yes"/-->
	
	<xsl:output method="xml" 
		doctype-system="http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd" 
    doctype-public="-//W3C//DTD XHTML 1.0 Transitional//EN"/> 
        

	<!--xsl:param name="p_date"/-->

	<xsl:include href="border.xsl"/>
	<xsl:include href="content.xsl"/>
	
</xsl:stylesheet>