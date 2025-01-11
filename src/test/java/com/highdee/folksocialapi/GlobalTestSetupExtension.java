package com.highdee.folksocialapi;

import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class GlobalTestSetupExtension implements BeforeAllCallback {

    @Override
    public void beforeAll(ExtensionContext context) {
        DataSource dataSource = SpringExtension.getApplicationContext(context).getBean(DataSource.class);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {

            statement.execute("SET FOREIGN_KEY_CHECKS = 0;");

            // Empty all tables
            statement.execute("TRUNCATE TABLE post_media;");
            statement.execute("TRUNCATE TABLE posts;");
            statement.execute("TRUNCATE TABLE users;");
            //


            statement.execute("SET FOREIGN_KEY_CHECKS = 1;");

            System.out.println("Database cleared successfully!");
        } catch (SQLException e) {
            System.out.println("Unable to clear Database");
            throw new RuntimeException(e);
        }
    }
}
