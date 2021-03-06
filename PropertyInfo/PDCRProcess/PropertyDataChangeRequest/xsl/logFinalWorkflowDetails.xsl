<?xml version = '1.0' encoding = 'UTF-8'?>
<xsl:stylesheet version="1.0" xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20" xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/" xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:inp2="http://assessor.lacounty.gov/amp/type/common/ResponseHeader/v1.0" xmlns:ns0="http://xmlns.oracle.com/bpel/workflow/common" xmlns:evidence="http://xmlns.oracle.com/bpel/workflow/TaskEvidenceService" xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions" xmlns:jaxb="http://java.sun.com/xml/ns/jaxb" xmlns:ns1="http://assessor.lacounty.gov/amp/data/ao/LogWorkFlowDetails/v1.0" xmlns:inp1="http://assessor.lacounty.gov/amp/type/common/RequestHeader/v1.0" xmlns:ora="http://schemas.oracle.com/xpath/extension" xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator" xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction" xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc" xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue" xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath" xmlns:med="http://schemas.oracle.com/mediator/xpath" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath" xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk" xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:tns="http://xmlns.oracle.com/bpel/workflow/task" xmlns:bpmn="http://schemas.oracle.com/bpm/xpath" xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap" exclude-result-prefixes="xsi xsl inp2 ns0 evidence jaxb ns1 inp1 xsd tns xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap" xmlns:oracle-xsl-mapper="http://www.oracle.com/xsl/mapper/schemas">
  <oracle-xsl-mapper:schema>
      <!--SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY.-->
      <oracle-xsl-mapper:mapSources>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="oramds:/soa/shared/workflow/WorkflowTask.xsd"/>
            <oracle-xsl-mapper:rootElement name="task" namespace="http://xmlns.oracle.com/bpel/workflow/task"/>
            <oracle-xsl-mapper:param name="execDataWFLogDO"/>
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
      <!--GENERATED BY ORACLE XSL MAPPER 12.2.1.2.0(XSLT Build 161003.0739.0018) AT [THU MAR 09 15:05:03 IST 2017].-->
   </oracle-xsl-mapper:schema>
   <!--User Editing allowed BELOW this line - DO NOT DELETE THIS LINE-->
   <xsl:param name="LogWorkflowDetailsPDO"/>
  <xsl:template match="/">
    <ns1:logWorkFlowDetailsRequest>
      <ns1:header>
        <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:header/inp1:applicationId">
          <inp1:applicationId>
            <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:header/inp1:applicationId"/>
          </inp1:applicationId>
        </xsl:if>
        <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:header/inp1:requestId">
          <inp1:requestId>
            <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:header/inp1:requestId"/>
          </inp1:requestId>
        </xsl:if>
      </ns1:header>
      <ns1:ain>
        <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:ain"/>
      </ns1:ain>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:aoid">
        <ns1:aoid>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:aoid"/>
        </ns1:aoid>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:userId">
        <ns1:userId>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:userId"/>
        </ns1:userId>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:processType">
        <ns1:processType>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:processType"/>
        </ns1:processType>
      </xsl:if>
      <ns1:initiatedBy>
        <xsl:value-of select="/tns:task/tns:creator"/>
      </ns1:initiatedBy>
      <ns1:initiatedDate>
        <xsl:value-of select="/tns:task/tns:startDate"/>
      </ns1:initiatedDate>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:claimedBy">
        <ns1:claimedBy>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:claimedBy"/>
        </ns1:claimedBy>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:claimedDate">
        <ns1:claimedDate>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:claimedDate"/>
        </ns1:claimedDate>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:reviewedBy">
        <ns1:reviewedBy>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:reviewedBy"/>
        </ns1:reviewedBy>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:reviewedDate">
        <ns1:reviewedDate>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:reviewedDate"/>
        </ns1:reviewedDate>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:editStatus">
        <ns1:editStatus>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:editStatus"/>
        </ns1:editStatus>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:editBy">
        <ns1:editBy>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:editBy"/>
        </ns1:editBy>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:editDate">
        <ns1:editDate>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:editDate"/>
        </ns1:editDate>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:approvedBy">
        <ns1:approvedBy>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:approvedBy"/>
        </ns1:approvedBy>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:approvedDate">
        <ns1:approvedDate>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:approvedDate"/>
        </ns1:approvedDate>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:processStatus">
        <xsl:choose>
          <xsl:when test="contains($LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:processStatus,&quot;Cancelled&quot;)">
            <ns1:processStatus>
              <xsl:text disable-output-escaping="no">Cancelled</xsl:text>
            </ns1:processStatus>
          </xsl:when>
          <xsl:when test="contains($LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:processStatus,&quot;Errored&quot;)">
            <ns1:processStatus>
              <xsl:text disable-output-escaping="no">Errored</xsl:text>
            </ns1:processStatus>
          </xsl:when>
          <xsl:otherwise>
            <ns1:processStatus>
              <xsl:text disable-output-escaping="no">Completed</xsl:text>
            </ns1:processStatus>
          </xsl:otherwise>
        </xsl:choose>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:processName">
        <ns1:processName>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:processName"/>
        </ns1:processName>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:processInstanceId">
        <ns1:processInstanceId>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:processInstanceId"/>
        </ns1:processInstanceId>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:ecid">
        <ns1:ecid>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:ecid"/>
        </ns1:ecid>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:district">
        <ns1:district>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:district"/>
        </ns1:district>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:region">
        <ns1:region>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:region"/>
        </ns1:region>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:processStartTime">
        <ns1:processStartTime>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:processStartTime"/>
        </ns1:processStartTime>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:processEndTime">
        <ns1:processEndTime>
          <xsl:value-of select="xp20:current-dateTime()"/>
        </ns1:processEndTime>
      </xsl:if>
      <!--xsl:copy-of select="/tns:task/tns:payload">
        <?oracle-xsl-mapper-position ns1:processPayload?>
      </xsl:copy-of-->
      <ns1:processPayload>
        <xsl:copy-of select="/tns:task"/>
      </ns1:processPayload>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:taskId">
        <ns1:taskId>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:taskId"/>
        </ns1:taskId>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:taskName">
        <ns1:taskName>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:taskName"/>
        </ns1:taskName>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:taskAction">
        <ns1:taskAction>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:taskAction"/>
        </ns1:taskAction>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:taskType">
        <ns1:taskType>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:taskType"/>
        </ns1:taskType>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:assignee">
        <ns1:assignee>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:assignee"/>
        </ns1:assignee>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:taskStartDate">
        <ns1:taskStartDate>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:taskStartDate"/>
        </ns1:taskStartDate>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:taskEndDate">
        <ns1:taskEndDate>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:taskEndDate"/>
        </ns1:taskEndDate>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:source">
        <ns1:source>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:source"/>
        </ns1:source>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:errorCode">
        <ns1:errorCode>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:errorCode"/>
        </ns1:errorCode>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:errorType">
        <ns1:errorType>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:errorType"/>
        </ns1:errorType>
      </xsl:if>
      <xsl:if test="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:errorDescription">
        <ns1:errorDescription>
          <xsl:value-of select="$LogWorkflowDetailsPDO/ns1:logWorkFlowDetailsRequest/ns1:errorDescription"/>
        </ns1:errorDescription>
      </xsl:if>
      <ns1:owner>
        <xsl:value-of select="/tns:task/tns:ownerGroup"/>
      </ns1:owner>
      <ns1:notes>
        <xsl:for-each select="/tns:task/tns:userComment">
          <ns1:notes>
            <ns1:description>
              <xsl:value-of select="tns:comment"/>
            </ns1:description>
            <ns1:updateBy>
              <xsl:value-of select="tns:updatedBy/tns:displayName"/>
            </ns1:updateBy>
            <ns1:updatedDate>
              <xsl:value-of select="tns:updatedDate"/>
            </ns1:updatedDate>
          </ns1:notes>
        </xsl:for-each>
      </ns1:notes>
      <ns1:attachments>
        <xsl:for-each select="/tns:task/tns:attachment">
          <ns1:attachments>
            <ns1:content>
              <xsl:value-of select="tns:content"/>
            </ns1:content>
            <ns1:mimeType>
              <xsl:value-of select="tns:mimeType"/>
            </ns1:mimeType>
            <ns1:documentName>
              <xsl:value-of select="tns:name"/>
            </ns1:documentName>
            <ns1:documentObjID>
              <xsl:value-of select="tns:id"/>
            </ns1:documentObjID>
            <ns1:documentURL>
              <xsl:value-of select="tns:URI"/>
            </ns1:documentURL>
            <ns1:systemVersionFlag>
              <xsl:value-of select="tns:systemVersionFlag"/>
            </ns1:systemVersionFlag>
            <ns1:taskId>
              <xsl:value-of select="tns:taskId"/>
            </ns1:taskId>
            <ns1:version>
              <xsl:value-of select="tns:version"/>
            </ns1:version>
            <ns1:acl>
              <xsl:value-of select="tns:acl"/>
            </ns1:acl>
            <ns1:doesBelongToParent>
              <xsl:value-of select="tns:doesBelongToParent"/>
            </ns1:doesBelongToParent>
            <ns1:documentType>
              <xsl:value-of select="tns:mimeType"/>
            </ns1:documentType>
            <ns1:updateBy>
              <xsl:value-of select="tns:updatedBy"/>
            </ns1:updateBy>
            <ns1:updatedDate>
              <xsl:value-of select="tns:updatedDate"/>
            </ns1:updatedDate>
            <ns1:correlationId>
              <xsl:value-of select="tns:correlationId"/>
            </ns1:correlationId>
            <ns1:size>
              <xsl:value-of select="tns:size"/>
            </ns1:size>
            <ns1:description>
              <xsl:value-of select="tns:description"/>
            </ns1:description>
            <ns1:storageType>
              <xsl:value-of select="tns:storageType"/>
            </ns1:storageType>
            <ns1:ucmDocType>
              <xsl:value-of select="tns:ucmDocType"/>
            </ns1:ucmDocType>
            <ns1:securityGroup>
              <xsl:value-of select="tns:securityGroup"/>
            </ns1:securityGroup>
            <ns1:account>
              <xsl:value-of select="tns:account"/>
            </ns1:account>
            <ns1:revision>
              <xsl:value-of select="tns:revision"/>
            </ns1:revision>
            <ns1:releaseDate>
              <xsl:value-of select="tns:releaseDate"/>
            </ns1:releaseDate>
            <ns1:expirationDate>
              <xsl:value-of select="tns:expirationDate"/>
            </ns1:expirationDate>
            <ns1:title>
              <xsl:value-of select="tns:title"/>
            </ns1:title>
            <ns1:attachmentScope>
              <xsl:value-of select="tns:attachmentScope"/>
            </ns1:attachmentScope>
            <ns1:isContentEncoded>
              <xsl:value-of select="tns:isContentEncoded"/>
            </ns1:isContentEncoded>
            <ns1:updatedByDisplayName>
              <xsl:value-of select="tns:updatedByDisplayName"/>
            </ns1:updatedByDisplayName>
            <ns1:id>
              <xsl:value-of select="tns:id"/>
            </ns1:id>
            <ns1:operation>
              <xsl:value-of select="tns:operation"/>
            </ns1:operation>
          </ns1:attachments>
        </xsl:for-each>
      </ns1:attachments>
      <ns1:outcome>
        <xsl:value-of select="/tns:task/tns:systemAttributes/tns:outcome"/>
      </ns1:outcome>
      <xsl:choose>
        <xsl:when test="contains(/tns:task/tns:systemAttributes/tns:outcome,'APPROVE')">
          <ns1:category>
            <xsl:text disable-output-escaping="no">BVM-Processed</xsl:text>
          </ns1:category>
        </xsl:when>
        <xsl:otherwise>
          <ns1:category>
            <xsl:text disable-output-escaping="no">BVM-Cancelled</xsl:text>
          </ns1:category>
        </xsl:otherwise>
      </xsl:choose>
    </ns1:logWorkFlowDetailsRequest>
  </xsl:template>
</xsl:stylesheet>