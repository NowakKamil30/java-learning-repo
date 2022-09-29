drop table if exists product cascade;

CREATE TABLE product
(
    id      bigint not null auto_increment primary key,
    description    varchar(255),
    created_date    timestamp,
    last_modified_date timestamp
) engine = InnoDB;