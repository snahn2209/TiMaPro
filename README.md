#TimeManagement Challenge
Group-Project-Manager challenging all teammates to win the productivity challenge

# MySQL localhost database
Schema: tmproject
````sql
CREATE SCHEMA `tmproject`;
````

projects table
```sql
CREATE TABLE tmproject.`projects` (
`projectID` bigint unsigned NOT NULL AUTO_INCREMENT,
`name` varchar(255) DEFAULT NULL,
`deadline` date DEFAULT NULL,
PRIMARY KEY (`projectID`),
UNIQUE KEY `projectID` (`projectID`),
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```

useraccount table
```sql
CREATE TABLE tmproject.`useraccount` (
  `userID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `totalpoints` int DEFAULT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `userID` (`userID`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```
tasks table
```sql
CREATE TABLE tmproject.`tasks` (
  `taskID` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  `timeestimation` double DEFAULT NULL,
  `prio` int DEFAULT NULL,
  `done` tinyint(1) DEFAULT NULL,
  `gotdonedate` date DEFAULT NULL,
  `maxpoints` int DEFAULT NULL,
  `responsibleuserid` bigint unsigned DEFAULT NULL,
  `projectid` bigint unsigned DEFAULT NULL,
  PRIMARY KEY (`taskID`),
  UNIQUE KEY `taskID` (`taskID`),
  KEY `responsibleuserid` (`responsibleuserid`),
  KEY `projectid` (`projectid`),
  CONSTRAINT `tasks_ibfk_1` FOREIGN KEY (`responsibleuserid`) REFERENCES `useraccount` (`userID`),
  CONSTRAINT `tasks_ibfk_2` FOREIGN KEY (`projectid`) REFERENCES `projects` (`projectID`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
```
projectuser Table
```sql
CREATE TABLE tmproject.`projectuser` (
  `projectID` bigint unsigned NOT NULL,
  `userID` bigint unsigned NOT NULL,
  `userpoints` int DEFAULT NULL,
  PRIMARY KEY (`projectID`,`userID`),
  FOREIGN KEY (`projectID`) REFERENCES `projects` (`projectID`),
  FOREIGN KEY (`userID`) REFERENCES `useraccount` (`userID`)
);
```
example test-data
```sql
INSERT INTO tmproject.projects (name, deadline) VALUES ('Project1', '2022-12-31');
INSERT INTO tmproject.projects (name, deadline) VALUES ('Project2', '2022-12-31');

INSERT INTO tmproject.useraccount (name, totalpoints) VALUES ('Tom', 0); 
INSERT INTO tmproject.useraccount (name, totalpoints) VALUES ('Pia', 0);

INSERT INTO tmproject.projectuser (projectID, userID, userpoints) VALUES (1,1,3);
INSERT INTO tmproject.projectuser (projectID, userID, userpoints) VALUES (1,2,5);

INSERT INTO tmproject.tasks (name, deadline, timeestimation, prio, done, maxpoints, responsibleuserid, projectid) VALUES ('hallo', '2022-12-31', 1.0, 7, false, 6, 1, 1);
INSERT INTO tmproject.tasks (name, deadline, timeestimation, prio, done, maxpoints, responsibleuserid, projectid) VALUES ('Task2', '2022-12-31', 1.5, 9, false, 9, 2, 2);
```

