<?xml version = '1.0' encoding = 'UTF-8'?>
<xsl:stylesheet version="1.0" xmlns:ns1="http://xmlns.oracle.com/bpm/bpmobject/Data/UIStateBO" xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20" xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/" xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ns4="http://xmlns.oracle.com/bpel/workflow/common" xmlns:ns3="http://assessor.lacounty.gov/amp/type/common/ResponseHeader/v1.0" xmlns:evidence="http://xmlns.oracle.com/bpel/workflow/TaskEvidenceService" xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions" xmlns:bpmo="http://xmlns.oracle.com/bpm/bpmobject/" xmlns:jaxb="http://java.sun.com/xml/ns/jaxb" xmlns:ns7="http://assessor.lacounty.gov/amp/type/common/RequestHeader/v1.0" xmlns:ns10="http://xmlns.oracle.com/bpm/bpmobject/Data/ProcessRoutingBO" xmlns:ora="http://schemas.oracle.com/xpath/extension" xmlns:ns8="http://xmlns.oracle.com/bpm/bpmobject/Data/SortBYEBO" xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator" xmlns:ns0="http://xmlns.oracle.com/bpm/bpmobject/Data/AssignToUserOrGroupBO" xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction" xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc" xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue" xmlns:ns2="http://assessor.lacounty.gov/amp/data/bvm/RetrieveBYE/v1.0" xmlns:ns5="http://xmlns.oracle.com/bpm/bpmobject/Data/RetriveBYEDetails" xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath" xmlns:med="http://schemas.oracle.com/mediator/xpath" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath" xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk" xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions" xmlns:ns6="http://assessor.lacounty.gov/amp/type/bvm/BaseYearEvent/v1.0" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:bpmn="http://schemas.oracle.com/bpm/xpath" xmlns:tns="http://xmlns.oracle.com/bpel/workflow/task" xmlns:ns9="http://assessor.lacounty.gov/amp/data/bvm/ManageBYE/v1.0" xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap" exclude-result-prefixes="xsi xsl ns1 ns4 ns3 evidence bpmo jaxb ns7 ns10 ns8 ns0 ns2 ns5 ns6 xsd tns ns9 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap" xmlns:oracle-xsl-mapper="http://www.oracle.com/xsl/mapper/schemas">
  <oracle-xsl-mapper:schema>
      <!--SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY.-->
      <oracle-xsl-mapper:mapSources>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="../xsd/ReviewBaseYearEventTaskWorkflowTask.xsd"/>
            <oracle-xsl-mapper:rootElement name="task" namespace="http://xmlns.oracle.com/bpel/workflow/task"/>
            <oracle-xsl-mapper:param name="execData"/>
         </oracle-xsl-mapper:source>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="../businessCatalog/Data/ProcessRoutingBO.xsd"/>
            <oracle-xsl-mapper:rootElement name="ProcessRoutingBO" namespace="http://xmlns.oracle.com/bpm/bpmobject/Data/ProcessRoutingBO"/>
            <oracle-xsl-mapper:param name="ProcessRoutingPDO"/>
         </oracle-xsl-mapper:source>
      </oracle-xsl-mapper:mapSources>
      <oracle-xsl-mapper:mapTargets>
         <oracle-xsl-mapper:target type="XSD">
            <oracle-xsl-mapper:schema location="../businessCatalog/Data/ProcessRoutingBO.xsd"/>
            <oracle-xsl-mapper:rootElement name="ProcessRoutingBO" namespace="http://xmlns.oracle.com/bpm/bpmobject/Data/ProcessRoutingBO"/>
         </oracle-xsl-mapper:target>
      </oracle-xsl-mapper:mapTargets>
      <oracle-xsl-mapper:mapShowPrefixes type="true"/>
      <!--GENERATED BY ORACLE XSL MAPPER 12.2.1.2.0(XSLT Build 161003.0739.0018) AT [THU MAR 09 12:18:09 IST 2017].-->
   </oracle-xsl-mapper:schema>
   <!--User Editing allowed BELOW this line - DO NOT DELETE THIS LINE-->
   <xsl:param name="ProcessRoutingPDO"/>
  <xsl:template match="/">
    <ns10:ProcessRoutingBO>
      <ns10:submitBYE_Outcome>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:submitBYE_Outcome/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:submitBYE_Outcome/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:submitBYE_Outcome"/>
      </ns10:submitBYE_Outcome>
      <ns10:manageBYE_Outcome>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:manageBYE_Outcome/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:manageBYE_Outcome/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:manageBYE_Outcome"/>
      </ns10:manageBYE_Outcome>
      <ns10:manageBYE_PA_Outcome>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:manageBYE_PA_Outcome/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:manageBYE_PA_Outcome/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:manageBYE_PA_Outcome"/>
      </ns10:manageBYE_PA_Outcome>
      <ns10:reviewBYE_Outcome>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:reviewBYE_Outcome/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:reviewBYE_Outcome/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:reviewBYE_Outcome"/>
      </ns10:reviewBYE_Outcome>
      <ns10:reviewBYE_PA_Otcome>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:reviewBYE_PA_Otcome/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:reviewBYE_PA_Otcome/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:reviewBYE_PA_Otcome"/>
      </ns10:reviewBYE_PA_Otcome>
      <ns10:approveBYE_Outcome>
        <xsl:value-of select="/tns:task/tns:systemAttributes/tns:outcome"/>
      </ns10:approveBYE_Outcome>
      <ns10:fyiOutcome>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:fyiOutcome/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:fyiOutcome/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:fyiOutcome"/>
      </ns10:fyiOutcome>
      <ns10:assignTo_AppraiserId>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignTo_AppraiserId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignTo_AppraiserId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignTo_AppraiserId"/>
      </ns10:assignTo_AppraiserId>
      <ns10:supervisorId>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:supervisorId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:supervisorId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:supervisorId"/>
      </ns10:supervisorId>
      <ns10:principalAppraiserId>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:principalAppraiserId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:principalAppraiserId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:principalAppraiserId"/>
      </ns10:principalAppraiserId>
      <ns10:chiefAppraiserId>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAppraiserId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAppraiserId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAppraiserId"/>
      </ns10:chiefAppraiserId>
      <ns10:assignTo_SupervisorId>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignTo_SupervisorId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignTo_SupervisorId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignTo_SupervisorId"/>
      </ns10:assignTo_SupervisorId>
      <ns10:assignTo_SupervisorGroupId>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignTo_SupervisorGroupId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignTo_SupervisorGroupId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignTo_SupervisorGroupId"/>
      </ns10:assignTo_SupervisorGroupId>
      <ns10:assignedToAppraiserBy>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToAppraiserBy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToAppraiserBy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToAppraiserBy"/>
      </ns10:assignedToAppraiserBy>
      <ns10:assignedToSupervisorBy>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToSupervisorBy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToSupervisorBy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToSupervisorBy"/>
      </ns10:assignedToSupervisorBy>
      <ns10:assignedToPABy>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToPABy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToPABy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToPABy"/>
      </ns10:assignedToPABy>
      <ns10:assignedToChiefBy>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToChiefBy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToChiefBy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToChiefBy"/>
      </ns10:assignedToChiefBy>
      <ns10:submitTaskTitle>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:submitTaskTitle/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:submitTaskTitle/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:submitTaskTitle"/>
      </ns10:submitTaskTitle>
      <ns10:supervisorTaskTitle>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:supervisorTaskTitle/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:supervisorTaskTitle/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:supervisorTaskTitle"/>
      </ns10:supervisorTaskTitle>
      <ns10:pATaskTitle>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pATaskTitle/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pATaskTitle/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pATaskTitle"/>
      </ns10:pATaskTitle>
      <ns10:supervisorGroupTaskTitle>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:supervisorGroupTaskTitle/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:supervisorGroupTaskTitle/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:supervisorGroupTaskTitle"/>
      </ns10:supervisorGroupTaskTitle>
      <ns10:pAGroupTaskTitle>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAGroupTaskTitle/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAGroupTaskTitle/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAGroupTaskTitle"/>
      </ns10:pAGroupTaskTitle>
      <ns10:submitBy>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:submitBy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:submitBy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:submitBy"/>
      </ns10:submitBy>
      <xsl:choose>
        <xsl:when test="string-length(/tns:task/tns:systemAttributes/tns:acquiredBy) > 0">
          <ns10:approvedBy>
            <xsl:value-of select="/tns:task/tns:systemAttributes/tns:acquiredBy"/>
          </ns10:approvedBy>
        </xsl:when>
        <xsl:otherwise>
          <ns10:approvedBy>
            <xsl:value-of select="/tns:task/tns:systemAttributes/tns:updatedBy/tns:id"/>
          </ns10:approvedBy>
        </xsl:otherwise>
      </xsl:choose>
      <ns10:approvedByRole>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:approvedByRole/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:approvedByRole/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:approvedByRole"/>
      </ns10:approvedByRole>
      <ns10:pAAssigningToSupervisor>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAAssigningToSupervisor/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAAssigningToSupervisor/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAAssigningToSupervisor"/>
      </ns10:pAAssigningToSupervisor>
      <ns10:chiefAssigningToPA>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAssigningToPA/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAssigningToPA/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAssigningToPA"/>
      </ns10:chiefAssigningToPA>
      <ns10:chiefAssigningToSupervisor>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAssigningToSupervisor/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAssigningToSupervisor/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAssigningToSupervisor"/>
      </ns10:chiefAssigningToSupervisor>
      <ns10:pAAssigningToPA>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAAssigningToPA/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAAssigningToPA/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAAssigningToPA"/>
      </ns10:pAAssigningToPA>
      <ns10:accquiredBy_SupervisorId>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:accquiredBy_SupervisorId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:accquiredBy_SupervisorId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:accquiredBy_SupervisorId"/>
      </ns10:accquiredBy_SupervisorId>
      <ns10:accquiredBy_PAID>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:accquiredBy_PAID/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:accquiredBy_PAID/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:accquiredBy_PAID"/>
      </ns10:accquiredBy_PAID>
      <ns10:accquiredBy_ChiefID>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:accquiredBy_ChiefID/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:accquiredBy_ChiefID/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:accquiredBy_ChiefID"/>
      </ns10:accquiredBy_ChiefID>
      <ns10:selfAssign>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:selfAssign/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:selfAssign/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:selfAssign"/>
      </ns10:selfAssign>
      <ns10:pAAccquired>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAAccquired/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAAccquired/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAAccquired"/>
      </ns10:pAAccquired>
      <ns10:supervisorAccquired>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:supervisorAccquired/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:supervisorAccquired/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:supervisorAccquired"/>
      </ns10:supervisorAccquired>
      <ns10:chiefAccquired>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAccquired/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAccquired/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAccquired"/>
      </ns10:chiefAccquired>
      <ns10:escalateTo>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:escalateTo/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:escalateTo/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:escalateTo"/>
      </ns10:escalateTo>
      <ns10:escalationPath>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:escalationPath/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:escalationPath/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:escalationPath"/>
      </ns10:escalationPath>
      <ns10:isProcessEscalated>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:isProcessEscalated/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:isProcessEscalated/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:isProcessEscalated"/>
      </ns10:isProcessEscalated>
      <ns10:approvedByUserId>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:approvedByUserId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:approvedByUserId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:approvedByUserId"/>
      </ns10:approvedByUserId>
      <ns10:assignedToDirectorBy>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToDirectorBy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToDirectorBy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToDirectorBy"/>
      </ns10:assignedToDirectorBy>
      <ns10:assignedToAssistantAssessorBy>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToAssistantAssessorBy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToAssistantAssessorBy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:assignedToAssistantAssessorBy"/>
      </ns10:assignedToAssistantAssessorBy>
      <ns10:directorId>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:directorId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:directorId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:directorId"/>
      </ns10:directorId>
      <xsl:choose>
        <xsl:when test="string-length(/tns:task/tns:systemAttributes/tns:acquiredBy) > 0">
          <ns10:assistantAssessorId>
            <xsl:value-of select="/tns:task/tns:systemAttributes/tns:acquiredBy"/>
          </ns10:assistantAssessorId>
        </xsl:when>
        <xsl:otherwise>
          <ns10:assistantAssessorId>
            <xsl:value-of select="/tns:task/tns:systemAttributes/tns:updatedBy/tns:id"/>
          </ns10:assistantAssessorId>
        </xsl:otherwise>
      </xsl:choose>
      <ns10:noOfUserModifiedEvents>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:noOfUserModifiedEvents/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:noOfUserModifiedEvents/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:noOfUserModifiedEvents"/>
      </ns10:noOfUserModifiedEvents>
      <ns10:pAAssigningToAppraiser>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAAssigningToAppraiser/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAAssigningToAppraiser/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:pAAssigningToAppraiser"/>
      </ns10:pAAssigningToAppraiser>
      <ns10:chiefAssigningToAppraiser>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAssigningToAppraiser/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAssigningToAppraiser/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:chiefAssigningToAppraiser"/>
      </ns10:chiefAssigningToAppraiser>
      <ns10:isProcessCancelled>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:isProcessCancelled/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:isProcessCancelled/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:isProcessCancelled"/>
      </ns10:isProcessCancelled>
      <ns10:isInvalid>
        <xsl:if test="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:isInvalid/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:isInvalid/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns10:ProcessRoutingBO/ns10:isInvalid"/>
      </ns10:isInvalid>
    </ns10:ProcessRoutingBO>
  </xsl:template>
</xsl:stylesheet>