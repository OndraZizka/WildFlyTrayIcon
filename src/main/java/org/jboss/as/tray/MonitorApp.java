/*
 * MonitorApp.java
 */

package org.jboss.as.tray;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.jdesktop.application.Application;
import org.jdesktop.application.SingleFrameApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * The main class of the application.
 */
public class MonitorApp extends SingleFrameApplication
{
     private static Logger log = Logger.getLogger( MonitorApp.class.getName()  );

     /**
      * Singleton stuff.
      */
     private static MonitorApp instance;
     public static MonitorApp getInstance() {
       if( null == instance ) throw new RuntimeException("MonitorApp not initialized yet.");
       return instance;
     }


     // Spring Application Context
     private static ApplicationContext ac;
     public static ApplicationContext getAppContext(){ return ac; }
     public static Object getBean(String name) { return ac.getBean(name); }



    /**
     * At startup create and show the main frame of the application.
     */
    @Override protected void startup() {
        init();
        show(new MonitorMainWindow(this));
    }

    /**
     * This method is to initialize the specified window by injecting resources.
     * Windows shown in our application come fully initialized from the GUI
     * builder, so this additional configuration is not needed.
     */
    @Override protected void configureWindow(java.awt.Window root) {
    }

    /**
     * A convenient static getter for the application instance.
     * @return the instance of MonitorApp
     */
    public static MonitorApp getApplication() {
        return Application.getInstance(MonitorApp.class);
    }

    /**
     * Main method launching the application.
     */
    public static void main(String[] args) {
        launch(MonitorApp.class, args);
    }





  /**
   * Inicializace objektu aplikace - načte logging, Spring a options
   */
  public void init(){

    // Logging
    initLogging();

    // -- Spring Bean Factory --
    this.ac = new ClassPathXmlApplicationContext("spring/SpringBeans.xml");

  }// init()



  /** init() helper. */
  private void initLogging()
  {
    String logConfigFile = "logging.properties";
    try {
			log.info("Načítám konfiguraci logování ze souboru: "+logConfigFile);
			log.info("(nastaveno v systémové proměnné java.util.logging.config.file)");
      LogManager.getLogManager().readConfiguration(new FileInputStream(logConfigFile));
    }catch(IOException ex){
      System.out.println("Chyba při načítání nastavení logování ze souboru ["+logConfigFile+"]. Bude použito výchozí.");
    }
    log = Logger.getLogger(MonitorApp.class.getName());
  }





}// class
