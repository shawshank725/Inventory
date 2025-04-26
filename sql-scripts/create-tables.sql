create database if not exists `inventory_test`;
USE `inventory_test`;

SET foreign_key_checks = 0;
DROP TABLE IF EXISTS `roles`;
DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `users_roles`;
SET foreign_key_checks = 1;

CREATE TABLE `users` (
  `user_id` int primary key auto_increment,
  `username` varchar(50) unique key NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL, 
  `password` varchar(60) NOT NULL,
  `enabled` tinyint NOT NULL,
  `profile_photo` varchar(200) DEFAULT "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Default_pfp.svg/2048px-Default_pfp.svg.png"
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `users` (`username`,`firstname`, `lastname`, `email`, `password`,`enabled`) VALUES 
('ben10000','Ben','Tennyson','ben10@gmail.com','$2a$12$E6xA14LnI.Ay/zqFdmrXGeCvdm/WpcCkXDrGou9Y7guQozx/Fj4pe',1),
('jimmy','James','McGill','bcs@gmail.com','$2a$12$E6xA14LnI.Ay/zqFdmrXGeCvdm/WpcCkXDrGou9Y7guQozx/Fj4pe',1),
('batman','Bruce','Wayne','batman@gmail.com','$2a$12$E6xA14LnI.Ay/zqFdmrXGeCvdm/WpcCkXDrGou9Y7guQozx/Fj4pe',1);

CREATE TABLE `roles` (
   `role_id` int NOT NULL AUTO_INCREMENT,
  `role_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `roles` (role_name) VALUES 
('ROLE_BUYER'),('ROLE_SELLER'),('ROLE_ADMIN');


CREATE TABLE `users_roles` (
  `user_id` int NOT NULL,
  `role_id` int NOT NULL,
  
  PRIMARY KEY (`user_id`,`role_id`),
  
  KEY `FK_ROLE_idx` (`role_id`),
  
  CONSTRAINT `FK_USER_05` FOREIGN KEY (`user_id`) 
  REFERENCES `users` (`user_id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION,
  
  CONSTRAINT `FK_ROLE` FOREIGN KEY (`role_id`) 
  REFERENCES `roles` (`role_id`) 
  ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO `users_roles` (user_id,role_id)
VALUES 
(1,3),
(2,2),
(3,1);

