/**
 * (c) 2003-2016 MuleSoft, Inc. The software in this package is
 * published under the terms of the CPAL v1.0 license, a copy of which
 * has been included with this distribution in the LICENSE.md file.
 */
package org.mule.modules.cookbook.automation.system;

import com.cookbook.tutorial.service.InvalidTokenException;
import org.junit.Before;
import org.junit.Test;
import org.mule.modules.cookbook.config.OAuthConfig;
import org.mule.tools.devkit.ctf.configuration.util.ConfigurationUtils;

import java.util.Properties;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

public class OAuthConfigTestCases {

    private OAuthConfig config;

    @Before
    public void setUp() throws Exception {
        final Properties props = ConfigurationUtils.getAutomationCredentialsProperties();
        config = new OAuthConfig();
        config.setAddress(props.getProperty("config.address"));
        config.setConsumerKey(props.getProperty("oauth.consumerKey"));
        config.setConsumerSecret(props.getProperty("oauth.consumerSecret"));
        config.setAccessToken(props.getProperty("oauth.accessToken"));
        config.postAuthorize();
    }

    @Test
    public void testValidCredentials() throws Exception {
        config.testConnect();
    }

    @Test
    public void testEmptyAccessToken() throws Exception {
        try{
            config.setAccessToken("");
            config.postAuthorize();
            config.testConnect();
        } catch(RuntimeException e){
            assertThat(e.getCause(), instanceOf(InvalidTokenException.class));
        }

    }
}
