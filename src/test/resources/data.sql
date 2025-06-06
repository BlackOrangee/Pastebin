
INSERT INTO `Users` (`username`, `password`)
VALUES (
        'user1',
        'password1'
       );

INSERT INTO `Boards` (id, name, content, creator, created, last_change, expires)
VALUES (1,
        'name',
        'content',
        1,
        now(),
        now(),
        DATEADD('DAY', 1, CURRENT_DATE)

       );