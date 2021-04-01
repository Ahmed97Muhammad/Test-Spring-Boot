package com.example.demo.config.db;

import com.example.demo.entities.PropertiesConfigurationDB;
import com.example.demo.repositories.PropertiesConfigurationRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ConfigurationMap {

    private Map<String,String> configMap = new HashMap<>();

    PropertiesConfigurationRepository propertiesConfigurationRepository;

    public ConfigurationMap(PropertiesConfigurationRepository propertiesConfigurationRepository) {
        this.propertiesConfigurationRepository = propertiesConfigurationRepository;
    }

//    Called once spring context is loaded for this bean
    @PostConstruct
    void init(){
        readAllConfigPropertiesFromDBToConfigMap();
    }

//    Read all db tables and add to our configMap
//    Can be called from any service or controller layer if we want to force update our properties
    public void readAllConfigPropertiesFromDBToConfigMap() {
        List<PropertiesConfigurationDB> propertiesToBeUpdatedInConfigMap = propertiesConfigurationRepository.findAllByIsPropertyUpdateInFile(false);
        configMap = propertiesToBeUpdatedInConfigMap.stream().collect(Collectors.toMap(PropertiesConfigurationDB::getPropertyName, PropertiesConfigurationDB::getPropertyValue));
    }

//    Getting property from map and if it is null force reloading it
    public String getConfigProperty(String key) {
        String value = configMap.get(key);
        if(Objects.nonNull(value)){
            readAllConfigPropertiesFromDBToConfigMap();
            return configMap.get(key);
        }
        return value;
    }

}
