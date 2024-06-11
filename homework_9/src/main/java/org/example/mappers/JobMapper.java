package org.example.mappers;

import org.example.dto.JobDto;
import org.example.dto.JobDto;
import org.example.models.Job;
import org.example.models.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JobMapper {


    JobMapper INSTANCE = Mappers.getMapper(JobMapper.class);

    JobDto toJobDto(Job j);
    Job toModel(JobDto dto);


}
