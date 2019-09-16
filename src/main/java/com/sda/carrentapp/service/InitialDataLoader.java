package com.sda.carrentapp.service;

import com.sda.carrentapp.entity.*;
import com.sda.carrentapp.repository.BookingRepository;
import com.sda.carrentapp.repository.CarRepository;
import com.sda.carrentapp.repository.DepartmentRepository;
import com.sda.carrentapp.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@AllArgsConstructor
public class InitialDataLoader {
    private CarRepository carsRepository;
    private BookingRepository bookingRepository;
    private UserRepository employeeRepository;
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserService userServiceSecurity;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @EventListener
    @Transactional
    public void init(ApplicationReadyEvent applicationReadyEvent) {
        RentalOffice rentalOffice = new RentalOffice("C@rs", "www.rental.com", "Warsaw", "Marian Kowalski", "logo");

        Department department1 = Department.builder().address("Kraków").rentalOffice(rentalOffice).entityStatus(EntityStatus.ACTIVE).build();
        Department department2 = Department.builder().address("Gdańsk").rentalOffice(rentalOffice).entityStatus(EntityStatus.ACTIVE).build();
        Department department3 = Department.builder().address("Sopot").rentalOffice(rentalOffice).entityStatus(EntityStatus.ACTIVE).build();
        Department department4 = Department.builder().address("Warszawa").rentalOffice(rentalOffice).entityStatus(EntityStatus.ACTIVE).build();
        Department unsign = Department.builder().address("unsign").rentalOffice(rentalOffice).entityStatus(EntityStatus.ARCHIVED).build();

        List<Department> departments = new ArrayList<>(Arrays.asList(department1, department2, department3, department4, unsign));
        departments.forEach(departmentRepository::save);

        Car car1 = Car.builder()
                .brand("Hyundai")
                .model("i30")
                .bodyType("hatchback")
                .productionYear("2010")
                .color("grey")
                .mileage(87_000d)
                .status(Status.AVAILABLE)
                .dailyFee(35.50)
                .department(department1)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        Car car2 = Car.builder()
                .brand("Ford")
                .model("Fiesta")
                .bodyType("hatchback")
                .productionYear("2014")
                .color("blue")
                .mileage(36_500d)
                .status(Status.AVAILABLE)
                .dailyFee(20.50)
                .department(department1)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        Car car3 = Car.builder()
                .brand("Ford")
                .model("Focus")
                .bodyType("hatchback")
                .productionYear("2012")
                .color("black")
                .mileage(40_800d)
                .status(Status.AVAILABLE)
                .dailyFee(30.20)
                .department(department2)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        Car car4 = Car.builder()
                .brand("Hyundai")
                .model("i50")
                .bodyType("combi")
                .productionYear("2011")
                .color("grey")
                .mileage(83_200d)
                .status(Status.AVAILABLE)
                .dailyFee(35.50)
                .department(department2)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        Car car5 = Car.builder()
                .brand("Fiat")
                .model("Multipla")
                .bodyType("VAN")
                .productionYear("2015")
                .color("grey")
                .mileage(87_000d)
                .status(Status.AVAILABLE)
                .dailyFee(35.50)
                .department(department3)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        Car car6 = Car.builder()
                .brand("Audi")
                .model("Q5")
                .bodyType("SUV")
                .productionYear("2018")
                .color("silver")
                .mileage(25_300d)
                .status(Status.AVAILABLE)
                .dailyFee(52.30)
                .department(department3)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        Car car7 = Car.builder()
                .brand("Audi")
                .model("Q7")
                .bodyType("SUV")
                .productionYear("2019")
                .color("white")
                .mileage(35_300d)
                .status(Status.AVAILABLE)
                .dailyFee(82.30)
                .department(department4)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        Car car8 = Car.builder()
                .brand("Audi")
                .model("Q7")
                .bodyType("SUV")
                .productionYear("2016")
                .color("black")
                .mileage(55_300d)
                .status(Status.AVAILABLE)
                .dailyFee(62.30)
                .department(department4)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        List<Car> cars = new ArrayList<>(Arrays.asList(car1, car2, car3, car4, car5, car6, car7, car8));
        cars.forEach(carsRepository::save);

        User user1 = User.builder()
                .firstName("Adam")
                .lastName("Małysz")
                .email("amalysz@gmail.com")
                .role(Role.USER)
                .username("adam1")
                .password(encoder.encode("malys"))
                .address("Kraków")
                .department(unsign)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        User user2 = User.builder()
                .firstName("Anna")
                .lastName("Nowak")
                .email("anowak@gmail.com")
                .role(Role.USER)
                .username("anna")
                .password(encoder.encode("nowak"))
                .address("Gdańsk")
                .department(unsign)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        User user3 = User.builder()
                .firstName("Marta")
                .lastName("Kowalska")
                .role(Role.USER)
                .email("mkowalska@gmail.com")
                .username("marta")
                .password(encoder.encode("kowalska"))
                .address("Sopot")
                .department(unsign)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        Booking booking1 = Booking.builder()
                .reservationDate(LocalDate.now().minusDays(3))
                .startDate(LocalDate.now().minusDays(2))
                .endDate(LocalDate.now().minusDays(1))
                .fee(200d)
                .rentDepartment(department1)
                .returnDepartment(department2)
                .user(user1)
                .car(car1)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        Booking booking2 = Booking.builder()
                .reservationDate(LocalDate.now().minusDays(13))
                .startDate(LocalDate.now().minusDays(12))
                .endDate(LocalDate.now().minusDays(11))
                .fee(300d)
                .rentDepartment(department1)
                .returnDepartment(department3)
                .user(user2)
                .car(car2)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        Booking booking3 = Booking.builder()
                .reservationDate(LocalDate.now().minusDays(3))
                .startDate(LocalDate.now().minusDays(2))
                .endDate(LocalDate.now().minusDays(1))
                .fee(400d)
                .rentDepartment(department2)
                .returnDepartment(department3)
                .user(user3)
                .car(car5)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        bookingRepository.save(booking1);
        bookingRepository.save(booking2);
        bookingRepository.save(booking3);

        User employee1 = User.builder()
                .firstName("Jacek")
                .lastName("Herrmann")
                .email("jherrmann@email.com")
                .role(Role.ADMIN)
                .username("jacek")
                .password(encoder.encode("jacek"))
                .address("Gdańsk")
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        User employee2 = User.builder()
                .firstName("Lukasz")
                .lastName("Mie")
                .role(Role.ADMIN)
                .username("lukasz")
                .password(encoder.encode("lukasz"))
                .address("Gdańsk")
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        User employee3 = User.builder()
                .firstName("Michał")
                .lastName("Miler")
                .role(Role.EMPLOYEE)
                .username("michal")
                .password(encoder.encode("miler"))
                .department(department2)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        User employee4 = User.builder()
                .firstName("Marcin")
                .lastName("Baczyński")
                .role(Role.EMPLOYEE)
                .username("marcin")
                .password(encoder.encode("baczynski"))
                .department(department2)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        User employee5 = User.builder()
                .firstName("Andrzej")
                .lastName("Ponitka")
                .role(Role.EMPLOYEE)
                .username("andrzej")
                .password(encoder.encode("ponitka"))
                .department(department3)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        User employee6 = User.builder()
                .firstName("Szymon")
                .lastName("Nowak")
                .role(Role.EMPLOYEE)
                .username("szymon")
                .password(encoder.encode("nowak"))
                .department(department3)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        User employee7 = User.builder()
                .firstName("Monika")
                .lastName("Malek")
                .role(Role.EMPLOYEE)
                .username("monika")
                .password(encoder.encode("malek"))
                .department(department4)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        User employee8 = User.builder()
                .firstName("Patryk")
                .lastName("Nowicki")
                .role(Role.EMPLOYEE)
                .username("patryk")
                .password(encoder.encode("nowicki"))
                .department(department4)
                .entityStatus(EntityStatus.ACTIVE)
                .build();

        List<User> employees = new ArrayList<>
                (Arrays.asList(employee1, employee2, employee3, employee4, employee5, employee6, employee7, employee8));

        employees.forEach(employeeRepository::save);
    }
}
