<?xml version = '1.0' encoding = 'UTF-8'?>
<xsl:stylesheet version="1.0" xmlns:ns5="http://xmlns.oracle.com/bpm/bpmobject/Data/UIStateBO" xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20" xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/" xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:ns8="http://assessor.lacounty.gov/amp/type/common/ResponseHeader/v1.0" xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions" xmlns:bpmo="http://xmlns.oracle.com/bpm/bpmobject/" xmlns:ns7="http://assessor.lacounty.gov/amp/type/common/RequestHeader/v1.0" xmlns:ns9="http://xmlns.oracle.com/bpm/bpmobject/Data/ProcessRoutingBO" xmlns:ora="http://schemas.oracle.com/xpath/extension" xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator" xmlns:ns2="http://xmlns.oracle.com/bpm/bpmobject/Data/SortBYEBO" xmlns:ns3="http://xmlns.oracle.com/bpm/bpmobject/Data/AssignToUserOrGroupBO" xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction" xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc" xmlns:ns1="http://assessor.lacounty.gov/amp/data/bvm/RetrieveBYE/v1.0" xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue" xmlns:ns0="http://xmlns.oracle.com/bpm/bpmobject/Data/RetriveBYEDetails" xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath" xmlns:med="http://schemas.oracle.com/mediator/xpath" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath" xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk" xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions" xmlns:ns6="http://assessor.lacounty.gov/amp/type/bvm/BaseYearEvent/v1.0" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:bpmn="http://schemas.oracle.com/bpm/xpath" xmlns:ns4="http://assessor.lacounty.gov/amp/data/bvm/ManageBYE/v1.0" xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap" exclude-result-prefixes="xsi xsl ns5 ns8 bpmo ns7 ns9 ns2 ns3 ns1 ns0 ns6 xsd ns4 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap" xmlns:oracle-xsl-mapper="http://www.oracle.com/xsl/mapper/schemas">
  <oracle-xsl-mapper:schema>
      <!--SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY.-->
      <oracle-xsl-mapper:mapSources>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="../businessCatalog/Data/RetriveBYEDetails.xsd"/>
            <oracle-xsl-mapper:rootElement name="RetriveBYEDetails" namespace="http://xmlns.oracle.com/bpm/bpmobject/Data/RetriveBYEDetails"/>
            <oracle-xsl-mapper:param name="RetriveBYE_PDO"/>
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
    <ns9:ProcessRoutingBO>
      <ns9:submitBYE_Outcome>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:taskOutcome/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:taskOutcome/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:taskOutcome"/>
      </ns9:submitBYE_Outcome>
      <ns9:manageBYE_Outcome>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:manageBYE_Outcome/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:manageBYE_Outcome/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:manageBYE_Outcome"/>
      </ns9:manageBYE_Outcome>
      <ns9:manageBYE_PA_Outcome>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:manageBYE_PA_Outcome/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:manageBYE_PA_Outcome/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:manageBYE_PA_Outcome"/>
      </ns9:manageBYE_PA_Outcome>
      <ns9:reviewBYE_Outcome>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:reviewBYE_Outcome/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:reviewBYE_Outcome/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:reviewBYE_Outcome"/>
      </ns9:reviewBYE_Outcome>
      <ns9:reviewBYE_PA_Otcome>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:reviewBYE_PA_Otcome/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:reviewBYE_PA_Otcome/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:reviewBYE_PA_Otcome"/>
      </ns9:reviewBYE_PA_Otcome>
      <ns9:approveBYE_Outcome>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:approveBYE_Outcome/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:approveBYE_Outcome/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:approveBYE_Outcome"/>
      </ns9:approveBYE_Outcome>
      <ns9:fyiOutcome>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:fyiOutcome/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:fyiOutcome/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:fyiOutcome"/>
      </ns9:fyiOutcome>
      <xsl:choose>
        <xsl:when test="string-length(/ns0:RetriveBYEDetails/ns0:updatedBy) > 0.0">
          <ns9:assignTo_AppraiserId>
            <xsl:if test="/ns0:RetriveBYEDetails/ns0:updatedBy/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="/ns0:RetriveBYEDetails/ns0:updatedBy/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="/ns0:RetriveBYEDetails/ns0:updatedBy"/>
          </ns9:assignTo_AppraiserId>
        </xsl:when>
        <xsl:otherwise>
          <ns9:assignTo_AppraiserId>
            <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignTo_AppraiserId/@xsi:nil">
              <xsl:attribute name="xsi:nil">
                <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignTo_AppraiserId/@xsi:nil"/>
              </xsl:attribute>
            </xsl:if>
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignTo_AppraiserId"/>
          </ns9:assignTo_AppraiserId>
        </xsl:otherwise>
      </xsl:choose>
      <ns9:supervisorId>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:supervisorId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:supervisorId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:supervisorId"/>
      </ns9:supervisorId>
      <ns9:principalAppraiserId>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:principalAppraiserId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:principalAppraiserId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:principalAppraiserId"/>
      </ns9:principalAppraiserId>
      <ns9:chiefAppraiserId>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAppraiserId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAppraiserId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAppraiserId"/>
      </ns9:chiefAppraiserId>
      <ns9:assignTo_SupervisorId>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignTo_SupervisorId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignTo_SupervisorId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignTo_SupervisorId"/>
      </ns9:assignTo_SupervisorId>
      <ns9:assignTo_SupervisorGroupId>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignTo_SupervisorGroupId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignTo_SupervisorGroupId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignTo_SupervisorGroupId"/>
      </ns9:assignTo_SupervisorGroupId>
      <ns9:assignedToAppraiserBy>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToAppraiserBy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToAppraiserBy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToAppraiserBy"/>
      </ns9:assignedToAppraiserBy>
      <ns9:assignedToSupervisorBy>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToSupervisorBy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToSupervisorBy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToSupervisorBy"/>
      </ns9:assignedToSupervisorBy>
      <ns9:assignedToPABy>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToPABy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToPABy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToPABy"/>
      </ns9:assignedToPABy>
      <ns9:assignedToChiefBy>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToChiefBy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToChiefBy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToChiefBy"/>
      </ns9:assignedToChiefBy>
      <ns9:submitTaskTitle>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:submitTaskTitle/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:submitTaskTitle/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:submitTaskTitle"/>
      </ns9:submitTaskTitle>
      <ns9:supervisorTaskTitle>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:supervisorTaskTitle/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:supervisorTaskTitle/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:supervisorTaskTitle"/>
      </ns9:supervisorTaskTitle>
      <ns9:pATaskTitle>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pATaskTitle/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pATaskTitle/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pATaskTitle"/>
      </ns9:pATaskTitle>
      <ns9:supervisorGroupTaskTitle>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:supervisorGroupTaskTitle/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:supervisorGroupTaskTitle/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:supervisorGroupTaskTitle"/>
      </ns9:supervisorGroupTaskTitle>
      <ns9:pAGroupTaskTitle>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAGroupTaskTitle/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAGroupTaskTitle/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAGroupTaskTitle"/>
      </ns9:pAGroupTaskTitle>
      <ns9:submitBy>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:submitBy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:submitBy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:submitBy"/>
      </ns9:submitBy>
      <ns9:approvedBy>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:approvedBy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:approvedBy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:approvedBy"/>
      </ns9:approvedBy>
      <ns9:approvedByRole>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:approvedByRole/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:approvedByRole/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:approvedByRole"/>
      </ns9:approvedByRole>
      <ns9:pAAssigningToSupervisor>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAAssigningToSupervisor/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAAssigningToSupervisor/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAAssigningToSupervisor"/>
      </ns9:pAAssigningToSupervisor>
      <ns9:chiefAssigningToPA>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAssigningToPA/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAssigningToPA/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAssigningToPA"/>
      </ns9:chiefAssigningToPA>
      <ns9:chiefAssigningToSupervisor>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAssigningToSupervisor/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAssigningToSupervisor/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAssigningToSupervisor"/>
      </ns9:chiefAssigningToSupervisor>
      <ns9:pAAssigningToPA>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAAssigningToPA/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAAssigningToPA/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAAssigningToPA"/>
      </ns9:pAAssigningToPA>
      <ns9:accquiredBy_SupervisorId>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:accquiredBy_SupervisorId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:accquiredBy_SupervisorId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:accquiredBy_SupervisorId"/>
      </ns9:accquiredBy_SupervisorId>
      <ns9:accquiredBy_PAID>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:accquiredBy_PAID/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:accquiredBy_PAID/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:accquiredBy_PAID"/>
      </ns9:accquiredBy_PAID>
      <ns9:accquiredBy_ChiefID>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:accquiredBy_ChiefID/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:accquiredBy_ChiefID/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:accquiredBy_ChiefID"/>
      </ns9:accquiredBy_ChiefID>
      <ns9:selfAssign>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:selfAssign/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:selfAssign/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:selfAssign"/>
      </ns9:selfAssign>
      <ns9:pAAccquired>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAAccquired/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAAccquired/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAAccquired"/>
      </ns9:pAAccquired>
      <ns9:supervisorAccquired>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:supervisorAccquired/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:supervisorAccquired/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:supervisorAccquired"/>
      </ns9:supervisorAccquired>
      <ns9:chiefAccquired>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAccquired/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAccquired/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAccquired"/>
      </ns9:chiefAccquired>
      <ns9:escalateTo>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:escalateTo/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:escalateTo/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:escalateTo"/>
      </ns9:escalateTo>
      <ns9:escalationPath>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:escalationPath/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:escalationPath/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:escalationPath"/>
      </ns9:escalationPath>
      <ns9:isProcessEscalated>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:isProcessEscalated/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:isProcessEscalated/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:isProcessEscalated"/>
      </ns9:isProcessEscalated>
      <ns9:approvedByUserId>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:approvedByUserId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:approvedByUserId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:approvedByUserId"/>
      </ns9:approvedByUserId>
      <ns9:assignedToDirectorBy>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToDirectorBy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToDirectorBy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToDirectorBy"/>
      </ns9:assignedToDirectorBy>
      <ns9:assignedToAssistantAssessorBy>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToAssistantAssessorBy/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToAssistantAssessorBy/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assignedToAssistantAssessorBy"/>
      </ns9:assignedToAssistantAssessorBy>
      <ns9:directorId>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:directorId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:directorId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:directorId"/>
      </ns9:directorId>
      <ns9:assistantAssessorId>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assistantAssessorId/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assistantAssessorId/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:assistantAssessorId"/>
      </ns9:assistantAssessorId>
      <ns9:noOfUserModifiedEvents>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:noOfUserModifiedEvents/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:noOfUserModifiedEvents/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:noOfUserModifiedEvents"/>
      </ns9:noOfUserModifiedEvents>
      <ns9:pAAssigningToAppraiser>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAAssigningToAppraiser/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAAssigningToAppraiser/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:pAAssigningToAppraiser"/>
      </ns9:pAAssigningToAppraiser>
      <ns9:chiefAssigningToAppraiser>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAssigningToAppraiser/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAssigningToAppraiser/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:chiefAssigningToAppraiser"/>
      </ns9:chiefAssigningToAppraiser>
      <ns9:isProcessCancelled>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:isProcessCancelled/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:isProcessCancelled/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:isProcessCancelled"/>
      </ns9:isProcessCancelled>
      <ns9:isInvalid>
        <xsl:if test="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:isInvalid/@xsi:nil">
          <xsl:attribute name="xsi:nil">
            <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:isInvalid/@xsi:nil"/>
          </xsl:attribute>
        </xsl:if>
        <xsl:value-of select="$ProcessRoutingPDO/ns9:ProcessRoutingBO/ns9:isInvalid"/>
      </ns9:isInvalid>
      <ns9:taskOutcome>
        <xsl:text disable-output-escaping="no"/>
      </ns9:taskOutcome>
    </ns9:ProcessRoutingBO>
  </xsl:template>
</xsl:stylesheet>