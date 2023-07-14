-- Drop existing tables
DROP TABLE IF EXISTS FoodAllergies;
DROP TABLE IF EXISTS DietaryPreferences;
DROP TABLE IF EXISTS HealthConditions;
DROP TABLE IF EXISTS Goals;
DROP TABLE IF EXISTS Profile;

-- Create Profile table
CREATE TABLE `Profile` (
                           `ProfileID` INT AUTO_INCREMENT PRIMARY KEY,
                           `UserID` INT,
                           `Age` INT,
                           `Gender` VARCHAR(255),
                           `Height` FLOAT,
                           `Weight` FLOAT,
                           `BodyFatPercentage` FLOAT,
                           `MaintainCalories` INT
);

-- Create Goals table
CREATE TABLE `Goals` (
                         `GoalID` INT AUTO_INCREMENT PRIMARY KEY,
                         `ProfileID` INT,
                         `GoalDescription` VARCHAR(255),
                         `TargetWeight` FLOAT,
                         `TargetBodyFatPercentage` FLOAT,
                         `TargetCaloricIntake` FLOAT
);

-- Create HealthConditions table
CREATE TABLE `HealthConditions` (
                                    `ConditionID` INT AUTO_INCREMENT PRIMARY KEY,
                                    `ProfileID` INT,
                                    `ConditionDescription` VARCHAR(255)
);

-- Create DietaryPreferences table
CREATE TABLE DietaryPreferences (
                                    PreferenceID INT PRIMARY KEY AUTO_INCREMENT,
                                    ProfileID INT UNIQUE,
                                    Vegetarian BOOLEAN,
                                    Vegan BOOLEAN,
                                    GlutenFree BOOLEAN,
                                    FOREIGN KEY (ProfileID) REFERENCES Profile(ProfileID)
);

-- Create FoodAllergies table
CREATE TABLE `FoodAllergies` (
                                 `AllergyID` INT AUTO_INCREMENT PRIMARY KEY,
                                 `ProfileID` INT,
                                 `AllergyName` VARCHAR(255) NOT NULL UNIQUE,
                                 FOREIGN KEY (`ProfileID`) REFERENCES `Profile` (`ProfileID`)
);

ALTER TABLE `DietaryPreferences` ADD FOREIGN KEY (`ProfileID`) REFERENCES `Profile` (`ProfileID`);

ALTER TABLE `Profile` ADD FOREIGN KEY (`UserID`) REFERENCES `users` (`id`);

ALTER TABLE `Goals` ADD FOREIGN KEY (`ProfileID`) REFERENCES `Profile` (`ProfileID`);

ALTER TABLE `HealthConditions` ADD FOREIGN KEY (`ProfileID`) REFERENCES `Profile` (`ProfileID`);
