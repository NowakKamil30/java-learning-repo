create table book_natural (
                           isbn varchar(255),
                           publisher varchar(255),
                           title varchar(255),
                           author bigint,
                           primary key (title)
) engine=InnoDB;