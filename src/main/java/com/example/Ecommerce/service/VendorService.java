package com.example.Ecommerce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.Ecommerce.ClassDto.VendorDTO;
import com.example.Ecommerce.entity.Vendors;
import com.example.Ecommerce.exception.ResourceNotFoundException;
import com.example.Ecommerce.repository.VendorRepository;

@Service
public class VendorService {

    @Autowired
    private VendorRepository repo;

    // ================= Entity -> DTO =================

    public VendorDTO convertToDTO(Vendors vendor) {

        VendorDTO dto = new VendorDTO();

        dto.setId(vendor.getId());
        dto.setName(vendor.getName());
        dto.setCompanyName(vendor.getCompanyName());
        dto.setEmail(vendor.getEmail());
        dto.setPhone(vendor.getPhone());
        dto.setAddress(vendor.getAddress());

        return dto;
    }

    // ================= DTO -> Entity =================

    public Vendors convertToEntity(VendorDTO dto) {

        Vendors vendor = new Vendors();

        vendor.setId(dto.getId());
        vendor.setName(dto.getName());
        vendor.setCompanyName(dto.getCompanyName());
        vendor.setEmail(dto.getEmail());
        vendor.setPhone(dto.getPhone());
        vendor.setAddress(dto.getAddress());

        return vendor;
    }

    // ================= Save =================

    public VendorDTO save(VendorDTO dto) {

        Vendors vendor = convertToEntity(dto);

        Vendors savedVendor = repo.save(vendor);

        return convertToDTO(savedVendor);
    }

    // ================= Get All =================

    @Cacheable("VendorService")
    public List<VendorDTO> getAll() {

        List<Vendors> vendors = repo.findAll();

        List<VendorDTO> dtoList = new ArrayList<>();

        for (Vendors vendor : vendors) {

            dtoList.add(convertToDTO(vendor));

        }

        return dtoList;
    }

    // ================= Get By Id =================

    @Cacheable(value = "VendorService", key = "#id")
    public VendorDTO getById(Long id) {

        Vendors vendor = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor Not Found : " + id));

        VendorDTO dto = convertToDTO(vendor);

        return dto;
    }

    // ================= Update =================

    @CacheEvict(value = "VendorService", allEntries = true)
    public VendorDTO update(Long id, VendorDTO dto) {

        Vendors vendor = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor Not Found : " + id));

        vendor.setName(dto.getName());
        vendor.setCompanyName(dto.getCompanyName());
        vendor.setEmail(dto.getEmail());
        vendor.setPhone(dto.getPhone());
        vendor.setAddress(dto.getAddress());

        Vendors updatedVendor = repo.save(vendor);

        return convertToDTO(updatedVendor);
    }

    // ================= Delete =================

    @CacheEvict(value = "VendorService", allEntries = true)
    public String delete(Long id) {

        repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor Not Found : " + id));

        repo.deleteById(id);

        return "Vendor Deleted Successfully";
    }

    // ================= Sorting =================

	@Cacheable(value = "vendors", key = "#field")

    public List<VendorDTO> sorting(String field) {

        List<Vendors> vendors = repo.findAll(Sort.by(field));

        List<VendorDTO> dtoList = new ArrayList<>();

        for (Vendors vendor : vendors) {

            dtoList.add(convertToDTO(vendor));

        }

        return dtoList;
    }

    // ================= Pagination =================

    public Page<VendorDTO> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Vendors> vendorPage = repo.findAll(pageable);

        return vendorPage.map(this::convertToDTO);
    }

}