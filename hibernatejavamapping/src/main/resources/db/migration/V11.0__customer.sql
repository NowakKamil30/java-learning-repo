create table customer (
    id bigint not null auto_increment primary key,
    created_date timestamp,
    last_modified_date timestamp,
    customer_name varchar(50),
    address varchar(30),
    city varchar(30),
    state varchar(30),
    zip_code varchar(30),
    phone varchar(20),
    email varchar(255)
)