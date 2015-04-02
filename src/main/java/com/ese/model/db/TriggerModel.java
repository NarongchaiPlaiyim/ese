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

    @Column(name="isvalid")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqid-gen")
    @SequenceGenerator(name = "seqid-gen", sequenceName = "RTDS_ADSINPUT_SEQ", allocationSize = 1, initialValue = 0)
    private int isValid;
}
