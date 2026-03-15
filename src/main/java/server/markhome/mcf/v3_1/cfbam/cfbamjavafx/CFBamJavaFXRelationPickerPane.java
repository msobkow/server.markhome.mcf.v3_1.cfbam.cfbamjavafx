// Description: Java 25 JavaFX Picker of Obj Pane implementation for Relation.

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
import java.util.List;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;
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
 *	CFBamJavaFXRelationPickerPane JavaFX Pick Obj Pane implementation
 *	for Relation.
 */
public class CFBamJavaFXRelationPickerPane
extends CFBorderPane
implements ICFBamJavaFXRelationPaneList
{
	public static String S_FormName = "Choose Relation";
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected Collection<ICFBamRelationObj> javafxDataCollection = null;
	protected ObservableList<ICFBamRelationObj> observableListOfRelation = null;
	protected TableColumn<ICFBamRelationObj, CFLibDbKeyHash256> tableColumnId = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnName = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnShortName = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnLabel = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnShortDescription = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnDescription = null;
	protected TableColumn<ICFBamRelationObj, ICFBamSchema.RelationTypeEnum> tableColumnRelationType = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnDbName = null;
	protected TableColumn<ICFBamRelationObj, String> tableColumnSuffix = null;
	protected TableColumn<ICFBamRelationObj, Boolean> tableColumnIsRequired = null;
	protected TableColumn<ICFBamRelationObj, Boolean> tableColumnIsXsdContainer = null;
	protected TableColumn<ICFBamRelationObj, Boolean> tableColumnIsLateResolver = null;
	protected TableColumn<ICFBamRelationObj, Boolean> tableColumnAllowAddendum = null;
	protected TableColumn<ICFBamRelationObj, ICFBamSchemaDefObj> tableColumnLookupDefSchema = null;
	protected TableColumn<ICFBamRelationObj, ICFBamIndexObj> tableColumnLookupFromIndex = null;
	protected TableColumn<ICFBamRelationObj, ICFBamTableObj> tableColumnLookupToTable = null;
	protected TableColumn<ICFBamRelationObj, ICFBamIndexObj> tableColumnLookupToIndex = null;
	protected TableColumn<ICFBamRelationObj, ICFBamRelationObj> tableColumnLookupNarrowed = null;
	protected TableView<ICFBamRelationObj> dataTable = null;
	protected CFHBox hboxMenu = null;
	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected ICFBamJavaFXRelationChosen invokeWhenChosen = null;
	protected ICFBamTableObj javafxContainer = null;
	protected CFButton buttonCancel = null;
	protected CFButton buttonChooseNone = null;
	protected CFButton buttonChooseSelected = null;
	protected ScrollPane scrollMenu = null;
	public CFBamJavaFXRelationPickerPane( ICFFormManager formManager,
		ICFBamJavaFXSchema argSchema,
		ICFBamRelationObj argFocus,
		ICFBamTableObj argContainer,
		Collection<ICFBamRelationObj> argDataCollection,
		ICFBamJavaFXRelationChosen whenChosen )
	{
		super();
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
		if( whenChosen == null ) {
			throw new CFLibNullArgumentException( getClass(),
				S_ProcName,
				6,
				"whenChosen" );
		}
		invokeWhenChosen = whenChosen;
		// argFocus is optional; focus may be set later during execution as
		// conditions of the runtime change.
		javafxSchema = argSchema;
		javaFXFocus = argFocus;
		javafxContainer = argContainer;
		setJavaFXDataCollection( argDataCollection );
		dataTable = new TableView<ICFBamRelationObj>();
		tableColumnId = new TableColumn<ICFBamRelationObj,CFLibDbKeyHash256>( "Id" );
		tableColumnId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFBamRelationObj, CFLibDbKeyHash256> p ) {
				ICFBamScopeObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibDbKeyHash256 value = obj.getRequiredId();
					ReadOnlyObjectWrapper<CFLibDbKeyHash256> observable = new ReadOnlyObjectWrapper<CFLibDbKeyHash256>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnId.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,CFLibDbKeyHash256>,TableCell<ICFBamRelationObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFBamRelationObj,CFLibDbKeyHash256> call(
				TableColumn<ICFBamRelationObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnId );
		tableColumnName = new TableColumn<ICFBamRelationObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );
		tableColumnShortName = new TableColumn<ICFBamRelationObj,String>( "Short Name" );
		tableColumnShortName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalShortName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnShortName.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortName );
		tableColumnLabel = new TableColumn<ICFBamRelationObj,String>( "Label" );
		tableColumnLabel.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalLabel();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnLabel.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLabel );
		tableColumnShortDescription = new TableColumn<ICFBamRelationObj,String>( "Short Description" );
		tableColumnShortDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalShortDescription();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnShortDescription.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortDescription );
		tableColumnDescription = new TableColumn<ICFBamRelationObj,String>( "Description" );
		tableColumnDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalDescription();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnDescription.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDescription );
		tableColumnRelationType = new TableColumn<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum>( "RelationType" );
		tableColumnRelationType.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum>,ObservableValue<ICFBamSchema.RelationTypeEnum> >() {
			public ObservableValue<ICFBamSchema.RelationTypeEnum> call( CellDataFeatures<ICFBamRelationObj, ICFBamSchema.RelationTypeEnum> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamSchema.RelationTypeEnum value = obj.getRequiredRelationType();
					ReadOnlyObjectWrapper<ICFBamSchema.RelationTypeEnum> observable = new ReadOnlyObjectWrapper<ICFBamSchema.RelationTypeEnum>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnRelationType.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum>,TableCell<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum>>() {
			@Override public TableCell<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum> call(
				TableColumn<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum> arg)
			{
				return new CFEnumTableCell<ICFBamRelationObj,ICFBamSchema.RelationTypeEnum>();
			}
		});
		dataTable.getColumns().add( tableColumnRelationType );
		tableColumnDbName = new TableColumn<ICFBamRelationObj,String>( "Db Name" );
		tableColumnDbName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalDbName();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnDbName.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDbName );
		tableColumnSuffix = new TableColumn<ICFBamRelationObj,String>( "Suffix" );
		tableColumnSuffix.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamRelationObj, String> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getOptionalSuffix();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSuffix.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,String>,TableCell<ICFBamRelationObj,String>>() {
			@Override public TableCell<ICFBamRelationObj,String> call(
				TableColumn<ICFBamRelationObj,String> arg)
			{
				return new CFStringTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnSuffix );
		tableColumnIsRequired = new TableColumn<ICFBamRelationObj,Boolean>( "Is Required" );
		tableColumnIsRequired.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamRelationObj, Boolean> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsRequired();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsRequired.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,Boolean>,TableCell<ICFBamRelationObj,Boolean>>() {
			@Override public TableCell<ICFBamRelationObj,Boolean> call(
				TableColumn<ICFBamRelationObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsRequired );
		tableColumnIsXsdContainer = new TableColumn<ICFBamRelationObj,Boolean>( "Is XSD Container" );
		tableColumnIsXsdContainer.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamRelationObj, Boolean> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsXsdContainer();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsXsdContainer.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,Boolean>,TableCell<ICFBamRelationObj,Boolean>>() {
			@Override public TableCell<ICFBamRelationObj,Boolean> call(
				TableColumn<ICFBamRelationObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsXsdContainer );
		tableColumnIsLateResolver = new TableColumn<ICFBamRelationObj,Boolean>( "IsLateResolver" );
		tableColumnIsLateResolver.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamRelationObj, Boolean> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsLateResolver();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsLateResolver.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,Boolean>,TableCell<ICFBamRelationObj,Boolean>>() {
			@Override public TableCell<ICFBamRelationObj,Boolean> call(
				TableColumn<ICFBamRelationObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsLateResolver );
		tableColumnAllowAddendum = new TableColumn<ICFBamRelationObj,Boolean>( "AllowAddendum" );
		tableColumnAllowAddendum.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamRelationObj, Boolean> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredAllowAddendum();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnAllowAddendum.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,Boolean>,TableCell<ICFBamRelationObj,Boolean>>() {
			@Override public TableCell<ICFBamRelationObj,Boolean> call(
				TableColumn<ICFBamRelationObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnAllowAddendum );
		tableColumnLookupDefSchema = new TableColumn<ICFBamRelationObj, ICFBamSchemaDefObj>( "Defining Schema Definition" );
		tableColumnLookupDefSchema.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,ICFBamSchemaDefObj>,ObservableValue<ICFBamSchemaDefObj> >() {
			public ObservableValue<ICFBamSchemaDefObj> call( CellDataFeatures<ICFBamRelationObj, ICFBamSchemaDefObj> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamSchemaDefObj ref = obj.getOptionalLookupDefSchema();
					ReadOnlyObjectWrapper<ICFBamSchemaDefObj> observable = new ReadOnlyObjectWrapper<ICFBamSchemaDefObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupDefSchema.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,ICFBamSchemaDefObj>,TableCell<ICFBamRelationObj,ICFBamSchemaDefObj>>() {
			@Override public TableCell<ICFBamRelationObj,ICFBamSchemaDefObj> call(
				TableColumn<ICFBamRelationObj,ICFBamSchemaDefObj> arg)
			{
				return new CFReferenceTableCell<ICFBamRelationObj,ICFBamSchemaDefObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupDefSchema );
		tableColumnLookupFromIndex = new TableColumn<ICFBamRelationObj, ICFBamIndexObj>( "From Index" );
		tableColumnLookupFromIndex.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,ICFBamIndexObj>,ObservableValue<ICFBamIndexObj> >() {
			public ObservableValue<ICFBamIndexObj> call( CellDataFeatures<ICFBamRelationObj, ICFBamIndexObj> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamIndexObj ref = obj.getRequiredLookupFromIndex();
					ReadOnlyObjectWrapper<ICFBamIndexObj> observable = new ReadOnlyObjectWrapper<ICFBamIndexObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupFromIndex.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,ICFBamIndexObj>,TableCell<ICFBamRelationObj,ICFBamIndexObj>>() {
			@Override public TableCell<ICFBamRelationObj,ICFBamIndexObj> call(
				TableColumn<ICFBamRelationObj,ICFBamIndexObj> arg)
			{
				return new CFReferenceTableCell<ICFBamRelationObj,ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupFromIndex );
		tableColumnLookupToTable = new TableColumn<ICFBamRelationObj, ICFBamTableObj>( "To Table" );
		tableColumnLookupToTable.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,ICFBamTableObj>,ObservableValue<ICFBamTableObj> >() {
			public ObservableValue<ICFBamTableObj> call( CellDataFeatures<ICFBamRelationObj, ICFBamTableObj> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamTableObj ref = obj.getRequiredLookupToTable();
					ReadOnlyObjectWrapper<ICFBamTableObj> observable = new ReadOnlyObjectWrapper<ICFBamTableObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupToTable.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,ICFBamTableObj>,TableCell<ICFBamRelationObj,ICFBamTableObj>>() {
			@Override public TableCell<ICFBamRelationObj,ICFBamTableObj> call(
				TableColumn<ICFBamRelationObj,ICFBamTableObj> arg)
			{
				return new CFReferenceTableCell<ICFBamRelationObj,ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupToTable );
		tableColumnLookupToIndex = new TableColumn<ICFBamRelationObj, ICFBamIndexObj>( "To Index" );
		tableColumnLookupToIndex.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,ICFBamIndexObj>,ObservableValue<ICFBamIndexObj> >() {
			public ObservableValue<ICFBamIndexObj> call( CellDataFeatures<ICFBamRelationObj, ICFBamIndexObj> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamIndexObj ref = obj.getRequiredLookupToIndex();
					ReadOnlyObjectWrapper<ICFBamIndexObj> observable = new ReadOnlyObjectWrapper<ICFBamIndexObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupToIndex.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,ICFBamIndexObj>,TableCell<ICFBamRelationObj,ICFBamIndexObj>>() {
			@Override public TableCell<ICFBamRelationObj,ICFBamIndexObj> call(
				TableColumn<ICFBamRelationObj,ICFBamIndexObj> arg)
			{
				return new CFReferenceTableCell<ICFBamRelationObj,ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupToIndex );
		tableColumnLookupNarrowed = new TableColumn<ICFBamRelationObj, ICFBamRelationObj>( "Narrowed Relation" );
		tableColumnLookupNarrowed.setCellValueFactory( new Callback<CellDataFeatures<ICFBamRelationObj,ICFBamRelationObj>,ObservableValue<ICFBamRelationObj> >() {
			public ObservableValue<ICFBamRelationObj> call( CellDataFeatures<ICFBamRelationObj, ICFBamRelationObj> p ) {
				ICFBamRelationObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamRelationObj ref = obj.getOptionalLookupNarrowed();
					ReadOnlyObjectWrapper<ICFBamRelationObj> observable = new ReadOnlyObjectWrapper<ICFBamRelationObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupNarrowed.setCellFactory( new Callback<TableColumn<ICFBamRelationObj,ICFBamRelationObj>,TableCell<ICFBamRelationObj,ICFBamRelationObj>>() {
			@Override public TableCell<ICFBamRelationObj,ICFBamRelationObj> call(
				TableColumn<ICFBamRelationObj,ICFBamRelationObj> arg)
			{
				return new CFReferenceTableCell<ICFBamRelationObj,ICFBamRelationObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupNarrowed );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFBamRelationObj>() {
				@Override public void changed( ObservableValue<? extends ICFBamRelationObj> observable,
					ICFBamRelationObj oldValue,
					ICFBamRelationObj newValue )
				{
					setJavaFXFocus( newValue );
					if( buttonChooseSelected != null ) {
						if( newValue != null ) {
							buttonChooseSelected.setDisable( false );
						}
						else {
							buttonChooseSelected.setDisable( true );
						}
					}
				}
			});
		hboxMenu = new CFHBox( 10 );
		buttonCancel = new CFButton();
		buttonCancel.setMinWidth( 200 );
		buttonCancel.setText( "Cancel" );
		buttonCancel.setOnAction( new EventHandler<ActionEvent>() {
			@Override public void handle( ActionEvent e ) {
				final String S_ProcName = "handle";
				try {
					cfFormManager.closeCurrentForm();
				}
				catch( Throwable t ) {
					CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
				}
			}
		});
		hboxMenu.getChildren().add( buttonCancel );
		buttonChooseNone = new CFButton();
		buttonChooseNone.setMinWidth( 200 );
		buttonChooseNone.setText( "ChooseNone" );
		buttonChooseNone.setOnAction( new EventHandler<ActionEvent>() {
			@Override public void handle( ActionEvent e ) {
				final String S_ProcName = "handle";
				try {
					ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
					if( schemaObj == null ) {
						throw new CFLibNullArgumentException( getClass(),
							S_ProcName,
							0,
							"schemaObj" );
					}
					invokeWhenChosen.choseRelation( null );
					cfFormManager.closeCurrentForm();
				}
				catch( Throwable t ) {
					CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
				}
			}
		});
		hboxMenu.getChildren().add( buttonChooseNone );
		buttonChooseSelected = new CFButton();
		buttonChooseSelected.setMinWidth( 200 );
		buttonChooseSelected.setText( "ChooseSelected" );
		buttonChooseSelected.setOnAction( new EventHandler<ActionEvent>() {
			@Override public void handle( ActionEvent e ) {
				final String S_ProcName = "handle";
				try {
					ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
					if( schemaObj == null ) {
						throw new CFLibNullArgumentException( getClass(),
							S_ProcName,
							0,
							"schemaObj" );
					}
					ICFBamRelationObj selectedInstance = getJavaFXFocusAsRelation();
					invokeWhenChosen.choseRelation( selectedInstance );
					cfFormManager.closeCurrentForm();
				}
				catch( Throwable t ) {
					CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
				}
			}
		});
		hboxMenu.getChildren().add( buttonChooseSelected );
		if( argFocus != null ) {
			dataTable.getSelectionModel().select( argFocus );
		}

		scrollMenu = new ScrollPane();
		scrollMenu.setVbarPolicy( ScrollBarPolicy.NEVER );
		scrollMenu.setHbarPolicy( ScrollBarPolicy.AS_NEEDED );
		scrollMenu.setFitToHeight( true );
		scrollMenu.setContent( hboxMenu );

		setTop( scrollMenu );
		setCenter( dataTable );
		adjustListButtons();
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
		if( dataTable == null ) {
			return;
		}
	}

	public ICFBamRelationObj getJavaFXFocusAsRelation() {
		return( (ICFBamRelationObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsRelation( ICFBamRelationObj value ) {
		setJavaFXFocus( value );
	}

	public class RelationByQualNameComparator
	implements Comparator<ICFBamRelationObj>
	{
		public RelationByQualNameComparator() {
		}

		public int compare( ICFBamRelationObj lhs, ICFBamRelationObj rhs ) {
			if( lhs == null ) {
				if( rhs == null ) {
					return( 0 );
				}
				else {
					return( -1 );
				}
			}
			else if( rhs == null ) {
				return( 1 );
			}
			else {
				String lhsValue = lhs.getObjQualifiedName();
				String rhsValue = rhs.getObjQualifiedName();
				if( lhsValue == null ) {
					if( rhsValue == null ) {
						return( 0 );
					}
					else {
						return( -1 );
					}
				}
				else if( rhsValue == null ) {
					return( 1 );
				}
				else {
					return( lhsValue.compareTo( rhsValue ) );
				}
			}
		}
	}

	protected RelationByQualNameComparator compareRelationByQualName = new RelationByQualNameComparator();

	public Collection<ICFBamRelationObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFBamRelationObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfRelation = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFBamRelationObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfRelation.add( iter.next() );
				}
				observableListOfRelation.sort( compareRelationByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfRelation );
			// Hack from stackoverflow to fix JavaFX TableView refresh issue
			((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
			((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
		}
	}

	public ICFBamTableObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFBamTableObj value ) {
		javafxContainer = value;
	}

	public void adjustListButtons() {
		boolean enableState;
		ICFBamRelationObj selectedObj = getJavaFXFocusAsRelation();
		if( selectedObj == null ) {
			enableState = false;
		}
		else {
			enableState = true;
		}

		if( buttonChooseSelected != null ) {
			buttonChooseSelected.setDisable( ! enableState );
		}
		if( buttonChooseNone != null ) {
			buttonChooseNone.setDisable( false );
		}
		if( buttonCancel != null ) {
			buttonCancel.setDisable( false );
		}

	}
}

