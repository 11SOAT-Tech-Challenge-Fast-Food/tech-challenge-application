CREATE TABLE IF NOT EXISTS orders(
    id UUID primary key,
    status varchar(255) not null,
    customer_id UUID,
    FOREIGN KEY(customer_id) REFERENCES customer (id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create index idx_order_customer_id on orders(customer_id);
create index idx_order_created_at on orders(created_at);

CREATE TABLE IF NOT EXISTS order_item(
    id UUID primary key,
    product_id UUID not null,
    order_id UUID not null,
    FOREIGN KEY(product_id) REFERENCES product (id),
    FOREIGN KEY(order_id) REFERENCES orders (id),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

create index idx_order_item_product_id on order_item(product_id);
create index idx_order_item_order_id on order_item(order_id);
