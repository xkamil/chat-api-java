package com.xkamil.config;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestDatabaseConfiguration {
    private static final String DATABASE = "chat";
    private static final String HOST = "localhost";
    private static final int PORT = 27017;

    @Bean
    public MongoClient getClient() {
        MongoClient mongoClient = new MongoClient(HOST, PORT);

        return new MongoClient();
    }

    @Bean
    public MongoDatabase getDatabase() {
        return getClient().getDatabase(DATABASE);
    }
}
