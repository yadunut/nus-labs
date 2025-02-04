/**
 * This class implements something that
 * probably is just a value but may be nothing.
 * We will never return null in this class, but
 * we may return something that contains nothing
 * where the nothing is a null.
 *
 * @author Yadunand Prem (10B)
 * @version CS2030S AY22/23 Semester 1
 */
class Probably<T> implements Actionable<T>, Immutatorable<T>, Applicable<T> {
  private final T value;

  private static final Probably<?> NONE = new Probably<>(null);

  /**
   * Private constructor, can only be invoked inside.
   * This is called a factory method. We can only
   * create this using the two public static method.
   *
   * @param value T
   */
  private Probably(T value) {
    this.value = value;
  }

  /**
   * It is probably nothing, no value inside.
   *
   * @param <T> type T
   * @return The shared NOTHING.
   */
  public static <T> Probably<T> none() {
    @SuppressWarnings("unchecked")
    Probably<T> res = (Probably<T>) NONE;
    return res;
  }

  /**
   * It is probably just the given value.
   * Unless the value is null, then nothing is
   * given again.
   *
   * @param <T>   type T
   *
   * @param value Probably this is the value
   *              unless it is null then we say
   *              that there is no
   * @return The given value or nothing but
   *         never null.
   */
  public static <T> Probably<T> just(T value) {
    if (value == null) {
      return none();
    }
    return new Probably<>(value);
  }

  /**
   * Check for equality between something that
   * is probably a value but maybe nothing.
   *
   * @param obj The other value to be compared.
   * @return True if the two values are equal,
   *         false otherwise.
   */
  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj instanceof Probably<?>) {
      Probably<?> some = (Probably<?>) obj;
      if (this.value == some.value) {
        return true;
      }
      if (this.value == null || some.value == null) {
        return false;
      }
      return this.value.equals(some.value);
    }
    return false;
  }

  /**
   * String representation of something that
   * is probably a value but maybe nothing.
   *
   * @return The string representation.
   */
  @Override
  public String toString() {
    if (this.value == null) {
      return "<>";
    } else {
      return "<" + this.value.toString() + ">";
    }
  }

  @Override
  public void act(Action<? super T> action) {
    if (this.value == null) {
      return;
    }
    action.call(this.value);
  }

  public Probably<T> check(Immutator<Boolean, ? super T> eq) {
    if (this.value == null) {
      return none();
    }
    if (eq.invoke(this.value)) {
      return this;
    }
    return none();
  }

  @Override
  public <R> Probably<R> transform(Immutator<? extends R, ? super T> immutator) {
    if (this.value == null) {
      return none();
    }
    return just(immutator.invoke(this.value));
  }

  @Override
  public <R> Probably<R> apply(Probably<? extends Immutator<? extends R, ? super T>> probablyImmutator) {
    if (probablyImmutator.value != null) {
      return this.transform(probablyImmutator.value);
    }
    return none();
  }

}
