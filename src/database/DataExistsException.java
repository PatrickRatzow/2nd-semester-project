package database;

public class DataExistsException extends Exception {
    public DataExistsException(String msg) {
        super(msg);
    }
}
