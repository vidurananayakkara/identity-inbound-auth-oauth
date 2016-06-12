/*
 * Copyright (c) 2016, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * you may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.wso2.carbon.identity.oidc.dcr.internal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.osgi.service.component.ComponentContext;
import org.wso2.carbon.identity.application.authentication.framework.inbound.HttpIdentityRequestFactory;
import org.wso2.carbon.identity.application.authentication.framework.inbound.HttpIdentityResponseFactory;
import org.wso2.carbon.identity.application.authentication.framework.inbound.IdentityProcessor;
import org.wso2.carbon.identity.application.mgt.ApplicationManagementService;
import org.wso2.carbon.identity.oidc.dcr.factory.HttpOIDCRegistrationResponseFactory;
import org.wso2.carbon.identity.oidc.dcr.factory.OIDCRegistrationRequestFactory;
import org.wso2.carbon.identity.oidc.dcr.processor.OIDCDCRProcessor;

/**
 * @scr.component name="identity.oidc.dcr" immediate="true"
 * @scr.reference name="identity.application.management.service"
 * interface="org.wso2.carbon.identity.application.mgt.ApplicationManagementService"
 * cardinality="1..1" policy="dynamic"
 * bind="setApplicationManagementService" unbind="unsetApplicationManagementService"
 */

public class OIDCDCRServiceComponent {

    private static final Log log = LogFactory.getLog(OIDCDCRServiceComponent.class);

    @SuppressWarnings("unused")
    protected void activate(ComponentContext componentContext) {
        try {
            System.out.print("DDD");

            componentContext.getBundleContext().registerService(HttpIdentityRequestFactory.class.getName(),
                                                                new OIDCRegistrationRequestFactory(), null);
            componentContext.getBundleContext().registerService(IdentityProcessor.class.getName(),
                                                                new OIDCDCRProcessor(), null);
            componentContext.getBundleContext().registerService(HttpIdentityResponseFactory.class.getName(),
                                                                new HttpOIDCRegistrationResponseFactory(), null);


        } catch (Exception ee) {
            ee.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    protected void deactivate(ComponentContext componentContext) {
        if (log.isDebugEnabled()) {
            log.debug("Stopping OIDCDCRServiceComponent");
        }
    }


    /**
     * Sets ApplicationManagement Service.
     *
     * @param applicationManagementService An instance of ApplicationManagementService
     */
    protected void setApplicationManagementService(ApplicationManagementService applicationManagementService) {
        if (log.isDebugEnabled()) {
            log.debug("Setting ApplicationManagement Service");
        }
        OIDCDCRDataHolder.getInstance().
                setApplicationManagementService(applicationManagementService);
    }

    /**
     * Unsets ApplicationManagement Service.
     *
     * @param applicationManagementService An instance of ApplicationManagementService
     */
    protected void unsetApplicationManagementService(ApplicationManagementService applicationManagementService) {
        if (log.isDebugEnabled()) {
            log.debug("Unsetting ApplicationManagement.");
        }
        OIDCDCRDataHolder.getInstance().setApplicationManagementService(null);
    }


}