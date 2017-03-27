package ist.meic.pa.translators;

import javassist.*;

import ist.meic.pa.annotations.KeywordArgs;
import java.util.List;
import java.util.ArrayList;

//exceptions
import ist.meic.pa.exceptions.*;

//import Logger
import java.util.logging.Logger;
import java.util.logging.Level;

//Auxiliary class
import ist.meic.pa.Argument;

public class KeywordTranslator implements Translator{

  private static Logger LOGGER=Logger.getLogger("TranslatorLogger");

  public void start(ClassPool pool)throws NotFoundException, CannotCompileException{

  }

  public void onLoad(ClassPool pool, String className)throws NotFoundException, CannotCompileException{
    CtClass ctClass = pool.get(className);
    LOGGER.setLevel(Level.SEVERE);
    printInfo(ctClass);
    keywordConstructors(ctClass);
  }

  private void printInfo(CtClass ctClass){
	   LOGGER.log(Level.INFO, "Compile time class found. Name "+ctClass);
  }

  private void keywordConstructors(CtClass ctClass){
    CtConstructor[] cc = ctClass.getConstructors();
    LOGGER.log(Level.INFO, "Parsing the arguments....\n");
    for(CtConstructor c : cc){
      try{
        LOGGER.log(Level.INFO, "Parsing class: "+c.getName());
        List<Argument> args=parseKeys(getKeywordAnnotations(c).value());
        LOGGER.log(Level.INFO, "Applying all the found arguments for  "+c.getName());
		applyKeyArgs(args,c,ctClass);

      }catch(RuntimeException e){
        LOGGER.log(Level.INFO,"Cant find keywords class!!!");
      }
    }

  }

  private void applyKeyArgs(List<Argument> args,CtConstructor c,CtClass ctClass){
	String name=ctClass.getName();
  LOGGER.log(Level.INFO,"Args be: "+args);
	String template="{ ";
	for(Argument a: args){
		template+="$0."+a.getName();
		if(a.getValue()!=null){
			template+="="+a.getValue();
		}
		template+=";";
	}
	template+=
	"for(int i=0;i<$1.length;i+=2){"+ 
	"java.lang.reflect.Field f=null;"+
		"Class current=this.getClass();"+
		"while(current!=null && f==null){"+
			"try{"+
				"f=current.getDeclaredField((String)$1[i]);"+
			"}catch(NullPointerException e3){"+
				"throw new RuntimeException(\"Unrecognized keyword: \"+$1[i]);"+
			"}catch(NoSuchFieldException e2){"+
				"current=current.getSuperclass();"+
				"continue;"+
			"}"+
			"f.setAccessible(true);"+
			"f.set($0,$1[i+1]);"+
			"f.setAccessible(false);"+
		"}"+"if(current==null)throw new RuntimeException(\"Unrecognized keyword: \"+$1[i]);"+
	"}";
	template+="}";

	
	try{
		c.insertBeforeBody(template);
	}catch(CannotCompileException e){
		LOGGER.log(Level.INFO,"HMmm it did not compile....Retry I guess....");
		e.printStackTrace();
		throw new RuntimeException(e);
	}
  }

  //Dummy implementation of a parse. Some1 change this if possible... ty <3 you
  private List<Argument> parseKeys(String keys){
    if(keys.trim().equals("")){return new ArrayList<Argument>();}
    String[] allArgs=keys.split(",");
    ArrayList<Argument> args=new ArrayList<Argument>();
    for(String s : allArgs){
      LOGGER.log(Level.INFO, "Found mr "+s);
      if(s.contains("=")){
        args.add(new Argument(s.split("=")[0], s.split("=")[1]));
      }else{
        args.add(new Argument(s, null));
      }
    }
  return args;
  }

  private KeywordArgs getKeywordAnnotations(CtBehavior ctBehavior){
	try{
		return (KeywordArgs)ctBehavior.getAnnotation(KeywordArgs.class);
	}catch(ClassNotFoundException e){throw new RuntimeException(e);}
  }


}
