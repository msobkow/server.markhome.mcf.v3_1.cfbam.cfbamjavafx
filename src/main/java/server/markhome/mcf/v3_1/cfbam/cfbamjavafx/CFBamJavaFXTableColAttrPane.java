// Description: Java 25 JavaFX Attribute Pane implementation for TableCol.

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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.Priority;
import server.markhome.mcf.v3_1.cflib.*;
import server.markhome.mcf.v3_1.cflib.dbutil.*;
import server.markhome.mcf.v3_1.cflib.inz.Inz;
import server.markhome.mcf.v3_1.cflib.javafx.*;
import server.markhome.mcf.v3_1.cflib.javafx.CFReferenceEditor.ICFReferenceCallback;
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
 *	CFBamJavaFXTableColAttrPane JavaFX Attribute Pane implementation
 *	for TableCol.
 */
public class CFBamJavaFXTableColAttrPane
extends CFGridPane
implements ICFBamJavaFXTableColPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFBamJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected class DataTypeCFLabel
		extends CFLabel
	{
		public DataTypeCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.TableCol.AttrPane.ParentDataType.EffLabel"));
		}
	}

	protected class CallbackDataTypeChosen
	implements ICFBamJavaFXValueChosen
	{
		public CallbackDataTypeChosen() {
		}

		public void choseValue( ICFBamValueObj value ) {
			if( javafxReferenceParentDataType != null ) {
				ICFBamTableColObj cur = getJavaFXFocusAsTableCol();
				if( cur != null ) {
					ICFBamTableColEditObj editObj = (ICFBamTableColEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceParentDataType.setReferencedObject( value );
							editObj.setRequiredParentDataType( value );
						}
					}
				}
			}
		}
	}

	protected class DataTypeReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
			ICFBamTableColObj focus = getEffJavaFXFocus();
			ICFBamValueObj referencedObj = (ICFBamValueObj)javafxReferenceParentDataType.getReferencedObject();
			java.util.List<ICFBamValueObj> listOfValue = null;
			ICFBamTableObj refTable = (ICFBamTableObj)focus.getRequiredContainerTable( javafxIsInitializing );
			if( refTable == null ) {
				CFConsole.message( String.format(Inz.s("cflibjavafx.common.SpecifyRelationBeforeSelectingTarget"), Inz.s("cfbam.javafx.TableCol.AttrPane.ContainerTable.EffLabel"), Inz.s("cfbam.javafx.TableCol.AttrPane.ParentDataType.EffLabel")));
				return;
			}
			ICFBamSchemaDefObj refSchemaDef = refTable.getRequiredContainerSchemaDef( javafxIsInitializing );
			if( refSchemaDef == null ) {
				CFConsole.message( String.format(Inz.s("cflibjavafx.common.SpecifyRelationBeforeSelectingTarget"), Inz.s("cfbam.javafx.Table.AttrPane.ContainerSchemaDef.EffLabel"), Inz.s("cfbam.javafx.TableCol.AttrPane.ParentDataType.EffLabel")));
				return;
			}
			listOfValue = refSchemaDef.getOptionalComponentsTypes( javafxIsInitializing );
			if( listOfValue == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"listOfValue" );
			}
			Collection<ICFBamValueObj> cltn = listOfValue;
			CFBorderPane form = javafxSchema.getValueFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackDataTypeChosen() );
			((ICFBamJavaFXValuePaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamTableColObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFBamValueObj referencedObj = (ICFBamValueObj)javafxReferenceParentDataType.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					int classCode = referencedObj.getClassCode();
					ICFSecSchema.ClassMapEntry entry = ICFBamSchema.getClassMapByRuntimeClassCode(classCode);
					int backingClassCode = entry.getBackingClassCode();
					if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamValue.CLASS_CODE ) {
						form = javafxSchema.getValueFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFBamJavaFXValuePaneCommon spec = (ICFBamJavaFXValuePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamAtom.CLASS_CODE ) {
						ICFBamAtomObj obj = (ICFBamAtomObj)referencedObj;
						form = javafxSchema.getAtomFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXAtomPaneCommon spec = (ICFBamJavaFXAtomPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamBlobDef.CLASS_CODE ) {
						ICFBamBlobDefObj obj = (ICFBamBlobDefObj)referencedObj;
						form = javafxSchema.getBlobDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXBlobDefPaneCommon spec = (ICFBamJavaFXBlobDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamBlobType.CLASS_CODE ) {
						ICFBamBlobTypeObj obj = (ICFBamBlobTypeObj)referencedObj;
						form = javafxSchema.getBlobTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXBlobTypePaneCommon spec = (ICFBamJavaFXBlobTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamBlobCol.CLASS_CODE ) {
						ICFBamBlobColObj obj = (ICFBamBlobColObj)referencedObj;
						form = javafxSchema.getBlobColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXBlobColPaneCommon spec = (ICFBamJavaFXBlobColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamBoolDef.CLASS_CODE ) {
						ICFBamBoolDefObj obj = (ICFBamBoolDefObj)referencedObj;
						form = javafxSchema.getBoolDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXBoolDefPaneCommon spec = (ICFBamJavaFXBoolDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamBoolType.CLASS_CODE ) {
						ICFBamBoolTypeObj obj = (ICFBamBoolTypeObj)referencedObj;
						form = javafxSchema.getBoolTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXBoolTypePaneCommon spec = (ICFBamJavaFXBoolTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamBoolCol.CLASS_CODE ) {
						ICFBamBoolColObj obj = (ICFBamBoolColObj)referencedObj;
						form = javafxSchema.getBoolColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXBoolColPaneCommon spec = (ICFBamJavaFXBoolColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDateDef.CLASS_CODE ) {
						ICFBamDateDefObj obj = (ICFBamDateDefObj)referencedObj;
						form = javafxSchema.getDateDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDateDefPaneCommon spec = (ICFBamJavaFXDateDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDateType.CLASS_CODE ) {
						ICFBamDateTypeObj obj = (ICFBamDateTypeObj)referencedObj;
						form = javafxSchema.getDateTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDateTypePaneCommon spec = (ICFBamJavaFXDateTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDateCol.CLASS_CODE ) {
						ICFBamDateColObj obj = (ICFBamDateColObj)referencedObj;
						form = javafxSchema.getDateColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDateColPaneCommon spec = (ICFBamJavaFXDateColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDoubleDef.CLASS_CODE ) {
						ICFBamDoubleDefObj obj = (ICFBamDoubleDefObj)referencedObj;
						form = javafxSchema.getDoubleDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDoubleDefPaneCommon spec = (ICFBamJavaFXDoubleDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDoubleType.CLASS_CODE ) {
						ICFBamDoubleTypeObj obj = (ICFBamDoubleTypeObj)referencedObj;
						form = javafxSchema.getDoubleTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDoubleTypePaneCommon spec = (ICFBamJavaFXDoubleTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDoubleCol.CLASS_CODE ) {
						ICFBamDoubleColObj obj = (ICFBamDoubleColObj)referencedObj;
						form = javafxSchema.getDoubleColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDoubleColPaneCommon spec = (ICFBamJavaFXDoubleColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamFloatDef.CLASS_CODE ) {
						ICFBamFloatDefObj obj = (ICFBamFloatDefObj)referencedObj;
						form = javafxSchema.getFloatDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXFloatDefPaneCommon spec = (ICFBamJavaFXFloatDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamFloatType.CLASS_CODE ) {
						ICFBamFloatTypeObj obj = (ICFBamFloatTypeObj)referencedObj;
						form = javafxSchema.getFloatTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXFloatTypePaneCommon spec = (ICFBamJavaFXFloatTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamFloatCol.CLASS_CODE ) {
						ICFBamFloatColObj obj = (ICFBamFloatColObj)referencedObj;
						form = javafxSchema.getFloatColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXFloatColPaneCommon spec = (ICFBamJavaFXFloatColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamInt16Def.CLASS_CODE ) {
						ICFBamInt16DefObj obj = (ICFBamInt16DefObj)referencedObj;
						form = javafxSchema.getInt16DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt16DefPaneCommon spec = (ICFBamJavaFXInt16DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamInt16Type.CLASS_CODE ) {
						ICFBamInt16TypeObj obj = (ICFBamInt16TypeObj)referencedObj;
						form = javafxSchema.getInt16TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt16TypePaneCommon spec = (ICFBamJavaFXInt16TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamId16Gen.CLASS_CODE ) {
						ICFBamId16GenObj obj = (ICFBamId16GenObj)referencedObj;
						form = javafxSchema.getId16GenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXId16GenPaneCommon spec = (ICFBamJavaFXId16GenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamEnumDef.CLASS_CODE ) {
						ICFBamEnumDefObj obj = (ICFBamEnumDefObj)referencedObj;
						form = javafxSchema.getEnumDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXEnumDefPaneCommon spec = (ICFBamJavaFXEnumDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamEnumType.CLASS_CODE ) {
						ICFBamEnumTypeObj obj = (ICFBamEnumTypeObj)referencedObj;
						form = javafxSchema.getEnumTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXEnumTypePaneCommon spec = (ICFBamJavaFXEnumTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamInt16Col.CLASS_CODE ) {
						ICFBamInt16ColObj obj = (ICFBamInt16ColObj)referencedObj;
						form = javafxSchema.getInt16ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt16ColPaneCommon spec = (ICFBamJavaFXInt16ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamInt32Def.CLASS_CODE ) {
						ICFBamInt32DefObj obj = (ICFBamInt32DefObj)referencedObj;
						form = javafxSchema.getInt32DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt32DefPaneCommon spec = (ICFBamJavaFXInt32DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamInt32Type.CLASS_CODE ) {
						ICFBamInt32TypeObj obj = (ICFBamInt32TypeObj)referencedObj;
						form = javafxSchema.getInt32TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt32TypePaneCommon spec = (ICFBamJavaFXInt32TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamId32Gen.CLASS_CODE ) {
						ICFBamId32GenObj obj = (ICFBamId32GenObj)referencedObj;
						form = javafxSchema.getId32GenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXId32GenPaneCommon spec = (ICFBamJavaFXId32GenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamInt32Col.CLASS_CODE ) {
						ICFBamInt32ColObj obj = (ICFBamInt32ColObj)referencedObj;
						form = javafxSchema.getInt32ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt32ColPaneCommon spec = (ICFBamJavaFXInt32ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamInt64Def.CLASS_CODE ) {
						ICFBamInt64DefObj obj = (ICFBamInt64DefObj)referencedObj;
						form = javafxSchema.getInt64DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt64DefPaneCommon spec = (ICFBamJavaFXInt64DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamInt64Type.CLASS_CODE ) {
						ICFBamInt64TypeObj obj = (ICFBamInt64TypeObj)referencedObj;
						form = javafxSchema.getInt64TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt64TypePaneCommon spec = (ICFBamJavaFXInt64TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamId64Gen.CLASS_CODE ) {
						ICFBamId64GenObj obj = (ICFBamId64GenObj)referencedObj;
						form = javafxSchema.getId64GenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXId64GenPaneCommon spec = (ICFBamJavaFXId64GenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamInt64Col.CLASS_CODE ) {
						ICFBamInt64ColObj obj = (ICFBamInt64ColObj)referencedObj;
						form = javafxSchema.getInt64ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXInt64ColPaneCommon spec = (ICFBamJavaFXInt64ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamNmTokenDef.CLASS_CODE ) {
						ICFBamNmTokenDefObj obj = (ICFBamNmTokenDefObj)referencedObj;
						form = javafxSchema.getNmTokenDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNmTokenDefPaneCommon spec = (ICFBamJavaFXNmTokenDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamNmTokenType.CLASS_CODE ) {
						ICFBamNmTokenTypeObj obj = (ICFBamNmTokenTypeObj)referencedObj;
						form = javafxSchema.getNmTokenTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNmTokenTypePaneCommon spec = (ICFBamJavaFXNmTokenTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamNmTokenCol.CLASS_CODE ) {
						ICFBamNmTokenColObj obj = (ICFBamNmTokenColObj)referencedObj;
						form = javafxSchema.getNmTokenColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNmTokenColPaneCommon spec = (ICFBamJavaFXNmTokenColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamNmTokensDef.CLASS_CODE ) {
						ICFBamNmTokensDefObj obj = (ICFBamNmTokensDefObj)referencedObj;
						form = javafxSchema.getNmTokensDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNmTokensDefPaneCommon spec = (ICFBamJavaFXNmTokensDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamNmTokensType.CLASS_CODE ) {
						ICFBamNmTokensTypeObj obj = (ICFBamNmTokensTypeObj)referencedObj;
						form = javafxSchema.getNmTokensTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNmTokensTypePaneCommon spec = (ICFBamJavaFXNmTokensTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamNmTokensCol.CLASS_CODE ) {
						ICFBamNmTokensColObj obj = (ICFBamNmTokensColObj)referencedObj;
						form = javafxSchema.getNmTokensColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNmTokensColPaneCommon spec = (ICFBamJavaFXNmTokensColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamNumberDef.CLASS_CODE ) {
						ICFBamNumberDefObj obj = (ICFBamNumberDefObj)referencedObj;
						form = javafxSchema.getNumberDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNumberDefPaneCommon spec = (ICFBamJavaFXNumberDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamNumberType.CLASS_CODE ) {
						ICFBamNumberTypeObj obj = (ICFBamNumberTypeObj)referencedObj;
						form = javafxSchema.getNumberTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNumberTypePaneCommon spec = (ICFBamJavaFXNumberTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamNumberCol.CLASS_CODE ) {
						ICFBamNumberColObj obj = (ICFBamNumberColObj)referencedObj;
						form = javafxSchema.getNumberColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXNumberColPaneCommon spec = (ICFBamJavaFXNumberColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash128Def.CLASS_CODE ) {
						ICFBamDbKeyHash128DefObj obj = (ICFBamDbKeyHash128DefObj)referencedObj;
						form = javafxSchema.getDbKeyHash128DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash128DefPaneCommon spec = (ICFBamJavaFXDbKeyHash128DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash128Col.CLASS_CODE ) {
						ICFBamDbKeyHash128ColObj obj = (ICFBamDbKeyHash128ColObj)referencedObj;
						form = javafxSchema.getDbKeyHash128ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash128ColPaneCommon spec = (ICFBamJavaFXDbKeyHash128ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash128Type.CLASS_CODE ) {
						ICFBamDbKeyHash128TypeObj obj = (ICFBamDbKeyHash128TypeObj)referencedObj;
						form = javafxSchema.getDbKeyHash128TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash128TypePaneCommon spec = (ICFBamJavaFXDbKeyHash128TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash128Gen.CLASS_CODE ) {
						ICFBamDbKeyHash128GenObj obj = (ICFBamDbKeyHash128GenObj)referencedObj;
						form = javafxSchema.getDbKeyHash128GenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash128GenPaneCommon spec = (ICFBamJavaFXDbKeyHash128GenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash160Def.CLASS_CODE ) {
						ICFBamDbKeyHash160DefObj obj = (ICFBamDbKeyHash160DefObj)referencedObj;
						form = javafxSchema.getDbKeyHash160DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash160DefPaneCommon spec = (ICFBamJavaFXDbKeyHash160DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash160Col.CLASS_CODE ) {
						ICFBamDbKeyHash160ColObj obj = (ICFBamDbKeyHash160ColObj)referencedObj;
						form = javafxSchema.getDbKeyHash160ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash160ColPaneCommon spec = (ICFBamJavaFXDbKeyHash160ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash160Type.CLASS_CODE ) {
						ICFBamDbKeyHash160TypeObj obj = (ICFBamDbKeyHash160TypeObj)referencedObj;
						form = javafxSchema.getDbKeyHash160TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash160TypePaneCommon spec = (ICFBamJavaFXDbKeyHash160TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash160Gen.CLASS_CODE ) {
						ICFBamDbKeyHash160GenObj obj = (ICFBamDbKeyHash160GenObj)referencedObj;
						form = javafxSchema.getDbKeyHash160GenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash160GenPaneCommon spec = (ICFBamJavaFXDbKeyHash160GenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash224Def.CLASS_CODE ) {
						ICFBamDbKeyHash224DefObj obj = (ICFBamDbKeyHash224DefObj)referencedObj;
						form = javafxSchema.getDbKeyHash224DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash224DefPaneCommon spec = (ICFBamJavaFXDbKeyHash224DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash224Col.CLASS_CODE ) {
						ICFBamDbKeyHash224ColObj obj = (ICFBamDbKeyHash224ColObj)referencedObj;
						form = javafxSchema.getDbKeyHash224ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash224ColPaneCommon spec = (ICFBamJavaFXDbKeyHash224ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash224Type.CLASS_CODE ) {
						ICFBamDbKeyHash224TypeObj obj = (ICFBamDbKeyHash224TypeObj)referencedObj;
						form = javafxSchema.getDbKeyHash224TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash224TypePaneCommon spec = (ICFBamJavaFXDbKeyHash224TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash224Gen.CLASS_CODE ) {
						ICFBamDbKeyHash224GenObj obj = (ICFBamDbKeyHash224GenObj)referencedObj;
						form = javafxSchema.getDbKeyHash224GenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash224GenPaneCommon spec = (ICFBamJavaFXDbKeyHash224GenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash256Def.CLASS_CODE ) {
						ICFBamDbKeyHash256DefObj obj = (ICFBamDbKeyHash256DefObj)referencedObj;
						form = javafxSchema.getDbKeyHash256DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash256DefPaneCommon spec = (ICFBamJavaFXDbKeyHash256DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash256Col.CLASS_CODE ) {
						ICFBamDbKeyHash256ColObj obj = (ICFBamDbKeyHash256ColObj)referencedObj;
						form = javafxSchema.getDbKeyHash256ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash256ColPaneCommon spec = (ICFBamJavaFXDbKeyHash256ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash256Type.CLASS_CODE ) {
						ICFBamDbKeyHash256TypeObj obj = (ICFBamDbKeyHash256TypeObj)referencedObj;
						form = javafxSchema.getDbKeyHash256TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash256TypePaneCommon spec = (ICFBamJavaFXDbKeyHash256TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash256Gen.CLASS_CODE ) {
						ICFBamDbKeyHash256GenObj obj = (ICFBamDbKeyHash256GenObj)referencedObj;
						form = javafxSchema.getDbKeyHash256GenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash256GenPaneCommon spec = (ICFBamJavaFXDbKeyHash256GenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash384Def.CLASS_CODE ) {
						ICFBamDbKeyHash384DefObj obj = (ICFBamDbKeyHash384DefObj)referencedObj;
						form = javafxSchema.getDbKeyHash384DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash384DefPaneCommon spec = (ICFBamJavaFXDbKeyHash384DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash384Col.CLASS_CODE ) {
						ICFBamDbKeyHash384ColObj obj = (ICFBamDbKeyHash384ColObj)referencedObj;
						form = javafxSchema.getDbKeyHash384ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash384ColPaneCommon spec = (ICFBamJavaFXDbKeyHash384ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash384Type.CLASS_CODE ) {
						ICFBamDbKeyHash384TypeObj obj = (ICFBamDbKeyHash384TypeObj)referencedObj;
						form = javafxSchema.getDbKeyHash384TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash384TypePaneCommon spec = (ICFBamJavaFXDbKeyHash384TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash384Gen.CLASS_CODE ) {
						ICFBamDbKeyHash384GenObj obj = (ICFBamDbKeyHash384GenObj)referencedObj;
						form = javafxSchema.getDbKeyHash384GenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash384GenPaneCommon spec = (ICFBamJavaFXDbKeyHash384GenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash512Def.CLASS_CODE ) {
						ICFBamDbKeyHash512DefObj obj = (ICFBamDbKeyHash512DefObj)referencedObj;
						form = javafxSchema.getDbKeyHash512DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash512DefPaneCommon spec = (ICFBamJavaFXDbKeyHash512DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash512Col.CLASS_CODE ) {
						ICFBamDbKeyHash512ColObj obj = (ICFBamDbKeyHash512ColObj)referencedObj;
						form = javafxSchema.getDbKeyHash512ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash512ColPaneCommon spec = (ICFBamJavaFXDbKeyHash512ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash512Type.CLASS_CODE ) {
						ICFBamDbKeyHash512TypeObj obj = (ICFBamDbKeyHash512TypeObj)referencedObj;
						form = javafxSchema.getDbKeyHash512TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash512TypePaneCommon spec = (ICFBamJavaFXDbKeyHash512TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamDbKeyHash512Gen.CLASS_CODE ) {
						ICFBamDbKeyHash512GenObj obj = (ICFBamDbKeyHash512GenObj)referencedObj;
						form = javafxSchema.getDbKeyHash512GenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXDbKeyHash512GenPaneCommon spec = (ICFBamJavaFXDbKeyHash512GenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamStringDef.CLASS_CODE ) {
						ICFBamStringDefObj obj = (ICFBamStringDefObj)referencedObj;
						form = javafxSchema.getStringDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXStringDefPaneCommon spec = (ICFBamJavaFXStringDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamStringType.CLASS_CODE ) {
						ICFBamStringTypeObj obj = (ICFBamStringTypeObj)referencedObj;
						form = javafxSchema.getStringTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXStringTypePaneCommon spec = (ICFBamJavaFXStringTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamStringCol.CLASS_CODE ) {
						ICFBamStringColObj obj = (ICFBamStringColObj)referencedObj;
						form = javafxSchema.getStringColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXStringColPaneCommon spec = (ICFBamJavaFXStringColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTZDateDef.CLASS_CODE ) {
						ICFBamTZDateDefObj obj = (ICFBamTZDateDefObj)referencedObj;
						form = javafxSchema.getTZDateDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZDateDefPaneCommon spec = (ICFBamJavaFXTZDateDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTZDateType.CLASS_CODE ) {
						ICFBamTZDateTypeObj obj = (ICFBamTZDateTypeObj)referencedObj;
						form = javafxSchema.getTZDateTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZDateTypePaneCommon spec = (ICFBamJavaFXTZDateTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTZDateCol.CLASS_CODE ) {
						ICFBamTZDateColObj obj = (ICFBamTZDateColObj)referencedObj;
						form = javafxSchema.getTZDateColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZDateColPaneCommon spec = (ICFBamJavaFXTZDateColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTZTimeDef.CLASS_CODE ) {
						ICFBamTZTimeDefObj obj = (ICFBamTZTimeDefObj)referencedObj;
						form = javafxSchema.getTZTimeDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZTimeDefPaneCommon spec = (ICFBamJavaFXTZTimeDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTZTimeType.CLASS_CODE ) {
						ICFBamTZTimeTypeObj obj = (ICFBamTZTimeTypeObj)referencedObj;
						form = javafxSchema.getTZTimeTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZTimeTypePaneCommon spec = (ICFBamJavaFXTZTimeTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTZTimeCol.CLASS_CODE ) {
						ICFBamTZTimeColObj obj = (ICFBamTZTimeColObj)referencedObj;
						form = javafxSchema.getTZTimeColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZTimeColPaneCommon spec = (ICFBamJavaFXTZTimeColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTZTimestampDef.CLASS_CODE ) {
						ICFBamTZTimestampDefObj obj = (ICFBamTZTimestampDefObj)referencedObj;
						form = javafxSchema.getTZTimestampDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZTimestampDefPaneCommon spec = (ICFBamJavaFXTZTimestampDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTZTimestampType.CLASS_CODE ) {
						ICFBamTZTimestampTypeObj obj = (ICFBamTZTimestampTypeObj)referencedObj;
						form = javafxSchema.getTZTimestampTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZTimestampTypePaneCommon spec = (ICFBamJavaFXTZTimestampTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTZTimestampCol.CLASS_CODE ) {
						ICFBamTZTimestampColObj obj = (ICFBamTZTimestampColObj)referencedObj;
						form = javafxSchema.getTZTimestampColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTZTimestampColPaneCommon spec = (ICFBamJavaFXTZTimestampColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTextDef.CLASS_CODE ) {
						ICFBamTextDefObj obj = (ICFBamTextDefObj)referencedObj;
						form = javafxSchema.getTextDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTextDefPaneCommon spec = (ICFBamJavaFXTextDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTextType.CLASS_CODE ) {
						ICFBamTextTypeObj obj = (ICFBamTextTypeObj)referencedObj;
						form = javafxSchema.getTextTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTextTypePaneCommon spec = (ICFBamJavaFXTextTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTextCol.CLASS_CODE ) {
						ICFBamTextColObj obj = (ICFBamTextColObj)referencedObj;
						form = javafxSchema.getTextColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTextColPaneCommon spec = (ICFBamJavaFXTextColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTimeDef.CLASS_CODE ) {
						ICFBamTimeDefObj obj = (ICFBamTimeDefObj)referencedObj;
						form = javafxSchema.getTimeDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTimeDefPaneCommon spec = (ICFBamJavaFXTimeDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTimeType.CLASS_CODE ) {
						ICFBamTimeTypeObj obj = (ICFBamTimeTypeObj)referencedObj;
						form = javafxSchema.getTimeTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTimeTypePaneCommon spec = (ICFBamJavaFXTimeTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTimeCol.CLASS_CODE ) {
						ICFBamTimeColObj obj = (ICFBamTimeColObj)referencedObj;
						form = javafxSchema.getTimeColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTimeColPaneCommon spec = (ICFBamJavaFXTimeColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTimestampDef.CLASS_CODE ) {
						ICFBamTimestampDefObj obj = (ICFBamTimestampDefObj)referencedObj;
						form = javafxSchema.getTimestampDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTimestampDefPaneCommon spec = (ICFBamJavaFXTimestampDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTimestampType.CLASS_CODE ) {
						ICFBamTimestampTypeObj obj = (ICFBamTimestampTypeObj)referencedObj;
						form = javafxSchema.getTimestampTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTimestampTypePaneCommon spec = (ICFBamJavaFXTimestampTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTimestampCol.CLASS_CODE ) {
						ICFBamTimestampColObj obj = (ICFBamTimestampColObj)referencedObj;
						form = javafxSchema.getTimestampColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTimestampColPaneCommon spec = (ICFBamJavaFXTimestampColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTokenDef.CLASS_CODE ) {
						ICFBamTokenDefObj obj = (ICFBamTokenDefObj)referencedObj;
						form = javafxSchema.getTokenDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTokenDefPaneCommon spec = (ICFBamJavaFXTokenDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTokenType.CLASS_CODE ) {
						ICFBamTokenTypeObj obj = (ICFBamTokenTypeObj)referencedObj;
						form = javafxSchema.getTokenTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTokenTypePaneCommon spec = (ICFBamJavaFXTokenTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTokenCol.CLASS_CODE ) {
						ICFBamTokenColObj obj = (ICFBamTokenColObj)referencedObj;
						form = javafxSchema.getTokenColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTokenColPaneCommon spec = (ICFBamJavaFXTokenColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUInt16Def.CLASS_CODE ) {
						ICFBamUInt16DefObj obj = (ICFBamUInt16DefObj)referencedObj;
						form = javafxSchema.getUInt16DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt16DefPaneCommon spec = (ICFBamJavaFXUInt16DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUInt16Type.CLASS_CODE ) {
						ICFBamUInt16TypeObj obj = (ICFBamUInt16TypeObj)referencedObj;
						form = javafxSchema.getUInt16TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt16TypePaneCommon spec = (ICFBamJavaFXUInt16TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUInt16Col.CLASS_CODE ) {
						ICFBamUInt16ColObj obj = (ICFBamUInt16ColObj)referencedObj;
						form = javafxSchema.getUInt16ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt16ColPaneCommon spec = (ICFBamJavaFXUInt16ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUInt32Def.CLASS_CODE ) {
						ICFBamUInt32DefObj obj = (ICFBamUInt32DefObj)referencedObj;
						form = javafxSchema.getUInt32DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt32DefPaneCommon spec = (ICFBamJavaFXUInt32DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUInt32Type.CLASS_CODE ) {
						ICFBamUInt32TypeObj obj = (ICFBamUInt32TypeObj)referencedObj;
						form = javafxSchema.getUInt32TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt32TypePaneCommon spec = (ICFBamJavaFXUInt32TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUInt32Col.CLASS_CODE ) {
						ICFBamUInt32ColObj obj = (ICFBamUInt32ColObj)referencedObj;
						form = javafxSchema.getUInt32ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt32ColPaneCommon spec = (ICFBamJavaFXUInt32ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUInt64Def.CLASS_CODE ) {
						ICFBamUInt64DefObj obj = (ICFBamUInt64DefObj)referencedObj;
						form = javafxSchema.getUInt64DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt64DefPaneCommon spec = (ICFBamJavaFXUInt64DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUInt64Type.CLASS_CODE ) {
						ICFBamUInt64TypeObj obj = (ICFBamUInt64TypeObj)referencedObj;
						form = javafxSchema.getUInt64TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt64TypePaneCommon spec = (ICFBamJavaFXUInt64TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUInt64Col.CLASS_CODE ) {
						ICFBamUInt64ColObj obj = (ICFBamUInt64ColObj)referencedObj;
						form = javafxSchema.getUInt64ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUInt64ColPaneCommon spec = (ICFBamJavaFXUInt64ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUuidDef.CLASS_CODE ) {
						ICFBamUuidDefObj obj = (ICFBamUuidDefObj)referencedObj;
						form = javafxSchema.getUuidDefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUuidDefPaneCommon spec = (ICFBamJavaFXUuidDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUuidType.CLASS_CODE ) {
						ICFBamUuidTypeObj obj = (ICFBamUuidTypeObj)referencedObj;
						form = javafxSchema.getUuidTypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUuidTypePaneCommon spec = (ICFBamJavaFXUuidTypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUuidGen.CLASS_CODE ) {
						ICFBamUuidGenObj obj = (ICFBamUuidGenObj)referencedObj;
						form = javafxSchema.getUuidGenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUuidGenPaneCommon spec = (ICFBamJavaFXUuidGenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUuidCol.CLASS_CODE ) {
						ICFBamUuidColObj obj = (ICFBamUuidColObj)referencedObj;
						form = javafxSchema.getUuidColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUuidColPaneCommon spec = (ICFBamJavaFXUuidColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUuid6Def.CLASS_CODE ) {
						ICFBamUuid6DefObj obj = (ICFBamUuid6DefObj)referencedObj;
						form = javafxSchema.getUuid6DefFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUuid6DefPaneCommon spec = (ICFBamJavaFXUuid6DefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUuid6Type.CLASS_CODE ) {
						ICFBamUuid6TypeObj obj = (ICFBamUuid6TypeObj)referencedObj;
						form = javafxSchema.getUuid6TypeFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUuid6TypePaneCommon spec = (ICFBamJavaFXUuid6TypePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUuid6Gen.CLASS_CODE ) {
						ICFBamUuid6GenObj obj = (ICFBamUuid6GenObj)referencedObj;
						form = javafxSchema.getUuid6GenFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUuid6GenPaneCommon spec = (ICFBamJavaFXUuid6GenPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamUuid6Col.CLASS_CODE ) {
						ICFBamUuid6ColObj obj = (ICFBamUuid6ColObj)referencedObj;
						form = javafxSchema.getUuid6ColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXUuid6ColPaneCommon spec = (ICFBamJavaFXUuid6ColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTableCol.CLASS_CODE ) {
						ICFBamTableColObj obj = (ICFBamTableColObj)referencedObj;
						form = javafxSchema.getTableColFactory().newAddForm( cfFormManager, obj, null, true );
						ICFBamJavaFXTableColPaneCommon spec = (ICFBamJavaFXTableColPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFBamValueObj, ICFBamAtomObj, ICFBamBlobDefObj, ICFBamBlobTypeObj, ICFBamBlobColObj, ICFBamBoolDefObj, ICFBamBoolTypeObj, ICFBamBoolColObj, ICFBamDateDefObj, ICFBamDateTypeObj, ICFBamDateColObj, ICFBamDoubleDefObj, ICFBamDoubleTypeObj, ICFBamDoubleColObj, ICFBamFloatDefObj, ICFBamFloatTypeObj, ICFBamFloatColObj, ICFBamInt16DefObj, ICFBamInt16TypeObj, ICFBamId16GenObj, ICFBamEnumDefObj, ICFBamEnumTypeObj, ICFBamInt16ColObj, ICFBamInt32DefObj, ICFBamInt32TypeObj, ICFBamId32GenObj, ICFBamInt32ColObj, ICFBamInt64DefObj, ICFBamInt64TypeObj, ICFBamId64GenObj, ICFBamInt64ColObj, ICFBamNmTokenDefObj, ICFBamNmTokenTypeObj, ICFBamNmTokenColObj, ICFBamNmTokensDefObj, ICFBamNmTokensTypeObj, ICFBamNmTokensColObj, ICFBamNumberDefObj, ICFBamNumberTypeObj, ICFBamNumberColObj, ICFBamDbKeyHash128DefObj, ICFBamDbKeyHash128ColObj, ICFBamDbKeyHash128TypeObj, ICFBamDbKeyHash128GenObj, ICFBamDbKeyHash160DefObj, ICFBamDbKeyHash160ColObj, ICFBamDbKeyHash160TypeObj, ICFBamDbKeyHash160GenObj, ICFBamDbKeyHash224DefObj, ICFBamDbKeyHash224ColObj, ICFBamDbKeyHash224TypeObj, ICFBamDbKeyHash224GenObj, ICFBamDbKeyHash256DefObj, ICFBamDbKeyHash256ColObj, ICFBamDbKeyHash256TypeObj, ICFBamDbKeyHash256GenObj, ICFBamDbKeyHash384DefObj, ICFBamDbKeyHash384ColObj, ICFBamDbKeyHash384TypeObj, ICFBamDbKeyHash384GenObj, ICFBamDbKeyHash512DefObj, ICFBamDbKeyHash512ColObj, ICFBamDbKeyHash512TypeObj, ICFBamDbKeyHash512GenObj, ICFBamStringDefObj, ICFBamStringTypeObj, ICFBamStringColObj, ICFBamTZDateDefObj, ICFBamTZDateTypeObj, ICFBamTZDateColObj, ICFBamTZTimeDefObj, ICFBamTZTimeTypeObj, ICFBamTZTimeColObj, ICFBamTZTimestampDefObj, ICFBamTZTimestampTypeObj, ICFBamTZTimestampColObj, ICFBamTextDefObj, ICFBamTextTypeObj, ICFBamTextColObj, ICFBamTimeDefObj, ICFBamTimeTypeObj, ICFBamTimeColObj, ICFBamTimestampDefObj, ICFBamTimestampTypeObj, ICFBamTimestampColObj, ICFBamTokenDefObj, ICFBamTokenTypeObj, ICFBamTokenColObj, ICFBamUInt16DefObj, ICFBamUInt16TypeObj, ICFBamUInt16ColObj, ICFBamUInt32DefObj, ICFBamUInt32TypeObj, ICFBamUInt32ColObj, ICFBamUInt64DefObj, ICFBamUInt64TypeObj, ICFBamUInt64ColObj, ICFBamUuidDefObj, ICFBamUuidTypeObj, ICFBamUuidGenObj, ICFBamUuidColObj, ICFBamUuid6DefObj, ICFBamUuid6TypeObj, ICFBamUuid6GenObj, ICFBamUuid6ColObj, ICFBamTableColObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
	}

	protected class DataTypeCFReferenceEditor
		extends CFReferenceEditor
	{
		public DataTypeCFReferenceEditor() {
			super( new DataTypeReferenceCallback() );
			setFieldNameInzTag( "cfbam.javafx.TableCol.AttrPane.DataType.EffLabel" );
		}
	}

	protected class DefSchemaCFLabel
		extends CFLabel
	{
		public DefSchemaCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Value.AttrPane.LookupDefSchema.EffLabel"));
		}
	}

	protected class CallbackDefSchemaChosen
	implements ICFBamJavaFXSchemaDefChosen
	{
		public CallbackDefSchemaChosen() {
		}

		public void choseSchemaDef( ICFBamSchemaDefObj value ) {
			if( javafxReferenceLookupDefSchema != null ) {
				ICFBamTableColObj cur = getJavaFXFocusAsTableCol();
				if( cur != null ) {
					ICFBamTableColEditObj editObj = (ICFBamTableColEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupDefSchema.setReferencedObject( value );
							editObj.setOptionalLookupDefSchema( value );
						}
					}
				}
			}
		}
	}

	protected class DefSchemaReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
			ICFBamTableColObj focus = getEffJavaFXFocus();
			ICFBamSchemaDefObj referencedObj = (ICFBamSchemaDefObj)javafxReferenceLookupDefSchema.getReferencedObject();
			java.util.List<ICFBamSchemaDefObj> listOfSchemaDef = null;
			Collection<ICFBamSchemaDefObj> cltn = null;
			CFBorderPane form = javafxSchema.getSchemaDefFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackDefSchemaChosen() );
			((ICFBamJavaFXSchemaDefPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamTableColObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFBamSchemaDefObj referencedObj = (ICFBamSchemaDefObj)javafxReferenceLookupDefSchema.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					int classCode = referencedObj.getClassCode();
					ICFSecSchema.ClassMapEntry entry = ICFBamSchema.getClassMapByRuntimeClassCode(classCode);
					int backingClassCode = entry.getBackingClassCode();
					if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamSchemaDef.CLASS_CODE ) {
						form = javafxSchema.getSchemaDefFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFBamJavaFXSchemaDefPaneCommon spec = (ICFBamJavaFXSchemaDefPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFBamSchemaDefObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
	}

	protected class DefSchemaCFReferenceEditor
		extends CFReferenceEditor
	{
		public DefSchemaCFReferenceEditor() {
			super( new DefSchemaReferenceCallback() );
			setFieldNameInzTag( "cfbam.javafx.Value.AttrPane.DefSchema.EffLabel" );
		}
	}

	protected class IdCFLabel
		extends CFLabel
	{
		public IdCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Value.AttrPane.Id.EffLabel"));
		}
	}

	protected class IdEditor
		extends CFDbKeyHash256Editor
	{
		public IdEditor() {
			super();
			setFieldNameInzTag( "cfbam.javafx.Value.AttrPane.Id.EffLabel" );
		}
	}

	protected class NameCFLabel
		extends CFLabel
	{
		public NameCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Value.AttrPane.Name.EffLabel"));
		}
	}

	protected class NameEditor
		extends CFStringEditor
	{
		public NameEditor() {
			super();
			setMaxLen( 192 );
			setFieldNameInzTag( "cfbam.javafx.Value.AttrPane.Name.EffLabel" );
		}
	}

	protected class ShortNameCFLabel
		extends CFLabel
	{
		public ShortNameCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Value.AttrPane.ShortName.EffLabel"));
		}
	}

	protected class ShortNameEditor
		extends CFStringEditor
	{
		public ShortNameEditor() {
			super();
			setMaxLen( 16 );
			setFieldNameInzTag( "cfbam.javafx.Value.AttrPane.ShortName.EffLabel" );
		}
	}

	protected class LabelCFLabel
		extends CFLabel
	{
		public LabelCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Value.AttrPane.Label.EffLabel"));
		}
	}

	protected class LabelEditor
		extends CFStringEditor
	{
		public LabelEditor() {
			super();
			setMaxLen( 64 );
			setFieldNameInzTag( "cfbam.javafx.Value.AttrPane.Label.EffLabel" );
		}
	}

	protected class ShortDescriptionCFLabel
		extends CFLabel
	{
		public ShortDescriptionCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Value.AttrPane.ShortDescription.EffLabel"));
		}
	}

	protected class ShortDescriptionEditor
		extends CFStringEditor
	{
		public ShortDescriptionEditor() {
			super();
			setMaxLen( 128 );
			setFieldNameInzTag( "cfbam.javafx.Value.AttrPane.ShortDescription.EffLabel" );
		}
	}

	protected class DescriptionCFLabel
		extends CFLabel
	{
		public DescriptionCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Value.AttrPane.Description.EffLabel"));
		}
	}

	protected class DescriptionEditor
		extends CFStringEditor
	{
		public DescriptionEditor() {
			super();
			setMaxLen( 1023 );
			setFieldNameInzTag( "cfbam.javafx.Value.AttrPane.Description.EffLabel" );
		}
	}

	protected class DefaultXmlValueCFLabel
		extends CFLabel
	{
		public DefaultXmlValueCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Value.AttrPane.DefaultXmlValue.EffLabel"));
		}
	}

	protected class DefaultXmlValueEditor
		extends CFStringEditor
	{
		public DefaultXmlValueEditor() {
			super();
			setMaxLen( 1023 );
			setFieldNameInzTag( "cfbam.javafx.Value.AttrPane.DefaultXmlValue.EffLabel" );
		}
	}

	protected class IsNullableCFLabel
		extends CFLabel
	{
		public IsNullableCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Value.AttrPane.IsNullable.EffLabel"));
		}
	}

	protected class IsNullableEditor
		extends CFBoolEditor
	{
		public IsNullableEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfbam.javafx.Value.AttrPane.IsNullable.EffLabel" );
		}
	}

	protected class GenerateIdCFLabel
		extends CFLabel
	{
		public GenerateIdCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Value.AttrPane.GenerateId.EffLabel"));
		}
	}

	protected class GenerateIdEditor
		extends CFBoolEditor
	{
		public GenerateIdEditor() {
			super();
			setIsNullable( true );
			setFieldNameInzTag( "cfbam.javafx.Value.AttrPane.GenerateId.EffLabel" );
		}
	}

	protected class ImplementsPolymorphCFLabel
		extends CFLabel
	{
		public ImplementsPolymorphCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Value.AttrPane.ImplementsPolymorph.EffLabel"));
		}
	}

	protected class ImplementsPolymorphEditor
		extends CFBoolEditor
	{
		public ImplementsPolymorphEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfbam.javafx.Value.AttrPane.ImplementsPolymorph.EffLabel" );
		}
	}

	protected class DbNameCFLabel
		extends CFLabel
	{
		public DbNameCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.TableCol.AttrPane.DbName.EffLabel"));
		}
	}

	protected class DbNameEditor
		extends CFStringEditor
	{
		public DbNameEditor() {
			super();
			setMaxLen( 32 );
			setFieldNameInzTag( "cfbam.javafx.TableCol.AttrPane.DbName.EffLabel" );
		}
	}

	protected class XmlElementNameCFLabel
		extends CFLabel
	{
		public XmlElementNameCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.TableCol.AttrPane.XmlElementName.EffLabel"));
		}
	}

	protected class XmlElementNameEditor
		extends CFStringEditor
	{
		public XmlElementNameEditor() {
			super();
			setMaxLen( 192 );
			setFieldNameInzTag( "cfbam.javafx.TableCol.AttrPane.XmlElementName.EffLabel" );
		}
	}

	protected ICFBamValueObj javafxParentDataTypeObj = null;
	protected DataTypeCFLabel javafxLabelParentDataType = null;
	protected DataTypeCFReferenceEditor javafxReferenceParentDataType = null;
	protected ICFBamSchemaDefObj javafxLookupDefSchemaObj = null;
	protected DefSchemaCFLabel javafxLabelLookupDefSchema = null;
	protected DefSchemaCFReferenceEditor javafxReferenceLookupDefSchema = null;
	protected IdCFLabel javafxLabelId = null;
	protected IdEditor javafxEditorId = null;
	protected NameCFLabel javafxLabelName = null;
	protected NameEditor javafxEditorName = null;
	protected ShortNameCFLabel javafxLabelShortName = null;
	protected ShortNameEditor javafxEditorShortName = null;
	protected LabelCFLabel javafxLabelLabel = null;
	protected LabelEditor javafxEditorLabel = null;
	protected ShortDescriptionCFLabel javafxLabelShortDescription = null;
	protected ShortDescriptionEditor javafxEditorShortDescription = null;
	protected DescriptionCFLabel javafxLabelDescription = null;
	protected DescriptionEditor javafxEditorDescription = null;
	protected DefaultXmlValueCFLabel javafxLabelDefaultXmlValue = null;
	protected DefaultXmlValueEditor javafxEditorDefaultXmlValue = null;
	protected IsNullableCFLabel javafxLabelIsNullable = null;
	protected IsNullableEditor javafxEditorIsNullable = null;
	protected GenerateIdCFLabel javafxLabelGenerateId = null;
	protected GenerateIdEditor javafxEditorGenerateId = null;
	protected ImplementsPolymorphCFLabel javafxLabelImplementsPolymorph = null;
	protected ImplementsPolymorphEditor javafxEditorImplementsPolymorph = null;
	protected DbNameCFLabel javafxLabelDbName = null;
	protected DbNameEditor javafxEditorDbName = null;
	protected XmlElementNameCFLabel javafxLabelXmlElementName = null;
	protected XmlElementNameEditor javafxEditorXmlElementName = null;

	public CFBamJavaFXTableColAttrPane( ICFFormManager formManager, ICFBamJavaFXSchema argSchema, ICFBamTableColObj argFocus ) {
		super();
		Control ctrl;
		CFLabel label;
		CFReferenceEditor reference;
		final String S_ProcName = "construct-schema-focus";
		if( formManager == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"formManager" );
		}
		cfFormManager = formManager;
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				2,
				"argSchema" );
		}
		// argFocus is optional; focus may be set later during execution as
		// conditions of the runtime change.
		javafxSchema = argSchema;
		setJavaFXFocusAsTableCol( argFocus );
		setPadding( new Insets(5) );
		setHgap( 5 );
		setVgap( 5 );
		setAlignment( Pos.CENTER );
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth( 100 );
		getColumnConstraints().addAll( column1 );
		int gridRow = 0;
		label = getJavaFXLabelParentDataType();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceParentDataType();
		setHalignment( reference, HPos.LEFT );
		add( reference, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelLookupDefSchema();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceLookupDefSchema();
		setHalignment( reference, HPos.LEFT );
		add( reference, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelId();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorId();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelName();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorName();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelShortName();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorShortName();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelLabel();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorLabel();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelShortDescription();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorShortDescription();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelDescription();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorDescription();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelDefaultXmlValue();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorDefaultXmlValue();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelIsNullable();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorIsNullable();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelGenerateId();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorGenerateId();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelImplementsPolymorph();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorImplementsPolymorph();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelDbName();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorDbName();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelXmlElementName();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorXmlElementName();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		populateFields();
		adjustComponentEnableStates();
		javafxIsInitializing = false;
	}

	public ICFFormManager getCFFormManager() {
		return( cfFormManager );
	}

	public void setCFFormManager( ICFFormManager value ) {
		final String S_ProcName = "setCFFormManager";
		if( value == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				1,
				"value" );
		}
		cfFormManager = value;
	}

	public ICFBamJavaFXSchema getJavaFXSchema() {
		return( javafxSchema );
	}

	public void setJavaFXFocus( ICFLibAnyObj value ) {
		final String S_ProcName = "setJavaFXFocus";
		if( ( value == null ) || ( value instanceof ICFBamTableColObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamTableColObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFBamTableColObj getJavaFXFocusAsTableCol() {
		return( (ICFBamTableColObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsTableCol( ICFBamTableColObj value ) {
		setJavaFXFocus( value );
	}

	public ICFBamTableColObj getEffJavaFXFocus() {
		ICFBamTableColObj eff = (ICFBamTableColObj)getJavaFXFocus();
		if( eff != null ) {
			if( null != eff.getEdit() ) {
				eff = (ICFBamTableColObj)eff.getEdit();
			}
		}
		return( eff );
	}

	public ICFBamValueObj getJavaFXParentDataTypeObj() {
		return( javafxParentDataTypeObj );
	}

	public void setJavaFXParentDataTypeObj( ICFBamValueObj value ) {
		javafxParentDataTypeObj = value;
	}

	public CFLabel getJavaFXLabelParentDataType() {
		if( javafxLabelParentDataType == null ) {
			javafxLabelParentDataType = new DataTypeCFLabel();
		}
		return( javafxLabelParentDataType );
	}

	public CFReferenceEditor getJavaFXReferenceParentDataType() {
		if( javafxReferenceParentDataType == null ) {
			javafxReferenceParentDataType = new DataTypeCFReferenceEditor();
		}
		return( javafxReferenceParentDataType );
	}

	public void setJavaFXReferenceParentDataType( DataTypeCFReferenceEditor value ) {
		javafxReferenceParentDataType = value;
	}

	public ICFBamSchemaDefObj getJavaFXLookupDefSchemaObj() {
		return( javafxLookupDefSchemaObj );
	}

	public void setJavaFXLookupDefSchemaObj( ICFBamSchemaDefObj value ) {
		javafxLookupDefSchemaObj = value;
	}

	public CFLabel getJavaFXLabelLookupDefSchema() {
		if( javafxLabelLookupDefSchema == null ) {
			javafxLabelLookupDefSchema = new DefSchemaCFLabel();
		}
		return( javafxLabelLookupDefSchema );
	}

	public CFReferenceEditor getJavaFXReferenceLookupDefSchema() {
		if( javafxReferenceLookupDefSchema == null ) {
			javafxReferenceLookupDefSchema = new DefSchemaCFReferenceEditor();
		}
		return( javafxReferenceLookupDefSchema );
	}

	public void setJavaFXReferenceLookupDefSchema( DefSchemaCFReferenceEditor value ) {
		javafxReferenceLookupDefSchema = value;
	}

	public IdCFLabel getJavaFXLabelId() {
		if( javafxLabelId == null ) {
			javafxLabelId = new IdCFLabel();
		}
		return( javafxLabelId );
	}

	public void setJavaFXLabelId( IdCFLabel value ) {
		javafxLabelId = value;
	}

	public IdEditor getJavaFXEditorId() {
		if( javafxEditorId == null ) {
			javafxEditorId = new IdEditor();
		}
		return( javafxEditorId );
	}

	public void setJavaFXEditorId( IdEditor value ) {
		javafxEditorId = value;
	}

	public NameCFLabel getJavaFXLabelName() {
		if( javafxLabelName == null ) {
			javafxLabelName = new NameCFLabel();
		}
		return( javafxLabelName );
	}

	public void setJavaFXLabelName( NameCFLabel value ) {
		javafxLabelName = value;
	}

	public NameEditor getJavaFXEditorName() {
		if( javafxEditorName == null ) {
			javafxEditorName = new NameEditor();
		}
		return( javafxEditorName );
	}

	public void setJavaFXEditorName( NameEditor value ) {
		javafxEditorName = value;
	}

	public ShortNameCFLabel getJavaFXLabelShortName() {
		if( javafxLabelShortName == null ) {
			javafxLabelShortName = new ShortNameCFLabel();
		}
		return( javafxLabelShortName );
	}

	public void setJavaFXLabelShortName( ShortNameCFLabel value ) {
		javafxLabelShortName = value;
	}

	public ShortNameEditor getJavaFXEditorShortName() {
		if( javafxEditorShortName == null ) {
			javafxEditorShortName = new ShortNameEditor();
		}
		return( javafxEditorShortName );
	}

	public void setJavaFXEditorShortName( ShortNameEditor value ) {
		javafxEditorShortName = value;
	}

	public LabelCFLabel getJavaFXLabelLabel() {
		if( javafxLabelLabel == null ) {
			javafxLabelLabel = new LabelCFLabel();
		}
		return( javafxLabelLabel );
	}

	public void setJavaFXLabelLabel( LabelCFLabel value ) {
		javafxLabelLabel = value;
	}

	public LabelEditor getJavaFXEditorLabel() {
		if( javafxEditorLabel == null ) {
			javafxEditorLabel = new LabelEditor();
		}
		return( javafxEditorLabel );
	}

	public void setJavaFXEditorLabel( LabelEditor value ) {
		javafxEditorLabel = value;
	}

	public ShortDescriptionCFLabel getJavaFXLabelShortDescription() {
		if( javafxLabelShortDescription == null ) {
			javafxLabelShortDescription = new ShortDescriptionCFLabel();
		}
		return( javafxLabelShortDescription );
	}

	public void setJavaFXLabelShortDescription( ShortDescriptionCFLabel value ) {
		javafxLabelShortDescription = value;
	}

	public ShortDescriptionEditor getJavaFXEditorShortDescription() {
		if( javafxEditorShortDescription == null ) {
			javafxEditorShortDescription = new ShortDescriptionEditor();
		}
		return( javafxEditorShortDescription );
	}

	public void setJavaFXEditorShortDescription( ShortDescriptionEditor value ) {
		javafxEditorShortDescription = value;
	}

	public DescriptionCFLabel getJavaFXLabelDescription() {
		if( javafxLabelDescription == null ) {
			javafxLabelDescription = new DescriptionCFLabel();
		}
		return( javafxLabelDescription );
	}

	public void setJavaFXLabelDescription( DescriptionCFLabel value ) {
		javafxLabelDescription = value;
	}

	public DescriptionEditor getJavaFXEditorDescription() {
		if( javafxEditorDescription == null ) {
			javafxEditorDescription = new DescriptionEditor();
		}
		return( javafxEditorDescription );
	}

	public void setJavaFXEditorDescription( DescriptionEditor value ) {
		javafxEditorDescription = value;
	}

	public DefaultXmlValueCFLabel getJavaFXLabelDefaultXmlValue() {
		if( javafxLabelDefaultXmlValue == null ) {
			javafxLabelDefaultXmlValue = new DefaultXmlValueCFLabel();
		}
		return( javafxLabelDefaultXmlValue );
	}

	public void setJavaFXLabelDefaultXmlValue( DefaultXmlValueCFLabel value ) {
		javafxLabelDefaultXmlValue = value;
	}

	public DefaultXmlValueEditor getJavaFXEditorDefaultXmlValue() {
		if( javafxEditorDefaultXmlValue == null ) {
			javafxEditorDefaultXmlValue = new DefaultXmlValueEditor();
		}
		return( javafxEditorDefaultXmlValue );
	}

	public void setJavaFXEditorDefaultXmlValue( DefaultXmlValueEditor value ) {
		javafxEditorDefaultXmlValue = value;
	}

	public IsNullableCFLabel getJavaFXLabelIsNullable() {
		if( javafxLabelIsNullable == null ) {
			javafxLabelIsNullable = new IsNullableCFLabel();
		}
		return( javafxLabelIsNullable );
	}

	public void setJavaFXLabelIsNullable( IsNullableCFLabel value ) {
		javafxLabelIsNullable = value;
	}

	public IsNullableEditor getJavaFXEditorIsNullable() {
		if( javafxEditorIsNullable == null ) {
			javafxEditorIsNullable = new IsNullableEditor();
		}
		return( javafxEditorIsNullable );
	}

	public void setJavaFXEditorIsNullable( IsNullableEditor value ) {
		javafxEditorIsNullable = value;
	}

	public GenerateIdCFLabel getJavaFXLabelGenerateId() {
		if( javafxLabelGenerateId == null ) {
			javafxLabelGenerateId = new GenerateIdCFLabel();
		}
		return( javafxLabelGenerateId );
	}

	public void setJavaFXLabelGenerateId( GenerateIdCFLabel value ) {
		javafxLabelGenerateId = value;
	}

	public GenerateIdEditor getJavaFXEditorGenerateId() {
		if( javafxEditorGenerateId == null ) {
			javafxEditorGenerateId = new GenerateIdEditor();
		}
		return( javafxEditorGenerateId );
	}

	public void setJavaFXEditorGenerateId( GenerateIdEditor value ) {
		javafxEditorGenerateId = value;
	}

	public ImplementsPolymorphCFLabel getJavaFXLabelImplementsPolymorph() {
		if( javafxLabelImplementsPolymorph == null ) {
			javafxLabelImplementsPolymorph = new ImplementsPolymorphCFLabel();
		}
		return( javafxLabelImplementsPolymorph );
	}

	public void setJavaFXLabelImplementsPolymorph( ImplementsPolymorphCFLabel value ) {
		javafxLabelImplementsPolymorph = value;
	}

	public ImplementsPolymorphEditor getJavaFXEditorImplementsPolymorph() {
		if( javafxEditorImplementsPolymorph == null ) {
			javafxEditorImplementsPolymorph = new ImplementsPolymorphEditor();
		}
		return( javafxEditorImplementsPolymorph );
	}

	public void setJavaFXEditorImplementsPolymorph( ImplementsPolymorphEditor value ) {
		javafxEditorImplementsPolymorph = value;
	}

	public DbNameCFLabel getJavaFXLabelDbName() {
		if( javafxLabelDbName == null ) {
			javafxLabelDbName = new DbNameCFLabel();
		}
		return( javafxLabelDbName );
	}

	public void setJavaFXLabelDbName( DbNameCFLabel value ) {
		javafxLabelDbName = value;
	}

	public DbNameEditor getJavaFXEditorDbName() {
		if( javafxEditorDbName == null ) {
			javafxEditorDbName = new DbNameEditor();
		}
		return( javafxEditorDbName );
	}

	public void setJavaFXEditorDbName( DbNameEditor value ) {
		javafxEditorDbName = value;
	}

	public XmlElementNameCFLabel getJavaFXLabelXmlElementName() {
		if( javafxLabelXmlElementName == null ) {
			javafxLabelXmlElementName = new XmlElementNameCFLabel();
		}
		return( javafxLabelXmlElementName );
	}

	public void setJavaFXLabelXmlElementName( XmlElementNameCFLabel value ) {
		javafxLabelXmlElementName = value;
	}

	public XmlElementNameEditor getJavaFXEditorXmlElementName() {
		if( javafxEditorXmlElementName == null ) {
			javafxEditorXmlElementName = new XmlElementNameEditor();
		}
		return( javafxEditorXmlElementName );
	}

	public void setJavaFXEditorXmlElementName( XmlElementNameEditor value ) {
		javafxEditorXmlElementName = value;
	}

	public void populateFields()
	{
		ICFBamTableColObj popObj = getEffJavaFXFocus();
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			popObj = null;
		}
		if( popObj == null ) {
			javafxParentDataTypeObj = null;
		}
		else {
			javafxParentDataTypeObj = (ICFBamValueObj)popObj.getRequiredParentDataType( javafxIsInitializing );
		}
		if( javafxReferenceParentDataType != null ) {
			javafxReferenceParentDataType.setReferencedObject( javafxParentDataTypeObj );
		}

		if( popObj == null ) {
			javafxLookupDefSchemaObj = null;
		}
		else {
			javafxLookupDefSchemaObj = (ICFBamSchemaDefObj)popObj.getOptionalLookupDefSchema( javafxIsInitializing );
		}
		if( javafxReferenceLookupDefSchema != null ) {
			javafxReferenceLookupDefSchema.setReferencedObject( javafxLookupDefSchemaObj );
		}

		if( popObj == null ) {
			getJavaFXEditorId().setDbKeyHash256Value( null );
		}
		else {
			getJavaFXEditorId().setDbKeyHash256Value( popObj.getRequiredId() );
		}

		if( popObj == null ) {
			getJavaFXEditorName().setStringValue( null );
		}
		else {
			getJavaFXEditorName().setStringValue( popObj.getRequiredName() );
		}

		if( popObj == null ) {
			getJavaFXEditorShortName().setStringValue( null );
		}
		else {
			getJavaFXEditorShortName().setStringValue( popObj.getOptionalShortName() );
		}

		if( popObj == null ) {
			getJavaFXEditorLabel().setStringValue( null );
		}
		else {
			getJavaFXEditorLabel().setStringValue( popObj.getOptionalLabel() );
		}

		if( popObj == null ) {
			getJavaFXEditorShortDescription().setStringValue( null );
		}
		else {
			getJavaFXEditorShortDescription().setStringValue( popObj.getOptionalShortDescription() );
		}

		if( popObj == null ) {
			getJavaFXEditorDescription().setStringValue( null );
		}
		else {
			getJavaFXEditorDescription().setStringValue( popObj.getOptionalDescription() );
		}

		if( popObj == null ) {
			getJavaFXEditorDefaultXmlValue().setStringValue( null );
		}
		else {
			getJavaFXEditorDefaultXmlValue().setStringValue( popObj.getOptionalDefaultXmlValue() );
		}

		if( popObj == null ) {
			getJavaFXEditorIsNullable().setBooleanValue( null );
		}
		else {
			getJavaFXEditorIsNullable().setBooleanValue( popObj.getRequiredIsNullable() );
		}

		if( popObj == null ) {
			getJavaFXEditorGenerateId().setBooleanValue( null );
		}
		else {
			getJavaFXEditorGenerateId().setBooleanValue( popObj.getOptionalGenerateId() );
		}

		if( popObj == null ) {
			getJavaFXEditorImplementsPolymorph().setBooleanValue( null );
		}
		else {
			getJavaFXEditorImplementsPolymorph().setBooleanValue( popObj.getRequiredImplementsPolymorph() );
		}

		if( popObj == null ) {
			getJavaFXEditorDbName().setStringValue( null );
		}
		else {
			getJavaFXEditorDbName().setStringValue( popObj.getOptionalDbName() );
		}

		if( popObj == null ) {
			getJavaFXEditorXmlElementName().setStringValue( null );
		}
		else {
			getJavaFXEditorXmlElementName().setStringValue( popObj.getOptionalXmlElementName() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFBamTableColObj focus = getJavaFXFocusAsTableCol();
		ICFBamTableColEditObj editObj;
		if( focus != null ) {
			editObj = (ICFBamTableColEditObj)(focus.getEdit());
		}
		else {
			editObj = null;
		}
		if( editObj == null ) {
			throw new CFLibUsageException( getClass(),
				S_ProcName,
				Inz.s("cflibjavafx.common.PaneIsUnfocusedOrNotEditing"),
				Inz.x("cflibjavafx.common.PaneIsUnfocusedOrNotEditing") );
		}

		javafxParentDataTypeObj = (ICFBamValueObj)( javafxReferenceParentDataType.getReferencedObject() );
		editObj.setRequiredParentDataType( javafxParentDataTypeObj );

		javafxLookupDefSchemaObj = (ICFBamSchemaDefObj)( javafxReferenceLookupDefSchema.getReferencedObject() );
		editObj.setOptionalLookupDefSchema( javafxLookupDefSchemaObj );

		if( getJavaFXEditorName().getStringValue() == null ) {
			editObj.setRequiredName( "" );
		}
		else {
			editObj.setRequiredName( getJavaFXEditorName().getStringValue() );
		}

		if( ( getJavaFXEditorShortName().getStringValue() != null ) && ( getJavaFXEditorShortName().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalShortName( null );
		}
		else {
			editObj.setOptionalShortName( getJavaFXEditorShortName().getStringValue() );
		}

		if( ( getJavaFXEditorLabel().getStringValue() != null ) && ( getJavaFXEditorLabel().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalLabel( null );
		}
		else {
			editObj.setOptionalLabel( getJavaFXEditorLabel().getStringValue() );
		}

		if( ( getJavaFXEditorShortDescription().getStringValue() != null ) && ( getJavaFXEditorShortDescription().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalShortDescription( null );
		}
		else {
			editObj.setOptionalShortDescription( getJavaFXEditorShortDescription().getStringValue() );
		}

		if( ( getJavaFXEditorDescription().getStringValue() != null ) && ( getJavaFXEditorDescription().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalDescription( null );
		}
		else {
			editObj.setOptionalDescription( getJavaFXEditorDescription().getStringValue() );
		}

		if( ( getJavaFXEditorDefaultXmlValue().getStringValue() != null ) && ( getJavaFXEditorDefaultXmlValue().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalDefaultXmlValue( null );
		}
		else {
			editObj.setOptionalDefaultXmlValue( getJavaFXEditorDefaultXmlValue().getStringValue() );
		}

		editObj.setRequiredIsNullable( getJavaFXEditorIsNullable().getBooleanValue() );

		editObj.setOptionalGenerateId( getJavaFXEditorGenerateId().getBooleanValue() );

		editObj.setRequiredImplementsPolymorph( getJavaFXEditorImplementsPolymorph().getBooleanValue() );

		if( ( getJavaFXEditorDbName().getStringValue() != null ) && ( getJavaFXEditorDbName().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalDbName( null );
		}
		else {
			editObj.setOptionalDbName( getJavaFXEditorDbName().getStringValue() );
		}

		if( ( getJavaFXEditorXmlElementName().getStringValue() != null ) && ( getJavaFXEditorXmlElementName().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalXmlElementName( null );
		}
		else {
			editObj.setOptionalXmlElementName( getJavaFXEditorXmlElementName().getStringValue() );
		}
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFBamTableColObj focus = getJavaFXFocusAsTableCol();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFBamTableColEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFBamTableColEditObj)focus.getEdit();
		}
		else {
			editObj = null;
		}
		switch( value ) {
			case Unknown:
				switch( oldValue ) {
					case Unknown:
						break;
					default:
						if( editObj != null ) {
							editObj.endEdit();
							editObj = null;
						}
						break;
				}
				break;
			case Add:
				switch( oldValue ) {
					case Unknown:
					case Add:
					case View:
						if( editObj == null ) {
							if( focus != null ) {
								if( ! focus.getIsNew() ) {
									throw new CFLibUsageException( getClass(),
										S_ProcName,
										Inz.x("cflibjavafx.common.MustBeNew"),
										Inz.s("cflibjavafx.common.MustBeNew") );
								}
								editObj = (ICFBamTableColEditObj)focus.beginEdit();
								if( editObj == null ) {
									throw new CFLibUsageException( getClass(),
										S_ProcName,
										Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
										Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
								}
							}
							else {
								throw new CFLibNullArgumentException( getClass(),
									S_ProcName,
									0,
									"focus" );
							}
						}
						break;
					case Edit:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							Inz.x("cflibjavafx.common.CannotTransitionEditToAdd"),
							Inz.s("cflibjavafx.common.CannotTransitionEditToAdd") );
					case Update:
						if( ( editObj == null ) || ( ! editObj.getIsNew() ) ) {
							throw new CFLibUsageException( getClass(),
								S_ProcName,
								Inz.x("cflibjavafx.common.CannotTransitionUpdateToAdd"),
								Inz.s("cflibjavafx.common.CannotTransitionUpdateToAdd") );
						}
						break;
					case Delete:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							Inz.x("cflibjavafx.common.CannotTransitionDeleteToAdd"),
							Inz.s("cflibjavafx.common.CannotTransitionDeleteToAdd") );
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							Inz.x("cflibjavafx.common.CannotTransitionDefaultToAdd"),
							Inz.s("cflibjavafx.common.CannotTransitionDefaultToAdd") );
				}
				break;
			case View:
				switch( oldValue ) {
					case Unknown:
						break;
					case View:
						break;
					case Edit:
						break;
					case Update:
						break;
					case Delete:
						break;
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							String.format(Inz.x("cflibjavafx.common.CannotTransitionOldValueToView"), oldValue),
							String.format(Inz.s("cflibjavafx.common.CannotTransitionOldValueToView"), oldValue) );
				}
				if( editObj != null ) {
					editObj.endEdit();
					editObj = null;
				}
				break;
			case Edit:
				switch( oldValue ) {
					case Unknown:
						if( editObj == null ) {
							editObj = (ICFBamTableColEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
									Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
							}
						}
						break;
					case View:
						if( editObj == null ) {
							editObj = (ICFBamTableColEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
									Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
							}
						}
						break;
					case Edit:
						if( editObj == null ) {
							editObj = (ICFBamTableColEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
									Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
							}
						}
						break;
					case Update:
						if( editObj == null ) {
							throw new CFLibUsageException( getClass(),
								S_ProcName,
								String.format(Inz.x("cflibjavafx.common.CannotTransitionOldValueToEdit"), oldValue),
								String.format(Inz.s("cflibjavafx.common.CannotTransitionOldValueToEdit"), oldValue) );
						}
						break;
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							String.format(Inz.x("cflibjavafx.common.CannotTransitionOldValueToEdit"), oldValue),
							String.format(Inz.s("cflibjavafx.common.CannotTransitionOldValueToEdit"), oldValue) );
				}
				break;
			case Update:
				if( ( oldValue != CFPane.PaneMode.Edit ) && ( oldValue != CFPane.PaneMode.Add ) ) {
					throw new CFLibUsageException( getClass(),
						S_ProcName,
						String.format(Inz.x("cflibjavafx.common.CannotTransitionOldValueToUpdate"), oldValue),
						String.format(Inz.s("cflibjavafx.common.CannotTransitionOldValueToUpdate"), oldValue) );
				}
				super.setPaneMode( value );
				if( editObj != null ) {
					postFields();
					if( editObj.getIsNew() ) {
						focus = (ICFBamTableColObj)editObj.create();
						setJavaFXFocus( focus );
					}
					else {
						editObj.update();
					}
					editObj = null;
				}
				setPaneMode( CFPane.PaneMode.View );
				break;
			case Delete:
				switch( oldValue ) {
					case View:
						if( focus != null ) {
							if( editObj == null ) {
								editObj = (ICFBamTableColEditObj)focus.beginEdit();
								if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
									Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
								}
							}
						}
						break;
					case Edit:
						if( focus != null ) {
							if( editObj == null ) {
								editObj = (ICFBamTableColEditObj)focus.beginEdit();
								if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
									Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
								}
							}
						}
						break;
					case Update:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							String.format(Inz.x("cflibjavafx.common.CannotTransitionOldValueToDelete"), oldValue),
							String.format(Inz.s("cflibjavafx.common.CannotTransitionOldValueToDelete"), oldValue) );
					case Delete:
						if( editObj == null ) {
							editObj = (ICFBamTableColEditObj)focus.beginEdit();
							if( editObj == null ) {
								throw new CFLibUsageException( getClass(),
									S_ProcName,
									Inz.x("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition"),
									Inz.s("cflibjavafx.common.ExpectedBeginEditToReturnNewEdition") );
							}
						}
						break;
					default:
						throw new CFLibUsageException( getClass(),
							S_ProcName,
							String.format(Inz.x("cflibjavafx.common.CannotTransitionOldValueToDelete"), oldValue),
							String.format(Inz.s("cflibjavafx.common.CannotTransitionOldValueToDelete"), oldValue) );
				}
				editObj.deleteInstance();
				editObj = null;
				setJavaFXFocus( null );
				setPaneMode( CFPane.PaneMode.Unknown );
				break;
			default:
				switch( oldValue ) {
					case Unknown:
						break;
					default:
						if( editObj != null ) {
							editObj.endEdit();
							editObj = null;
						}
						break;
				}
				break;
		}
		super.setPaneMode( value );
		populateFields();
		adjustComponentEnableStates();
	}

	public void adjustComponentEnableStates() {
		CFPane.PaneMode mode = getPaneMode();
		boolean isEditing;
		switch( mode ) {
			case Unknown:
			case View:
			case Delete:
				isEditing = false;
				break;
			case Add:
			case Edit:
			case Update:
				isEditing = true;
				break;
			default:
				isEditing = false;
				break;
		}
		if( isEditing ) {
			ICFBamTableColObj focus = getJavaFXFocusAsTableCol();
			if( focus == null ) {
				isEditing = false;
			}
			else if( null == focus.getEdit() ) {
				isEditing = false;
			}
		}
		if( javafxReferenceParentDataType != null ) {
			javafxReferenceParentDataType.setCustomDisable( ! isEditing );
		}
		if( javafxReferenceLookupDefSchema != null ) {
			javafxReferenceLookupDefSchema.setCustomDisable( ! isEditing );
		}
		if( javafxEditorId != null ) {
			javafxEditorId.setDisable( true );
		}
		if( javafxEditorName != null ) {
			javafxEditorName.setDisable( ! isEditing );
		}
		if( javafxEditorShortName != null ) {
			javafxEditorShortName.setDisable( ! isEditing );
		}
		if( javafxEditorLabel != null ) {
			javafxEditorLabel.setDisable( ! isEditing );
		}
		if( javafxEditorShortDescription != null ) {
			javafxEditorShortDescription.setDisable( ! isEditing );
		}
		if( javafxEditorDescription != null ) {
			javafxEditorDescription.setDisable( ! isEditing );
		}
		if( javafxEditorDefaultXmlValue != null ) {
			javafxEditorDefaultXmlValue.setDisable( ! isEditing );
		}
		if( javafxEditorIsNullable != null ) {
			javafxEditorIsNullable.setDisable( ! isEditing );
		}
		if( javafxEditorGenerateId != null ) {
			javafxEditorGenerateId.setDisable( ! isEditing );
		}
		if( javafxEditorImplementsPolymorph != null ) {
			javafxEditorImplementsPolymorph.setDisable( ! isEditing );
		}
		if( javafxEditorDbName != null ) {
			javafxEditorDbName.setDisable( ! isEditing );
		}
		if( javafxEditorXmlElementName != null ) {
			javafxEditorXmlElementName.setDisable( ! isEditing );
		}
	}
}
