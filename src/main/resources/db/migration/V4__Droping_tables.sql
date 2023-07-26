-- Drop existing tables
DROP TABLE IF EXISTS FoodAllergies;
DROP TABLE IF EXISTS DietaryPreferences;
DROP TABLE IF EXISTS HealthConditions;
DROP TABLE IF EXISTS Goals;
drop table if exists UserWorkoutDay;
drop table if exists WorkoutExercises;
drop table if exists Workouts;
drop table if exists Day;
DROP TABLE IF EXISTS Exercises;



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

CREATE TABLE  Exercises (
                            exercise_id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(50),
                            sets varchar(20),
                            reps varchar(20),
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


-- Create Chat table
CREATE TABLE `Chat` (
                        `ChatID` INT AUTO_INCREMENT PRIMARY KEY,
                        `ProfileID` INT,
                        `ModelName` VARCHAR(255), -- New column for model name
                        FOREIGN KEY (`ProfileID`) REFERENCES `Profile` (`ProfileID`)
);

-- Create Message table
CREATE TABLE `Message` (
                           `MessageID` INT AUTO_INCREMENT PRIMARY KEY,
                           `ChatID` INT,
                           `Role` VARCHAR(20),
                           `Content` VARCHAR(16000),
                           `Timestamp` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (`ChatID`) REFERENCES `Chat` (`ChatID`)
);

-- Insert mock data for Chat and Message tables
INSERT INTO `Chat` (`ChatID`, `ProfileID`, `ModelName`) VALUES
                                                            (1, 101, 'gpt-3.5-turbo'),
                                                            (2, 102, 'gpt-3.5-turbo'),
                                                            (3, 101, 'gpt-3.5-turbo'),
                                                            (4, 103, 'gpt-3.5-turbo');

INSERT INTO `Message` (`ChatID`, `Role`, `Content`) VALUES
                                                        (1, 'user', 'Hello, how are you?'),
                                                        (1, 'assistant', 'I am doing well. How can I assist you?'),
                                                        (1, 'user', 'I have a question about your products.'),
                                                        (1, 'assistant', 'Sure, I\'d be happy to help. What do you need to know?'),
  (2, 'user', 'Hi, is there a sale going on?'),
  (2, 'assistant', 'Yes, we currently have a 20% off sale on all items.'),
  (3, 'user', 'Do you offer free shipping?'),
  (3, 'assistant', 'Yes, we provide free shipping on orders over $50.'),
  (4, 'user', 'Can I track my order?'),
  (4, 'assistant', 'Certainly! Once your order ships, you will receive a tracking number.'),
  (4, 'user', 'Thanks for the information.'),
  (4, 'assistant', 'Youre welcome! If you have any more questions, feel free to ask.');

INSERT INTO Day (name) VALUES
                           ('Monday'),
                           ('Tuesday'),
                           ('Wednesday'),
                           ('Thursday'),
                           ('Friday'),
                           ('Saturday'),
                           ('Sunday');

