package com.api.rest.unittestsspringboot.models;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter // Genera métodos "setter" para los campos de la clase.
@Getter // Genera métodos "getter" para los campos de la clase.
@AllArgsConstructor // Genera un constructor que toma como argumentos todos los campos de la clase.
@NoArgsConstructor // Genera un constructor sin argumentos.
@Builder // Anotación utilizada para generar un patrón de diseño de construcción de objetos (Builder Pattern).


@Entity
@Table(name = "empleados")
public class EmpleadoModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;
    
    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private String apellido;
    
    @Column(nullable = false)
    private String email;
}
