package com.example.desmosclonejavafirst;

public class CredentialAttribute {
    private String credentialName;
    private String value;

    public CredentialAttribute(String credentialName, String value){
        this.credentialName = credentialName;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCredentialName() {
        return credentialName;
    }

    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName;
    }
}
