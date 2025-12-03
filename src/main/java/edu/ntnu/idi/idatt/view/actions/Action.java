package edu.ntnu.idi.idatt.view.actions;

/**
 * Represents an action that can be executed.
 * This class should be implemented from other
 * classes that define more specific actions
 * like menu logic.
 */
public interface Action {
  /**
   *  Executes the action logic.
   *  Implementing classes should override and
   *  define the behaviour of this method.
   */
  void execute();
}
