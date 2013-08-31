package org.openscada.ui.rap.test1;

import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.jface.databinding.swt.SWTObservables;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.rap.rwt.service.ServerPushSession;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.openscada.ui.databinding.item.DataItemObservableValue;

public class BasicEntryPoint extends AbstractEntryPoint {

	private ListViewer list;
	private Label label;
	private Text text;
	private DataBindingContext dbc;
	private DataItemObservableValue item;
	private ServerPushSession push;

	@Override
	protected void createContents(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		this.push = new ServerPushSession();
		this.push.start();

		Realm realm = SWTObservables.getRealm(parent.getDisplay());

		this.label = new Label(parent, SWT.NONE);
		label.setText("Value:");
		
		this.text = new Text(parent, SWT.READ_ONLY | SWT.BORDER);
		this.dbc = new DataBindingContext(realm);

		this.item = new DataItemObservableValue(Activator.getDefault()
				.getContext(), "connection.da", "OS.DEMO.ARDUINO1.LUX.V", realm);

		dbc.bindValue(SWTObservables.observeText(text), item);

		parent.addDisposeListener(new DisposeListener() {

			@Override
			public void widgetDisposed(DisposeEvent event) {
				handleDispose();
			}
		});
	}

	protected void handleDispose() {
		this.dbc.dispose();
		this.push.stop();
	}

}
