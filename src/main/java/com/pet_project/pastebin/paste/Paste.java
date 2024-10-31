package com.pet_project.pastebin.paste;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paste {
    @Id
    private String id;  // hash

    @NotEmpty(message = "This field shouldn't be empty")
    private String text;

    private String password;

    @CreatedDate
    private LocalDate createdDate;
}
