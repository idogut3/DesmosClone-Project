package com.example.desmosclonejavafirst.validations.text_validations;

import android.widget.EditText;

/**
 * CredentialAttribute represents a credential attribute associated with an EditText.
 * It encapsulates the credential name, its value, and the EditText widget.
 */
public class CredentialAttribute {
    private String credentialName;
    private String value;

    private EditText editText;

    /**
     * Constructs a CredentialAttribute with the specified credential name and EditText.
     *
     * @param credentialName The name or identifier of the credential attribute.
     * @param editText       The EditText widget associated with the credential attribute.
     */
    public CredentialAttribute(String credentialName, EditText editText) {
        this.credentialName = credentialName;
        this.editText = editText;
        this.value = editText.getText().toString().trim();
    }

    /**
     * Gets the current value of the credential attribute.
     *
     * @return The current value of the credential attribute.
     */
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
