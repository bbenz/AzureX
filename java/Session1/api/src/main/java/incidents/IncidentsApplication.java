package incidents;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class IncidentsApplication {

	private static final Log log = LogFactory.getLog(IncidentsApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(IncidentsApplication.class, args);
	}

}
