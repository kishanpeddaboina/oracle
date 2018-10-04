<?xml version = '1.0' encoding = 'UTF-8'?>
<xsl:stylesheet version="1.0" xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20" xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/" xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:inp3="http://assessor.lacounty.gov/amp/type/common/ResponseHeader/v1.0" xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions" xmlns:bpmo="http://xmlns.oracle.com/bpm/bpmobject/" xmlns:inp1="http://assessor.lacounty.gov/amp/type/common/RealPropertyInfo/v1.0" xmlns:inp2="http://assessor.lacounty.gov/amp/type/common/RequestHeader/v1.0" xmlns:ora="http://schemas.oracle.com/xpath/extension" xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator" xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction" xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc" xmlns:ns0="http://assessor.lacounty.gov/amp/data/ao/ManagePDCR/v1.0" xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue" xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath" xmlns:med="http://schemas.oracle.com/mediator/xpath" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath" xmlns:ns1="http://xmlns.oracle.com/bpm/bpmobject/Data/StringPrimitiveBO" xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk" xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:bpmn="http://schemas.oracle.com/bpm/xpath" xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap" xmlns:nss1="http://schemas.microsoft.com/2003/10/Serialization/" exclude-result-prefixes="xsi xsl inp3 inp1 inp2 ns0 xsd bpmo ns1 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap" xmlns:oracle-xsl-mapper="http://www.oracle.com/xsl/mapper/schemas">
  <oracle-xsl-mapper:schema>
      <!--SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY.-->
      <oracle-xsl-mapper:mapSources>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="oramds:/apps/amp/xsd/ao/ManagePDCR.xsd"/>
            <oracle-xsl-mapper:rootElement name="managePDCRRequest" namespace="http://assessor.lacounty.gov/amp/data/ao/ManagePDCR/v1.0"/>
            <oracle-xsl-mapper:param name="PDCRReqPDO"/>
         </oracle-xsl-mapper:source>
      </oracle-xsl-mapper:mapSources>
      <oracle-xsl-mapper:mapTargets>
         <oracle-xsl-mapper:target type="XSD">
            <oracle-xsl-mapper:schema location="../businessCatalog/Data/StringPrimitiveBO.xsd"/>
            <oracle-xsl-mapper:rootElement name="StringPrimitiveBO" namespace="http://xmlns.oracle.com/bpm/bpmobject/Data/StringPrimitiveBO"/>
         </oracle-xsl-mapper:target>
      </oracle-xsl-mapper:mapTargets>
      <oracle-xsl-mapper:mapShowPrefixes type="true"/>
      <!--GENERATED BY ORACLE XSL MAPPER 12.2.1.2.0(XSLT Build 161003.0739.0018) AT [THU MAR 09 15:05:03 IST 2017].-->
   </oracle-xsl-mapper:schema>
   <!--User Editing allowed BELOW this line - DO NOT DELETE THIS LINE-->
   <xsl:template match="/">
 
   <xsl:variable name="statusChng" select="concat('&lt;br>&lt;br>&lt;b>Status Change:&lt;/b> ',/ns0:managePDCRRequest/ns0:actionType)"/>
    <xsl:variable name="reqstrName" select="/ns0:managePDCRRequest/ns0:requestorInfo/ns0:requestorName"/>

    <xsl:variable name="bldgSize" select="concat(&quot;&lt;br>&lt;br>&lt;b>Building Size:&lt;/b> &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:improvementSizeSqft,&quot; => &quot;,/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:improvementSizeSqft)"/>
    <xsl:variable name="yrBlt" select="concat(&quot;&lt;br>&lt;br>&lt;b>Year Built:&lt;/b>  &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:yearBuilt,&quot; => &quot;,/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:yearBuilt)"/>
    <xsl:variable name="noBed" select="concat(&quot;&lt;br>&lt;br>&lt;b>No. of Bedrooms:&lt;/b>  &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:bedroomCount,&quot; => &quot;,/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:bedroomCount)"/>
    <xsl:variable name="noBath" select="concat(&quot;&lt;br>&lt;br>&lt;b>No. of Bathrooms:&lt;/b>  &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:bathroomCount,&quot; => &quot;,/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:bathroomCount)"/>
    <xsl:variable name="cmnts" select="concat(&quot;&lt;br>&lt;br>&lt;b>Comments:&lt;/b>  &quot;,/ns0:managePDCRRequest/ns0:requestorInfo/ns0:comments)"/>
    <ns1:StringPrimitiveBO>
      <ns1:content1>
      
        <xsl:text disable-output-escaping="no"> &lt;b>Dear </xsl:text>
            <xsl:value-of select="$reqstrName"/> <xsl:text>,&lt;/b> &lt;br>&lt;b> Your Property Data Change Request has been Submitted with the following details&lt;/b>&lt;br>&lt;br></xsl:text>
        <xsl:value-of select="concat(&quot;&lt;b>AIN:&lt;/b> &quot;,/ns0:managePDCRRequest/ns0:ain,&quot;&lt;br>&lt;br>&lt;b>Property Address:&lt;/b> &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:propertyAddress,&quot;&lt;br>&lt;br>&lt;b>Property Type:&lt;/b> Real&quot;,&quot;&lt;br>&lt;br>&lt;b>Requestor:&lt;/b> &quot;,/ns0:managePDCRRequest/ns0:requestorInfo/ns0:requestorName,&quot;&lt;br>&lt;br>&lt;b>Requestor Type:&lt;/b> &quot;,/ns0:managePDCRRequest/ns0:requestorInfo/ns0:requestorType,&quot;&lt;br>&lt;br>&lt;b>Requestor Email Address:&lt;/b> &quot;,/ns0:managePDCRRequest/ns0:requestorInfo/ns0:email,&quot;&lt;br>&lt;br>&lt;b>Reason for request:&lt;/b> &quot;,/ns0:managePDCRRequest/ns0:requestorInfo/ns0:reasonForRequest)"/>
        
        <xsl:choose>
        <xsl:when test="/ns0:managePDCRRequest/ns0:actionType = 'UPDATE'">
          <xsl:text>&lt;br>&lt;br>&lt;b>Status Change:&lt;/b> Update </xsl:text>
        </xsl:when>
        <xsl:when test="/ns0:managePDCRRequest/ns0:actionType = 'NO CHANGE'">
          <xsl:text>&lt;br>&lt;br>&lt;b>Status Change:&lt;/b> No Change </xsl:text>
        </xsl:when>
        <xsl:otherwise>
          <xsl:text>&lt;br>&lt;br>&lt;b>Status Change:&lt;/b> Manual Processing </xsl:text>
        </xsl:otherwise>
        </xsl:choose>
       
       <xsl:choose>
         <xsl:when test="/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:numberOfUnits != ''">
          <xsl:value-of select="concat(&quot;&lt;br>&lt;br>&lt;b>No. of Units:  &lt;/b>changed from  &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:numberOfUnits,&quot; to &quot;,/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:numberOfUnits)"/>
         </xsl:when> 
       <xsl:otherwise>
         <xsl:value-of select="concat(&quot;&lt;br>&lt;br>&lt;b>No. of Units:&lt;/b> &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:numberOfUnits,&quot;  (No change) &quot;)"/>

        </xsl:otherwise>
      </xsl:choose>  
      
      <xsl:choose>
         <xsl:when test="/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:improvementSizeSqft != ''">
          <xsl:value-of select="concat(&quot;&lt;br>&lt;br>&lt;b>Building Size:  &lt;/b>changed from &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:improvementSizeSqft,&quot; to &quot;,/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:improvementSizeSqft)"/>
         </xsl:when> 
       <xsl:otherwise>
         <xsl:value-of select="concat(&quot;&lt;br>&lt;br>&lt;b>Building Size:&lt;/b> &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:improvementSizeSqft,&quot;  (No change) &quot;)"/>
        </xsl:otherwise>
      </xsl:choose>  

     <xsl:choose>
         <xsl:when test="/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:yearBuilt != ''">
          <xsl:value-of select="concat(&quot;&lt;br>&lt;br>&lt;b>Year Built:  &lt;/b>changed from  &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:yearBuilt,&quot; to &quot;,/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:yearBuilt)"/>
         </xsl:when> 
       <xsl:otherwise>
         <xsl:value-of select="concat(&quot;&lt;br>&lt;br>&lt;b>Year Built:&lt;/b>  &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:yearBuilt,&quot;  (No change) &quot;)"/>
        </xsl:otherwise>
      </xsl:choose>  
      
     <xsl:choose>
         <xsl:when test="/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:bedroomCount != ''">
          <xsl:value-of select="concat(&quot;&lt;br>&lt;br>&lt;b>No. of Bedrooms:  &lt;/b>changed from  &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:bedroomCount,&quot; to &quot;,/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:bedroomCount)"/>
         </xsl:when> 
       <xsl:otherwise>
         <xsl:value-of select="concat(&quot;&lt;br>&lt;br>&lt;b>No. of Bedrooms:&lt;/b>  &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:bedroomCount,&quot;  (No change) &quot;)"/>
        </xsl:otherwise>
      </xsl:choose>  
      
      <xsl:choose>
         <xsl:when test="/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:bathroomCount != ''">
          <xsl:value-of select="concat(&quot;&lt;br>&lt;br>&lt;b>No. of Bath rooms:  &lt;/b>changed from  &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:bathroomCount,&quot; to &quot;,/ns0:managePDCRRequest/ns0:userEnteredRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:bathroomCount,$cmnts)"/>
         </xsl:when> 
         <xsl:otherwise>
         <xsl:value-of select="concat(&quot;&lt;br>&lt;br>&lt;b>No. of Bath rooms:&lt;/b>  &quot;,/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:subPartList/inp1:subPart/inp1:bathroomCount,&quot;  (No change) &quot;,$cmnts)"/>
        </xsl:otherwise>
      </xsl:choose> 
        
    
      <xsl:text>&lt;br>&lt;br>&lt;b> Regards, &lt;br> PDCR Processing System. &lt;br>&lt;/b>   </xsl:text>
      
      </ns1:content1>
    </ns1:StringPrimitiveBO>
  </xsl:template>
</xsl:stylesheet>