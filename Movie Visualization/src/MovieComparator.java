import java.util.*;

public class MovieComparator {

    public static List<Movie> compareMoviesFromCSV(String filePath1, String filePath2) {
        List<Movie> moviesPerson1 = MovieReader.readMoviesFromCSV(filePath1);
        List<Movie> moviesPerson2 = MovieReader.readMoviesFromCSV(filePath2);

        Map<String, Movie> moviesPerson1Map = new HashMap<>();
        for (Movie movie : moviesPerson1) {
            if (movie.getRating() >= 4.0) { // Only consider movies with rating >= 4.0
                moviesPerson1Map.put(movie.getName(), movie);
            }
        }

        List<Movie> commonMovies = new ArrayList<>();
        for (Movie movie : moviesPerson2) {
            if (movie.getRating() >= 4.0 && moviesPerson1Map.containsKey(movie.getName())) {
                // If the movie is rated >= 4.0 and is in the first list, add it to the result
                Movie person1Movie = moviesPerson1Map.get(movie.getName());
                if (person1Movie.getRating() >= 4.0 && movie.getRating() >= 4.0) {
                    // Add movie to common list
                    commonMovies.add(new Movie(movie.getName(), movie.getYear(), movie.getRating()));
                }
            }
        }

        return commonMovies;
    }
}