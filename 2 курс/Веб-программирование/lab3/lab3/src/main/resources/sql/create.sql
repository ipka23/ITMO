BEGIN;

CREATE TABLE points
(
    id       SERIAL PRIMARY KEY,
    x        DOUBLE PRECISION NOT NULL,
    y        DOUBLE PRECISION NOT NULL,
    r        DOUBLE PRECISION NOT NULL,
    result   varchar(9)       NOT NULL,
    date     DATE             NOT NULL,
    execTime varchar(9)
);

COMMIT;