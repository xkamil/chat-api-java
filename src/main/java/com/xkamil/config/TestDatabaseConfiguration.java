package com.xkamil.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.xkamil.Application;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestDatabaseConfiguration {
    private static MongoClientURI uri = new MongoClientURI("mongodb://krzysztof:jarzyna28@ds157829.mlab.com:57829");

    private static final String DATABASE = "chat-api";

    private static final Morphia morphia = new Morphia();
    private static Datastore datastore = null;

    @Bean
    public Datastore getDatastore() {
        morphia.mapPackage("com.xkamil.storage");

        // create the Datastore connecting to the default port on the local host
        if(datastore == null){
            synchronized (Application.class){
                if(datastore == null){
                    datastore = morphia.createDatastore(getClient(), DATABASE);
                    datastore.ensureIndexes();
                }
            }
        }

        return datastore;
    }


    @Bean
    public MongoClient getClient() {
        MongoClient mongoClient = new MongoClient(uri);

        return new MongoClient();
    }

    @Bean
    public MongoDatabase getDatabase() {
        return getClient().getDatabase(DATABASE);
    }

    @Bean
    public String getDatabaseName() {
        return DATABASE;
    }


}
