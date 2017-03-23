package ist.meic.pa;

public class Argument{
	private String _name;
	private Object _value=null;

	public Argument(String name, Object value){_name=name;_value=value;}

	public String getName(){return _name;}
	public Object getValue(){return _value;}

	public String toString(){
		return "Name "+getName()+",Value "+getValue();
	}
}
