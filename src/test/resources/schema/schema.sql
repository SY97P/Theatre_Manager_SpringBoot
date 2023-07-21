CREATE TABLE theatres
(
    theatre_id   VARCHAR(50) NOT NULL,
    theatre_name VARCHAR(50) NOT NULL,
    genre        VARCHAR(20) NOT NULL,
    age_rate     VARCHAR(20) NOT NULL,
    open_run     DATE        NOT NULL,
    close_run    DATE        NOT NULL,
    stage        VARCHAR(10) NOT NULL,

    PRIMARY KEY (theatre_id)
);

CREATE TABLE tickets
(
    ticket_id       VARCHAR(50) NOT NULL,
    theatre_id      VARCHAR(50) NOT NULL,
    ticket_price    LONG        NOT NULL,
    ticket_quantity LONG        NOT NULL,
    reserved_date   DATE        NOT NULL,

    PRIMARY KEY (ticket_id),
    FOREIGN KEY (theatre_id) REFERENCES theatres (theatre_id)
);

CREATE TABLE orders
(
    order_id     VARCHAR(50) NOT NULL,
    email        VARCHAR(50) NOT NULL,
    ordered_at   DATE        NOT NULL,
    order_status VARCHAR(20) NOT NULL,

    PRIMARY KEY (order_id)
);