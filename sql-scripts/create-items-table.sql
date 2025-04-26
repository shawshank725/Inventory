USE `inventory_test`;

DROP TABLE IF EXISTS `items`;

CREATE TABLE `items` (
  `item_id` INT PRIMARY KEY AUTO_INCREMENT,
  `item_name` VARCHAR(50) NOT NULL,
  `item_url` VARCHAR(200),
  `type` VARCHAR(50) NOT NULL,
  `quantity` INT NOT NULL,
  `price` float NOT NULL, 
  `details` VARCHAR(255) NOT NULL,
  `user_id` int NOT NULL,
  `active` boolean NOT NULL DEFAULT TRUE,
  CONSTRAINT `fk_items_user`
    FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
    ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

