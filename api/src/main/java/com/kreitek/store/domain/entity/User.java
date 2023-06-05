package com.kreitek.store.domain.entity;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSequence")
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    @Size(min = 3, max = 100)
    private String nick;

    @Column(length = 100, nullable = false)
    @Size(min = 3, max = 100)
    private String nombre;

    @Column(length = 100, nullable = false)
    @Size(min = 3, max = 100)
    private String apellidos;

    @Column(nullable = false)
    @Digits(integer = 20, fraction = 0)
    private Long telefono;

    @Column(length = 100, nullable = false,unique = true)
    @Email
    private String email;

    @Column(length = 100, nullable = false)
    @NotBlank
    private String password;

    public User() {
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Long getTelefono() {
        return telefono;
    }

    public void setTelefono(Long telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
