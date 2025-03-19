package org.example.pojo.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BirthdayDto {
    private Date birthday;
    private Integer age;
}
