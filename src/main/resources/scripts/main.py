import mysql.connector
from dotenv import load_dotenv, dotenv_values
import os
from bs4 import BeautifulSoup 
from urllib import request
import json
from datetime import datetime

class Config:
    load_dotenv()
    config = dotenv_values(os.path.join(os.path.dirname(__file__), ".env"))


    DATABASE_NAME = config["DATABASE_NAME"]
    SQL_FILE = config['SQL_FILE']
    URL = config['URL']
    DB_USER = config['DB_USER']
    DB_PASSWORD = config['DB_PASSWORD']
    DB_HOST = config['DB_HOST']


def get_all_stocks():

   with request.urlopen(Config.URL) as f:
    data = f.read().decode('utf-8')

    d = BeautifulSoup(data, 'html.parser')
    tds = []

    divs = d.find_all("div", {"class": "col-md-12"})

    div = divs[5]  

    table = div.find("table", {"class": "table table-hover table-striped sort"})

    rows = table.find_all('tr')
    for row in rows:
        cells = row.find_all('td')
        tds.append([cell.get_text(strip=True) for cell in cells])
    
    return tds
   

def update_all_stocks_with_stocks_param(stocks):
    """Web sayfasındaki hisse senetlerini kontrol eder ve veritabanını günceller."""
    conn = mysql.connector.connect(
        host=Config.DB_HOST,
        user=Config.DB_USER,
        password=Config.DB_PASSWORD,
        database=Config.DATABASE_NAME
    )
    c = conn.cursor()

    tds = stocks
    new_codes = [row[0] for row in tds if row]
    
    c.execute("SELECT stock_symbol FROM all_stocks")
    existing_stocks = [row[0] for row in c.fetchall()]

    new_stocks = [(code,) for code in new_codes if code not in existing_stocks]
    if new_stocks:
        c.executemany("INSERT INTO all_stocks (stock_symbol) VALUES (%s)", new_stocks)
        print(f"Added {len(new_stocks)} new stocks to the database.")

    conn.commit()
    conn.close()


def update_all_stocks():
    """Web sayfasındaki hisse senetlerini kontrol eder ve veritabanını günceller."""
    conn = mysql.connector.connect(
        host=Config.DB_HOST,
        user=Config.DB_USER,
        password=Config.DB_PASSWORD,
        database=Config.DATABASE_NAME
    )
    c = conn.cursor()

    tds = get_all_stocks()
    new_codes = [row[0] for row in tds if row]
    
    c.execute("SELECT stock_symbol FROM all_stocks")
    existing_stocks = [row[0] for row in c.fetchall()]

    new_stocks = [(code,) for code in new_codes if code not in existing_stocks]
    if new_stocks:
        c.executemany("INSERT INTO all_stocks (stock_symbol) VALUES (%s)", new_stocks)
        print(f"Added {len(new_stocks)} new stocks to the database.")

    conn.commit()
    conn.close()


def init_db(filename):
    conn = mysql.connector.connect(
        host=Config.DB_HOST,
        user=Config.DB_USER,
        password=Config.DB_PASSWORD,
        database=Config.DATABASE_NAME
    )
    c = conn.cursor()

    update_all_stocks()
    
    conn.commit()
    conn.close()

def get_user_stocks():
    conn = mysql.connector.connect(
        host=Config.DB_HOST,
        user=Config.DB_USER,
        password=Config.DB_PASSWORD,
        database=Config.DATABASE_NAME
    )
    c = conn.cursor()

    
    c.execute("SELECT stock_symbol FROM user_stocks")
    stocks = c.fetchall()

    
    conn.close()

    
    return [stock[0] for stock in stocks]

def main():
    init_db(Config.SQL_FILE)

    user_stocks = get_user_stocks()

    print("\nFinal list of stocks you're following:")
    for stock in user_stocks:
        print(f"- {stock}")

    stock_datas = get_all_stocks()

    user_stock_datas = [data for data in stock_datas if data if data[0] in user_stocks]

    update_all_stocks_with_stocks_param(stock_datas)

    
    current_date = datetime.now().strftime("%Y-%m-%d")

    user_stock_datas = [
        {
            "date": current_date,  # Tarih eklendi
            "stock_symbol": data[0],
            "company_name": data[1],
            "last_price": data[2],
            "high_price": data[3],
            "low_price": data[4],
            "volume": data[5],
            "daily_change": data[6],
            "weekly_change": data[7],
            "monthly_change": data[8],
            "yearly_change": data[9]
        }
        for data in stock_datas if data if data[0] in user_stocks
    ]

    with open(os.path.join(os.path.dirname(__file__),'../data/user_stock_datas.json'), 'w') as json_file:
        json.dump(user_stock_datas, json_file, indent=4)

    print("User's stock data has been saved to 'user_stock_datas.json'.")


if __name__=="__main__":
    main()
