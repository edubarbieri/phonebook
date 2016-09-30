package br.com.edubarbieri.app.bean;

public enum PersonType {
	FISICA("Física"), JURIDICA("Jurídica");

	public final String label;

	PersonType(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
}
