// Description: Java 25 JavaFX Display Element Factory for DbKeyHash256Col.

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

/**
 *	CFBamJavaFXDbKeyHash256ColFactory JavaFX Display Element Factory
 *	for DbKeyHash256Col.
 */
public class CFBamJavaFXDbKeyHash256ColFactory
implements ICFBamJavaFXDbKeyHash256ColFactory
{
	protected ICFBamJavaFXSchema javafxSchema = null;

	public CFBamJavaFXDbKeyHash256ColFactory( ICFBamJavaFXSchema argSchema ) {
		final String S_ProcName = "construct-schema";
		if( argSchema == null ) {
			throw new CFLibNullArgumentException( this.getClass(),
				S_ProcName,
				1,
				"argSchema" );
		}
		javafxSchema = argSchema;
	}

	public CFGridPane newAttrPane( ICFFormManager formManager, ICFBamDbKeyHash256ColObj argFocus ) {
		CFBamJavaFXDbKeyHash256ColAttrPane retnew = new CFBamJavaFXDbKeyHash256ColAttrPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newListPane( ICFFormManager formManager,
		ICFBamTableObj argContainer,
		ICFBamDbKeyHash256ColObj argFocus,
		Collection<ICFBamDbKeyHash256ColObj> argDataCollection,
		ICFRefreshCallback refreshCallback,
		boolean sortByChain )
	{
		CFBamJavaFXDbKeyHash256ColListPane retnew = new CFBamJavaFXDbKeyHash256ColListPane( formManager,
			javafxSchema,
			argContainer,
			argFocus,
			argDataCollection,
			refreshCallback,
			sortByChain );
		return( retnew );
	}

	public CFBorderPane newPickerPane( ICFFormManager formManager,
		ICFBamDbKeyHash256ColObj argFocus,
		ICFBamTableObj argContainer,
		Collection<ICFBamDbKeyHash256ColObj> argDataCollection,
		ICFBamJavaFXDbKeyHash256ColChosen whenChosen )
	{
		CFBamJavaFXDbKeyHash256ColPickerPane retnew = new CFBamJavaFXDbKeyHash256ColPickerPane( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFTabPane newEltTabPane( ICFFormManager formManager, ICFBamDbKeyHash256ColObj argFocus ) {
		CFBamJavaFXDbKeyHash256ColEltTabPane retnew = new CFBamJavaFXDbKeyHash256ColEltTabPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newAddPane( ICFFormManager formManager, ICFBamDbKeyHash256ColObj argFocus ) {
		CFBamJavaFXDbKeyHash256ColAddPane retnew = new CFBamJavaFXDbKeyHash256ColAddPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFSplitPane newViewEditPane( ICFFormManager formManager, ICFBamDbKeyHash256ColObj argFocus ) {
		CFBamJavaFXDbKeyHash256ColViewEditPane retnew = new CFBamJavaFXDbKeyHash256ColViewEditPane( formManager, javafxSchema, argFocus );
		return( retnew );
	}

	public CFBorderPane newAskDeleteForm( ICFFormManager formManager, ICFBamDbKeyHash256ColObj argFocus, ICFDeleteCallback callback ) {
		CFBamJavaFXDbKeyHash256ColAskDeleteForm retnew = new CFBamJavaFXDbKeyHash256ColAskDeleteForm( formManager, javafxSchema, argFocus, callback );
		return( retnew );
	}

	public CFBorderPane newPickerForm( ICFFormManager formManager,
		ICFBamDbKeyHash256ColObj argFocus,
		ICFBamTableObj argContainer,
		Collection<ICFBamDbKeyHash256ColObj> argDataCollection,
		ICFBamJavaFXDbKeyHash256ColChosen whenChosen )
	{
		CFBamJavaFXDbKeyHash256ColPickerForm retnew = new CFBamJavaFXDbKeyHash256ColPickerForm( formManager,
			javafxSchema,
			argFocus,
			argContainer,
			argDataCollection,
			whenChosen );
		return( retnew );
	}

	public CFBorderPane newAddForm( ICFFormManager formManager, ICFBamDbKeyHash256ColObj argFocus, ICFFormClosedCallback closeCallback, boolean allowSave ) {
		CFBamJavaFXDbKeyHash256ColAddForm retnew = new CFBamJavaFXDbKeyHash256ColAddForm( formManager, javafxSchema, argFocus, closeCallback, allowSave );
		return( retnew );
	}

	public CFBorderPane newViewEditForm( ICFFormManager formManager, ICFBamDbKeyHash256ColObj argFocus, ICFFormClosedCallback closeCallback, boolean cameFromAdd ) {
		CFBamJavaFXDbKeyHash256ColViewEditForm retnew = new CFBamJavaFXDbKeyHash256ColViewEditForm( formManager, javafxSchema, argFocus, closeCallback, cameFromAdd );
		return( retnew );
	}
}
