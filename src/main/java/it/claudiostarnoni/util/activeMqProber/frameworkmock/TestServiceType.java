package it.claudiostarnoni.util.activeMqProber.frameworkmock;

import intentfactory.core.seda.support.RemoteService;
import intentfactory.core.topology.TopologyType;

public class TestServiceType implements TopologyType<RemoteService> {

    public static final String TEST_ACTION_SERVICE = "testActionService";

    @Override
    public String getName() {
        return TEST_ACTION_SERVICE;
    }

    @Override
    public Class<RemoteService> getDiscoverableClass() {
        return RemoteService.class;
    }
}