package com.example.demo.config.db;

import com.example.demo.entities.PropertiesConfigurationDB;
import com.example.demo.repositories.PropertiesConfigurationRepository;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.springframework.core.env.*;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class ConfigMap {

    //    For serving ui messages and static content
    private Map<String,String> configMap = new HashMap<>();
    //    For updating app.property file only
    private Map<String,Object> configMapPropertyFileUpdate = new HashMap<>();
    String propsFileName = "application.properties";
    PropertiesConfigurationRepository propertiesConfigurationRepository;
    private ConfigurableEnvironment environment;

    public ConfigMap(PropertiesConfigurationRepository propertiesConfigurationRepository, ConfigurableEnvironment environment) {
        this.propertiesConfigurationRepository = propertiesConfigurationRepository;
        this.environment = environment;
    }

    @PostConstruct
    void init(){
        readAllConfigPropertiesFromDBToConfigMapPropertyFileUpdate();
        readAllConfigPropertiesFromDBToConfigMap();
    }

//    Updating property source after getting from db
    private void reload() throws IOException {
        MutablePropertySources propertySources = environment.getPropertySources();
        PropertySource<?> resourcePropertySource = propertySources.get("class path resource [application.properties]");
        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("/application.properties");
        properties.load(inputStream);
        inputStream.close();
        propertySources.replace("class path resource [application.properties]", new PropertiesPropertySource("class path resource [application.properties]", properties));
    }

    //    Refreshes config map from db
    public void readAllConfigPropertiesFromDBToConfigMap() {
        List<PropertiesConfigurationDB> propertiesToBeUpdatedInConfigMap = propertiesConfigurationRepository.findAllByIsPropertyUpdateInFile(false);
        configMap = propertiesToBeUpdatedInConfigMap.stream().collect(Collectors.toMap(PropertiesConfigurationDB::getPropertyName, PropertiesConfigurationDB::getPropertyValue));
    }

    //    creates a map to update app.properties file
    public void readAllConfigPropertiesFromDBToConfigMapPropertyFileUpdate() {
        List<PropertiesConfigurationDB> propertiesToBeUpdatedInPropertyFile = propertiesConfigurationRepository.findAllByIsPropertyUpdateInFile(true);
        configMapPropertyFileUpdate = propertiesToBeUpdatedInPropertyFile.stream().collect(Collectors.toMap(PropertiesConfigurationDB::getPropertyName, PropertiesConfigurationDB::getPropertyValue));
        updateConfigPropertiesInFile();
    }

    //    adding properties from db to app.properties file
    private void updateConfigPropertiesInFile() {
//        AbstractConfiguration config = ConfigurationManager.getConfigInstance();
//        configMapPropertyFileUpdate.forEach((key,value) -> {
//                config.setProperty(key, String.valueOf(value));
//        });

        try {
            Parameters params = new Parameters();
            FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                            .configure(params.properties()
                                    .setFileName(propsFileName));

            Configuration config = builder.getConfiguration();
            configMapPropertyFileUpdate.forEach((key,value) -> {
                config.setProperty(key, String.valueOf(value));
            });
            builder.save();
            reload();
        }
            catch (Exception e) {
            e.printStackTrace();
        }
    }

//    For ui messages and static content
    public String getConfigProperty(String key) {
        String value = configMap.get(key);
        if(Objects.nonNull(value)){
            readAllConfigPropertiesFromDBToConfigMap();
            return configMap.get(key);
        }
        return value;
    }

//    For getting data from app.properties file like hystrix timeout etc
    public String getConfigPropertyFromEnvironment(String key){
        return environment.getProperty(key);
    }

}
