<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"> 

    <xsl:param name="p_resourcePath"/>
    <xsl:param name="p_generatedDate"/>
    <xsl:param name="p_googleAnalyticsAccount"/>


    <xsl:template match="document">
	 
		<html>			
		
			<head>
				<title><xsl:value-of select="@title"/></title>
				<link rel="stylesheet" href="{$p_resourcePath}/shiftone.css" type="text/css" />
			</head>
		
			<body>

				<div class="header">
				
					<h1 class="header">ShiftOne </h1>
					<h2 class="header">
                        <a class="home" href="index.html">Java Runtime Analysis Toolkit</a>                        
                    </h2>
															 
					<a class="header" href="http://sourceforge.net/project/showfiles.php?group_id=41841&amp;package_id=33930">download</a>
					<a class="header" href="http://sourceforge.net/forum/forum.php?forum_id=136869">forum</a>
                    <a class="header" href="http://jrat.wiki.sourceforge.net">wiki</a>
                    <a class="header" href="http://sourceforge.net/projects/jrat">sf.net</a>
                    <a class="header" href="api">api</a>
                    <a class="header" href="lesser.html">license</a>
				</div>
				 
 				<div class="content">
                     <h1><xsl:value-of select="@title"/></h1>
                     <xsl:apply-templates/>
                </div>

                <div class="footer">
                    Copyright © 2007
                    <br/>
                    Generated <xsl:value-of select="$p_generatedDate"/>
                </div>
            </body>

            <script src="http://www.google-analytics.com/urchin.js" type="text/javascript">
            </script>
            <script type="text/javascript">
            _uacct = "<xsl:value-of select="$p_googleAnalyticsAccount"/>";
            urchinTracker();
            </script>

            <a href="http://sourceforge.net"><img border="0" height="37" width="125" src="http://sourceforge.net/sflogo.php?group_id=41841&amp;type=3"/></a>
        </html>
		 
	</xsl:template>


</xsl:stylesheet>