package bll.validators;

/*
 * Method to be implemented by the validator classes.
 * @param t object to be validated
 * @return 0 in case of valid and -1 otherwise
 */

public interface Validator<T> {
     int validate(T t);
}