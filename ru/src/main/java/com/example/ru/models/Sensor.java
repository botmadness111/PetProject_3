package com.example.ru.models;

import com.example.ru.DTO.SensorDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "sensor")
public class Sensor implements Serializable {

    public Sensor() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    @Size(min = 3, max = 30, message = "length of name should be between 3 and 30")
    private String name;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "created_who")
    private String created_who;


//    @OneToMany(mappedBy = "sensor")
//    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
//    private List<Measurements> measurements;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public String getCreated_who() {
        return created_who;
    }

    public void setCreated_who(String created_who) {
        this.created_who = created_who;
    }

//    public List<Measurements> getMeasurements() {
//        return measurements;
//    }
//
//    public void setMeasurements(List<Measurements> measurements) {
//        this.measurements = measurements;
//    }

    @Override
    public String toString() {
        return "Sensor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", created_who='" + created_who + '\'' +
                ", created_at=" + created_at +
                '}';
    }
}
