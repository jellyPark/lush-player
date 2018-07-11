CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(100) COLLATE utf8_unicode_ci NOT NULL,
  `user_type` char(1) COLLATE utf8_unicode_ci NOT NULL,
  `reg_date` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

insert into user(user_name, password, user_type, reg_date) values('user1','$2a$10$oXhcs6qujNbFA5yXauupSuWLQpMjVbAskvPbMvcUzurpsdIuSXs7m','2',now())