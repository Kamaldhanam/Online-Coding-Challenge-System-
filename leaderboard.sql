CREATE DATABASE IF NOT EXISTS coding_platform;

USE coding_platform;

CREATE TABLE IF NOT EXISTS leaderboard (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    problem_title VARCHAR(100) NOT NULL,
    score INT NOT NULL,
    submission_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
