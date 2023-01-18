CREATE TABLE flight (
    id INT AUTO_INCREMENT PRIMARY KEY,
    origin INT NOT NULL,
    destination INT NOT NULL,
    departure DATETIME NOT NULL,
    estimated_arrival DATETIME NOT NULL,
    actual_arrival DATETIME,
    seat_rows INT NOT NULL,
    seat_columns INT NOT NULL,
    FOREIGN KEY (origin) REFERENCES airport(id),
    FOREIGN KEY (destination) REFERENCES airport(id),
    CONSTRAINT seat_columns_check CHECK (seat_columns BETWEEN 1 AND 26)
);