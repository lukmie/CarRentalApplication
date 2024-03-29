# CarRentalApplication

<img src="https://github.com/lukmie/CarRentalApplication/blob/master/src/main/resources/static/img/main.PNG" width="800"/>
Spring MVC application designed for car rental company as a final project in Software Development Academy.

## Built With
* Spring Boot 2.1.8
* Spring Security
* Spring MVC
* H2 database 1.4.199
* Maven 
* Thymeleaf 3.0.9
* Bootstrap 4
* Lombok 1.18.8

## Features
### 1. Not authorized user
#### 1.1 Access to main page
<img src="https://github.com/lukmie/CarRentalApplication/blob/master/src/main/resources/static/img/unauthorized-main.PNG" width="800"/>

#### 1.2 Access to sign up page
<img src="https://github.com/lukmie/CarRentalApplication/blob/master/src/main/resources/static/img/sign-up.PNG" width="800"/>

#### 1.3 Username already exist error(login must be unique)
<img src="https://github.com/lukmie/CarRentalApplication/blob/master/src/main/resources/static/img/sign-up-error.PNG" width="800"/>

#### 1.4 Access to sign in page
<img src="https://github.com/lukmie/CarRentalApplication/blob/master/src/main/resources/static/img/sign-in.PNG" width="800"/>

#### 1.5 Access to fleet and branches
Not authorized user can view available cars and find location of the branch.




**Authorized user can:**
- Book a car at the selected date and localization;
- Access bookings, where he can edit or delete order;
- Change account settings.

**Admin(manager) can:**
- Add new and modify existing cars;
- Add new and modify existing departments;
- Add new and modify existing employees and users;
- Start and finish reservation of a car.

... and many more, full description will be added soon.


