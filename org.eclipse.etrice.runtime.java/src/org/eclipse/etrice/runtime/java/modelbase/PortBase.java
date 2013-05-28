/*******************************************************************************
 * Copyright (c) 2010 protos software gmbh (http://www.protos.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.etrice.runtime.java.modelbase;

import org.eclipse.etrice.runtime.java.messaging.Address;

/**
 * An abstract base class for port instances.
 * 
 * @author Thomas Schuetz
 *
 */
public abstract class PortBase extends InterfaceItemBase {
	
	public PortBase (IEventReceiver actor, String name, int localId, int idx, Address address, Address peerAddress){
		super(actor, name, localId, idx, address, peerAddress);
	}
}
