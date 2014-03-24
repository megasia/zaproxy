/*
 * Zed Attack Proxy (ZAP) and its related class files.
 * 
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 * 
 * Copyright 2012 The ZAP Development team
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package org.zaproxy.zap.extension.anticsrf;

import org.parosproxy.paros.model.HistoryReference;
import org.parosproxy.paros.network.HttpMessage;
import org.parosproxy.paros.network.HttpRequestHeader;
import org.zaproxy.zap.extension.api.API;
import org.zaproxy.zap.utils.DesktopUtils;
import org.zaproxy.zap.view.PopupMenuHistoryReference;

public class PopupMenuGenerateForm extends PopupMenuHistoryReference {

	private static final long serialVersionUID = 1L;
	
    /**
     * @param label
     */
    public PopupMenuGenerateForm(String label) {
    	super(label);
    }

	@Override
	public boolean isEnableForInvoker(Invoker invoker) {
		return true;
	}

	@Override
	public void performAction(HistoryReference href) throws Exception {
		// Redirect to the form generated by the API
		DesktopUtils.openUrlInBrowser(AntiCsrfAPI.ANTI_CSRF_FORM_URL + href.getHistoryId());
	}

	@Override
    public boolean isEnabledForHistoryReference (HistoryReference href) {
		if (API.getInstance().isEnabled() && DesktopUtils.canOpenUrlInBrowser()) {
			try {
				HttpMessage msg = href.getHttpMessage();
				if (msg.getRequestHeader().getMethod().equals(HttpRequestHeader.POST) &&
						msg.getRequestBody().length() > 0) {
					return true;
				}
			} catch (Exception e) {
				// Ignore - this is 'just' for a right click menu
			}
		}
		return false;
    }

}
