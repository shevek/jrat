<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"> 

    <xsl:param name="resource"/>

    <xsl:template match="document">
	 
		<html>			
		
			<head>
				<title><xsl:value-of select="@title"/></title>
				<link rel="stylesheet" href="static/sample.css" type="text/css" />
			</head>
		
			<body>
			
				<div class="header">
				
					<h1 class="header">ShiftOne </h1>
					<h2 class="header">Java Runtime Analysis Toolkit</h2>	
															 
					<a class="header" href="http://sourceforge.net/project/showfiles.php?group_id=41841&amp;package_id=33930">download</a>
					<a class="header" href="d">license</a>
					<a class="header" href="http://sourceforge.net/forum/forum.php?forum_id=136869">forum</a>
					<a class="header" href="http://sourceforge.net/projects/jrat">sourceforge</a>								
					
				</div>
				 
 				<div class="content">
                     <h1><xsl:value-of select="@title"/></h1>
                    <xsl:apply-templates/>
				</div>

                <div class="footer">
                    Copyright Jeff Drost © 2007
                    <br/>

                    <xsl:value-of select="$resource"/>
                </div>
            </body>
			
		</html>
		 
	</xsl:template>


</xsl:stylesheet>