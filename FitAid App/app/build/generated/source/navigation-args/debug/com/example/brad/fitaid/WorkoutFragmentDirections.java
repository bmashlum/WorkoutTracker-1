package com.example.brad.fitaid;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.navigation.NavDirections;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class WorkoutFragmentDirections {
  @NonNull
  public static ActionNavWorkoutsToNavJournal actionNavWorkoutsToNavJournal() {
    return new ActionNavWorkoutsToNavJournal();
  }

  public static class ActionNavWorkoutsToNavJournal implements NavDirections {
    public ActionNavWorkoutsToNavJournal() {
    }

    @NonNull
    public Bundle getArguments() {
      Bundle __outBundle = new Bundle();
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
      if (getActionId() != that.getActionId()) {
        return false;
      }
      return true;
    }

    @Override
    public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + getActionId();
      return result;
    }

    @Override
    public String toString() {
      return "ActionNavWorkoutsToNavJournal(actionId=" + getActionId() + "){"
          + "}";
    }
  }
}
