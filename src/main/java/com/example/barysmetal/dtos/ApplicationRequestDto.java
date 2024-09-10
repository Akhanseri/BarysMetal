package com.example.barysmetal.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ApplicationRequestDto {
    String fullName;
    String number;
    String comment;  // Optional, can be null
}
