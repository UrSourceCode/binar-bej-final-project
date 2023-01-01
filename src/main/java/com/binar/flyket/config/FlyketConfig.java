package com.binar.flyket.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.init.Jackson2RepositoryPopulatorFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class FlyketConfig {
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
     * pre-populate database
     */
    @Bean
    public Jackson2RepositoryPopulatorFactoryBean populatorFactoryBean() {
        Jackson2RepositoryPopulatorFactoryBean factory = new Jackson2RepositoryPopulatorFactoryBean();
        factory.setResources(new Resource[] {
//                new ClassPathResource("datajson/aircraft.json"),
//                new ClassPathResource("datajson/aircraftdetail.json"),
//                new ClassPathResource("datajson/paymentmethod.json"),
//                new ClassPathResource("datajson/flightroute.json"),
//                new ClassPathResource("datajson/detailseat.json"),
//                new ClassPathResource("datajson/flightschedule.json"),
//                new ClassPathResource("datajson/countries.json"),
//                new ClassPathResource("datajson/airports.json"),
        });
        return factory;
    }

}
