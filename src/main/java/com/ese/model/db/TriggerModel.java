package com.ese.model.db;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "trigger_model")
@Proxy(lazy=false)
public class TriggerModel {
    @Id
    private int id;

    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_id_seq")
    @SequenceGenerator(name = "student_id_seq",
            sequenceName = "Student_seq",
            allocationSize = 1)
    @Column(name="isvalid")
    private int isValid;
}
