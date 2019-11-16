package moblima.view;

import moblima.controller.*;
import moblima.model.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents the screen to choose a movie
 */
public class ChooseMovie extends View {
	/**
	 * Instantiates a new Choose movie view
	 *
	 * @param userType the user type
	 * @param nextView the next view
	 */
	public ChooseMovie(int userType, View nextView) {
		super("chooseMovie", userType, nextView);
	}

	/**
	 * Display the view
	 */
	public void display() {
		outputPageName("Choose a Movie");

		System.out.println("Chosen cineplex: '" + BookingController.getChosenCineplex().getCineplexName() + "'" + "\n");

		ArrayList < Movie > movieList = BookingController.getMovies();
		if (movieList.isEmpty()) {
			System.out.println("No movies are currently showing in this cinema. Please try another cineplex\n");
			Navigation.goBack();
			return;
		}

		HashMap<Integer, Movie> uniqueMovies = new HashMap<Integer, Movie>();

		printMovies(movieList, uniqueMovies);

		while (true) {
			int input = getChoice("Please select an option: ");
			if (input == 0) {
				Navigation.goBack();
				break;
			} else if (input <= uniqueMovies.size()) {
				boolean doBreak = doMovie(uniqueMovies.get(input));
				if (doBreak == true)
					break;
				else {
					System.out.println();
					printMovies(movieList, uniqueMovies);
				}
			} else {
				System.out.println("\nPlease enter a valid input\n");
			}
		}
	}

	/**
	 * Print the movies of a cineplex
	 *
	 * @param movieList Array of movie instances of a cineplex
	 * @param uniqueMovies a hashmap of movie instances
	 */
	private void printMovies(ArrayList < Movie > movieList, HashMap<Integer, Movie> uniqueMovies) {
		System.out.println("(0) Back");
		int index = 1;
		for (Movie i : movieList) {
			if (getUserType() == 0) {
				System.out.printf("(%s) '%s', Rating: %s, Showing status: %s\n",
						index, i.getTitle(), i.getOverallRatingForOutput(), i.getShowingStatus());
				uniqueMovies.put(index, i);
				index++;
			}
			else {
				if (!i.getShowingStatus().contentEquals("Coming Soon") && !i.getShowingStatus().contentEquals("End of Showing")) {
					System.out.printf("(%s) '%s', Rating: %s, Showing status: %s\n",
							index, i.getTitle(), i.getOverallRatingForOutput(), i.getShowingStatus());
					uniqueMovies.put(index, i);
					index++;
				}
			}
		}
	}

	/**
	 * Display options associated with chosen movie
	 *
	 * @param curMovie the cineplex's movie
	 * @return boolean value based on whether user choose to navigate to next menu or go back to previous menu
	 */
	private boolean doMovie(Movie curMovie) {
		System.out.printf("\nChosen movie: '%s'\n\n", curMovie.getTitle());

		System.out.println("(0) Back");
		if (getUserType() == 0)
			System.out.println("(1) Edit (Add/Remove) showtime information");
		else
			System.out.println("(1) Book this movie");
		System.out.println("(2) Movie information");

		while (true) {
			int input = getChoice("Please select an option: ");
			if (input == 0) {
				return false;
			} else if (input == 1) {
				if (getUserType() == 0) {
					StaffController.setChosenMovie(curMovie);
					Navigation.goTo(new AddShowtime(getUserType(), null));
				} else {
					BookingController.setChosenMovie(curMovie);
					Navigation.goTo(new ChooseShowtime(getUserType(), null));
					return true;
				}
			} else if (input == 2) {
				BookingController.setChosenMovie(curMovie);
				ReviewController.setChosenMovie(curMovie);
				Navigation.goTo(new MovieInformation(getUserType(), null));
				return true;
			} else {
				System.out.println("\nPlease enter a valid input\n");
			}
		}
	}
}
