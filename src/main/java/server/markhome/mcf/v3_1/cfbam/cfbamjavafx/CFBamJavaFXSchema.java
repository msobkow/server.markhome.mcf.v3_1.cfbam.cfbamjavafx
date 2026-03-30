// Description: Java 25 JavaFX Schema for CFBam.

/*
 *	server.markhome.mcf.CFBam
 *
 *	Copyright (c) 2016-2026 Mark Stephen Sobkow
 *	
 *	Mark's Code Fractal CFBam 3.1 Business Application Model
 *	
 *	Copyright 2016-2026 Mark Stephen Sobkow
 *	
 *	This file is part of Mark's Code Fractal CFBam.
 *	
 *	Mark's Code Fractal CFBam is available under dual commercial license from
 *	Mark Stephen Sobkow, or under the terms of the GNU General Public License,
 *	Version 3 or later with classpath and static linking exceptions.
 *	
 *	As a special exception, Mark Sobkow gives you permission to link this library
 *	with independent modules to produce an executable, provided that none of them
 *	conflict with the intent of the GPLv3; that is, you are not allowed to invoke
 *	the methods of this library from non-GPLv3-compatibly licensed code. You may not
 *	implement an LPGLv3 "wedge" to try to bypass this restriction. That said, code which
 *	does not rely on this library is free to specify whatever license its authors decide
 *	to use. Mark Sobkow specifically rejects the infectious nature of the GPLv3, and
 *	considers the mere act of including GPLv3 modules in an executable to be perfectly
 *	reasonable given tools like modern Java's single-jar deployment options.
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

public class CFBamJavaFXSchema
implements ICFBamJavaFXSchema
{
	protected ICFBamSchemaObj schema = null;
	protected String clusterName = "system";
	protected ICFSecClusterObj clusterObj = null;
	protected String tenantName = "system";
	protected ICFSecTenantObj tenantObj = null;
	protected String secUserName = "system";
	protected ICFSecSecUserObj secUserObj = null;
	protected ICFSecSecSessionObj secSessionObj = null;
	protected ICFBamJavaFXAtomFactory factoryAtom = null;
	protected ICFBamJavaFXBlobColFactory factoryBlobCol = null;
	protected ICFBamJavaFXBlobDefFactory factoryBlobDef = null;
	protected ICFBamJavaFXBlobTypeFactory factoryBlobType = null;
	protected ICFBamJavaFXBoolColFactory factoryBoolCol = null;
	protected ICFBamJavaFXBoolDefFactory factoryBoolDef = null;
	protected ICFBamJavaFXBoolTypeFactory factoryBoolType = null;
	protected ICFBamJavaFXChainFactory factoryChain = null;
	protected ICFBamJavaFXClearDepFactory factoryClearDep = null;
	protected ICFBamJavaFXClearSubDep1Factory factoryClearSubDep1 = null;
	protected ICFBamJavaFXClearSubDep2Factory factoryClearSubDep2 = null;
	protected ICFBamJavaFXClearSubDep3Factory factoryClearSubDep3 = null;
	protected ICFBamJavaFXClearTopDepFactory factoryClearTopDep = null;
	protected ICFSecJavaFXClusterFactory factoryCluster = null;
	protected ICFBamJavaFXDateColFactory factoryDateCol = null;
	protected ICFBamJavaFXDateDefFactory factoryDateDef = null;
	protected ICFBamJavaFXDateTypeFactory factoryDateType = null;
	protected ICFBamJavaFXDbKeyHash128ColFactory factoryDbKeyHash128Col = null;
	protected ICFBamJavaFXDbKeyHash128DefFactory factoryDbKeyHash128Def = null;
	protected ICFBamJavaFXDbKeyHash128GenFactory factoryDbKeyHash128Gen = null;
	protected ICFBamJavaFXDbKeyHash128TypeFactory factoryDbKeyHash128Type = null;
	protected ICFBamJavaFXDbKeyHash160ColFactory factoryDbKeyHash160Col = null;
	protected ICFBamJavaFXDbKeyHash160DefFactory factoryDbKeyHash160Def = null;
	protected ICFBamJavaFXDbKeyHash160GenFactory factoryDbKeyHash160Gen = null;
	protected ICFBamJavaFXDbKeyHash160TypeFactory factoryDbKeyHash160Type = null;
	protected ICFBamJavaFXDbKeyHash224ColFactory factoryDbKeyHash224Col = null;
	protected ICFBamJavaFXDbKeyHash224DefFactory factoryDbKeyHash224Def = null;
	protected ICFBamJavaFXDbKeyHash224GenFactory factoryDbKeyHash224Gen = null;
	protected ICFBamJavaFXDbKeyHash224TypeFactory factoryDbKeyHash224Type = null;
	protected ICFBamJavaFXDbKeyHash256ColFactory factoryDbKeyHash256Col = null;
	protected ICFBamJavaFXDbKeyHash256DefFactory factoryDbKeyHash256Def = null;
	protected ICFBamJavaFXDbKeyHash256GenFactory factoryDbKeyHash256Gen = null;
	protected ICFBamJavaFXDbKeyHash256TypeFactory factoryDbKeyHash256Type = null;
	protected ICFBamJavaFXDbKeyHash384ColFactory factoryDbKeyHash384Col = null;
	protected ICFBamJavaFXDbKeyHash384DefFactory factoryDbKeyHash384Def = null;
	protected ICFBamJavaFXDbKeyHash384GenFactory factoryDbKeyHash384Gen = null;
	protected ICFBamJavaFXDbKeyHash384TypeFactory factoryDbKeyHash384Type = null;
	protected ICFBamJavaFXDbKeyHash512ColFactory factoryDbKeyHash512Col = null;
	protected ICFBamJavaFXDbKeyHash512DefFactory factoryDbKeyHash512Def = null;
	protected ICFBamJavaFXDbKeyHash512GenFactory factoryDbKeyHash512Gen = null;
	protected ICFBamJavaFXDbKeyHash512TypeFactory factoryDbKeyHash512Type = null;
	protected ICFBamJavaFXDelDepFactory factoryDelDep = null;
	protected ICFBamJavaFXDelSubDep1Factory factoryDelSubDep1 = null;
	protected ICFBamJavaFXDelSubDep2Factory factoryDelSubDep2 = null;
	protected ICFBamJavaFXDelSubDep3Factory factoryDelSubDep3 = null;
	protected ICFBamJavaFXDelTopDepFactory factoryDelTopDep = null;
	protected ICFBamJavaFXDoubleColFactory factoryDoubleCol = null;
	protected ICFBamJavaFXDoubleDefFactory factoryDoubleDef = null;
	protected ICFBamJavaFXDoubleTypeFactory factoryDoubleType = null;
	protected ICFBamJavaFXEnumDefFactory factoryEnumDef = null;
	protected ICFBamJavaFXEnumTagFactory factoryEnumTag = null;
	protected ICFBamJavaFXEnumTypeFactory factoryEnumType = null;
	protected ICFBamJavaFXFloatColFactory factoryFloatCol = null;
	protected ICFBamJavaFXFloatDefFactory factoryFloatDef = null;
	protected ICFBamJavaFXFloatTypeFactory factoryFloatType = null;
	protected ICFSecJavaFXISOCcyFactory factoryISOCcy = null;
	protected ICFSecJavaFXISOCtryFactory factoryISOCtry = null;
	protected ICFSecJavaFXISOCtryCcyFactory factoryISOCtryCcy = null;
	protected ICFSecJavaFXISOCtryLangFactory factoryISOCtryLang = null;
	protected ICFSecJavaFXISOLangFactory factoryISOLang = null;
	protected ICFSecJavaFXISOTZoneFactory factoryISOTZone = null;
	protected ICFBamJavaFXId16GenFactory factoryId16Gen = null;
	protected ICFBamJavaFXId32GenFactory factoryId32Gen = null;
	protected ICFBamJavaFXId64GenFactory factoryId64Gen = null;
	protected ICFBamJavaFXIndexFactory factoryIndex = null;
	protected ICFBamJavaFXIndexColFactory factoryIndexCol = null;
	protected ICFBamJavaFXInt16ColFactory factoryInt16Col = null;
	protected ICFBamJavaFXInt16DefFactory factoryInt16Def = null;
	protected ICFBamJavaFXInt16TypeFactory factoryInt16Type = null;
	protected ICFBamJavaFXInt32ColFactory factoryInt32Col = null;
	protected ICFBamJavaFXInt32DefFactory factoryInt32Def = null;
	protected ICFBamJavaFXInt32TypeFactory factoryInt32Type = null;
	protected ICFBamJavaFXInt64ColFactory factoryInt64Col = null;
	protected ICFBamJavaFXInt64DefFactory factoryInt64Def = null;
	protected ICFBamJavaFXInt64TypeFactory factoryInt64Type = null;
	protected ICFIntJavaFXLicenseFactory factoryLicense = null;
	protected ICFIntJavaFXMajorVersionFactory factoryMajorVersion = null;
	protected ICFIntJavaFXMimeTypeFactory factoryMimeType = null;
	protected ICFIntJavaFXMinorVersionFactory factoryMinorVersion = null;
	protected ICFBamJavaFXNmTokenColFactory factoryNmTokenCol = null;
	protected ICFBamJavaFXNmTokenDefFactory factoryNmTokenDef = null;
	protected ICFBamJavaFXNmTokenTypeFactory factoryNmTokenType = null;
	protected ICFBamJavaFXNmTokensColFactory factoryNmTokensCol = null;
	protected ICFBamJavaFXNmTokensDefFactory factoryNmTokensDef = null;
	protected ICFBamJavaFXNmTokensTypeFactory factoryNmTokensType = null;
	protected ICFBamJavaFXNumberColFactory factoryNumberCol = null;
	protected ICFBamJavaFXNumberDefFactory factoryNumberDef = null;
	protected ICFBamJavaFXNumberTypeFactory factoryNumberType = null;
	protected ICFBamJavaFXParamFactory factoryParam = null;
	protected ICFBamJavaFXPopDepFactory factoryPopDep = null;
	protected ICFBamJavaFXPopSubDep1Factory factoryPopSubDep1 = null;
	protected ICFBamJavaFXPopSubDep2Factory factoryPopSubDep2 = null;
	protected ICFBamJavaFXPopSubDep3Factory factoryPopSubDep3 = null;
	protected ICFBamJavaFXPopTopDepFactory factoryPopTopDep = null;
	protected ICFBamJavaFXRelationFactory factoryRelation = null;
	protected ICFBamJavaFXRelationColFactory factoryRelationCol = null;
	protected ICFBamJavaFXSchemaDefFactory factorySchemaDef = null;
	protected ICFBamJavaFXSchemaRefFactory factorySchemaRef = null;
	protected ICFBamJavaFXSchemaTweakFactory factorySchemaTweak = null;
	protected ICFBamJavaFXScopeFactory factoryScope = null;
	protected ICFSecJavaFXSecClusGrpFactory factorySecClusGrp = null;
	protected ICFSecJavaFXSecClusGrpIncFactory factorySecClusGrpInc = null;
	protected ICFSecJavaFXSecClusGrpMembFactory factorySecClusGrpMemb = null;
	protected ICFSecJavaFXSecSessionFactory factorySecSession = null;
	protected ICFSecJavaFXSecSysGrpFactory factorySecSysGrp = null;
	protected ICFSecJavaFXSecSysGrpIncFactory factorySecSysGrpInc = null;
	protected ICFSecJavaFXSecSysGrpMembFactory factorySecSysGrpMemb = null;
	protected ICFSecJavaFXSecTentGrpFactory factorySecTentGrp = null;
	protected ICFSecJavaFXSecTentGrpIncFactory factorySecTentGrpInc = null;
	protected ICFSecJavaFXSecTentGrpMembFactory factorySecTentGrpMemb = null;
	protected ICFSecJavaFXSecUserFactory factorySecUser = null;
	protected ICFSecJavaFXSecUserEMConfFactory factorySecUserEMConf = null;
	protected ICFSecJavaFXSecUserPWHistoryFactory factorySecUserPWHistory = null;
	protected ICFSecJavaFXSecUserPWResetFactory factorySecUserPWReset = null;
	protected ICFSecJavaFXSecUserPasswordFactory factorySecUserPassword = null;
	protected ICFBamJavaFXServerListFuncFactory factoryServerListFunc = null;
	protected ICFBamJavaFXServerMethodFactory factoryServerMethod = null;
	protected ICFBamJavaFXServerObjFuncFactory factoryServerObjFunc = null;
	protected ICFBamJavaFXServerProcFactory factoryServerProc = null;
	protected ICFBamJavaFXStringColFactory factoryStringCol = null;
	protected ICFBamJavaFXStringDefFactory factoryStringDef = null;
	protected ICFBamJavaFXStringTypeFactory factoryStringType = null;
	protected ICFIntJavaFXSubProjectFactory factorySubProject = null;
	protected ICFSecJavaFXSysClusterFactory factorySysCluster = null;
	protected ICFBamJavaFXTZDateColFactory factoryTZDateCol = null;
	protected ICFBamJavaFXTZDateDefFactory factoryTZDateDef = null;
	protected ICFBamJavaFXTZDateTypeFactory factoryTZDateType = null;
	protected ICFBamJavaFXTZTimeColFactory factoryTZTimeCol = null;
	protected ICFBamJavaFXTZTimeDefFactory factoryTZTimeDef = null;
	protected ICFBamJavaFXTZTimeTypeFactory factoryTZTimeType = null;
	protected ICFBamJavaFXTZTimestampColFactory factoryTZTimestampCol = null;
	protected ICFBamJavaFXTZTimestampDefFactory factoryTZTimestampDef = null;
	protected ICFBamJavaFXTZTimestampTypeFactory factoryTZTimestampType = null;
	protected ICFBamJavaFXTableFactory factoryTable = null;
	protected ICFBamJavaFXTableColFactory factoryTableCol = null;
	protected ICFBamJavaFXTableTweakFactory factoryTableTweak = null;
	protected ICFSecJavaFXTenantFactory factoryTenant = null;
	protected ICFBamJavaFXTextColFactory factoryTextCol = null;
	protected ICFBamJavaFXTextDefFactory factoryTextDef = null;
	protected ICFBamJavaFXTextTypeFactory factoryTextType = null;
	protected ICFBamJavaFXTimeColFactory factoryTimeCol = null;
	protected ICFBamJavaFXTimeDefFactory factoryTimeDef = null;
	protected ICFBamJavaFXTimeTypeFactory factoryTimeType = null;
	protected ICFBamJavaFXTimestampColFactory factoryTimestampCol = null;
	protected ICFBamJavaFXTimestampDefFactory factoryTimestampDef = null;
	protected ICFBamJavaFXTimestampTypeFactory factoryTimestampType = null;
	protected ICFIntJavaFXTldFactory factoryTld = null;
	protected ICFBamJavaFXTokenColFactory factoryTokenCol = null;
	protected ICFBamJavaFXTokenDefFactory factoryTokenDef = null;
	protected ICFBamJavaFXTokenTypeFactory factoryTokenType = null;
	protected ICFIntJavaFXTopDomainFactory factoryTopDomain = null;
	protected ICFIntJavaFXTopProjectFactory factoryTopProject = null;
	protected ICFBamJavaFXTweakFactory factoryTweak = null;
	protected ICFBamJavaFXUInt16ColFactory factoryUInt16Col = null;
	protected ICFBamJavaFXUInt16DefFactory factoryUInt16Def = null;
	protected ICFBamJavaFXUInt16TypeFactory factoryUInt16Type = null;
	protected ICFBamJavaFXUInt32ColFactory factoryUInt32Col = null;
	protected ICFBamJavaFXUInt32DefFactory factoryUInt32Def = null;
	protected ICFBamJavaFXUInt32TypeFactory factoryUInt32Type = null;
	protected ICFBamJavaFXUInt64ColFactory factoryUInt64Col = null;
	protected ICFBamJavaFXUInt64DefFactory factoryUInt64Def = null;
	protected ICFBamJavaFXUInt64TypeFactory factoryUInt64Type = null;
	protected ICFIntJavaFXURLProtocolFactory factoryURLProtocol = null;
	protected ICFBamJavaFXUuid6ColFactory factoryUuid6Col = null;
	protected ICFBamJavaFXUuid6DefFactory factoryUuid6Def = null;
	protected ICFBamJavaFXUuid6GenFactory factoryUuid6Gen = null;
	protected ICFBamJavaFXUuid6TypeFactory factoryUuid6Type = null;
	protected ICFBamJavaFXUuidColFactory factoryUuidCol = null;
	protected ICFBamJavaFXUuidDefFactory factoryUuidDef = null;
	protected ICFBamJavaFXUuidGenFactory factoryUuidGen = null;
	protected ICFBamJavaFXUuidTypeFactory factoryUuidType = null;
	protected ICFBamJavaFXValueFactory factoryValue = null;

	public CFBamJavaFXSchema() {
		CFBamJavaFX.init();
	}

	public ICFSecSchemaObj getSchema() {
		return( schema );
	}

	public void setSchema( ICFSecSchemaObj argSchema ) {
		final String S_ProcName = "setSchema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		if( ! ( argSchema instanceof ICFBamSchemaObj ) ) {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"argSchema",
				argSchema,
				"ICFBamSchemaObj" );
		}
		schema = (ICFBamSchemaObj)argSchema;
	}

	public String getClusterName() {
		return( clusterName );
	}

	public void setClusterName( String argClusterName ) {
		final String S_ProcName = "setClusterName";
		if( ( argClusterName == null ) || ( argClusterName.length() <= 0 ) ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"argClusterName" );
		}
		clusterName = argClusterName;
		clusterObj = null;
	}

	public ICFSecClusterObj getClusterObj() {
		if( clusterObj == null ) {
			if( schema != null ) {
				clusterObj = schema.getClusterTableObj().readClusterByUDomNameIdx( clusterName );
			}
			if( clusterObj == null ) {
				throw new CFLibDataNotFoundException( getClass(),
					"getClusterObj",
					"UniqueDomainName",
					"UniqueDomainName",
					clusterName );
			}
		}
		else {
			throw new CFLibRuntimeException( getClass(),
				"getClusterObj",
				String.format(Inz.x("cflib.common.CannotResolveWithoutCnx"), "Cluster"),
				String.format(Inz.s("cflib.common.CannotResolveWithoutCnx"), "Cluster") );
		}
		return( clusterObj );
	}

	public String getTenantName() {
		return( tenantName );
	}

	public void setTenantName( String argTenantName ) {
		final String S_ProcName = "setTenantName";
		if( ( argTenantName == null ) || ( argTenantName.length() <= 0 ) ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"argTenantName" );
		}
		tenantName = argTenantName;
		tenantObj = null;
	}

	public ICFSecTenantObj getTenantObj() {
		if( tenantObj == null ) {
			if( schema != null ) {
				tenantObj = schema.getTenantTableObj().readTenantByUNameIdx( getClusterObj().getRequiredId(), tenantName );
			}
			if( tenantObj == null ) {
				throw new CFLibDataNotFoundException( getClass(),
					"getTenantObj",
					"UniqueTenantName",
					"UniqueTenantName",
					new Object() {	protected CFLibDbKeyHash256 clusterId = getClusterObj().getRequiredId();
						protected String name = tenantName; } );
			}
		}
		else {
			throw new CFLibRuntimeException( getClass(),
				"getTenantObj",
				String.format(Inz.x("cflib.common.CannotResolveWithoutCnx"), "Tenant"),
				String.format(Inz.s("cflib.common.CannotResolveWithoutCnx"), "Tenant") );
		}
		return( tenantObj );
	}

	public String getSecUserName() {
		return( secUserName );
	}

	public void setSecUserName( String argSecUserName ) {
		final String S_ProcName = "setSecUserName";
		if( ( argSecUserName == null ) || ( argSecUserName.length() <= 0 ) ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"argSecUserName" );
		}
		secUserName = argSecUserName;
		secUserObj = null;
	}

	public ICFSecSecUserObj getSecUserObj() {
		if( secUserObj == null ) {
			if( schema != null ) {
				secUserObj = schema.getSecUserTableObj().readSecUserByULoginIdx( secUserName );
			}
			if( secUserObj == null ) {
				throw new CFLibDataNotFoundException( getClass(),
					"getSecUserObj",
					"UniqueLogin",
					"UniqueLogin",
					secUserName );

			}
		}
		else {
			throw new CFLibRuntimeException( getClass(),
				"getSecUserObj",
				String.format(Inz.x("cflib.common.CannotResolveWithoutCnx"), "SecUser"),
				String.format(Inz.s("cflib.common.CannotResolveWithoutCnx"), "SecUser") );
		}
		return( secUserObj );
	}

	public ICFSecSecSessionObj getSecSessionObj() {
		if( secSessionObj == null ) {
			if( schema != null ) {
				getClusterObj();
				getTenantObj();
				getSecUserObj();
				secSessionObj = schema.getSecSessionTableObj().newInstance();
				ICFSecSecSessionEditObj sessionEdit = secSessionObj.beginEdit();
				sessionEdit.setRequiredSecUserId( secUserObj.getPKey() );
				sessionEdit.setRequiredStart( LocalDateTime.now() );
				sessionEdit.setOptionalFinish( null );
				secSessionObj = sessionEdit.create();
				sessionEdit = null;
			}
		}
		else {
			throw new CFLibRuntimeException( getClass(),
				"getSecSessionObj",
				String.format(Inz.x("cflib.common.CannotResolveWithoutCnx"), "SecSession"),
				String.format(Inz.s("cflib.common.CannotResolveWithoutCnx"), "SecSession") );
		}
		return( secSessionObj );
	}

	public ICFBamJavaFXAtomFactory getAtomFactory() {
		if( factoryAtom == null ) {
			factoryAtom = new CFBamJavaFXAtomFactory( this );
		}
		return( factoryAtom );
	}

	public ICFBamJavaFXBlobColFactory getBlobColFactory() {
		if( factoryBlobCol == null ) {
			factoryBlobCol = new CFBamJavaFXBlobColFactory( this );
		}
		return( factoryBlobCol );
	}

	public ICFBamJavaFXBlobDefFactory getBlobDefFactory() {
		if( factoryBlobDef == null ) {
			factoryBlobDef = new CFBamJavaFXBlobDefFactory( this );
		}
		return( factoryBlobDef );
	}

	public ICFBamJavaFXBlobTypeFactory getBlobTypeFactory() {
		if( factoryBlobType == null ) {
			factoryBlobType = new CFBamJavaFXBlobTypeFactory( this );
		}
		return( factoryBlobType );
	}

	public ICFBamJavaFXBoolColFactory getBoolColFactory() {
		if( factoryBoolCol == null ) {
			factoryBoolCol = new CFBamJavaFXBoolColFactory( this );
		}
		return( factoryBoolCol );
	}

	public ICFBamJavaFXBoolDefFactory getBoolDefFactory() {
		if( factoryBoolDef == null ) {
			factoryBoolDef = new CFBamJavaFXBoolDefFactory( this );
		}
		return( factoryBoolDef );
	}

	public ICFBamJavaFXBoolTypeFactory getBoolTypeFactory() {
		if( factoryBoolType == null ) {
			factoryBoolType = new CFBamJavaFXBoolTypeFactory( this );
		}
		return( factoryBoolType );
	}

	public ICFBamJavaFXChainFactory getChainFactory() {
		if( factoryChain == null ) {
			factoryChain = new CFBamJavaFXChainFactory( this );
		}
		return( factoryChain );
	}

	public ICFBamJavaFXClearDepFactory getClearDepFactory() {
		if( factoryClearDep == null ) {
			factoryClearDep = new CFBamJavaFXClearDepFactory( this );
		}
		return( factoryClearDep );
	}

	public ICFBamJavaFXClearSubDep1Factory getClearSubDep1Factory() {
		if( factoryClearSubDep1 == null ) {
			factoryClearSubDep1 = new CFBamJavaFXClearSubDep1Factory( this );
		}
		return( factoryClearSubDep1 );
	}

	public ICFBamJavaFXClearSubDep2Factory getClearSubDep2Factory() {
		if( factoryClearSubDep2 == null ) {
			factoryClearSubDep2 = new CFBamJavaFXClearSubDep2Factory( this );
		}
		return( factoryClearSubDep2 );
	}

	public ICFBamJavaFXClearSubDep3Factory getClearSubDep3Factory() {
		if( factoryClearSubDep3 == null ) {
			factoryClearSubDep3 = new CFBamJavaFXClearSubDep3Factory( this );
		}
		return( factoryClearSubDep3 );
	}

	public ICFBamJavaFXClearTopDepFactory getClearTopDepFactory() {
		if( factoryClearTopDep == null ) {
			factoryClearTopDep = new CFBamJavaFXClearTopDepFactory( this );
		}
		return( factoryClearTopDep );
	}

	public ICFSecJavaFXClusterFactory getClusterFactory() {
		if( factoryCluster == null ) {
			factoryCluster = new CFSecJavaFXClusterFactory( this );
		}
		return( factoryCluster );
	}

	public ICFBamJavaFXDateColFactory getDateColFactory() {
		if( factoryDateCol == null ) {
			factoryDateCol = new CFBamJavaFXDateColFactory( this );
		}
		return( factoryDateCol );
	}

	public ICFBamJavaFXDateDefFactory getDateDefFactory() {
		if( factoryDateDef == null ) {
			factoryDateDef = new CFBamJavaFXDateDefFactory( this );
		}
		return( factoryDateDef );
	}

	public ICFBamJavaFXDateTypeFactory getDateTypeFactory() {
		if( factoryDateType == null ) {
			factoryDateType = new CFBamJavaFXDateTypeFactory( this );
		}
		return( factoryDateType );
	}

	public ICFBamJavaFXDbKeyHash128ColFactory getDbKeyHash128ColFactory() {
		if( factoryDbKeyHash128Col == null ) {
			factoryDbKeyHash128Col = new CFBamJavaFXDbKeyHash128ColFactory( this );
		}
		return( factoryDbKeyHash128Col );
	}

	public ICFBamJavaFXDbKeyHash128DefFactory getDbKeyHash128DefFactory() {
		if( factoryDbKeyHash128Def == null ) {
			factoryDbKeyHash128Def = new CFBamJavaFXDbKeyHash128DefFactory( this );
		}
		return( factoryDbKeyHash128Def );
	}

	public ICFBamJavaFXDbKeyHash128GenFactory getDbKeyHash128GenFactory() {
		if( factoryDbKeyHash128Gen == null ) {
			factoryDbKeyHash128Gen = new CFBamJavaFXDbKeyHash128GenFactory( this );
		}
		return( factoryDbKeyHash128Gen );
	}

	public ICFBamJavaFXDbKeyHash128TypeFactory getDbKeyHash128TypeFactory() {
		if( factoryDbKeyHash128Type == null ) {
			factoryDbKeyHash128Type = new CFBamJavaFXDbKeyHash128TypeFactory( this );
		}
		return( factoryDbKeyHash128Type );
	}

	public ICFBamJavaFXDbKeyHash160ColFactory getDbKeyHash160ColFactory() {
		if( factoryDbKeyHash160Col == null ) {
			factoryDbKeyHash160Col = new CFBamJavaFXDbKeyHash160ColFactory( this );
		}
		return( factoryDbKeyHash160Col );
	}

	public ICFBamJavaFXDbKeyHash160DefFactory getDbKeyHash160DefFactory() {
		if( factoryDbKeyHash160Def == null ) {
			factoryDbKeyHash160Def = new CFBamJavaFXDbKeyHash160DefFactory( this );
		}
		return( factoryDbKeyHash160Def );
	}

	public ICFBamJavaFXDbKeyHash160GenFactory getDbKeyHash160GenFactory() {
		if( factoryDbKeyHash160Gen == null ) {
			factoryDbKeyHash160Gen = new CFBamJavaFXDbKeyHash160GenFactory( this );
		}
		return( factoryDbKeyHash160Gen );
	}

	public ICFBamJavaFXDbKeyHash160TypeFactory getDbKeyHash160TypeFactory() {
		if( factoryDbKeyHash160Type == null ) {
			factoryDbKeyHash160Type = new CFBamJavaFXDbKeyHash160TypeFactory( this );
		}
		return( factoryDbKeyHash160Type );
	}

	public ICFBamJavaFXDbKeyHash224ColFactory getDbKeyHash224ColFactory() {
		if( factoryDbKeyHash224Col == null ) {
			factoryDbKeyHash224Col = new CFBamJavaFXDbKeyHash224ColFactory( this );
		}
		return( factoryDbKeyHash224Col );
	}

	public ICFBamJavaFXDbKeyHash224DefFactory getDbKeyHash224DefFactory() {
		if( factoryDbKeyHash224Def == null ) {
			factoryDbKeyHash224Def = new CFBamJavaFXDbKeyHash224DefFactory( this );
		}
		return( factoryDbKeyHash224Def );
	}

	public ICFBamJavaFXDbKeyHash224GenFactory getDbKeyHash224GenFactory() {
		if( factoryDbKeyHash224Gen == null ) {
			factoryDbKeyHash224Gen = new CFBamJavaFXDbKeyHash224GenFactory( this );
		}
		return( factoryDbKeyHash224Gen );
	}

	public ICFBamJavaFXDbKeyHash224TypeFactory getDbKeyHash224TypeFactory() {
		if( factoryDbKeyHash224Type == null ) {
			factoryDbKeyHash224Type = new CFBamJavaFXDbKeyHash224TypeFactory( this );
		}
		return( factoryDbKeyHash224Type );
	}

	public ICFBamJavaFXDbKeyHash256ColFactory getDbKeyHash256ColFactory() {
		if( factoryDbKeyHash256Col == null ) {
			factoryDbKeyHash256Col = new CFBamJavaFXDbKeyHash256ColFactory( this );
		}
		return( factoryDbKeyHash256Col );
	}

	public ICFBamJavaFXDbKeyHash256DefFactory getDbKeyHash256DefFactory() {
		if( factoryDbKeyHash256Def == null ) {
			factoryDbKeyHash256Def = new CFBamJavaFXDbKeyHash256DefFactory( this );
		}
		return( factoryDbKeyHash256Def );
	}

	public ICFBamJavaFXDbKeyHash256GenFactory getDbKeyHash256GenFactory() {
		if( factoryDbKeyHash256Gen == null ) {
			factoryDbKeyHash256Gen = new CFBamJavaFXDbKeyHash256GenFactory( this );
		}
		return( factoryDbKeyHash256Gen );
	}

	public ICFBamJavaFXDbKeyHash256TypeFactory getDbKeyHash256TypeFactory() {
		if( factoryDbKeyHash256Type == null ) {
			factoryDbKeyHash256Type = new CFBamJavaFXDbKeyHash256TypeFactory( this );
		}
		return( factoryDbKeyHash256Type );
	}

	public ICFBamJavaFXDbKeyHash384ColFactory getDbKeyHash384ColFactory() {
		if( factoryDbKeyHash384Col == null ) {
			factoryDbKeyHash384Col = new CFBamJavaFXDbKeyHash384ColFactory( this );
		}
		return( factoryDbKeyHash384Col );
	}

	public ICFBamJavaFXDbKeyHash384DefFactory getDbKeyHash384DefFactory() {
		if( factoryDbKeyHash384Def == null ) {
			factoryDbKeyHash384Def = new CFBamJavaFXDbKeyHash384DefFactory( this );
		}
		return( factoryDbKeyHash384Def );
	}

	public ICFBamJavaFXDbKeyHash384GenFactory getDbKeyHash384GenFactory() {
		if( factoryDbKeyHash384Gen == null ) {
			factoryDbKeyHash384Gen = new CFBamJavaFXDbKeyHash384GenFactory( this );
		}
		return( factoryDbKeyHash384Gen );
	}

	public ICFBamJavaFXDbKeyHash384TypeFactory getDbKeyHash384TypeFactory() {
		if( factoryDbKeyHash384Type == null ) {
			factoryDbKeyHash384Type = new CFBamJavaFXDbKeyHash384TypeFactory( this );
		}
		return( factoryDbKeyHash384Type );
	}

	public ICFBamJavaFXDbKeyHash512ColFactory getDbKeyHash512ColFactory() {
		if( factoryDbKeyHash512Col == null ) {
			factoryDbKeyHash512Col = new CFBamJavaFXDbKeyHash512ColFactory( this );
		}
		return( factoryDbKeyHash512Col );
	}

	public ICFBamJavaFXDbKeyHash512DefFactory getDbKeyHash512DefFactory() {
		if( factoryDbKeyHash512Def == null ) {
			factoryDbKeyHash512Def = new CFBamJavaFXDbKeyHash512DefFactory( this );
		}
		return( factoryDbKeyHash512Def );
	}

	public ICFBamJavaFXDbKeyHash512GenFactory getDbKeyHash512GenFactory() {
		if( factoryDbKeyHash512Gen == null ) {
			factoryDbKeyHash512Gen = new CFBamJavaFXDbKeyHash512GenFactory( this );
		}
		return( factoryDbKeyHash512Gen );
	}

	public ICFBamJavaFXDbKeyHash512TypeFactory getDbKeyHash512TypeFactory() {
		if( factoryDbKeyHash512Type == null ) {
			factoryDbKeyHash512Type = new CFBamJavaFXDbKeyHash512TypeFactory( this );
		}
		return( factoryDbKeyHash512Type );
	}

	public ICFBamJavaFXDelDepFactory getDelDepFactory() {
		if( factoryDelDep == null ) {
			factoryDelDep = new CFBamJavaFXDelDepFactory( this );
		}
		return( factoryDelDep );
	}

	public ICFBamJavaFXDelSubDep1Factory getDelSubDep1Factory() {
		if( factoryDelSubDep1 == null ) {
			factoryDelSubDep1 = new CFBamJavaFXDelSubDep1Factory( this );
		}
		return( factoryDelSubDep1 );
	}

	public ICFBamJavaFXDelSubDep2Factory getDelSubDep2Factory() {
		if( factoryDelSubDep2 == null ) {
			factoryDelSubDep2 = new CFBamJavaFXDelSubDep2Factory( this );
		}
		return( factoryDelSubDep2 );
	}

	public ICFBamJavaFXDelSubDep3Factory getDelSubDep3Factory() {
		if( factoryDelSubDep3 == null ) {
			factoryDelSubDep3 = new CFBamJavaFXDelSubDep3Factory( this );
		}
		return( factoryDelSubDep3 );
	}

	public ICFBamJavaFXDelTopDepFactory getDelTopDepFactory() {
		if( factoryDelTopDep == null ) {
			factoryDelTopDep = new CFBamJavaFXDelTopDepFactory( this );
		}
		return( factoryDelTopDep );
	}

	public ICFBamJavaFXDoubleColFactory getDoubleColFactory() {
		if( factoryDoubleCol == null ) {
			factoryDoubleCol = new CFBamJavaFXDoubleColFactory( this );
		}
		return( factoryDoubleCol );
	}

	public ICFBamJavaFXDoubleDefFactory getDoubleDefFactory() {
		if( factoryDoubleDef == null ) {
			factoryDoubleDef = new CFBamJavaFXDoubleDefFactory( this );
		}
		return( factoryDoubleDef );
	}

	public ICFBamJavaFXDoubleTypeFactory getDoubleTypeFactory() {
		if( factoryDoubleType == null ) {
			factoryDoubleType = new CFBamJavaFXDoubleTypeFactory( this );
		}
		return( factoryDoubleType );
	}

	public ICFBamJavaFXEnumDefFactory getEnumDefFactory() {
		if( factoryEnumDef == null ) {
			factoryEnumDef = new CFBamJavaFXEnumDefFactory( this );
		}
		return( factoryEnumDef );
	}

	public ICFBamJavaFXEnumTagFactory getEnumTagFactory() {
		if( factoryEnumTag == null ) {
			factoryEnumTag = new CFBamJavaFXEnumTagFactory( this );
		}
		return( factoryEnumTag );
	}

	public ICFBamJavaFXEnumTypeFactory getEnumTypeFactory() {
		if( factoryEnumType == null ) {
			factoryEnumType = new CFBamJavaFXEnumTypeFactory( this );
		}
		return( factoryEnumType );
	}

	public ICFBamJavaFXFloatColFactory getFloatColFactory() {
		if( factoryFloatCol == null ) {
			factoryFloatCol = new CFBamJavaFXFloatColFactory( this );
		}
		return( factoryFloatCol );
	}

	public ICFBamJavaFXFloatDefFactory getFloatDefFactory() {
		if( factoryFloatDef == null ) {
			factoryFloatDef = new CFBamJavaFXFloatDefFactory( this );
		}
		return( factoryFloatDef );
	}

	public ICFBamJavaFXFloatTypeFactory getFloatTypeFactory() {
		if( factoryFloatType == null ) {
			factoryFloatType = new CFBamJavaFXFloatTypeFactory( this );
		}
		return( factoryFloatType );
	}

	public ICFSecJavaFXISOCcyFactory getISOCcyFactory() {
		if( factoryISOCcy == null ) {
			factoryISOCcy = new CFSecJavaFXISOCcyFactory( this );
		}
		return( factoryISOCcy );
	}

	public ICFSecJavaFXISOCtryFactory getISOCtryFactory() {
		if( factoryISOCtry == null ) {
			factoryISOCtry = new CFSecJavaFXISOCtryFactory( this );
		}
		return( factoryISOCtry );
	}

	public ICFSecJavaFXISOCtryCcyFactory getISOCtryCcyFactory() {
		if( factoryISOCtryCcy == null ) {
			factoryISOCtryCcy = new CFSecJavaFXISOCtryCcyFactory( this );
		}
		return( factoryISOCtryCcy );
	}

	public ICFSecJavaFXISOCtryLangFactory getISOCtryLangFactory() {
		if( factoryISOCtryLang == null ) {
			factoryISOCtryLang = new CFSecJavaFXISOCtryLangFactory( this );
		}
		return( factoryISOCtryLang );
	}

	public ICFSecJavaFXISOLangFactory getISOLangFactory() {
		if( factoryISOLang == null ) {
			factoryISOLang = new CFSecJavaFXISOLangFactory( this );
		}
		return( factoryISOLang );
	}

	public ICFSecJavaFXISOTZoneFactory getISOTZoneFactory() {
		if( factoryISOTZone == null ) {
			factoryISOTZone = new CFSecJavaFXISOTZoneFactory( this );
		}
		return( factoryISOTZone );
	}

	public ICFBamJavaFXId16GenFactory getId16GenFactory() {
		if( factoryId16Gen == null ) {
			factoryId16Gen = new CFBamJavaFXId16GenFactory( this );
		}
		return( factoryId16Gen );
	}

	public ICFBamJavaFXId32GenFactory getId32GenFactory() {
		if( factoryId32Gen == null ) {
			factoryId32Gen = new CFBamJavaFXId32GenFactory( this );
		}
		return( factoryId32Gen );
	}

	public ICFBamJavaFXId64GenFactory getId64GenFactory() {
		if( factoryId64Gen == null ) {
			factoryId64Gen = new CFBamJavaFXId64GenFactory( this );
		}
		return( factoryId64Gen );
	}

	public ICFBamJavaFXIndexFactory getIndexFactory() {
		if( factoryIndex == null ) {
			factoryIndex = new CFBamJavaFXIndexFactory( this );
		}
		return( factoryIndex );
	}

	public ICFBamJavaFXIndexColFactory getIndexColFactory() {
		if( factoryIndexCol == null ) {
			factoryIndexCol = new CFBamJavaFXIndexColFactory( this );
		}
		return( factoryIndexCol );
	}

	public ICFBamJavaFXInt16ColFactory getInt16ColFactory() {
		if( factoryInt16Col == null ) {
			factoryInt16Col = new CFBamJavaFXInt16ColFactory( this );
		}
		return( factoryInt16Col );
	}

	public ICFBamJavaFXInt16DefFactory getInt16DefFactory() {
		if( factoryInt16Def == null ) {
			factoryInt16Def = new CFBamJavaFXInt16DefFactory( this );
		}
		return( factoryInt16Def );
	}

	public ICFBamJavaFXInt16TypeFactory getInt16TypeFactory() {
		if( factoryInt16Type == null ) {
			factoryInt16Type = new CFBamJavaFXInt16TypeFactory( this );
		}
		return( factoryInt16Type );
	}

	public ICFBamJavaFXInt32ColFactory getInt32ColFactory() {
		if( factoryInt32Col == null ) {
			factoryInt32Col = new CFBamJavaFXInt32ColFactory( this );
		}
		return( factoryInt32Col );
	}

	public ICFBamJavaFXInt32DefFactory getInt32DefFactory() {
		if( factoryInt32Def == null ) {
			factoryInt32Def = new CFBamJavaFXInt32DefFactory( this );
		}
		return( factoryInt32Def );
	}

	public ICFBamJavaFXInt32TypeFactory getInt32TypeFactory() {
		if( factoryInt32Type == null ) {
			factoryInt32Type = new CFBamJavaFXInt32TypeFactory( this );
		}
		return( factoryInt32Type );
	}

	public ICFBamJavaFXInt64ColFactory getInt64ColFactory() {
		if( factoryInt64Col == null ) {
			factoryInt64Col = new CFBamJavaFXInt64ColFactory( this );
		}
		return( factoryInt64Col );
	}

	public ICFBamJavaFXInt64DefFactory getInt64DefFactory() {
		if( factoryInt64Def == null ) {
			factoryInt64Def = new CFBamJavaFXInt64DefFactory( this );
		}
		return( factoryInt64Def );
	}

	public ICFBamJavaFXInt64TypeFactory getInt64TypeFactory() {
		if( factoryInt64Type == null ) {
			factoryInt64Type = new CFBamJavaFXInt64TypeFactory( this );
		}
		return( factoryInt64Type );
	}

	public ICFIntJavaFXLicenseFactory getLicenseFactory() {
		if( factoryLicense == null ) {
			factoryLicense = new CFIntJavaFXLicenseFactory( this );
		}
		return( factoryLicense );
	}

	public ICFIntJavaFXMajorVersionFactory getMajorVersionFactory() {
		if( factoryMajorVersion == null ) {
			factoryMajorVersion = new CFIntJavaFXMajorVersionFactory( this );
		}
		return( factoryMajorVersion );
	}

	public ICFIntJavaFXMimeTypeFactory getMimeTypeFactory() {
		if( factoryMimeType == null ) {
			factoryMimeType = new CFIntJavaFXMimeTypeFactory( this );
		}
		return( factoryMimeType );
	}

	public ICFIntJavaFXMinorVersionFactory getMinorVersionFactory() {
		if( factoryMinorVersion == null ) {
			factoryMinorVersion = new CFIntJavaFXMinorVersionFactory( this );
		}
		return( factoryMinorVersion );
	}

	public ICFBamJavaFXNmTokenColFactory getNmTokenColFactory() {
		if( factoryNmTokenCol == null ) {
			factoryNmTokenCol = new CFBamJavaFXNmTokenColFactory( this );
		}
		return( factoryNmTokenCol );
	}

	public ICFBamJavaFXNmTokenDefFactory getNmTokenDefFactory() {
		if( factoryNmTokenDef == null ) {
			factoryNmTokenDef = new CFBamJavaFXNmTokenDefFactory( this );
		}
		return( factoryNmTokenDef );
	}

	public ICFBamJavaFXNmTokenTypeFactory getNmTokenTypeFactory() {
		if( factoryNmTokenType == null ) {
			factoryNmTokenType = new CFBamJavaFXNmTokenTypeFactory( this );
		}
		return( factoryNmTokenType );
	}

	public ICFBamJavaFXNmTokensColFactory getNmTokensColFactory() {
		if( factoryNmTokensCol == null ) {
			factoryNmTokensCol = new CFBamJavaFXNmTokensColFactory( this );
		}
		return( factoryNmTokensCol );
	}

	public ICFBamJavaFXNmTokensDefFactory getNmTokensDefFactory() {
		if( factoryNmTokensDef == null ) {
			factoryNmTokensDef = new CFBamJavaFXNmTokensDefFactory( this );
		}
		return( factoryNmTokensDef );
	}

	public ICFBamJavaFXNmTokensTypeFactory getNmTokensTypeFactory() {
		if( factoryNmTokensType == null ) {
			factoryNmTokensType = new CFBamJavaFXNmTokensTypeFactory( this );
		}
		return( factoryNmTokensType );
	}

	public ICFBamJavaFXNumberColFactory getNumberColFactory() {
		if( factoryNumberCol == null ) {
			factoryNumberCol = new CFBamJavaFXNumberColFactory( this );
		}
		return( factoryNumberCol );
	}

	public ICFBamJavaFXNumberDefFactory getNumberDefFactory() {
		if( factoryNumberDef == null ) {
			factoryNumberDef = new CFBamJavaFXNumberDefFactory( this );
		}
		return( factoryNumberDef );
	}

	public ICFBamJavaFXNumberTypeFactory getNumberTypeFactory() {
		if( factoryNumberType == null ) {
			factoryNumberType = new CFBamJavaFXNumberTypeFactory( this );
		}
		return( factoryNumberType );
	}

	public ICFBamJavaFXParamFactory getParamFactory() {
		if( factoryParam == null ) {
			factoryParam = new CFBamJavaFXParamFactory( this );
		}
		return( factoryParam );
	}

	public ICFBamJavaFXPopDepFactory getPopDepFactory() {
		if( factoryPopDep == null ) {
			factoryPopDep = new CFBamJavaFXPopDepFactory( this );
		}
		return( factoryPopDep );
	}

	public ICFBamJavaFXPopSubDep1Factory getPopSubDep1Factory() {
		if( factoryPopSubDep1 == null ) {
			factoryPopSubDep1 = new CFBamJavaFXPopSubDep1Factory( this );
		}
		return( factoryPopSubDep1 );
	}

	public ICFBamJavaFXPopSubDep2Factory getPopSubDep2Factory() {
		if( factoryPopSubDep2 == null ) {
			factoryPopSubDep2 = new CFBamJavaFXPopSubDep2Factory( this );
		}
		return( factoryPopSubDep2 );
	}

	public ICFBamJavaFXPopSubDep3Factory getPopSubDep3Factory() {
		if( factoryPopSubDep3 == null ) {
			factoryPopSubDep3 = new CFBamJavaFXPopSubDep3Factory( this );
		}
		return( factoryPopSubDep3 );
	}

	public ICFBamJavaFXPopTopDepFactory getPopTopDepFactory() {
		if( factoryPopTopDep == null ) {
			factoryPopTopDep = new CFBamJavaFXPopTopDepFactory( this );
		}
		return( factoryPopTopDep );
	}

	public ICFBamJavaFXRelationFactory getRelationFactory() {
		if( factoryRelation == null ) {
			factoryRelation = new CFBamJavaFXRelationFactory( this );
		}
		return( factoryRelation );
	}

	public ICFBamJavaFXRelationColFactory getRelationColFactory() {
		if( factoryRelationCol == null ) {
			factoryRelationCol = new CFBamJavaFXRelationColFactory( this );
		}
		return( factoryRelationCol );
	}

	public ICFBamJavaFXSchemaDefFactory getSchemaDefFactory() {
		if( factorySchemaDef == null ) {
			factorySchemaDef = new CFBamJavaFXSchemaDefFactory( this );
		}
		return( factorySchemaDef );
	}

	public ICFBamJavaFXSchemaRefFactory getSchemaRefFactory() {
		if( factorySchemaRef == null ) {
			factorySchemaRef = new CFBamJavaFXSchemaRefFactory( this );
		}
		return( factorySchemaRef );
	}

	public ICFBamJavaFXSchemaTweakFactory getSchemaTweakFactory() {
		if( factorySchemaTweak == null ) {
			factorySchemaTweak = new CFBamJavaFXSchemaTweakFactory( this );
		}
		return( factorySchemaTweak );
	}

	public ICFBamJavaFXScopeFactory getScopeFactory() {
		if( factoryScope == null ) {
			factoryScope = new CFBamJavaFXScopeFactory( this );
		}
		return( factoryScope );
	}

	public ICFSecJavaFXSecClusGrpFactory getSecClusGrpFactory() {
		if( factorySecClusGrp == null ) {
			factorySecClusGrp = new CFSecJavaFXSecClusGrpFactory( this );
		}
		return( factorySecClusGrp );
	}

	public ICFSecJavaFXSecClusGrpIncFactory getSecClusGrpIncFactory() {
		if( factorySecClusGrpInc == null ) {
			factorySecClusGrpInc = new CFSecJavaFXSecClusGrpIncFactory( this );
		}
		return( factorySecClusGrpInc );
	}

	public ICFSecJavaFXSecClusGrpMembFactory getSecClusGrpMembFactory() {
		if( factorySecClusGrpMemb == null ) {
			factorySecClusGrpMemb = new CFSecJavaFXSecClusGrpMembFactory( this );
		}
		return( factorySecClusGrpMemb );
	}

	public ICFSecJavaFXSecSessionFactory getSecSessionFactory() {
		if( factorySecSession == null ) {
			factorySecSession = new CFSecJavaFXSecSessionFactory( this );
		}
		return( factorySecSession );
	}

	public ICFSecJavaFXSecSysGrpFactory getSecSysGrpFactory() {
		if( factorySecSysGrp == null ) {
			factorySecSysGrp = new CFSecJavaFXSecSysGrpFactory( this );
		}
		return( factorySecSysGrp );
	}

	public ICFSecJavaFXSecSysGrpIncFactory getSecSysGrpIncFactory() {
		if( factorySecSysGrpInc == null ) {
			factorySecSysGrpInc = new CFSecJavaFXSecSysGrpIncFactory( this );
		}
		return( factorySecSysGrpInc );
	}

	public ICFSecJavaFXSecSysGrpMembFactory getSecSysGrpMembFactory() {
		if( factorySecSysGrpMemb == null ) {
			factorySecSysGrpMemb = new CFSecJavaFXSecSysGrpMembFactory( this );
		}
		return( factorySecSysGrpMemb );
	}

	public ICFSecJavaFXSecTentGrpFactory getSecTentGrpFactory() {
		if( factorySecTentGrp == null ) {
			factorySecTentGrp = new CFSecJavaFXSecTentGrpFactory( this );
		}
		return( factorySecTentGrp );
	}

	public ICFSecJavaFXSecTentGrpIncFactory getSecTentGrpIncFactory() {
		if( factorySecTentGrpInc == null ) {
			factorySecTentGrpInc = new CFSecJavaFXSecTentGrpIncFactory( this );
		}
		return( factorySecTentGrpInc );
	}

	public ICFSecJavaFXSecTentGrpMembFactory getSecTentGrpMembFactory() {
		if( factorySecTentGrpMemb == null ) {
			factorySecTentGrpMemb = new CFSecJavaFXSecTentGrpMembFactory( this );
		}
		return( factorySecTentGrpMemb );
	}

	public ICFSecJavaFXSecUserFactory getSecUserFactory() {
		if( factorySecUser == null ) {
			factorySecUser = new CFSecJavaFXSecUserFactory( this );
		}
		return( factorySecUser );
	}

	public ICFSecJavaFXSecUserEMConfFactory getSecUserEMConfFactory() {
		if( factorySecUserEMConf == null ) {
			factorySecUserEMConf = new CFSecJavaFXSecUserEMConfFactory( this );
		}
		return( factorySecUserEMConf );
	}

	public ICFSecJavaFXSecUserPWHistoryFactory getSecUserPWHistoryFactory() {
		if( factorySecUserPWHistory == null ) {
			factorySecUserPWHistory = new CFSecJavaFXSecUserPWHistoryFactory( this );
		}
		return( factorySecUserPWHistory );
	}

	public ICFSecJavaFXSecUserPWResetFactory getSecUserPWResetFactory() {
		if( factorySecUserPWReset == null ) {
			factorySecUserPWReset = new CFSecJavaFXSecUserPWResetFactory( this );
		}
		return( factorySecUserPWReset );
	}

	public ICFSecJavaFXSecUserPasswordFactory getSecUserPasswordFactory() {
		if( factorySecUserPassword == null ) {
			factorySecUserPassword = new CFSecJavaFXSecUserPasswordFactory( this );
		}
		return( factorySecUserPassword );
	}

	public ICFBamJavaFXServerListFuncFactory getServerListFuncFactory() {
		if( factoryServerListFunc == null ) {
			factoryServerListFunc = new CFBamJavaFXServerListFuncFactory( this );
		}
		return( factoryServerListFunc );
	}

	public ICFBamJavaFXServerMethodFactory getServerMethodFactory() {
		if( factoryServerMethod == null ) {
			factoryServerMethod = new CFBamJavaFXServerMethodFactory( this );
		}
		return( factoryServerMethod );
	}

	public ICFBamJavaFXServerObjFuncFactory getServerObjFuncFactory() {
		if( factoryServerObjFunc == null ) {
			factoryServerObjFunc = new CFBamJavaFXServerObjFuncFactory( this );
		}
		return( factoryServerObjFunc );
	}

	public ICFBamJavaFXServerProcFactory getServerProcFactory() {
		if( factoryServerProc == null ) {
			factoryServerProc = new CFBamJavaFXServerProcFactory( this );
		}
		return( factoryServerProc );
	}

	public ICFBamJavaFXStringColFactory getStringColFactory() {
		if( factoryStringCol == null ) {
			factoryStringCol = new CFBamJavaFXStringColFactory( this );
		}
		return( factoryStringCol );
	}

	public ICFBamJavaFXStringDefFactory getStringDefFactory() {
		if( factoryStringDef == null ) {
			factoryStringDef = new CFBamJavaFXStringDefFactory( this );
		}
		return( factoryStringDef );
	}

	public ICFBamJavaFXStringTypeFactory getStringTypeFactory() {
		if( factoryStringType == null ) {
			factoryStringType = new CFBamJavaFXStringTypeFactory( this );
		}
		return( factoryStringType );
	}

	public ICFIntJavaFXSubProjectFactory getSubProjectFactory() {
		if( factorySubProject == null ) {
			factorySubProject = new CFIntJavaFXSubProjectFactory( this );
		}
		return( factorySubProject );
	}

	public ICFSecJavaFXSysClusterFactory getSysClusterFactory() {
		if( factorySysCluster == null ) {
			factorySysCluster = new CFSecJavaFXSysClusterFactory( this );
		}
		return( factorySysCluster );
	}

	public ICFBamJavaFXTZDateColFactory getTZDateColFactory() {
		if( factoryTZDateCol == null ) {
			factoryTZDateCol = new CFBamJavaFXTZDateColFactory( this );
		}
		return( factoryTZDateCol );
	}

	public ICFBamJavaFXTZDateDefFactory getTZDateDefFactory() {
		if( factoryTZDateDef == null ) {
			factoryTZDateDef = new CFBamJavaFXTZDateDefFactory( this );
		}
		return( factoryTZDateDef );
	}

	public ICFBamJavaFXTZDateTypeFactory getTZDateTypeFactory() {
		if( factoryTZDateType == null ) {
			factoryTZDateType = new CFBamJavaFXTZDateTypeFactory( this );
		}
		return( factoryTZDateType );
	}

	public ICFBamJavaFXTZTimeColFactory getTZTimeColFactory() {
		if( factoryTZTimeCol == null ) {
			factoryTZTimeCol = new CFBamJavaFXTZTimeColFactory( this );
		}
		return( factoryTZTimeCol );
	}

	public ICFBamJavaFXTZTimeDefFactory getTZTimeDefFactory() {
		if( factoryTZTimeDef == null ) {
			factoryTZTimeDef = new CFBamJavaFXTZTimeDefFactory( this );
		}
		return( factoryTZTimeDef );
	}

	public ICFBamJavaFXTZTimeTypeFactory getTZTimeTypeFactory() {
		if( factoryTZTimeType == null ) {
			factoryTZTimeType = new CFBamJavaFXTZTimeTypeFactory( this );
		}
		return( factoryTZTimeType );
	}

	public ICFBamJavaFXTZTimestampColFactory getTZTimestampColFactory() {
		if( factoryTZTimestampCol == null ) {
			factoryTZTimestampCol = new CFBamJavaFXTZTimestampColFactory( this );
		}
		return( factoryTZTimestampCol );
	}

	public ICFBamJavaFXTZTimestampDefFactory getTZTimestampDefFactory() {
		if( factoryTZTimestampDef == null ) {
			factoryTZTimestampDef = new CFBamJavaFXTZTimestampDefFactory( this );
		}
		return( factoryTZTimestampDef );
	}

	public ICFBamJavaFXTZTimestampTypeFactory getTZTimestampTypeFactory() {
		if( factoryTZTimestampType == null ) {
			factoryTZTimestampType = new CFBamJavaFXTZTimestampTypeFactory( this );
		}
		return( factoryTZTimestampType );
	}

	public ICFBamJavaFXTableFactory getTableFactory() {
		if( factoryTable == null ) {
			factoryTable = new CFBamJavaFXTableFactory( this );
		}
		return( factoryTable );
	}

	public ICFBamJavaFXTableColFactory getTableColFactory() {
		if( factoryTableCol == null ) {
			factoryTableCol = new CFBamJavaFXTableColFactory( this );
		}
		return( factoryTableCol );
	}

	public ICFBamJavaFXTableTweakFactory getTableTweakFactory() {
		if( factoryTableTweak == null ) {
			factoryTableTweak = new CFBamJavaFXTableTweakFactory( this );
		}
		return( factoryTableTweak );
	}

	public ICFSecJavaFXTenantFactory getTenantFactory() {
		if( factoryTenant == null ) {
			factoryTenant = new CFSecJavaFXTenantFactory( this );
		}
		return( factoryTenant );
	}

	public ICFBamJavaFXTextColFactory getTextColFactory() {
		if( factoryTextCol == null ) {
			factoryTextCol = new CFBamJavaFXTextColFactory( this );
		}
		return( factoryTextCol );
	}

	public ICFBamJavaFXTextDefFactory getTextDefFactory() {
		if( factoryTextDef == null ) {
			factoryTextDef = new CFBamJavaFXTextDefFactory( this );
		}
		return( factoryTextDef );
	}

	public ICFBamJavaFXTextTypeFactory getTextTypeFactory() {
		if( factoryTextType == null ) {
			factoryTextType = new CFBamJavaFXTextTypeFactory( this );
		}
		return( factoryTextType );
	}

	public ICFBamJavaFXTimeColFactory getTimeColFactory() {
		if( factoryTimeCol == null ) {
			factoryTimeCol = new CFBamJavaFXTimeColFactory( this );
		}
		return( factoryTimeCol );
	}

	public ICFBamJavaFXTimeDefFactory getTimeDefFactory() {
		if( factoryTimeDef == null ) {
			factoryTimeDef = new CFBamJavaFXTimeDefFactory( this );
		}
		return( factoryTimeDef );
	}

	public ICFBamJavaFXTimeTypeFactory getTimeTypeFactory() {
		if( factoryTimeType == null ) {
			factoryTimeType = new CFBamJavaFXTimeTypeFactory( this );
		}
		return( factoryTimeType );
	}

	public ICFBamJavaFXTimestampColFactory getTimestampColFactory() {
		if( factoryTimestampCol == null ) {
			factoryTimestampCol = new CFBamJavaFXTimestampColFactory( this );
		}
		return( factoryTimestampCol );
	}

	public ICFBamJavaFXTimestampDefFactory getTimestampDefFactory() {
		if( factoryTimestampDef == null ) {
			factoryTimestampDef = new CFBamJavaFXTimestampDefFactory( this );
		}
		return( factoryTimestampDef );
	}

	public ICFBamJavaFXTimestampTypeFactory getTimestampTypeFactory() {
		if( factoryTimestampType == null ) {
			factoryTimestampType = new CFBamJavaFXTimestampTypeFactory( this );
		}
		return( factoryTimestampType );
	}

	public ICFIntJavaFXTldFactory getTldFactory() {
		if( factoryTld == null ) {
			factoryTld = new CFIntJavaFXTldFactory( this );
		}
		return( factoryTld );
	}

	public ICFBamJavaFXTokenColFactory getTokenColFactory() {
		if( factoryTokenCol == null ) {
			factoryTokenCol = new CFBamJavaFXTokenColFactory( this );
		}
		return( factoryTokenCol );
	}

	public ICFBamJavaFXTokenDefFactory getTokenDefFactory() {
		if( factoryTokenDef == null ) {
			factoryTokenDef = new CFBamJavaFXTokenDefFactory( this );
		}
		return( factoryTokenDef );
	}

	public ICFBamJavaFXTokenTypeFactory getTokenTypeFactory() {
		if( factoryTokenType == null ) {
			factoryTokenType = new CFBamJavaFXTokenTypeFactory( this );
		}
		return( factoryTokenType );
	}

	public ICFIntJavaFXTopDomainFactory getTopDomainFactory() {
		if( factoryTopDomain == null ) {
			factoryTopDomain = new CFIntJavaFXTopDomainFactory( this );
		}
		return( factoryTopDomain );
	}

	public ICFIntJavaFXTopProjectFactory getTopProjectFactory() {
		if( factoryTopProject == null ) {
			factoryTopProject = new CFIntJavaFXTopProjectFactory( this );
		}
		return( factoryTopProject );
	}

	public ICFBamJavaFXTweakFactory getTweakFactory() {
		if( factoryTweak == null ) {
			factoryTweak = new CFBamJavaFXTweakFactory( this );
		}
		return( factoryTweak );
	}

	public ICFBamJavaFXUInt16ColFactory getUInt16ColFactory() {
		if( factoryUInt16Col == null ) {
			factoryUInt16Col = new CFBamJavaFXUInt16ColFactory( this );
		}
		return( factoryUInt16Col );
	}

	public ICFBamJavaFXUInt16DefFactory getUInt16DefFactory() {
		if( factoryUInt16Def == null ) {
			factoryUInt16Def = new CFBamJavaFXUInt16DefFactory( this );
		}
		return( factoryUInt16Def );
	}

	public ICFBamJavaFXUInt16TypeFactory getUInt16TypeFactory() {
		if( factoryUInt16Type == null ) {
			factoryUInt16Type = new CFBamJavaFXUInt16TypeFactory( this );
		}
		return( factoryUInt16Type );
	}

	public ICFBamJavaFXUInt32ColFactory getUInt32ColFactory() {
		if( factoryUInt32Col == null ) {
			factoryUInt32Col = new CFBamJavaFXUInt32ColFactory( this );
		}
		return( factoryUInt32Col );
	}

	public ICFBamJavaFXUInt32DefFactory getUInt32DefFactory() {
		if( factoryUInt32Def == null ) {
			factoryUInt32Def = new CFBamJavaFXUInt32DefFactory( this );
		}
		return( factoryUInt32Def );
	}

	public ICFBamJavaFXUInt32TypeFactory getUInt32TypeFactory() {
		if( factoryUInt32Type == null ) {
			factoryUInt32Type = new CFBamJavaFXUInt32TypeFactory( this );
		}
		return( factoryUInt32Type );
	}

	public ICFBamJavaFXUInt64ColFactory getUInt64ColFactory() {
		if( factoryUInt64Col == null ) {
			factoryUInt64Col = new CFBamJavaFXUInt64ColFactory( this );
		}
		return( factoryUInt64Col );
	}

	public ICFBamJavaFXUInt64DefFactory getUInt64DefFactory() {
		if( factoryUInt64Def == null ) {
			factoryUInt64Def = new CFBamJavaFXUInt64DefFactory( this );
		}
		return( factoryUInt64Def );
	}

	public ICFBamJavaFXUInt64TypeFactory getUInt64TypeFactory() {
		if( factoryUInt64Type == null ) {
			factoryUInt64Type = new CFBamJavaFXUInt64TypeFactory( this );
		}
		return( factoryUInt64Type );
	}

	public ICFIntJavaFXURLProtocolFactory getURLProtocolFactory() {
		if( factoryURLProtocol == null ) {
			factoryURLProtocol = new CFIntJavaFXURLProtocolFactory( this );
		}
		return( factoryURLProtocol );
	}

	public ICFBamJavaFXUuid6ColFactory getUuid6ColFactory() {
		if( factoryUuid6Col == null ) {
			factoryUuid6Col = new CFBamJavaFXUuid6ColFactory( this );
		}
		return( factoryUuid6Col );
	}

	public ICFBamJavaFXUuid6DefFactory getUuid6DefFactory() {
		if( factoryUuid6Def == null ) {
			factoryUuid6Def = new CFBamJavaFXUuid6DefFactory( this );
		}
		return( factoryUuid6Def );
	}

	public ICFBamJavaFXUuid6GenFactory getUuid6GenFactory() {
		if( factoryUuid6Gen == null ) {
			factoryUuid6Gen = new CFBamJavaFXUuid6GenFactory( this );
		}
		return( factoryUuid6Gen );
	}

	public ICFBamJavaFXUuid6TypeFactory getUuid6TypeFactory() {
		if( factoryUuid6Type == null ) {
			factoryUuid6Type = new CFBamJavaFXUuid6TypeFactory( this );
		}
		return( factoryUuid6Type );
	}

	public ICFBamJavaFXUuidColFactory getUuidColFactory() {
		if( factoryUuidCol == null ) {
			factoryUuidCol = new CFBamJavaFXUuidColFactory( this );
		}
		return( factoryUuidCol );
	}

	public ICFBamJavaFXUuidDefFactory getUuidDefFactory() {
		if( factoryUuidDef == null ) {
			factoryUuidDef = new CFBamJavaFXUuidDefFactory( this );
		}
		return( factoryUuidDef );
	}

	public ICFBamJavaFXUuidGenFactory getUuidGenFactory() {
		if( factoryUuidGen == null ) {
			factoryUuidGen = new CFBamJavaFXUuidGenFactory( this );
		}
		return( factoryUuidGen );
	}

	public ICFBamJavaFXUuidTypeFactory getUuidTypeFactory() {
		if( factoryUuidType == null ) {
			factoryUuidType = new CFBamJavaFXUuidTypeFactory( this );
		}
		return( factoryUuidType );
	}

	public ICFBamJavaFXValueFactory getValueFactory() {
		if( factoryValue == null ) {
			factoryValue = new CFBamJavaFXValueFactory( this );
		}
		return( factoryValue );
	}
}
