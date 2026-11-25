
package com.tu.javaproject.mapper;

import com.tu.javaproject.dto.SeasonDto;
import com.tu.javaproject.model.Season;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SeasonMapper {

    SeasonDto toDto(Season season);

    @Mapping(target = "id", ignore = true)
    Season toEntity(SeasonDto dto);
}
