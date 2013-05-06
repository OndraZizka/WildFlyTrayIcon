
package org.jboss.as.tray.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;


/**
 *
 * @author Ondrej Zizka
 */
public class Query {

	 @XmlAttribute
   public String desc;

	 @XmlValue
	 public String sql;

   // For JAXB
   public Query() { }

	 public Query(String desc, String sql) {
			this.desc = desc;
			this.sql = sql;
	 }



}// class Query
