package org.zerock.myapp.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RunWith(SpringRunner.class)
@ContextConfiguration(locations = {
		"file:src/main/webapp/WEB-INF/spring/root-context.xml"
})
public class HikariDataSourceBeanTests {
	
	@Autowired
	HikariDataSource ds;
	
	// XX: 이 타입에 맞는 빈 객체가 오직 1개가 나와야 하는데 2개가 나왔기 때문에 주입이 불가능
	// HikariDataSource extends HikariConfig
	
//	@Autowired
//	HikariConfig conf;
	
	
	@Before
	public void setup() {
		
		log.debug("setup() invoked.");
		
		Objects.requireNonNull(this.ds);
//		Objects.requireNonNull(this.conf);
//		log.info("\t+ ds: {}, conf: {}",ds,conf);
		
	} // setup
	
	@Test
	public void testGetConnection() throws SQLException {
		
		log.debug("testXXX() invoked.");
		
		Connection conn = this.ds.getConnection();
		assert conn!=null;
		log.info("\t+ conn: {}",conn);
		
		conn.close();
		
	} // testGetConnection
	
	@Test
	public void testGetAllBoards() throws SQLException {
		
		log.debug("testGetAllBoards() invoked.");
		
		Connection conn = this.ds.getConnection();
		String sql = "SELECT * FROM tbl_board WHERE bno > ?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, 700);
		
		ResultSet rs = pstmt.executeQuery();
		while(rs.next()) {
			
			Integer bno = rs.getInt("bno");
			String title = rs.getString("title");
			String content = rs.getString("content");

			log.info("1. bno: {}, title: {}, content: {}",bno,title,content);
			// 여러개 넣을 수 있음 -> log4j2의 장점
			
		} // while
		
		rs.close();
		pstmt.close();
		conn.close();
		
		
	} // testGetAllBoards
	
	@After
	public void tearDown() {
		
		log.debug("tearDown() invoked.");
		
	} // tearDown

} // end class

 
// 지현이 크라켄 푸시~~~~~~~~~