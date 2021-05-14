package config;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import spring.ChangePasswordService;
import spring.MemberDao;
import spring.MemberInfoPrinter;
import spring.MemberListPrinter;
import spring.MemberPrinter;
import spring.MemberRegisterService;
import spring.VersionPrinter;

@Configuration
@EnableTransactionManagement
public class AppCtx {
	
	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		DataSource ds = new DataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		//ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setUrl("jdbc:mysql://localhost:3306/spring5fs?characterEncoding=utf8&useSSL=false");
		//ds.setUrl("jdbc:mysql://localhost:3306/spring5fs?useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=UTC");
		ds.setUsername("spring5");
		ds.setPassword("spring5");
		ds.setInitialSize(2);
		ds.setMaxActive(10);
		ds.setTestWhileIdle(true);
		ds.setMinEvictableIdleTimeMillis(60000*3);
		ds.setTimeBetweenEvictionRunsMillis(10*1000);
		return ds;
	}
	
	@Bean
	public PlatformTransactionManager transactionManager() {
		DataSourceTransactionManager tm = new DataSourceTransactionManager();
		tm.setDataSource(dataSource());
		return tm;
	}
	@Bean
	public VersionPrinter versionPrinter() {
		VersionPrinter versionPrinter = new VersionPrinter();
		versionPrinter.setMajorVersion(5);
		versionPrinter.setMinorVersion(0);
		return versionPrinter;
	}
	 @Bean
	 public MemberDao memberDao() {
		 return new MemberDao(dataSource());
	 }
	 
	 @Bean
	 public MemberPrinter memberPrinter() {
		 return new MemberPrinter();
	 }
	 
	 @Bean
	 public MemberListPrinter listPrinter() {
		 return new MemberListPrinter(memberDao(),memberPrinter());
	 }
	 
	 @Bean
	 public MemberInfoPrinter infoPrinter() {
		 MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
		 infoPrinter.setMemberDao(memberDao());
		 infoPrinter.setPrinter(memberPrinter());
		 return infoPrinter;
	 }
	 @Bean
	 public MemberRegisterService memberRegSvc() {
		 return new MemberRegisterService(memberDao());
	 }
	 
	 @Bean
	 public ChangePasswordService changePwdSvc() {
		 ChangePasswordService pwdSvc = new ChangePasswordService();
		 pwdSvc.setMemberDao(memberDao());
		 return pwdSvc;
	 }
}
