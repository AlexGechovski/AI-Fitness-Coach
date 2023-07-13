CREATE TABLE IF NOT EXISTS users (
                                     id INT PRIMARY KEY AUTO_INCREMENT,
                                     username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    registration_date DATE NOT NULL
    );

CREATE TABLE `Profile` (
                           `ProfileID` INT PRIMARY KEY,
                           `UserID` INT,
                           `Age` INT,
                           `Gender` VARCHAR(255),
                           `Height` FLOAT,
                           `Weight` FLOAT,
                           `BodyFatPercentage` FLOAT,
                           `MaintainCalories` INT
);

CREATE TABLE `Goals` (
                         `GoalID` INT PRIMARY KEY,
                         `ProfileID` INT,
                         `GoalDescription` VARCHAR(255),
                         `TargetWeight` FLOAT,
                         `TargetBodyFatPercentage` FLOAT,
                         `TargetCaloricIntake` FLOAT
);

CREATE TABLE `HealthConditions` (
                                    `ConditionID` INT PRIMARY KEY,
                                    `ProfileID` INT,
                                    `ConditionDescription` VARCHAR(255)
);

CREATE TABLE `DietaryPreferences` (
                                      `PreferenceID` INT PRIMARY KEY,
                                      `ProfileID` INT,
                                      `Vegetarian` BOOLEAN,
                                      `Vegan` BOOLEAN,
                                      `GlutenFree` BOOLEAN,
                                      `FoodAllergies` TEXT
);

ALTER TABLE `DietaryPreferences` ADD FOREIGN KEY (`ProfileID`) REFERENCES `Profile` (`ProfileID`);

ALTER TABLE `Profile` ADD FOREIGN KEY (`UserID`) REFERENCES `users` (`id`);

ALTER TABLE `Goals` ADD FOREIGN KEY (`ProfileID`) REFERENCES `Profile` (`ProfileID`);

ALTER TABLE `HealthConditions` ADD FOREIGN KEY (`ProfileID`) REFERENCES `Profile` (`ProfileID`);