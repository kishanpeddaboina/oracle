<?xml version = '1.0' encoding = 'UTF-8'?>
<xsl:stylesheet version="1.0" xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20" xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/" xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ns1="http://xmlns.oracle.com/bpel/workflow/common" xmlns:inp2="http://assessor.lacounty.gov/amp/type/common/ResponseHeader/v1.0" xmlns:evidence="http://xmlns.oracle.com/bpel/workflow/TaskEvidenceService" xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions" xmlns:bpmo="http://xmlns.oracle.com/bpm/bpmobject/" xmlns:jaxb="http://java.sun.com/xml/ns/jaxb" xmlns:ns3="http://assessor.lacounty.gov/amp/data/ao/LogWorkFlowDetails/v1.0" xmlns:inp1="http://assessor.lacounty.gov/amp/type/common/RequestHeader/v1.0" xmlns:ora="http://schemas.oracle.com/xpath/extension" xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator" xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction" xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc" xmlns:ns2="http://xmlns.oracle.com/bpm/bpmobject/Data/ErrorBO" xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue" xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath" xmlns:med="http://schemas.oracle.com/mediator/xpath" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath" xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk" xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions" xmlns:inp3="http://assessor.lacounty.gov/amp/type/bvm/BaseYearEvent/v1.0" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:bpmn="http://schemas.oracle.com/bpm/xpath" xmlns:ns0="http://xmlns.oracle.com/bpel/workflow/task" xmlns:tns="http://assessor.lacounty.gov/amp/data/bvm/ManageBYE/v1.0" xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap" exclude-result-prefixes="xsi xsl ns1 inp2 evidence bpmo jaxb ns3 inp1 ns2 inp3 xsd ns0 tns xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap" xmlns:oracle-xsl-mapper="http://www.oracle.com/xsl/mapper/schemas">
  <oracle-xsl-mapper:schema>
      <!--SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY.-->
      <oracle-xsl-mapper:mapSources>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="oramds:/apps/amp/xsd/bvm/ManageBYE.xsd"/>
            <oracle-xsl-mapper:rootElement name="manageBYERequest" namespace="http://assessor.lacounty.gov/amp/data/bvm/ManageBYE/v1.0"/>
            <oracle-xsl-mapper:param name="ManageBYE_ReqPDO"/>
         </oracle-xsl-mapper:source>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="oramds:/soa/shared/workflow/WorkflowTask.xsd"/>
            <oracle-xsl-mapper:rootElement name="task" namespace="http://xmlns.oracle.com/bpel/workflow/task"/>
            <oracle-xsl-mapper:param name="execDataWFLogPDO"/>
         </oracle-xsl-mapper:source>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="../businessCatalog/Data/ErrorBO.xsd"/>
            <oracle-xsl-mapper:rootElement name="ErrorBO" namespace="http://xmlns.oracle.com/bpm/bpmobject/Data/ErrorBO"/>
            <oracle-xsl-mapper:param name="ErrorPDO"/>
         </oracle-xsl-mapper:source>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="oramds:/apps/amp/xsd/ao/LogWorkFlowDetails.xsd"/>
            <oracle-xsl-mapper:rootElement name="logWorkFlowDetailsRequest" namespace="http://assessor.lacounty.gov/amp/data/ao/LogWorkFlowDetails/v1.0"/>
            <oracle-xsl-mapper:param name="LogWorkflowDetailsPDO"/>
         </oracle-xsl-mapper:source>
      </oracle-xsl-mapper:mapSources>
      <oracle-xsl-mapper:mapTargets>
         <oracle-xsl-mapper:target type="XSD">
            <oracle-xsl-mapper:schema location="oramds:/apps/amp/xsd/ao/LogWorkFlowDetails.xsd"/>
            <oracle-xsl-mapper:rootElement name="logWorkFlowDetailsRequest" namespace="http://assessor.lacounty.gov/amp/data/ao/LogWorkFlowDetails/v1.0"/>
         </oracle-xsl-mapper:target>
      </oracle-xsl-mapper:mapTargets>
      <oracle-xsl-mapper:mapShowPrefixes type="true"/>
      <!--GENERATED BY ORACLE XSL MAPPER 12.2.1.2.0(XSLT Build 161003.0739.0018) AT [THU MAR 09 12:18:03 IST 2017].-->
   </oracle-xsl-mapper:schema>
   <!--User Editing allowed BELOW this line - DO NOT DELETE THIS LINE-->
   <xsl:param name="execDataWFLogPDO"/>
  <xsl:param name="ErrorPDO"/>
  <xsl:param name="LogWorkflowDetailsPDO"/>
  <xsl:template match="/">
    <ns3:logWorkFlowDetailsRequest>
      <ns3:header>
        <inp1:applicationId>
          <xsl:value-of select="/tns:manageBYERequest/tns:header/inp1:applicationId"/>
        </inp1:applicationId>
        <inp1:requestId>
          <xsl:value-of select="/tns:manageBYERequest/tns:header/inp1:requestId"/>
        </inp1:requestId>
      </ns3:header>
      <ns3:ain>
        <xsl:value-of select="/tns:manageBYERequest/tns:ain"/>
      </ns3:ain>
      <ns3:aoid>
        <xsl:value-of select="/tns:manageBYERequest/tns:aoid"/>
      </ns3:aoid>
      <ns3:userId>
        <xsl:value-of select="/tns:manageBYERequest/tns:userId"/>
      </ns3:userId>
      <ns3:processType>
        <xsl:value-of select="/tns:manageBYERequest/tns:processType"/>
      </ns3:processType>
      <xsl:choose>
        <xsl:when test="string-length($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:acquiredBy) > 0">
          <ns3:claimedBy>
            <xsl:value-of select="$execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:acquiredBy"/>
          </ns3:claimedBy>
        </xsl:when>
        <xsl:otherwise>
          <ns3:claimedBy>
            <xsl:value-of select="$LogWorkflowDetailsPDO/ns3:logWorkFlowDetailsRequest/ns3:claimedBy"/>
          </ns3:claimedBy>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test="string-length($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:acquiredBy) > 0">
          <ns3:claimedDate>
            <xsl:value-of select="$execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:updatedDate"/>
          </ns3:claimedDate>
        </xsl:when>
        <xsl:otherwise>
          <ns3:claimedDate>
            <xsl:value-of select="$LogWorkflowDetailsPDO/ns3:logWorkFlowDetailsRequest/ns3:claimedDate"/>
          </ns3:claimedDate>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test="(contains($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:outcome,'REJECT') or contains($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:outcome,'CANCEL')) or (contains($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:outcome,'ASSIGN') or contains($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:state,'WITHDRAWN'))">
          <ns3:reviewedBy>
            <xsl:value-of select="$execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:updatedBy/ns0:displayName"/>
          </ns3:reviewedBy>
        </xsl:when>
        <xsl:otherwise>
          <ns3:reviewedBy>
            <xsl:value-of select="$LogWorkflowDetailsPDO/ns3:logWorkFlowDetailsRequest/ns3:reviewedBy"/>
          </ns3:reviewedBy>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test="(contains($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:outcome,'REJECT') or contains($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:outcome,'ASSIGN')) or (contains($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:outcome,'CANCEL') or contains($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:state,'WITHDRAWN'))">
          <ns3:reviewedDate>
            <xsl:value-of select="$execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:updatedDate"/>
          </ns3:reviewedDate>
        </xsl:when>
        <xsl:otherwise>
          <ns3:reviewedDate>
            <xsl:value-of select="$LogWorkflowDetailsPDO/ns3:logWorkFlowDetailsRequest/ns3:reviewedDate"/>
          </ns3:reviewedDate>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test="contains($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:outcome,'SUBMIT')">
          <ns3:editBy>
            <xsl:value-of select="$execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:updatedBy/ns0:displayName"/>
          </ns3:editBy>
        </xsl:when>
        <xsl:otherwise>
          <ns3:editBy>
            <xsl:value-of select="$LogWorkflowDetailsPDO/ns3:logWorkFlowDetailsRequest/ns3:editBy"/>
          </ns3:editBy>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test="contains($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:outcome,'SUBMIT')">
          <ns3:editDate>
            <xsl:value-of select="$execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:updatedDate"/>
          </ns3:editDate>
        </xsl:when>
        <xsl:otherwise>
          <ns3:editDate>
            <xsl:value-of select="$LogWorkflowDetailsPDO/ns3:logWorkFlowDetailsRequest/ns3:editDate"/>
          </ns3:editDate>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test="contains($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:outcome,'APPROVE')">
          <ns3:approvedBy>
            <xsl:value-of select="$execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:updatedBy/ns0:displayName"/>
          </ns3:approvedBy>
        </xsl:when>
        <xsl:otherwise>
          <ns3:approvedBy>
            <xsl:value-of select="$LogWorkflowDetailsPDO/ns3:logWorkFlowDetailsRequest/ns3:approvedBy"/>
          </ns3:approvedBy>
        </xsl:otherwise>
      </xsl:choose>
      <xsl:choose>
        <xsl:when test="contains($execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:outcome,'APPROVE')">
          <ns3:approvedDate>
            <xsl:value-of select="$execDataWFLogPDO/ns0:task/ns0:systemAttributes/ns0:updatedDate"/>
          </ns3:approvedDate>
        </xsl:when>
        <xsl:otherwise>
          <ns3:approvedDate>
            <xsl:value-of select="$LogWorkflowDetailsPDO/ns3:logWorkFlowDetailsRequest/ns3:approvedDate"/>
          </ns3:approvedDate>
        </xsl:otherwise>
      </xsl:choose>
      <ns3:source>
        <xsl:value-of select="/tns:manageBYERequest/tns:Source"/>
      </ns3:source>
      <ns3:errorCode>
        <xsl:value-of select="$ErrorPDO/ns2:ErrorBO/ns2:errorName"/>
      </ns3:errorCode>
      <ns3:errorType>
        <xsl:value-of select="$ErrorPDO/ns2:ErrorBO/ns2:errorNS"/>
      </ns3:errorType>
      <ns3:errorDescription>
        <xsl:value-of select="$ErrorPDO/ns2:ErrorBO/ns2:errorInfo"/>
      </ns3:errorDescription>
      <ns3:category>
        <xsl:value-of select="$execDataWFLogPDO/ns0:task/ns0:category"/>
      </ns3:category>
    </ns3:logWorkFlowDetailsRequest>
  </xsl:template>
</xsl:stylesheet>