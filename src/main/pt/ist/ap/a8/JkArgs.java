package pt.ist.ap.a8;

import java.lang.annotation.*;
import java.lang.reflect.*;
import java.util.HashMap;
import pt.ist.ap.a8.annotations.KeywordArgs;
import java.util.Map;
import java.util.AbstractMap.SimpleEntry;

//import the logger
import java.util.logging.Logger;
import java.util.logging.Level;

public class JkArgs{

  public static HashMap<Method,String> keywordedMeths=new HashMap<Method,String>();
  public static HashMap<SimpleEntry<Class,Type[]>,String> keywordedConsts=
  new HashMap<SimpleEntry<Class,Type[]>,String>();
  private static final Logger LOGGER=Logger.getLogger("JkLogger");

  public static void main(String[] argv){
    LOGGER.setLevel(Level.INFO);
    LOGGER.log(Level.INFO,"So it begins...");

    try{
      for(Constructor<?> c : Class.forName(argv[0]).getConstructors()){
        SimpleEntry<Class,Type[]> entry=new SimpleEntry<Class,Type[]>(c.getDeclaringClass(),c.getGenericParameterTypes());
        keywordedConsts.put(entry,((KeywordArgs)(c.getAnnotation(KeywordArgs.class))).value());

        LOGGER.log(Level.INFO,"Trying constructor "+c.getDeclaringClass());
      }

      for(Method m : Class.forName(argv[0]).getDeclaredMethods()){
        if(m.isAnnotationPresent(KeywordArgs.class)){

          keywordedMeths.put(m,((KeywordArgs)(m.getAnnotation(KeywordArgs.class))).value());
        }
      }
    }catch(Exception e){
      e.printStackTrace();
    }

    //Print all the stuff....for debugging purposes
    for(Map.Entry<Method,String> entry : keywordedMeths.entrySet()){
      LOGGER.log(Level.INFO,"Dumping methods found");
      LOGGER.log(Level.INFO, "Name in hashmap "+entry.getKey()+" And keyword args are \""+
      entry.getValue()+"\"");
    }

    //Print all the stuff....for debugging purposes
    for(Map.Entry<SimpleEntry<Class,Type[]>,String> entry : keywordedConsts.entrySet()){
      LOGGER.log(Level.INFO,"Dumping constructors found");
      LOGGER.log(Level.INFO, "Name in hashmap "+entry.getKey()+" And keyword args are \""+
      entry.getValue()+"\"");
    }
  }

}
