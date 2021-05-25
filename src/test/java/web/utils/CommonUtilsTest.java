package web.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import web.util.CommonUtils;

@SpringBootTest
public class CommonUtilsTest {
	@Test
	public void getPreviousPeriod_test_1() {
		Assertions.assertArrayEquals(new int[] { 3, 2020 }, CommonUtils.getPreviousPeriod(4, 2020));
	}

	@Test
	public void getPreviousPeriod_test_2() {
		Assertions.assertArrayEquals(new int[] { 12, 2019 }, CommonUtils.getPreviousPeriod(1, 2020));
	}
	
	@Test
	public void generateSHA1_test() {
		Assertions.assertEquals("6adfb183a4a2c94a2f92dab5ade762a47889a5a1", CommonUtils.generateSHA1("helloworld"));
	}
	
	@Test
	public void checkEmailFormat_test_1() {
		Assertions.assertTrue(CommonUtils.checkEmailFormat("qanh@gmail.com"));
	}
	
	@Test
	public void checkEmailFormat_test_2() {
		Assertions.assertTrue(CommonUtils.checkEmailFormat("anhlq.b17cn013@stu.ptit.edu.vn"));
	}
	
	@Test
	public void checkEmailFormat_test_3() {
		Assertions.assertFalse(CommonUtils.checkEmailFormat("This is not an email"));
	}
	
	@Test
	public void checkDateFormat_test_1() {
		Assertions.assertTrue(CommonUtils.checkDateFormat("2020/10/20", "yyyy/MM/dd"));
	}
	
	@Test
	public void checkDateFormat_test_2() {
		Assertions.assertTrue(CommonUtils.checkDateFormat("2021-09-11 11:13:10", "yyyy-MM-dd HH:mm:ss"));
	}
	
	@Test
	public void checkDateFormat_test_3() {
		Assertions.assertFalse(CommonUtils.checkDateFormat("2020-10-34", "yyyy-MM-dd"));
	}
	
	@Test
	public void checkDateFormat_test_4() {
		Assertions.assertFalse(CommonUtils.checkDateFormat("Not a date", "dd MM yyyy"));
	}
	
	@Test
	public void checkDateFormat_test_5() {
		Assertions.assertFalse(CommonUtils.checkDateFormat("2020-10-20", "Random format"));
	}
	
	@Test
	public void checkIntegerNumberFormat_test_1() {
		Assertions.assertTrue(CommonUtils.checkIntegerNumberFormat("2020"));
	}
	
	@Test
	public void checkIntegerNumberFormat_test_2() {
		Assertions.assertTrue(CommonUtils.checkIntegerNumberFormat("011"));
	}
	
	@Test
	public void checkIntegerNumberFormat_test_3() {
		Assertions.assertTrue(CommonUtils.checkIntegerNumberFormat("-511"));
	}
	
	@Test
	public void checkIntegerNumberFormat_test_4() {
		Assertions.assertFalse(CommonUtils.checkIntegerNumberFormat("56.32"));
	}
	
	@Test
	public void checkIntegerNumberFormat_test_5() {
		Assertions.assertFalse(CommonUtils.checkIntegerNumberFormat("What?"));
	}
	
	@Test
	public void checkIntegerNumberFormat_test_6() {
		Assertions.assertFalse(CommonUtils.checkIntegerNumberFormat("90$"));
	}
	
	@Test
	public void checkDoubleNumberFormat_test_1() {
		Assertions.assertTrue(CommonUtils.checkDoubleNumberFormat("100"));
	}
	
	@Test
	public void checkDoubleNumberFormat_test_2() {
		Assertions.assertTrue(CommonUtils.checkDoubleNumberFormat("22.64"));
	}
	
	@Test
	public void checkDoubleNumberFormat_test_3() {
		Assertions.assertTrue(CommonUtils.checkDoubleNumberFormat("-60"));
	}
	
	@Test
	public void checkDoubleNumberFormat_test_4() {
		Assertions.assertTrue(CommonUtils.checkDoubleNumberFormat("-9.6"));
	}
	
	@Test
	public void checkDoubleNumberFormat_test_5() {
		Assertions.assertTrue(CommonUtils.checkDoubleNumberFormat("0"));
	}
	
	@Test
	public void checkDoubleNumberFormat_test_6() {
		Assertions.assertFalse(CommonUtils.checkDoubleNumberFormat("3.6.7"));
	}
	
	@Test
	public void checkDoubleNumberFormat_test_7() {
		Assertions.assertFalse(CommonUtils.checkDoubleNumberFormat("-5 df 4"));
	}
	
	@Test
	public void checkIdNumberFormat_test_1() {
		Assertions.assertTrue(CommonUtils.checkIdNumberFormat("525364043"));
	}
	
	@Test
	public void checkIdNumberFormat_test_2() {
		Assertions.assertTrue(CommonUtils.checkIdNumberFormat("012412433525"));
	}
	
	@Test
	public void checkIdNumberFormat_test_3() {
		Assertions.assertFalse(CommonUtils.checkIdNumberFormat("54624325"));
	}
	
	@Test
	public void checkIdNumberFormat_test_4() {
		Assertions.assertFalse(CommonUtils.checkIdNumberFormat("5253644043"));
	}
	
	@Test
	public void checkIdNumberFormat_test_5() {
		Assertions.assertFalse(CommonUtils.checkIdNumberFormat("52536434043"));
	}
	
	@Test
	public void checkIdNumberFormat_test_6() {
		Assertions.assertFalse(CommonUtils.checkIdNumberFormat("5253644044323"));
	}
	
	@Test
	public void checkIdNumberFormat_test_7() {
		Assertions.assertFalse(CommonUtils.checkIdNumberFormat("adwsfsmfl"));
	}
	
	@Test
	public void checkGenerateRandomCode_test_1() {
		Assertions.assertEquals(5, CommonUtils.generateRandomCode(5).length());
	}
}

