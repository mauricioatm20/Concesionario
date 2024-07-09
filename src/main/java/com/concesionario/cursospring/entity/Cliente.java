package com.concesionario.cursospring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "clientes")
public class Cliente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String Name;
    private String DNI;
    private Integer NumCompras;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
    private Set<Venta> venta;
}
