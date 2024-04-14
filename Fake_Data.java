package DownloadNotifier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import com.github.javafaker.Faker;

public class Fake_Data {
    public void createData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            // Connect to your PostgreSQL database
            conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/Downtime?",
                "root",
                "admin"
            );

            // SQL query to insert dummy transactions
            String sql = "INSERT INTO cust_txn (AccountNumber, transaction_date, amount, currency, TransactionType, Description) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);

            // Generate and insert 1 million dummy transactions
            generateAndInsertDummyTransactions(pstmt, 1000000);

            System.out.println("Dummy transactions inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close PreparedStatement and Connection
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to generate and insert dummy transactions
    private void generateAndInsertDummyTransactions(PreparedStatement pstmt, int numTransactions) throws SQLException {
        Random random = new Random();
        Faker faker = new Faker();
        for (int i = 0; i < numTransactions; i++) {
            pstmt.setLong(1, ThreadLocalRandom.current().nextLong(1000000000));
            Timestamp transactionDate = new Timestamp(System.currentTimeMillis() - ThreadLocalRandom.current().nextLong(365 * 24 * 60 * 60 * 1000L)); // Random date in the last year
            pstmt.setTimestamp(2, transactionDate); // transaction_date
            pstmt.setDouble(3, 10 + random.nextDouble() * 990); // amount (between 10 and 1000)
            String[] currencies = {"USD", "EUR", "GBP","AUD","SGD","AED","HKD","INR"};
            pstmt.setString(4, currencies[random.nextInt(currencies.length)]); // currency
            String[] transactionTypes = {"debit", "credit"};
            pstmt.setString(5, transactionTypes[random.nextInt(transactionTypes.length)]); // TransactionType
            pstmt.setString(6, faker.company().name() + " " + faker.lorem().sentence()); // Random merchant name + description
            pstmt.addBatch();
        }
        pstmt.executeBatch();
    }
}
