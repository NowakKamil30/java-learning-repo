drop table if exists credit_card;

create table credit_card (
    id bigint not null auto_increment,
    credit_card_number varchar(20),
    cvv varchar(4),
    expiration_date varchar(7),
    created_date timestamp,
    last_modified_date timestamp,
    version integer,
    PRIMARY KEY (id)
);