package com.winhong.plugins.cicd.cas;

import java.util.EnumSet;
import java.util.Properties;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

import com.winhong.plugins.cicd.filter.UAPLoginFilter;
import com.winhong.plugins.cicd.uapconfig.SessionUtils;
import com.winhong.uap.cas.client.authentication.AuthenticationFilter;
import com.winhong.uap.cas.client.session.SingleSignOutFilter;
import com.winhong.uap.cas.client.util.AssertionThreadLocalFilter;
import com.winhong.uap.cas.client.util.HttpServletRequestWrapperFilter;
import com.winhong.uap.cas.client.validation.Cas30ProxyReceivingTicketValidationFilter;

/**
 * 
 * @author li
 * 
 */
public class RegisterUAPFilter {
	private Properties pro = SessionUtils.getFilterProp();
	private ServletContextHandler context;
	public RegisterUAPFilter(ServletContextHandler context){
		this.context = context;
	}
	
	//register filter bus
	public void registerFilterBus(){
		registerAuthenticationFilter();
		registerSingleSignOutFilter();
		registerCas30ProxyReceivingTicketValidationFilter();
		registerHttpServletRequestWrapperFilter();
		registerAssertionThreadLocalFilter();
		registerUAPFilter();
		
	}
	
	//AuthenFilter
	private void registerAuthenticationFilter(){
		FilterHolder authFilter = new FilterHolder(AuthenticationFilter.class);
		authFilter.setInitParameter("casServerLoginUrl",pro.getProperty("casServerLoginUrl"));
		authFilter.setInitParameter("serverName", pro.getProperty("serverName"));
		authFilter.setInitParameter("ignorePattern", pro.getProperty("ignorePattern"));
		context.addFilter(authFilter, "/*", EnumSet.of(DispatcherType.REQUEST));
	}
	//SingleSignOutFilter
	private void registerSingleSignOutFilter() {
        FilterHolder ssoFilter = new FilterHolder(SingleSignOutFilter.class);
        ssoFilter.setInitParameter("casServerUrlPrefix", pro.getProperty("casServerUrlPrefix"));
        ///ssoFilter.setInitParameter("casServerUrlPrefix", "https://10.10.111.151/uap");
        context.addFilter(ssoFilter, "/*", EnumSet.of(DispatcherType.REQUEST));
	}
	//Cas30ProxyReceivingTicketValidationFilter
	private void registerCas30ProxyReceivingTicketValidationFilter() {
		FilterHolder cas30TicketValidationFilter = new FilterHolder(Cas30ProxyReceivingTicketValidationFilter.class);
		cas30TicketValidationFilter.setInitParameter("casServerUrlPrefix", pro.getProperty("casServerUrlPrefix"));
		///cas30TicketValidationFilter.setInitParameter("casServerUrlPrefix", "https://10.10.111.151/uap");
		cas30TicketValidationFilter.setInitParameter("serverName", pro.getProperty("serverName"));
		context.addFilter(cas30TicketValidationFilter, "/*", EnumSet.of(DispatcherType.REQUEST));
	}
	//HttpServletRequestWrapperFilter
	private void registerHttpServletRequestWrapperFilter() {
		FilterHolder httpServletRequestWrapperFilter = new FilterHolder(HttpServletRequestWrapperFilter.class);
		context.addFilter(httpServletRequestWrapperFilter, "/*", EnumSet.of(DispatcherType.REQUEST));
	}
	//AssertionThreadLocalFilter
	private void registerAssertionThreadLocalFilter() {
		FilterHolder assertionThreadLocalFilter = new FilterHolder(AssertionThreadLocalFilter.class);
		context.addFilter(assertionThreadLocalFilter, "/*", EnumSet.of(DispatcherType.REQUEST));
	}
	//UAPFilter
	private void registerUAPFilter() {
        FilterHolder uapFilter = new FilterHolder(UAPLoginFilter.class);
        context.addFilter(uapFilter, "/*", EnumSet.of(DispatcherType.REQUEST));
	}

}
