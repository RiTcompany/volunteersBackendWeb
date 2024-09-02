package org.example.mapper;

import org.example.pojo.dto.BirthdayDto;
import org.example.utils.DateUtil;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class DateMapper {
    public BirthdayDto birthdayWithAge(Date birthdayDate) {
        BirthdayDto birthdayDto = new BirthdayDto();
        birthdayDto.setBirthday(birthdayDate);
        birthdayDto.setAge(DateUtil.age(birthdayDate));
        return birthdayDto;
    }
}
