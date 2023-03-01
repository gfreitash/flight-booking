CREATE TABLE booking (
    flight_id INT NOT NULL,
    passenger_id INT NOT NULL,
    ticket INT NOT NULL,
    seat_row INT NOT NULL,
    seat_column INT NOT NULL,
    PRIMARY KEY (flight_id, passenger_id),
    FOREIGN KEY (flight_id) REFERENCES flight(id),
    FOREIGN KEY (passenger_id) REFERENCES passenger(id),
    CONSTRAINT ticket_unique UNIQUE (ticket),
    CONSTRAINT seat_unique UNIQUE (flight_id,seat_row, seat_column)
);