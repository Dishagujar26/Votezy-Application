package com.disha.votezy.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class VoterRequestDTO {

//    @NotNull(groups = VoterOnCreate.class, message = "Age is required!")
//    @Min(value = 18, message = "Age must be at least 18 to register as a voter")
//    private Integer age;

//    @NotNull(groups = VoterOnCreate.class, message = "Name is required!")
    @Size(min = 1, message = "Name must contain at least 1 character")
    private String name;

//    @NotNull(groups = VoterOnCreate.class, message = "Email is required!")
    @Email(message = "Invalid email format")
    private String email;
}
