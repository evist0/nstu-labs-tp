package com.evist0.client;

import com.evist0.client.models.AppModel;
import com.evist0.common.dto.taxpayers.TaxpayerDTOBuilder;
import com.evist0.common.entities.CompanyTaxpayer;
import com.evist0.common.entities.Entity;
import com.evist0.common.entities.IndividualTaxpayer;
import com.evist0.common.moveSystem.MoveTask;
import com.evist0.common.moveSystem.Vector2d;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.Vector;

public class Database {
    private final AppModel model;
    private Connection connection;

    public Database(AppModel model) throws ClassNotFoundException {
        this.model = model;

        this.connection = null;

        Class.forName("org.postgresql.Driver");

        try {
            String url = "jdbc:postgresql://localhost/nstudb";
            String user = "nstu";
            String password = "123456As";

            this.connection = DriverManager
                    .getConnection(url, user, password);

        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }

        if (this.connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }
    }

    public void saveEntities(boolean shouldSaveIndividual, boolean shouldSaveCompany) {
        this.dropTable();
        this.createTable();

        var toBeSaved = this.model.getTaxpayers()
                .stream()
                .filter(entity -> entity instanceof IndividualTaxpayer && shouldSaveIndividual || entity instanceof CompanyTaxpayer && shouldSaveCompany)
                .toList();

        String sb = """
                INSERT INTO taxpayers(id, image, position_x, position_y, destination_x, destination_y, speed_x, speed_y, timestamp, ttl, isIndividual)
                VALUES
                                
                """ + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?),\n".repeat(toBeSaved.size() - 1)
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (var statement = connection.prepareStatement(sb)) {
            for (int i = 0; i < toBeSaved.size(); i++) {
                var entity = toBeSaved.get(i);

                statement.setObject(i * 11 + 1, entity.getId());
                statement.setInt(i * 11 + 2, entity.getImageVariant());
                statement.setFloat(i * 11 + 3, entity.getPosition().x);
                statement.setFloat(i * 11 + 4, entity.getPosition().y);
                statement.setFloat(i * 11 + 5, entity.getMoveTask().getDestination().x);
                statement.setFloat(i * 11 + 6, entity.getMoveTask().getDestination().y);
                statement.setFloat(i * 11 + 7, entity.getMoveTask().getSpeed().x);
                statement.setFloat(i * 11 + 8, entity.getMoveTask().getSpeed().y);
                statement.setObject(i * 11 + 9, entity.getTimestamp());
                statement.setObject(i * 11 + 10, entity.getTtl());
                statement.setBoolean(i * 11 + 11, entity instanceof IndividualTaxpayer);
            }

            statement.execute();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void loadEntities() {
        String query = """
                SELECT * FROM taxpayers
                """;

        try (var statement = connection.createStatement()) {
            var rs = statement.executeQuery(query);

            var toBeCreated = new Vector<Entity>();

            while (rs.next()) {
                var id = rs.getObject(1);
                var imageVariant = rs.getInt(2);
                var positionX = rs.getFloat(3);
                var positionY = rs.getFloat(4);
                var destinationX = rs.getFloat(5);
                var destinationY = rs.getFloat(6);
                var speedX = rs.getFloat(7);
                var speedY = rs.getFloat(8);
                var timestamp = rs.getObject(9);
                var ttl = rs.getObject(10);
                var isIndividual = rs.getBoolean(11);

                var dtoBuilder = new TaxpayerDTOBuilder()
                        .setImageVariant(imageVariant)
                        .setPosition(new Vector2d(positionX, positionY))
                        .setMoveTask(new MoveTask(new Vector2d(destinationX, destinationY), new Vector2d(speedX, speedY)))
                        .setTimestamp((Long) timestamp)
                        .setTtl((Long) ttl);

                Entity taxpayer = null;

                if (isIndividual) {
                    taxpayer = new IndividualTaxpayer(dtoBuilder.build());
                } else {
                    taxpayer = new CompanyTaxpayer(dtoBuilder.build());
                }

                toBeCreated.add(taxpayer);
            }

            model.setTaxpayers(toBeCreated);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void createTable() {
        String createTableQuery = """
                CREATE TABLE IF NOT EXISTS taxpayers (
                    id UUID PRIMARY KEY,
                    image INT NOT NULL,
                    position_x REAL NOT NULL,
                    position_y REAL NOT NULL,
                    destination_x REAL NOT NULL,
                    destination_y REAL NOT NULL,
                    speed_x REAL NOT NULL,
                    speed_y REAL NOT NULL,
                    timestamp BIGINT NOT NULL,
                    ttl BIGINT NOT NULL,
                    isIndividual BOOL NOT NULL
                )""";

        try (var statement = connection.createStatement()) {
            statement.execute(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void dropTable() {
        String createTableQuery = """
                DROP TABLE IF EXISTS taxpayers""";

        try (var statement = connection.createStatement()) {
            statement.execute(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
