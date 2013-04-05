/*******************************************************************************
 * Copyright (c) 2010 protos software gmbh (http://www.protos.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.etrice.runtime.java.messaging;

/**
 * An implementation of the IRTObject interface using a hierarchical structure
 * to assemble paths.
 * 
 * @author Henrik Rentz-Reichert
 *
 */
public class RTObject implements IRTObject	{

	private IRTObject parent = null;
	private String name = NO_NAME;
	
	public RTObject(IRTObject parent, String name){
		this.parent = parent;
		this.name = name;
	}

	public IRTObject getParent() {
		return parent;
	}
	
	public String getInstancePath() {
		String path = PATH_DELIM + name;
		
		if (parent!=null)
			path = parent.getInstancePath()+path;
		
		return path;
	}

	public String getInstancePathName() {
		String path = PATHNAME_DELIM + name;
		
		if (parent!=null)
			path = parent.getInstancePathName()+path;
		
		return path;
	}
}
