
package org.jboss.as.tray.beans;


import java.io.Serializable;


/**
 *
 * @author Ondrej Zizka
 */
public class Metric implements Serializable
{
  public final String desc;
	public final String value;

	 public Metric(String desc, String value) {
			this.desc = desc;
			this.value = value;
	 }

   @Override public String toString() {
      return "Metric{'"+desc+"': '"+value+"'";
   }

}// class Metric
