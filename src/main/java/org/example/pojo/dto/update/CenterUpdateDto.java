package org.example.pojo.dto.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CenterUpdateDto {
    private Long id;
    private String name;
    private String location;
    private String contact;
}