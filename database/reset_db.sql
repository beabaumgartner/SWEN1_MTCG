DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Cards CASCADE;
DROP TABLE IF EXISTS Package CASCADE;
DROP TABLE IF EXISTS Stack CASCADE;
DROP TABLE IF EXISTS Deck CASCADE;
DROP TABLE IF EXISTS Trading CASCADE;
DROP TABLE IF EXISTS Tokens CASCADE;

-- CREATE DATABASE DB_MTCG;

-- create tables
CREATE TABLE Users (
                       user_id SERIAL PRIMARY KEY,
                       username VARCHAR UNIQUE NOT NULL,
                       name VARCHAR DEFAULT NULL,
                       elo INT DEFAULT 100,
                       coins INT Default 20,
                       wins INT Default 0,
                       losses INT Default 0,
                       bio VARCHAR DEFAULT NULL,
                       image VARCHAR DEFAULT NULL,
                       password VARCHAR NOT NULL
);

CREATE TABLE Tokens (
                        token_id SERIAL PRIMARY KEY,
                        user_id INT NOT NULL,
                        token VARCHAR NOT NULL
);

CREATE TABLE Cards (
                       card_id VARCHAR PRIMARY KEY,
                       user_id INT NULL,
                       deck_id INT NULL,
                       package_id INT NULL,
                       stack_id INT NULL,
                       trading_id VARCHAR NULL,
                       card_name VARCHAR NOT NULL,
                       damage INT NOT NULL
);
CREATE TABLE Package (
    package_id SERIAL PRIMARY KEY
);

CREATE TABLE Deck (
    deck_id SERIAL PRIMARY KEY
);

CREATE TABLE Trading (
                         trading_id VARCHAR PRIMARY KEY,
                         type_card VARCHAR NOT NULL,
                         minimum_damage INT NOT NULL
);

-- alter tables
ALTER TABLE Cards
    ADD CONSTRAINT package_id_fk
        FOREIGN KEY (package_id)
            REFERENCES Package (package_id)
            ON DELETE CASCADE;

ALTER TABLE Cards
    ADD CONSTRAINT deck_id_fk
        FOREIGN KEY (deck_id)
            REFERENCES Deck (deck_id)
            ON DELETE CASCADE;

ALTER TABLE Cards
    ADD CONSTRAINT user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES Users (user_id)
            ON DELETE CASCADE;

ALTER TABLE Cards
    ADD CONSTRAINT trading_id_fk
        FOREIGN KEY (trading_id)
            REFERENCES Trading (trading_id)
            ON DELETE CASCADE;

ALTER TABLE Tokens
    ADD CONSTRAINT user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES Users (user_id)
            ON DELETE CASCADE;

