package org.example.pojo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BirthdayDto {
    private Date birthday;
    private Integer age;
}
