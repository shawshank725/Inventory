USE `inventory_test`;
CREATE TABLE `orders` (
  `order_id` INT AUTO_INCREMENT PRIMARY KEY,
  `item_id` INT NOT NULL,
  `user_id` INT NOT NULL,
  `status` VARCHAR(50),
  `payment_mode` VARCHAR(20),
  `address` VARCHAR(50),
  FOREIGN KEY (`item_id`) REFERENCES items(`item_id`),
  FOREIGN KEY (`user_id`) REFERENCES users(`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
