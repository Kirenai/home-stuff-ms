CREATE
    DATABASE IF NOT EXISTS `hs_user` CHARACTER SET utf8 COLLATE utf8_spanish_ci;
CREATE
    DATABASE IF NOT EXISTS `hs_role` CHARACTER SET utf8 COLLATE utf8_spanish_ci;
CREATE
    DATABASE IF NOT EXISTS `hs_nourishment` CHARACTER SET utf8 COLLATE utf8_spanish_ci;
CREATE
    DATABASE IF NOT EXISTS `hs_consumption` CHARACTER SET utf8 COLLATE utf8_spanish_ci;
CREATE
    DATABASE IF NOT EXISTS `hs_category` CHARACTER SET utf8 COLLATE utf8_spanish_ci;

CREATE TABLE `hs_user`.`users`
(
    `user_id`    INT          NOT NULL AUTO_INCREMENT,
    `username`   VARCHAR(45)  NULL,
    `password`   VARCHAR(255) NULL,
    `first_name` VARCHAR(30)  NULL,
    `last_name`  VARCHAR(30)  NULL,
    `age`        INT          NULL,
    PRIMARY KEY (`user_id`),
    CONSTRAINT `unq_username` UNIQUE (`username`)
);

CREATE TABLE `hs_role`.`roles`
(
    `role_id` INT         NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(45) NULL,
    PRIMARY KEY (`role_id`),
    CONSTRAINT `unq_name` UNIQUE (`name`)
);

INSERT INTO `hs_role`.`roles`(`name`)
VALUES ('ROLE_USER');

CREATE TABLE `hs_role`.`roles_users`
(
    `role_id` INT NULL,
    `user_id` INT NULL,
    CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `hs_role`.`roles` (`role_id`)
);

CREATE TABLE `hs_nourishment`.`nourishments`
(
    `nourishment_id`      INT          NOT NULL AUTO_INCREMENT,
    `name`                VARCHAR(30)  NOT NULL,
    `image_url`           VARCHAR(255) NOT NULL,
    `description`         TEXT,
    `is_available`        TINYINT(1)   NOT NULL,
    `unit`                INT,
    `percentage`          INT,
    `user_id`             INT          NOT NULL,
    `category_id`         INT          NOT NULL,
    `nourishment_type_id` INT          NOT NULL,
    PRIMARY KEY (`nourishment_id`),
    CONSTRAINT `unq_name` UNIQUE (`name`),
    CONSTRAINT `fk_nourishment_type` FOREIGN KEY (`nourishment_type_id`)
        REFERENCES `hs_nourishment`.`nourishment_type` (`nourishment_type_id`)
);

CREATE TABLE `hs_nourishment`.`nourishment_type`
(
    `nourishment_type_id` INT         NOT NULL AUTO_INCREMENT,
    `name`                VARCHAR(30) NOT NULL,
    PRIMARY KEY (`nourishment_type_id`),
    CONSTRAINT `unq_name` UNIQUE (`name`)
);

CREATE TABLE `hs_consumption`.`consumptions`
(
    `consumption_id` INT NOT NULL AUTO_INCREMENT,
    `unit`           INT,
    `percentage`     INT,
    `nourishment_id` INT NOT NULL,
    `user_id`        INT NOT NULL,
    PRIMARY KEY (`consumption_id`)
);

CREATE TABLE `hs_category`.`categories`
(
    `category_id` INT         NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(50) NOT NULL,
    PRIMARY KEY (`category_id`),
    CONSTRAINT `unq_name` UNIQUE (`name`)
);