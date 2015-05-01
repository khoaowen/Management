package com.khoaowen.main.model;

import java.time.LocalDate;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public class Person {

	private StringProperty imagePath;
	private StringProperty firstName;
	private StringProperty lastName;
	private ObjectProperty<Sexe> sexe;
	private StringProperty religiousName;
	private ObjectProperty<LocalDate> birthday;
	private StringProperty ethnicGroup;
	private StringProperty nationality;
	private StringProperty hometown;
	private StringProperty placeOfResidence;
	/**
	 * Identity number
	 */
	private StringProperty idNumber;
	/**
	 * Date of issue of Id card
	 */
	private ObjectProperty<LocalDate> idNumberIssueDate;
	/**
	 * Place of issue of Id card
	 */
	private StringProperty idNumberIssuePlace;
	private StringProperty phoneNumber;
	/**
	 * Full name of father
	 */
	private StringProperty fatherFullName;
	/**
	 * Full name of mother
	 */
	private StringProperty motherFullName;
	/**
	 * the level of study
	 */
	private StringProperty studyLevel;
	/**
	 * the level of foreign language
	 */
	private StringProperty languageLevel;
	private StringProperty note;

	
	public StringProperty imagePathProperty() {
		return imagePath;
	}
	
	public StringProperty firstNameProperty() {
		return firstName;
	}
	
	public StringProperty lastNameProperty() {
		return lastName;
	}
	
	public ObjectProperty<Sexe> sexeProperty() {
		return sexe;
	}

	public StringProperty religousNameProperty() {
		return religiousName;
	}
	
	public ObjectProperty<LocalDate> birthdayProperty() {
		return birthday;
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
	
	public String getFirstName() {
		return firstName.get();
	}

	public String getLastName() {
		return lastName.get();
	}

	public Sexe getSexe() {
		return sexe.get();
	}

	public String getReligiousName() {
		return religiousName.get();
	}

	public LocalDate getBirthday() {
		return birthday.get();
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
	
	public String getImagePath() {
		return imagePath.get();
	}
	
	
	public void setFirstName(String firstName) {
		this.firstName.set(firstName);;
	}

	public void setLastName(String lastName) {
		this.lastName.set(lastName);
	}

	public void setSexe(Sexe sexe) {
		this.sexe.set(sexe);
	}

	public void setReligiousName(String religiousName) {
		this.religiousName.set(religiousName);
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday.set(birthday);
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

	public void setImagePath(String imagePath) {
		this.imagePath.set(imagePath);
	}
}
