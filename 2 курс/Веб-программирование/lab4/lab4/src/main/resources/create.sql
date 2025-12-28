CREATE TABLE IF NOT EXISTS users (
                                     id BIGSERIAL PRIMARY KEY,
                                     login VARCHAR(50) UNIQUE NOT NULL,
                                     password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS points (
                                      id BIGSERIAL PRIMARY KEY,
                                      x NUMERIC NOT NULL,
                                      y NUMERIC NOT NULL,
                                      r NUMERIC NOT NULL,
                                      result VARCHAR(50) NOT NULL,
                                      date VARCHAR(50) NOT NULL,
                                      exectime VARCHAR(50) NOT NULL,
                                      user_id BIGINT NOT NULL REFERENCES users(id)
);