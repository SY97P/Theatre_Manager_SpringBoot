create table theatres
(
    theatre_id   varchar(50) not null,
    theatre_name varchar(50) not null,
    genre        varchar(20) not null,
    age_rate     varchar(20) not null,
    open_run     date        not null,
    close_run    date        not null,
    stage        varchar(10)  not null,

    primary key (theatre_id)
);