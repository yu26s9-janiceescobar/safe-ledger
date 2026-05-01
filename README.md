# Safe Ledger

## Description
A Java command-line application for tracking business transactions for a skincare business.

## Features
- Add deposits or payments.
- View Ledger with a page browsing feature.
- Filter by deposits, payments.
- Displays reports of month to date, prior month transactions, year to date, and prior year transactions.
- Search by vendor.
- Custom search with multiple filters including start date, end date, description, 
vendor, and between a minimum and maximum amount.
- Edit transactions before confirming addition to ledger account.
- Saves all transactions to a CSV file.

## How to Run
1. Clone Repository.
2. Open in IntelliJ
3. Run in 'Main.java'

## Technologies Used
- Java
- CSV file storage.

## Author 
Janice Escobar-Hernandez

## Technical Highlights
### Displaying Transactions 
One of the more challenging features to implement was displaying 10 transactions per page. 
The program displays a maximum of ten transactions and handles edge cases such as reaching the first 
and last page, displaying pages with less than 10 transactions when the total amount of transactions
is not divisible by 10, and handling if there have been no transactions added to the CSV before it reaches the display page method. 
I first worked on this feature in my "online-store" program that can be found on my github and really improved on the method
in this program.

Key Logic Includes:
- Calculating the last page based on number of transactions, if transaction count is not divisible by ten, I would add an extra page.
- Tracking remaining transactions to avoid index out of bounds error.
- Tracking and returning the correct page number when user attempts to go before page 1 or after the last page.
- Passing the transaction list in the parameter so any transaction list filtered through other methods can be displayed in the same way.