package jriecken_gae_java_mini_profiler._39.mocks;

import java.util.ArrayList;
import java.util.List;

/**
 * Mock for com.google.appengine.tools.appstats.StatsProtos
 */
public class StatsProtos {
    
    /**
     * Mock for RequestStatProto
     */
    public static class RequestStatProto {
        
        public long getDurationMilliseconds() {
            return 100L;
        }
        
        public List<AggregateRpcStatsProto> getRpcStatsList() {
            List<AggregateRpcStatsProto> list = new ArrayList<>();
            list.add(new AggregateRpcStatsProto());
            return list;
        }
        
        public List<IndividualRpcStatsProto> getIndividualStatsList() {
            List<IndividualRpcStatsProto> list = new ArrayList<>();
            list.add(new IndividualRpcStatsProto());
            return list;
        }
    }
    
    /**
     * Mock for AggregateRpcStatsProto
     */
    public static class AggregateRpcStatsProto {
        
        public String getServiceCallName() {
            return "datastore_v3.Get";
        }
        
        public int getTotalAmountOfCalls() {
            return 1;
        }
    }
    
    /**
     * Mock for IndividualRpcStatsProto
     */
    public static class IndividualRpcStatsProto {
        
        public String getServiceCallName() {
            return "datastore_v3.Get";
        }
        
        public long getDurationMilliseconds() {
            return 50L;
        }
        
        public long getStartOffsetMilliseconds() {
            return 10L;
        }
        
        public String getRequestDataSummary() {
            return "request summary";
        }
        
        public String getResponseDataSummary() {
            return "response summary";
        }
        
        public List<StackFrameProto> getCallStackList() {
            List<StackFrameProto> list = new ArrayList<>();
            list.add(new StackFrameProto());
            return list;
        }
    }
    
    /**
     * Mock for StackFrameProto
     */
    public static class StackFrameProto {
        
        public String getClassOrFileName() {
            return "TestClass";
        }
        
        public String getFunctionName() {
            return "testMethod";
        }
        
        public int getLineNumber() {
            return 42;
        }
    }
}
