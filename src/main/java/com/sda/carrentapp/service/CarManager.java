package com.sda.carrentapp.service;

import com.sda.carrentapp.entity.*;
import com.sda.carrentapp.exception.CarNotFoundException;
import com.sda.carrentapp.repository.CarRepository;
import com.sda.carrentapp.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CarManager {
    private CarRepository carRepository;
    private DepartmentRepository departmentRepository;

    public CarManager(CarRepository carRepository, DepartmentRepository departmentRepository) {
        this.carRepository = carRepository;
        this.departmentRepository = departmentRepository;
    }

    public List<Car> getCars() {
        return carRepository.findAllByEntityStatus(EntityStatus.ACTIVE);
    }

    public Car getCarById(Long id) {
        return carRepository.getOne(id);
    }

    public Set<Car> getCarsByRentDepAndDateAndStatus(LocalDate startDate, Department rentDepartment) {
        return carRepository.findAllByRentDepartmentAndDateAndStatus(startDate, rentDepartment);
    }

    public void saveCar(CreateCarRequest carRequest) {
        Car entity = CarMapper.map(carRequest);
        entity.setEntityStatus(EntityStatus.ACTIVE);
        carRepository.save(entity);
    }

    public void delete(Long id) {
        Car car = carRepository.findById(id).orElseThrow(CarNotFoundException::new);
        car.setEntityStatus(EntityStatus.ARCHIVED);
        carRepository.save(car);
    }

    public void patchCar(Long id, UpdateCarRequest updateCarRequest) {
        Car car = carRepository.findById(id).orElseThrow(CarNotFoundException::new);

        Optional.ofNullable(updateCarRequest.getBrand()).ifPresent(car::setBrand);
        Optional.ofNullable(updateCarRequest.getModel()).ifPresent(car::setModel);
        Optional.ofNullable(updateCarRequest.getBodyType()).ifPresent(car::setBodyType);
        Optional.ofNullable(updateCarRequest.getProductionYear()).ifPresent(car::setProductionYear);
        Optional.ofNullable(updateCarRequest.getColor()).ifPresent(car::setColor);
        Optional.ofNullable(updateCarRequest.getMileage()).ifPresent(car::setMileage);
        Optional.ofNullable(updateCarRequest.getStatus()).ifPresent(car::setStatus);
        Optional.ofNullable(updateCarRequest.getDailyFee()).ifPresent(car::setDailyFee);

        carRepository.save(car);
    }

    public List<String> getAllBodyTypes() {
        return carRepository.findAll().stream()
                .map(Car::getBodyType)
                .distinct()
                .collect(Collectors.toList());
    }
}
