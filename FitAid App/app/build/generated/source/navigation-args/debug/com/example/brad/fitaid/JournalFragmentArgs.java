package com.example.brad.fitaid;

import android.os.Bundle;
import androidx.annotation.NonNull;
import java.lang.IllegalArgumentException;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;

public class JournalFragmentArgs {
  @NonNull
  private String exercisesClicked;

  private JournalFragmentArgs() {
  }

  @NonNull
  public static JournalFragmentArgs fromBundle(Bundle bundle) {
    JournalFragmentArgs result = new JournalFragmentArgs();
    bundle.setClassLoader(JournalFragmentArgs.class.getClassLoader());
    if (bundle.containsKey("exercisesClicked")) {
      result.exercisesClicked = bundle.getString("exercisesClicked");
      if (result.exercisesClicked == null) {
        throw new IllegalArgumentException("Argument \"exercisesClicked\" is marked as non-null but was passed a null value.");
      }
    } else {
      throw new IllegalArgumentException("Required argument \"exercisesClicked\" is missing and does not have an android:defaultValue");
    }
    return result;
  }

  @NonNull
  public String getExercisesClicked() {
    return exercisesClicked;
  }

  @NonNull
  public Bundle toBundle() {
    Bundle __outBundle = new Bundle();
    __outBundle.putString("exercisesClicked", this.exercisesClicked);
    return __outBundle;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
        return true;
    }
    if (object == null || getClass() != object.getClass()) {
        return false;
    }
    JournalFragmentArgs that = (JournalFragmentArgs) object;
    if (exercisesClicked != null ? !exercisesClicked.equals(that.exercisesClicked) : that.exercisesClicked != null) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (exercisesClicked != null ? exercisesClicked.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "JournalFragmentArgs{"
        + "exercisesClicked=" + exercisesClicked
        + "}";
  }

  public static class Builder {
    @NonNull
    private String exercisesClicked;

    public Builder(JournalFragmentArgs original) {
      this.exercisesClicked = original.exercisesClicked;
    }

    public Builder(@NonNull String exercisesClicked) {
      this.exercisesClicked = exercisesClicked;
      if (this.exercisesClicked == null) {
        throw new IllegalArgumentException("Argument \"exercisesClicked\" is marked as non-null but was passed a null value.");
      }
    }

    @NonNull
    public JournalFragmentArgs build() {
      JournalFragmentArgs result = new JournalFragmentArgs();
      result.exercisesClicked = this.exercisesClicked;
      return result;
    }

    @NonNull
    public Builder setExercisesClicked(@NonNull String exercisesClicked) {
      if (exercisesClicked == null) {
        throw new IllegalArgumentException("Argument \"exercisesClicked\" is marked as non-null but was passed a null value.");
      }
      this.exercisesClicked = exercisesClicked;
      return this;
    }

    @NonNull
    public String getExercisesClicked() {
      return exercisesClicked;
    }
  }
}
