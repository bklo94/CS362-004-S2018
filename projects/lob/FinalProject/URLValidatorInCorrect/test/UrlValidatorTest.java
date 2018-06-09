

import junit.framework.TestCase;
import java.security.SecureRandom;
import java.util.Random;

//You can use this as a skeleton for your 3 different test approach
//It is an optional to use this file, you can generate your own test file(s) to test the target function!
// Again, it is up to you to use this file or not!


public class UrlValidatorTest extends TestCase {

   //Global arrays to form the random URL's
   String[] trueTestUrlScheme = {
		   "http://",
		   "ftp://",
		   "https://",
		   "h3t://",
		   ""
   };

   String[] falseTestUrlScheme = {
		   "3ht://",
		   "http:/",
		   "http:",
		   "http/",
		   "://"
   };

   String[] trueTestUrlAuthority = {
		   "www.google.com",
		   "go.com",
		   "go.au",
		   "0.0.0.0",
		   "255.255.255.255",
		   "255.com",
		   "go.cc"
   };

   String[] falseTestUrlAuthority = {
		   "256.256.256.256",
		   "1.2.3.4.5",
		   "1.2.3.4.",
		   "1.2.3",
		   ".1.2.3.4",
		   "go.a",
		   "go.a1a",
		   "go.1aa",
		   "aaa.",
		   ".aaa",
		   "aaa",
		   ""
   };

   String[] trueTestUrlPort = {
		   ":80",
		   ":65535",
		   ":0",
		   "",
		   ":65636"
   };

   String[] falseTestUrlPort = {
		   ":-1",
		   ":65a"
   };

   String[] trueTestPath = {
		   "/test1",
		   "/t123",
		   "/$23",
		   "/test1/",
		   "",
		   "/test1/file",
		   "/t123/file",
		   "/$23/file",
		   "/test1//file"
   };

   String[] falseTestPath = {
		   "/..",
		   "/../",
		   "/#",
		   "/../file",
		   "/..//file",
		   "/#/file"
   };

   String[] trueTestUrlQuery = {
		   "?action=view",
		   "?action=edit&mode=up",
		   ""
   };

   public UrlValidatorTest(String testName) {
      super(testName);
   }

   public void printManualTest(UrlValidator urlVal, String testUrl, String expected) {
	   if(urlVal.isValid(testUrl)) {
		   System.out.println("\"" + testUrl + "\" is Valid. Expected: " + expected);
	   }
	   else {
		   System.out.println("\"" + testUrl + "\" is Invalid. Expected: " + expected);
	   }
   }

   public void testManualTest()
   {
	   //You can use this function to implement your manual testing
	   UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);

	   System.out.println("BEGIN MANUAL TESTING");
	   printManualTest(urlVal, "http://www.google.com","Valid");
	   printManualTest(urlVal, "http:www.google.com","Invalid");
	   printManualTest(urlVal, "http://www.google.com/","Valid");
	   //printManualTest(urlVal, "https://www.google.com", "Valid"); //https can potentially crash the program.
	   printManualTest(urlVal, "http://google.com","Valid");
	   printManualTest(urlVal, "http://google.com/","Valid");
	   printManualTest(urlVal, "http://www.google.com/test","Valid");
	   printManualTest(urlVal, "http://www.google.com/../test","Invalid");

	   printManualTest(urlVal, "http://www.google.com:8080","Valid");
	   printManualTest(urlVal, "http://www.google.com:80/test","Valid");
	   printManualTest(urlVal, "http://www.google.com:8000000/test","Invalid");

	   printManualTest(urlVal, "http://www.google.com/search?rlz=1C1EJFA_enUS720US720&ei=zZAYW-viH6uL0wKPiZsI&btnG=Search&q=test","Valid"); //url from searching for "test" in google

	   printManualTest(urlVal, "http://testurl.edu","Valid");
	   printManualTest(urlVal, "http://testurl.gov","Valid");

	   printManualTest(urlVal, "http://.com","Invalid");

	   printManualTest(urlVal, "http://www.google.com/test?test=1","Valid");
	   printManualTest(urlVal, "http://www.google.com?test=1&mode=up","Valid");

	   printManualTest(urlVal, "http://127.0.0.1", "Valid");
	   printManualTest(urlVal, "http://127.0.0.1:80", "Valid");
	   printManualTest(urlVal, "http://127.0.0.1:80/test.html", "Valid");
	   printManualTest(urlVal, "http://127.0.0.1:80/test", "Valid");
	   printManualTest(urlVal, "http://127.255.0.1", "Valid");
	   printManualTest(urlVal, "http://255.255.255.255", "Valid");

	   printManualTest(urlVal, "http://5000.5000.5000.5000","Invalid");

	   //.getInstance() is bugged.
	   //InetAddressValidator inet = InetAddressValidator.getInstance();
	   //if(inet.isValid("5000.5000.5000.5000")) { //fail due to inet being null.
	   //   System.out.println("5000.5000.5000.5000 returned Valid");
	   //}
	   //else {
	   //   System.out.println("5000.5000.5000.5000 returned Invalid");
	   //}

	   System.out.println("END MANUAL TESTING");
   }


   // test a URL in lowercase that has an allowed scheme and authority
   public void testIsValid_DefaultConstructor_LowercaseValidScheme()
   {
    UrlValidator urlVal = new UrlValidator();
    assert urlVal.isValid("http://www.sciencealert.com") == true : "valid lowercase scheme is not passing isValid";
   }

   // test a URL in uppercase that has an allowed scheme and authority
   public void testIsValid_DefaultConstructor_UppercaseValidScheme()
   {
    UrlValidator urlVal = new UrlValidator(); // default constructor requires lowerCase http, https, ftp
    assert urlVal.isValid("HTTP://WWW.SCIENCEALERT.COM") == true : "valid uppercase scheme is not passing isValid";
   }

   // test a URL in uppercase that uses a UrlValidator constructor that allows upperCase scheme and authority
   public void testIsValid_Constructor_UppercaseValidScheme()
   {
    String[] schemes = {"http", "HTTP"};
    UrlValidator urlVal = new UrlValidator(schemes); // constructor allows http and HTTP schemes
    assert urlVal.isValid("HTTP://www.sciencealert.com") == true : "constructor allows uppercase 'HTTP' scheme, but uppercase is not passing isValid";
   }

   // test a URL that has an invalid scheme and an allowed authority
   public void testIsValid_DefaultConstructor_InvalidScheme()
   {
    UrlValidator urlVal = new UrlValidator();
    assert urlVal.isValid("jtp://www.sciencealert.com") == false : "UrlValidator default constructor and 'jtp' scheme is passing isValid";
   }

   // test a URL that has an allowed scheme but no authority
   public void testIsValid_DefaultConstructor_ValidScheme_NoAuthority()
   {
    UrlValidator urlVal = new UrlValidator();
    assert urlVal.isValid("http://") == false : "UrlValidator default constructor, valid scheme, and no authority is passing isValid";
   }

   public void testIsValid()
   {
	   System.out.println("Random Testing Start");
	   //get the random seed
	   Random random = new Random();

	   //url true/false indicator
	   String trueTestURL = "";
	   String falseTestURL = "";
	   UrlValidator trueValidator;
	   UrlValidator falseValidator;

	   //number of iterations the test will run
	   for (int i = 0; i < 10000; i++)
	   {
         //Variables used to grab the random URL based on the size of the array
		   int trueSchemeNum = random.nextInt(5);
		   int falseSchemeNum = random.nextInt(5);
		   int trueAuthorityNum = random.nextInt(7);
		   int falseAuthorityNum = random.nextInt(12);
		   int truePortNum = random.nextInt(5);
		   int falsePortNum = random.nextInt(2);
		   int truePathNum = random.nextInt(9);
		   int falsePathNum = random.nextInt(6);
		   int trueQueryNum = random.nextInt(3);

		   //generate the URL's using the random variables
		   trueTestURL = trueTestUrlScheme[trueSchemeNum] + trueTestUrlAuthority[trueAuthorityNum] +
				   trueTestUrlPort[truePortNum] + trueTestPath[truePathNum] + trueTestUrlQuery[trueQueryNum];
		   falseTestURL = falseTestUrlScheme[falseSchemeNum] + falseTestUrlAuthority[falseAuthorityNum] +
				   falseTestUrlPort[falsePortNum] + falseTestPath[falsePathNum];

		   //check the URLs
		   trueValidator = new UrlValidator(null, null, UrlValidator.ALLOW_LOCAL_URLS);
		   trueValidator.isValid(trueTestURL);
		   falseValidator = new UrlValidator(null, null, UrlValidator.ALLOW_LOCAL_URLS);
		   falseValidator.isValid(falseTestURL);

         //Test the supposed valid URL's
         //Check the URL, if it does not pass then print out the URL
		   System.out.println("-TRUE URL TEST");
		   boolean trueBugCount = trueValidator.isValid(trueTestURL);
		   if (trueBugCount) {
			   System.out.println("TEST PASS!");
		   }
         else {
			   System.out.println("TEST FAIL!");
			   System.out.println("URL Fail: " + trueTestURL);
			   System.out.println("Actual: " + trueBugCount + " | Expected: true");
		   }

         //Test the supposed invalid URL's
         //Check the URL, if it does not pass then print out the URL
		   System.out.println("-FALSE URL TEST");
		   boolean falseBugCount = falseValidator.isValid(falseTestURL);
		   if (!falseBugCount) {
			   System.out.println("TEST PASS!");
		   }
         else {
            System.out.println("TEST FAIL!");
			   System.out.println("URL Fail: " + falseTestURL);
			   System.out.println("Actual: " + falseBugCount + " | Expected: false");
		   }
	   }
	   System.out.println("Random Testing End");
   }

   //extra unit test to test port
   public void testExtraUnitTest()
   {
     //check for valid URL port
     System.out.println("Extra Unit Test Start");
     SecureRandom random = new SecureRandom();
     UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
     int RandomNumber;
     for(int i = 0;i<10000;i++)
     {
        RandomNumber = random.nextInt(10000) + 1;
        System.out.println("http://www.google.com:"+ RandomNumber);
        System.out.println(urlVal.isValid("http://www.google.com:"+ RandomNumber)) ;
     }
     System.out.println("Extra Unit Test End");
   }
}
