package com.example.Coffee.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String name;

    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String phone;

    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String email;

    @NotEmpty(message = "Must not be empty")
    @Size(max = 255, message = "Must be less than 255 characters")
    private String status;

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Feedback feedback = (Feedback) o;
        return Objects.equals(id, feedback.id) && Objects.equals(name, feedback.name) && Objects.equals(phone, feedback.phone) && Objects.equals(email, feedback.email) && Objects.equals(status, feedback.status);
    }
}
