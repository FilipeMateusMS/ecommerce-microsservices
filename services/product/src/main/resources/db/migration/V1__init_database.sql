create table if not exists category
(
    id bigint generated always as identity primary key,,
    description varchar(255),
    name        varchar(255) not null unique
);

create table if not exists product
(
    id bigint generated always as identity primary key,
    description        varchar(255) not null,
    name               varchar(255) not null unique,
    price              numeric(10,2) not null check (price >= 0),
    available_quantity integer not null check (available_quantity >= 0),
    category_id        bigint not null

    constraint fk_product_category foreign key (id) references category(id)
);

-- create sequence if not exists category_seq increment by 50;
-- create sequence if not exists product_seq increment by 50;