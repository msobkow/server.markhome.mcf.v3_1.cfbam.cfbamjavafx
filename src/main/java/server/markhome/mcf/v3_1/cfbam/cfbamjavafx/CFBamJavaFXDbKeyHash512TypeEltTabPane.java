// Description: Java 25 JavaFX Element TabPane implementation for DbKeyHash512Type.

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
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
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

/**
 *	CFBamJavaFXDbKeyHash512TypeEltTabPane JavaFX Element TabPane implementation
 *	for DbKeyHash512Type.
 */
public class CFBamJavaFXDbKeyHash512TypeEltTabPane
extends CFTabPane
implements ICFBamJavaFXDbKeyHash512TypePaneCommon
{
	protected ICFFormManager cfFormManager = null;
	protected ICFBamJavaFXSchema javafxSchema = null;
	protected boolean javafxIsInitializing = true;
	public final String LABEL_TabChildrenRefTableColList = "Optional Children Referencing Table Columns";
	protected CFTab tabChildrenRefTableCol = null;
	public final String LABEL_TabChildrenRefIndexColList = "Optional Children Referencing Index Columns";
	protected CFTab tabChildrenRefIndexCol = null;
	protected CFBorderPane tabViewChildrenRefTableColListPane = null;
	protected CFBorderPane tabViewChildrenRefIndexColListPane = null;

	public CFBamJavaFXDbKeyHash512TypeEltTabPane( ICFFormManager formManager, ICFBamJavaFXSchema argSchema, ICFBamDbKeyHash512TypeObj argFocus ) {
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
		setJavaFXFocusAsDbKeyHash512Type( argFocus );
		// Wire the newly constructed Panes/Tabs to this TabPane
		tabChildrenRefTableCol = new CFTab();
		tabChildrenRefTableCol.setText( LABEL_TabChildrenRefTableColList );
		tabChildrenRefTableCol.setContent( getTabViewChildrenRefTableColListPane() );
		getTabs().add( tabChildrenRefTableCol );
		tabChildrenRefIndexCol = new CFTab();
		tabChildrenRefIndexCol.setText( LABEL_TabChildrenRefIndexColList );
		tabChildrenRefIndexCol.setContent( getTabViewChildrenRefIndexColListPane() );
		getTabs().add( tabChildrenRefIndexCol );
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
		if( ( value == null ) || ( value instanceof ICFBamDbKeyHash512TypeObj ) ) {
			super.setJavaFXFocus( value );
		}
		else {
			throw new CFLibUnsupportedClassException( getClass(),
				S_ProcName,
				"value",
				value,
				"ICFBamDbKeyHash512TypeObj" );
		}
	}

	public void setJavaFXFocusAsDbKeyHash512Type( ICFBamDbKeyHash512TypeObj value ) {
		setJavaFXFocus( value );
	}

	public ICFBamDbKeyHash512TypeObj getJavaFXFocusAsDbKeyHash512Type() {
		return( (ICFBamDbKeyHash512TypeObj)getJavaFXFocus() );
	}

	protected class RefreshChildrenRefTableColList
	implements ICFRefreshCallback
	{
		public RefreshChildrenRefTableColList() {
		}

		public void refreshMe() {
			Collection<ICFBamTableColObj> dataCollection;
			ICFBamDbKeyHash512TypeObj focus = (ICFBamDbKeyHash512TypeObj)getJavaFXFocusAsDbKeyHash512Type();
			if( focus != null ) {
				dataCollection = focus.getOptionalChildrenRefTableCol( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewChildrenRefTableColListPane();
			ICFBamJavaFXTableColPaneList jpList = (ICFBamJavaFXTableColPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewChildrenRefTableColListPane() {
		if( tabViewChildrenRefTableColListPane == null ) {
			ICFBamDbKeyHash512TypeObj focus = (ICFBamDbKeyHash512TypeObj)getJavaFXFocusAsDbKeyHash512Type();
			Collection<ICFBamTableColObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalChildrenRefTableCol( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFBamTableObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFBamTableObj ) ) {
				javafxContainer = (ICFBamTableObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenRefTableColListPane = javafxSchema.getTableColFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshChildrenRefTableColList(), false );
		}
		return( tabViewChildrenRefTableColListPane );
	}

	protected class RefreshChildrenRefIndexColList
	implements ICFRefreshCallback
	{
		public RefreshChildrenRefIndexColList() {
		}

		public void refreshMe() {
			Collection<ICFBamIndexColObj> dataCollection;
			ICFBamDbKeyHash512TypeObj focus = (ICFBamDbKeyHash512TypeObj)getJavaFXFocusAsDbKeyHash512Type();
			if( focus != null ) {
				dataCollection = focus.getOptionalChildrenRefIndexCol( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			CFBorderPane pane = getTabViewChildrenRefIndexColListPane();
			ICFBamJavaFXIndexColPaneList jpList = (ICFBamJavaFXIndexColPaneList)pane;
			jpList.setJavaFXDataCollection( dataCollection );
		}
	}

	public CFBorderPane getTabViewChildrenRefIndexColListPane() {
		if( tabViewChildrenRefIndexColListPane == null ) {
			ICFBamDbKeyHash512TypeObj focus = (ICFBamDbKeyHash512TypeObj)getJavaFXFocusAsDbKeyHash512Type();
			Collection<ICFBamIndexColObj> dataCollection;
			if( focus != null ) {
				dataCollection = focus.getOptionalChildrenRefIndexCol( javafxIsInitializing );
			}
			else {
				dataCollection = null;
			}
			ICFBamIndexObj javafxContainer;
			if( ( focus != null ) && ( focus instanceof ICFBamIndexObj ) ) {
				javafxContainer = (ICFBamIndexObj)focus;
			}
			else {
				javafxContainer = null;
			}
			tabViewChildrenRefIndexColListPane = javafxSchema.getIndexColFactory().newListPane( cfFormManager, javafxContainer, null, dataCollection, new RefreshChildrenRefIndexColList(), false );
		}
		return( tabViewChildrenRefIndexColListPane );
	}

	public void setPaneMode( CFPane.PaneMode value ) {
		CFPane.PaneMode oldMode = getPaneMode();
		super.setPaneMode( value );
		if( tabViewChildrenRefTableColListPane != null ) {
			((ICFBamJavaFXTableColPaneCommon)tabViewChildrenRefTableColListPane).setPaneMode( value );
		}
		if( tabViewChildrenRefIndexColListPane != null ) {
			((ICFBamJavaFXIndexColPaneCommon)tabViewChildrenRefIndexColListPane).setPaneMode( value );
		}
	}
}
