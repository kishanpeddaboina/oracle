<?xml version = '1.0' encoding = 'UTF-8'?>
<xsl:stylesheet version="1.0" xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20" xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/" xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ns7="http://assessor.lacounty.gov/amp/data/ao/WorkflowAssignment/v1.0" xmlns:ns9="http://xmlns.oracle.com/bpel/workflow/common" xmlns:inp2="http://assessor.lacounty.gov/amp/type/common/ResponseHeader/v1.0" xmlns:evidence="http://xmlns.oracle.com/bpel/workflow/TaskEvidenceService" xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions" xmlns:bpmo="http://xmlns.oracle.com/bpm/bpmobject/" xmlns:jaxb="http://java.sun.com/xml/ns/jaxb" xmlns:ns10="http://assessor.lacounty.gov/amp/data/ao/LogWorkFlowDetails/v1.0" xmlns:inp1="http://assessor.lacounty.gov/amp/type/common/RequestHeader/v1.0" xmlns:ora="http://schemas.oracle.com/xpath/extension" xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator" xmlns:ns2="http://xmlns.oracle.com/bpm/bpmobject/Data/SortBYEBO" xmlns:ns3="http://xmlns.oracle.com/bpm/bpmobject/Data/AssignToUserOrGroupBO" xmlns:tns="http://assessor.lacounty.gov/amp/data/bvm/VerifyBYE/v1.0" xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction" xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc" xmlns:ns1="http://assessor.lacounty.gov/amp/data/bvm/RetrieveBYE/v1.0" xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue" xmlns:ns0="http://xmlns.oracle.com/bpm/bpmobject/Data/RetriveBYEDetails" xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath" xmlns:med="http://schemas.oracle.com/mediator/xpath" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath" xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk" xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions" xmlns:inp3="http://assessor.lacounty.gov/amp/type/bvm/BaseYearEvent/v1.0" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:ns8="http://xmlns.oracle.com/bpel/workflow/task" xmlns:bpmn="http://schemas.oracle.com/bpm/xpath" xmlns:ns6="http://assessor.lacounty.gov/amp/data/ao/UpsertWorkflowAssignment/v1.0" xmlns:ns4="http://assessor.lacounty.gov/amp/data/bvm/ManageBYE/v1.0" xmlns:ns5="http://xmlns.oracle.com/bpm/bpmobject/Data/InitiatorInfoBO" xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap" exclude-result-prefixes="xsi xsl ns9 inp2 evidence bpmo jaxb inp1 ns2 ns3 tns ns1 ns0 inp3 xsd ns8 ns4 ns5 ns10 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap" xmlns:oracle-xsl-mapper="http://www.oracle.com/xsl/mapper/schemas">
  <oracle-xsl-mapper:schema>
      <!--SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY.-->
      <oracle-xsl-mapper:mapSources>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="../businessCatalog/Data/RetriveBYEDetails.xsd"/>
            <oracle-xsl-mapper:rootElement name="RetriveBYEDetails" namespace="http://xmlns.oracle.com/bpm/bpmobject/Data/RetriveBYEDetails"/>
            <oracle-xsl-mapper:param name="RetriveBYE_PDO"/>
         </oracle-xsl-mapper:source>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="oramds:/soa/shared/workflow/WorkflowTask.xsd"/>
            <oracle-xsl-mapper:rootElement name="task" namespace="http://xmlns.oracle.com/bpel/workflow/task"/>
            <oracle-xsl-mapper:param name="execDataWFLogPDO"/>
         </oracle-xsl-mapper:source>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="oramds:/apps/amp/xsd/bvm/VerifyBYE.xsd"/>
            <oracle-xsl-mapper:rootElement name="verifyBYERequest" namespace="http://assessor.lacounty.gov/amp/data/bvm/VerifyBYE/v1.0"/>
            <oracle-xsl-mapper:param name="VerifyBYE_ReqPDO"/>
         </oracle-xsl-mapper:source>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="../businessCatalog/Data/InitiatorInfoBO.xsd"/>
            <oracle-xsl-mapper:rootElement name="InitiatorInfoBO" namespace="http://xmlns.oracle.com/bpm/bpmobject/Data/InitiatorInfoBO"/>
            <oracle-xsl-mapper:param name="InitiatorInfoPDO"/>
         </oracle-xsl-mapper:source>
      </oracle-xsl-mapper:mapSources>
      <oracle-xsl-mapper:mapTargets>
         <oracle-xsl-mapper:target type="XSD">
            <oracle-xsl-mapper:schema location="oramds:/apps/amp/xsd/ao/LogWorkFlowDetails.xsd"/>
            <oracle-xsl-mapper:rootElement name="logWorkFlowDetailsRequest" namespace="http://assessor.lacounty.gov/amp/data/ao/LogWorkFlowDetails/v1.0"/>
         </oracle-xsl-mapper:target>
      </oracle-xsl-mapper:mapTargets>
      <oracle-xsl-mapper:mapShowPrefixes type="true"/>
      <!--GENERATED BY ORACLE XSL MAPPER 12.2.1.2.0(XSLT Build 161003.0739.0018) AT [THU MAR 09 12:18:09 IST 2017].-->
   </oracle-xsl-mapper:schema>
   <!--User Editing allowed BELOW this line - DO NOT DELETE THIS LINE-->
   <xsl:param name="execDataWFLogPDO"/>
  <xsl:param name="VerifyBYE_ReqPDO"/>
  <xsl:param name="InitiatorInfoPDO"/>
  <xsl:template match="/">
    <ns10:logWorkFlowDetailsRequest>
      <ns10:header>
        <xsl:if test="$VerifyBYE_ReqPDO/tns:verifyBYERequest/tns:header/inp1:applicationId">
          <inp1:applicationId>
            <xsl:value-of select="$VerifyBYE_ReqPDO/tns:verifyBYERequest/tns:header/inp1:applicationId"/>
          </inp1:applicationId>
        </xsl:if>
        <xsl:if test="$VerifyBYE_ReqPDO/tns:verifyBYERequest/tns:header/inp1:requestId">
          <inp1:requestId>
            <xsl:value-of select="$VerifyBYE_ReqPDO/tns:verifyBYERequest/tns:header/inp1:requestId"/>
          </inp1:requestId>
        </xsl:if>
      </ns10:header>
      <ns10:ain>
        <xsl:value-of select="$VerifyBYE_ReqPDO/tns:verifyBYERequest/tns:ain"/>
      </ns10:ain>
      <ns10:aoid>
        <xsl:value-of select="$VerifyBYE_ReqPDO/tns:verifyBYERequest/tns:aoid"/>
      </ns10:aoid>
      <ns10:userId>
        <xsl:value-of select="$VerifyBYE_ReqPDO/tns:verifyBYERequest/tns:userId"/>
      </ns10:userId>
      <ns10:processType>
        <xsl:value-of select="$VerifyBYE_ReqPDO/tns:verifyBYERequest/tns:processType"/>
      </ns10:processType>
      <ns10:initiatedBy>
        <xsl:value-of select="$InitiatorInfoPDO/ns5:InitiatorInfoBO/ns5:initiatorUserId"/>
      </ns10:initiatedBy>
      <ns10:initiatedDate>
        <xsl:value-of select="$InitiatorInfoPDO/ns5:InitiatorInfoBO/ns5:initiatedDate"/>
      </ns10:initiatedDate>
      <ns10:processStatus>
        <xsl:text disable-output-escaping="no">CANCELLED</xsl:text>
      </ns10:processStatus>
      <ns10:processName>
        <xsl:value-of select="$InitiatorInfoPDO/ns5:InitiatorInfoBO/ns5:processName"/>
      </ns10:processName>
      <ns10:processInstanceId>
        <xsl:value-of select="$InitiatorInfoPDO/ns5:InitiatorInfoBO/ns5:processInstanceId"/>
      </ns10:processInstanceId>
      <ns10:ecid>
        <xsl:value-of select="$InitiatorInfoPDO/ns5:InitiatorInfoBO/ns5:ecid"/>
      </ns10:ecid>
      <ns10:district>
        <xsl:value-of select="/ns0:RetriveBYEDetails/ns1:RetrieveBYEDetails/ns1:BYEDetailsOriginal/ns1:districtName"/>
      </ns10:district>
      <ns10:region>
        <xsl:value-of select="/ns0:RetriveBYEDetails/ns1:RetrieveBYEDetails/ns1:BYEDetailsOriginal/ns1:region"/>
      </ns10:region>
      <ns10:processStartTime>
        <xsl:value-of select="$InitiatorInfoPDO/ns5:InitiatorInfoBO/ns5:initiatedDate"/>
      </ns10:processStartTime>
      <ns10:processEndTime>
        <xsl:value-of select="xp20:current-dateTime()"/>
      </ns10:processEndTime>
      <xsl:copy-of select="/ns0:RetriveBYEDetails/ns1:RetrieveBYEDetails/ns1:BYEDetailsOriginal">
        <?oracle-xsl-mapper-position ns10:processPayload?>
      </xsl:copy-of>
      <ns10:source>
        <xsl:value-of select="$VerifyBYE_ReqPDO/tns:verifyBYERequest/tns:Source"/>
      </ns10:source>
      <ns10:owner>
        <xsl:value-of select="$VerifyBYE_ReqPDO/tns:verifyBYERequest/tns:ownerId"/>
      </ns10:owner>
      <ns10:outcome>
        <xsl:text disable-output-escaping="no">FYI</xsl:text>
      </ns10:outcome>
      <ns10:state>
        <xsl:text disable-output-escaping="no">COMPLETED</xsl:text>
      </ns10:state>
    </ns10:logWorkFlowDetailsRequest>
  </xsl:template>
</xsl:stylesheet>