CREATE TABLE IF NOT EXISTS customer(
    id UUID primary key,
    name varchar(255) not null,
    email varchar(255) null,
    cpf varchar(255) null,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_customer_id_name ON customer(id, name);
CREATE INDEX idx_customer_id_cpf ON customer(id, cpf);
CREATE INDEX idx_customer_created_at ON customer(created_at);