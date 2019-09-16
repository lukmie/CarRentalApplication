package com.sda.carrentapp.service;

import com.sda.carrentapp.entity.Car;
import com.sda.carrentapp.entity.CreateCarRequest;

public class CarMapper {
    public static Car map(CreateCarRequest carRequest) {
        Car car = new Car();
        car.setBrand(carRequest.getBrand());
        car.setModel(carRequest.getModel());
        car.setBodyType(carRequest.getBodyType());
        car.setProductionYear(carRequest.getProductionYear());
        car.setColor(carRequest.getColor());
        car.setMileage(carRequest.getMileage());
        car.setStatus(carRequest.getStatus());
        car.setDailyFee(carRequest.getDailyFee());
        return car;
    }
}
