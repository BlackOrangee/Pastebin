
INSERT INTO `Users` (`username`, `password`)
VALUES (
        'userName',
        'password'
       );

INSERT INTO `Boards` (name, content, creator, created, last_change, expires)
VALUES ('default board name',
        'content',
        1,
        now(),
        now(),
        DATEADD('DAY', 1, CURRENT_DATE)

       );