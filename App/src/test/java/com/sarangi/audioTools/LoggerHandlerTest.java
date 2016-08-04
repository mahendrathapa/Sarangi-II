import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.sarangi.audioTools.*;
import java.util.logging.Level;
/**
 * Unit Functional test LoggerHandhler
 */

public class LoggerHandlerTest extends TestCase{

        public void testLoggerHandler() {

                LoggerHandler lH = new LoggerHandler();

                LoggerHandler.LogType lT = LoggerHandler.LogType.FEATURE_EXTRACTION;

                lH.loggingSystem(lT,Level.INFO,new String("Testing Log"));

        }
}


