package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.view.UserInterface;


/**
 * Main class for starting the application.
 * This class contains the main method which initializes and
 * starts the user interface, starting the main loop.
 */
public class Main {

  /**
   * Main method to start the application.
   *
   * @param args Main method arguments.
   */
  public static void main(String[] args) {

    UserInterface userInterface = new UserInterface();

    userInterface.init();

    userInterface.start();


  }


}
