// Description: Java 25 JavaFX Attribute Pane implementation for Relation.

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
 *	CFBamJavaFXRelationAttrPane JavaFX Attribute Pane implementation
 *	for Relation.
 */
public class CFBamJavaFXRelationAttrPane
extends CFGridPane
implements ICFBamJavaFXRelationPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFBamJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected ObservableList<String> observableListOfRelationType =
		FXCollections.observableArrayList(
			"Unknown",
			"Lookup",
			"Superclass",
			"Container",
			"Components",
			"Owner",
			"Parent",
			"Children" );

	protected class DefSchemaCFLabel
		extends CFLabel
	{
		public DefSchemaCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.LookupDefSchema.EffLabel"));
		}
	}

	protected class CallbackDefSchemaChosen
	implements ICFBamJavaFXSchemaDefChosen
	{
		public CallbackDefSchemaChosen() {
		}

		public void choseSchemaDef( ICFBamSchemaDefObj value ) {
			if( javafxReferenceLookupDefSchema != null ) {
				ICFBamRelationObj cur = getJavaFXFocusAsRelation();
				if( cur != null ) {
					ICFBamRelationEditObj editObj = (ICFBamRelationEditObj)cur.getEdit();
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
			ICFBamRelationObj focus = getEffJavaFXFocus();
			ICFBamSchemaDefObj referencedObj = (ICFBamSchemaDefObj)javafxReferenceLookupDefSchema.getReferencedObject();
			java.util.List<ICFBamSchemaDefObj> listOfSchemaDef = null;
			Collection<ICFBamSchemaDefObj> cltn = null;
			CFBorderPane form = javafxSchema.getSchemaDefFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackDefSchemaChosen() );
			((ICFBamJavaFXSchemaDefPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamRelationObj focus = getEffJavaFXFocus();
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
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.DefSchema.EffLabel" );
		}
	}

	protected class FromIndexCFLabel
		extends CFLabel
	{
		public FromIndexCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.LookupFromIndex.EffLabel"));
		}
	}

	protected class CallbackFromIndexChosen
	implements ICFBamJavaFXIndexChosen
	{
		public CallbackFromIndexChosen() {
		}

		public void choseIndex( ICFBamIndexObj value ) {
			if( javafxReferenceLookupFromIndex != null ) {
				ICFBamRelationObj cur = getJavaFXFocusAsRelation();
				if( cur != null ) {
					ICFBamRelationEditObj editObj = (ICFBamRelationEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupFromIndex.setReferencedObject( value );
							editObj.setRequiredLookupFromIndex( value );
						}
					}
				}
			}
		}
	}

	protected class FromIndexReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
			ICFBamRelationObj focus = getEffJavaFXFocus();
			ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupFromIndex.getReferencedObject();
			java.util.List<ICFBamIndexObj> listOfIndex = null;
			ICFBamTableObj refFromTable = (ICFBamTableObj)focus.getRequiredContainerFromTable( javafxIsInitializing );
			if( refFromTable == null ) {
				CFConsole.message( String.format(Inz.s("cflibjavafx.common.SpecifyRelationBeforeSelectingTarget"), Inz.s("cfbam.javafx.Relation.AttrPane.ContainerFromTable.EffLabel"), Inz.s("cfbam.javafx.Relation.AttrPane.LookupFromIndex.EffLabel")));
				return;
			}
			listOfIndex = refFromTable.getOptionalComponentsIndex( javafxIsInitializing );
			if( listOfIndex == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"listOfIndex" );
			}
			Collection<ICFBamIndexObj> cltn = listOfIndex;
			CFBorderPane form = javafxSchema.getIndexFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackFromIndexChosen() );
			((ICFBamJavaFXIndexPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamRelationObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupFromIndex.getReferencedObject();
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

	protected class FromIndexCFReferenceEditor
		extends CFReferenceEditor
	{
		public FromIndexCFReferenceEditor() {
			super( new FromIndexReferenceCallback() );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.FromIndex.EffLabel" );
		}
	}

	protected class ToTableCFLabel
		extends CFLabel
	{
		public ToTableCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.LookupToTable.EffLabel"));
		}
	}

	protected class CallbackToTableChosen
	implements ICFBamJavaFXTableChosen
	{
		public CallbackToTableChosen() {
		}

		public void choseTable( ICFBamTableObj value ) {
			if( javafxReferenceLookupToTable != null ) {
				ICFBamRelationObj cur = getJavaFXFocusAsRelation();
				if( cur != null ) {
					ICFBamRelationEditObj editObj = (ICFBamRelationEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupToTable.setReferencedObject( value );
							editObj.setRequiredLookupToTable( value );
						}
					}
				}
			}
		}
	}

	protected class ToTableReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
			ICFBamRelationObj focus = getEffJavaFXFocus();
			ICFBamTableObj referencedObj = (ICFBamTableObj)javafxReferenceLookupToTable.getReferencedObject();
			java.util.List<ICFBamTableObj> listOfTable = null;
			ICFBamTableObj refFromTable = (ICFBamTableObj)focus.getRequiredContainerFromTable( javafxIsInitializing );
			if( refFromTable == null ) {
				CFConsole.message( String.format(Inz.s("cflibjavafx.common.SpecifyRelationBeforeSelectingTarget"), Inz.s("cfbam.javafx.Relation.AttrPane.ContainerFromTable.EffLabel"), Inz.s("cfbam.javafx.Relation.AttrPane.LookupToTable.EffLabel")));
				return;
			}
			ICFBamSchemaDefObj refSchemaDef = refFromTable.getRequiredContainerSchemaDef( javafxIsInitializing );
			if( refSchemaDef == null ) {
				CFConsole.message( String.format(Inz.s("cflibjavafx.common.SpecifyRelationBeforeSelectingTarget"), Inz.s("cfbam.javafx.Table.AttrPane.ContainerSchemaDef.EffLabel"), Inz.s("cfbam.javafx.Relation.AttrPane.LookupToTable.EffLabel")));
				return;
			}
			listOfTable = refSchemaDef.getOptionalComponentsTables( javafxIsInitializing );
			if( listOfTable == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"listOfTable" );
			}
			Collection<ICFBamTableObj> cltn = listOfTable;
			CFBorderPane form = javafxSchema.getTableFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackToTableChosen() );
			((ICFBamJavaFXTablePaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamRelationObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFBamTableObj referencedObj = (ICFBamTableObj)javafxReferenceLookupToTable.getReferencedObject();
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

	protected class ToTableCFReferenceEditor
		extends CFReferenceEditor
	{
		public ToTableCFReferenceEditor() {
			super( new ToTableReferenceCallback() );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.ToTable.EffLabel" );
		}
	}

	protected class ToIndexCFLabel
		extends CFLabel
	{
		public ToIndexCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.LookupToIndex.EffLabel"));
		}
	}

	protected class CallbackToIndexChosen
	implements ICFBamJavaFXIndexChosen
	{
		public CallbackToIndexChosen() {
		}

		public void choseIndex( ICFBamIndexObj value ) {
			if( javafxReferenceLookupToIndex != null ) {
				ICFBamRelationObj cur = getJavaFXFocusAsRelation();
				if( cur != null ) {
					ICFBamRelationEditObj editObj = (ICFBamRelationEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupToIndex.setReferencedObject( value );
							editObj.setRequiredLookupToIndex( value );
						}
					}
				}
			}
		}
	}

	protected class ToIndexReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
			ICFBamRelationObj focus = getEffJavaFXFocus();
			ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupToIndex.getReferencedObject();
			java.util.List<ICFBamIndexObj> listOfIndex = null;
			ICFBamTableObj refToTable = (ICFBamTableObj)focus.getRequiredLookupToTable( javafxIsInitializing );
			if( refToTable == null ) {
				CFConsole.message( String.format(Inz.s("cflibjavafx.common.SpecifyRelationBeforeSelectingTarget"), Inz.s("cfbam.javafx.Relation.AttrPane.LookupToTable.EffLabel"), Inz.s("cfbam.javafx.Relation.AttrPane.LookupToIndex.EffLabel")));
				return;
			}
			listOfIndex = refToTable.getOptionalComponentsIndex( javafxIsInitializing );
			if( listOfIndex == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"listOfIndex" );
			}
			Collection<ICFBamIndexObj> cltn = listOfIndex;
			CFBorderPane form = javafxSchema.getIndexFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackToIndexChosen() );
			((ICFBamJavaFXIndexPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamRelationObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFBamIndexObj referencedObj = (ICFBamIndexObj)javafxReferenceLookupToIndex.getReferencedObject();
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

	protected class ToIndexCFReferenceEditor
		extends CFReferenceEditor
	{
		public ToIndexCFReferenceEditor() {
			super( new ToIndexReferenceCallback() );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.ToIndex.EffLabel" );
		}
	}

	protected class NarrowedRelationCFLabel
		extends CFLabel
	{
		public NarrowedRelationCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.LookupNarrowed.EffLabel"));
		}
	}

	protected class CallbackNarrowedRelationChosen
	implements ICFBamJavaFXRelationChosen
	{
		public CallbackNarrowedRelationChosen() {
		}

		public void choseRelation( ICFBamRelationObj value ) {
			if( javafxReferenceLookupNarrowed != null ) {
				ICFBamRelationObj cur = getJavaFXFocusAsRelation();
				if( cur != null ) {
					ICFBamRelationEditObj editObj = (ICFBamRelationEditObj)cur.getEdit();
					if( null != editObj ) {
						CFPane.PaneMode curMode = getPaneMode();
						if( ( curMode == CFPane.PaneMode.Add ) || ( curMode == CFPane.PaneMode.Edit ) ) {
							javafxReferenceLookupNarrowed.setReferencedObject( value );
							editObj.setOptionalLookupNarrowed( value );
						}
					}
				}
			}
		}
	}

	protected class NarrowedRelationReferenceCallback
	implements ICFReferenceCallback
	{
		public void chose( ICFLibAnyObj value ) {
			final String S_ProcName = "chose";
			Node cont;
			ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
			ICFBamRelationObj focus = getEffJavaFXFocus();
			ICFBamRelationObj referencedObj = (ICFBamRelationObj)javafxReferenceLookupNarrowed.getReferencedObject();
			java.util.List<ICFBamRelationObj> listOfRelation = null;
			ICFBamTableObj refFromTable = (ICFBamTableObj)focus.getRequiredContainerFromTable( javafxIsInitializing );
			if( refFromTable == null ) {
				CFConsole.message( String.format(Inz.s("cflibjavafx.common.SpecifyRelationBeforeSelectingTarget"), Inz.s("cfbam.javafx.Relation.AttrPane.ContainerFromTable.EffLabel"), Inz.s("cfbam.javafx.Relation.AttrPane.LookupNarrowed.EffLabel")));
				return;
			}
			listOfRelation = refFromTable.getOptionalComponentsRelation( javafxIsInitializing );
			if( listOfRelation == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"listOfRelation" );
			}
			Collection<ICFBamRelationObj> cltn = listOfRelation;
			CFBorderPane form = javafxSchema.getRelationFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackNarrowedRelationChosen() );
			((ICFBamJavaFXRelationPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamRelationObj focus = getEffJavaFXFocus();
			if( focus != null ) {
				ICFBamRelationObj referencedObj = (ICFBamRelationObj)javafxReferenceLookupNarrowed.getReferencedObject();
				CFBorderPane form = null;
				if( referencedObj != null ) {
					int classCode = referencedObj.getClassCode();
					ICFSecSchema.ClassMapEntry entry = ICFBamSchema.getClassMapByRuntimeClassCode(classCode);
					int backingClassCode = entry.getBackingClassCode();
					if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamRelation.CLASS_CODE ) {
						form = javafxSchema.getRelationFactory().newAddForm( cfFormManager, referencedObj, null, true );
						ICFBamJavaFXRelationPaneCommon spec = (ICFBamJavaFXRelationPaneCommon)form;
						spec.setJavaFXFocus( referencedObj );
						spec.setPaneMode( CFPane.PaneMode.View );
					}
					else {
						throw new CFLibUnsupportedClassException( getClass(),
							S_ProcName,
							"javaFXFocus",
							focus,
							"ICFBamRelationObj" );
					}
					cfFormManager.pushForm( form );
				}
			}
		}
	}

	protected class NarrowedRelationCFReferenceEditor
		extends CFReferenceEditor
	{
		public NarrowedRelationCFReferenceEditor() {
			super( new NarrowedRelationReferenceCallback() );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.NarrowedRelation.EffLabel" );
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
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.Name.EffLabel"));
		}
	}

	protected class NameEditor
		extends CFStringEditor
	{
		public NameEditor() {
			super();
			setMaxLen( 192 );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.Name.EffLabel" );
		}
	}

	protected class ShortNameCFLabel
		extends CFLabel
	{
		public ShortNameCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.ShortName.EffLabel"));
		}
	}

	protected class ShortNameEditor
		extends CFStringEditor
	{
		public ShortNameEditor() {
			super();
			setMaxLen( 16 );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.ShortName.EffLabel" );
		}
	}

	protected class LabelCFLabel
		extends CFLabel
	{
		public LabelCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.Label.EffLabel"));
		}
	}

	protected class LabelEditor
		extends CFStringEditor
	{
		public LabelEditor() {
			super();
			setMaxLen( 64 );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.Label.EffLabel" );
		}
	}

	protected class ShortDescriptionCFLabel
		extends CFLabel
	{
		public ShortDescriptionCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.ShortDescription.EffLabel"));
		}
	}

	protected class ShortDescriptionEditor
		extends CFStringEditor
	{
		public ShortDescriptionEditor() {
			super();
			setMaxLen( 128 );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.ShortDescription.EffLabel" );
		}
	}

	protected class DescriptionCFLabel
		extends CFLabel
	{
		public DescriptionCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.Description.EffLabel"));
		}
	}

	protected class DescriptionEditor
		extends CFStringEditor
	{
		public DescriptionEditor() {
			super();
			setMaxLen( 1023 );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.Description.EffLabel" );
		}
	}

	protected class RelationTypeCFLabel
		extends CFLabel
	{
		public RelationTypeCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.RelationType.EffLabel"));
		}
	}

	protected class RelationTypeEditor
		extends ComboBox<String>
	{
		public RelationTypeEditor() {
			super();
			setItems( observableListOfRelationType );
		}
	}

	protected class DbNameCFLabel
		extends CFLabel
	{
		public DbNameCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.DbName.EffLabel"));
		}
	}

	protected class DbNameEditor
		extends CFStringEditor
	{
		public DbNameEditor() {
			super();
			setMaxLen( 32 );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.DbName.EffLabel" );
		}
	}

	protected class SuffixCFLabel
		extends CFLabel
	{
		public SuffixCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.Suffix.EffLabel"));
		}
	}

	protected class SuffixEditor
		extends CFStringEditor
	{
		public SuffixEditor() {
			super();
			setMaxLen( 16 );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.Suffix.EffLabel" );
		}
	}

	protected class IsRequiredCFLabel
		extends CFLabel
	{
		public IsRequiredCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.IsRequired.EffLabel"));
		}
	}

	protected class IsRequiredEditor
		extends CFBoolEditor
	{
		public IsRequiredEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.IsRequired.EffLabel" );
		}
	}

	protected class IsXsdContainerCFLabel
		extends CFLabel
	{
		public IsXsdContainerCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.IsXsdContainer.EffLabel"));
		}
	}

	protected class IsXsdContainerEditor
		extends CFBoolEditor
	{
		public IsXsdContainerEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.IsXsdContainer.EffLabel" );
		}
	}

	protected class IsLateResolverCFLabel
		extends CFLabel
	{
		public IsLateResolverCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.IsLateResolver.EffLabel"));
		}
	}

	protected class IsLateResolverEditor
		extends CFBoolEditor
	{
		public IsLateResolverEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.IsLateResolver.EffLabel" );
		}
	}

	protected class AllowAddendumCFLabel
		extends CFLabel
	{
		public AllowAddendumCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Relation.AttrPane.AllowAddendum.EffLabel"));
		}
	}

	protected class AllowAddendumEditor
		extends CFBoolEditor
	{
		public AllowAddendumEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfbam.javafx.Relation.AttrPane.AllowAddendum.EffLabel" );
		}
	}

	protected ICFBamSchemaDefObj javafxLookupDefSchemaObj = null;
	protected DefSchemaCFLabel javafxLabelLookupDefSchema = null;
	protected DefSchemaCFReferenceEditor javafxReferenceLookupDefSchema = null;
	protected ICFBamIndexObj javafxLookupFromIndexObj = null;
	protected FromIndexCFLabel javafxLabelLookupFromIndex = null;
	protected FromIndexCFReferenceEditor javafxReferenceLookupFromIndex = null;
	protected ICFBamTableObj javafxLookupToTableObj = null;
	protected ToTableCFLabel javafxLabelLookupToTable = null;
	protected ToTableCFReferenceEditor javafxReferenceLookupToTable = null;
	protected ICFBamIndexObj javafxLookupToIndexObj = null;
	protected ToIndexCFLabel javafxLabelLookupToIndex = null;
	protected ToIndexCFReferenceEditor javafxReferenceLookupToIndex = null;
	protected ICFBamRelationObj javafxLookupNarrowedObj = null;
	protected NarrowedRelationCFLabel javafxLabelLookupNarrowed = null;
	protected NarrowedRelationCFReferenceEditor javafxReferenceLookupNarrowed = null;
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
	protected RelationTypeCFLabel javafxLabelRelationType = null;
	protected RelationTypeEditor javafxEditorRelationType = null;
	protected DbNameCFLabel javafxLabelDbName = null;
	protected DbNameEditor javafxEditorDbName = null;
	protected SuffixCFLabel javafxLabelSuffix = null;
	protected SuffixEditor javafxEditorSuffix = null;
	protected IsRequiredCFLabel javafxLabelIsRequired = null;
	protected IsRequiredEditor javafxEditorIsRequired = null;
	protected IsXsdContainerCFLabel javafxLabelIsXsdContainer = null;
	protected IsXsdContainerEditor javafxEditorIsXsdContainer = null;
	protected IsLateResolverCFLabel javafxLabelIsLateResolver = null;
	protected IsLateResolverEditor javafxEditorIsLateResolver = null;
	protected AllowAddendumCFLabel javafxLabelAllowAddendum = null;
	protected AllowAddendumEditor javafxEditorAllowAddendum = null;

	public CFBamJavaFXRelationAttrPane( ICFFormManager formManager, ICFBamJavaFXSchema argSchema, ICFBamRelationObj argFocus ) {
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
		setJavaFXFocusAsRelation( argFocus );
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

		label = getJavaFXLabelLookupFromIndex();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceLookupFromIndex();
		setHalignment( reference, HPos.LEFT );
		add( reference, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelLookupToTable();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceLookupToTable();
		setHalignment( reference, HPos.LEFT );
		add( reference, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelLookupToIndex();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceLookupToIndex();
		setHalignment( reference, HPos.LEFT );
		add( reference, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelLookupNarrowed();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		reference = getJavaFXReferenceLookupNarrowed();
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

		label = getJavaFXLabelRelationType();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorRelationType();
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

		label = getJavaFXLabelSuffix();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorSuffix();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelIsRequired();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorIsRequired();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelIsXsdContainer();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorIsXsdContainer();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelIsLateResolver();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorIsLateResolver();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelAllowAddendum();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorAllowAddendum();
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
		if( ( value == null ) || ( value instanceof ICFBamRelationObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamRelationObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFBamRelationObj getJavaFXFocusAsRelation() {
		return( (ICFBamRelationObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsRelation( ICFBamRelationObj value ) {
		setJavaFXFocus( value );
	}

	public ICFBamRelationObj getEffJavaFXFocus() {
		ICFBamRelationObj eff = (ICFBamRelationObj)getJavaFXFocus();
		if( eff != null ) {
			if( null != eff.getEdit() ) {
				eff = (ICFBamRelationObj)eff.getEdit();
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

	public ICFBamIndexObj getJavaFXLookupFromIndexObj() {
		return( javafxLookupFromIndexObj );
	}

	public void setJavaFXLookupFromIndexObj( ICFBamIndexObj value ) {
		javafxLookupFromIndexObj = value;
	}

	public CFLabel getJavaFXLabelLookupFromIndex() {
		if( javafxLabelLookupFromIndex == null ) {
			javafxLabelLookupFromIndex = new FromIndexCFLabel();
		}
		return( javafxLabelLookupFromIndex );
	}

	public CFReferenceEditor getJavaFXReferenceLookupFromIndex() {
		if( javafxReferenceLookupFromIndex == null ) {
			javafxReferenceLookupFromIndex = new FromIndexCFReferenceEditor();
		}
		return( javafxReferenceLookupFromIndex );
	}

	public void setJavaFXReferenceLookupFromIndex( FromIndexCFReferenceEditor value ) {
		javafxReferenceLookupFromIndex = value;
	}

	public ICFBamTableObj getJavaFXLookupToTableObj() {
		return( javafxLookupToTableObj );
	}

	public void setJavaFXLookupToTableObj( ICFBamTableObj value ) {
		javafxLookupToTableObj = value;
	}

	public CFLabel getJavaFXLabelLookupToTable() {
		if( javafxLabelLookupToTable == null ) {
			javafxLabelLookupToTable = new ToTableCFLabel();
		}
		return( javafxLabelLookupToTable );
	}

	public CFReferenceEditor getJavaFXReferenceLookupToTable() {
		if( javafxReferenceLookupToTable == null ) {
			javafxReferenceLookupToTable = new ToTableCFReferenceEditor();
		}
		return( javafxReferenceLookupToTable );
	}

	public void setJavaFXReferenceLookupToTable( ToTableCFReferenceEditor value ) {
		javafxReferenceLookupToTable = value;
	}

	public ICFBamIndexObj getJavaFXLookupToIndexObj() {
		return( javafxLookupToIndexObj );
	}

	public void setJavaFXLookupToIndexObj( ICFBamIndexObj value ) {
		javafxLookupToIndexObj = value;
	}

	public CFLabel getJavaFXLabelLookupToIndex() {
		if( javafxLabelLookupToIndex == null ) {
			javafxLabelLookupToIndex = new ToIndexCFLabel();
		}
		return( javafxLabelLookupToIndex );
	}

	public CFReferenceEditor getJavaFXReferenceLookupToIndex() {
		if( javafxReferenceLookupToIndex == null ) {
			javafxReferenceLookupToIndex = new ToIndexCFReferenceEditor();
		}
		return( javafxReferenceLookupToIndex );
	}

	public void setJavaFXReferenceLookupToIndex( ToIndexCFReferenceEditor value ) {
		javafxReferenceLookupToIndex = value;
	}

	public ICFBamRelationObj getJavaFXLookupNarrowedObj() {
		return( javafxLookupNarrowedObj );
	}

	public void setJavaFXLookupNarrowedObj( ICFBamRelationObj value ) {
		javafxLookupNarrowedObj = value;
	}

	public CFLabel getJavaFXLabelLookupNarrowed() {
		if( javafxLabelLookupNarrowed == null ) {
			javafxLabelLookupNarrowed = new NarrowedRelationCFLabel();
		}
		return( javafxLabelLookupNarrowed );
	}

	public CFReferenceEditor getJavaFXReferenceLookupNarrowed() {
		if( javafxReferenceLookupNarrowed == null ) {
			javafxReferenceLookupNarrowed = new NarrowedRelationCFReferenceEditor();
		}
		return( javafxReferenceLookupNarrowed );
	}

	public void setJavaFXReferenceLookupNarrowed( NarrowedRelationCFReferenceEditor value ) {
		javafxReferenceLookupNarrowed = value;
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

	public RelationTypeCFLabel getJavaFXLabelRelationType() {
		if( javafxLabelRelationType == null ) {
			javafxLabelRelationType = new RelationTypeCFLabel();
		}
		return( javafxLabelRelationType );
	}

	public void setJavaFXLabelRelationType( RelationTypeCFLabel value ) {
		javafxLabelRelationType = value;
	}

	public RelationTypeEditor getJavaFXEditorRelationType() {
		if( javafxEditorRelationType == null ) {
			javafxEditorRelationType = new RelationTypeEditor();
		}
		return( javafxEditorRelationType );
	}

	public void setJavaFXEditorRelationType( RelationTypeEditor value ) {
		javafxEditorRelationType = value;
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

	public SuffixCFLabel getJavaFXLabelSuffix() {
		if( javafxLabelSuffix == null ) {
			javafxLabelSuffix = new SuffixCFLabel();
		}
		return( javafxLabelSuffix );
	}

	public void setJavaFXLabelSuffix( SuffixCFLabel value ) {
		javafxLabelSuffix = value;
	}

	public SuffixEditor getJavaFXEditorSuffix() {
		if( javafxEditorSuffix == null ) {
			javafxEditorSuffix = new SuffixEditor();
		}
		return( javafxEditorSuffix );
	}

	public void setJavaFXEditorSuffix( SuffixEditor value ) {
		javafxEditorSuffix = value;
	}

	public IsRequiredCFLabel getJavaFXLabelIsRequired() {
		if( javafxLabelIsRequired == null ) {
			javafxLabelIsRequired = new IsRequiredCFLabel();
		}
		return( javafxLabelIsRequired );
	}

	public void setJavaFXLabelIsRequired( IsRequiredCFLabel value ) {
		javafxLabelIsRequired = value;
	}

	public IsRequiredEditor getJavaFXEditorIsRequired() {
		if( javafxEditorIsRequired == null ) {
			javafxEditorIsRequired = new IsRequiredEditor();
		}
		return( javafxEditorIsRequired );
	}

	public void setJavaFXEditorIsRequired( IsRequiredEditor value ) {
		javafxEditorIsRequired = value;
	}

	public IsXsdContainerCFLabel getJavaFXLabelIsXsdContainer() {
		if( javafxLabelIsXsdContainer == null ) {
			javafxLabelIsXsdContainer = new IsXsdContainerCFLabel();
		}
		return( javafxLabelIsXsdContainer );
	}

	public void setJavaFXLabelIsXsdContainer( IsXsdContainerCFLabel value ) {
		javafxLabelIsXsdContainer = value;
	}

	public IsXsdContainerEditor getJavaFXEditorIsXsdContainer() {
		if( javafxEditorIsXsdContainer == null ) {
			javafxEditorIsXsdContainer = new IsXsdContainerEditor();
		}
		return( javafxEditorIsXsdContainer );
	}

	public void setJavaFXEditorIsXsdContainer( IsXsdContainerEditor value ) {
		javafxEditorIsXsdContainer = value;
	}

	public IsLateResolverCFLabel getJavaFXLabelIsLateResolver() {
		if( javafxLabelIsLateResolver == null ) {
			javafxLabelIsLateResolver = new IsLateResolverCFLabel();
		}
		return( javafxLabelIsLateResolver );
	}

	public void setJavaFXLabelIsLateResolver( IsLateResolverCFLabel value ) {
		javafxLabelIsLateResolver = value;
	}

	public IsLateResolverEditor getJavaFXEditorIsLateResolver() {
		if( javafxEditorIsLateResolver == null ) {
			javafxEditorIsLateResolver = new IsLateResolverEditor();
		}
		return( javafxEditorIsLateResolver );
	}

	public void setJavaFXEditorIsLateResolver( IsLateResolverEditor value ) {
		javafxEditorIsLateResolver = value;
	}

	public AllowAddendumCFLabel getJavaFXLabelAllowAddendum() {
		if( javafxLabelAllowAddendum == null ) {
			javafxLabelAllowAddendum = new AllowAddendumCFLabel();
		}
		return( javafxLabelAllowAddendum );
	}

	public void setJavaFXLabelAllowAddendum( AllowAddendumCFLabel value ) {
		javafxLabelAllowAddendum = value;
	}

	public AllowAddendumEditor getJavaFXEditorAllowAddendum() {
		if( javafxEditorAllowAddendum == null ) {
			javafxEditorAllowAddendum = new AllowAddendumEditor();
		}
		return( javafxEditorAllowAddendum );
	}

	public void setJavaFXEditorAllowAddendum( AllowAddendumEditor value ) {
		javafxEditorAllowAddendum = value;
	}

	public void populateFields()
	{
		ICFBamRelationObj popObj = getEffJavaFXFocus();
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
			javafxLookupFromIndexObj = null;
		}
		else {
			javafxLookupFromIndexObj = (ICFBamIndexObj)popObj.getRequiredLookupFromIndex( javafxIsInitializing );
		}
		if( javafxReferenceLookupFromIndex != null ) {
			javafxReferenceLookupFromIndex.setReferencedObject( javafxLookupFromIndexObj );
		}

		if( popObj == null ) {
			javafxLookupToTableObj = null;
		}
		else {
			javafxLookupToTableObj = (ICFBamTableObj)popObj.getRequiredLookupToTable( javafxIsInitializing );
		}
		if( javafxReferenceLookupToTable != null ) {
			javafxReferenceLookupToTable.setReferencedObject( javafxLookupToTableObj );
		}

		if( popObj == null ) {
			javafxLookupToIndexObj = null;
		}
		else {
			javafxLookupToIndexObj = (ICFBamIndexObj)popObj.getRequiredLookupToIndex( javafxIsInitializing );
		}
		if( javafxReferenceLookupToIndex != null ) {
			javafxReferenceLookupToIndex.setReferencedObject( javafxLookupToIndexObj );
		}

		if( popObj == null ) {
			javafxLookupNarrowedObj = null;
		}
		else {
			javafxLookupNarrowedObj = (ICFBamRelationObj)popObj.getOptionalLookupNarrowed( javafxIsInitializing );
		}
		if( javafxReferenceLookupNarrowed != null ) {
			javafxReferenceLookupNarrowed.setReferencedObject( javafxLookupNarrowedObj );
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
			getJavaFXEditorRelationType().setValue( null );
		}
		else {
			getJavaFXEditorRelationType().setValue(
				( popObj.getRequiredRelationType() == null )
					? null
					: popObj.getRequiredRelationType().toString() );
		}

		if( popObj == null ) {
			getJavaFXEditorDbName().setStringValue( null );
		}
		else {
			getJavaFXEditorDbName().setStringValue( popObj.getOptionalDbName() );
		}

		if( popObj == null ) {
			getJavaFXEditorSuffix().setStringValue( null );
		}
		else {
			getJavaFXEditorSuffix().setStringValue( popObj.getOptionalSuffix() );
		}

		if( popObj == null ) {
			getJavaFXEditorIsRequired().setBooleanValue( null );
		}
		else {
			getJavaFXEditorIsRequired().setBooleanValue( popObj.getRequiredIsRequired() );
		}

		if( popObj == null ) {
			getJavaFXEditorIsXsdContainer().setBooleanValue( null );
		}
		else {
			getJavaFXEditorIsXsdContainer().setBooleanValue( popObj.getRequiredIsXsdContainer() );
		}

		if( popObj == null ) {
			getJavaFXEditorIsLateResolver().setBooleanValue( null );
		}
		else {
			getJavaFXEditorIsLateResolver().setBooleanValue( popObj.getRequiredIsLateResolver() );
		}

		if( popObj == null ) {
			getJavaFXEditorAllowAddendum().setBooleanValue( null );
		}
		else {
			getJavaFXEditorAllowAddendum().setBooleanValue( popObj.getRequiredAllowAddendum() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFBamRelationObj focus = getJavaFXFocusAsRelation();
		ICFBamRelationEditObj editObj;
		if( focus != null ) {
			editObj = (ICFBamRelationEditObj)(focus.getEdit());
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

		javafxLookupFromIndexObj = (ICFBamIndexObj)( javafxReferenceLookupFromIndex.getReferencedObject() );
		editObj.setRequiredLookupFromIndex( javafxLookupFromIndexObj );

		javafxLookupToTableObj = (ICFBamTableObj)( javafxReferenceLookupToTable.getReferencedObject() );
		editObj.setRequiredLookupToTable( javafxLookupToTableObj );

		javafxLookupToIndexObj = (ICFBamIndexObj)( javafxReferenceLookupToIndex.getReferencedObject() );
		editObj.setRequiredLookupToIndex( javafxLookupToIndexObj );

		javafxLookupNarrowedObj = (ICFBamRelationObj)( javafxReferenceLookupNarrowed.getReferencedObject() );
		editObj.setOptionalLookupNarrowed( javafxLookupNarrowedObj );

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

		String strrelationtype = getJavaFXEditorRelationType().getValue();
		ICFBamSchema.RelationTypeEnum currelationtype = ICFBamSchema.parseRelationTypeEnum( "RelationType", strrelationtype );
		editObj.setRequiredRelationType( currelationtype );

		if( ( getJavaFXEditorDbName().getStringValue() != null ) && ( getJavaFXEditorDbName().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalDbName( null );
		}
		else {
			editObj.setOptionalDbName( getJavaFXEditorDbName().getStringValue() );
		}

		if( ( getJavaFXEditorSuffix().getStringValue() != null ) && ( getJavaFXEditorSuffix().getStringValue().length() <= 0 ) ) {
			editObj.setOptionalSuffix( null );
		}
		else {
			editObj.setOptionalSuffix( getJavaFXEditorSuffix().getStringValue() );
		}

		editObj.setRequiredIsRequired( getJavaFXEditorIsRequired().getBooleanValue() );

		editObj.setRequiredIsXsdContainer( getJavaFXEditorIsXsdContainer().getBooleanValue() );

		editObj.setRequiredIsLateResolver( getJavaFXEditorIsLateResolver().getBooleanValue() );

		editObj.setRequiredAllowAddendum( getJavaFXEditorAllowAddendum().getBooleanValue() );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFBamRelationObj focus = getJavaFXFocusAsRelation();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFBamRelationEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFBamRelationEditObj)focus.getEdit();
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
								editObj = (ICFBamRelationEditObj)focus.beginEdit();
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
							editObj = (ICFBamRelationEditObj)focus.beginEdit();
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
							editObj = (ICFBamRelationEditObj)focus.beginEdit();
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
							editObj = (ICFBamRelationEditObj)focus.beginEdit();
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
						focus = (ICFBamRelationObj)editObj.create();
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
								editObj = (ICFBamRelationEditObj)focus.beginEdit();
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
								editObj = (ICFBamRelationEditObj)focus.beginEdit();
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
							editObj = (ICFBamRelationEditObj)focus.beginEdit();
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
			ICFBamRelationObj focus = getJavaFXFocusAsRelation();
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
		if( javafxReferenceLookupFromIndex != null ) {
			javafxReferenceLookupFromIndex.setCustomDisable( ! isEditing );
		}
		if( javafxReferenceLookupToTable != null ) {
			javafxReferenceLookupToTable.setCustomDisable( ! isEditing );
		}
		if( javafxReferenceLookupToIndex != null ) {
			javafxReferenceLookupToIndex.setCustomDisable( ! isEditing );
		}
		if( javafxReferenceLookupNarrowed != null ) {
			javafxReferenceLookupNarrowed.setCustomDisable( ! isEditing );
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
		if( javafxEditorRelationType != null ) {
			javafxEditorRelationType.setDisable( ! isEditing );
		}
		if( javafxEditorDbName != null ) {
			javafxEditorDbName.setDisable( ! isEditing );
		}
		if( javafxEditorSuffix != null ) {
			javafxEditorSuffix.setDisable( ! isEditing );
		}
		if( javafxEditorIsRequired != null ) {
			javafxEditorIsRequired.setDisable( ! isEditing );
		}
		if( javafxEditorIsXsdContainer != null ) {
			javafxEditorIsXsdContainer.setDisable( ! isEditing );
		}
		if( javafxEditorIsLateResolver != null ) {
			javafxEditorIsLateResolver.setDisable( ! isEditing );
		}
		if( javafxEditorAllowAddendum != null ) {
			javafxEditorAllowAddendum.setDisable( ! isEditing );
		}
	}
}
