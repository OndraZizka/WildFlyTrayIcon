
package org.jboss.as.tray.ex;


/**
 *
 * @author Ondrej Zizka
 */
public class MonitorIOException extends MonitorException
{
	 public MonitorIOException(String message, Throwable cause) {
			super(message, cause);
	 }

	 public MonitorIOException(String message) {
			super(message);
	 }

	 public MonitorIOException(Throwable cause) {
			super(cause);
	 }

}// class MonitorIOException
