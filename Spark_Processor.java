package DownloadNotifier;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Spark_Processor {
    public void createReport() {
        long start_time = System.currentTimeMillis();
        // Spark Session
        SparkSession spark = SparkSession
                .builder()
                .appName("SparkTransactionProcessor")
                .master("local[*]")
                .getOrCreate();

        // Assuming you have JDBC connection details for your database
        String jdbcUrl = "jdbc:mysql://localhost:3306/Downtime";
        String username = "root";
        String password = "admin";

                // Read data from tables
        Dataset<Row> transactionDF = spark.read()
                .format("jdbc")
                .option("url", jdbcUrl)
                .option("dbtable", "cust_txn")
                .option("user", username)
                .option("password", password)
                .load();

        // Perform transformations to align with MT942 format
        Dataset<Row> mt942DF = transactionDF.select(
                transactionDF.col("AccountNumber").alias("AccountNumber"),
                transactionDF.col("transaction_date").alias("Date"),
                transactionDF.col("amount").alias("Amount"),
                transactionDF.col("currency").alias("Currency"),
                transactionDF.col("TransactionType").alias("TransactionType"),
                transactionDF.col("Description").alias("Description")
        );

        // Write data to CSV file in MT942 format
        String outputFile = "../target/mt942_statement.csv";      
        mt942DF.write()
                .format("csv")
                .option("header", "true")
                .mode("overwrite") // or mode("append") depending on your requirement
                .save(outputFile);
        long end_time = System.currentTimeMillis();
        System.out.println(end_time - start_time);
        // Stop Spark Session
        spark.stop();
    }
}
