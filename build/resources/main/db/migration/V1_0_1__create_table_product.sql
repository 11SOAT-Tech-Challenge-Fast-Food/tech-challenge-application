CREATE TABLE IF NOT EXISTS product(
    id UUID primary key,
    name varchar(255) not null,
    description varchar(255) not null,
    category varchar(255) not null,
    price DECIMAL not null,
    amount INT not null
);

create index idx_product_name on product(name);
create index idx_product_category on product(category);

insert into product (id, name, description, category, price, amount)
values ('d2a6b8f3-f477-44ec-91b7-1fd26ec821cd', 'Burgao', 'Burgao Saboroso', 'BURGUER', 25.00, 1);

insert into product (id, name, description, category, price, amount)
values ('3e6b7b56-58ec-4439-97f3-c39f8842b7b2', 'Batatas Frita Média', 'Batatas Fritas tamanho médio', 'SNACK', 12.50, 1);

insert into product (id, name, description, category, price, amount)
values ('8a245de7-00d4-490e-b510-79b6f7c7f26e' ,'Coca Cola Média', 'Copo Coca Cola tamanho médio', 'DRINK', 10.00, 1);

insert into product (id, name, description, category, price, amount)
values ('6cebcfa2-2c1b-4d33-9797-1cfa258f7aaf', 'Pudin', 'Sobremesa de doce de leite', 'DESSERT', 15.00, 1);