package com.example.desmosclonejavafirst.validations.text_validations;

import android.widget.EditText;

public class CredentialAttribute {
    private String credentialName;
    private String value;

    private EditText editText;

    public CredentialAttribute(String credentialName, EditText editText) {
        this.credentialName = credentialName;
        this.editText = editText;
        this.value = editText.getText().toString().trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.editText.setText(value);
        this.value = value;
    }

    public String getCredentialName() {
        return credentialName;
    }

    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName;
    }

    public EditText getEditText() {
        return editText;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }
}
