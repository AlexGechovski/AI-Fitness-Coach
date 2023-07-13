DROP TABLE IF EXISTS DietaryPreferences;

CREATE TABLE DietaryPreferences (
                                    PreferenceID INT PRIMARY KEY,
                                    ProfileID INT UNIQUE,
                                    Vegetarian BOOLEAN,
                                    Vegan BOOLEAN,
                                    GlutenFree BOOLEAN,
                                    FOREIGN KEY (ProfileID) REFERENCES Profile(ProfileID)
);

-- FoodAllergies table
CREATE TABLE FoodAllergies (
                               AllergyID INT PRIMARY KEY,
                               ProfileID INT,
                               AllergyName VARCHAR(255),
                               FOREIGN KEY (ProfileID) REFERENCES Profile(ProfileID)
);