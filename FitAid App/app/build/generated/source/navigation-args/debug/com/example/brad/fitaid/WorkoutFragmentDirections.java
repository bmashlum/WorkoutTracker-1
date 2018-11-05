package com.example.brad.fitaid;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class WorkoutFragmentDirections {
  @NonNull
  public static ActionNavWorkoutsToNavJournal actionNavWorkoutsToNavJournal(@NonNull String exercisesClicked) {
    return new ActionNavWorkoutsToNavJournal(exercisesClicked);
  }

  public static class ActionNavWorkoutsToNavJournal implements NavDirections {
    @NonNull
    private String exercisesClicked;

    public ActionNavWorkoutsToNavJournal(@NonNull String exercisesClicked) {
      this.exercisesClicked = exercisesClicked;
      if (this.exercisesClicked == null) {
        throw new IllegalArgumentException("Argument \"exercisesClicked\" is marked as non-null but was passed a null value.");
      }
    }

    @NonNull
    public ActionNavWorkoutsToNavJournal setExercisesClicked(@NonNull String exercisesClicked) {
      if (exercisesClicked == null) {
        throw new IllegalArgumentException("Argument \"exercisesClicked\" is marked as non-null but was passed a null value.");
      }
      this.exercisesClicked = exercisesClicked;
      return this;
    }

    @NonNull
    public Bundle getArguments() {
      Bundle __outBundle = new Bundle();
      __outBundle.putString("exercisesClicked", this.exercisesClicked);
      return __outBundle;
    }

    public int getActionId() {
      return com.example.brad.fitaid.R.id.action_navWorkouts_to_navJournal;
    }

    @Override
    public boolean equals(Object object) {
      if (this == object) {
          return true;
      }
      if (object == null || getClass() != object.getClass()) {
          return false;
      }
      ActionNavWorkoutsToNavJournal that = (ActionNavWorkoutsToNavJournal) object;
      if (exercisesClicked != null ? !exercisesClicked.equals(that.exercisesClicked) : that.exercisesClicked != null) {
        return false;
      }
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + (exercisesClicked != null ? exercisesClicked.hashCode() : 0);
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNavWorkoutsToNavJournal(actionId=" + getActionId() + "){"
          + "exercisesClicked=" + exercisesClicked
          + "}";
    }
  }
}
