USE song;

CREATE TABLE `tbl_song` (
   `id` int unsigned NOT NULL AUTO_INCREMENT,
   `name` varchar(500) NOT NULL,
   `artist` varchar(500) DEFAULT NULL,
   `album` varchar(500) DEFAULT NULL,
   `length` varchar(500) DEFAULT NULL,
   `resource_id` int NOT NULL,
   `year` int DEFAULT NULL,
   PRIMARY KEY (`id`)
 )