CREATE TABLE unique_transaction_id
(
    id                    BIGINT NOT NULL,
    fundid                BIGINT NOT NULL,
    swap_id               BIGINT NOT NULL,
    date_time             VARCHAR(255),
    event                 VARCHAR(255),
    unique_product_id     VARCHAR(255),
    unique_transaction_id VARCHAR(255),
    CONSTRAINT pk_unique_transaction_id PRIMARY KEY (id)
);