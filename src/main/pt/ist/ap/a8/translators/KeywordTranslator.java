package pt.ist.ap.a8.translators;

import javassist.*;

import pt.ist.ap.a8.annotations.KeywordArgs;
import java.util.List;
import java.util.ArrayList;

//exceptions
import pt.ist.ap.a8.exceptions.*;

//import Logger
import java.util.logging.Logger;
import java.util.logging.Level;

//Auxiliary class
import pt.ist.ap.a8.Argument;

public class KeywordTranslator implements Translator{

  private static Logger LOGGER=Logger.getLogger("TranslatorLogger");

  public void start(ClassPool pool)throws NotFoundException, CannotCompileException{

  }

  public void onLoad(ClassPool pool, String className)throws NotFoundException, CannotCompileException{
    CtClass ctClass = pool.get(className);
    LOGGER.setLevel(Level.INFO);
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
		applyKeyArgs(args,c);

      }catch(RuntimeException e){
        LOGGER.log(Level.SEVERE,"Cant find keywords class!!!");
      }
    }

  }

  private void applyKeyArgs(List<Argument> args,CtConstructor c){

  }

  //Dummy implementation of a parse. Some1 change this if possible... ty <3 you
  private List<Argument> parseKeys(String keys){
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
	}catch(ClassNotFoundException e){throw new RuntimeException();}
  }


}
