package org.example.debeziumapp.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class PackageDto {

    private Long id;

    private List<Long> changesId;
}
