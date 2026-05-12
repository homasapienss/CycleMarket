package com.example.cyclemarket.services;

import com.example.cyclemarket.entities.Manufacturer;
import com.example.cyclemarket.exception.notfound.ManufacturerNotFoundException;
import com.example.cyclemarket.repos.ManufacturerRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ManufacturerService {
    private final ManufacturerRepo manufacturerRepo;

    public List<Manufacturer> getAllManufacturers() {
        return manufacturerRepo.findAll();
    }
    public Manufacturer getById(Long id) {
        return manufacturerRepo.findById(id).orElseThrow(ManufacturerNotFoundException::new);
    }
}
