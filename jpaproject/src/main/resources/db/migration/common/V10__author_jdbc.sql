create table author_jdbc (
                        id bigint auto_increment not null,
                        first_name varchar(255),
                        last_name varchar(255),
                        primary key (id)
) engine=InnoDB;


Insert Into author_jdbc (first_name, last_name) VALUES ('Kamil', 'Nowak');