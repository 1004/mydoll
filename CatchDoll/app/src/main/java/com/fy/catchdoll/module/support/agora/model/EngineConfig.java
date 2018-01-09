package com.fy.catchdoll.module.support.agora.model;

public class EngineConfig {
    public int mClientRole;

    public int mVideoProfile;

    public int mUid;

    public String mChannel;

    public int mWawajiUid;

    public void reset() {
        mChannel = null;
        mWawajiUid = 0;
    }

    EngineConfig() {
    }
}
