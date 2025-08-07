package Server.managers;

import Common.collectionElements.Coordinates;
import Common.collectionElements.FuelType;
import Common.collectionElements.Vehicle;
import Server.utils.User;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.ConcurrentHashMap;

public class DatabaseManager {
    private Connection connection;
    private ResultSet resultSet;
    private ScriptRunner scriptRunner;

    public DatabaseManager() {
        try {
            this.connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/studs", "s465993", System.getenv("PSWD"));
            this.scriptRunner = new ScriptRunner(connection);
            setScriptRunnerConfig();
            System.out.println("connected");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            FileReader reader = new FileReader("src/main/java/Server/sql/create.sql", StandardCharsets.UTF_8);
            scriptRunner.runScript(reader);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ConcurrentHashMap<String, Vehicle> getCollection() {
        ConcurrentHashMap<String, Vehicle> collection = new ConcurrentHashMap<>();
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT collection.key, collection.owner_id, elements.id AS element_id, elements.name, elements.creation_date, elements.engine_power, elements.capacity, elements.distance_travelled, elements.fuel_type, coordinates.x, coordinates.y FROM collection JOIN elements ON collection.element_id = elements.id JOIN coordinates ON elements.coordinates_id = coordinates.id");
            while (resultSet.next()) {

                Long id = resultSet.getLong("element_id");
                String name = resultSet.getString("name");
                Integer x = resultSet.getInt("x");
                Long y = resultSet.getLong("y");
                ZonedDateTime creationDate = resultSet.getObject("creation_date", OffsetDateTime.class).atZoneSameInstant(ZoneId.of("Europe/Moscow"));
                Long enginePower = resultSet.getLong("engine_power");
                double capacity = resultSet.getDouble("capacity");
                Long distanceTravelled = resultSet.getLong("distance_travelled");
                FuelType fuelType = FuelType.valueOf(resultSet.getString("fuel_type"));
                int ownerID = resultSet.getInt("owner_id");
                Vehicle vehicle = new Vehicle(id, name, new Coordinates(x, y), creationDate, enginePower, capacity, distanceTravelled, fuelType, ownerID);
                String key = resultSet.getString("key");
                collection.put(key, vehicle);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return collection;
    }

    public ConcurrentHashMap<Integer, User> getUsers() {
        ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM users");
            while (resultSet.next()) {
                Integer userID = resultSet.getInt("id");
                String userName = resultSet.getString("login");
                String password = resultSet.getString("password");
                User user = new User(userName, password);
                users.put(userID, user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void insertUser(String name, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (login, password) VALUES (?, ?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, password);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean insertNewElement(String key, Vehicle vehicle, String login) {
        boolean result = false;
        try {
            connection.setAutoCommit(false);
            vehicle.setOwnerID(getOwnerID(login));
            PreparedStatement preparedStatementCoordinates = connection.prepareStatement("INSERT INTO coordinates (x, y) VALUES (?, ?)");
            preparedStatementCoordinates.setInt(1, vehicle.getCoordinates().getX());
            preparedStatementCoordinates.setLong(2, vehicle.getCoordinates().getY());

            preparedStatementCoordinates.executeUpdate();

            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT id FROM coordinates ORDER BY id DESC LIMIT 1");
            long maxID = 1;
            while (resultSet.next()) {
                maxID = resultSet.getInt("id");
            }

            PreparedStatement preparedStatementElement = connection.prepareStatement("INSERT INTO elements (name, coordinates_id, creation_date, engine_power, capacity, distance_travelled, fuel_type) VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatementElement.setString(1, vehicle.getName());
            preparedStatementElement.setLong(2, maxID);
            preparedStatementElement.setObject(3, vehicle.getCreationDate().toOffsetDateTime());
            preparedStatementElement.setLong(4, vehicle.getEnginePower());
            preparedStatementElement.setDouble(5, vehicle.getCapacity());
            preparedStatementElement.setLong(6, vehicle.getDistanceTravelled());
            preparedStatementElement.setString(7, vehicle.getFuelType().getFuelType());

            preparedStatementElement.executeUpdate();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT id FROM elements ORDER BY id DESC LIMIT 1");
            maxID = 1;
            while (resultSet.next()) {
                maxID = resultSet.getInt("id");
            }

            PreparedStatement preparedStatementCollection = connection.prepareStatement("INSERT INTO collection (key, element_id, owner_id) VALUES (?, ?, ?)");
            preparedStatementCollection.setString(1, key);
            preparedStatementCollection.setInt(2, (int) maxID);
            preparedStatementCollection.setInt(3, vehicle.getOwnerID());

            preparedStatementCollection.executeUpdate();

            connection.commit();
            result = true;
        }
        catch (SQLException e) {
            try {
                e.printStackTrace();
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean updateElementByID(int id, Vehicle vehicle, String login) {
        boolean result = false;
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatementElement = connection.prepareStatement("UPDATE elements SET name = ?, creation_date = ?, engine_power = ?, capacity = ?, distance_travelled = ?, fuel_type = ? WHERE id = ? AND EXISTS (SELECT 1 FROM collection WHERE collection.element_id = elements.id AND collection.owner_id = ?)");
            preparedStatementElement.setString(1, vehicle.getName());
            preparedStatementElement.setObject(2, vehicle.getCreationDate().toOffsetDateTime());
            preparedStatementElement.setLong(3, vehicle.getEnginePower());
            preparedStatementElement.setDouble(4, vehicle.getCapacity());
            preparedStatementElement.setLong(5, vehicle.getDistanceTravelled());
            preparedStatementElement.setString(6, vehicle.getFuelType().name());
            preparedStatementElement.setInt(7, id);
            preparedStatementElement.setInt(8, getOwnerID(login));

            PreparedStatement preparedStatementCoordinates = connection.prepareStatement("UPDATE coordinates SET x = ?, y = ? WHERE id = ?");
            preparedStatementCoordinates.setInt(1, vehicle.getCoordinates().getX());
            preparedStatementCoordinates.setLong(2, vehicle.getCoordinates().getY());
            preparedStatementCoordinates.setInt(3, id);

            int affectedRows = preparedStatementElement.executeUpdate() + preparedStatementCoordinates.executeUpdate();

            if (affectedRows > 0 && affectedRows % 2 == 0) {
                connection.commit();
                result = true;
            }
            else {
                connection.rollback();
            }
        }
        catch (SQLException e) {
            try {
                e.printStackTrace();
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean deleteElementByKey(String key, String login) {
        boolean result = false;
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatementDelete = connection.prepareStatement("DELETE FROM coordinates WHERE id = (SELECT coordinates_id FROM elements WHERE id = (SELECT element_id FROM collection WHERE key = ? AND owner_id = ?))");
            preparedStatementDelete.setString(1, key);
            preparedStatementDelete.setInt(2, getOwnerID(login));

            int affectedRows = preparedStatementDelete.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                result = true;
            }
            else {
                connection.rollback();
            }
        }
        catch (SQLException e) {
            try {
                e.printStackTrace();
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean clearCollection(String login) {
        boolean result = false;
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM coordinates WHERE id IN (SELECT coordinates_id FROM elements WHERE id IN (SELECT element_id FROM collection WHERE owner_id = ?))");
            preparedStatement.setInt(1, getOwnerID(login));

            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows > 0) {
                connection.commit();
                result = true;
            }
            else {
                connection.rollback();
            }
        }
        catch (SQLException e) {
            try {
                e.printStackTrace();
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public boolean updateByKey(String key, Vehicle vehicle, String login) {
        boolean result = false;
        try {
            connection.setAutoCommit(false);

            PreparedStatement preparedStatementElement = connection.prepareStatement("UPDATE element SET name = ?, creation_date = ?, engine_power = ?, capacity = ?, distance_travelled = ?, fuel_type = ? WHERE id = (SELECT element_id FROM collection WHERE key = ?) AND EXISTS (SELECT 1 FROM collection WHERE collection.element_id = elements.id AND collection.owner_id = ?)");
            preparedStatementElement.setString(1, vehicle.getName());
            preparedStatementElement.setObject(2, vehicle.getCreationDate().toOffsetDateTime());
            preparedStatementElement.setLong(3, vehicle.getEnginePower());
            preparedStatementElement.setDouble(4, vehicle.getCapacity());
            preparedStatementElement.setLong(5, vehicle.getDistanceTravelled());
            preparedStatementElement.setString(6, vehicle.getFuelType().name());
            preparedStatementElement.setString(7, key);
            preparedStatementElement.setInt(8, getOwnerID(login));

            PreparedStatement preparedStatementCoordinates = connection.prepareStatement("UPDATE coordinates SET x = ?, y = ?, WHERE id = ?");
            preparedStatementCoordinates.setInt(1, vehicle.getCoordinates().getX());
            preparedStatementCoordinates.setLong(2, vehicle.getCoordinates().getY());
            preparedStatementCoordinates.setInt(3, Integer.parseInt(vehicle.getId().toString()));

            int affectedRows = preparedStatementElement.executeUpdate() + preparedStatementCoordinates.executeUpdate();

            if (affectedRows > 0 && affectedRows % 2 == 0) {
                connection.commit();
                result = true;
            }
            else {
                connection.rollback();
            }
        }
        catch (SQLException e) {
            try {
                e.printStackTrace();
                connection.rollback();
            } catch (SQLException ex) {
                e.printStackTrace();
            }
        }
        finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void dropDB() {
        try {
            FileReader reader = new FileReader("src/main/java/Server/sql/drop.sql", StandardCharsets.UTF_8);
            scriptRunner.runScript(reader);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getOwnerID(String login) {
        int id = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT id FROM users WHERE login = ?");
            preparedStatement.setString(1, login);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    private void setScriptRunnerConfig() {
        scriptRunner.setStopOnError(true);
        scriptRunner.setAutoCommit(false);
        scriptRunner.setLogWriter(null);
        scriptRunner.setSendFullScript(false);
    }

}
