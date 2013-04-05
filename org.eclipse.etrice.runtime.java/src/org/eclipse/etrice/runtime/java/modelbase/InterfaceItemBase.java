/*******************************************************************************
 * Copyright (c) 2010 protos software gmbh (http://www.protos.de).
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package org.eclipse.etrice.runtime.java.modelbase;

import org.eclipse.etrice.runtime.java.messaging.AbstractMessageReceiver;
import org.eclipse.etrice.runtime.java.messaging.Address;
import org.eclipse.etrice.runtime.java.messaging.IMessageReceiver;
import org.eclipse.etrice.runtime.java.messaging.MessageService;
import org.eclipse.etrice.runtime.java.messaging.RTServices;

/**
 * The abstract base class for actor class interface items like ports and saps.
 * 
 * @author Henrik Rentz-Reichert
 *
 */
public abstract class InterfaceItemBase extends AbstractMessageReceiver {
	
	private IMessageReceiver ownMsgReceiver;
	private IMessageReceiver peerMsgReceiver;
	private int localId;
	private int idx;
	private Address peerAddress;

	public InterfaceItemBase (IEventReceiver actor, String name, int localId, int idx, Address ownAddress, Address peerAddress){
		super(actor, ownAddress, name);
		this.ownMsgReceiver = RTServices.getInstance().getMsgSvcCtrl().getMsgSvc(ownAddress.threadID);
		this.peerMsgReceiver = peerAddress==null? null: RTServices.getInstance().getMsgSvcCtrl().getMsgSvc(peerAddress.threadID);
		this.localId = localId;
		this.idx = idx;
		this.peerAddress = peerAddress;
		
		if (getAddress()!=null && this.ownMsgReceiver instanceof MessageService) {
			MessageService ms = (MessageService) this.ownMsgReceiver;
			// register at the own dispatcher to receive messages
			ms.getMessageDispatcher().addMessageReceiver(this);
		}
	}

	protected IMessageReceiver getMsgReceiver() {
		return ownMsgReceiver;
	}

	protected IMessageReceiver getPeerMsgReceiver() {
		return peerMsgReceiver;
	}

	public void setMsgReceiver(IMessageReceiver msgReceiver) {
		this.ownMsgReceiver = msgReceiver;
	}
	
	public IEventReceiver getActor() {
		return (IEventReceiver) getParent();
	}

	public int getLocalId() {
		return localId;
	}

	public int getIdx() {
		return idx;
	}

	protected Address getPeerAddress() {
		return peerAddress;
	}
}
