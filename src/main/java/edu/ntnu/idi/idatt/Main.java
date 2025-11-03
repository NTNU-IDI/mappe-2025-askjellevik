package edu.ntnu.idi.idatt;

import edu.ntnu.idi.idatt.DiaryRegister.DiaryRegister;
import edu.ntnu.idi.idatt.UserInterface.UserInterface;

public class Main {

  public static void main(String[] args) {

    UserInterface userInterface = new UserInterface();

    UserInterface.init();

    UserInterface.start();


  }


}
