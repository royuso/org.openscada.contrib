package org.eclipse.etrice.runtime.java.messaging;

import java.util.ArrayList;

import org.eclipse.etrice.runtime.java.debugging.DebuggingService;
import org.eclipse.etrice.runtime.java.modelbase.EventMessage;
import org.eclipse.etrice.runtime.java.modelbase.EventWithDataMessage;
import org.eclipse.etrice.runtime.java.modelbase.IEventReceiver;
import org.eclipse.etrice.runtime.java.modelbase.PortBase;

public class RTSystemServicesProtocol {
	// message IDs
	// TODO: separate class for message IDs: class MSG{public static volatile int MSG_MIN = 0; ...} -> better structure
	// error if msgID <= MSG_MIN
	public static final int MSG_MIN = 0;
	//IDs for outgoing messages
	public static final int OUT_dummy = 1;

	//IDs for incoming messages
	public static final int IN_executeInitialTransition = 2;
	public static final int IN_startDebugging = 3;
	public static final int IN_stopDebugging = 4;

	//error if msgID >= MSG_MAX
	public static final int MSG_MAX = 5;

	private static String messageStrings[] = {"MIN", "dummy",
			"executeInitialTransition", "startDebugging", "stopDebugging",
			"MAX"};

	public String getMessageString(int msg_id) {
		if (msg_id < 0 || msg_id > MSG_MAX + 1) {
			// id out of range
			return "Message ID out of range";
		} else {
			return messageStrings[msg_id];
		}
	}

	// port class
	static public class RTSystemServicesProtocolPort extends PortBase {

		// constructors
		public RTSystemServicesProtocolPort(IEventReceiver actor, String name,
				int localId, Address addr, Address peerAddress) {
			super(actor, name, localId, 0, addr, peerAddress);
			DebuggingService.getInstance().addPortInstance(this);
		}
		public RTSystemServicesProtocolPort(IEventReceiver actor, String name,
				int localId, int idx, Address addr, Address peerAddress) {
			super(actor, name, localId, idx, addr, peerAddress);
			DebuggingService.getInstance().addPortInstance(this);
		}

		@Override
		public void receive(Message m) {
			if (!(m instanceof EventMessage))
				return;
			EventMessage msg = (EventMessage) m;
			if (msg.getEvtId() <= 0 || msg.getEvtId() >= MSG_MAX)
				System.out.println("unknown");
			else {
				if (messageStrings[msg.getEvtId()] != "timerTick") {
					// TODOTS: model switch for activation
					DebuggingService.getInstance().addMessageAsyncIn(
							getPeerAddress(), getAddress(),
							messageStrings[msg.getEvtId()]);
				}

				if (msg instanceof EventWithDataMessage)
					getActor().receiveEvent(this, msg.getEvtId(),
							((EventWithDataMessage) msg).getData());
				else
					getActor().receiveEvent(this, msg.getEvtId(), null);

			}
		}

		// sent messages

		public void dummy() {

			if (messageStrings[OUT_dummy] != "timerTick") {
				// TODOTS: model switch for activation
				DebuggingService.getInstance().addMessageAsyncOut(getAddress(),
						getPeerAddress(), messageStrings[OUT_dummy]);
			}
			if (getPeerAddress() != null)
				getPeerMsgReceiver().receive(
						new EventMessage(getPeerAddress(), OUT_dummy));

		}

	}

	// replicated port class
	static public class RTSystemServicesProtocolPortRepl {
		private ArrayList<RTSystemServicesProtocolPort> ports;
		private int replication;

		public RTSystemServicesProtocolPortRepl(IEventReceiver actor,
				String name, int localId, Address[] addr, Address[] peerAddress) {
			replication = addr.length;
			ports = new ArrayList<RTSystemServicesProtocol.RTSystemServicesProtocolPort>(
					replication);
			for (int i = 0; i < replication; ++i) {
				ports.add(new RTSystemServicesProtocolPort(actor, name + i,
						localId, i, addr[i], peerAddress[i]));
			}
		}

		public int getReplication() {
			return replication;
		}

		public RTSystemServicesProtocolPort get(int i) {
			return ports.get(i);
		}

		// outgoing messages

		public void dummy() {
			for (int i = 0; i < replication; ++i) {
				ports.get(i).dummy();
			}
		}

	}

	// interface for port class
	public interface IRTSystemServicesProtocolPort {

		// incoming messages
		public void executeInitialTransition();
		public void startDebugging();
		public void stopDebugging();

	}

	// port class
	static public class RTSystemServicesProtocolConjPort extends PortBase {

		// constructors
		public RTSystemServicesProtocolConjPort(IEventReceiver actor,
				String name, int localId, Address addr, Address peerAddress) {
			super(actor, name, localId, 0, addr, peerAddress);
			DebuggingService.getInstance().addPortInstance(this);
		}
		public RTSystemServicesProtocolConjPort(IEventReceiver actor,
				String name, int localId, int idx, Address addr,
				Address peerAddress) {
			super(actor, name, localId, idx, addr, peerAddress);
			DebuggingService.getInstance().addPortInstance(this);
		}

		@Override
		public void receive(Message m) {
			if (!(m instanceof EventMessage))
				return;
			EventMessage msg = (EventMessage) m;
			if (msg.getEvtId() <= 0 || msg.getEvtId() >= MSG_MAX)
				System.out.println("unknown");
			else {
				if (messageStrings[msg.getEvtId()] != "timerTick") {
					// TODOTS: model switch for activation
					DebuggingService.getInstance().addMessageAsyncIn(
							getPeerAddress(), getAddress(),
							messageStrings[msg.getEvtId()]);
				}

				if (msg instanceof EventWithDataMessage)
					getActor().receiveEvent(this, msg.getEvtId(),
							((EventWithDataMessage) msg).getData());
				else
					getActor().receiveEvent(this, msg.getEvtId(), null);

			}
		}

		// sent messages

		public void executeInitialTransition() {

			if (messageStrings[IN_executeInitialTransition] != "timerTick") {
				// TODOTS: model switch for activation
				DebuggingService.getInstance().addMessageAsyncOut(getAddress(),
						getPeerAddress(),
						messageStrings[IN_executeInitialTransition]);
			}
			if (getPeerAddress() != null)
				getPeerMsgReceiver().receive(
						new EventMessage(getPeerAddress(),
								IN_executeInitialTransition));

		}

		public void startDebugging() {

			if (messageStrings[IN_startDebugging] != "timerTick") {
				// TODOTS: model switch for activation
				DebuggingService.getInstance().addMessageAsyncOut(getAddress(),
						getPeerAddress(), messageStrings[IN_startDebugging]);
			}
			if (getPeerAddress() != null)
				getPeerMsgReceiver().receive(
						new EventMessage(getPeerAddress(), IN_startDebugging));

		}

		public void stopDebugging() {

			if (messageStrings[IN_stopDebugging] != "timerTick") {
				// TODOTS: model switch for activation
				DebuggingService.getInstance().addMessageAsyncOut(getAddress(),
						getPeerAddress(), messageStrings[IN_stopDebugging]);
			}
			if (getPeerAddress() != null)
				getPeerMsgReceiver().receive(
						new EventMessage(getPeerAddress(), IN_stopDebugging));

		}

	}

	// replicated port class
	static public class RTSystemServicesProtocolConjPortRepl {
		private ArrayList<RTSystemServicesProtocolConjPort> ports;
		private int replication;

		public RTSystemServicesProtocolConjPortRepl(IEventReceiver actor,
				String name, int localId, Address[] addr, Address[] peerAddress) {
			replication = addr.length;
			ports = new ArrayList<RTSystemServicesProtocol.RTSystemServicesProtocolConjPort>(
					replication);
			for (int i = 0; i < replication; ++i) {
				ports.add(new RTSystemServicesProtocolConjPort(actor, name + i,
						localId, i, addr[i], peerAddress[i]));
			}
		}

		public int getReplication() {
			return replication;
		}

		public RTSystemServicesProtocolConjPort get(int i) {
			return ports.get(i);
		}

		// incoming messages

		public void executeInitialTransition() {
			for (int i = 0; i < replication; ++i) {
				ports.get(i).executeInitialTransition();
			}
		}

		public void startDebugging() {
			for (int i = 0; i < replication; ++i) {
				ports.get(i).startDebugging();
			}
		}

		public void stopDebugging() {
			for (int i = 0; i < replication; ++i) {
				ports.get(i).stopDebugging();
			}
		}

	}

	// interface for port class
	public interface IRTSystemServicesProtocolConjPort {

		// outgoing messages
		public void dummy();

	}

}
