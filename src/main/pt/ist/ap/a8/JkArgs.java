package pt.ist.ap.a8;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.HashMap;
import pt.ist.ap.a8.annotations.KeywordArgs;
import pt.ist.ap.a8.Argument;

import java.util.Map;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.ArrayList;

//import the logger
import java.util.logging.Logger;
import java.util.logging.Level;

//import javassist
import javassist.*;
import javassist.expr.*;

//regex stuff
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import for translator
import pt.ist.ap.a8.translators.KeywordTranslator;

public class JkArgs{


  //public static String PATTERN="([A-z])*(?:(=)?)(\\\"([A-z])*\\\"|([0-9])+)*(?:(,)?)";
  //public static String PATTERN_ATTR_NAME="([A-z])*(?=(=)?)(\"([A-z])*\"";
  public static HashMap<Method,List<Argument>> keywordedMeths=new HashMap<Method,List<Argument>>();
  public static HashMap<SimpleEntry<Class,Type[]>,List<Argument>> keywordedConsts=
  new HashMap<SimpleEntry<Class,Type[]>,List<Argument>>();
  private static final Logger LOGGER=Logger.getLogger("JkLogger");

  public static void main(String[] argv){
    LOGGER.setLevel(Level.INFO);
    LOGGER.log(Level.INFO,"So it begins...");

    try{
      Translator translator = new KeywordTranslator();
      ClassPool pool = ClassPool.getDefault();
      Loader classLoader = new Loader();
      classLoader.addTranslator(pool, translator);
      String[] restArgs=new String[argv.length-1];
      System.arraycopy(argv, 1, restArgs, 0, restArgs.length);
      classLoader.run(argv[0],restArgs);

/*        for(Constructor<?> c : Class.forName(argv[0]).getConstructors()){
        SimpleEntry<Class,Type[]> entry=new SimpleEntry<Class,Type[]>(c.getDeclaringClass(),c.getGenericParameterTypes());
        keywordedConsts.put(entry,parseKeys(((KeywordArgs)(c.getAnnotation(KeywordArgs.class))).value()));

        LOGGER.log(Level.INFO,"Trying constructor "+c.getDeclaringClass());
      }

      for(Method m : Class.forName(argv[0]).getDeclaredMethods()){
        if(m.isAnnotationPresent(KeywordArgs.class)){

          keywordedMeths.put(m,parseKeys(((KeywordArgs)(m.getAnnotation(KeywordArgs.class))).value()));
        }
      }*/
    }catch(Exception e){
		LOGGER.log(Level.SEVERE,"ALLU ACKBAR!!!");
      e.printStackTrace();
    }catch( Throwable t){
		LOGGER.log(Level.SEVERE,"ALLU SNACKBAR!!!");
		t.printStackTrace();
	}

    //Print all the stuff....for debugging purposes
    for(Map.Entry<Method,List<Argument>> entry : keywordedMeths.entrySet()){
      LOGGER.log(Level.INFO,"Dumping methods found");
      LOGGER.log(Level.INFO, "Name in hashmap "+entry.getKey()+" And keyword args are \""+
      entry.getValue()+"\"");
    }

    //Print all the stuff....for debugging purposes
    for(Map.Entry<SimpleEntry<Class,Type[]>,List<Argument>> entry : keywordedConsts.entrySet()){
      LOGGER.log(Level.INFO,"Dumping constructors found");
      LOGGER.log(Level.INFO, "Name in hashmap "+entry.getKey()+" And keyword args are \""+
      entry.getValue()+"\"");
    }
  }

  //public static void applyKeyArgs(CtClass ctClass, CtMethod ctMethod){
//
//  }

  //Dummy implementation of a parse. Some1 change this if possible... ty <3 you
  public static List<Argument> parseKeys(String keys){
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
}
