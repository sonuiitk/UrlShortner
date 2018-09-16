# UrlShortner
This is java REST API project used for MoneySmart asssignment


This problem can be solved in many ways. One of the approach can be to hash the id of the table or maintain a unique number somewhere (REDIS INCR functionality) so that whenever a new request comes, it assigns a new number to it. This number can then be hashed to any base string (in this application I have used base62, but this can be increased or decreased and can be jumbled up to avoid exposing real ids). Since I assumed that we might be doing some heavy analytics with this data, so I went with relational DB, otherwise we can go with NoSql DB also. Dong heavy queries in NoSql is difficult as compared to relational DB, that is why I used relational DB.

Properties of the application are defined in src/urlshortner.properties . Database properties are in src/hibernate-mysql.properties . Place the GEOIP.dat file where application can access this file and change this file location in urlshortner.properties file.
To build this project, go the the root folder where build.xml is there. From this window, run command "ant". This will generate a war file. This file needs to be deployed in Apache-tomcat. 
In DB, run the below scripts to create the tables.

CREATE TABLE `request_location_info` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `country_name` varchar(50) DEFAULT NULL,
  `country_code` varchar(45) DEFAULT NULL,
  `city_name` varchar(100) DEFAULT NULL,
  `latitude` varchar(45) DEFAULT NULL,
  `longitude` varchar(45) DEFAULT NULL,
  `user_agent` varchar(1000) DEFAULT NULL,
  `device_os` varchar(1000) DEFAULT NULL,
  `device_browser` varchar(1000) DEFAULT NULL,
  `short_url_id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `short_urls` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `url` varchar(5000) NOT NULL,
  `url_md5` varchar(150) NOT NULL,
  `created_at` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


Instead of starting the ids from 1, we can give some random number and start from there. 




Enhancements:
1. Currently there is no user management system is there. We can make the service premium by having on paid customer using it. Or atleast they should be our user. 
If we even don't want this, we may go for user tracking / info collection from some cookie based system management.

2. Right now I am not caching any request / entry. To serve requests faster, we might go with caching. We can implement caching in 2 ways. One can be least recently used cache and other one can be least frequently used cache. 

3. Instead of going with MySql (relational database), we can go with any NoSql also. Because this site will be mostly real-only site, by using NoSql we can make it highly fault tolerable and during days of high hits we can expand it horizontally easily. Plus if we are not going to use complex queries in DB, NoSql's in-built search functionalities work better.

4. Right now there is no check for duplicate requests. With the help of cookies and hashing algorithms (hash of url is getting stored in DB), we can check whether the same user is sending the same url or not. A lot of possibilities are possible here depending on the feature.

5. There is no view part here in this application. Mostly, they are serving as the APIs. Implementation of AJAX can be used here in some pages.

6. Implementation of logger. I have implemented the logger in this application but not logging anything through the code.

7. Make the random id of constant length, may be 6-8 characters. But some things have to be taken care of before doing that. One is the string which we are using for hashing.

8. Can use Spring framework for dependency injection.
