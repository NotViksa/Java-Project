
package com.tu.javaproject.mapper;

import com.tu.javaproject.dto.HotelDto;
import com.tu.javaproject.model.Hotel;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    HotelDto toDto(Hotel hotel);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "rooms", ignore = true)
    Hotel toEntity(HotelDto dto);
}
