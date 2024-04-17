
create sequence unique_transaction_id_seq start with 1 increment by 50;

create table unique_transaction_id (
                                       fundid bigint not null,
                                       id bigint not null,
                                       swap_id bigint not null,
                                       date_time varchar(255),
                                       event varchar(255),
                                       unique_product_id varchar(255),
                                       unique_transaction_id varchar(255),
                                       primary key (id)
);
