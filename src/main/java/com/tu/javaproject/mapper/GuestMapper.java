
package com.tu.javaproject.mapper;

import com.tu.javaproject.dto.GuestDto;
import com.tu.javaproject.model.Guest;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    GuestDto toDto(Guest guest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    Guest toEntity(GuestDto dto);
}
