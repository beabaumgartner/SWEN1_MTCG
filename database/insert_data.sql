INSERT INTO Users (username, password) VALUES('bea08', '$2y$10$WpYMMVI32Dgc.JkVDynGTucqJbindPY.LMWfDBl.12WfBv9HBUySO');
INSERT INTO Users (username, password) VALUES('flex', '$2y$10$WpYMMVI32Dgc.JkVDynGTucqJbindPY.LMWfDBl.12WfBv9HBUySO');

INSERT INTO Cards (card_id, card_name, damage, package_id) VALUES('da', 'Wasserkobold',32, 12);
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
INSERT INTO Package (user_id) VALUES(NULL);
INSERT INTO Package (user_id) VALUES(NULL);

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