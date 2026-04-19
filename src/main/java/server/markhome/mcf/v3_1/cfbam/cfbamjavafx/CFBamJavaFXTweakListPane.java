// Description: Java 25 JavaFX List of Obj Pane implementation for Tweak.

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
import java.util.*;
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
 *	CFBamJavaFXTweakListPane JavaFX List of Obj Pane implementation
 *	for Tweak.
 */
public class CFBamJavaFXTweakListPane
extends CFBorderPane
implements ICFBamJavaFXTweakPaneList
{
	public static String S_FormName = "List Tweak";
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected Collection<ICFBamTweakObj> javafxDataCollection = null;
	protected ObservableList<ICFBamTweakObj> observableListOfTweak = null;
	protected ScrollPane scrollMenu = null;
	protected CFHBox hboxMenu = null;
	protected ScrollPane scrollMenuAdd = null;
	protected CFVBox vboxMenuAdd = null;
	protected CFButton buttonAdd = null;
	protected CFButton buttonCancelAdd = null;
	protected CFButton buttonAddTableTweak = null;
	protected CFButton buttonAddSchemaTweak = null;
	protected CFButton buttonAddIndexTweak = null;
	protected CFButton buttonViewSelected = null;
	protected CFButton buttonEditSelected = null;
	protected CFButton buttonDeleteSelected = null;
	protected TableView<ICFBamTweakObj> dataTable = null;
	protected TableColumn<ICFBamTweakObj,String> tableColumnObjKind = null;
	protected TableColumn<ICFBamTweakObj, CFLibDbKeyHash256> tableColumnTenantId = null;
	protected TableColumn<ICFBamTweakObj, CFLibDbKeyHash256> tableColumnId = null;
	protected TableColumn<ICFBamTweakObj, CFLibDbKeyHash256> tableColumnDefSchemaTenantId = null;
	protected TableColumn<ICFBamTweakObj, String> tableColumnName = null;
	protected TableColumn<ICFBamTweakObj, Boolean> tableColumnReplacesInherited = null;
	protected TableColumn<ICFBamTweakObj, String> tableColumnTweakGelText = null;
	protected TableColumn<ICFBamTweakObj, ICFBamSchemaDefObj> tableColumnLookupDefSchema = null;

	public final String S_ColumnNames[] = { "Name" };
	protected ICFFormManager cfFormManager = null;
	protected boolean javafxIsInitializing = true;
	protected boolean javafxSortByChain = false;
	protected ICFBamScopeObj javafxContainer = null;
	protected ICFRefreshCallback javafxRefreshCallback = null;
	class ViewEditClosedCallback implements ICFFormClosedCallback {
		public ViewEditClosedCallback() {
		}

		@Override
		public void formClosed( ICFLibAnyObj affectedObject ) {
			if( affectedObject != null ) {
				refreshMe();
			}
		}
	}

	protected ViewEditClosedCallback viewEditClosedCallback = null;

	public ICFFormClosedCallback getViewEditClosedCallback() {
		if( viewEditClosedCallback == null ) {
			viewEditClosedCallback = new ViewEditClosedCallback();
		}
		return( viewEditClosedCallback );
	}

	class DeleteCallback implements ICFDeleteCallback {
		public DeleteCallback() {
		}
		@Override
		public void deleted( ICFLibAnyObj deletedObject ) {
			if( deletedObject != null ) {
				refreshMe();
			}
		}

		@Override
		public void formClosed( ICFLibAnyObj affectedObject ) {
			if( affectedObject != null ) {
				refreshMe();
			}
		}
	}

	protected DeleteCallback deleteCallback = null;

	public ICFDeleteCallback getDeleteCallback() {
		if( deleteCallback == null ) {
			deleteCallback = new DeleteCallback();
		}
		return( deleteCallback );
	}


	public CFBamJavaFXTweakListPane( ICFFormManager formManager,
		ICFBamJavaFXSchema argSchema,
		ICFBamScopeObj argContainer,
		ICFBamTweakObj argFocus,
		Collection<ICFBamTweakObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
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
		// argFocus is optional; focus may be set later during execution as
		// conditions of the runtime change.
		javafxSchema = argSchema;
		javaFXFocus = argFocus;
		javafxContainer = argContainer;
		javafxRefreshCallback = refreshCallback;
		javafxSortByChain = sortByChain;
		setJavaFXDataCollection( argDataCollection );
		dataTable = new TableView<ICFBamTweakObj>();
		tableColumnObjKind = new TableColumn<ICFBamTweakObj,String>( "Class Code" );
		tableColumnObjKind.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTweakObj,String>,ObservableValue<String> >() {
			@Override public ObservableValue<String> call( CellDataFeatures<ICFBamTweakObj, String> p ) {
				ICFBamTweakObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					int classCode = obj.getClassCode();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( Integer.toString(classCode) );
					return( observable );
				}
			}
		});
		tableColumnObjKind.setCellFactory( new Callback<TableColumn<ICFBamTweakObj,String>,TableCell<ICFBamTweakObj,String>>() {
			@Override public TableCell<ICFBamTweakObj,String> call(
				TableColumn<ICFBamTweakObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTweakObj>();
			}
		});
		dataTable.getColumns().add( tableColumnObjKind );
		tableColumnTenantId = new TableColumn<ICFBamTweakObj,CFLibDbKeyHash256>( "Tenant Id" );
		tableColumnTenantId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTweakObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFBamTweakObj, CFLibDbKeyHash256> p ) {
				ICFBamTweakObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibDbKeyHash256 value = obj.getRequiredTenantId();
					ReadOnlyObjectWrapper<CFLibDbKeyHash256> observable = new ReadOnlyObjectWrapper<CFLibDbKeyHash256>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnTenantId.setCellFactory( new Callback<TableColumn<ICFBamTweakObj,CFLibDbKeyHash256>,TableCell<ICFBamTweakObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFBamTweakObj,CFLibDbKeyHash256> call(
				TableColumn<ICFBamTweakObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFBamTweakObj>();
			}
		});
		dataTable.getColumns().add( tableColumnTenantId );
		tableColumnId = new TableColumn<ICFBamTweakObj,CFLibDbKeyHash256>( "Id" );
		tableColumnId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTweakObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFBamTweakObj, CFLibDbKeyHash256> p ) {
				ICFBamTweakObj obj = p.getValue();
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
		tableColumnId.setCellFactory( new Callback<TableColumn<ICFBamTweakObj,CFLibDbKeyHash256>,TableCell<ICFBamTweakObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFBamTweakObj,CFLibDbKeyHash256> call(
				TableColumn<ICFBamTweakObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFBamTweakObj>();
			}
		});
		dataTable.getColumns().add( tableColumnId );
		tableColumnDefSchemaTenantId = new TableColumn<ICFBamTweakObj,CFLibDbKeyHash256>( "Defining Schema Tenant Id" );
		tableColumnDefSchemaTenantId.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTweakObj,CFLibDbKeyHash256>,ObservableValue<CFLibDbKeyHash256> >() {
			public ObservableValue<CFLibDbKeyHash256> call( CellDataFeatures<ICFBamTweakObj, CFLibDbKeyHash256> p ) {
				ICFBamTweakObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					CFLibDbKeyHash256 value = obj.getOptionalDefSchemaTenantId();
					ReadOnlyObjectWrapper<CFLibDbKeyHash256> observable = new ReadOnlyObjectWrapper<CFLibDbKeyHash256>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnDefSchemaTenantId.setCellFactory( new Callback<TableColumn<ICFBamTweakObj,CFLibDbKeyHash256>,TableCell<ICFBamTweakObj,CFLibDbKeyHash256>>() {
			@Override public TableCell<ICFBamTweakObj,CFLibDbKeyHash256> call(
				TableColumn<ICFBamTweakObj,CFLibDbKeyHash256> arg)
			{
				return new CFDbKeyHash256TableCell<ICFBamTweakObj>();
			}
		});
		dataTable.getColumns().add( tableColumnDefSchemaTenantId );
		tableColumnName = new TableColumn<ICFBamTweakObj,String>( "Name" );
		tableColumnName.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTweakObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTweakObj, String> p ) {
				ICFBamTweakObj obj = p.getValue();
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
		tableColumnName.setCellFactory( new Callback<TableColumn<ICFBamTweakObj,String>,TableCell<ICFBamTweakObj,String>>() {
			@Override public TableCell<ICFBamTweakObj,String> call(
				TableColumn<ICFBamTweakObj,String> arg)
			{
				return new CFStringTableCell<ICFBamTweakObj>();
			}
		});
		dataTable.getColumns().add( tableColumnName );
		tableColumnReplacesInherited = new TableColumn<ICFBamTweakObj,Boolean>( "Replaces Inherited" );
		tableColumnReplacesInherited.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTweakObj,Boolean>,ObservableValue<Boolean> >() {
			public ObservableValue<Boolean> call( CellDataFeatures<ICFBamTweakObj, Boolean> p ) {
				ICFBamTweakObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					boolean value = obj.getRequiredReplacesInherited();
					Boolean wrapped = Boolean.valueOf( value );
					ReadOnlyObjectWrapper<Boolean> observable = new ReadOnlyObjectWrapper<Boolean>();
					observable.setValue( wrapped );
					return( observable );
				}
			}
		});
		tableColumnReplacesInherited.setCellFactory( new Callback<TableColumn<ICFBamTweakObj,Boolean>,TableCell<ICFBamTweakObj,Boolean>>() {
			@Override public TableCell<ICFBamTweakObj,Boolean> call(
				TableColumn<ICFBamTweakObj,Boolean> arg)
			{
				return new CFBoolTableCell<ICFBamTweakObj>();
			}
		});
		dataTable.getColumns().add( tableColumnReplacesInherited );
		tableColumnTweakGelText = new TableColumn<ICFBamTweakObj,String>( "GEL Text" );
		tableColumnTweakGelText.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTweakObj,String>,ObservableValue<String> >() {
			public ObservableValue<String> call( CellDataFeatures<ICFBamTweakObj, String> p ) {
				ICFBamTweakObj obj = p.getValue();
				if( obj == null ) {
					return( null );
				}
				else {
					String value = obj.getRequiredTweakGelText();
					ReadOnlyObjectWrapper<String> observable = new ReadOnlyObjectWrapper<String>();
					observable.setValue( value );
					return( observable );
				}
			}
		});
		tableColumnTweakGelText.setCellFactory( new Callback<TableColumn<ICFBamTweakObj,String>,TableCell<ICFBamTweakObj,String>>() {
			@Override public TableCell<ICFBamTweakObj,String> call(
				TableColumn<ICFBamTweakObj,String> arg)
			{
				return new CFTextTableCell<ICFBamTweakObj>();
			}
		});
		dataTable.getColumns().add( tableColumnTweakGelText );
		tableColumnLookupDefSchema = new TableColumn<ICFBamTweakObj, ICFBamSchemaDefObj>( "Defining Schema Definition" );
		tableColumnLookupDefSchema.setCellValueFactory( new Callback<CellDataFeatures<ICFBamTweakObj,ICFBamSchemaDefObj>,ObservableValue<ICFBamSchemaDefObj> >() {
			public ObservableValue<ICFBamSchemaDefObj> call( CellDataFeatures<ICFBamTweakObj, ICFBamSchemaDefObj> p ) {
				ICFBamTweakObj obj = p.getValue();
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
		tableColumnLookupDefSchema.setCellFactory( new Callback<TableColumn<ICFBamTweakObj,ICFBamSchemaDefObj>,TableCell<ICFBamTweakObj,ICFBamSchemaDefObj>>() {
			@Override public TableCell<ICFBamTweakObj,ICFBamSchemaDefObj> call(
				TableColumn<ICFBamTweakObj,ICFBamSchemaDefObj> arg)
			{
				return new CFReferenceTableCell<ICFBamTweakObj,ICFBamSchemaDefObj>();
			}
		});
		dataTable.getColumns().add( tableColumnLookupDefSchema );
		dataTable.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<ICFBamTweakObj>() {
				@Override public void changed( ObservableValue<? extends ICFBamTweakObj> observable,
					ICFBamTweakObj oldValue,
					ICFBamTweakObj newValue )
				{
					setJavaFXFocus( newValue );
				}
			});

		scrollMenu = new ScrollPane();
		scrollMenu.setVbarPolicy( ScrollBarPolicy.NEVER );
		scrollMenu.setHbarPolicy( ScrollBarPolicy.AS_NEEDED );
		scrollMenu.setFitToHeight( true );
		scrollMenu.setContent( getPanelHBoxMenu() );

		setTop( scrollMenu );
		setCenter( dataTable );
		javafxIsInitializing = false;
		if( observableListOfTweak != null ) {
			dataTable.setItems( observableListOfTweak );
		}
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

	public void setPaneMode( CFPane.PaneMode value ) {
		super.setPaneMode( value );
		adjustListButtons();
	}

	public void setJavaFXFocus( ICFLibAnyObj value ) {
		final String S_ProcName = "setJavaFXFocus";
		if( ( value == null ) || ( value instanceof ICFBamTweakObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamTweakObj" );
		}
		adjustListButtons();
	}

	public ICFBamTweakObj getJavaFXFocusAsTweak() {
		return( (ICFBamTweakObj)getJavaFXFocus() );
	}

	public void setJavaFXFocusAsTweak( ICFBamTweakObj value ) {
		setJavaFXFocus( value );
	}

	public class TweakByQualNameComparator
	implements Comparator<ICFBamTweakObj>
	{
		public TweakByQualNameComparator() {
		}

		public int compare( ICFBamTweakObj lhs, ICFBamTweakObj rhs ) {
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

	protected TweakByQualNameComparator compareTweakByQualName = new TweakByQualNameComparator();

	public Collection<ICFBamTweakObj> getJavaFXDataCollection() {
		return( javafxDataCollection );
	}

	public void setJavaFXDataCollection( Collection<ICFBamTweakObj> value ) {
		final String S_ProcName = "setJavaFXDataCollection";
		javafxDataCollection = value;
		observableListOfTweak = FXCollections.observableArrayList();
		if( javafxDataCollection != null ) {
				Iterator<ICFBamTweakObj> iter = javafxDataCollection.iterator();
				while( iter.hasNext() ) {
					observableListOfTweak.add( iter.next() );
				}
				observableListOfTweak.sort( compareTweakByQualName );
		}
		if( dataTable != null ) {
			dataTable.setItems( observableListOfTweak );
			// Hack from stackoverflow to fix JavaFX TableView refresh issue
			((TableColumn)dataTable.getColumns().get(0)).setVisible( false );
			((TableColumn)dataTable.getColumns().get(0)).setVisible( true );
		}
	}

	protected class CompareCFButtonByText
	implements Comparator<CFButton>
	{
		public CompareCFButtonByText() {
		}

		@Override public int compare( CFButton lhs, CFButton rhs ) {
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
				int retval = lhs.getText().compareTo( rhs.getText() );
				return( retval );
			}
		}
	}

	public CFHBox getPanelHBoxMenu() {
		if( hboxMenu == null ) {
			hboxMenu = new CFHBox( 10 );
				LinkedList<CFButton> list = new LinkedList<CFButton>();

				vboxMenuAdd = new CFVBox( 10 );
					buttonAddTableTweak = new CFButton();
					buttonAddTableTweak.setMinWidth( 200 );
					buttonAddTableTweak.setText( "Add TableTweak" );
					buttonAddTableTweak.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamTableTweakObj obj = (ICFBamTableTweakObj)schemaObj.getTableTweakTableObj().newInstance();
								CFBorderPane frame = javafxSchema.getTableTweakFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamTableTweakEditObj edit = (ICFBamTableTweakEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFBamTableObj container = (ICFBamTableObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerTableDef( container );
								ICFBamJavaFXTableTweakPaneCommon jpanelCommon = (ICFBamJavaFXTableTweakPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamTableObj ) {
						list.add( buttonAddTableTweak );
					}
					buttonAddSchemaTweak = new CFButton();
					buttonAddSchemaTweak.setMinWidth( 200 );
					buttonAddSchemaTweak.setText( "Add SchemaTweak" );
					buttonAddSchemaTweak.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamSchemaTweakObj obj = (ICFBamSchemaTweakObj)schemaObj.getSchemaTweakTableObj().newInstance();
								CFBorderPane frame = javafxSchema.getSchemaTweakFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamSchemaTweakEditObj edit = (ICFBamSchemaTweakEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFBamSchemaDefObj container = (ICFBamSchemaDefObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerSchemaDef( container );
								ICFBamJavaFXSchemaTweakPaneCommon jpanelCommon = (ICFBamJavaFXSchemaTweakPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamSchemaDefObj ) {
						list.add( buttonAddSchemaTweak );
					}
					buttonAddIndexTweak = new CFButton();
					buttonAddIndexTweak.setMinWidth( 200 );
					buttonAddIndexTweak.setText( "Add IndexTweak" );
					buttonAddIndexTweak.setOnAction( new EventHandler<ActionEvent>() {
						@Override public void handle( ActionEvent e ) {
							final String S_ProcName = "handle";
							try {
								ICFBamSchemaObj schemaObj = (ICFBamSchemaObj)javafxSchema.getSchema();
								ICFBamIndexTweakObj obj = (ICFBamIndexTweakObj)schemaObj.getIndexTweakTableObj().newInstance();
								CFBorderPane frame = javafxSchema.getIndexTweakFactory().newAddForm( cfFormManager, obj, getViewEditClosedCallback(), true );
								ICFBamIndexTweakEditObj edit = (ICFBamIndexTweakEditObj)( obj.beginEdit() );
								if( edit == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"edit" );
								}
								ICFBamIndexObj container = (ICFBamIndexObj)( getJavaFXContainer() );
								if( container == null ) {
									throw new CFLibNullArgumentException( getClass(),
										S_ProcName,
										0,
										"JavaFXContainer" );
								}
								edit.setRequiredContainerIndexDef( container );
								ICFBamJavaFXIndexTweakPaneCommon jpanelCommon = (ICFBamJavaFXIndexTweakPaneCommon)frame;
								jpanelCommon.setJavaFXFocus( obj );
								jpanelCommon.setPaneMode( CFPane.PaneMode.Add );
								cfFormManager.pushForm( frame );
								setLeft( null );
								adjustListButtons();
							}
							catch( Throwable t ) {
								CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
							}
						}
					});
					if( javafxContainer instanceof ICFBamIndexObj ) {
						list.add( buttonAddIndexTweak );
					}

				int len = list.size();
				CFButton arr[] = new CFButton[len];
				Iterator<CFButton> iter = list.iterator();
				int idx = 0;
				while( iter.hasNext() ) {
					arr[idx++] = iter.next();
				}
				Arrays.sort( arr, new CompareCFButtonByText() );
				for( idx = 0; idx < len; idx ++ ) {
					vboxMenuAdd.getChildren().add( arr[idx] );
				}

				buttonCancelAdd = new CFButton();
				buttonCancelAdd.setMinWidth( 200 );
				buttonCancelAdd.setText( "Cancel Add..." );
				buttonCancelAdd.setOnAction( new EventHandler<ActionEvent>() {
					@Override public void handle( ActionEvent e ) {
						final String S_ProcName = "handle";
						try {
							setLeft( null );
							adjustListButtons();
						}
						catch( Throwable t ) {
							CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
						}
					}
				});
				vboxMenuAdd.getChildren().add( buttonCancelAdd );

				scrollMenuAdd = new ScrollPane();
				scrollMenuAdd.setMinWidth( 240 );
				scrollMenuAdd.setMaxWidth( 240 );
				scrollMenuAdd.setFitToWidth( true );
				scrollMenuAdd.setHbarPolicy( ScrollBarPolicy.NEVER );
				scrollMenuAdd.setVbarPolicy( ScrollBarPolicy.AS_NEEDED );
				scrollMenuAdd.setContent( vboxMenuAdd );

			buttonAdd = new CFButton();
			buttonAdd.setMinWidth( 200 );
			buttonAdd.setText( "Add..." );
			buttonAdd.setOnAction( new EventHandler<ActionEvent>() {
				@Override public void handle( ActionEvent e ) {
					final String S_ProcName = "handle";
					try {
						setLeft( scrollMenuAdd );
						adjustListButtons();
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonAdd );

			buttonViewSelected = new CFButton();
			buttonViewSelected.setMinWidth( 200 );
			buttonViewSelected.setText( "View Selected" );
			buttonViewSelected.setOnAction( new EventHandler<ActionEvent>() {
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
						ICFBamTweakObj selectedInstance = getJavaFXFocusAsTweak();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFBamSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTweak.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getTweakFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTweakPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTableTweak.CLASS_CODE ) {
								ICFBamTableTweakObj obj = (ICFBamTableTweakObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTableTweakFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTableTweakPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamSchemaTweak.CLASS_CODE ) {
								ICFBamSchemaTweakObj obj = (ICFBamSchemaTweakObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getSchemaTweakFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXSchemaTweakPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamIndexTweak.CLASS_CODE ) {
								ICFBamIndexTweakObj obj = (ICFBamIndexTweakObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getIndexTweakFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXIndexTweakPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamTweakObj, ICFBamTableTweakObj, ICFBamSchemaTweakObj, ICFBamIndexTweakObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonViewSelected );

			buttonEditSelected = new CFButton();
			buttonEditSelected.setMinWidth( 200 );
			buttonEditSelected.setText( "Edit Selected" );
			buttonEditSelected.setOnAction( new EventHandler<ActionEvent>() {
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
						ICFBamTweakObj selectedInstance = getJavaFXFocusAsTweak();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFBamSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTweak.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getTweakFactory().newViewEditForm( cfFormManager, selectedInstance, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTweakPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTableTweak.CLASS_CODE ) {
								ICFBamTableTweakObj obj = (ICFBamTableTweakObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTableTweakFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXTableTweakPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamSchemaTweak.CLASS_CODE ) {
								ICFBamSchemaTweakObj obj = (ICFBamSchemaTweakObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getSchemaTweakFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXSchemaTweakPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamIndexTweak.CLASS_CODE ) {
								ICFBamIndexTweakObj obj = (ICFBamIndexTweakObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getIndexTweakFactory().newViewEditForm( cfFormManager, obj, getViewEditClosedCallback(), false );
								((ICFBamJavaFXIndexTweakPaneCommon)frame).setPaneMode( CFPane.PaneMode.Edit );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamTweakObj, ICFBamTableTweakObj, ICFBamSchemaTweakObj, ICFBamIndexTweakObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonEditSelected );

			buttonDeleteSelected = new CFButton();
			buttonDeleteSelected.setMinWidth( 200 );
			buttonDeleteSelected.setText( "Delete Selected" );
			buttonDeleteSelected.setOnAction( new EventHandler<ActionEvent>() {
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
						ICFBamTweakObj selectedInstance = getJavaFXFocusAsTweak();
						if( selectedInstance != null ) {
							int classCode = selectedInstance.getClassCode();
							ICFSecSchema.ClassMapEntry entry = ICFBamSchema.getClassMapByRuntimeClassCode(classCode);
							int backingClassCode = entry.getBackingClassCode();
							if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTweak.CLASS_CODE ) {
								CFBorderPane frame = javafxSchema.getTweakFactory().newAskDeleteForm( cfFormManager, selectedInstance, getDeleteCallback() );
								((ICFBamJavaFXTweakPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamTableTweak.CLASS_CODE ) {
								ICFBamTableTweakObj obj = (ICFBamTableTweakObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getTableTweakFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXTableTweakPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamSchemaTweak.CLASS_CODE ) {
								ICFBamSchemaTweakObj obj = (ICFBamSchemaTweakObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getSchemaTweakFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXSchemaTweakPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else if( entry.getSchemaName().equals("CFBam") && backingClassCode == ICFBamIndexTweak.CLASS_CODE ) {
								ICFBamIndexTweakObj obj = (ICFBamIndexTweakObj)selectedInstance;
								CFBorderPane frame = javafxSchema.getIndexTweakFactory().newAskDeleteForm( cfFormManager, obj, getDeleteCallback() );
								((ICFBamJavaFXIndexTweakPaneCommon)frame).setPaneMode( CFPane.PaneMode.View );
								cfFormManager.pushForm( frame );
							}
							else {
								throw new CFLibUnsupportedClassException( getClass(),
									S_ProcName,
									"selectedInstance",
									selectedInstance,
									"ICFBamTweakObj, ICFBamTableTweakObj, ICFBamSchemaTweakObj, ICFBamIndexTweakObj" );
							}
						}
					}
					catch( Throwable t ) {
						CFConsole.formException( S_FormName, ((CFButton)e.getSource()).getText(), t );
					}
				}
			});
			hboxMenu.getChildren().add( buttonDeleteSelected );

		}
		return( hboxMenu );
	}

	public ICFBamScopeObj getJavaFXContainer() {
		return( javafxContainer );
	}

	public void setJavaFXContainer( ICFBamScopeObj value ) {
		javafxContainer = value;
	}

	public void refreshMe() {
		if( javafxRefreshCallback != null ) {
			javafxRefreshCallback.refreshMe();
		}
	}

	public void adjustListButtons() {
		boolean enableState;
		boolean inEditState;
		boolean allowAdds;
		boolean inAddMode = false;
		ICFBamTweakObj selectedObj = getJavaFXFocusAsTweak();
		CFPane.PaneMode mode = getPaneMode();
		if( mode == CFPane.PaneMode.Edit ) {
			inEditState = true;
			allowAdds = false;
		}
		else {
			inEditState = false;
			if( getJavaFXContainer() != null ) {
				if( getLeft() != null ) {
					allowAdds = false;
					inAddMode = true;
				}
				else {
					allowAdds = true;
				}
			}
			else {
				allowAdds = false;
			}
		}
		if( selectedObj == null ) {
			enableState = false;
		}
		else {
			if( ( ! inAddMode ) && ( ! inEditState ) ) {
				enableState = true;
			}
			else {
				enableState = false;
			}
		}

		if( buttonViewSelected != null ) {
			buttonViewSelected.setDisable( ! enableState );
		}
		if( buttonEditSelected != null ) {
			if( inEditState ) {
				buttonEditSelected.setDisable( true );
			}
			else {
				buttonEditSelected.setDisable( ! enableState );
			}
		}
		if( buttonDeleteSelected != null ) {
			buttonDeleteSelected.setDisable( ! enableState );
		}

		if( buttonAdd != null ) {
			buttonAdd.setDisable( ! allowAdds );
		}
	}
}
