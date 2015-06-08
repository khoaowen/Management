package com.khoaowen.main.view;

import java.util.List;
import java.util.function.Predicate;

import com.khoaowen.main.model.Person;
import com.khoaowen.utils.JfxUtils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.scene.control.TextField;

/** 
 * A listener which helps filter the list of Person in function of text input
 */
public class SearchTFListener implements ChangeListener<String> {
	
	private FilteredList<Person> filteredData;
	private TextField searchListTextField;
	private Predicate<Person> externalPred;
	
	public SearchTFListener(TextField searchTextField, List<Person> people) {
		this.filteredData = new FilteredList<Person>(FXCollections.observableArrayList(people), p -> true);
		this.searchListTextField = searchTextField;
	}

	@Override
	public void changed(ObservableValue<? extends String> observable,
			String oldValue, String newValue) {

		Predicate<Person> pred = person->{
			// if text is empty, show all
    		if (newValue == null || newValue.isEmpty()) {
    			searchListTextField.getStyleClass().remove("redShadow");
    			return true;
    		}
    		if (!searchListTextField.getStyleClass().contains("redShadow")) {
    			searchListTextField.getStyleClass().add("redShadow");
    		}
    		// Compare first name and last name of every person with filter text.
            String userFilter =  JfxUtils.normalizeStringToCompare(newValue);
            String firstName = JfxUtils.normalizeStringToCompare(person.getFirstName());
            if (firstName.contains(userFilter)) {
                return true; // Filter matches first name.
            }
            String lastName = JfxUtils.normalizeStringToCompare(person.getLastName());
			if (lastName.contains(userFilter)) {
                return true; // Filter matches last name.
            }
			String fullName = lastName + " " + firstName;
			if (fullName.contains(userFilter)) {
				return true;
			}
			String fullNameInverted = firstName + " " + lastName;
			if (fullNameInverted.contains(userFilter)) {
				return true;
			}
            return false; // Does not match.
		};
		if (externalPred != null) {
			pred = pred.and(externalPred);
		}
		filteredData.setPredicate(pred);
		
	}

	public FilteredList<Person> getFilteredData() {
		return filteredData;
	}

	/**
	 * set the predicator to combine with the text search predication
	 * @param externalPred
	 */
	public void setExternalPred(Predicate<Person> externalPred) {
		this.externalPred = externalPred;
	}

}
