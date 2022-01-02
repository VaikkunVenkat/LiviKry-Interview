CREATE TABLE `services` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `updated_at` datetime(6) NOT NULL,
  `url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `services` (`id`, `created_at`, `name`, `status`, `updated_at`, `url`)
VALUES
	(1, '2022-01-01 22:46:46.379000', 'jsonplaceholder', 'OK', '2022-01-01 23:58:52.554000', 'https://jsonplaceholder.typicode.com/posts'),
	(2, '2022-01-01 22:52:11.157000', 'Bored', 'OK', '2022-01-01 22:52:11.157000', 'https://www.boredapi.com/api'),
	(3, '2022-01-02 15:04:16.035000', 'datausa', 'OK', '2022-01-02 15:04:16.035000', 'https://datausa.io/api/data'),
	(4, '2022-01-02 15:30:36.488000', 'randomDog', 'OK', '2022-01-02 15:30:36.488000', 'https://dog.ceo/api/breeds/image/random');
