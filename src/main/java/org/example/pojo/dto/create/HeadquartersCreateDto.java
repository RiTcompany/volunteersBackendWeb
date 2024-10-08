package org.example.pojo.dto.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HeadquartersCreateDto {
    private Long federalId;
    private Double rank;
    private String name;
    private String location;
    private String contact;
    private Long teamLeaderVolunteerId;
}
