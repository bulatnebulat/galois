/*
 * Decompiled with CFR 0_114.
 */
package appli;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.border.Border;
import javax.swing.text.Document;

public abstract class Worker
extends JPanel
implements ActionListener,
PropertyChangeListener {
    private JProgressBar progressBar;
    private JButton startButton = new JButton("Start");
    private JButton stopButton;
    private JTextArea taskOutput;
    private Task task;
    WorkerDialog wDialog = null;

    public abstract boolean work(Task var1);

    public abstract boolean terminate(Task var1);

    public void go(final JFrame frame, final String title, final String message) {
        try {
            SwingUtilities.invokeAndWait(new Runnable(){

                @Override
                public void run() {
                    WorkerDialog wDialog = new WorkerDialog(frame, title, message, Worker.this);
                    wDialog.setLocationRelativeTo(wDialog.getParent());
                    wDialog.setVisible(true);
                }
            });
        }
        catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Worker() {
        super(new BorderLayout());
        this.startButton.setActionCommand("Click here to start algorithm execution");
        this.startButton.addActionListener(this);
        this.progressBar = new JProgressBar(0, 100);
        this.progressBar.setValue(0);
        this.progressBar.setStringPainted(false);
        this.taskOutput = new JTextArea(12, 50);
        this.taskOutput.setMargin(new Insets(5, 5, 5, 5));
        this.taskOutput.setEditable(false);
        JPanel panel = new JPanel();
        panel.add(this.startButton);
        panel.add(this.progressBar);
        this.add((Component)panel, "First");
        this.add((Component)new JScrollPane(this.taskOutput), "Center");
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        this.progressBar.setIndeterminate(true);
        this.startButton.setEnabled(false);
        this.task = new Task();
        this.task.addPropertyChangeListener(this);
        this.task.execute();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            int progress = (Integer)evt.getNewValue();
            this.progressBar.setIndeterminate(false);
            this.progressBar.setValue(progress);
            this.taskOutput.append(String.format("Completed  %d%%.\n", progress));
        }
    }

    static /* synthetic */ void access$3(Worker worker, JButton jButton) {
        worker.stopButton = jButton;
    }

    class Task
    extends SwingWorker<Void, Void> {
        Task() {
        }

        public void setProgress(double progress) {
            this.setProgress((int)progress);
        }

        public void message(String message) {
            Worker.this.taskOutput.append(String.valueOf(message) + "\n");
            Worker.this.taskOutput.setCaretPosition(Worker.this.taskOutput.getDocument().getLength());
        }

        @Override
        public Void doInBackground() {
            if (Worker.this.stopButton != null) {
                Worker.this.stopButton.setEnabled(false);
            }
            this.setProgress(0);
            Worker.this.taskOutput.append("Running...\n");
            Worker.this.work(this);
            return null;
        }

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            Worker.this.startButton.setEnabled(true);
            Worker.this.startButton.setText("Again");
            this.setProgress(100);
            Worker.this.terminate(this);
            if (Worker.this.stopButton != null) {
                Worker.this.stopButton.setEnabled(true);
            }
        }
    }

    public class WorkerDialog
    extends JDialog
    implements ActionListener {
        public WorkerDialog(JFrame parent, String title, String message, Worker worker2) {
            super(parent, title, true);
            if (parent != null) {
                Dimension parentSize = parent.getSize();
                Point p = parent.getLocation();
                this.setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
            }
            JPanel messagePane = new JPanel();
            messagePane.add(new JLabel(message));
            this.getContentPane().add((Component)messagePane, "North");
            this.getContentPane().add((Component)worker2, "Center");
            JPanel buttonPane = new JPanel();
            Worker.access$3(Worker.this, new JButton("Close"));
            buttonPane.add(Worker.this.stopButton);
            Worker.this.stopButton.addActionListener(this);
            this.getContentPane().add((Component)buttonPane, "South");
            Worker.this.stopButton.setEnabled(true);
            this.setDefaultCloseOperation(2);
            this.pack();
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            this.setVisible(false);
            this.dispose();
        }
    }

}

