package com.sda.carrentapp.repository;

import com.sda.carrentapp.entity.Car;
import com.sda.carrentapp.entity.Department;
import com.sda.carrentapp.entity.EntityStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    List<Car> findAllByEntityStatus(EntityStatus entityStatus);

    //    @Query("select c from Car c where (c.) like lower(concat('%', :search, '%')) " +
//            "or lower(c.lastName) like lower(concat('%', :search, '%'))")
//    SELECT * FROM cars left join bookings on cars.id = bookings.car_id where cars.status = 'AVAILABLE' and cars.department_id = 1  and  bookings.end_date >= '2019-09-01'
//    @Query("select c from Car c left join c.bookings b where c.department=:department and c.status = 'AVAILABLE' and b.end_date > '2019-09-01' ")
//    @Query("select c from Car c left join c.bookings b where c.department_id = 1 and c.status = 'AVAILABLE' and b.end_date > '2019-09-01' ")
    @Query("select c from cars c left join c.bookings b where " +
            "c.department = :rentDepFromForm and " +
            "(c.status = 'AVAILABLE' " +
//            "or b.startDate <= :bookingEndDateFromForm)")
            "and b.startDate < :bookingEndDateFromForm)")
    Set<Car> findAllByRentDepartmentAndDateAndStatus(@Param("bookingEndDateFromForm") LocalDate c,
                                                     @Param("rentDepFromForm") Department rentDepFromForm);

    List<Car> findAllByDepartment(Department department);

}
