/*
 * MonitorMainWindow.java
 */
package org.jboss.as.tray;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.logging.Logger;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JToggleButton;
import org.jboss.as.tray.beans.Metric;
import org.jboss.as.tray.config.JaxbConfigPersister;
import org.jboss.as.tray.config.Queries;
import org.jboss.as.tray.ex.MonitorException;
import org.jboss.as.tray.ex.MonitorIOException;

/**
 * The application's main frame.
 */
public class MonitorMainWindow extends FrameView {

    Logger log = Logger.getLogger( MonitorMainWindow.class.getName() );
    int keyStrokeCount = 0;
    private String lastExStackTrace = "No exception yet.";


    public MonitorMainWindow( SingleFrameApplication app ) {
        super( app );

        initComponents();

        // status bar initialization - message timeout, idle icon and busy animation, etc
        ResourceMap resourceMap = getResourceMap();
        int messageTimeout = resourceMap.getInteger( "StatusBar.messageTimeout" );
        messageTimer = new Timer( messageTimeout, new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                statusMessageLabel.setText( "" );
            }
        } );
        messageTimer.setRepeats( false );
        int busyAnimationRate = resourceMap.getInteger( "StatusBar.busyAnimationRate" );
        for( int i = 0; i < busyIcons.length; i++ ) {
            busyIcons[i] = resourceMap.getIcon( "StatusBar.busyIcons[" + i + "]" );
        }
        busyIconTimer = new Timer( busyAnimationRate, new ActionListener() {
            public void actionPerformed( ActionEvent e ) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon( busyIcons[busyIconIndex] );
            }
        } );
        idleIcon = resourceMap.getIcon( "StatusBar.idleIcon" );
        statusAnimationLabel.setIcon( idleIcon );
        progressBar.setVisible( false );

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor( getApplication().getContext() );
        taskMonitor.addPropertyChangeListener( new java.beans.PropertyChangeListener() {
            public void propertyChange( java.beans.PropertyChangeEvent evt ) {
                String propertyName = evt.getPropertyName();
                if( "started".equals( propertyName ) ) {
                    if( !busyIconTimer.isRunning() ) {
                        statusAnimationLabel.setIcon( busyIcons[0] );
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible( true );
                    progressBar.setIndeterminate( true );
                } else if( "done".equals( propertyName ) ) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon( idleIcon );
                    progressBar.setVisible( false );
                    progressBar.setValue( 0 );
                } else if( "message".equals( propertyName ) ) {
                    String text = (String) (evt.getNewValue());
                    statusMessageLabel.setText( (text == null) ? "" : text );
                    messageTimer.restart();
                } else if( "progress".equals( propertyName ) ) {
                    int value = (Integer) (evt.getNewValue());
                    progressBar.setVisible( true );
                    progressBar.setIndeterminate( false );
                    progressBar.setValue( value );
                }
            }
        } );
    }


    @Action
    public void showAboutBox() {
        if( aboutBox == null ) {
            JFrame mainFrame = MonitorApp.getApplication().getMainFrame();
            aboutBox = new AboutBox( mainFrame );
            aboutBox.setLocationRelativeTo( mainFrame );
        }
        MonitorApp.getApplication().show( aboutBox );
    }


    @Action
    public void updateMetrics() throws MonitorException {
        try {
            JaxbConfigPersister queriesLoader = (JaxbConfigPersister) MonitorApp.getBean( "queriesLoader" );
            Queries queries = queriesLoader.load();

            MetricsUpdater updater = (MetricsUpdater) MonitorApp.getBean( "metricsUpdater" );
            List<Metric> metrics = updater.updateMetrics( queries );

            showMetrics( metrics );
        } catch( MonitorIOException ex ) {
            throw new MonitorException( ex );
        }

    }


    public void showMetrics( List<Metric> metrics ) {
        // Clear.
        log.info( "removeAll()" );
        this.metricsPanel.removeAll();

        NumberFormat formatter = new DecimalFormat( "#,##0" );

        // Put new.
        for( Metric metric : metrics ) {
            log.info( "Adding " + metric );
            JLabel labelDesc = new JLabel( metric.desc );
            JLabel labelValue = new JLabel( metric.value );
            metricsPanel.add( labelDesc );
            metricsPanel.add( labelValue );
        }

        //this.metricsPanel.invalidate();
        //this.metricsPanel.list();
        //this.metricsPanel.repaint();
        this.metricsPanel.doLayout();
    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings( "unchecked" )
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    menuBar = new javax.swing.JMenuBar();
    javax.swing.JMenu fileMenu = new javax.swing.JMenu();
    javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
    javax.swing.JMenu helpMenu = new javax.swing.JMenu();
    javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
    statusPanel = new javax.swing.JPanel();
    javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
    statusMessageLabel = new javax.swing.JLabel();
    statusAnimationLabel = new javax.swing.JLabel();
    progressBar = new javax.swing.JProgressBar();
    showExceptionButton = new javax.swing.JToggleButton();
    updateMetricsButton = new javax.swing.JButton();
    mainPanel = new javax.swing.JPanel();
    metricsPanel = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();

    menuBar.setName("menuBar"); // NOI18N

    fileMenu.setName("fileMenu"); // NOI18N

    javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(MonitorApp.class).getContext().getActionMap(MonitorMainWindow.class, this);
    exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
    exitMenuItem.setName("exitMenuItem"); // NOI18N
    fileMenu.add(exitMenuItem);

    menuBar.add(fileMenu);

    helpMenu.setName("helpMenu"); // NOI18N

    aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
    aboutMenuItem.setName("aboutMenuItem"); // NOI18N
    helpMenu.add(aboutMenuItem);

    menuBar.add(helpMenu);

    statusPanel.setName("statusPanel"); // NOI18N

    statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

    statusMessageLabel.setName("statusMessageLabel"); // NOI18N

    statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
    statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

    progressBar.setName("progressBar"); // NOI18N

    showExceptionButton.setName("showExceptionButton"); // NOI18N
    showExceptionButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        showExceptionButtonActionPerformed(evt);
      }
    });

    updateMetricsButton.setAction(actionMap.get("updateMetrics")); // NOI18N
    org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(MonitorApp.class).getContext().getResourceMap(MonitorMainWindow.class);
    updateMetricsButton.setText(resourceMap.getString("updateMetricsButton.text")); // NOI18N
    updateMetricsButton.setName("updateMetricsButton"); // NOI18N

    org.jdesktop.layout.GroupLayout statusPanelLayout = new org.jdesktop.layout.GroupLayout(statusPanel);
    statusPanel.setLayout(statusPanelLayout);
    statusPanelLayout.setHorizontalGroup(
      statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE)
      .add(statusPanelLayout.createSequentialGroup()
        .addContainerGap()
        .add(statusMessageLabel)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(updateMetricsButton)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 52, Short.MAX_VALUE)
        .add(showExceptionButton)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
        .add(statusAnimationLabel)
        .addContainerGap())
    );
    statusPanelLayout.setVerticalGroup(
      statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(statusPanelLayout.createSequentialGroup()
        .add(statusPanelSeparator, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
          .add(statusPanelLayout.createSequentialGroup()
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 13, Short.MAX_VALUE)
            .add(statusPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
              .add(statusMessageLabel)
              .add(statusAnimationLabel)
              .add(progressBar, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
              .add(showExceptionButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
            .add(11, 11, 11))
          .add(statusPanelLayout.createSequentialGroup()
            .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
            .add(updateMetricsButton)
            .addContainerGap())))
    );

    mainPanel.setName("mainPanel"); // NOI18N

    metricsPanel.setName("metricsPanel"); // NOI18N
    metricsPanel.setLayout(new java.awt.GridLayout(0, 2));

    jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
    jLabel1.setName("jLabel1"); // NOI18N
    metricsPanel.add(jLabel1);

    org.jdesktop.layout.GroupLayout mainPanelLayout = new org.jdesktop.layout.GroupLayout(mainPanel);
    mainPanel.setLayout(mainPanelLayout);
    mainPanelLayout.setHorizontalGroup(
      mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(mainPanelLayout.createSequentialGroup()
        .addContainerGap()
        .add(metricsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
        .addContainerGap())
    );
    mainPanelLayout.setVerticalGroup(
      mainPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
      .add(mainPanelLayout.createSequentialGroup()
        .addContainerGap()
        .add(metricsPanel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 177, Short.MAX_VALUE)
        .addContainerGap())
    );

    setComponent(mainPanel);
    setMenuBar(menuBar);
    setStatusBar(statusPanel);
  }// </editor-fold>//GEN-END:initComponents


    private void showExceptionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showExceptionButtonActionPerformed
        JToggleButton btn = (JToggleButton) evt.getSource();
        if( btn.isSelected() ) {
        } else {
        }
    }//GEN-LAST:event_showExceptionButtonActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel jLabel1;
  private javax.swing.JPanel mainPanel;
  private javax.swing.JMenuBar menuBar;
  private javax.swing.JPanel metricsPanel;
  private javax.swing.JProgressBar progressBar;
  private javax.swing.JToggleButton showExceptionButton;
  private javax.swing.JLabel statusAnimationLabel;
  private javax.swing.JLabel statusMessageLabel;
  private javax.swing.JPanel statusPanel;
  private javax.swing.JButton updateMetricsButton;
  // End of variables declaration//GEN-END:variables
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private JDialog aboutBox;

}
