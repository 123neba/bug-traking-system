CREATE DATABASE IF NOT EXISTS bugtracker_db;
USE bugtracker_db;


CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL
);


INSERT IGNORE INTO users (username, password, role) VALUES ('admin', 'admin123', 'Admin');
INSERT IGNORE INTO users (username, password, role) VALUES ('dev1', 'dev123', 'Developer');


CREATE TABLE IF NOT EXISTS bugs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(50) DEFAULT 'Open',
    assigned_to VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
