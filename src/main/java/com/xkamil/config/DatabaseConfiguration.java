package com.xkamil.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.jndi.MongoClientFactory;
import com.xkamil.Application;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("production")
public class DatabaseConfiguration {

    private static final String DATABASE = "chat-api";
    private static final String HOST = "ds157829.mlab.com";
    private static final int PORT = 57829;
    private static final String USER = "mietek";
    private static final String PASSWORD = "mietek";


    private static final Morphia morphia = new Morphia();
    private static Datastore datastore = null;

    @Bean
    public Datastore getDatastore() {
        morphia.mapPackage("com.xkamil.storage");

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

        List<ServerAddress> seeds = new ArrayList<ServerAddress>();
        seeds.add( new ServerAddress(HOST, PORT ));
        List<MongoCredential> credentials = new ArrayList<MongoCredential>();
        credentials.add(
                MongoCredential.createScramSha1Credential(
                        USER,
                        DATABASE,
                        PASSWORD.toCharArray()
                )
        );
        MongoClient mongo = new MongoClient( seeds, credentials );

        return new MongoClient( seeds, credentials );
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
