import org.apache.log4j.lf5.LogLevel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Vaerys on 24/05/2016.
 */
public class LoggerSetup{
    private static boolean isInit = false;

    public LoggerSetup(){

    }

    private static void initLogging() {
        isInit = true;

        Logger degugLogger = LoggerFactory.getLogger("DegugLogger");

    }

    private static void log(LogLevel level, String debugMsg) {
        if(!isInit){
            initLogging();
        }

    }

    public static void debug(String debugMsg){
        log(LogLevel.DEBUG, debugMsg);
    }

}
