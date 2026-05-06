CREATE TABLE appointments (
    id UUID PRIMARY KEY,
    client_id UUID NOT NULL,
    service_name VARCHAR(100) NOT NULL,
    appointment_date TIMESTAMP NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_client FOREIGN KEY (client_id) REFERENCES users(id)
);
