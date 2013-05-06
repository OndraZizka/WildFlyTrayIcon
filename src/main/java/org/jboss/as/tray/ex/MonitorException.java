
package org.jboss.as.tray.ex;


import java.io.Serializable;
import java.util.*;
import java.util.logging.*;


/**
 *
 * @author Ondrej Zizka
 */
public class MonitorException extends Exception implements Serializable
{

   public MonitorException(Throwable cause) {
      super(cause);
   }

   public MonitorException(String message, Throwable cause) {
      super(message, cause);
   }

   public MonitorException(String message) {
      super(message);
   }

}// class MonitorException
