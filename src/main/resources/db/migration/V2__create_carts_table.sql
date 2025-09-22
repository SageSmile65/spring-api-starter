create table carts
(
    id           binary(16) default (uuid_to_bin(uuid())) not null
        primary key,
    date_created date       default (curdate())           null invisible
);
create table cart_items
(
    id         bigint        not null
        primary key,
    cart_id    binary(16)    not null,
    product_id bigint        not null,
    quantity   int default 1 null,
    constraint cart_items_cart_product_unique
        unique (cart_id, product_id),
    constraint cart_items___fk
        foreign key (product_id) references products (id)
            on delete cascade,
    constraint cart_items_carts_id_fk
        foreign key (cart_id) references carts (id)
            on delete cascade
);

