// Description: Java 25 JavaFX Attribute Pane implementation for SchemaTweak.

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
 *	CFBamJavaFXSchemaTweakAttrPane JavaFX Attribute Pane implementation
 *	for SchemaTweak.
 */
public class CFBamJavaFXSchemaTweakAttrPane
extends CFGridPane
implements ICFBamJavaFXSchemaTweakPaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFBamJavaFXSchema javafxSchema = null;
	boolean javafxIsInitializing = true;

	protected class DefSchemaCFLabel
		extends CFLabel
	{
		public DefSchemaCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Tweak.AttrPane.LookupDefSchema.EffLabel"));
		}
	}

	protected class CallbackDefSchemaChosen
	implements ICFBamJavaFXSchemaDefChosen
	{
		public CallbackDefSchemaChosen() {
		}

		public void choseSchemaDef( ICFBamSchemaDefObj value ) {
			if( javafxReferenceLookupDefSchema != null ) {
				ICFBamSchemaTweakObj cur = getJavaFXFocusAsSchemaTweak();
				if( cur != null ) {
					ICFBamSchemaTweakEditObj editObj = (ICFBamSchemaTweakEditObj)cur.getEdit();
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
			ICFBamSchemaTweakObj focus = getEffJavaFXFocus();
			ICFBamSchemaDefObj referencedObj = (ICFBamSchemaDefObj)javafxReferenceLookupDefSchema.getReferencedObject();
			java.util.List<ICFBamSchemaDefObj> listOfSchemaDef = null;
			Collection<ICFBamSchemaDefObj> cltn = null;
			CFBorderPane form = javafxSchema.getSchemaDefFactory().newPickerForm( cfFormManager, referencedObj, null, cltn, new CallbackDefSchemaChosen() );
			((ICFBamJavaFXSchemaDefPaneCommon)form).setPaneMode( CFPane.PaneMode.View );
			cfFormManager.pushForm( form );
		}

		public void view( ICFLibAnyObj value ) {
			final String S_ProcName = "actionPerformed";
			ICFBamSchemaTweakObj focus = getEffJavaFXFocus();
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
			setFieldNameInzTag( "cfbam.javafx.Tweak.AttrPane.DefSchema.EffLabel" );
		}
	}

	protected class TenantIdCFLabel
		extends CFLabel
	{
		public TenantIdCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Tweak.AttrPane.TenantId.EffLabel"));
		}
	}

	protected class TenantIdEditor
		extends CFDbKeyHash256Editor
	{
		public TenantIdEditor() {
			super();
			setFieldNameInzTag( "cfbam.javafx.Tweak.AttrPane.TenantId.EffLabel" );
		}
	}

	protected class IdCFLabel
		extends CFLabel
	{
		public IdCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Tweak.AttrPane.Id.EffLabel"));
		}
	}

	protected class IdEditor
		extends CFDbKeyHash256Editor
	{
		public IdEditor() {
			super();
			setFieldNameInzTag( "cfbam.javafx.Tweak.AttrPane.Id.EffLabel" );
		}
	}

	protected class DefSchemaTenantIdCFLabel
		extends CFLabel
	{
		public DefSchemaTenantIdCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Tweak.AttrPane.DefSchemaTenantId.EffLabel"));
		}
	}

	protected class DefSchemaTenantIdEditor
		extends CFDbKeyHash256Editor
	{
		public DefSchemaTenantIdEditor() {
			super();
			setFieldNameInzTag( "cfbam.javafx.Tweak.AttrPane.DefSchemaTenantId.EffLabel" );
		}
	}

	protected class NameCFLabel
		extends CFLabel
	{
		public NameCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Tweak.AttrPane.Name.EffLabel"));
		}
	}

	protected class NameEditor
		extends CFStringEditor
	{
		public NameEditor() {
			super();
			setMaxLen( 192 );
			setFieldNameInzTag( "cfbam.javafx.Tweak.AttrPane.Name.EffLabel" );
		}
	}

	protected class ReplacesInheritedCFLabel
		extends CFLabel
	{
		public ReplacesInheritedCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Tweak.AttrPane.ReplacesInherited.EffLabel"));
		}
	}

	protected class ReplacesInheritedEditor
		extends CFBoolEditor
	{
		public ReplacesInheritedEditor() {
			super();
			setIsNullable( false );
			setFieldNameInzTag( "cfbam.javafx.Tweak.AttrPane.ReplacesInherited.EffLabel" );
		}
	}

	protected class TweakGelTextCFLabel
		extends CFLabel
	{
		public TweakGelTextCFLabel() {
			super();
			setText(Inz.s("cfbam.javafx.Tweak.AttrPane.TweakGelText.EffLabel"));
		}
	}

	protected class TweakGelTextEditor
		extends CFTextEditor
	{
		public TweakGelTextEditor() {
			super();
			setMaxLen( 2000000 );
			setFieldNameInzTag( "cfbam.javafx.Tweak.AttrPane.TweakGelText.EffLabel" );
			setPrefHeight( 10 * 20 );
			setMinHeight( 10 * 20 );
		}
	}

	protected ICFBamSchemaDefObj javafxLookupDefSchemaObj = null;
	protected DefSchemaCFLabel javafxLabelLookupDefSchema = null;
	protected DefSchemaCFReferenceEditor javafxReferenceLookupDefSchema = null;
	protected TenantIdCFLabel javafxLabelTenantId = null;
	protected TenantIdEditor javafxEditorTenantId = null;
	protected IdCFLabel javafxLabelId = null;
	protected IdEditor javafxEditorId = null;
	protected DefSchemaTenantIdCFLabel javafxLabelDefSchemaTenantId = null;
	protected DefSchemaTenantIdEditor javafxEditorDefSchemaTenantId = null;
	protected NameCFLabel javafxLabelName = null;
	protected NameEditor javafxEditorName = null;
	protected ReplacesInheritedCFLabel javafxLabelReplacesInherited = null;
	protected ReplacesInheritedEditor javafxEditorReplacesInherited = null;
	protected TweakGelTextCFLabel javafxLabelTweakGelText = null;
	protected TweakGelTextEditor javafxEditorTweakGelText = null;

	public CFBamJavaFXSchemaTweakAttrPane( ICFFormManager formManager, ICFBamJavaFXSchema argSchema, ICFBamSchemaTweakObj argFocus ) {
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
		setJavaFXFocusAsSchemaTweak( argFocus );
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

		label = getJavaFXLabelTenantId();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorTenantId();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
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

		label = getJavaFXLabelDefSchemaTenantId();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorDefSchemaTenantId();
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

		label = getJavaFXLabelReplacesInherited();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorReplacesInherited();
		setHalignment( ctrl, HPos.LEFT );
		add( ctrl, 0, gridRow );
		gridRow ++;

		label = getJavaFXLabelTweakGelText();
		setHalignment( label, HPos.LEFT );
		setValignment( label, VPos.BOTTOM );
		add( label, 0, gridRow );
		gridRow ++;

		ctrl = getJavaFXEditorTweakGelText();
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
		if( ( value == null ) || ( value instanceof ICFBamSchemaTweakObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamSchemaTweakObj" );
		}
		populateFields();
		adjustComponentEnableStates();
	}

	public ICFBamSchemaTweakObj getJavaFXFocusAsSchemaTweak() {
		return( (ICFBamSchemaTweakObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsSchemaTweak( ICFBamSchemaTweakObj value ) {
		setJavaFXFocus( value );
	}

	public ICFBamSchemaTweakObj getEffJavaFXFocus() {
		ICFBamSchemaTweakObj eff = (ICFBamSchemaTweakObj)getJavaFXFocus();
		if( eff != null ) {
			if( null != eff.getEdit() ) {
				eff = (ICFBamSchemaTweakObj)eff.getEdit();
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

	public TenantIdCFLabel getJavaFXLabelTenantId() {
		if( javafxLabelTenantId == null ) {
			javafxLabelTenantId = new TenantIdCFLabel();
		}
		return( javafxLabelTenantId );
	}

	public void setJavaFXLabelTenantId( TenantIdCFLabel value ) {
		javafxLabelTenantId = value;
	}

	public TenantIdEditor getJavaFXEditorTenantId() {
		if( javafxEditorTenantId == null ) {
			javafxEditorTenantId = new TenantIdEditor();
		}
		return( javafxEditorTenantId );
	}

	public void setJavaFXEditorTenantId( TenantIdEditor value ) {
		javafxEditorTenantId = value;
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

	public DefSchemaTenantIdCFLabel getJavaFXLabelDefSchemaTenantId() {
		if( javafxLabelDefSchemaTenantId == null ) {
			javafxLabelDefSchemaTenantId = new DefSchemaTenantIdCFLabel();
		}
		return( javafxLabelDefSchemaTenantId );
	}

	public void setJavaFXLabelDefSchemaTenantId( DefSchemaTenantIdCFLabel value ) {
		javafxLabelDefSchemaTenantId = value;
	}

	public DefSchemaTenantIdEditor getJavaFXEditorDefSchemaTenantId() {
		if( javafxEditorDefSchemaTenantId == null ) {
			javafxEditorDefSchemaTenantId = new DefSchemaTenantIdEditor();
		}
		return( javafxEditorDefSchemaTenantId );
	}

	public void setJavaFXEditorDefSchemaTenantId( DefSchemaTenantIdEditor value ) {
		javafxEditorDefSchemaTenantId = value;
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

	public ReplacesInheritedCFLabel getJavaFXLabelReplacesInherited() {
		if( javafxLabelReplacesInherited == null ) {
			javafxLabelReplacesInherited = new ReplacesInheritedCFLabel();
		}
		return( javafxLabelReplacesInherited );
	}

	public void setJavaFXLabelReplacesInherited( ReplacesInheritedCFLabel value ) {
		javafxLabelReplacesInherited = value;
	}

	public ReplacesInheritedEditor getJavaFXEditorReplacesInherited() {
		if( javafxEditorReplacesInherited == null ) {
			javafxEditorReplacesInherited = new ReplacesInheritedEditor();
		}
		return( javafxEditorReplacesInherited );
	}

	public void setJavaFXEditorReplacesInherited( ReplacesInheritedEditor value ) {
		javafxEditorReplacesInherited = value;
	}

	public TweakGelTextCFLabel getJavaFXLabelTweakGelText() {
		if( javafxLabelTweakGelText == null ) {
			javafxLabelTweakGelText = new TweakGelTextCFLabel();
		}
		return( javafxLabelTweakGelText );
	}

	public void setJavaFXLabelTweakGelText( TweakGelTextCFLabel value ) {
		javafxLabelTweakGelText = value;
	}

	public TweakGelTextEditor getJavaFXEditorTweakGelText() {
		if( javafxEditorTweakGelText == null ) {
			javafxEditorTweakGelText = new TweakGelTextEditor();
		}
		return( javafxEditorTweakGelText );
	}

	public void setJavaFXEditorTweakGelText( TweakGelTextEditor value ) {
		javafxEditorTweakGelText = value;
	}

	public void populateFields()
	{
		ICFBamSchemaTweakObj popObj = getEffJavaFXFocus();
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
			getJavaFXEditorTenantId().setDbKeyHash256Value( null );
		}
		else {
			getJavaFXEditorTenantId().setDbKeyHash256Value( popObj.getRequiredTenantId() );
		}

		if( popObj == null ) {
			getJavaFXEditorId().setDbKeyHash256Value( null );
		}
		else {
			getJavaFXEditorId().setDbKeyHash256Value( popObj.getRequiredId() );
		}

		if( popObj == null ) {
			getJavaFXEditorDefSchemaTenantId().setDbKeyHash256Value( null );
		}
		else {
			getJavaFXEditorDefSchemaTenantId().setDbKeyHash256Value( popObj.getOptionalDefSchemaTenantId() );
		}

		if( popObj == null ) {
			getJavaFXEditorName().setStringValue( null );
		}
		else {
			getJavaFXEditorName().setStringValue( popObj.getRequiredName() );
		}

		if( popObj == null ) {
			getJavaFXEditorReplacesInherited().setBooleanValue( null );
		}
		else {
			getJavaFXEditorReplacesInherited().setBooleanValue( popObj.getRequiredReplacesInherited() );
		}

		if( popObj == null ) {
			getJavaFXEditorTweakGelText().setTextValue( null );
		}
		else {
			getJavaFXEditorTweakGelText().setTextValue( popObj.getRequiredTweakGelText() );
		}
	}

	public void postFields()
	{
		final String S_ProcName = "postFields";
		ICFBamSchemaTweakObj focus = getJavaFXFocusAsSchemaTweak();
		ICFBamSchemaTweakEditObj editObj;
		if( focus != null ) {
			editObj = (ICFBamSchemaTweakEditObj)(focus.getEdit());
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

		editObj.setRequiredTenantId( getJavaFXEditorTenantId().getDbKeyHash256Value() );

		editObj.setOptionalDefSchemaTenantId( getJavaFXEditorDefSchemaTenantId().getDbKeyHash256Value() );

		if( getJavaFXEditorName().getStringValue() == null ) {
			editObj.setRequiredName( "" );
		}
		else {
			editObj.setRequiredName( getJavaFXEditorName().getStringValue() );
		}

		editObj.setRequiredReplacesInherited( getJavaFXEditorReplacesInherited().getBooleanValue() );

		if( getJavaFXEditorTweakGelText().getTextValue() == null ) {
			editObj.setRequiredTweakGelText( "" );
		}
		else {
			editObj.setRequiredTweakGelText( getJavaFXEditorTweakGelText().getTextValue() );
		}
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		final String S_ProcName = "setPaneMode";
		CFPane.PaneMode oldValue = getPaneMode();
		if( oldValue == value ) {
			return;
		}
		ICFBamSchemaTweakObj focus = getJavaFXFocusAsSchemaTweak();
		if( ( value != CFPane.PaneMode.Unknown ) && ( value != CFPane.PaneMode.View ) ) {
			if( focus == null ) {
				throw new CFLibNullArgumentException( getClass(),
					S_ProcName,
					0,
					"javaFXFocus" );
			}
		}
		ICFBamSchemaTweakEditObj editObj;
		if( focus != null ) {
			editObj  = (ICFBamSchemaTweakEditObj)focus.getEdit();
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
								editObj = (ICFBamSchemaTweakEditObj)focus.beginEdit();
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
							editObj = (ICFBamSchemaTweakEditObj)focus.beginEdit();
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
							editObj = (ICFBamSchemaTweakEditObj)focus.beginEdit();
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
							editObj = (ICFBamSchemaTweakEditObj)focus.beginEdit();
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
						focus = (ICFBamSchemaTweakObj)editObj.create();
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
								editObj = (ICFBamSchemaTweakEditObj)focus.beginEdit();
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
								editObj = (ICFBamSchemaTweakEditObj)focus.beginEdit();
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
							editObj = (ICFBamSchemaTweakEditObj)focus.beginEdit();
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
			ICFBamSchemaTweakObj focus = getJavaFXFocusAsSchemaTweak();
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
		if( javafxEditorTenantId != null ) {
			javafxEditorTenantId.setDisable( ! isEditing );
		}
		if( javafxEditorId != null ) {
			javafxEditorId.setDisable( true );
		}
		if( javafxEditorDefSchemaTenantId != null ) {
			javafxEditorDefSchemaTenantId.setDisable( ! isEditing );
		}
		if( javafxEditorName != null ) {
			javafxEditorName.setDisable( ! isEditing );
		}
		if( javafxEditorReplacesInherited != null ) {
			javafxEditorReplacesInherited.setDisable( ! isEditing );
		}
		if( javafxEditorTweakGelText != null ) {
			javafxEditorTweakGelText.setDisable( ! isEditing );
		}
	}
}
