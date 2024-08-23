package org.example.debeziumapp.mapper;

import org.example.debeziumapp.dto.ChangeDto;
import org.example.debeziumapp.entity.Change;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChangeMapper {

    ChangeDto toDto(Change change);
}
