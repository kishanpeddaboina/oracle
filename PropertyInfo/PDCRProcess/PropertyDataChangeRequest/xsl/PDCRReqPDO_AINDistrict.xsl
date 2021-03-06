<?xml version = '1.0' encoding = 'UTF-8'?>
<xsl:stylesheet version="1.0" xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20" xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/" xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ns2="http://xmlns.oracle.com/bpm/bpmobject/Data/AINAttributesBO" xmlns:inp3="http://assessor.lacounty.gov/amp/type/common/ResponseHeader/v1.0" xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions" xmlns:bpmo="http://xmlns.oracle.com/bpm/bpmobject/" xmlns:inp1="http://assessor.lacounty.gov/amp/type/common/RealPropertyInfo/v1.0" xmlns:inp2="http://assessor.lacounty.gov/amp/type/common/RequestHeader/v1.0" xmlns:ora="http://schemas.oracle.com/xpath/extension" xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator" xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction" xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc" xmlns:ns0="http://assessor.lacounty.gov/amp/data/ao/ManagePDCR/v1.0" xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue" xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath" xmlns:med="http://schemas.oracle.com/mediator/xpath" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath" xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk" xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:bpmn="http://schemas.oracle.com/bpm/xpath" xmlns:ns1="http://xmlns.oracle.com/bpm/bpmobject/Data/InitiatorInfoBO" xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap" exclude-result-prefixes="xsi xsl inp3 bpmo inp1 inp2 ns0 xsd ns1 ns2 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap" xmlns:oracle-xsl-mapper="http://www.oracle.com/xsl/mapper/schemas">
  <oracle-xsl-mapper:schema>
      <!--SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY.-->
      <oracle-xsl-mapper:mapSources>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="oramds:/apps/amp/xsd/ao/ManagePDCR.xsd"/>
            <oracle-xsl-mapper:rootElement name="managePDCRRequest" namespace="http://assessor.lacounty.gov/amp/data/ao/ManagePDCR/v1.0"/>
            <oracle-xsl-mapper:param name="PDCRReqPDO"/>
         </oracle-xsl-mapper:source>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="../businessCatalog/Data/InitiatorInfoBO.xsd"/>
            <oracle-xsl-mapper:rootElement name="InitiatorInfoBO" namespace="http://xmlns.oracle.com/bpm/bpmobject/Data/InitiatorInfoBO"/>
            <oracle-xsl-mapper:param name="InitiatorInfoPDO"/>
         </oracle-xsl-mapper:source>
      </oracle-xsl-mapper:mapSources>
      <oracle-xsl-mapper:mapTargets>
         <oracle-xsl-mapper:target type="XSD">
            <oracle-xsl-mapper:schema location="../businessCatalog/Data/AINAttributesBO.xsd"/>
            <oracle-xsl-mapper:rootElement name="AINAttributesBO" namespace="http://xmlns.oracle.com/bpm/bpmobject/Data/AINAttributesBO"/>
         </oracle-xsl-mapper:target>
      </oracle-xsl-mapper:mapTargets>
      <oracle-xsl-mapper:mapShowPrefixes type="true"/>
      <!--GENERATED BY ORACLE XSL MAPPER 12.2.1.2.0(XSLT Build 161003.0739.0018) AT [THU MAR 09 15:05:03 IST 2017].-->
   </oracle-xsl-mapper:schema>
   <!--User Editing allowed BELOW this line - DO NOT DELETE THIS LINE-->
   <xsl:param name="InitiatorInfoPDO"/>
  <xsl:template match="/">
    <ns2:AINAttributesBO>
      <xsl:choose>
        <xsl:when test="contains(/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:district,&quot;North&quot;)">
          <ns2:ainDistrict>
            <xsl:text disable-output-escaping="no">North</xsl:text>
          </ns2:ainDistrict>
        </xsl:when>
        <xsl:when test="contains(/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:district,&quot;South&quot;)">
          <ns2:ainDistrict>
            <xsl:text disable-output-escaping="no">South</xsl:text>
          </ns2:ainDistrict>
        </xsl:when>
        <xsl:when test="contains(/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:district,&quot;East&quot;)">
          <ns2:ainDistrict>
            <xsl:text disable-output-escaping="no">East</xsl:text>
          </ns2:ainDistrict>
        </xsl:when>
        <xsl:when test="contains(/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:district,&quot;West&quot;)">
          <ns2:ainDistrict>
            <xsl:text disable-output-escaping="no">West</xsl:text>
          </ns2:ainDistrict>
        </xsl:when>
        <xsl:when test="contains(/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:district,&quot;Lancaster&quot;)">
          <ns2:ainDistrict>
            <xsl:text disable-output-escaping="no">North</xsl:text>
          </ns2:ainDistrict>
        </xsl:when>
        <xsl:when test="contains(/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:district,&quot;Hall of Administration&quot;)">
          <ns2:ainDistrict>
            <xsl:text disable-output-escaping="no">HOA</xsl:text>
          </ns2:ainDistrict>
        </xsl:when>
        <xsl:when test="contains(/ns0:managePDCRRequest/ns0:originalRealPropertyInfo/inp1:district,&quot;Unknown&quot;)">
          <ns2:ainDistrict>
            <xsl:text disable-output-escaping="no">HOA</xsl:text>
          </ns2:ainDistrict>
        </xsl:when>
        <xsl:otherwise>
          <ns2:ainDistrict>
            <xsl:if test="$InitiatorInfoPDO/ns1:InitiatorInfoBO/ns1:initiatorDistrict/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="$InitiatorInfoPDO/ns1:InitiatorInfoBO/ns1:initiatorDistrict/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="$InitiatorInfoPDO/ns1:InitiatorInfoBO/ns1:initiatorDistrict"/>
          </ns2:ainDistrict>
        </xsl:otherwise>
      </xsl:choose>
    </ns2:AINAttributesBO>
  </xsl:template>
</xsl:stylesheet>