// Description: Java 25 JavaFX Picker of Obj Pane implementation for Table.

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
 *	CFBamJavaFXTablePickerPane JavaFX Pick Obj Pane implementation
 *	for Table.
 */
public class CFBamJavaFXTablePickerPane
extends CFBorderPane
implements ICFBamJavaFXTablePaneList
{
	public static String S_FormName = "Choose Table";
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected Collection<ICFBamTableObj> javafxDataCollection = null;
	protected ObservableList<ICFBamTableObj> observableListOfTable = null;
	protected TableColumn<ICFBamTableObj, CFLibDbKeyHash256> tableColumnId = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnName = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnDbName = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnShortName = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnLabel = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnShortDescription = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnDescription = null;
	protected TableColumn<ICFBamTableObj, Boolean> tableColumnPageData = null;
	protected TableColumn<ICFBamTableObj, String> tableColumnTableClassCode = null;
	protected TableColumn<ICFBamTableObj, Boolean> tableColumnIsInstantiable = null;
	protected TableColumn<ICFBamTableObj, Boolean> tableColumnHasHistory = null;
	protected TableColumn<ICFBamTableObj, Boolean> tableColumnHasAuditColumns = null;
	protected TableColumn<ICFBamTableObj, Boolean> tableColumnIsMutable = null;
	protected TableColumn<ICFBamTableObj, Boolean> tableColumnIsServerOnly = null;
	protected TableColumn<ICFBamTableObj, ICFBamSchema.LoaderBehaviourEnum> tableColumnLoaderBehaviour = null;
	protected TableColumn<ICFBamTableObj, ICFBamSchema.SecScopeEnum> tableColumnSecScope = null;
	protected TableColumn<ICFBamTableObj, ICFBamSchemaDefObj> tableColumnLookupDefSchema = null;
	protected TableColumn<ICFBamTableObj, ICFBamIndexObj> tableColumnLookupLookupIndex = null;
	protected TableColumn<ICFBamTableObj, ICFBamIndexObj> tableColumnLookupAltIndex = null;
	protected TableColumn<ICFBamTableObj, ICFBamTableObj> tableColumnLookupQualTable = null;
	protected TableColumn<ICFBamTableObj, ICFBamIndexObj> tableColumnLookupPrimaryIndex = null;
	protected TableView<ICFBamTableObj> dataTable = null;
	protected CFHBox hboxMenu = null;
	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected ICFBamJavaFXTableChosen invokeWhenChosen = null;
	protected ICFBamSchemaDefObj javafxContainer = null;
	protected CFButton buttonCancel = null;
	protected CFButton buttonChooseNone = null;
	protected CFButton buttonChooseSelected = null;
	protected ScrollPane scrollMenu = null;
	public CFBamJavaFXTablePickerPane( ICFFormManager formManager,
		ICFBamJavaFXSchema argSchema,
		ICFBamTableObj argFocus,
		ICFBamSchemaDefObj argContainer,
		Collection<ICFBamTableObj> argDataCollection,
		ICFBamJavaFXTableChosen whenChosen )
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
		dataTable = new TableView<ICFBamTableObj>();
		tableColumnId = new TableColumn<ICFBamTableObj,CFLibDbKeyHash256>( "Id" );
		tableColumnId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFBamTableObj, CFLibDbKeyHash256> p ) {
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
		tableColumnId.setCellFactory( new Callback<TableColumn<ICFBamTableObj,CFLibDbKeyHash256>,TableCell<ICFBamTableObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFBamTableObj,CFLibDbKeyHash256> call(
				TableColumn<ICFBamTableObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnId );
		tableColumnName = new TableColumn<ICFBamTableObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );
		tableColumnDbName = new TableColumn<ICFBamTableObj,String>( "Db Name" );
		tableColumnDbName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnDbName.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDbName );
		tableColumnShortName = new TableColumn<ICFBamTableObj,String>( "Short Name" );
		tableColumnShortName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnShortName.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortName );
		tableColumnLabel = new TableColumn<ICFBamTableObj,String>( "Label" );
		tableColumnLabel.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnLabel.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLabel );
		tableColumnShortDescription = new TableColumn<ICFBamTableObj,String>( "Short Description" );
		tableColumnShortDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnShortDescription.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnShortDescription );
		tableColumnDescription = new TableColumn<ICFBamTableObj,String>( "Description" );
		tableColumnDescription.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnDescription.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDescription );
		tableColumnPageData = new TableColumn<ICFBamTableObj,Boolean>( "PageData" );
		tableColumnPageData.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamTableObj, Boolean> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredPageData();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnPageData.setCellFactory( new Callback<TableColumn<ICFBamTableObj,Boolean>,TableCell<ICFBamTableObj,Boolean>>() {
			@Override public TableCell<ICFBamTableObj,Boolean> call(
				TableColumn<ICFBamTableObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnPageData );
		tableColumnTableClassCode = new TableColumn<ICFBamTableObj,String>( "Table Class Code" );
		tableColumnTableClassCode.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTableObj, String> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredTableClassCode();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnTableClassCode.setCellFactory( new Callback<TableColumn<ICFBamTableObj,String>,TableCell<ICFBamTableObj,String>>() {
			@Override public TableCell<ICFBamTableObj,String> call(
				TableColumn<ICFBamTableObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnTableClassCode );
		tableColumnIsInstantiable = new TableColumn<ICFBamTableObj,Boolean>( "Is Instantiable" );
		tableColumnIsInstantiable.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamTableObj, Boolean> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsInstantiable();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsInstantiable.setCellFactory( new Callback<TableColumn<ICFBamTableObj,Boolean>,TableCell<ICFBamTableObj,Boolean>>() {
			@Override public TableCell<ICFBamTableObj,Boolean> call(
				TableColumn<ICFBamTableObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsInstantiable );
		tableColumnHasHistory = new TableColumn<ICFBamTableObj,Boolean>( "Has History" );
		tableColumnHasHistory.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamTableObj, Boolean> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredHasHistory();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnHasHistory.setCellFactory( new Callback<TableColumn<ICFBamTableObj,Boolean>,TableCell<ICFBamTableObj,Boolean>>() {
			@Override public TableCell<ICFBamTableObj,Boolean> call(
				TableColumn<ICFBamTableObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnHasHistory );
		tableColumnHasAuditColumns = new TableColumn<ICFBamTableObj,Boolean>( "Has Audit Columns" );
		tableColumnHasAuditColumns.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamTableObj, Boolean> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredHasAuditColumns();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnHasAuditColumns.setCellFactory( new Callback<TableColumn<ICFBamTableObj,Boolean>,TableCell<ICFBamTableObj,Boolean>>() {
			@Override public TableCell<ICFBamTableObj,Boolean> call(
				TableColumn<ICFBamTableObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnHasAuditColumns );
		tableColumnIsMutable = new TableColumn<ICFBamTableObj,Boolean>( "Is Mutable" );
		tableColumnIsMutable.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamTableObj, Boolean> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsMutable();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsMutable.setCellFactory( new Callback<TableColumn<ICFBamTableObj,Boolean>,TableCell<ICFBamTableObj,Boolean>>() {
			@Override public TableCell<ICFBamTableObj,Boolean> call(
				TableColumn<ICFBamTableObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsMutable );
		tableColumnIsServerOnly = new TableColumn<ICFBamTableObj,Boolean>( "Is Server Table Only" );
		tableColumnIsServerOnly.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamTableObj, Boolean> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredIsServerOnly();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnIsServerOnly.setCellFactory( new Callback<TableColumn<ICFBamTableObj,Boolean>,TableCell<ICFBamTableObj,Boolean>>() {
			@Override public TableCell<ICFBamTableObj,Boolean> call(
				TableColumn<ICFBamTableObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnIsServerOnly );
		tableColumnLoaderBehaviour = new TableColumn<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum>( "Loader Behaviour" );
		tableColumnLoaderBehaviour.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum>,ObservableValue<ICFBamSchema.LoaderBehaviourEnum> >() {
			public ObservableValue<ICFBamSchema.LoaderBehaviourEnum> call( CellDataFeatures<ICFBamTableObj, ICFBamSchema.LoaderBehaviourEnum> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamSchema.LoaderBehaviourEnum value = obj.getRequiredLoaderBehaviour();
					ReadOnlyObjectWrapper<ICFBamSchema.LoaderBehaviourEnum> observable = new ReadOnlyObjectWrapper<ICFBamSchema.LoaderBehaviourEnum>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnLoaderBehaviour.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum>,TableCell<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum> call(
				TableColumn<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum> arg)
			{
				return new CFEnumTableCell<ICFBamTableObj,ICFBamSchema.LoaderBehaviourEnum>();
			}
		});
		dataTable.getColumns().add( tableColumnLoaderBehaviour );
		tableColumnSecScope = new TableColumn<ICFBamTableObj,ICFBamSchema.SecScopeEnum>( "Security Scope" );
		tableColumnSecScope.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamSchema.SecScopeEnum>,ObservableValue<ICFBamSchema.SecScopeEnum> >() {
			public ObservableValue<ICFBamSchema.SecScopeEnum> call( CellDataFeatures<ICFBamTableObj, ICFBamSchema.SecScopeEnum> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamSchema.SecScopeEnum value = obj.getRequiredSecScope();
					ReadOnlyObjectWrapper<ICFBamSchema.SecScopeEnum> observable = new ReadOnlyObjectWrapper<ICFBamSchema.SecScopeEnum>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnSecScope.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamSchema.SecScopeEnum>,TableCell<ICFBamTableObj,ICFBamSchema.SecScopeEnum>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamSchema.SecScopeEnum> call(
				TableColumn<ICFBamTableObj,ICFBamSchema.SecScopeEnum> arg)
			{
				return new CFEnumTableCell<ICFBamTableObj,ICFBamSchema.SecScopeEnum>();
			}
		});
		dataTable.getColumns().add( tableColumnSecScope );
		tableColumnLookupDefSchema = new TableColumn<ICFBamTableObj, ICFBamSchemaDefObj>( "Defining Schema Definition" );
		tableColumnLookupDefSchema.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamSchemaDefObj>,ObservableValue<ICFBamSchemaDefObj> >() {
			public ObservableValue<ICFBamSchemaDefObj> call( CellDataFeatures<ICFBamTableObj, ICFBamSchemaDefObj> p ) {
				ICFBamTableObj obj = p.getValue();
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
		tableColumnLookupDefSchema.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamSchemaDefObj>,TableCell<ICFBamTableObj,ICFBamSchemaDefObj>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamSchemaDefObj> call(
				TableColumn<ICFBamTableObj,ICFBamSchemaDefObj> arg)
			{
				return new CFReferenceTableCell<ICFBamTableObj,ICFBamSchemaDefObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupDefSchema );
		tableColumnLookupLookupIndex = new TableColumn<ICFBamTableObj, ICFBamIndexObj>( "Lookup Index" );
		tableColumnLookupLookupIndex.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamIndexObj>,ObservableValue<ICFBamIndexObj> >() {
			public ObservableValue<ICFBamIndexObj> call( CellDataFeatures<ICFBamTableObj, ICFBamIndexObj> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamIndexObj ref = obj.getOptionalLookupLookupIndex();
					ReadOnlyObjectWrapper<ICFBamIndexObj> observable = new ReadOnlyObjectWrapper<ICFBamIndexObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupLookupIndex.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamIndexObj>,TableCell<ICFBamTableObj,ICFBamIndexObj>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamIndexObj> call(
				TableColumn<ICFBamTableObj,ICFBamIndexObj> arg)
			{
				return new CFReferenceTableCell<ICFBamTableObj,ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupLookupIndex );
		tableColumnLookupAltIndex = new TableColumn<ICFBamTableObj, ICFBamIndexObj>( "Alt Index" );
		tableColumnLookupAltIndex.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamIndexObj>,ObservableValue<ICFBamIndexObj> >() {
			public ObservableValue<ICFBamIndexObj> call( CellDataFeatures<ICFBamTableObj, ICFBamIndexObj> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamIndexObj ref = obj.getOptionalLookupAltIndex();
					ReadOnlyObjectWrapper<ICFBamIndexObj> observable = new ReadOnlyObjectWrapper<ICFBamIndexObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupAltIndex.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamIndexObj>,TableCell<ICFBamTableObj,ICFBamIndexObj>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamIndexObj> call(
				TableColumn<ICFBamTableObj,ICFBamIndexObj> arg)
			{
				return new CFReferenceTableCell<ICFBamTableObj,ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupAltIndex );
		tableColumnLookupQualTable = new TableColumn<ICFBamTableObj, ICFBamTableObj>( "Qualifying Table" );
		tableColumnLookupQualTable.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamTableObj>,ObservableValue<ICFBamTableObj> >() {
			public ObservableValue<ICFBamTableObj> call( CellDataFeatures<ICFBamTableObj, ICFBamTableObj> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamTableObj ref = obj.getOptionalLookupQualTable();
					ReadOnlyObjectWrapper<ICFBamTableObj> observable = new ReadOnlyObjectWrapper<ICFBamTableObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupQualTable.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamTableObj>,TableCell<ICFBamTableObj,ICFBamTableObj>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamTableObj> call(
				TableColumn<ICFBamTableObj,ICFBamTableObj> arg)
			{
				return new CFReferenceTableCell<ICFBamTableObj,ICFBamTableObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupQualTable );
		tableColumnLookupPrimaryIndex = new TableColumn<ICFBamTableObj, ICFBamIndexObj>( "Primary Index" );
		tableColumnLookupPrimaryIndex.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTableObj,ICFBamIndexObj>,ObservableValue<ICFBamIndexObj> >() {
			public ObservableValue<ICFBamIndexObj> call( CellDataFeatures<ICFBamTableObj, ICFBamIndexObj> p ) {
				ICFBamTableObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					ICFBamIndexObj ref = obj.getOptionalLookupPrimaryIndex();
					ReadOnlyObjectWrapper<ICFBamIndexObj> observable = new ReadOnlyObjectWrapper<ICFBamIndexObj>();
					observable.setValue( ref );
					return( observable );
				}
			}
		});
		tableColumnLookupPrimaryIndex.setCellFactory( new Callback<TableColumn<ICFBamTableObj,ICFBamIndexObj>,TableCell<ICFBamTableObj,ICFBamIndexObj>>() {
			@Override public TableCell<ICFBamTableObj,ICFBamIndexObj> call(
				TableColumn<ICFBamTableObj,ICFBamIndexObj> arg)
			{
				return new CFReferenceTableCell<ICFBamTableObj,ICFBamIndexObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupPrimaryIndex );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFBamTableObj>() {
				@Override public void changed( ObservableValue<? extends ICFBamTableObj> observable,
					ICFBamTableObj oldValue,
					ICFBamTableObj newValue )
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
					invokeWhenChosen.choseTable( null );
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
					ICFBamTableObj selectedInstance = getJavaFXFocusAsTable();
					invokeWhenChosen.choseTable( selectedInstance );
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
		if( dataTable == null ) {
			return;
		}
	}

	public ICFBamTableObj getJavaFXFocusAsTable() {
		return( (ICFBamTableObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsTable( ICFBamTableObj value ) {
		setJavaFXFocus( value );
	}

	public class TableByQualNameComparator
	implements Comparator<ICFBamTableObj>
	{
		public TableByQualNameComparator() {
		}

		public int compare( ICFBamTableObj lhs, ICFBamTableObj rhs ) {
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

	protected TableByQualNameComparator compareTableByQualName = new TableByQualNameComparator();

	public Collection<ICFBamTableObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFBamTableObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfTable = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFBamTableObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfTable.add( iter.next() );
				}
				observableListOfTable.sort( compareTableByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfTable );
			// Hack from stackoverflow to fix JavaFX TableView refresh issue
			((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
			((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
		}
	}

	public ICFBamSchemaDefObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFBamSchemaDefObj value ) {
		javafxContainer = value;
	}

	public void adjustListButtons() {
		boolean enableState;
		ICFBamTableObj selectedObj = getJavaFXFocusAsTable();
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

