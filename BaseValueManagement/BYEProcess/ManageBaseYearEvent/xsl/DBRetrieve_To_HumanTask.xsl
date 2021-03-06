<?xml version = '1.0' encoding = 'UTF-8'?>
<xsl:stylesheet version="1.0" xmlns:ns5="http://xmlns.oracle.com/bpm/bpmobject/Data/UIStateBO" xmlns:xp20="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.Xpath20" xmlns:bpws="http://schemas.xmlsoap.org/ws/2003/03/business-process/" xmlns:bpel="http://docs.oasis-open.org/wsbpel/2.0/process/executable" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:inp2="http://assessor.lacounty.gov/amp/type/common/ResponseHeader/v1.0" xmlns:bpm="http://xmlns.oracle.com/bpmn20/extensions" xmlns:bpmo="http://xmlns.oracle.com/bpm/bpmobject/" xmlns:inp1="http://assessor.lacounty.gov/amp/type/common/RequestHeader/v1.0" xmlns:ora="http://schemas.oracle.com/xpath/extension" xmlns:socket="http://www.oracle.com/XSL/Transform/java/oracle.tip.adapter.socket.ProtocolTranslator" xmlns:ns2="http://xmlns.oracle.com/bpm/bpmobject/Data/SortBYEBO" xmlns:ns3="http://xmlns.oracle.com/bpm/bpmobject/Data/AssignToUserOrGroupBO" xmlns:mhdr="http://www.oracle.com/XSL/Transform/java/oracle.tip.mediator.service.common.functions.MediatorExtnFunction" xmlns:oraext="http://www.oracle.com/XSL/Transform/java/oracle.tip.pc.services.functions.ExtFunc" xmlns:dvm="http://www.oracle.com/XSL/Transform/java/oracle.tip.dvm.LookupValue" xmlns:ns1="http://assessor.lacounty.gov/amp/data/bvm/RetrieveBYE/v1.0" xmlns:ns0="http://xmlns.oracle.com/bpm/bpmobject/Data/RetriveBYEDetails" xmlns:hwf="http://xmlns.oracle.com/bpel/workflow/xpath" xmlns:med="http://schemas.oracle.com/mediator/xpath" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:ids="http://xmlns.oracle.com/bpel/services/IdentityService/xpath" xmlns:xdk="http://schemas.oracle.com/bpel/extension/xpath/function/xdk" xmlns:xref="http://www.oracle.com/XSL/Transform/java/oracle.tip.xref.xpath.XRefXPathFunctions" xmlns:inp3="http://assessor.lacounty.gov/amp/type/bvm/BaseYearEvent/v1.0" xmlns:tns="http://assessor.lacounty.gov/amp/data/bvm/RetrieveBaseYearEvents/v1.0" xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:bpmn="http://schemas.oracle.com/bpm/xpath" xmlns:ns4="http://assessor.lacounty.gov/amp/data/bvm/ManageBYE/v1.0" xmlns:ldap="http://schemas.oracle.com/xpath/extension/ldap" exclude-result-prefixes="xsi xsl inp2 inp1 inp3 tns xsd ns5 bpmo ns2 ns3 ns1 ns0 ns4 xp20 bpws bpel bpm ora socket mhdr oraext dvm hwf med ids xdk xref bpmn ldap" xmlns:oracle-xsl-mapper="http://www.oracle.com/xsl/mapper/schemas">
  <oracle-xsl-mapper:schema>
      <!--SPECIFICATION OF MAP SOURCES AND TARGETS, DO NOT MODIFY.-->
      <oracle-xsl-mapper:mapSources>
         <oracle-xsl-mapper:source type="XSD">
            <oracle-xsl-mapper:schema location="oramds:/apps/amp/xsd/bvm/RetrieveBaseYearEvents.xsd"/>
            <oracle-xsl-mapper:rootElement name="retrieveBaseYearEventsResponse" namespace="http://assessor.lacounty.gov/amp/data/bvm/RetrieveBaseYearEvents/v1.0"/>
            <oracle-xsl-mapper:param name="retrieveBaseYearEventsResponse"/>
         </oracle-xsl-mapper:source>
      </oracle-xsl-mapper:mapSources>
      <oracle-xsl-mapper:mapTargets>
         <oracle-xsl-mapper:target type="XSD">
            <oracle-xsl-mapper:schema location="../businessCatalog/Data/RetriveBYEDetails.xsd"/>
            <oracle-xsl-mapper:rootElement name="RetriveBYEDetails" namespace="http://xmlns.oracle.com/bpm/bpmobject/Data/RetriveBYEDetails"/>
         </oracle-xsl-mapper:target>
      </oracle-xsl-mapper:mapTargets>
      <oracle-xsl-mapper:mapShowPrefixes type="true"/>
      <!--GENERATED BY ORACLE XSL MAPPER 12.2.1.2.0(XSLT Build 161003.0739.0018) AT [THU MAR 09 12:18:03 IST 2017].-->
   </oracle-xsl-mapper:schema>
   <!--User Editing allowed BELOW this line - DO NOT DELETE THIS LINE-->
   <xsl:template match="/">
    <ns0:RetriveBYEDetails>
      <ns1:RetrieveBYEDetails>
        <ns1:BYEDetailsOriginal>
          <ns1:header>
            <inp2:operationStatus>
              <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:header/inp2:operationStatus"/>
            </inp2:operationStatus>
            <inp2:instanceId>
              <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:header/inp2:instanceId"/>
            </inp2:instanceId>
            <inp2:requestId>
              <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:header/inp2:requestId"/>
            </inp2:requestId>
          </ns1:header>
          <ns1:ain>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:ain"/>
          </ns1:ain>
          <ns1:aoid>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:aoid"/>
          </ns1:aoid>
          <ns1:ainType>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:ainType"/>
          </ns1:ainType>
          <ns1:reviewRequired>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:reviewRequired"/>
          </ns1:reviewRequired>
          <ns1:processStatus>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:processStatus"/>
          </ns1:processStatus>
          <ns1:ecid>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:ecid"/>
          </ns1:ecid>
          <ns1:dbLockStatus>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:dbLockStatus"/>
          </ns1:dbLockStatus>
          <ns1:lockedByUserId>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:lockedByUserId"/>
          </ns1:lockedByUserId>
          <ns1:districtId>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:districtId"/>
          </ns1:districtId>
          <ns1:districtName>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:districtName"/>
          </ns1:districtName>
          <ns1:region>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:region"/>
          </ns1:region>
          <ns1:regionName>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:regionName"/>
          </ns1:regionName>
          <ns1:clusterRouteId>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:clusterRouteId"/>
          </ns1:clusterRouteId>
          <ns1:clusterRoute>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:clusterRoute"/>
          </ns1:clusterRoute>
          <ns1:clusterRouteName>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:clusterRouteName"/>
          </ns1:clusterRouteName>
          <ns1:baseYearEventList>
            <xsl:for-each select="/tns:retrieveBaseYearEventsResponse/tns:baseYearEventList/tns:baseYearEvent">
              <ns1:baseYearEvent>
                <inp3:ain>
                  <xsl:value-of select="inp3:ain"/>
                </inp3:ain>
                <xsl:if test="inp3:aoid">
                  <inp3:aoid>
                    <xsl:value-of select="inp3:aoid"/>
                  </inp3:aoid>
                </xsl:if>
                <xsl:if test="inp3:reviewRequired">
                  <inp3:reviewRequired>
                    <xsl:value-of select="inp3:reviewRequired"/>
                  </inp3:reviewRequired>
                </xsl:if>
                <inp3:byeId>
                  <xsl:value-of select="inp3:byeId"/>
                </inp3:byeId>
                <xsl:if test="inp3:ownershipCode">
                  <inp3:ownershipCode>
                    <xsl:value-of select="inp3:ownershipCode"/>
                  </inp3:ownershipCode>
                </xsl:if>
                <xsl:if test="inp3:percentOwnership">
                  <inp3:percentOwnership>
                    <xsl:value-of select="inp3:percentOwnership"/>
                  </inp3:percentOwnership>
                </xsl:if>
                <xsl:if test="inp3:eventNameId">
                  <inp3:eventNameId>
                    <xsl:value-of select="inp3:eventNameId"/>
                  </inp3:eventNameId>
                </xsl:if>
                <xsl:if test="inp3:eventNameKey">
                  <inp3:eventNameKey>
                    <xsl:value-of select="inp3:eventNameKey"/>
                  </inp3:eventNameKey>
                </xsl:if>
                <xsl:if test="inp3:eventNameLegend">
                  <inp3:eventNameLegend>
                    <xsl:value-of select="inp3:eventNameLegend"/>
                  </inp3:eventNameLegend>
                </xsl:if>
                <xsl:if test="inp3:eventDescriptionId">
                  <inp3:eventDescriptionId>
                    <xsl:value-of select="inp3:eventDescriptionId"/>
                  </inp3:eventDescriptionId>
                </xsl:if>
                <xsl:if test="inp3:eventDescriptionKey">
                  <inp3:eventDescriptionKey>
                    <xsl:value-of select="inp3:eventDescriptionKey"/>
                  </inp3:eventDescriptionKey>
                </xsl:if>
                <xsl:if test="inp3:eventDescriptionLegend">
                  <inp3:eventDescriptionLegend>
                    <xsl:value-of select="inp3:eventDescriptionLegend"/>
                  </inp3:eventDescriptionLegend>
                </xsl:if>
                <xsl:if test="inp3:eventDate">
                  <inp3:eventDate>
                    <xsl:value-of select="inp3:eventDate"/>
                  </inp3:eventDate>
                </xsl:if>
                <xsl:if test="inp3:recordingDate">
                  <inp3:recordingDate>
                    <xsl:value-of select="inp3:recordingDate"/>
                  </inp3:recordingDate>
                </xsl:if>
                <xsl:if test="inp3:documentNumber">
                  <inp3:documentNumber>
                    <xsl:value-of select="inp3:documentNumber"/>
                  </inp3:documentNumber>
                </xsl:if>
                <xsl:if test="inp3:sequenceNumber">
                  <inp3:sequenceNumber>
                    <xsl:value-of select="inp3:sequenceNumber"/>
                  </inp3:sequenceNumber>
                </xsl:if>
                <xsl:if test="inp3:baseYear">
                  <inp3:baseYear>
                    <xsl:value-of select="inp3:baseYear"/>
                  </inp3:baseYear>
                </xsl:if>
                <xsl:if test="inp3:subUnitNumber">
                  <inp3:subUnitNumber>
                    <xsl:value-of select="inp3:subUnitNumber"/>
                  </inp3:subUnitNumber>
                </xsl:if>
                <xsl:if test="inp3:subUnitDescription">
                  <inp3:subUnitDescription>
                    <xsl:value-of select="inp3:subUnitDescription"/>
                  </inp3:subUnitDescription>
                </xsl:if>
                <xsl:if test="inp3:partiallyCompleteNumber">
                  <inp3:partiallyCompleteNumber>
                    <xsl:value-of select="inp3:partiallyCompleteNumber"/>
                  </inp3:partiallyCompleteNumber>
                </xsl:if>
                <xsl:if test="inp3:blendedValueFlag">
                  <inp3:blendedValueFlag>
                    <xsl:value-of select="inp3:blendedValueFlag"/>
                  </inp3:blendedValueFlag>
                </xsl:if>
                <xsl:if test="inp3:percentageAppliedToValue">
                  <inp3:percentageAppliedToValue>
                    <xsl:value-of select="inp3:percentageAppliedToValue"/>
                  </inp3:percentageAppliedToValue>
                </xsl:if>
                <xsl:if test="inp3:landValue">
                  <inp3:landValue>
                    <xsl:value-of select="inp3:landValue"/>
                  </inp3:landValue>
                </xsl:if>
                <xsl:if test="inp3:improvementValue">
                  <inp3:improvementValue>
                    <xsl:value-of select="inp3:improvementValue"/>
                  </inp3:improvementValue>
                </xsl:if>
                <xsl:if test="inp3:landReasonCodeId">
                  <inp3:landReasonCodeId>
                    <xsl:value-of select="inp3:landReasonCodeId"/>
                  </inp3:landReasonCodeId>
                </xsl:if>
                <xsl:if test="inp3:landReasonCodeKey">
                  <inp3:landReasonCodeKey>
                    <xsl:value-of select="inp3:landReasonCodeKey"/>
                  </inp3:landReasonCodeKey>
                </xsl:if>
                <xsl:if test="inp3:landReasonCodeLegend">
                  <inp3:landReasonCodeLegend>
                    <xsl:value-of select="inp3:landReasonCodeLegend"/>
                  </inp3:landReasonCodeLegend>
                </xsl:if>
                <xsl:if test="inp3:improvementReasonCodeId">
                  <inp3:improvementReasonCodeId>
                    <xsl:value-of select="inp3:improvementReasonCodeId"/>
                  </inp3:improvementReasonCodeId>
                </xsl:if>
                <xsl:if test="inp3:improvementReasonCodeKey">
                  <inp3:improvementReasonCodeKey>
                    <xsl:value-of select="inp3:improvementReasonCodeKey"/>
                  </inp3:improvementReasonCodeKey>
                </xsl:if>
                <xsl:if test="inp3:improvementReasonCodeLegend">
                  <inp3:improvementReasonCodeLegend>
                    <xsl:value-of select="inp3:improvementReasonCodeLegend"/>
                  </inp3:improvementReasonCodeLegend>
                </xsl:if>
                <xsl:if test="inp3:fixtureValue">
                  <inp3:fixtureValue>
                    <xsl:value-of select="inp3:fixtureValue"/>
                  </inp3:fixtureValue>
                </xsl:if>
                <xsl:if test="inp3:retainedLandValue">
                  <inp3:retainedLandValue>
                    <xsl:value-of select="inp3:retainedLandValue"/>
                  </inp3:retainedLandValue>
                </xsl:if>
                <xsl:if test="inp3:retainedImprovementValue">
                  <inp3:retainedImprovementValue>
                    <xsl:value-of select="inp3:retainedImprovementValue"/>
                  </inp3:retainedImprovementValue>
                </xsl:if>
                <xsl:if test="inp3:retainedFixtureValue">
                  <inp3:retainedFixtureValue>
                    <xsl:value-of select="inp3:retainedFixtureValue"/>
                  </inp3:retainedFixtureValue>
                </xsl:if>
                <xsl:if test="inp3:ppAssessmentNumber">
                  <inp3:ppAssessmentNumber>
                    <xsl:value-of select="inp3:ppAssessmentNumber"/>
                  </inp3:ppAssessmentNumber>
                </xsl:if>
                <xsl:if test="inp3:personalPropertyType">
                  <inp3:personalPropertyType>
                    <xsl:value-of select="inp3:personalPropertyType"/>
                  </inp3:personalPropertyType>
                </xsl:if>
                <xsl:if test="inp3:ppDescription">
                  <inp3:ppDescription>
                    <xsl:value-of select="inp3:ppDescription"/>
                  </inp3:ppDescription>
                </xsl:if>
                <xsl:if test="inp3:totalAdjAcquisitionCost">
                  <inp3:totalAdjAcquisitionCost>
                    <xsl:value-of select="inp3:totalAdjAcquisitionCost"/>
                  </inp3:totalAdjAcquisitionCost>
                </xsl:if>
                <xsl:if test="inp3:percentageFixture">
                  <inp3:percentageFixture>
                    <xsl:value-of select="inp3:percentageFixture"/>
                  </inp3:percentageFixture>
                </xsl:if>
                <xsl:if test="inp3:effectiveBeginDate">
                  <inp3:effectiveBeginDate>
                    <xsl:value-of select="inp3:effectiveBeginDate"/>
                  </inp3:effectiveBeginDate>
                </xsl:if>
                <xsl:if test="inp3:effectiveEndDate">
                  <inp3:effectiveEndDate>
                    <xsl:value-of select="inp3:effectiveEndDate"/>
                  </inp3:effectiveEndDate>
                </xsl:if>
                <xsl:if test="inp3:verificationDate">
                  <inp3:verificationDate>
                    <xsl:value-of select="inp3:verificationDate"/>
                  </inp3:verificationDate>
                </xsl:if>
                <xsl:if test="inp3:eventSource">
                  <inp3:eventSource>
                    <xsl:value-of select="inp3:eventSource"/>
                  </inp3:eventSource>
                </xsl:if>
                <xsl:if test="inp3:verifiedBy">
                  <inp3:verifiedBy>
                    <xsl:value-of select="inp3:verifiedBy"/>
                  </inp3:verifiedBy>
                </xsl:if>
                <xsl:if test="inp3:byeLegacySourceSystem">
                  <inp3:byeLegacySourceSystem>
                    <xsl:value-of select="inp3:byeLegacySourceSystem"/>
                  </inp3:byeLegacySourceSystem>
                </xsl:if>
                <xsl:if test="inp3:originalAIN">
                  <inp3:originalAIN>
                    <xsl:value-of select="inp3:originalAIN"/>
                  </inp3:originalAIN>
                </xsl:if>
                <xsl:if test="inp3:originalBYEId">
                  <inp3:originalBYEId>
                    <xsl:value-of select="inp3:originalBYEId"/>
                  </inp3:originalBYEId>
                </xsl:if>
                <xsl:if test="inp3:rollType">
                  <inp3:rollType>
                    <xsl:value-of select="inp3:rollType"/>
                  </inp3:rollType>
                </xsl:if>
                <xsl:if test="inp3:transactionType">
                  <inp3:transactionType>
                    <xsl:value-of select="inp3:transactionType"/>
                  </inp3:transactionType>
                </xsl:if>
                <xsl:if test="inp3:userId">
                  <inp3:userId>
                    <xsl:value-of select="inp3:userId"/>
                  </inp3:userId>
                </xsl:if>
                <xsl:if test="inp3:newConstructionDate">
                  <inp3:newConstructionDate>
                    <xsl:value-of select="inp3:newConstructionDate"/>
                  </inp3:newConstructionDate>
                </xsl:if>
                <xsl:if test="inp3:createBy">
                  <inp3:createBy>
                    <xsl:value-of select="inp3:createBy"/>
                  </inp3:createBy>
                </xsl:if>
                <xsl:if test="inp3:createdDate">
                  <inp3:createdDate>
                    <xsl:value-of select="inp3:createdDate"/>
                  </inp3:createdDate>
                </xsl:if>
                <xsl:if test="inp3:modifiedBy">
                  <inp3:modifiedBy>
                    <xsl:value-of select="inp3:modifiedBy"/>
                  </inp3:modifiedBy>
                </xsl:if>
                <xsl:if test="inp3:inactiveDate">
                  <inp3:inactiveDate>
                    <xsl:value-of select="inp3:inactiveDate"/>
                  </inp3:inactiveDate>
                </xsl:if>
                <xsl:if test="inp3:modifiedDate">
                  <inp3:modifiedDate>
                    <xsl:value-of select="inp3:modifiedDate"/>
                  </inp3:modifiedDate>
                </xsl:if>
                <xsl:if test="inp3:currentOnClose">
                  <inp3:currentOnClose>
                    <xsl:value-of select="inp3:currentOnClose"/>
                  </inp3:currentOnClose>
                </xsl:if>
                <xsl:if test="inp3:notes">
                  <inp3:notes>
                    <xsl:value-of select="inp3:notes"/>
                  </inp3:notes>
                </xsl:if>
                <inp3:asmtRollId>
                  <xsl:value-of select="inp3:asmtRollId"/>
                </inp3:asmtRollId>
              </ns1:baseYearEvent>
            </xsl:for-each>
          </ns1:baseYearEventList>
        </ns1:BYEDetailsOriginal>
        <ns1:BYEDetailsUserModified>
          <ns1:header>
            <inp2:operationStatus>
              <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:header/inp2:operationStatus"/>
            </inp2:operationStatus>
            <inp2:instanceId>
              <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:header/inp2:instanceId"/>
            </inp2:instanceId>
            <inp2:requestId>
              <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:header/inp2:requestId"/>
            </inp2:requestId>
          </ns1:header>
          <ns1:ain>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:ain"/>
          </ns1:ain>
          <ns1:aoid>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:aoid"/>
          </ns1:aoid>
          <ns1:ainType>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:ainType"/>
          </ns1:ainType>
          <ns1:reviewRequired>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:reviewRequired"/>
          </ns1:reviewRequired>
          <ns1:processStatus>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:processStatus"/>
          </ns1:processStatus>
          <ns1:ecid>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:ecid"/>
          </ns1:ecid>
          <ns1:dbLockStatus>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:dbLockStatus"/>
          </ns1:dbLockStatus>
          <ns1:lockedByUserId>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:lockedByUserId"/>
          </ns1:lockedByUserId>
          <ns1:districtId>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:districtId"/>
          </ns1:districtId>
          <ns1:districtName>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:districtName"/>
          </ns1:districtName>
          <ns1:region>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:region"/>
          </ns1:region>
          <ns1:regionName>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:regionName"/>
          </ns1:regionName>
          <ns1:clusterRouteId>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:clusterRouteId"/>
          </ns1:clusterRouteId>
          <ns1:clusterRoute>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:clusterRoute"/>
          </ns1:clusterRoute>
          <ns1:clusterRouteName>
            <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:clusterRouteName"/>
          </ns1:clusterRouteName>
          <ns1:baseYearEventList>
            <xsl:for-each select="/tns:retrieveBaseYearEventsResponse/tns:baseYearEventList/tns:baseYearEvent">
              <ns1:baseYearEvent>
                <inp3:ain>
                  <xsl:value-of select="inp3:ain"/>
                </inp3:ain>
                <xsl:if test="inp3:aoid">
                  <inp3:aoid>
                    <xsl:value-of select="inp3:aoid"/>
                  </inp3:aoid>
                </xsl:if>
                <xsl:if test="inp3:reviewRequired">
                  <inp3:reviewRequired>
                    <xsl:value-of select="inp3:reviewRequired"/>
                  </inp3:reviewRequired>
                </xsl:if>
                <inp3:byeId>
                  <xsl:value-of select="inp3:byeId"/>
                </inp3:byeId>
                <xsl:if test="inp3:ownershipCode">
                  <inp3:ownershipCode>
                    <xsl:value-of select="inp3:ownershipCode"/>
                  </inp3:ownershipCode>
                </xsl:if>
                <xsl:if test="inp3:percentOwnership">
                  <inp3:percentOwnership>
                    <xsl:value-of select="inp3:percentOwnership"/>
                  </inp3:percentOwnership>
                </xsl:if>
                <xsl:if test="inp3:eventNameId">
                  <inp3:eventNameId>
                    <xsl:value-of select="inp3:eventNameId"/>
                  </inp3:eventNameId>
                </xsl:if>
                <xsl:if test="inp3:eventNameKey">
                  <inp3:eventNameKey>
                    <xsl:value-of select="inp3:eventNameKey"/>
                  </inp3:eventNameKey>
                </xsl:if>
                <xsl:if test="inp3:eventNameLegend">
                  <inp3:eventNameLegend>
                    <xsl:value-of select="inp3:eventNameLegend"/>
                  </inp3:eventNameLegend>
                </xsl:if>
                <xsl:if test="inp3:eventDescriptionId">
                  <inp3:eventDescriptionId>
                    <xsl:value-of select="inp3:eventDescriptionId"/>
                  </inp3:eventDescriptionId>
                </xsl:if>
                <xsl:if test="inp3:eventDescriptionKey">
                  <inp3:eventDescriptionKey>
                    <xsl:value-of select="inp3:eventDescriptionKey"/>
                  </inp3:eventDescriptionKey>
                </xsl:if>
                <xsl:if test="inp3:eventDescriptionLegend">
                  <inp3:eventDescriptionLegend>
                    <xsl:value-of select="inp3:eventDescriptionLegend"/>
                  </inp3:eventDescriptionLegend>
                </xsl:if>
                <xsl:if test="inp3:eventDate">
                  <inp3:eventDate>
                    <xsl:value-of select="inp3:eventDate"/>
                  </inp3:eventDate>
                </xsl:if>
                <xsl:if test="inp3:recordingDate">
                  <inp3:recordingDate>
                    <xsl:value-of select="inp3:recordingDate"/>
                  </inp3:recordingDate>
                </xsl:if>
                <xsl:if test="inp3:documentNumber">
                  <inp3:documentNumber>
                    <xsl:value-of select="inp3:documentNumber"/>
                  </inp3:documentNumber>
                </xsl:if>
                <xsl:if test="inp3:sequenceNumber">
                  <inp3:sequenceNumber>
                    <xsl:value-of select="inp3:sequenceNumber"/>
                  </inp3:sequenceNumber>
                </xsl:if>
                <xsl:if test="inp3:baseYear">
                  <inp3:baseYear>
                    <xsl:value-of select="inp3:baseYear"/>
                  </inp3:baseYear>
                </xsl:if>
                <xsl:if test="inp3:subUnitNumber">
                  <inp3:subUnitNumber>
                    <xsl:value-of select="inp3:subUnitNumber"/>
                  </inp3:subUnitNumber>
                </xsl:if>
                <xsl:if test="inp3:subUnitDescription">
                  <inp3:subUnitDescription>
                    <xsl:value-of select="inp3:subUnitDescription"/>
                  </inp3:subUnitDescription>
                </xsl:if>
                <xsl:if test="inp3:partiallyCompleteNumber">
                  <inp3:partiallyCompleteNumber>
                    <xsl:value-of select="inp3:partiallyCompleteNumber"/>
                  </inp3:partiallyCompleteNumber>
                </xsl:if>
                <xsl:if test="inp3:blendedValueFlag">
                  <inp3:blendedValueFlag>
                    <xsl:value-of select="inp3:blendedValueFlag"/>
                  </inp3:blendedValueFlag>
                </xsl:if>
                <xsl:if test="inp3:percentageAppliedToValue">
                  <inp3:percentageAppliedToValue>
                    <xsl:value-of select="inp3:percentageAppliedToValue"/>
                  </inp3:percentageAppliedToValue>
                </xsl:if>
                <xsl:if test="inp3:landValue">
                  <inp3:landValue>
                    <xsl:value-of select="inp3:landValue"/>
                  </inp3:landValue>
                </xsl:if>
                <xsl:if test="inp3:improvementValue">
                  <inp3:improvementValue>
                    <xsl:value-of select="inp3:improvementValue"/>
                  </inp3:improvementValue>
                </xsl:if>
                <xsl:if test="inp3:landReasonCodeId">
                  <inp3:landReasonCodeId>
                    <xsl:value-of select="inp3:landReasonCodeId"/>
                  </inp3:landReasonCodeId>
                </xsl:if>
                <xsl:if test="inp3:landReasonCodeKey">
                  <inp3:landReasonCodeKey>
                    <xsl:value-of select="inp3:landReasonCodeKey"/>
                  </inp3:landReasonCodeKey>
                </xsl:if>
                <xsl:if test="inp3:landReasonCodeLegend">
                  <inp3:landReasonCodeLegend>
                    <xsl:value-of select="inp3:landReasonCodeLegend"/>
                  </inp3:landReasonCodeLegend>
                </xsl:if>
                <xsl:if test="inp3:improvementReasonCodeId">
                  <inp3:improvementReasonCodeId>
                    <xsl:value-of select="inp3:improvementReasonCodeId"/>
                  </inp3:improvementReasonCodeId>
                </xsl:if>
                <xsl:if test="inp3:improvementReasonCodeKey">
                  <inp3:improvementReasonCodeKey>
                    <xsl:value-of select="inp3:improvementReasonCodeKey"/>
                  </inp3:improvementReasonCodeKey>
                </xsl:if>
                <xsl:if test="inp3:improvementReasonCodeLegend">
                  <inp3:improvementReasonCodeLegend>
                    <xsl:value-of select="inp3:improvementReasonCodeLegend"/>
                  </inp3:improvementReasonCodeLegend>
                </xsl:if>
                <xsl:if test="inp3:fixtureValue">
                  <inp3:fixtureValue>
                    <xsl:value-of select="inp3:fixtureValue"/>
                  </inp3:fixtureValue>
                </xsl:if>
                <xsl:if test="inp3:retainedLandValue">
                  <inp3:retainedLandValue>
                    <xsl:value-of select="inp3:retainedLandValue"/>
                  </inp3:retainedLandValue>
                </xsl:if>
                <xsl:if test="inp3:retainedImprovementValue">
                  <inp3:retainedImprovementValue>
                    <xsl:value-of select="inp3:retainedImprovementValue"/>
                  </inp3:retainedImprovementValue>
                </xsl:if>
                <xsl:if test="inp3:retainedFixtureValue">
                  <inp3:retainedFixtureValue>
                    <xsl:value-of select="inp3:retainedFixtureValue"/>
                  </inp3:retainedFixtureValue>
                </xsl:if>
                <xsl:if test="inp3:ppAssessmentNumber">
                  <inp3:ppAssessmentNumber>
                    <xsl:value-of select="inp3:ppAssessmentNumber"/>
                  </inp3:ppAssessmentNumber>
                </xsl:if>
                <xsl:if test="inp3:personalPropertyType">
                  <inp3:personalPropertyType>
                    <xsl:value-of select="inp3:personalPropertyType"/>
                  </inp3:personalPropertyType>
                </xsl:if>
                <xsl:if test="inp3:ppDescription">
                  <inp3:ppDescription>
                    <xsl:value-of select="inp3:ppDescription"/>
                  </inp3:ppDescription>
                </xsl:if>
                <xsl:if test="inp3:totalAdjAcquisitionCost">
                  <inp3:totalAdjAcquisitionCost>
                    <xsl:value-of select="inp3:totalAdjAcquisitionCost"/>
                  </inp3:totalAdjAcquisitionCost>
                </xsl:if>
                <xsl:if test="inp3:percentageFixture">
                  <inp3:percentageFixture>
                    <xsl:value-of select="inp3:percentageFixture"/>
                  </inp3:percentageFixture>
                </xsl:if>
                <xsl:if test="inp3:effectiveBeginDate">
                  <inp3:effectiveBeginDate>
                    <xsl:value-of select="inp3:effectiveBeginDate"/>
                  </inp3:effectiveBeginDate>
                </xsl:if>
                <xsl:if test="inp3:effectiveEndDate">
                  <inp3:effectiveEndDate>
                    <xsl:value-of select="inp3:effectiveEndDate"/>
                  </inp3:effectiveEndDate>
                </xsl:if>
                <xsl:if test="inp3:verificationDate">
                  <inp3:verificationDate>
                    <xsl:value-of select="inp3:verificationDate"/>
                  </inp3:verificationDate>
                </xsl:if>
                <xsl:if test="inp3:eventSource">
                  <inp3:eventSource>
                    <xsl:value-of select="inp3:eventSource"/>
                  </inp3:eventSource>
                </xsl:if>
                <xsl:if test="inp3:verifiedBy">
                  <inp3:verifiedBy>
                    <xsl:value-of select="inp3:verifiedBy"/>
                  </inp3:verifiedBy>
                </xsl:if>
                <xsl:if test="inp3:byeLegacySourceSystem">
                  <inp3:byeLegacySourceSystem>
                    <xsl:value-of select="inp3:byeLegacySourceSystem"/>
                  </inp3:byeLegacySourceSystem>
                </xsl:if>
                <xsl:if test="inp3:originalAIN">
                  <inp3:originalAIN>
                    <xsl:value-of select="inp3:originalAIN"/>
                  </inp3:originalAIN>
                </xsl:if>
                <xsl:if test="inp3:originalBYEId">
                  <inp3:originalBYEId>
                    <xsl:value-of select="inp3:originalBYEId"/>
                  </inp3:originalBYEId>
                </xsl:if>
                <xsl:if test="inp3:rollType">
                  <inp3:rollType>
                    <xsl:value-of select="inp3:rollType"/>
                  </inp3:rollType>
                </xsl:if>
                <xsl:if test="inp3:transactionType">
                  <inp3:transactionType>
                    <xsl:value-of select="inp3:transactionType"/>
                  </inp3:transactionType>
                </xsl:if>
                <xsl:if test="inp3:userId">
                  <inp3:userId>
                    <xsl:value-of select="inp3:userId"/>
                  </inp3:userId>
                </xsl:if>
                <xsl:if test="inp3:newConstructionDate">
                  <inp3:newConstructionDate>
                    <xsl:value-of select="inp3:newConstructionDate"/>
                  </inp3:newConstructionDate>
                </xsl:if>
                <xsl:if test="inp3:createBy">
                  <inp3:createBy>
                    <xsl:value-of select="inp3:createBy"/>
                  </inp3:createBy>
                </xsl:if>
                <xsl:if test="inp3:createdDate">
                  <inp3:createdDate>
                    <xsl:value-of select="inp3:createdDate"/>
                  </inp3:createdDate>
                </xsl:if>
                <xsl:if test="inp3:modifiedBy">
                  <inp3:modifiedBy>
                    <xsl:value-of select="inp3:modifiedBy"/>
                  </inp3:modifiedBy>
                </xsl:if>
                <xsl:if test="inp3:inactiveDate">
                  <inp3:inactiveDate>
                    <xsl:value-of select="inp3:inactiveDate"/>
                  </inp3:inactiveDate>
                </xsl:if>
                <xsl:if test="inp3:modifiedDate">
                  <inp3:modifiedDate>
                    <xsl:value-of select="inp3:modifiedDate"/>
                  </inp3:modifiedDate>
                </xsl:if>
                <xsl:if test="inp3:currentOnClose">
                  <inp3:currentOnClose>
                    <xsl:value-of select="inp3:currentOnClose"/>
                  </inp3:currentOnClose>
                </xsl:if>
                <xsl:if test="inp3:notes">
                  <inp3:notes>
                    <xsl:value-of select="inp3:notes"/>
                  </inp3:notes>
                </xsl:if>
              </ns1:baseYearEvent>
            </xsl:for-each>
          </ns1:baseYearEventList>
        </ns1:BYEDetailsUserModified>
      </ns1:RetrieveBYEDetails>
      <ns0:sortBYE>
        <ns2:ain>
          <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:ain"/>
        </ns2:ain>
        <ns2:district>
          <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:districtName"/>
        </ns2:district>
        <ns2:region>
          <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:region"/>
        </ns2:region>
        <ns2:cluster>
          <xsl:value-of select="/tns:retrieveBaseYearEventsResponse/tns:clusterRoute"/>
        </ns2:cluster>
      </ns0:sortBYE>
    </ns0:RetriveBYEDetails>
  </xsl:template>
</xsl:stylesheet>