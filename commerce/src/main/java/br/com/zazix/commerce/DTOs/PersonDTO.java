package br.com.zazix.commerce.DTOs;

import br.com.zazix.commerce.entities.Person;

import java.time.LocalDate;

public class PersonDTO {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private LocalDate birthDate;


    public PersonDTO() {
    }

    public PersonDTO(Long id, String name, String email, String phone, LocalDate birthDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.birthDate = birthDate;
    }

    public PersonDTO(Person entity ) {
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        phone = entity.getPhone();
        birthDate = entity.getBirthDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}
