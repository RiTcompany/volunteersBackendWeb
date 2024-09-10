package org.example.pojo.dto.update;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeadquartersUpdateDto {
    private Long id;
    private String name;
    private String location;
    private String contact;
}
