<?xml version = '1.0' encoding = 'UTF-8'?>
<xsl:stylesheet version="1.0" xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/" xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20" xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction" xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable" xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:inp2="http://assessor.lacounty.gov/amp/type/common/ResponseHeader/v1.0" xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue" xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:med="http://schemas.oracle.com/mediator/xpath" xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath" xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions" xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk" xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions" xmlns:inp3="http://assessor.lacounty.gov/amp/type/bvm/BaseYearEvent/v1.0" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:inp1="http://assessor.lacounty.gov/amp/type/common/RequestHeader/v1.0" xmlns:bpmn="http://schemas.oracle.com/bpm/xpath" xmlns:ora="http://schemas.oracle.com/xpath/extension" xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator" xmlns:tns="http://assessor.lacounty.gov/amp/data/bvm/ManageBYE/v1.0" xmlns:ns0="http://assessor.lacounty.gov/amp/data/ao/UpdateProcessStatus/v1.0" xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap" exclude-result-prefixes="xsi xsl inp2 inp3 xsd inp1 tns ns0 bpws xp20 mhdr bpel oraext dvm hwf med ids bpm xdk xref bpmn ora socket ldap" xmlns:oracle-xsl-mapper="http://www.oracle.com/xsl/mapper/schemas">
  <oracle-xsl-mapper:schema>
      <!--SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY.-->
      <oracle-xsl-mapper:mapSources>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="oramds:/apps/amp/xsd/bvm/ManageBYE.xsd"/>
            <oracle-xsl-mapper:rootElement name="manageBYERequest" namespace="http://assessor.lacounty.gov/amp/data/bvm/ManageBYE/v1.0"/>
            <oracle-xsl-mapper:param name="ManageBYE_ReqPDO"/>
         </oracle-xsl-mapper:source>
      </oracle-xsl-mapper:mapSources>
      <oracle-xsl-mapper:mapTargets>
         <oracle-xsl-mapper:target type="XSD">
            <oracle-xsl-mapper:schema location="oramds:/apps/amp/xsd/ao/UpdateProcessStatus.xsd"/>
            <oracle-xsl-mapper:rootElement name="updateProcessStatusRequest" namespace="http://assessor.lacounty.gov/amp/data/ao/UpdateProcessStatus/v1.0"/>
         </oracle-xsl-mapper:target>
      </oracle-xsl-mapper:mapTargets>
      <oracle-xsl-mapper:mapShowPrefixes type="true"/>
      <!--GENERATED BY ORACLE XSL MAPPER 12.2.1.2.0(XSLT Build 161003.0739.0018) AT [THU MAR 09 12:18:03 IST 2017].-->
   </oracle-xsl-mapper:schema>
   <!--User Editing allowed BELOW this line - DO NOT DELETE THIS LINE-->
   <xsl:template match="/">
    <ns0:updateProcessStatusRequest>
      <ns0:header>
        <inp1:applicationId>
          <xsl:value-of select="/tns:manageBYERequest/tns:header/inp1:applicationId"/>
        </inp1:applicationId>
        <inp1:requestId>
          <xsl:value-of select="/tns:manageBYERequest/tns:header/inp1:requestId"/>
        </inp1:requestId>
      </ns0:header>
      <ns0:ain>
        <xsl:value-of select="/tns:manageBYERequest/tns:ain"/>
      </ns0:ain>
      <ns0:aoid>
        <xsl:value-of select="/tns:manageBYERequest/tns:aoid"/>
      </ns0:aoid>
      <ns0:userId>
        <xsl:value-of select="/tns:manageBYERequest/tns:userId"/>
      </ns0:userId>
      <ns0:processType>
        <xsl:value-of select="string(/tns:manageBYERequest/tns:processType)"/>
      </ns0:processType>
      <ns0:initiatedBy>
        <xsl:value-of select="/tns:manageBYERequest/tns:userId"/>
      </ns0:initiatedBy>
      <ns0:initiatedDate>
        <xsl:value-of select="xp20:current-dateTime()"/>
      </ns0:initiatedDate>
      <ns0:lockStatus>
        <xsl:text disable-output-escaping="no">LOCKED</xsl:text>
      </ns0:lockStatus>
      <ns0:lockStatusDate>
        <xsl:value-of select="xp20:current-dateTime()"/>
      </ns0:lockStatusDate>
      <ns0:processAssignment>
        <xsl:value-of select="string(/tns:manageBYERequest/tns:processType)"/>
      </ns0:processAssignment>
      <ns0:processStatus>
        <xsl:text disable-output-escaping="no">SUBMITTED</xsl:text>
      </ns0:processStatus>
    </ns0:updateProcessStatusRequest>
  </xsl:template>
</xsl:stylesheet>