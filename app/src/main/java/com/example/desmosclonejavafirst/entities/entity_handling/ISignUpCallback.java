package com.example.desmosclonejavafirst.entities.entity_handling;

public interface ISignUpCallback {
    void onSignUpSuccess();

    void onSignUpFailure(String errorMessage);
}
