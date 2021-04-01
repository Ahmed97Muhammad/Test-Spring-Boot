package com.example.demo.entities;


import javax.persistence.*;

@Entity
@Table(name = "Properties_Configuration")
public class PropertiesConfigurationDB {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "Property_Name")
    private String propertyName;
    @Column(name = "Property_Value")
    private String propertyValue;
    @Column(name = "Is_Property_Update_In_File")
    private boolean isPropertyUpdateInFile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public boolean isPropertyUpdateInFile() {
        return isPropertyUpdateInFile;
    }

    public void setPropertyUpdateInFile(boolean propertyUpdateInFile) {
        isPropertyUpdateInFile = propertyUpdateInFile;
    }
}
