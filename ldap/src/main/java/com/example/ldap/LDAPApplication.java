package com.example.ldap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LDAPApplication {

    public static void main(String[] args) {
        SpringApplication.run(LDAPApplication.class, args);
    }

    /**
     * dn           (Distinguished Name)
     * dc           (Domain Access Component)
     * o            (Organization Name): start search process
     * ou           (Organization Unit)
     * cn           (Common Name): account name or user
     * uid          Username
     * userPassword password
     * sn           (Surname)
     */

    /**
     * LDAP servers can use LDIF (LDAP Data Interchange Format) files to exchange user data
     * The spring.ldap.embedded.ldif property inside application.properties lets Spring Boot pull in an LDIF data file
     * This makes it easy to pre-load demonstration data
     */

}
