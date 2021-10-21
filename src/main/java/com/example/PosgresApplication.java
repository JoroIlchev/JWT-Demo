package com.example;

import com.example.domain.Person;
import com.example.domain.Role;
import com.example.service.PersonService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

@SpringBootApplication
public class PosgresApplication {

    public static void main(String[] args) {
        SpringApplication.run(PosgresApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(PersonService personService){
        return args -> {
            personService.saveRole(new Role(null, "ROLE_USER"));
            personService.saveRole(new Role(null, "ROLE_MANAGER"));
            personService.saveRole(new Role(null, "ROLE_ADMIN"));
            personService.saveRole(new Role(null, "ROLE_SUPER_ADMIN"));

            personService.savePerson(new Person(null, "Pesho Peshev", "pesho", "1" , new ArrayList<>()));
            personService.savePerson(new Person(null, "Stamat STamatov", "stamat", "1" , new ArrayList<>()));
            personService.savePerson(new Person(null, "Krum Krumov", "krum", "1" , new ArrayList<>()));

            personService.addRoleToPerson("pesho","ROLE_USER" );
            personService.addRoleToPerson("pesho","ROLE_MANAGER" );
            personService.addRoleToPerson("pesho","ROLE_ADMIN" );
            personService.addRoleToPerson("pesho","ROLE_SUPER_ADMIN" );
            personService.addRoleToPerson("stamat","ROLE_USER" );
            personService.addRoleToPerson("krum","ROLE_MANAGER" );

        };
    }
}
