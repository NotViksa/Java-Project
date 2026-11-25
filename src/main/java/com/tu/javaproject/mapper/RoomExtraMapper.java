
package com.tu.javaproject.mapper;

import com.tu.javaproject.dto.RoomExtraDto;
import com.tu.javaproject.model.RoomExtra;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoomExtraMapper {

    RoomExtraDto toDto(RoomExtra extra);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    RoomExtra toEntity(RoomExtraDto dto);
}
