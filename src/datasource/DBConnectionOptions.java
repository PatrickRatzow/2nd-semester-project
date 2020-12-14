package datasource;

public class DBConnectionOptions {
	private final String dataSource;
	private final String host;
	private final String user;
	private final String database;
	private final String password;
	private final int port;
	private final int poolSize;
	private final boolean test;
	
	public DBConnectionOptions(String dataSource, String host, String user, String database, String password,
							   int poolSize, int port, boolean test) {
		this.dataSource = dataSource;
		this.host = host;
		this.user = user;
		this.database = database;
		this.password = password;
		this.poolSize = poolSize;
		this.port = port;
		this.test = test;
	}
	
	public String getDataSource() {
		return dataSource;
	}
	
	public String getHost() {
		return host;
	}
	
	public String getUser() {
		return user;
	}

	public String getDatabase() {
		return database;
	}
	
	public String getPassword() {
		return password;
	}
	
	public int getPoolSize() {
		return poolSize;
	}
	
	public int getPort() {
		return port;
	}
	
	public boolean isTest() {
		return test;
	}
}
