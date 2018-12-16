package com.basharallabadi.dev.tsa.tsakafkaspark;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.streaming.StreamingQueryException;
import org.apache.spark.sql.types.DataTypes;

import static org.apache.spark.sql.functions.*;

class SparkTweetsConsumer {

    private final static String BROKER = "localhost:9092";

    void consume() throws StreamingQueryException {
        // Setup connection to Kafka
        SparkSession spark = SparkSession
                .builder()
                .appName("com.basharallabadi.tsa-kafka-spark")
                .getOrCreate();

        spark.udf().register("getLength", getLength, DataTypes.IntegerType);
        Dataset<Row> kafka = spark.readStream()
                .format("kafka")
                .option("kafka.bootstrap.servers", BROKER)
                .option("subscribe", "test")
                .option("startingOffsets", "earliest")
                .load();

        Dataset<Row> rowDataSet = kafka
                .selectExpr(new String[]{"CAST(key AS STRING)", "CAST(value AS STRING) AS value"})
                .withColumn("length", callUDF("getLength", col("value")));

        rowDataSet.writeStream()
                .outputMode("append")
                .format("console")
                .option("truncate", false)
                .start()
                .awaitTermination();
    }

    // simple function to simulate data processing
    private static UDF1<String, Integer> getLength = String::length;
}
