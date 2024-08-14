CREATE TABLE IF NOT EXISTS user_stocks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    stock_symbol TEXT NOT NULL,
    UNIQUE(stock_symbol)
);

CREATE TABLE IF NOT EXISTS all_stocks (
    id INT PRIMARY KEY AUTO_INCREMENT,
    stock_symbol TEXT NOT NULL,
    UNIQUE(stock_symbol)
);

CREATE TABLE IF NOT EXISTS stock_data (
    id INT PRIMARY KEY AUTO_INCREMENT,
    stock_symbol TEXT NOT NULL,
    company_name TEXT,
    last_price REAL,
    high_price REAL,
    low_price REAL,
    volume REAL,
    daily_change REAL,
    weekly_change REAL,
    monthly_change REAL,
    yearly_change REAL,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);
