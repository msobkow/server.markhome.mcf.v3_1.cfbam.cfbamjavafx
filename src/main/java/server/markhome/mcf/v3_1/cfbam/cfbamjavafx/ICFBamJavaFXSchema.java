// Description: Java 25 JavaFX Schema Interface for CFBam.

/*
 *	server.markhome.mcf.CFBam
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal 3.1 CFBam - Business Application Model
 *	
 *	This file is part of Mark's Code Fractal CFBam.
 *	
 *	Mark's Code Fractal CFBam is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU General Public License,
 *	Version 3 or later.
 *	
 *	Mark's Code Fractal CFBam is free software: you can redistribute it and/or
 *	modify it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation, either version 3 of the License, or
 *	(at your option) any later version.
 *	
 *	Mark's Code Fractal CFBam is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *	
 *	You should have received a copy of the GNU General Public License
 *	along with Mark's Code Fractal CFBam.  If not, see <https://www.gnu.org/licenses/>.
 *	
 *	If you wish to modify and use this code without publishing your changes,
 *	or integrate it with proprietary code, please contact Mark Stephen Sobkow
 *	for a commercial license at mark.sobkow@gmail.com
 *	
 */

package server.markhome.mcf.v3_1.cfbam.cfbamjavafx;

import java.math.*;
import java.time.*;
import java.text.*;
import java.util.*;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.inz.Inz;
import server.markhome.mcf.v3_1.cflib.javafx.*;
import org.apache.commons.codec.binary.Base64;
import server.markhome.mcf.v3_1.cfsec.cfsec.*;
import server.markhome.mcf.v3_1.cfint.cfint.*;
import server.markhome.mcf.v3_1.cfbam.cfbam.*;
import server.markhome.mcf.v3_1.cfsec.cfsecobj.*;
import server.markhome.mcf.v3_1.cfint.cfintobj.*;
import server.markhome.mcf.v3_1.cfbam.cfbamobj.*;
import server.markhome.mcf.v3_1.cfsec.cfsecjavafx.*;
import server.markhome.mcf.v3_1.cfint.cfintjavafx.*;

/**
 *	The ICFBamJavaFXSchema defines the factory interface for the
 *	for the various panes and components used to construct a GUI interface
 *	using the JavaFX framework.
 */
public interface ICFBamJavaFXSchema
	extends ICFSecJavaFXSchema,
		ICFIntJavaFXSchema
{
	ICFSecSchemaObj getSchema();
	void setSchema( ICFSecSchemaObj argSchema );

	String getClusterName();
	void setClusterName( String argClusterName );
	ICFSecClusterObj getClusterObj();

	String getTenantName();
	void setTenantName( String argTenantName );
	ICFSecTenantObj getTenantObj();

	String getSecUserName();
	void setSecUserName( String argSecUserName );
	ICFSecSecUserObj getSecUserObj();

	ICFSecSecSessionObj getSecSessionObj();

	public ICFBamJavaFXAtomFactory getAtomFactory();

	public ICFBamJavaFXBlobColFactory getBlobColFactory();

	public ICFBamJavaFXBlobDefFactory getBlobDefFactory();

	public ICFBamJavaFXBlobTypeFactory getBlobTypeFactory();

	public ICFBamJavaFXBoolColFactory getBoolColFactory();

	public ICFBamJavaFXBoolDefFactory getBoolDefFactory();

	public ICFBamJavaFXBoolTypeFactory getBoolTypeFactory();

	public ICFBamJavaFXChainFactory getChainFactory();

	public ICFBamJavaFXClearDepFactory getClearDepFactory();

	public ICFBamJavaFXClearSubDep1Factory getClearSubDep1Factory();

	public ICFBamJavaFXClearSubDep2Factory getClearSubDep2Factory();

	public ICFBamJavaFXClearSubDep3Factory getClearSubDep3Factory();

	public ICFBamJavaFXClearTopDepFactory getClearTopDepFactory();

	public ICFSecJavaFXClusterFactory getClusterFactory();

	public ICFBamJavaFXDateColFactory getDateColFactory();

	public ICFBamJavaFXDateDefFactory getDateDefFactory();

	public ICFBamJavaFXDateTypeFactory getDateTypeFactory();

	public ICFBamJavaFXDbKeyHash128ColFactory getDbKeyHash128ColFactory();

	public ICFBamJavaFXDbKeyHash128DefFactory getDbKeyHash128DefFactory();

	public ICFBamJavaFXDbKeyHash128GenFactory getDbKeyHash128GenFactory();

	public ICFBamJavaFXDbKeyHash128TypeFactory getDbKeyHash128TypeFactory();

	public ICFBamJavaFXDbKeyHash160ColFactory getDbKeyHash160ColFactory();

	public ICFBamJavaFXDbKeyHash160DefFactory getDbKeyHash160DefFactory();

	public ICFBamJavaFXDbKeyHash160GenFactory getDbKeyHash160GenFactory();

	public ICFBamJavaFXDbKeyHash160TypeFactory getDbKeyHash160TypeFactory();

	public ICFBamJavaFXDbKeyHash224ColFactory getDbKeyHash224ColFactory();

	public ICFBamJavaFXDbKeyHash224DefFactory getDbKeyHash224DefFactory();

	public ICFBamJavaFXDbKeyHash224GenFactory getDbKeyHash224GenFactory();

	public ICFBamJavaFXDbKeyHash224TypeFactory getDbKeyHash224TypeFactory();

	public ICFBamJavaFXDbKeyHash256ColFactory getDbKeyHash256ColFactory();

	public ICFBamJavaFXDbKeyHash256DefFactory getDbKeyHash256DefFactory();

	public ICFBamJavaFXDbKeyHash256GenFactory getDbKeyHash256GenFactory();

	public ICFBamJavaFXDbKeyHash256TypeFactory getDbKeyHash256TypeFactory();

	public ICFBamJavaFXDbKeyHash384ColFactory getDbKeyHash384ColFactory();

	public ICFBamJavaFXDbKeyHash384DefFactory getDbKeyHash384DefFactory();

	public ICFBamJavaFXDbKeyHash384GenFactory getDbKeyHash384GenFactory();

	public ICFBamJavaFXDbKeyHash384TypeFactory getDbKeyHash384TypeFactory();

	public ICFBamJavaFXDbKeyHash512ColFactory getDbKeyHash512ColFactory();

	public ICFBamJavaFXDbKeyHash512DefFactory getDbKeyHash512DefFactory();

	public ICFBamJavaFXDbKeyHash512GenFactory getDbKeyHash512GenFactory();

	public ICFBamJavaFXDbKeyHash512TypeFactory getDbKeyHash512TypeFactory();

	public ICFBamJavaFXDelDepFactory getDelDepFactory();

	public ICFBamJavaFXDelSubDep1Factory getDelSubDep1Factory();

	public ICFBamJavaFXDelSubDep2Factory getDelSubDep2Factory();

	public ICFBamJavaFXDelSubDep3Factory getDelSubDep3Factory();

	public ICFBamJavaFXDelTopDepFactory getDelTopDepFactory();

	public ICFBamJavaFXDoubleColFactory getDoubleColFactory();

	public ICFBamJavaFXDoubleDefFactory getDoubleDefFactory();

	public ICFBamJavaFXDoubleTypeFactory getDoubleTypeFactory();

	public ICFBamJavaFXEnumDefFactory getEnumDefFactory();

	public ICFBamJavaFXEnumTagFactory getEnumTagFactory();

	public ICFBamJavaFXEnumTypeFactory getEnumTypeFactory();

	public ICFBamJavaFXFloatColFactory getFloatColFactory();

	public ICFBamJavaFXFloatDefFactory getFloatDefFactory();

	public ICFBamJavaFXFloatTypeFactory getFloatTypeFactory();

	public ICFSecJavaFXHostNodeFactory getHostNodeFactory();

	public ICFSecJavaFXISOCcyFactory getISOCcyFactory();

	public ICFSecJavaFXISOCtryFactory getISOCtryFactory();

	public ICFSecJavaFXISOCtryCcyFactory getISOCtryCcyFactory();

	public ICFSecJavaFXISOCtryLangFactory getISOCtryLangFactory();

	public ICFSecJavaFXISOLangFactory getISOLangFactory();

	public ICFSecJavaFXISOTZoneFactory getISOTZoneFactory();

	public ICFBamJavaFXId16GenFactory getId16GenFactory();

	public ICFBamJavaFXId32GenFactory getId32GenFactory();

	public ICFBamJavaFXId64GenFactory getId64GenFactory();

	public ICFBamJavaFXIndexFactory getIndexFactory();

	public ICFBamJavaFXIndexColFactory getIndexColFactory();

	public ICFBamJavaFXInt16ColFactory getInt16ColFactory();

	public ICFBamJavaFXInt16DefFactory getInt16DefFactory();

	public ICFBamJavaFXInt16TypeFactory getInt16TypeFactory();

	public ICFBamJavaFXInt32ColFactory getInt32ColFactory();

	public ICFBamJavaFXInt32DefFactory getInt32DefFactory();

	public ICFBamJavaFXInt32TypeFactory getInt32TypeFactory();

	public ICFBamJavaFXInt64ColFactory getInt64ColFactory();

	public ICFBamJavaFXInt64DefFactory getInt64DefFactory();

	public ICFBamJavaFXInt64TypeFactory getInt64TypeFactory();

	public ICFIntJavaFXLicenseFactory getLicenseFactory();

	public ICFIntJavaFXMajorVersionFactory getMajorVersionFactory();

	public ICFIntJavaFXMimeTypeFactory getMimeTypeFactory();

	public ICFIntJavaFXMinorVersionFactory getMinorVersionFactory();

	public ICFBamJavaFXNmTokenColFactory getNmTokenColFactory();

	public ICFBamJavaFXNmTokenDefFactory getNmTokenDefFactory();

	public ICFBamJavaFXNmTokenTypeFactory getNmTokenTypeFactory();

	public ICFBamJavaFXNmTokensColFactory getNmTokensColFactory();

	public ICFBamJavaFXNmTokensDefFactory getNmTokensDefFactory();

	public ICFBamJavaFXNmTokensTypeFactory getNmTokensTypeFactory();

	public ICFBamJavaFXNumberColFactory getNumberColFactory();

	public ICFBamJavaFXNumberDefFactory getNumberDefFactory();

	public ICFBamJavaFXNumberTypeFactory getNumberTypeFactory();

	public ICFBamJavaFXParamFactory getParamFactory();

	public ICFBamJavaFXPopDepFactory getPopDepFactory();

	public ICFBamJavaFXPopSubDep1Factory getPopSubDep1Factory();

	public ICFBamJavaFXPopSubDep2Factory getPopSubDep2Factory();

	public ICFBamJavaFXPopSubDep3Factory getPopSubDep3Factory();

	public ICFBamJavaFXPopTopDepFactory getPopTopDepFactory();

	public ICFBamJavaFXRelationFactory getRelationFactory();

	public ICFBamJavaFXRelationColFactory getRelationColFactory();

	public ICFBamJavaFXSchemaDefFactory getSchemaDefFactory();

	public ICFBamJavaFXSchemaRefFactory getSchemaRefFactory();

	public ICFBamJavaFXScopeFactory getScopeFactory();

	public ICFSecJavaFXSecDeviceFactory getSecDeviceFactory();

	public ICFSecJavaFXSecGroupFactory getSecGroupFactory();

	public ICFSecJavaFXSecGrpIncFactory getSecGrpIncFactory();

	public ICFSecJavaFXSecGrpMembFactory getSecGrpMembFactory();

	public ICFSecJavaFXSecSessionFactory getSecSessionFactory();

	public ICFSecJavaFXSecUserFactory getSecUserFactory();

	public ICFBamJavaFXServerListFuncFactory getServerListFuncFactory();

	public ICFBamJavaFXServerMethodFactory getServerMethodFactory();

	public ICFBamJavaFXServerObjFuncFactory getServerObjFuncFactory();

	public ICFBamJavaFXServerProcFactory getServerProcFactory();

	public ICFSecJavaFXServiceFactory getServiceFactory();

	public ICFSecJavaFXServiceTypeFactory getServiceTypeFactory();

	public ICFBamJavaFXStringColFactory getStringColFactory();

	public ICFBamJavaFXStringDefFactory getStringDefFactory();

	public ICFBamJavaFXStringTypeFactory getStringTypeFactory();

	public ICFIntJavaFXSubProjectFactory getSubProjectFactory();

	public ICFSecJavaFXSysClusterFactory getSysClusterFactory();

	public ICFSecJavaFXTSecGroupFactory getTSecGroupFactory();

	public ICFSecJavaFXTSecGrpIncFactory getTSecGrpIncFactory();

	public ICFSecJavaFXTSecGrpMembFactory getTSecGrpMembFactory();

	public ICFBamJavaFXTZDateColFactory getTZDateColFactory();

	public ICFBamJavaFXTZDateDefFactory getTZDateDefFactory();

	public ICFBamJavaFXTZDateTypeFactory getTZDateTypeFactory();

	public ICFBamJavaFXTZTimeColFactory getTZTimeColFactory();

	public ICFBamJavaFXTZTimeDefFactory getTZTimeDefFactory();

	public ICFBamJavaFXTZTimeTypeFactory getTZTimeTypeFactory();

	public ICFBamJavaFXTZTimestampColFactory getTZTimestampColFactory();

	public ICFBamJavaFXTZTimestampDefFactory getTZTimestampDefFactory();

	public ICFBamJavaFXTZTimestampTypeFactory getTZTimestampTypeFactory();

	public ICFBamJavaFXTableFactory getTableFactory();

	public ICFBamJavaFXTableColFactory getTableColFactory();

	public ICFSecJavaFXTenantFactory getTenantFactory();

	public ICFBamJavaFXTextColFactory getTextColFactory();

	public ICFBamJavaFXTextDefFactory getTextDefFactory();

	public ICFBamJavaFXTextTypeFactory getTextTypeFactory();

	public ICFBamJavaFXTimeColFactory getTimeColFactory();

	public ICFBamJavaFXTimeDefFactory getTimeDefFactory();

	public ICFBamJavaFXTimeTypeFactory getTimeTypeFactory();

	public ICFBamJavaFXTimestampColFactory getTimestampColFactory();

	public ICFBamJavaFXTimestampDefFactory getTimestampDefFactory();

	public ICFBamJavaFXTimestampTypeFactory getTimestampTypeFactory();

	public ICFIntJavaFXTldFactory getTldFactory();

	public ICFBamJavaFXTokenColFactory getTokenColFactory();

	public ICFBamJavaFXTokenDefFactory getTokenDefFactory();

	public ICFBamJavaFXTokenTypeFactory getTokenTypeFactory();

	public ICFIntJavaFXTopDomainFactory getTopDomainFactory();

	public ICFIntJavaFXTopProjectFactory getTopProjectFactory();

	public ICFBamJavaFXUInt16ColFactory getUInt16ColFactory();

	public ICFBamJavaFXUInt16DefFactory getUInt16DefFactory();

	public ICFBamJavaFXUInt16TypeFactory getUInt16TypeFactory();

	public ICFBamJavaFXUInt32ColFactory getUInt32ColFactory();

	public ICFBamJavaFXUInt32DefFactory getUInt32DefFactory();

	public ICFBamJavaFXUInt32TypeFactory getUInt32TypeFactory();

	public ICFBamJavaFXUInt64ColFactory getUInt64ColFactory();

	public ICFBamJavaFXUInt64DefFactory getUInt64DefFactory();

	public ICFBamJavaFXUInt64TypeFactory getUInt64TypeFactory();

	public ICFIntJavaFXURLProtocolFactory getURLProtocolFactory();

	public ICFBamJavaFXUuid6ColFactory getUuid6ColFactory();

	public ICFBamJavaFXUuid6DefFactory getUuid6DefFactory();

	public ICFBamJavaFXUuid6GenFactory getUuid6GenFactory();

	public ICFBamJavaFXUuid6TypeFactory getUuid6TypeFactory();

	public ICFBamJavaFXUuidColFactory getUuidColFactory();

	public ICFBamJavaFXUuidDefFactory getUuidDefFactory();

	public ICFBamJavaFXUuidGenFactory getUuidGenFactory();

	public ICFBamJavaFXUuidTypeFactory getUuidTypeFactory();

	public ICFBamJavaFXValueFactory getValueFactory();
}
