CREATE TABLE airport (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    iata_code VARCHAR(3) NOT NULL,
    city VARCHAR(255) NOT NULL,
    state VARCHAR(255) NOT NULL,
    state_abbreviation CHAR(2) NOT NULL,
    latitude DECIMAL(9,6) NOT NULL,
    longitude DECIMAL(9,6) NOT NULL,
    yearly_passengers INT NULL,
    CONSTRAINT iata_code_unique UNIQUE (iata_code)
);
