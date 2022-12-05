-- CREATE DATABASE DB_MTCG;

-- create tables
CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR UNIQUE NOT NULL,
    name VARCHAR DEFAULT NULL,
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
    card_name VARCHAR NOT NULL,
    damage INT NOT NULL,
    category VARCHAR NOT NULL,
    element_type VARCHAR NOT NULL
);

CREATE TABLE Stack (
    stack_id VARCHAR PRIMARY KEY,
    user_id INT NOT NULL
);

CREATE TABLE Stack_Cards (
    stack_id VARCHAR NOT NULL,
    card_id VARCHAR NOT NULL,
    quantity INT NULL,

    PRIMARY KEY (stack_id, card_id)
);

CREATE TABLE Deck (
    deck_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL
);

CREATE TABLE Deck_Cards (
    deck_id INT NOT NULL,
    card_id VARCHAR NOT NULL,
    quantity INT NULL,

    PRIMARY KEY (deck_id, card_id)
);

CREATE TABLE Store (
    store_id VARCHAR PRIMARY KEY,
    user_id INT NOT NULL,
    card_id VARCHAR NOT NULL,
    requirements VARCHAR NOT NULL
);

-- alter tables
ALTER TABLE Stack
ADD CONSTRAINT user_id_fk
FOREIGN KEY (user_id)
REFERENCES Users (user_id)
ON DELETE CASCADE;

ALTER TABLE Stack_Cards
ADD CONSTRAINT stack_id_fk
FOREIGN KEY (stack_id)
REFERENCES Stack (stack_id)
ON DELETE CASCADE;

ALTER TABLE Stack_Cards
ADD CONSTRAINT card_id_fk
FOREIGN KEY (card_id)
REFERENCES Cards (card_id)
ON DELETE CASCADE;

ALTER TABLE Deck
ADD CONSTRAINT user_id_fk
FOREIGN KEY (user_id)
REFERENCES Users (user_id)
ON DELETE CASCADE;

ALTER TABLE Deck_Cards
ADD CONSTRAINT deck_id_fk
FOREIGN KEY (deck_id)
REFERENCES Deck (deck_id)
ON DELETE CASCADE;

ALTER TABLE Deck_Cards
ADD CONSTRAINT card_id_fk
FOREIGN KEY (card_id)
REFERENCES Cards (card_id)
ON DELETE CASCADE;

ALTER TABLE Store
ADD CONSTRAINT user_id_fk
FOREIGN KEY (user_id)
REFERENCES Users (user_id)
ON DELETE CASCADE;

ALTER TABLE Store
ADD CONSTRAINT card_id_fk
FOREIGN KEY (card_id)
REFERENCES Cards (card_id)
ON DELETE CASCADE;

ALTER TABLE Tokens
ADD CONSTRAINT user_id_fk
FOREIGN KEY (user_id)
REFERENCES Users (user_id)
ON DELETE CASCADE;

