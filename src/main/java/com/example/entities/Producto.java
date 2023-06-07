package com.example.entities;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "productos")

public class Producto implements Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "este campo no puede ser null")
    @NotBlank(message = "el nombre del producto no puede estar vacío")
    @Size( min= 4, message = "el nombre no puede tener menos de 4 caracteres")
    @Size(max = 25, message = "el nombre no puede exceder de 25 caracteres")
    private String name;

    @NotBlank(message = "la descripcion no puede estar vacio")
    private String descripcion;

    @Min(value = 0, message = "el precio no puede ser menor que cero")
    private int stock;

    @Min(value = 0, message = "el precio no puede serr negativo")
    private double price;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "el proyecto tiene que tener una presentacion")
    @JsonIgnoreProperties({"hebernateLazyInitializer", "handler"})
    private Presentacion presentacion;

    
}
