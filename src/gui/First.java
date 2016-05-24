package gui;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Button;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import javax.swing.JTable;

import galois.DocThread;
import galois.ReadCSV;
import galois.TopicThread;

public class First	{

	protected Shell shell;
	private Table table;
	public ReadCSV csv; 

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			First window = new First();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(847, 532);
		shell.setText("GSH builder");
		
		Button btnLoad = new Button(shell, SWT.NONE);
		btnLoad.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					table.removeAll();
					FileDialog fd = new FileDialog(shell, SWT.OPEN);
			        fd.setText("Open");
			        fd.setFilterPath("C:/");
			        String[] filterExt = { "*.csv", "*.*" };
			        fd.setFilterExtensions(filterExt);
			        String selected = fd.open();
			        csv = new ReadCSV(selected);
			        csv.read();
			        csv.show(table);
				}
				catch(Exception ex) {
					
				}
			}
		});
		btnLoad.setBounds(705, 413, 114, 50);
		btnLoad.setText("Load");
		
		table = new Table(shell, SWT.BORDER | SWT.FULL_SELECTION  | SWT.V_SCROLL
		        | SWT.H_SCROLL);
		table.setBounds(10, 10, 809, 387);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		Button btnExtract = new Button(shell, SWT.NONE);
		btnExtract.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TopicThread T1 = new TopicThread(csv, "Thread-1");
				T1.start();

				DocThread T2 = new DocThread(csv, "Thread-2");;
				T2.start();
				
				
			}
		});
		btnExtract.setBounds(585, 413, 114, 50);
		btnExtract.setText("Extract");
		
		Button btnBuild = new Button(shell, SWT.NONE);
		btnBuild.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				csv.deleteEqual();
				csv.coverGraph();
			}
		});
		btnBuild.setBounds(465, 413, 114, 50);
		btnBuild.setText("Build");
		
		Button btnPluton = new Button(shell, SWT.NONE);
		btnPluton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		btnPluton.setBounds(265, 413, 114, 50);
		btnPluton.setText("Pluton");
		
		Button btnCeres = new Button(shell, SWT.NONE);
		btnCeres.setBounds(153, 413, 106, 50);
		btnCeres.setText("Ceres");
		
		Button btnAres = new Button(shell, SWT.NONE);
		btnAres.setBounds(39, 413, 108, 50);
		btnAres.setText("Ares");

	}
}
