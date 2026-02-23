package com.sublimenoir.SublimeNoir.web.dto;

import com.sublimenoir.SublimeNoir.domain.entity.User;
import lombok.Data;

@Data
public class CreateUserRequestDTO {
    private String username;
    private String email;
    private String firstName;
    private String lastName;

    public CreateUserRequestDTO(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }

    // getters & setters ???
}
