package moblima.view;

import moblima.controller.*;
import moblima.model.*;

/**
 * Represents the screen that shows all information on a movie
 */
public class MovieInformation extends View {
	/**
	 * Instantiates a new Movie information view
	 *
	 * @param userType the user type
	 * @param nextView the next view
	 */
	public MovieInformation(int userType, View nextView) {
		super("movieInformation", userType, nextView);
	}

	/**
	 * Display the view
	 */
	public void display() {

		outputPageName("Movie Information");

		if (BookingController.getChosenTitle() != null) {
			Movie actualMovie = BookingController.getChosenCineplex().getMovieWithTitle(BookingController.getChosenTitle());
			BookingController.setChosenMovie(actualMovie);
			ReviewController.setChosenMovie(actualMovie);
		}

		BookingController.getChosenMovie().output();
		System.out.println();

		if (getUserType() == 0)
			System.out.println("(0) Back");
		else {
			System.out.println(
					"(0) Back\n"
							+ "(1) Book this movie\n"
							+ "(2) See all reviews\n"
							+ "(3) Leave a review");
		}

		boolean loop = true;
		while (loop) {
			int input = getChoice("Please select an option: ");
			switch (input) {
				case 0:
					Navigation.goBack();
					loop = false;
					break;

				case 1:
					if (getUserType() == 0)
						System.out.println("\nPlease enter a valid input!\n");
					else {
						if (BookingController.getChosenMovie().getShowingStatus().contentEquals("Coming Soon"))
							System.out.println("Sorry, this movie is not showing yet.");
						else if (BookingController.getChosenMovie().getShowingStatus().contentEquals("End of Showing"))
							System.out.println("Sorry, this movie is no longer showing.");
						else {
							Navigation.goTo(new ChooseShowtime(getUserType(), null));
							loop = false;
						}
					}
					break;

				case 2:
					if (getUserType() == 0)
						System.out.println("\nPlease enter a valid input!\n");
					else {
						Navigation.goTo(new ReviewList(getUserType(), null));
						loop = false;
					}
					break;

				case 3:
					if (getUserType() == 0)
						System.out.println("\nPlease enter a valid input!\n");
					else {
						Navigation.goTo(new LeaveReview(getUserType(), null));
						loop = false;
					}
					break;

				default:
					System.out.println("\nPlease enter a valid input!\n");
			}
		}
	}
}
