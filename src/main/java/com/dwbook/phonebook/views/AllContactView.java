package com.dwbook.phonebook.views;

import java.util.List;

import com.dwbook.phonebook.representations.Contact;
import com.dwbook.phonebook.representations.ContactList;

import io.dropwizard.views.View;

public class AllContactView extends View {
	
	ContactList contacts;
	
	private String test="hello";
	
	public AllContactView(ContactList contacts) {
		super("/views/allContact.mustache");
		this.contacts = contacts;
	}
	
	public ContactList getContacts() {
		return contacts;
	}
	
	public String getTest() {
		return test;
	}
}

