package com.example.Coffee.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@Table(name = "educations")
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Title shouldn't be empty")
    @Size(max = 255, message = "Title should be less than 255 characters")
    private String title;
    @NotEmpty(message = "Text shouldn't be empty")
    @Size(max = 255, message = "Text should be less than 255 characters")
    private String text;
    private String photo;
    private boolean active;

    @Override
    public String toString() {
        return "Education{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", active=" + active +
                '}';
    }
}
