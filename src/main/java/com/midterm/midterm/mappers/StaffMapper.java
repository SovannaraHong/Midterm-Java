package com.midterm.midterm.mappers;


import com.midterm.midterm.dto.request.StaffRequest;
import com.midterm.midterm.dto.response.StaffResponse;
import com.midterm.midterm.entities.Staff;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StaffMapper {

    StaffResponse toResponse(Staff staff);

    @Mapping(target = "password", ignore = true)
        // password is hashed/set manually in the service, never mapped directly
    Staff toEntity(StaffRequest request);
}