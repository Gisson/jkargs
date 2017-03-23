import ist.meic.pa.annotations.KeywordArgs;

public class MixKeys {
	long l;
	byte b;
	boolean v;

	@KeywordArgs("l=10,b,v=true")
	public MixKeys(Object... args) {}

	public String toString() {
		return String.format("l: %s, b: %s, v :%s",
				l, b, v);
	}
}
