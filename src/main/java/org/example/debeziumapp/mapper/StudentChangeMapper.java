package org.example.debeziumapp.mapper;

import org.example.debeziumapp.dto.StudentChangeDto;
import org.example.debeziumapp.entity.StudentChange;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StudentChangeMapper {

    StudentChangeDto toDto(StudentChange studentChange);
}
