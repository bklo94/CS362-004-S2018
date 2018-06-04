

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



   public void testManualTest()
   {
//You can use this function to implement your manual testing

   }


   public void testYourFirstPartition()
   {
	 //You can use this function to implement your First Partition testing

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
