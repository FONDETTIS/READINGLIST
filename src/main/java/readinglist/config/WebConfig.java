package readinglist.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.springframework.core.io.Resource;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.validation.MessageCodesResolver;
import org.springframework.validation.Validator;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;



//import mycompany.common.processor.BLCDialect;
//import mycompany.common.processor.bundle.BroadleafResourceHttpRequestHandler;
//import mycompany.dataPG.ReadingListRepository;

//import nz.net.ultraq.thymeleaf.LayoutDialect;

import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;

@Configuration
//@EnableWebMvc
//@WebAppConfiguration
//@ImportResource("classpath:processor-config.xml")
//@ComponentScan("mycompany.controllers")
@ComponentScan({"readinglist.controller","readinglist.utils"})
//@ComponentScan("mycompany.common.processor")
//public class WebConfig	implements WebMvcConfigurer {
//@Import(MessagingMailConfig.class)
//public class WebConfig	extends ConfigurationSupport {
@ImportResource({"classpath*:applicationContext.xml"})
public class WebConfig	{
	
		 
	 	String basename1 = "classpath:/messages/GeneratedMessagesEntityCommon";
		String basename2 = "classpath:/messages/GeneratedMessagesEntityFramework";
		String basename3 = "classpath:/messages/GeneratedMessagesEntityOpenAdmin";
		String basename4 = "classpath:/messages/GeneratedMessagesEntityProfile";
		String basename5 = "classpath:/messages/OpenAdminMessages";
		String basename6 = "classpath:/messages/messages";
			
		String basename7  = "classpath:/messages/CustomerCareMessages";
		String basename8  = "classpath:/messages/MerchandisingMessages";
		String basename9  = "classpath:/messages/PromotionMessages";
		String basename10 = "classpath:/messages/RuleMessages";
		String basename11 = "classpath:/messages/AbstractModuleMessages";
		String basename12 = "classpath:/messages/UtilityMessages";
		
		String basename13 = "classpath:/messages/ContentManagementMessages";
		String basename14 = "classpath:/messages/GeneratedMessagesEntityCMS";
		String basename15 = "classpath:/messages/SandBoxMessages";
		String basename16 = "classpath:/messages/common";
		
			
		String[] basenames = {basename1,basename2,basename3,basename4,basename5,basename6,
				basename7,basename8,basename9,basename10,basename11,basename12,
				basename13,basename14,basename15,basename16};
	
	 
	 @Bean
		public ReloadableResourceBundleMessageSource messageSource() {
		    ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		    //messageSource.setBasename("classpath:runtime-properties/messages");
		    
		    messageSource.setBasenames(basenames);
		    
		    //messageSource.setCacheSeconds(10); //reload messages every 10 seconds
		    return messageSource;
		}
	
	

	
//	  @Autowired
//	  private BLCDialect blDialect;
	
	  @Autowired
	  private TemplateEngine templateEngine;
	  
	  
//	  @Bean(name="blJsRequestHandler")
//	  public BroadleafResourceHttpRequestHandler blJsRequestHandler()
//	  {
//	  BroadleafResourceHttpRequestHandler blJsRequestHandler = new BroadleafResourceHttpRequestHandler();
//	  return blJsRequestHandler;
//	  }

//	  @Bean(name="simpleHandlerMapping")
//	  public SimpleUrlHandlerMapping simpleHandlerMapping()
//	  {
//	  SimpleUrlHandlerMapping simpleHandlerMapping = new SimpleUrlHandlerMapping();
//	  return simpleHandlerMapping;
//	  }
	  
//	  <bean class="org.springframework.web.servlet.handler.SimpleUrlHandlerMapping">
//      <property name="order" value="-10" />
//      <property name="mappings">
//          <props>
//              <prop key="/js/**">blJsResources</prop>
//              <prop key="/css/**">blCssResources</prop>
//          </props>
//      </property>
//      </bean>
      
	  @Bean 
	  public ViewResolver viewResolver(SpringTemplateEngine templateEngine) 
	  {
	  ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
	  viewResolver.setTemplateEngine(templateEngine); 
	  return viewResolver; 
	  }
	  
	  
	  
	  
//	  @Bean
//	  public SpringTemplateEngine templateEngine(TemplateResolver templateResolver) 
//	  { 
//	  SpringTemplateEngine templateEngine = new  SpringTemplateEngine();
//	  templateEngine.setTemplateResolver(templateResolver);
//	  templateEngine.addDialect(blDialect);
//	  return templateEngine; 
//	  }
 
	  
	  /*
	  @Bean public TemplateResolver templateResolver() 
	  { 
	  TemplateResolver
	  templateResolver = new ServletContextTemplateResolver();
	  //templateResolver.setPrefix("/templates/fondettis/");
	  //templateResolver.setSuffix(".html");
	  //templateResolver.setTemplateMode("HTML5"); 
	  return templateResolver; 
	  }
	  */
	  
	  
	  
	  
	  /*
	  @Bean
	  public MultipartResolver multipartResolver() throws IOException 
	  {
	    return new StandardServletMultipartResolver();
	  }
	  */
	  
	
      

 
}
