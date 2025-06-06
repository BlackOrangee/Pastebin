DROP TABLE IF EXISTS `Signs`;
DROP TABLE IF EXISTS `Boards`;
DROP TABLE IF EXISTS `Users`;

CREATE TABLE IF NOT EXISTS `Users` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `username` varchar(255) NOT NULL,
    `password` varchar(255) NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `Boards` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` varchar(255) NOT NULL UNIQUE,
    `content` TEXT,
    `creator` BIGINT NOT NULL,
    `created` DATETIME NOT NULL,
    `last_change` DATETIME NOT NULL,
    `expires` DATETIME NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`creator`) REFERENCES `Users`(`id`)
);

CREATE TABLE IF NOT EXISTS `Signs` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `user_id` BIGINT NOT NULL,
    `board_id` BIGINT NOT NULL,
    `last_change` DATETIME NOT NULL,
    `row_start` INT NOT NULL,
    `row_end` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `Users`(`id`),
    FOREIGN KEY (`board_id`) REFERENCES `Boards`(`id`)
);

