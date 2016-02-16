package com.pointwise.manager;

/**
 * Created by wbatista on 2/15/16.
 * This class is just for throwing an exception in order to send data back to the queue
 */
public class SimulateUnreliableNetworkManager {
    public void unreliableNetwork() throws Exception {
        throw new Exception("Unreliable network, your data will be delivered as soon as possible");
    }
}