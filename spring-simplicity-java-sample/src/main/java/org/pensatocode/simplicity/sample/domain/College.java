package org.pensatocode.simplicity.sample.domain;

import org.springframework.data.annotation.Id;

import java.util.Objects;

public class College {
    @Id
    Long id = 0L;
    String name = "";
    String nameOfCity = "";

    public College() {
    }

    public College(Long id, String name, String nameOfCity) {
        this.id = id;
        this.name = name;
        this.nameOfCity = nameOfCity;
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

    public String getNameOfCity() {
        return nameOfCity;
    }

    public void setNameOfCity(String nameOfCity) {
        this.nameOfCity = nameOfCity;
    }

    @Override
    public String toString() {
        return "College=[" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nameOfCity='" + nameOfCity + '\'' +
                ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        College college = (College) o;
        return Objects.equals(id, college.id) &&
                Objects.equals(name, college.name) &&
                Objects.equals(nameOfCity, college.nameOfCity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, nameOfCity);
    }
}
