package com.midterm.midterm.services.serviceimpl;

import com.midterm.midterm.dto.request.StaffRequest;
import com.midterm.midterm.dto.response.StaffResponse;
import com.midterm.midterm.entities.Staff;
import com.midterm.midterm.exception.DuplicateResourceException;
import com.midterm.midterm.exception.ResourceNotFoundException;
import com.midterm.midterm.mappers.StaffMapper;
import com.midterm.midterm.repository.StaffRepository;
import com.midterm.midterm.services.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;

    @Override
    public List<StaffResponse> getAll() {
        return staffRepository.findAll()
                .stream()
                .map(staffMapper::toResponse)
                .toList();
    }

    @Override
    public StaffResponse getById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("Staff", id));
        return staffMapper.toResponse(staff);
    }

    @Override
    public StaffResponse create(StaffRequest request) {
        if (staffRepository.existsByUserName(request.getUserName())) {
            throw new DuplicateResourceException("Username '" + request.getUserName() + "' is already taken");
        }
        Staff staff = staffMapper.toEntity(request);
        staff.setPassword(request.getPassword());
        return staffMapper.toResponse(staffRepository.save(staff));
    }

    @Override
    public StaffResponse update(Long id, StaffRequest request) {
        Staff existing = staffRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("Staff", id));

        if (!existing.getUserName().equals(request.getUserName())
                && staffRepository.existsByUserName(request.getUserName())) {
            throw new DuplicateResourceException("Username '" + request.getUserName() + "' is already taken");
        }

        existing.setUserName(request.getUserName());
        existing.setRole(request.getRole());
        return staffMapper.toResponse(staffRepository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!staffRepository.existsById(id)) {
            throw ResourceNotFoundException.notFoundException("Staff", id);
        }
        staffRepository.deleteById(id);
    }
}