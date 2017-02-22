package incidents.configurations;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

@Configuration
public class DbConfiguration {
    @Autowired
    private DbConfigurationProperties dbConfigProperties;
	  /*
	   * Use the standard Mongo driver API to create a com.mongodb.Mongo instance.
	   */

    
	@SuppressWarnings("deprecation")
	public @Bean MongoClient mongo() throws UnknownHostException {
		String connectString = dbConfigProperties.getConnectString();
	    return new MongoClient(new MongoClientURI(connectString));
	   }
}
