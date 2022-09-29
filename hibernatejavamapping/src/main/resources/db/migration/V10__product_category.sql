create table category(
    id bigint not null auto_increment primary key,
    description varchar(50),
    created_date timestamp,
    last_modified_date timestamp
);


create table product_category (
    product_id bigint not null,
    category_id bigint not null,
    primary key (product_id, category_id),
    constraint pc_product_id_fr foreign key (product_id) references product(id),
    constraint pc_category_id_fr foreign key (category_id) references category(id)
);