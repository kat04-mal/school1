CREATE TABLE car (
                     id BIGSERIAL PRIMARY KEY,
                     brand VARCHAR(255) NOT NULL,
                     model VARCHAR(255) NOT NULL,
                     price DECIMAL(10,2) NOT NULL
);

CREATE TABLE person (
                        id BIGSERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        age INT NOT NULL,
                        has_driver_license BOOLEAN NOT NULL,
                        car_id BIGINT,

                        CONSTRAINT fk_person_car
                            FOREIGN KEY (car_id)
                                REFERENCES car(id)
);