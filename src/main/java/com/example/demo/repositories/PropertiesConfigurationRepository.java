package com.example.demo.repositories;

import com.example.demo.entities.PropertiesConfigurationDB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertiesConfigurationRepository extends JpaRepository<PropertiesConfigurationDB,Long> {

    List<PropertiesConfigurationDB> findAllByIsPropertyUpdateInFile(boolean flag);
    PropertiesConfigurationDB findPropertiesConfigurationByPropertyName(String propertyName);

}
