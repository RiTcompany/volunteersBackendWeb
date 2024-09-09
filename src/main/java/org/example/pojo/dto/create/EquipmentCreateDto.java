package org.example.pojo.dto.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EquipmentCreateDto {
    private Long equipmentId;
    private String type;
    private int year;
}
