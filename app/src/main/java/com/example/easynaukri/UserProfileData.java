package com.example.easynaukri;

public class UserProfileData {
    public String Username;
    public String Email;
    public String Password;
    public String Number;
    public String FullNameData;
    public String FatherNameData;
    public String MotherNameData;
    public String DobData;
    public String AddressData;
    public String StateData;
    public String CityData;
    public String PinCodeData;
    public String GenderData;
    public String SessionData;

    public UserProfileData(){

    }
    public UserProfileData(String username, String email, String password, String number,String session) {
        Username = username;
        Email = email;
        Password = password;
        Number = number;
        SessionData=session;
    }

    public UserProfileData(String fullNameData, String fatherNameData, String motherNameData, String dobData, String addressData, String stateData, String cityData, String pinCodeData, String genderData) {
        FullNameData = fullNameData;
        FatherNameData = fatherNameData;
        MotherNameData = motherNameData;
        DobData = dobData;
        AddressData = addressData;
        StateData = stateData;
        CityData = cityData;
        PinCodeData = pinCodeData;
        GenderData = genderData;
    }
}
