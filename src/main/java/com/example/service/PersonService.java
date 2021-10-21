package com.example.service;

import com.example.domain.Person;
import com.example.domain.Role;

import java.util.List;

public interface PersonService {
    Person savePerson(Person person);
    Role saveRole(Role role);
    void addRoleToPerson(String username, String roleName);
    Person getPerson(String username);
    List<Person> getAllPeople();
}
