
package org.jboss.as.tray;


import java.util.*;
import java.util.logging.*;
import javax.sql.DataSource;
import org.apache.commons.lang.ObjectUtils;
import org.jboss.as.tray.beans.Metric;
import org.jboss.as.tray.config.Queries;
import org.jboss.as.tray.config.Query;
import org.springframework.jdbc.core.support.JdbcDaoSupport;


/**
 *
 * @author Ondrej Zizka
 */
public class MetricsUpdater extends JdbcDaoSupport
{
  private static final Logger log = Logger.getLogger( MetricsUpdater.class.getName() );

  private DataSource dataSource;
  
  List<Metric> updateMetrics( Queries queries ){
     List<Metric> metrics = new ArrayList<Metric>( queries.queries.size() );
     for( Query query : queries.queries ){
         Object res = this.getJdbcTemplate().queryForObject(query.sql, Object.class);
         metrics.add( new Metric(query.desc, ObjectUtils.toString(res, "null") ));
     }
     return metrics;
  }

}// class MetricsUpdater
