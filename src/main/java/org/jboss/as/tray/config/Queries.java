
package org.jboss.as.tray.config;


import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


/**
 *
 * @author Ondrej Zizka
 */
@XmlRootElement(name="queries")
public class Queries implements Serializable
{

	 @XmlElement(name="query")
	 public List<Query> queries;

}// class Queries
