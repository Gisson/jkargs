package pt.ist.ap.a8.translators;

import javassist.*;

//import Logger
import java.util.logging.Logger;
import java.util.logging.Level;

public class KeywordTranslator implements Translator{

  private static Logger LOGGER=Logger.getLogger("TranslatorLogger");

  public void start(ClassPool pool)throws NotFoundException, CannotCompileException{

  }

  public void onLoad(ClassPool pool, String className)throws NotFoundException, CannotCompileException{
    CtClass ctClass = pool.get(className);
    LOGGER.setLevel(Level.INFO);
    printInfo(ctClass);
  }

  private void printInfo(CtClass ctClass){
	LOGGER.log(Level.INFO, "Compile time class found. Name "+ctClass);
  }


}
