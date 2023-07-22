CREATE TABLE IF NOT EXISTS performances
(
    performance_id   VARCHAR(50) NOT NULL,
    performance_name VARCHAR(50) NOT NULL,
    genre            VARCHAR(20) NOT NULL,
    age_rate         VARCHAR(20) NOT NULL,
    open_run         DATE        NOT NULL,
    close_run        DATE        NOT NULL,
    stage            VARCHAR(10) NOT NULL,
    price            LONG        NOT NULL,

    PRIMARY KEY (performance_id)
);

CREATE TABLE IF NOT EXISTS ticket_orders
(
    order_id     VARCHAR(50) NOT NULL,
    email        VARCHAR(50) NOT NULL,
    ordered_at   DATE        NOT NULL,
    order_status VARCHAR(20) NOT NULL,

    PRIMARY KEY (order_id)
);

CREATE TABLE IF NOT EXISTS tickets
(
    ticket_id      VARCHAR(50) NOT NULL,
    order_id       VARCHAR(50) NOT NULL,
    performance_id VARCHAR(50) NOT NULL,
    ticket_price   LONG        NOT NULL,
    reserved_date  DATE        NOT NULL,

    PRIMARY KEY (ticket_id),
    FOREIGN KEY (performance_id) REFERENCES performances (performance_id) on update cascade on delete cascade,
    FOREIGN KEY (order_id) REFERENCES ticket_orders (order_id) on update cascade on delete cascade
);