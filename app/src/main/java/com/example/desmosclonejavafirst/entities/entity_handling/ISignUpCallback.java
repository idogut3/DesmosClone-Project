package com.example.desmosclonejavafirst.entities.entity_handling;


/**
 * Interface to handle the sign-up operation callbacks.
 */
public interface ISignUpCallback {
    /**
     * Called when sign-up is successful.
     */
    void onSignUpSuccess();

    /**
     * Called when sign-up fails.
     *
     * @param errorMessage The error message detailing why the sign-up failed.
     */
    void onSignUpFailure(String errorMessage);
}
