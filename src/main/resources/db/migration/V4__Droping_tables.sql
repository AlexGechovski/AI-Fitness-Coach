-- Drop existing tables
DROP TABLE IF EXISTS FoodAllergies;
DROP TABLE IF EXISTS DietaryPreferences;
DROP TABLE IF EXISTS HealthConditions;
DROP TABLE IF EXISTS Goals;
DROP TABLE IF EXISTS Profile;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS users (
                                     id INT PRIMARY KEY AUTO_INCREMENT,
                                     username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    registration_date DATE NOT NULL,
    role VARCHAR(255) NOT NULL
    );


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


CREATE TABLE Day (
                     day_id INT AUTO_INCREMENT PRIMARY KEY,
                     name VARCHAR(20)
);

DROP TABLE IF EXISTS Exercises;

CREATE TABLE  Exercises (
                           exercise_id INT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(50),
                           sets INT,
                           reps INT,
                           duration varchar(20),
);

CREATE TABLE Workouts (
                          workout_id INT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(50)
);

CREATE TABLE WorkoutExercises (
                                  workout_id INT,
                                  exercise_id INT,
                                  sequence INT,
                                  FOREIGN KEY (workout_id) REFERENCES Workouts(workout_id),
                                  FOREIGN KEY (exercise_id) REFERENCES Exercises(exercise_id),
                                  PRIMARY KEY (workout_id, exercise_id, sequence)
);

CREATE TABLE UserWorkoutDay (
                                user_workout_day_id INT AUTO_INCREMENT PRIMARY KEY,
                                profile_id INT,
                                day_id INT,
                                workout_id INT,
                                FOREIGN KEY (profile_id) REFERENCES Profile(ProfileID),
                                FOREIGN KEY (day_id) REFERENCES Day(day_id),
                                FOREIGN KEY (workout_id) REFERENCES Workouts(workout_id)
);

INSERT INTO Day (name) VALUES
                           ('Monday'),
                           ('Tuesday'),
                           ('Wednesday'),
                           ('Thursday'),
                           ('Friday'),
                           ('Saturday'),
                           ('Sunday');

