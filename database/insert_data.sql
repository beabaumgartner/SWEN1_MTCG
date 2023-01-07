INSERT INTO Users (username, password) VALUES('bea08', '$2y$10$WpYMMVI32Dgc.JkVDynGTucqJbindPY.LMWfDBl.12WfBv9HBUySO');
INSERT INTO Users (username, password) VALUES('flex', '$2y$10$WpYMMVI32Dgc.JkVDynGTucqJbindPY.LMWfDBl.12WfBv9HBUySO');

INSERT INTO Cards (card_id, card_name, card_type, damage) VALUES('da', 'Wasserkobold', 'monster',32);
INSERT INTO Cards (card_id, card_name, damage) VALUES('do', 'Hexe',50);
INSERT INTO Cards (card_id, card_name, damage) VALUES('de', 'Hydra',22);
INSERT INTO Cards (card_id, card_name, damage) VALUES('di', 'Kobold',30);

INSERT INTO Stack (user_id) VALUES(1);
INSERT INTO Stack (user_id) VALUES(2);

INSERT INTO Stack_Cards (stack_id, card_id) VALUES(1, 2);
INSERT INTO Stack_Cards (stack_id, card_id) VALUES(1, 3);
INSERT INTO Stack_Cards (stack_id, card_id) VALUES(1, 4);
-----------
INSERT INTO Stack_Cards (stack_id, card_id) VALUES(2, 4);
INSERT INTO Stack_Cards (stack_id, card_id) VALUES(2, 2);
INSERT INTO Stack_Cards (stack_id, card_id) VALUES(2, 3);


INSERT INTO Deck (user_id) VALUES(1);
INSERT INTO Deck (user_id) VALUES(2);

INSERT INTO Deck_Cards (deck_id, card_id) VALUES(1, 'do');
INSERT INTO Deck_Cards (deck_id, card_id) VALUES(1, 'do');
INSERT INTO Deck_Cards (deck_id, card_id) VALUES(1, 'do');
-----------
INSERT INTO Deck_Cards (deck_id, card_id) VALUES(2, 'do');
INSERT INTO Deck_Cards (deck_id, card_id) VALUES(2, 'do');
INSERT INTO Deck_Cards (deck_id, card_id) VALUES(2, 'do');

----------------------------------------------------------------------
INSERT INTO Package (package_id) VALUES(DEFAULT);
INSERT INTO Package (package_id) VALUES(DEFAULT);

INSERT INTO Cards (card_id, card_name, damage) VALUES('di', 'Kobold',30);



INSERT INTO Package_Cards (package_id, card_id) VALUES(1, 'do');
INSERT INTO Package_Cards (package_id, card_id) VALUES(1, 'da');
INSERT INTO Package_Cards (package_id, card_id) VALUES(1, 'di');
-----------
INSERT INTO Package_Cards (package_id, card_id) VALUES(2, 'de');
INSERT INTO Package_Cards (package_id, card_id) VALUES(2, 'di');
INSERT INTO Package_Cards (package_id, card_id) VALUES(2, 'da');

UPDATE Users
SET name = 'Felix Schuster',
    bio = 'Ich mag Eislaufen',
    image = ':)'
WHERE username = 'flex';

UPDATE Package
SET user_id = '1'
WHERE package_id = 1;

SELECT name, bio, image from Users Where username = 'flex';

Alter USER postgres with PASSWORD 'postgres';

SELECT * FROM Tokens WHERE user_id = 1;
SELECT * FROM Tokens JOIN Users ON Tokens.user_id = Users.user_id WHERE users.username = 'bea08' AND Tokens.token = 'bea08-mtcgToken'

SELECT * FROM Cards JOIN Package_Cards ON Cards.card_id = Package_Cards.card_id JOIN Package ON Package.package_id = Package_Cards.package_id WHERE Package.package_id = 1;

INSERT INTO Package (package_id) VALUES(DEFAULT) RETURNING package_id;

SELECT * FROM Package WHERE user_id IS NULL;

SELECT * FROM Users WHERE user_id = 1

SELECT * FROM Card WHERE user_id

    getStackIdByUserId
SELECT * FROM Stack WHERE user_id = ?;

SELECT Cards.card_id, Cards.card_name, Cards.damage, Stack.user_id FROM Cards JOIN Stack_Cards ON Cards.card_id = Stack_Cards.card_id JOIN Stack ON Stack.stack_id = Stack_Cards.stack_id WHERE Stack.user_id = 1;

SELECT * FROM Package WHERE user_id IS NULL LIMIT 1;


INSERT INTO Package (package_id) VALUES(DEFAULT) RETURNING package_id;

SELECT Cards.card_id, Cards.card_name, Cards.damage FROM Cards
JOIN Package_Cards
ON Cards.card_id = Package_Cards.card_id
JOIN Package
ON Package.package_id = Package_Cards.package_id
WHERE Package.package_id = ?;

SELECT Cards.card_id, Cards.card_name, Cards.damage FROM Cards
JOIN Package
ON Cards.package_id = Package.package_id
WHERE Cards.package_id = 15;



SELECT Cards.card_id, Cards.card_name, Cards.damage, Stack.user_id FROM Cards
    JOIN Stack_Cards
    ON Cards.card_id = Stack_Cards.card_id
    JOIN Stack
    ON Stack.stack_id = Stack_Cards.stack_id
WHERE Stack.user_id = ?;

SELECT Cards.card_id, Cards.card_name, Cards.damage From Cards WHERE user_id = 1;

SELECT card_id, card_name, damage From Cards WHERE user_id = 1 AND deck_id IS NOT NULL;

INSERT INTO Deck (deck_id) VALUES(DEFAULT) RETURNING deck_id;

SELECT * From Cards WHERE user_id = 1 AND deck_id < 4;
card_id, card_name, damage

SELECT * From Cards WHERE user_id = 1 AND deck_id IS NOT NULL;

UPDATE Cards
SET deck_id = NULL
WHERE user_id = ?
  AND deck_id != 4;


SELECT * FROM Users ORDER BY elo DESC;


UPDATE Users
SET elo = '80'
WHERE user_id = 1;

INSERT INTO Trading (trading_id, type_card, minimum_damage) VALUES (4, 'f', 12);

UPDATE Cards
SET trading_id = 4
WHERE user_id = 1 AND card_id = '1d3f175b-c067-4359-989d-96562bfa382c'AND deck_id IS NULL AND trading_id IS NULL;

SELECT Cards.trading_id, Cards.card_id, Trading.type_card, Trading.minimum_damage
FROM Trading JOIN Cards
ON Trading.trading_id = Cards.trading_id;

UPDATE Cards
SET trading_id = NULL
WHERE user_id = 1 AND card_id = '91a6471b-1426-43f6-ad65-6fc473e16f9f';

DELETE FROM Trading
WHERE trading_id = '336cd85277-4590-49d4-b0cf-ba0a921faad0';


SELECT Cards.trading_id, Cards.card_id, Trading.type_card, Trading.minimum_damage
FROM Trading JOIN Cards
ON Trading.trading_id = Cards.trading_id WHERE Trading.trading_id = '336cd85277-4590-49d4-b0cf-ba0a921faad0';


INSERT INTO Cards (card_id, card_name, damage, card_type, package_id) VALUES(?, ?, ?, ?, ?);

SELECT Cards.card_id, Cards.card_name, Cards.damage
FROM Cards
WHERE card_id = '4a2757d6-b1c3-47ac-b9a3-91deab093531'
  AND user_id = 1
  AND deck_id IS NULL
  AND trading_id IS NULL;


SELECT card_id, card_name, damage From Cards
WHERE user_id = 1
  AND card_id = 1
  And deck_id IS NULL
  AND card_type = ?
  AND damage >= ?;

SELECT * FROM Cards
WHERE card_id = '91a6471b-1426-43f6-ad65-6fc473e16f9f';