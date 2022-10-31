package com.example.watchlateryoutubebot.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "TEST_ENTITIES")
public class TestEntity {

    protected TestEntity() {}

    public TestEntity(String name, Integer intValue) {
        this.name = name;
        this.intValue = intValue;
    }

    public String testString() {
        return this.name + this.intValue;
    }
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private Integer intValue;

}
