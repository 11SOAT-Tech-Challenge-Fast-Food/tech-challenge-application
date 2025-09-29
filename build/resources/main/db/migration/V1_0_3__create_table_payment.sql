CREATE TABLE IF NOT EXISTS payments(
    id UUID primary key,
    external_id varchar(255) not null,
    status varchar(255) not null,
    order_id UUID not null,
    FOREIGN KEY(order_id) REFERENCES orders (id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
