
package org.jboss.as.tray.config;

import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.log4j.Logger;
import org.jboss.as.tray.ex.MonitorIOException;


/**
 *
 * @author Ondrej Zizka
 */
public class JaxbConfigPersister
{
   private static final Logger log = Logger.getLogger(JaxbConfigPersister.class);


   private String filePath = "queries.xml";
   public void setFilePath(String filePath) {      this.filePath = filePath;   }

   public Writer writer = null;

   // Const

   /** Default config file path: "JawaBotConfig.xml" */
   public JaxbConfigPersister() { }

   /** Configures the bot according to the given config file. */
   public JaxbConfigPersister( String filePath ) {
      this.filePath = filePath;
   }

   /** The given writer will be used when writing via JAXB. */
   public JaxbConfigPersister(Writer soutCopyingFileWriter) {
      this.writer = soutCopyingFileWriter;
   }


   
   /**
    * Loads the configuration from the file (set by setFilePath).
    * @returns new JawaBot configured according to the loaded configuration.
    * TODO: Move to the JawaBot class.
    */
   public Queries load() throws MonitorIOException
   {
      log.info("Loading config from: "+this.filePath);

      try{
         //FileReader reader = new FileReader(filePath);

         InputStream is = JaxbConfigPersister.class.getClassLoader().getResourceAsStream(this.filePath);
         InputStreamReader reader = new InputStreamReader(is, "utf-8");


         JAXBContext jc = JAXBContext.newInstance( Queries.class );
         Unmarshaller mc = jc.createUnmarshaller();
         Queries config = (Queries) mc.unmarshal(reader);

				 return config;
      }
		catch (/*JAXB*/Exception ex) {
			throw new MonitorIOException( ex );
		}
   }



   /**
    *
    * @param bot
    * TODO: Move to the JawaBot class.
    */
	public void save( Queries queries ) throws MonitorIOException {

    // Store it to a XML.
		try {
			Writer writer = this.writer;
			if( null == writer )
				 writer = new FileWriter(filePath);

			JAXBContext jc = JAXBContext.newInstance( Queries.class );
			Marshaller mc = jc.createMarshaller();
			mc.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			mc.marshal( queries, writer );
		}
		catch (/*JAXB*/Exception ex) {
			throw new MonitorIOException( ex );
		}
      
	}


}// class
