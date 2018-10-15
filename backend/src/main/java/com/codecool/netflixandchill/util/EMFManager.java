package com.codecool.netflixandchill.util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class EMFManager {

    private static EntityManagerFactory instance = null;

    public static EntityManagerFactory getInstance() {
        if (instance == null) {
            Map<String, String> env = System.getenv();
            Map<String, Object> configOverrides = new HashMap<>();
            for (String envName : env.keySet()) {
                if (envName.contains("DB_USER_NAME")) {
                    configOverrides.put("javax.persistence.jdbc.user", env.get(envName));
                }
                if (envName.contains("DB_PASSWORD")) {
                    configOverrides.put("javax.persistence.jdbc.password", env.get(envName));
                }
            }

            instance = Persistence.createEntityManagerFactory("netflixandchill", configOverrides);
        }
        return instance;
    }

    private EMFManager() {}
}
