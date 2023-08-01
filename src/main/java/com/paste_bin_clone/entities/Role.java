package com.paste_bin_clone.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "roles")
@JsonIgnoreProperties("users")
@Data
@Accessors(chain = true)
public class Role {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

}
