package com.khoaowen.main.model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Person {

	private int id;
	private final ObjectProperty<byte[]> image = new SimpleObjectProperty<>();
	private final StringProperty firstName = new SimpleStringProperty();
	private final StringProperty lastName = new SimpleStringProperty();
	private final ObjectProperty<Sex> sex = new SimpleObjectProperty<>();
	private final ObjectProperty<Role> role = new SimpleObjectProperty<>();
	private final StringProperty email = new SimpleStringProperty();
	private final StringProperty religiousName = new SimpleStringProperty();
	private final ObjectProperty<LocalDate> birthday = new SimpleObjectProperty<>();
	private final StringProperty placeOfBirth = new SimpleStringProperty();
	/**
	 * The ceremony date which a person takes refuge in the Buddha, in the dharma and in
	 * the sangha
	 */
	private final ObjectProperty<LocalDate> religiousDate = new SimpleObjectProperty<>();
	/**
	 * The date that the lay brother is adopted by Buddhist temple
	 */
	private final ObjectProperty<LocalDate> adoptedDate = new SimpleObjectProperty<>();
	private final StringProperty ethnicGroup = new SimpleStringProperty();
	private final StringProperty nationality = new SimpleStringProperty();
	private final StringProperty hometown = new SimpleStringProperty();
	private final StringProperty placeOfResidence = new SimpleStringProperty();
	private final StringProperty placeOfTempResidence = new SimpleStringProperty();
	/**
	 * Identity number
	 */
	private final StringProperty idNumber = new SimpleStringProperty();
	/**
	 * Date of issue of Id card
	 */
	private final ObjectProperty<LocalDate> idNumberIssueDate = new SimpleObjectProperty<>();
	/**
	 * Place of issue of Id card
	 */
	private final StringProperty idNumberIssuePlace = new SimpleStringProperty();
	private final StringProperty phoneNumber = new SimpleStringProperty();
	/**
	 * Full name of father
	 */
	private final StringProperty fatherFullName = new SimpleStringProperty();
	/**
	 * Full name of mother
	 */
	private final StringProperty motherFullName = new SimpleStringProperty();
	/**
	 * the level of study
	 */
	private final StringProperty studyLevel = new SimpleStringProperty();
	/**
	 * the level of foreign language
	 */
	private final StringProperty languageLevel = new SimpleStringProperty();
	private final StringProperty note = new SimpleStringProperty();

	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public ObjectProperty<byte[]> imageProperty() {
		return image;
	}

	public StringProperty firstNameProperty() {
		return firstName;
	}

	public StringProperty emailProperty() {
		return email;
	}

	public StringProperty lastNameProperty() {
		return lastName;
	}

	public ObjectProperty<Sex> sexProperty() {
		return sex;
	}
	
	public ObjectProperty<Role> roleProperty() {
		return role;
	}

	public StringProperty religousNameProperty() {
		return religiousName;
	}

	public ObjectProperty<LocalDate> birthdayProperty() {
		return birthday;
	}
	
	public StringProperty placeOfBirthd() {
		return placeOfBirth;
	}
 
	public StringProperty ethnicGroupProperty() {
		return ethnicGroup;
	}

	public StringProperty nationalityProperty() {
		return nationality;
	}

	public StringProperty hometownProperty() {
		return hometown;
	}

	public StringProperty placeOfResidenceProperty() {
		return placeOfResidence;
	}
	
	public StringProperty placeOfTempResidenceProperty() {
		return placeOfTempResidence;
	}

	public StringProperty idNumberProperty() {
		return idNumber;
	}

	public ObjectProperty<LocalDate> idNumberIssueDateProperty() {
		return idNumberIssueDate;
	}

	public StringProperty idNumberIssuePlaceProperty() {
		return idNumberIssuePlace;
	}

	public StringProperty phoneNumberProperty() {
		return phoneNumber;
	}

	public StringProperty fatherFullNameProperty() {
		return fatherFullName;
	}

	public StringProperty motherFullNameProperty() {
		return motherFullName;
	}

	public StringProperty studyLevelProperty() {
		return studyLevel;
	}

	public StringProperty languageLevelProperty() {
		return languageLevel;
	}

	public StringProperty noteProperty() {
		return note;
	}
	
	public ObjectProperty<LocalDate> religiousDateProperty() {
		return religiousDate;
	}
	
	public ObjectProperty<LocalDate> adoptedDateProperty() {
		return adoptedDate;
	}

	public String getFirstName() {
		return firstName.get();
	}

	public String getLastName() {
		return lastName.get();
	}

	public String getEmail() {
		return email.get();
	}

	public Sex getSex() {
		return sex.get();
	}
	
	public Role getRole() {
		return role.get();
	}

	public String getReligiousName() {
		return religiousName.get();
	}

	public LocalDate getBirthday() {
		return birthday.get();
	}
	
	public String getPlaceOfBirth() {
		return placeOfBirth.get();
	}

	public String getEthnicGroup() {
		return ethnicGroup.get();
	}

	public String getNationality() {
		return nationality.get();
	}

	public String getHometown() {
		return hometown.get();
	}

	public String getPlaceOfResidence() {
		return placeOfResidence.get();
	}
	
	public String getPlaceOfTempResidence() {
		return placeOfTempResidence.get();
	}

	public String getIdNumber() {
		return idNumber.get();
	}

	public LocalDate getIdNumberIssueDate() {
		return idNumberIssueDate.get();
	}

	public String getIdNumberIssuePlace() {
		return idNumberIssuePlace.get();
	}

	public String getPhoneNumber() {
		return phoneNumber.get();
	}

	public String getFatherFullName() {
		return fatherFullName.get();
	}

	public String getMotherFullName() {
		return motherFullName.get();
	}

	public String getStudyLevel() {
		return studyLevel.get();
	}

	public String getLanguageLevel() {
		return languageLevel.get();
	}

	public String getNote() {
		return note.get();
	}
	
	public byte[] getImage() {
		return image.get();
	}
	
	public LocalDate getReligiousDate() {
		return religiousDate.get();
	}

	public LocalDate getAdoptedDate() {
		return adoptedDate.get();
	}
	
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public void setSex(Sex sexe) {
		this.sex.set(sexe);
	}

	public void setRole(Role role) {
		this.role.set(role);
	}
	
	public void setReligiousName(String religiousName) {
		this.religiousName.set(religiousName);
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday.set(birthday);
	}
	
	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth.set(placeOfBirth);
	}

	public void setEthnicGroup(String ethnicGroup) {
		this.ethnicGroup.set(ethnicGroup);
	}

	public void setNationality(String nationality) {
		this.nationality.set(nationality);
	}

	public void setHometown(String hometown) {
		this.hometown.set(hometown);
	}

	public void setPlaceOfResidence(String placeOfResidence) {
		this.placeOfResidence.set(placeOfResidence);
	}
	
	public void setPlaceOfTempResidence(String placeOfTempResidence) {
		this.placeOfTempResidence.set(placeOfTempResidence);
	}

	public void setIdNumber(String idNumber) {
		this.idNumber.set(idNumber);
	}

	public void setIdNumberIssueDate(LocalDate idNumberIssueDate) {
		this.idNumberIssueDate.set(idNumberIssueDate);
	}

	public void setIdNumberIssuePlace(String idNumberIssuePlace) {
		this.idNumberIssuePlace.set(idNumberIssuePlace);
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber.set(phoneNumber);
	}

	public void setFatherFullName(String fatherFullName) {
		this.fatherFullName.set(fatherFullName);
	}

	public void setMotherFullName(String motherFullName) {
		this.motherFullName.set(motherFullName);
	}

	public void setStudyLevel(String studyLevel) {
		this.studyLevel.set(studyLevel);
	}

	public void setLanguageLevel(String languageLevel) {
		this.languageLevel.set(languageLevel);
	}

	public void setNote(String note) {
		this.note.set(note);
	}

	public void setImage(byte[] imageFile) {
		this.image.set(imageFile);
	}
	
	public void setReligiousDate(LocalDate religiousDate) {
		this.religiousDate.set(religiousDate);
	}
	
	public void setAdoptedDate(LocalDate adoptedDate) {
		this.adoptedDate.set(adoptedDate);
	}
	
	@Override
	public String toString() {
		return "Name " + this.getFirstName() + " " + this.getLastName() + "; email " + this.getEmail();
	}
}
