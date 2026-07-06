-- ============================================
-- Hotel Management System - Database Schema
-- ============================================

CREATE DATABASE IF NOT EXISTS hotel_management;
USE hotel_management;

-- Users table (for authentication)
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'STAFF') NOT NULL DEFAULT 'STAFF',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Rooms table
CREATE TABLE IF NOT EXISTS rooms (
    id INT AUTO_INCREMENT PRIMARY KEY,
    room_number VARCHAR(10) NOT NULL UNIQUE,
    room_type ENUM('STANDARD', 'DELUXE', 'SUITE') NOT NULL,
    price_per_night DOUBLE NOT NULL,
    status ENUM('AVAILABLE', 'OCCUPIED', 'MAINTENANCE') NOT NULL DEFAULT 'AVAILABLE',
    capacity INT NOT NULL DEFAULT 2,
    description VARCHAR(255)
);

-- Guests table
CREATE TABLE IF NOT EXISTS guests (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    id_number VARCHAR(50) UNIQUE NOT NULL,
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Employees table
CREATE TABLE IF NOT EXISTS employees (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(20),
    position VARCHAR(50) NOT NULL,
    salary DOUBLE NOT NULL,
    hire_date DATE NOT NULL,
    status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE'
);

-- Reservations table
CREATE TABLE IF NOT EXISTS reservations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    guest_id INT NOT NULL,
    room_id INT NOT NULL,
    check_in DATE NOT NULL,
    check_out DATE NOT NULL,
    total_price DOUBLE NOT NULL,
    status ENUM('PENDING', 'CONFIRMED', 'CHECKED_IN', 'CHECKED_OUT', 'CANCELLED') NOT NULL DEFAULT 'PENDING',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (guest_id) REFERENCES guests(id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(id) ON DELETE CASCADE
);

-- Payments table
CREATE TABLE IF NOT EXISTS payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    reservation_id INT NOT NULL,
    amount DOUBLE NOT NULL,
    method ENUM('CASH', 'CREDIT_CARD', 'DEBIT_CARD', 'TRANSFER') NOT NULL,
    status ENUM('PENDING', 'PAID', 'REFUNDED') NOT NULL DEFAULT 'PENDING',
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE
);

-- Default admin user (password: admin123)
INSERT INTO users (username, password, role) VALUES
('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'ADMIN'),
('staff1', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', 'STAFF');

-- Sample rooms
INSERT INTO rooms (room_number, room_type, price_per_night, status, capacity, description) VALUES
('101', 'STANDARD', 350000, 'AVAILABLE', 2, 'Standard room with garden view'),
('102', 'STANDARD', 350000, 'AVAILABLE', 2, 'Standard room with pool view'),
('201', 'DELUXE', 650000, 'AVAILABLE', 3, 'Deluxe room with balcony'),
('202', 'DELUXE', 650000, 'AVAILABLE', 3, 'Deluxe room ocean view'),
('301', 'SUITE', 1200000, 'AVAILABLE', 4, 'Presidential suite with full amenities'),
('302', 'SUITE', 1200000, 'AVAILABLE', 4, 'Family suite with living area');

-- Sample employees
INSERT INTO employees (name, email, phone, position, salary, hire_date, status) VALUES
('Budi Santoso', 'budi@hotel.com', '08112345678', 'Manager', 8000000, '2020-01-15', 'ACTIVE'),
('Sari Dewi', 'sari@hotel.com', '08123456789', 'Receptionist', 4500000, '2021-03-10', 'ACTIVE'),
('Ahmad Fauzi', 'ahmad@hotel.com', '08134567890', 'Housekeeping', 3500000, '2022-06-01', 'ACTIVE');