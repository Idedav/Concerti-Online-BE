package com.project.utils;

import com.project.model.Role;
import com.project.model.User;
import com.project.model.enums.RoleCode;
import com.project.repository.RoleRepository;
import com.project.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class DbInitializer implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

        if (!roleRepository.existsByRoleCode(RoleCode.ROLE_USER)) {
            roleRepository.save(Role.builder().role(RoleCode.ROLE_USER).build());
        }
        if (!roleRepository.existsByRoleCode(RoleCode.ROLE_ADMIN)) {
            roleRepository.save(Role.builder().role(RoleCode.ROLE_ADMIN).build());
        }

        Role role = roleRepository.findByRoleCode(RoleCode.ROLE_ADMIN).orElseThrow();

        createUsers(role);
        createConcert();
        createTickets();

    }

    private void createUsers(Role role){

        if(userRepository.findByEmail("alessandro.verdi@gmail.com").isEmpty()){
            userRepository.save(User.builder()
                    .name("alessandro")
                    .surname("verdi")
                    .email("alessandro.verdi@gmail.com")
                    .password(passwordEncoder.encode("1234"))
                    .roles(List.of(role))
                    .build());
        }

        if(userRepository.findByEmail("giada.bianchi@gmail.com").isEmpty()){
            userRepository.save(User.builder()
                    .name("giada")
                    .surname("bianchi")
                    .email("giada.bianchi@gmail.com")
                    .password(passwordEncoder.encode("1234"))
                    .roles(List.of(role))
                    .build());
        }


        if(userRepository.findByEmail("davide.corda@gmail.com").isEmpty()){
            userRepository.save(User.builder()
                    .name("davide")
                    .surname("corda")
                    .email("davide.corda@gmail.com")
                    .password(passwordEncoder.encode("1234"))
                    .roles(List.of(role))
                    .build());
        }


        if(userRepository.findByEmail("valerio.scanu@gmail.com").isEmpty()){
            userRepository.save(User.builder()
                    .name("valerio")
                    .surname("scanu")
                    .email("valerio.scanu@gmail.com")
                    .password(passwordEncoder.encode("1234"))
                    .roles(List.of(role))
                    .build());
        }

        if(userRepository.findByEmail("alice.neri@gmail.com").isEmpty()){
            userRepository.save(User.builder()
                    .name("alice")
                    .surname("neri")
                    .email("alice.neri@gmail.com")
                    .password(passwordEncoder.encode("1234"))
                    .roles(List.of(role))
                    .build());
        }



    }

    @Transactional
    private void createConcert(){
        String insertConcerts = "INSERT INTO concerts (city, band, reply, unit_price, qty_available) VALUES " +
                "('Milano', 'Coldplay', '2024-11-20', 60.0, 150), " +
                "('Roma', 'Green Day', '2024-12-10', 70.0, 5), " +
                "('Torino', 'Toy Dolls', '2024-09-01', 70.0, 200), " +
                "('Venezia', 'Linkin Park', '2024-11-10', 70.0, 200), " +
                "('Firenze', 'U2', '2024-10-15', 70.0, 200), " +
                "('Torino', 'Muse', '2024-9-22', 70.0, 200), " +
                "('Milano', 'The Offspring', '2024-12-22', 70.0, 200), " +
                "('Milano', 'The Beatles', '2024-11-26', 70.0, 200), " +
                "('Roma', 'Pink Floyd', '2025-03-08', 70.0, 200), " +
                "('Napoli', 'Queen', '2025-01-15', 80.0, 180)";
        entityManager.createNativeQuery(insertConcerts).executeUpdate();
    }

    @Transactional
    private void createTickets() {
        String insertTickets = "INSERT INTO tickets (concert_id, user_id, tickets_qty, type_payment, total_price) VALUES " +
                "((SELECT id FROM concerts WHERE id = 2), " +
                "(SELECT id FROM users WHERE id = 2), " +
                "3, 'CREDIT_CARD', 180.0), " +

                "((SELECT id FROM concerts WHERE id = 4), " +
                "(SELECT id FROM users WHERE id = 4), " +
                "1, 'PAYPAL', 70.0), " +

                "((SELECT id FROM concerts WHERE id = 8), " +
                "(SELECT id FROM users WHERE id = 3), " +
                "1, 'DEBIT_CARD', 70.0), " +

                "((SELECT id FROM concerts WHERE id = 1), " +
                "(SELECT id FROM users WHERE id = 3), " +
                "1, 'CREDIT_CARD', 70.0), " +

                "((SELECT id FROM concerts WHERE id = 3), " +
                "(SELECT id FROM users WHERE id = 3), " +
                "1, 'DEBIT_CARD', 70.0), " +


                "((SELECT id FROM concerts WHERE id = 9), " +
                "(SELECT id FROM users WHERE id = 4), " +
                "2, 'CREDIT_CARD', 160.0)";
        entityManager.createNativeQuery(insertTickets).executeUpdate();
    }
}
