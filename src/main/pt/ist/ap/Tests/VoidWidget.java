package pt.ist.ap.a8.Tests;

import pt.ist.ap.a8.annotations.KeywordArgs;

public class VoidWidget {
	int someNumber;
	char someChar;
	double someDouble;

	@KeywordArgs("someNumber,someChar,someDouble")
	public VoidWidget(Object... args) {}

	public String toString() {
		return String.format("someNumber: %s, someChar: %s, someDouble: %s",
				someNumber, someChar, someDouble);
	}
}
