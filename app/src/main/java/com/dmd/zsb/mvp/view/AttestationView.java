package com.dmd.zsb.mvp.view;

import com.dmd.zsb.protocol.response.attestationResponse;

/**
 * Created by Administrator on 2016/6/17.
 */
public interface AttestationView extends BaseView {
    void onAttestation(attestationResponse response);
}
