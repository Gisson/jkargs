package pt.ist.ap.a8.Tests;

import pt.ist.ap.a8.annotations.KeywordArgs;

public class Unknown {
	@KeywordArgs("")
	public Unknown(Object... args) {}

	public String toString() {
		return String.format("Nothing...");
	}
}
