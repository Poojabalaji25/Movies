package movie;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input = readInput();
		try {
			connect(input);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String readInput() {
		Scanner user_input = new Scanner(System.in);
		String input;
		System.out.print("Enter your input: INSERT/SelectALL/SelectMovieName ");
		input = user_input.next();
		return input;
	}

	public static String readMovieTitle() {
		Scanner user_input = new Scanner(System.in);
		String MovieTitle;
		System.out.print("Enter your favourite MovieName: ");
		MovieTitle = user_input.next();
		MovieTitle = "'" + MovieTitle + "'";
		return MovieTitle;
	}

	final public static void printResultSet(ResultSet rs) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columnsNumber = rsmd.getColumnCount();
		while (rs.next()) {
			for (int i = 1; i <= columnsNumber; i++) {
				if (i > 1)
					System.out.print(" | ");
				System.out.print(rs.getString(i));
			}
			System.out.println("");
		}
	}

	public static void connect(String input) throws Exception{
		Connection conn = null;
		Statement stmt = null;
		try {
					
			// create a connection to the database
			String url = "jdbc:sqlite:C:\\sqlite\\movie_lite.db";
			conn = DriverManager.getConnection(url);
			System.out.println("Connection to SQLite has been established.");
			PreparedStatement preparedStmt = conn.prepareStatement(
					"INSERT INTO Movies(movie_name, actor, actress, director, release_year) VALUES(?, ?, ?,?, ?)");
			 stmt = conn.createStatement();

			if (input.equalsIgnoreCase("INSERT")) {
				Scanner readInput = new Scanner(System.in);
				Movie newMovie = new Movie();
				System.out.println("Please enter movie name: ");
				newMovie.setMovieName(readInput.nextLine());
				System.out.println("Please enter actor name: ");
				newMovie.setActor(readInput.nextLine());
				System.out.println("Please enter actress name: ");
				newMovie.setActress(readInput.nextLine());
				System.out.println("Please enter director name: ");
				newMovie.setDirector(readInput.nextLine());
				System.out.println("Please enter year of release: ");
				newMovie.setReleaseYear(readInput.nextInt());
				preparedStmt.setString(1, newMovie.getMovieName());
				preparedStmt.setString(2, newMovie.getActor());
				preparedStmt.setString(3, newMovie.getActress());
				preparedStmt.setString(4, newMovie.getDirector());
				preparedStmt.setInt(5, newMovie.getReleaseYear());
				preparedStmt.executeUpdate();
				System.out.println("Inserted records into the movies table...");
			} else if (input.equalsIgnoreCase("SelectALL")) {
				ResultSet rs = stmt.executeQuery("SELECT * FROM Movies");
				printResultSet(rs);
			} else if (input.equalsIgnoreCase("SelectMovieName")) {
				String MovieTitle = readMovieTitle();
				ResultSet rs = stmt.executeQuery("SELECT * FROM Movies where Movies.MovieTitle =" + MovieTitle);
				printResultSet(rs);

			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}

	}
}
