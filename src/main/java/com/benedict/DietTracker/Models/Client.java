package com.benedict.DietTracker.Models;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.LocalDate;

public class Client {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty payeeAddress;
    private  final ObjectProperty<LocalDate> dateCreated;

    public Client(String firstName, String lastName, String pAddress, LocalDate date){
        this.firstName = new SimpleStringProperty(this, "First Name", firstName);
        this.lastName = new SimpleStringProperty(this, "Last Name", lastName);
        this.payeeAddress = new SimpleStringProperty(this, "Payee Address", pAddress);
        this.dateCreated = new SimpleObjectProperty<>(this, "Date", date);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    public StringProperty payeeAddressProperty() {
        return payeeAddress;
    }

    public ObjectProperty<LocalDate> dateCreatedProperty() {
        return dateCreated;
    }

}
