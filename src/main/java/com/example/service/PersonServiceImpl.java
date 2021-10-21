package com.example.service;


import com.example.domain.Person;
import com.example.domain.Role;
import com.example.domain.repository.PersonRepository;
import com.example.domain.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Transactional
@Slf4j
public class PersonServiceImpl implements PersonService, UserDetailsService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonServiceImpl(PersonRepository personRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Person savePerson(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        log.info("Saving a new user to the DB with username " + person.getUsername());
        return personRepository.save(person);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Saving a new role to the DB with name " + role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToPerson(String username, String roleName) {
        log.info("Adding to the user " + username + " Role with name " + roleName);
        Person person = personRepository.findUserByUsername(username);
        Role role = roleRepository.findRoleByName(roleName);
        person.getRoles().add(role);
//        personRepository.save(person);

    }

    @Override
    public Person getPerson(String username) {
        log.info("Returning a user with username " + username);
        return personRepository.findUserByUsername(username);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public List<Person> getAllPeople() {
        return personRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Person person = this.personRepository.findUserByUsername(username);
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        person.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        });
        return new User(person.getUsername(), person.getPassword(), authorities);
    }
}
