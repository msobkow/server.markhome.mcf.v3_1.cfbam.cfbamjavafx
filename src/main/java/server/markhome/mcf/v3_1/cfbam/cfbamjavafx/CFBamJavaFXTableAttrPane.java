// Description: Java 25 JavaFX Attribute Pane implementation for Table.

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
 *	CFBamJavaFXTableAttrPane JavaFX Attribute Pane implementation
 *	for Table.
 */
public class CFBamJavaFXTableAttrPane
extends CFGridPane
implements ICFBamJavaFXTablePaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFBamJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected ObservableList<String> observableListOfLoaderBehaviour =
		FXCollections.observableArrayList(
			"Insert",
			"Update",
			"Replace" );

	protected ObservableList<String> observableListOfSecScope =
		FXCollections.observableArrayList(
			"None",
			"System",
			"Cluster",
			"Tenant",
			"SystemGroup",
			"ClusterGroup",
			"TenantGroup" );

	protected class DefSchemaCFLabel
		extends CFLabel
	{
		public DefSchemaCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.LookupDefSchema.EffLabel"));
		}
	}

	protected class CallbackDefSchemaChosen
	implements ICFBamJavaFXSchemaDefChosen
	{
		public CallbackDefSchemaChosen() {
		}

		public void choseSchemaDef( ICFBamSchemaDefObj value ) {
			if( javafxReferenceLookupDefSchema != null ) {
				ICFBamTableObj cur = getJavaFXFocusAsTable();
				if( cur != null ) {
					ICFBamTableEditObj editObj = (ICFBamTableEditObj)cur.getEdit();
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
			ICFBamTableObj focus = getEffJavaFXFocus();
			ICFBamSchemaDefObj referencedObj = (ICFBamSchemaDefObj)javafxReferenceLookupDefSchema.getReferencedObject();
			java.util.List<ICFBamSchemaDefObj> listOfSchemaDef = null;
			Collection<ICFBamSchemaDefObj> cltn = null;
			CFBorderPane form = javafxSchema.getSchemaDefFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackDefSchemaChosen() );
			((ICFBamJavaFXSchemaDefPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamTableObj focus = getEffJavaFXFocus();
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
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.DefSchema.EffLabel" );
		}
	}

	protected class LookupIndexCFLabel
		extends CFLabel
	{
		public LookupIndexCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.LookupLookupIndex.EffLabel"));
		}
	}

	protected class CallbackLookupIndexChosen
	implements ICFBamJavaFXIndexChosen
	{
		public CallbackLookupIndexChosen() {
		}

		public void choseIndex( ICFBamIndexObj value ) {
			if( javafxReferenceLookupLookupIndex != null ) {
				ICFBamTableObj cur = getJavaFXFocusAsTable();
				if( cur != null ) {
					ICFBamTableEditObj editObj = (ICFBamTableEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupLookupIndex.setReferencedObject( value );
							editObj.setOptionalLookupLookupIndex( value );
						}
					}
				}
			}
		}
	}

	protected class LookupIndexReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
			ICFBamTableObj focus = getEffJavaFXFocus();
			ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupLookupIndex.getReferencedObject();
			java.util.List<ICFBamIndexObj> listOfIndex = null;
			Collection<ICFBamIndexObj> cltn = null;
			CFBorderPane form = javafxSchema.getIndexFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackLookupIndexChosen() );
			((ICFBamJavaFXIndexPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamTableObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupLookupIndex.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					int classCode = referencedObj.getClassCode();
					ICFSecSchema.ClassMapEntry entry = ICFBamSchema.getClassMapByRuntimeClassCode(classCode);
					int backingClassCode = entry.getBackingClassCode();
					if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamIndex.CLASS_CODE ) {
						form = javafxSchema.getIndexFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFBamJavaFXIndexPaneCommon spec = (ICFBamJavaFXIndexPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFBamIndexObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
	}

	protected class LookupIndexCFReferenceEditor
		extends CFReferenceEditor
	{
		public LookupIndexCFReferenceEditor() {
			super( new LookupIndexReferenceCallback() );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.LookupIndex.EffLabel" );
		}
	}

	protected class AltIndexCFLabel
		extends CFLabel
	{
		public AltIndexCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.LookupAltIndex.EffLabel"));
		}
	}

	protected class CallbackAltIndexChosen
	implements ICFBamJavaFXIndexChosen
	{
		public CallbackAltIndexChosen() {
		}

		public void choseIndex( ICFBamIndexObj value ) {
			if( javafxReferenceLookupAltIndex != null ) {
				ICFBamTableObj cur = getJavaFXFocusAsTable();
				if( cur != null ) {
					ICFBamTableEditObj editObj = (ICFBamTableEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupAltIndex.setReferencedObject( value );
							editObj.setOptionalLookupAltIndex( value );
						}
					}
				}
			}
		}
	}

	protected class AltIndexReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
			ICFBamTableObj focus = getEffJavaFXFocus();
			ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupAltIndex.getReferencedObject();
			java.util.List<ICFBamIndexObj> listOfIndex = null;
			Collection<ICFBamIndexObj> cltn = null;
			CFBorderPane form = javafxSchema.getIndexFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackAltIndexChosen() );
			((ICFBamJavaFXIndexPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamTableObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupAltIndex.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					int classCode = referencedObj.getClassCode();
					ICFSecSchema.ClassMapEntry entry = ICFBamSchema.getClassMapByRuntimeClassCode(classCode);
					int backingClassCode = entry.getBackingClassCode();
					if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamIndex.CLASS_CODE ) {
						form = javafxSchema.getIndexFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFBamJavaFXIndexPaneCommon spec = (ICFBamJavaFXIndexPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFBamIndexObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
	}

	protected class AltIndexCFReferenceEditor
		extends CFReferenceEditor
	{
		public AltIndexCFReferenceEditor() {
			super( new AltIndexReferenceCallback() );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.AltIndex.EffLabel" );
		}
	}

	protected class QualifyingTableCFLabel
		extends CFLabel
	{
		public QualifyingTableCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.LookupQualTable.EffLabel"));
		}
	}

	protected class CallbackQualifyingTableChosen
	implements ICFBamJavaFXTableChosen
	{
		public CallbackQualifyingTableChosen() {
		}

		public void choseTable( ICFBamTableObj value ) {
			if( javafxReferenceLookupQualTable != null ) {
				ICFBamTableObj cur = getJavaFXFocusAsTable();
				if( cur != null ) {
					ICFBamTableEditObj editObj = (ICFBamTableEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupQualTable.setReferencedObject( value );
							editObj.setOptionalLookupQualTable( value );
						}
					}
				}
			}
		}
	}

	protected class QualifyingTableReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
			ICFBamTableObj focus = getEffJavaFXFocus();
			ICFBamTableObj referencedObj = (ICFBamTableObj)javafxReferenceLookupQualTable.getReferencedObject();
			java.util.List<ICFBamTableObj> listOfTable = null;
			Collection<ICFBamTableObj> cltn = null;
			CFBorderPane form = javafxSchema.getTableFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackQualifyingTableChosen() );
			((ICFBamJavaFXTablePaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamTableObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFBamTableObj referencedObj = (ICFBamTableObj)javafxReferenceLookupQualTable.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					int classCode = referencedObj.getClassCode();
					ICFSecSchema.ClassMapEntry entry = ICFBamSchema.getClassMapByRuntimeClassCode(classCode);
					int backingClassCode = entry.getBackingClassCode();
					if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTable.CLASS_CODE ) {
						form = javafxSchema.getTableFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFBamJavaFXTablePaneCommon spec = (ICFBamJavaFXTablePaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFBamTableObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
	}

	protected class QualifyingTableCFReferenceEditor
		extends CFReferenceEditor
	{
		public QualifyingTableCFReferenceEditor() {
			super( new QualifyingTableReferenceCallback() );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.QualifyingTable.EffLabel" );
		}
	}

	protected class PrimaryIndexCFLabel
		extends CFLabel
	{
		public PrimaryIndexCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.LookupPrimaryIndex.EffLabel"));
		}
	}

	protected class CallbackPrimaryIndexChosen
	implements ICFBamJavaFXIndexChosen
	{
		public CallbackPrimaryIndexChosen() {
		}

		public void choseIndex( ICFBamIndexObj value ) {
			if( javafxReferenceLookupPrimaryIndex != null ) {
				ICFBamTableObj cur = getJavaFXFocusAsTable();
				if( cur != null ) {
					ICFBamTableEditObj editObj = (ICFBamTableEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupPrimaryIndex.setReferencedObject( value );
							editObj.setOptionalLookupPrimaryIndex( value );
						}
					}
				}
			}
		}
	}

	protected class PrimaryIndexReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
			ICFBamTableObj focus = getEffJavaFXFocus();
			ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupPrimaryIndex.getReferencedObject();
			java.util.List<ICFBamIndexObj> listOfIndex = null;
			listOfIndex = focus.getOptionalComponentsIndex( javafxIsInitializing );
			if( listOfIndex == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"listOfIndex" );
			}
			Collection<ICFBamIndexObj> cltn = listOfIndex;
			CFBorderPane form = javafxSchema.getIndexFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackPrimaryIndexChosen() );
			((ICFBamJavaFXIndexPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamTableObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupPrimaryIndex.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					int classCode = referencedObj.getClassCode();
					ICFSecSchema.ClassMapEntry entry = ICFBamSchema.getClassMapByRuntimeClassCode(classCode);
					int backingClassCode = entry.getBackingClassCode();
					if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamIndex.CLASS_CODE ) {
						form = javafxSchema.getIndexFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFBamJavaFXIndexPaneCommon spec = (ICFBamJavaFXIndexPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFBamIndexObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
	}

	protected class PrimaryIndexCFReferenceEditor
		extends CFReferenceEditor
	{
		public PrimaryIndexCFReferenceEditor() {
			super( new PrimaryIndexReferenceCallback() );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.PrimaryIndex.EffLabel" );
		}
	}

	protected class IdCFLabel
		extends CFLabel
	{
		public IdCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Scope.AttrPane.Id.EffLabel"));
		}
	}

	protected class IdEditor
		extends CFDbKeyHash256Editor
	{
		public IdEditor() {
			super();
			setFieldNameInzTag( "cfbam.javafx.Scope.AttrPane.Id.EffLabel" );
		}
	}

	protected class NameCFLabel
		extends CFLabel
	{
		public NameCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.Name.EffLabel"));
		}
	}

	protected class NameEditor
		extends CFStringEditor
	{
		public NameEditor() {
			super();
			setMaxLen( 192 );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.Name.EffLabel" );
		}
	}

	protected class DbNameCFLabel
		extends CFLabel
	{
		public DbNameCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.DbName.EffLabel"));
		}
	}

	protected class DbNameEditor
		extends CFStringEditor
	{
		public DbNameEditor() {
			super();
			setMaxLen( 32 );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.DbName.EffLabel" );
		}
	}

	protected class ShortNameCFLabel
		extends CFLabel
	{
		public ShortNameCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.ShortName.EffLabel"));
		}
	}

	protected class ShortNameEditor
		extends CFStringEditor
	{
		public ShortNameEditor() {
			super();
			setMaxLen( 16 );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.ShortName.EffLabel" );
		}
	}

	protected class LabelCFLabel
		extends CFLabel
	{
		public LabelCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.Label.EffLabel"));
		}
	}

	protected class LabelEditor
		extends CFStringEditor
	{
		public LabelEditor() {
			super();
			setMaxLen( 64 );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.Label.EffLabel" );
		}
	}

	protected class ShortDescriptionCFLabel
		extends CFLabel
	{
		public ShortDescriptionCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.ShortDescription.EffLabel"));
		}
	}

	protected class ShortDescriptionEditor
		extends CFStringEditor
	{
		public ShortDescriptionEditor() {
			super();
			setMaxLen( 50 );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.ShortDescription.EffLabel" );
		}
	}

	protected class DescriptionCFLabel
		extends CFLabel
	{
		public DescriptionCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.Description.EffLabel"));
		}
	}

	protected class DescriptionEditor
		extends CFStringEditor
	{
		public DescriptionEditor() {
			super();
			setMaxLen( 100 );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.Description.EffLabel" );
		}
	}

	protected class PageDataCFLabel
		extends CFLabel
	{
		public PageDataCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.PageData.EffLabel"));
		}
	}

	protected class PageDataEditor
		extends CFBoolEditor
	{
		public PageDataEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.PageData.EffLabel" );
		}
	}

	protected class TableClassCodeCFLabel
		extends CFLabel
	{
		public TableClassCodeCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.TableClassCode.EffLabel"));
		}
	}

	protected class TableClassCodeEditor
		extends CFStringEditor
	{
		public TableClassCodeEditor() {
			super();
			setMaxLen( 4 );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.TableClassCode.EffLabel" );
		}
	}

	protected class IsInstantiableCFLabel
		extends CFLabel
	{
		public IsInstantiableCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.IsInstantiable.EffLabel"));
		}
	}

	protected class IsInstantiableEditor
		extends CFBoolEditor
	{
		public IsInstantiableEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.IsInstantiable.EffLabel" );
		}
	}

	protected class HasHistoryCFLabel
		extends CFLabel
	{
		public HasHistoryCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.HasHistory.EffLabel"));
		}
	}

	protected class HasHistoryEditor
		extends CFBoolEditor
	{
		public HasHistoryEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.HasHistory.EffLabel" );
		}
	}

	protected class HasAuditColumnsCFLabel
		extends CFLabel
	{
		public HasAuditColumnsCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.HasAuditColumns.EffLabel"));
		}
	}

	protected class HasAuditColumnsEditor
		extends CFBoolEditor
	{
		public HasAuditColumnsEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.HasAuditColumns.EffLabel" );
		}
	}

	protected class IsMutableCFLabel
		extends CFLabel
	{
		public IsMutableCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.IsMutable.EffLabel"));
		}
	}

	protected class IsMutableEditor
		extends CFBoolEditor
	{
		public IsMutableEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.IsMutable.EffLabel" );
		}
	}

	protected class IsServerOnlyCFLabel
		extends CFLabel
	{
		public IsServerOnlyCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.IsServerOnly.EffLabel"));
		}
	}

	protected class IsServerOnlyEditor
		extends CFBoolEditor
	{
		public IsServerOnlyEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfbam.javafx.Table.AttrPane.IsServerOnly.EffLabel" );
		}
	}

	protected class LoaderBehaviourCFLabel
		extends CFLabel
	{
		public LoaderBehaviourCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.LoaderBehaviour.EffLabel"));
		}
	}

	protected class LoaderBehaviourEditor
		extends ComboBox<String>
	{
		public LoaderBehaviourEditor() {
			super();
			setItems( observableListOfLoaderBehaviour );
		}
	}

	protected class SecScopeCFLabel
		extends CFLabel
	{
		public SecScopeCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Table.AttrPane.SecScope.EffLabel"));
		}
	}

	protected class SecScopeEditor
		extends ComboBox<String>
	{
		public SecScopeEditor() {
			super();
			setItems( observableListOfSecScope );
		}
	}

	protected ICFBamSchemaDefObj javafxLookupDefSchemaObj = null;
	protected DefSchemaCFLabel javafxLabelLookupDefSchema = null;
	protected DefSchemaCFReferenceEditor javafxReferenceLookupDefSchema = null;
	protected ICFBamIndexObj javafxLookupLookupIndexObj = null;
	protected LookupIndexCFLabel javafxLabelLookupLookupIndex = null;
	protected LookupIndexCFReferenceEditor javafxReferenceLookupLookupIndex = null;
	protected ICFBamIndexObj javafxLookupAltIndexObj = null;
	protected AltIndexCFLabel javafxLabelLookupAltIndex = null;
	protected AltIndexCFReferenceEditor javafxReferenceLookupAltIndex = null;
	protected ICFBamTableObj javafxLookupQualTableObj = null;
	protected QualifyingTableCFLabel javafxLabelLookupQualTable = null;
	protected QualifyingTableCFReferenceEditor javafxReferenceLookupQualTable = null;
	protected ICFBamIndexObj javafxLookupPrimaryIndexObj = null;
	protected PrimaryIndexCFLabel javafxLabelLookupPrimaryIndex = null;
	protected PrimaryIndexCFReferenceEditor javafxReferenceLookupPrimaryIndex = null;
	protected IdCFLabel javafxLabelId = null;
	protected IdEditor javafxEditorId = null;
	protected NameCFLabel javafxLabelName = null;
	protected NameEditor javafxEditorName = null;
	protected DbNameCFLabel javafxLabelDbName = null;
	protected DbNameEditor javafxEditorDbName = null;
	protected ShortNameCFLabel javafxLabelShortName = null;
	protected ShortNameEditor javafxEditorShortName = null;
	protected LabelCFLabel javafxLabelLabel = null;
	protected LabelEditor javafxEditorLabel = null;
	protected ShortDescriptionCFLabel javafxLabelShortDescription = null;
	protected ShortDescriptionEditor javafxEditorShortDescription = null;
	protected DescriptionCFLabel javafxLabelDescription = null;
	protected DescriptionEditor javafxEditorDescription = null;
	protected PageDataCFLabel javafxLabelPageData = null;
	protected PageDataEditor javafxEditorPageData = null;
	protected TableClassCodeCFLabel javafxLabelTableClassCode = null;
	protected TableClassCodeEditor javafxEditorTableClassCode = null;
	protected IsInstantiableCFLabel javafxLabelIsInstantiable = null;
	protected IsInstantiableEditor javafxEditorIsInstantiable = null;
	protected HasHistoryCFLabel javafxLabelHasHistory = null;
	protected HasHistoryEditor javafxEditorHasHistory = null;
	protected HasAuditColumnsCFLabel javafxLabelHasAuditColumns = null;
	protected HasAuditColumnsEditor javafxEditorHasAuditColumns = null;
	protected IsMutableCFLabel javafxLabelIsMutable = null;
	protected IsMutableEditor javafxEditorIsMutable = null;
	protected IsServerOnlyCFLabel javafxLabelIsServerOnly = null;
	protected IsServerOnlyEditor javafxEditorIsServerOnly = null;
	protected LoaderBehaviourCFLabel javafxLabelLoaderBehaviour = null;
	protected LoaderBehaviourEditor javafxEditorLoaderBehaviour = null;
	protected SecScopeCFLabel javafxLabelSecScope = null;
	protected SecScopeEditor javafxEditorSecScope = null;

	public CFBamJavaFXTableAttrPane( ICFFormManager formManager, ICFBamJavaFXSchema argSchema, ICFBamTableObj argFocus ) {
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
		setJavaFXFocusAsTable( argFocus );
		setPadding( new Insets(5) );
		setHgap( 5 );
		setVgap( 5 );
		setAlignment( Pos.CENTER );
		ColumnConstraints column1 = new ColumnConstraints();
		column1.setPercentWidth( 100 );
		getColumnConstraints().addAll( column1 );
		int gridRow = 0;
		label = getJavaFXLabelLookupDefSchema();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceLookupDefSchema();
		setHalignment( reference, HPos.LEFT );
		add( reference, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelLookupLookupIndex();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceLookupLookupIndex();
		setHalignment( reference, HPos.LEFT );
		add( reference, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelLookupAltIndex();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceLookupAltIndex();
		setHalignment( reference, HPos.LEFT );
		add( reference, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelLookupQualTable();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceLookupQualTable();
		setHalignment( reference, HPos.LEFT );
		add( reference, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelLookupPrimaryIndex();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceLookupPrimaryIndex();
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

		label = getJavaFXLabelDbName();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorDbName();
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

		label = getJavaFXLabelPageData();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorPageData();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelTableClassCode();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorTableClassCode();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelIsInstantiable();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorIsInstantiable();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelHasHistory();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorHasHistory();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelHasAuditColumns();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorHasAuditColumns();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelIsMutable();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorIsMutable();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelIsServerOnly();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorIsServerOnly();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelLoaderBehaviour();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorLoaderBehaviour();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelSecScope();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorSecScope();
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
		if( ( value == null ) || ( value instanceof ICFBamTableObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamTableObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFBamTableObj getJavaFXFocusAsTable() {
		return( (ICFBamTableObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsTable( ICFBamTableObj value ) {
		setJavaFXFocus( value );
	}

	public ICFBamTableObj getEffJavaFXFocus() {
		ICFBamTableObj eff = (ICFBamTableObj)getJavaFXFocus();
		if( eff != null ) {
			if( null != eff.getEdit() ) {
				eff = (ICFBamTableObj)eff.getEdit();
			}
		}
		return( eff );
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

	public ICFBamIndexObj getJavaFXLookupLookupIndexObj() {
		return( javafxLookupLookupIndexObj );
	}

	public void setJavaFXLookupLookupIndexObj( ICFBamIndexObj value ) {
		javafxLookupLookupIndexObj = value;
	}

	public CFLabel getJavaFXLabelLookupLookupIndex() {
		if( javafxLabelLookupLookupIndex == null ) {
			javafxLabelLookupLookupIndex = new LookupIndexCFLabel();
		}
		return( javafxLabelLookupLookupIndex );
	}

	public CFReferenceEditor getJavaFXReferenceLookupLookupIndex() {
		if( javafxReferenceLookupLookupIndex == null ) {
			javafxReferenceLookupLookupIndex = new LookupIndexCFReferenceEditor();
		}
		return( javafxReferenceLookupLookupIndex );
	}

	public void setJavaFXReferenceLookupLookupIndex( LookupIndexCFReferenceEditor value ) {
		javafxReferenceLookupLookupIndex = value;
	}

	public ICFBamIndexObj getJavaFXLookupAltIndexObj() {
		return( javafxLookupAltIndexObj );
	}

	public void setJavaFXLookupAltIndexObj( ICFBamIndexObj value ) {
		javafxLookupAltIndexObj = value;
	}

	public CFLabel getJavaFXLabelLookupAltIndex() {
		if( javafxLabelLookupAltIndex == null ) {
			javafxLabelLookupAltIndex = new AltIndexCFLabel();
		}
		return( javafxLabelLookupAltIndex );
	}

	public CFReferenceEditor getJavaFXReferenceLookupAltIndex() {
		if( javafxReferenceLookupAltIndex == null ) {
			javafxReferenceLookupAltIndex = new AltIndexCFReferenceEditor();
		}
		return( javafxReferenceLookupAltIndex );
	}

	public void setJavaFXReferenceLookupAltIndex( AltIndexCFReferenceEditor value ) {
		javafxReferenceLookupAltIndex = value;
	}

	public ICFBamTableObj getJavaFXLookupQualTableObj() {
		return( javafxLookupQualTableObj );
	}

	public void setJavaFXLookupQualTableObj( ICFBamTableObj value ) {
		javafxLookupQualTableObj = value;
	}

	public CFLabel getJavaFXLabelLookupQualTable() {
		if( javafxLabelLookupQualTable == null ) {
			javafxLabelLookupQualTable = new QualifyingTableCFLabel();
		}
		return( javafxLabelLookupQualTable );
	}

	public CFReferenceEditor getJavaFXReferenceLookupQualTable() {
		if( javafxReferenceLookupQualTable == null ) {
			javafxReferenceLookupQualTable = new QualifyingTableCFReferenceEditor();
		}
		return( javafxReferenceLookupQualTable );
	}

	public void setJavaFXReferenceLookupQualTable( QualifyingTableCFReferenceEditor value ) {
		javafxReferenceLookupQualTable = value;
	}

	public ICFBamIndexObj getJavaFXLookupPrimaryIndexObj() {
		return( javafxLookupPrimaryIndexObj );
	}

	public void setJavaFXLookupPrimaryIndexObj( ICFBamIndexObj value ) {
		javafxLookupPrimaryIndexObj = value;
	}

	public CFLabel getJavaFXLabelLookupPrimaryIndex() {
		if( javafxLabelLookupPrimaryIndex == null ) {
			javafxLabelLookupPrimaryIndex = new PrimaryIndexCFLabel();
		}
		return( javafxLabelLookupPrimaryIndex );
	}

	public CFReferenceEditor getJavaFXReferenceLookupPrimaryIndex() {
		if( javafxReferenceLookupPrimaryIndex == null ) {
			javafxReferenceLookupPrimaryIndex = new PrimaryIndexCFReferenceEditor();
		}
		return( javafxReferenceLookupPrimaryIndex );
	}

	public void setJavaFXReferenceLookupPrimaryIndex( PrimaryIndexCFReferenceEditor value ) {
		javafxReferenceLookupPrimaryIndex = value;
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

	public PageDataCFLabel getJavaFXLabelPageData() {
		if( javafxLabelPageData == null ) {
			javafxLabelPageData = new PageDataCFLabel();
		}
		return( javafxLabelPageData );
	}

	public void setJavaFXLabelPageData( PageDataCFLabel value ) {
		javafxLabelPageData = value;
	}

	public PageDataEditor getJavaFXEditorPageData() {
		if( javafxEditorPageData == null ) {
			javafxEditorPageData = new PageDataEditor();
		}
		return( javafxEditorPageData );
	}

	public void setJavaFXEditorPageData( PageDataEditor value ) {
		javafxEditorPageData = value;
	}

	public TableClassCodeCFLabel getJavaFXLabelTableClassCode() {
		if( javafxLabelTableClassCode == null ) {
			javafxLabelTableClassCode = new TableClassCodeCFLabel();
		}
		return( javafxLabelTableClassCode );
	}

	public void setJavaFXLabelTableClassCode( TableClassCodeCFLabel value ) {
		javafxLabelTableClassCode = value;
	}

	public TableClassCodeEditor getJavaFXEditorTableClassCode() {
		if( javafxEditorTableClassCode == null ) {
			javafxEditorTableClassCode = new TableClassCodeEditor();
		}
		return( javafxEditorTableClassCode );
	}

	public void setJavaFXEditorTableClassCode( TableClassCodeEditor value ) {
		javafxEditorTableClassCode = value;
	}

	public IsInstantiableCFLabel getJavaFXLabelIsInstantiable() {
		if( javafxLabelIsInstantiable == null ) {
			javafxLabelIsInstantiable = new IsInstantiableCFLabel();
		}
		return( javafxLabelIsInstantiable );
	}

	public void setJavaFXLabelIsInstantiable( IsInstantiableCFLabel value ) {
		javafxLabelIsInstantiable = value;
	}

	public IsInstantiableEditor getJavaFXEditorIsInstantiable() {
		if( javafxEditorIsInstantiable == null ) {
			javafxEditorIsInstantiable = new IsInstantiableEditor();
		}
		return( javafxEditorIsInstantiable );
	}

	public void setJavaFXEditorIsInstantiable( IsInstantiableEditor value ) {
		javafxEditorIsInstantiable = value;
	}

	public HasHistoryCFLabel getJavaFXLabelHasHistory() {
		if( javafxLabelHasHistory == null ) {
			javafxLabelHasHistory = new HasHistoryCFLabel();
		}
		return( javafxLabelHasHistory );
	}

	public void setJavaFXLabelHasHistory( HasHistoryCFLabel value ) {
		javafxLabelHasHistory = value;
	}

	public HasHistoryEditor getJavaFXEditorHasHistory() {
		if( javafxEditorHasHistory == null ) {
			javafxEditorHasHistory = new HasHistoryEditor();
		}
		return( javafxEditorHasHistory );
	}

	public void setJavaFXEditorHasHistory( HasHistoryEditor value ) {
		javafxEditorHasHistory = value;
	}

	public HasAuditColumnsCFLabel getJavaFXLabelHasAuditColumns() {
		if( javafxLabelHasAuditColumns == null ) {
			javafxLabelHasAuditColumns = new HasAuditColumnsCFLabel();
		}
		return( javafxLabelHasAuditColumns );
	}

	public void setJavaFXLabelHasAuditColumns( HasAuditColumnsCFLabel value ) {
		javafxLabelHasAuditColumns = value;
	}

	public HasAuditColumnsEditor getJavaFXEditorHasAuditColumns() {
		if( javafxEditorHasAuditColumns == null ) {
			javafxEditorHasAuditColumns = new HasAuditColumnsEditor();
		}
		return( javafxEditorHasAuditColumns );
	}

	public void setJavaFXEditorHasAuditColumns( HasAuditColumnsEditor value ) {
		javafxEditorHasAuditColumns = value;
	}

	public IsMutableCFLabel getJavaFXLabelIsMutable() {
		if( javafxLabelIsMutable == null ) {
			javafxLabelIsMutable = new IsMutableCFLabel();
		}
		return( javafxLabelIsMutable );
	}

	public void setJavaFXLabelIsMutable( IsMutableCFLabel value ) {
		javafxLabelIsMutable = value;
	}

	public IsMutableEditor getJavaFXEditorIsMutable() {
		if( javafxEditorIsMutable == null ) {
			javafxEditorIsMutable = new IsMutableEditor();
		}
		return( javafxEditorIsMutable );
	}

	public void setJavaFXEditorIsMutable( IsMutableEditor value ) {
		javafxEditorIsMutable = value;
	}

	public IsServerOnlyCFLabel getJavaFXLabelIsServerOnly() {
		if( javafxLabelIsServerOnly == null ) {
			javafxLabelIsServerOnly = new IsServerOnlyCFLabel();
		}
		return( javafxLabelIsServerOnly );
	}

	public void setJavaFXLabelIsServerOnly( IsServerOnlyCFLabel value ) {
		javafxLabelIsServerOnly = value;
	}

	public IsServerOnlyEditor getJavaFXEditorIsServerOnly() {
		if( javafxEditorIsServerOnly == null ) {
			javafxEditorIsServerOnly = new IsServerOnlyEditor();
		}
		return( javafxEditorIsServerOnly );
	}

	public void setJavaFXEditorIsServerOnly( IsServerOnlyEditor value ) {
		javafxEditorIsServerOnly = value;
	}

	public LoaderBehaviourCFLabel getJavaFXLabelLoaderBehaviour() {
		if( javafxLabelLoaderBehaviour == null ) {
			javafxLabelLoaderBehaviour = new LoaderBehaviourCFLabel();
		}
		return( javafxLabelLoaderBehaviour );
	}

	public void setJavaFXLabelLoaderBehaviour( LoaderBehaviourCFLabel value ) {
		javafxLabelLoaderBehaviour = value;
	}

	public LoaderBehaviourEditor getJavaFXEditorLoaderBehaviour() {
		if( javafxEditorLoaderBehaviour == null ) {
			javafxEditorLoaderBehaviour = new LoaderBehaviourEditor();
		}
		return( javafxEditorLoaderBehaviour );
	}

	public void setJavaFXEditorLoaderBehaviour( LoaderBehaviourEditor value ) {
		javafxEditorLoaderBehaviour = value;
	}

	public SecScopeCFLabel getJavaFXLabelSecScope() {
		if( javafxLabelSecScope == null ) {
			javafxLabelSecScope = new SecScopeCFLabel();
		}
		return( javafxLabelSecScope );
	}

	public void setJavaFXLabelSecScope( SecScopeCFLabel value ) {
		javafxLabelSecScope = value;
	}

	public SecScopeEditor getJavaFXEditorSecScope() {
		if( javafxEditorSecScope == null ) {
			javafxEditorSecScope = new SecScopeEditor();
		}
		return( javafxEditorSecScope );
	}

	public void setJavaFXEditorSecScope( SecScopeEditor value ) {
		javafxEditorSecScope = value;
	}

	public void populateFields()
	{
		ICFBamTableObj popObj = getEffJavaFXFocus();
		if( getPaneMode() == CFPane.PaneMode.Unknown ) {
			popObj = null;
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
			javafxLookupLookupIndexObj = null;
		}
		else {
			javafxLookupLookupIndexObj = (ICFBamIndexObj)popObj.getOptionalLookupLookupIndex( javafxIsInitializing );
		}
		if( javafxReferenceLookupLookupIndex != null ) {
			javafxReferenceLookupLookupIndex.setReferencedObject( javafxLookupLookupIndexObj );
		}

		if( popObj == null ) {
			javafxLookupAltIndexObj = null;
		}
		else {
			javafxLookupAltIndexObj = (ICFBamIndexObj)popObj.getOptionalLookupAltIndex( javafxIsInitializing );
		}
		if( javafxReferenceLookupAltIndex != null ) {
			javafxReferenceLookupAltIndex.setReferencedObject( javafxLookupAltIndexObj );
		}

		if( popObj == null ) {
			javafxLookupQualTableObj = null;
		}
		else {
			javafxLookupQualTableObj = (ICFBamTableObj)popObj.getOptionalLookupQualTable( javafxIsInitializing );
		}
		if( javafxReferenceLookupQualTable != null ) {
			javafxReferenceLookupQualTable.setReferencedObject( javafxLookupQualTableObj );
		}

		if( popObj == null ) {
			javafxLookupPrimaryIndexObj = null;
		}
		else {
			javafxLookupPrimaryIndexObj = (ICFBamIndexObj)popObj.getOptionalLookupPrimaryIndex( javafxIsInitializing );
		}
		if( javafxReferenceLookupPrimaryIndex != null ) {
			javafxReferenceLookupPrimaryIndex.setReferencedObject( javafxLookupPrimaryIndexObj );
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
			getJavaFXEditorDbName().setStringValue( null );
		}
		else {
			getJavaFXEditorDbName().setStringValue( popObj.getOptionalDbName() );
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
			getJavaFXEditorPageData().setBooleanValue( null );
		}
		else {
			getJavaFXEditorPageData().setBooleanValue( popObj.getRequiredPageData() );
		}

		if( popObj == null ) {
			getJavaFXEditorTableClassCode().setStringValue( null );
		}
		else {
			getJavaFXEditorTableClassCode().setStringValue( popObj.getRequiredTableClassCode() );
		}

		if( popObj == null ) {
			getJavaFXEditorIsInstantiable().setBooleanValue( null );
		}
		else {
			getJavaFXEditorIsInstantiable().setBooleanValue( popObj.getRequiredIsInstantiable() );
		}

		if( popObj == null ) {
			getJavaFXEditorHasHistory().setBooleanValue( null );
		}
		else {
			getJavaFXEditorHasHistory().setBooleanValue( popObj.getRequiredHasHistory() );
		}

		if( popObj == null ) {
			getJavaFXEditorHasAuditColumns().setBooleanValue( null );
		}
		else {
			getJavaFXEditorHasAuditColumns().setBooleanValue( popObj.getRequiredHasAuditColumns() );
		}

		if( popObj == null ) {
			getJavaFXEditorIsMutable().setBooleanValue( null );
		}
		else {
			getJavaFXEditorIsMutable().setBooleanValue( popObj.getRequiredIsMutable() );
		}

		if( popObj == null ) {
			getJavaFXEditorIsServerOnly().setBooleanValue( null );
		}
		else {
			getJavaFXEditorIsServerOnly().setBooleanValue( popObj.getRequiredIsServerOnly() );
		}

		if( popObj == null ) {
			getJavaFXEditorLoaderBehaviour().setValue( null );
		}
		else {
			getJavaFXEditorLoaderBehaviour().setValue(
				( popObj.getRequiredLoaderBehaviour() == null )
					? null
					: popObj.getRequiredLoaderBehaviour().toString() );
		}

		if( popObj == null ) {
			getJavaFXEditorSecScope().setValue( null );
		}
		else {
			getJavaFXEditorSecScope().setValue(
				( popObj.getRequiredSecScope() == null )
					? null
					: popObj.getRequiredSecScope().toString() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFBamTableObj focus = getJavaFXFocusAsTable();
		ICFBamTableEditObj editObj;
		if( focus != null ) {
			editObj = (ICFBamTableEditObj)(focus.getEdit());
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

		javafxLookupDefSchemaObj = (ICFBamSchemaDefObj)( javafxReferenceLookupDefSchema.getReferencedObject() );
		editObj.setOptionalLookupDefSchema( javafxLookupDefSchemaObj );

		javafxLookupLookupIndexObj = (ICFBamIndexObj)( javafxReferenceLookupLookupIndex.getReferencedObject() );
		editObj.setOptionalLookupLookupIndex( javafxLookupLookupIndexObj );

		javafxLookupAltIndexObj = (ICFBamIndexObj)( javafxReferenceLookupAltIndex.getReferencedObject() );
		editObj.setOptionalLookupAltIndex( javafxLookupAltIndexObj );

		javafxLookupQualTableObj = (ICFBamTableObj)( javafxReferenceLookupQualTable.getReferencedObject() );
		editObj.setOptionalLookupQualTable( javafxLookupQualTableObj );

		javafxLookupPrimaryIndexObj = (ICFBamIndexObj)( javafxReferenceLookupPrimaryIndex.getReferencedObject() );
		editObj.setOptionalLookupPrimaryIndex( javafxLookupPrimaryIndexObj );

		if( getJavaFXEditorName().getStringValue() == null ) {
			editObj.setRequiredName( "" );
		}
		else {
			editObj.setRequiredName( getJavaFXEditorName().getStringValue() );
		}

		if( ( getJavaFXEditorDbName().getStringValue() != null ) && ( getJavaFXEditorDbName().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalDbName( null );
		}
		else {
			editObj.setOptionalDbName( getJavaFXEditorDbName().getStringValue() );
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

		editObj.setRequiredPageData( getJavaFXEditorPageData().getBooleanValue() );

		if( getJavaFXEditorTableClassCode().getStringValue() == null ) {
			editObj.setRequiredTableClassCode( "" );
		}
		else {
			editObj.setRequiredTableClassCode( getJavaFXEditorTableClassCode().getStringValue() );
		}

		editObj.setRequiredIsInstantiable( getJavaFXEditorIsInstantiable().getBooleanValue() );

		editObj.setRequiredHasHistory( getJavaFXEditorHasHistory().getBooleanValue() );

		editObj.setRequiredHasAuditColumns( getJavaFXEditorHasAuditColumns().getBooleanValue() );

		editObj.setRequiredIsMutable( getJavaFXEditorIsMutable().getBooleanValue() );

		editObj.setRequiredIsServerOnly( getJavaFXEditorIsServerOnly().getBooleanValue() );

		String strloaderbehaviour = getJavaFXEditorLoaderBehaviour().getValue();
		ICFBamSchema.LoaderBehaviourEnum curloaderbehaviour = ICFBamSchema.parseLoaderBehaviourEnum( "LoaderBehaviour", strloaderbehaviour );
		editObj.setRequiredLoaderBehaviour( curloaderbehaviour );

		String strsecscope = getJavaFXEditorSecScope().getValue();
		ICFBamSchema.SecScopeEnum cursecscope = ICFBamSchema.parseSecScopeEnum( "SecScope", strsecscope );
		editObj.setRequiredSecScope( cursecscope );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFBamTableObj focus = getJavaFXFocusAsTable();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFBamTableEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFBamTableEditObj)focus.getEdit();
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
								editObj = (ICFBamTableEditObj)focus.beginEdit();
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
							editObj = (ICFBamTableEditObj)focus.beginEdit();
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
							editObj = (ICFBamTableEditObj)focus.beginEdit();
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
							editObj = (ICFBamTableEditObj)focus.beginEdit();
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
						focus = (ICFBamTableObj)editObj.create();
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
								editObj = (ICFBamTableEditObj)focus.beginEdit();
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
								editObj = (ICFBamTableEditObj)focus.beginEdit();
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
							editObj = (ICFBamTableEditObj)focus.beginEdit();
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
			ICFBamTableObj focus = getJavaFXFocusAsTable();
			if( focus == null ) {
				isEditing = false;
			}
			else if( null == focus.getEdit() ) {
				isEditing = false;
			}
		}
		if( javafxReferenceLookupDefSchema != null ) {
			javafxReferenceLookupDefSchema.setCustomDisable( ! isEditing );
		}
		if( javafxReferenceLookupLookupIndex != null ) {
			javafxReferenceLookupLookupIndex.setCustomDisable( ! isEditing );
		}
		if( javafxReferenceLookupAltIndex != null ) {
			javafxReferenceLookupAltIndex.setCustomDisable( ! isEditing );
		}
		if( javafxReferenceLookupQualTable != null ) {
			javafxReferenceLookupQualTable.setCustomDisable( ! isEditing );
		}
		if( javafxReferenceLookupPrimaryIndex != null ) {
			javafxReferenceLookupPrimaryIndex.setCustomDisable( ! isEditing );
		}
		if( javafxEditorId != null ) {
			javafxEditorId.setDisable( true );
		}
		if( javafxEditorName != null ) {
			javafxEditorName.setDisable( ! isEditing );
		}
		if( javafxEditorDbName != null ) {
			javafxEditorDbName.setDisable( ! isEditing );
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
		if( javafxEditorPageData != null ) {
			javafxEditorPageData.setDisable( ! isEditing );
		}
		if( javafxEditorTableClassCode != null ) {
			javafxEditorTableClassCode.setDisable( ! isEditing );
		}
		if( javafxEditorIsInstantiable != null ) {
			javafxEditorIsInstantiable.setDisable( ! isEditing );
		}
		if( javafxEditorHasHistory != null ) {
			javafxEditorHasHistory.setDisable( ! isEditing );
		}
		if( javafxEditorHasAuditColumns != null ) {
			javafxEditorHasAuditColumns.setDisable( ! isEditing );
		}
		if( javafxEditorIsMutable != null ) {
			javafxEditorIsMutable.setDisable( ! isEditing );
		}
		if( javafxEditorIsServerOnly != null ) {
			javafxEditorIsServerOnly.setDisable( ! isEditing );
		}
		if( javafxEditorLoaderBehaviour != null ) {
			javafxEditorLoaderBehaviour.setDisable( ! isEditing );
		}
		if( javafxEditorSecScope != null ) {
			javafxEditorSecScope.setDisable( ! isEditing );
		}
	}
}
