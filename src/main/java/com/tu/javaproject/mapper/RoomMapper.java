
package com.tu.javaproject.mapper;

import com.tu.javaproject.dto.RoomDto;
import com.tu.javaproject.model.Room;
import com.tu.javaproject.model.RoomExtra;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoomMapper {

    @Mapping(target = "hotelId", source = "hotel.id")
    @Mapping(target = "extraIds", source = "extras")
    RoomDto toDto(Room room);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "extras", ignore = true)
    Room toEntity(RoomDto dto);

    default Set<Long> mapExtraIds(Set<RoomExtra> extras) {

        if (extras == null) {
            return Set.of();
        }

        return extras.stream()
                .map(RoomExtra::getId)
                .collect(Collectors.toSet());
    }
}
