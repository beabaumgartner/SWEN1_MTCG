-- CREATE DATABASE DB_MTCG;

-- create tables
CREATE TABLE Users (
    user_id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL,
    coins INT NOT NULL,
    password VARCHAR(64) NOT NULL
);

CREATE TABLE Tokens (
    token_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    token VARCHAR(50) NOT NULL
);

CREATE TABLE Cards (
    card_id SERIAL PRIMARY KEY,
    damage INT NOT NULL,
    category VARCHAR(1) NOT NULL,
    element_type VARCHAR(1) NOT NULL
);

CREATE TABLE Stack (
    stack_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL
);

CREATE TABLE Stack_Cards (
    stack_id INT NOT NULL,
    card_id INT NOT NULL,
    quantity INT NULL,

    PRIMARY KEY (stack_id, card_id)
);

CREATE TABLE Deck (
    deck_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL
);

CREATE TABLE Deck_Cards (
    deck_id INT NOT NULL,
    card_id INT NOT NULL,
    quantity INT NULL,

    PRIMARY KEY (deck_id, card_id)
);

CREATE TABLE Store (
    store_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    card_id INT NOT NULL,
    requirements VARCHAR(50) NOT NULL
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

