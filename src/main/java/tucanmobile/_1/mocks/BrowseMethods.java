package tucanmobile._1.mocks;

import tucanmobile._1.requirements.AnswerObject;
import tucanmobile._1.requirements.RequestObject;

/**
 * Mock for com.dalthed.tucan.Connection.BrowseMethods - stub implementation for testing.
 */
public class BrowseMethods {
    
    public AnswerObject browse(RequestObject requestInfo) {
        // Return a dummy AnswerObject for testing
        return new AnswerObject("", "", null, null);
    }
}
