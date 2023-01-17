# TimeManagement Challenge
Group-Project-Manager challenging all teammates to win the productivity challenge

# User Stories

we completed:

- [25] As a user I need to confirm that I want to mark a task as finished (submit button)
- [12] As a user I can mark my tasks as finished with a checkbox when they are done so that I can see how many tasks are still left
- [3] As a user I can create tasks so that I know what to do to finish the project
- [28] As a user I can see how much points I can earn finishing that task so that I am motivated to finish my tasks on time
- [11] As a user I can edit the deadline for a task so that I can see when the task is due
- [10] As a user I can edit the time-duration for each task so that I can estimate how much time the task will take
- [4] As a user I can prioritize tasks to work on the most important one first
- [26] As a user I can invite other users to the project so that they can also access and work on the project
- [24] As a user I can set or alter a deadline for a project so that all members know when the project has to be finished
- [22] As a user I can give my project a name so that I know what this project is about
- [2] As a user I can create a new project so that I have an overview over all of them
- [18] As a user my points account gets ranked with my group members so that I feel more encouraged to earn more points
- [5]  As a user I have a points account so that I can see how productive I have been
- [1] As a user I can see all my upcoming tasks in a list so that I can choose the most important ones and work more efficiently
- [29] As a user I can see all my upcoming projects in a list so that I can choose the most important ones first and work more efficiently.
- [17] As a user I get only a fraction of the maximum amount of points if I finish a task/project after the deadline so that I am motivated to actually meet my deadlines
- [16] As a user I earn the most points for tasks that take the longest so that I get most rewarded for the hardest tasks
- [15] As a user I can earn the maximum amount of points for finishing tasks/projects on time so that I am motivated to meet my deadlines
- [19] As a user I can set up Challenges with my friends/teammates so that I get rewarded if I earn the most points
- [20] As a user I can see the amount of points I would earn by completing this task so that I feel more motivated to work on hard tasks


# MySQL localhost database

- host = localhost 
- port = 3306 
- database = TMproject 
- username = root 
- password = TMProject

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

