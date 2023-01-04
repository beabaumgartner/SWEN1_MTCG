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
    damage INT NOT NULL
);
CREATE TABLE Package (
    package_id SERIAL PRIMARY KEY,
    user_id INT NULL
);

CREATE TABLE Package_Cards (
    package_id INTEGER NOT NULL,
    card_id VARCHAR NOT NULL,

    PRIMARY KEY (package_id, card_id)
);

CREATE TABLE Stack (
    stack_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL
);

CREATE TABLE Stack_Cards (
    stack_id INTEGER NOT NULL,
    card_id VARCHAR NOT NULL,

    PRIMARY KEY (stack_id, card_id)
);

CREATE TABLE Deck (
    deck_id SERIAL PRIMARY KEY,
    user_id INT NOT NULL
);

CREATE TABLE Deck_Cards (
    deck_id INT NOT NULL,
    card_id VARCHAR NOT NULL,

    PRIMARY KEY (deck_id, card_id)
);

CREATE TABLE Trading (
    trading_id SERIAL PRIMARY KEY,
    user_seller_id INT NOT NULL,
    user_buyer_id INT NULL,
    card_id VARCHAR NOT NULL,
    requirements VARCHAR NOT NULL
);

-- alter tables
ALTER TABLE Package
ADD CONSTRAINT user_id_fk
FOREIGN KEY (user_id)
REFERENCES Users (user_id)
ON DELETE CASCADE;

ALTER TABLE Package_Cards
ADD CONSTRAINT package_id_fk
FOREIGN KEY (package_id)
REFERENCES Package (package_id)
ON DELETE CASCADE;

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

ALTER TABLE Trading
ADD CONSTRAINT user_seller_id_fk
FOREIGN KEY (user_seller_id)
REFERENCES Users (user_id)
ON DELETE CASCADE;

ALTER TABLE Trading
ADD CONSTRAINT user_buyer_id_fk
FOREIGN KEY (user_buyer_id)
REFERENCES Users (user_id)
ON DELETE CASCADE;

ALTER TABLE Trading
ADD CONSTRAINT card_id_fk
FOREIGN KEY (card_id)
REFERENCES Cards (card_id)
ON DELETE CASCADE;

ALTER TABLE Tokens
ADD CONSTRAINT user_id_fk
FOREIGN KEY (user_id)
REFERENCES Users (user_id)
ON DELETE CASCADE;

