package moblima.view;

import moblima.model.StackArg;

public class BaseMenu {
	public BaseMenu() {
	}

	public void display(Navigation navigation) {
		System.out.println(
				  "=====================================\n"
				+ "---------Welcome to MOBLIMA!---------\n"
				+ "=====================================\n"
				+ "(1) Admin\n"
				+ "(2) Movie-goer\n"
				+ "(3) Quit");

		StackArg curView = navigation.getLastView();
		while (true) {
			int input = navigation.getChoice("Please select an option: ");
			if (input == 1) {
				navigation.goTo(new StackArg("adminMenu", 0));
				break;
			} else if (input == 2) {
				navigation.goTo(new StackArg("moviegoerMenu", 1));
				break;
			} else if (input == 3) {
				navigation.exit();
			} else {
				System.out.println("\nPlease enter a valid input!\n");
			}
		}
	}
}