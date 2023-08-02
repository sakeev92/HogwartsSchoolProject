CREATE TABLE Cars (
                      car_id INT PRIMARY KEY,
                      make VARCHAR(50) not null ,
                      model VARCHAR(50) not null ,
                      price DECIMAL(10, 2) not null
);
CREATE TABLE People (
                        person_id INT PRIMARY KEY,
                        name VARCHAR(50) not null ,
                        age INT not null ,
                        has_license BOOLEAN not null ,
                        car_id INT REFERENCES Cars (car_id) not null
);