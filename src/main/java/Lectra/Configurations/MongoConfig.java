package Lectra.Configurations;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClientFactory;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "Lectra")
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${db_username}")
    private String username;
    @Value("${db_password}")
    private String password;
    @Value("${db_DBName}")
    private String DBName;

    @Bean
    @Override
    public MongoClient mongoClient(){
        String link="mongodb://"+username+":"+password+"@localhost:27017/"+DBName+"?retryWrites=true";
//                "mongodb://"+username+":"+password+"@localhost:27017/"+DBName+"?retryWrites=true";
        return MongoClients.create(link);
    }

    @Override
    protected String getDatabaseName() {
        return "Lectra";
    }
}