package com.midterm.midterm.services.serviceimpl;

import com.midterm.midterm.dto.request.StaffRequest;
import com.midterm.midterm.dto.response.StaffResponse;
import com.midterm.midterm.entities.Sale;
import com.midterm.midterm.entities.Staff;
import com.midterm.midterm.enums.Role;
import com.midterm.midterm.exception.CannotDeleteAdminException;
import com.midterm.midterm.exception.CannotDeleteSelfException;
import com.midterm.midterm.exception.DuplicateResourceException;
import com.midterm.midterm.exception.ResourceNotFoundException;
import com.midterm.midterm.mappers.StaffMapper;
import com.midterm.midterm.repository.SaleRepository;
import com.midterm.midterm.repository.StaffRepository;
import com.midterm.midterm.services.FileStorageService;
import com.midterm.midterm.services.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final StaffMapper staffMapper;
    private final FileStorageService fileStorageService;
    private final SaleRepository repository;

    @Value("${app.upload.base-url}")
    private String uploadBaseUrl;

    @Override
    public List<StaffResponse> getAll() {
        return staffRepository.findAll()
                .stream()
                .map(staffMapper::toResponse)
                .toList();
    }

    @Override
    public StaffResponse getById(Long id) {
        Staff staff = findStaffOrThrow(id);
        return staffMapper.toResponse(staff);
    }

    @Override
    public StaffResponse create(StaffRequest request) {
        if (staffRepository.existsByUserName(request.getUserName())) {
            throw new DuplicateResourceException("Username '" + request.getUserName() + "' is already taken");
        }
        Staff staff = staffMapper.toEntity(request);
        staff.setPassword(request.getPassword());
        if (staff.getStatus() == null) {
            staff.setStatus(true);
        }
        return staffMapper.toResponse(staffRepository.save(staff));
    }

    @Override
    public StaffResponse update(Long id, StaffRequest request) {
        Staff existing = findStaffOrThrow(id);

        if (!existing.getUserName().equals(request.getUserName())
                && staffRepository.existsByUserName(request.getUserName())) {
            throw new DuplicateResourceException("Username '" + request.getUserName() + "' is already taken");
        }

        existing.setUserName(request.getUserName());
        existing.setRole(request.getRole());
        existing.setStatus(request.getStatus());
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
        existing.setPassword(request.getPassword()); 
    }
        return staffMapper.toResponse(staffRepository.save(existing));
    }

    @Override
    public void delete(Long id, Long currentStaffId) {

        Staff staff = findStaffOrThrow(id);

        if (staff.getRole() == Role.ADMIN) {
            throw new CannotDeleteAdminException(
                    "Cannot delete staff with ADMIN role"
            );
        }

        if (id.equals(currentStaffId)) {
            throw new CannotDeleteSelfException(
                    "Cannot delete your own account"
            );
        }

        List<Sale> staffSales = repository.findByStaff_Sid(id);
        repository.deleteAll(staffSales);

        staffRepository.delete(staff);
    }

    @Override
    public StaffResponse uploadImage(Long id, MultipartFile file) {
        Staff staff = findStaffOrThrow(id);

        if (staff.getImageUrl() != null && staff.getImageUrl().startsWith(uploadBaseUrl)) {
            String oldFilename = staff.getImageUrl().substring(staff.getImageUrl().lastIndexOf('/') + 1);
            fileStorageService.delete(oldFilename);
        }

        String filename = fileStorageService.store(file);
        staff.setImageUrl(uploadBaseUrl + "/" + filename);

        return staffMapper.toResponse(staffRepository.save(staff));
    }

    private Staff findStaffOrThrow(Long id) {
        return staffRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.notFoundException("Staff", id));
    }
}