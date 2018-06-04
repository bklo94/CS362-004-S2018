

import junit.framework.TestCase;

import java.security.SecureRandom;
import java.util.Random;

//You can use this as a skeleton for your 3 different test approach
//It is an optional to use this file, you can generate your own test file(s) to test the target function!
// Again, it is up to you to use this file or not!





public class UrlValidatorTest extends TestCase {


private boolean printStatus = false; //passStatus and failStatus control pass/fail messages separately
private boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test were using.

//TRUE = print
private boolean passStatus = false;
private boolean failStatus = true;

   public UrlValidatorTest(String testName) {
       testIsValid();
   }



   public void testManualTest()
   {
//You can use this function to implement your manual testing

   }


   public void testYourFirstPartition()
   {
	 //You can use this function to implement your First Partition testing

   }

   public void testYourSecondPartition(){
		 //You can use this function to implement your Second Partition testing

   }
   //You need to create more test cases for your Partitions if you need to

   public void testIsValid()
   {
      System.out.println("         [Base Testing Starts]         ");
      //random seeds
      Random random = new Random();

      //url true/false indicator
      String trueTestURL = "";
      String falseTestURL = "";
      UrlValidator trueValidator;
      UrlValidator falseValidator;

      //change this to 10000
      for (int i = 0; i < 10000; i++)
      {
         int trueSchemeNum = random.nextInt(5);
         int falseSchemeNum = random.nextInt(5);
         int trueAuthorityNum = random.nextInt(7);
         int falseAuthorityNum = random.nextInt(12);
         int truePortNum = random.nextInt(4);
         int falsePortNum = random.nextInt(3);
         int truePathNum = random.nextInt(9);
         int falsePathNum = random.nextInt(6);
         int trueQueryNum = random.nextInt(3);

         //generates true urls
         trueTestURL = trueTestUrlScheme[trueSchemeNum] + trueTestUrlAuthority[trueAuthorityNum] +
                 trueTestUrlPort[truePortNum] + trueTestPath[truePathNum] + trueTestUrlQuery[trueQueryNum];

         //generates false urls
         falseTestURL = falseTestUrlScheme[falseSchemeNum] + falseTestUrlAuthority[falseAuthorityNum] +
                 falseTestUrlPort[falsePortNum] + falseTestPath[falsePathNum];

         //true validator
         trueValidator = new UrlValidator(null, null, UrlValidator.ALLOW_LOCAL_URLS);
         trueValidator.isValid(trueTestURL);

         //false validator
         falseValidator = new UrlValidator(null, null, UrlValidator.ALLOW_LOCAL_URLS);
         falseValidator.isValid(falseTestURL);


         //test true urls
         System.out.println(" ----- Testing True URLS ----- ");
         boolean trueBugCount = trueValidator.isValid(trueTestURL);
         if (trueBugCount) {
            System.out.println("TEST PASS!");
         } else {
            // if test fails print the count and the particular URL that failed
            System.out.println("TEST FAIL!");
            System.out.println("URL Fail: " + trueTestURL);
            System.out.println("Actual: " + trueBugCount + " | Expected: true");
         }

         //test false urls
         System.out.println(" ----- Testing False URLS ----- ");
         boolean falseBugCount = falseValidator.isValid(falseTestURL);
         if (!falseBugCount) {
            System.out.println("TEST PASS!");
         } else {
            System.out.println("URL Fail: " + falseTestURL);
            System.out.println("Actual: " + falseBugCount + " | Expected: false");
         }
      }
      System.out.println("          [Base Testing Ended]            ");
   }
   /**
    * Create set of tests by taking the testUrlXXX arrays and
    * running through all possible permutations of their combinations.
    *
    * @param testObjects Used to create a url.
    */

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
           ""
   };

   String[] falseTestUrlPort = {
           ":-1",
           ":65a",
           ":65636"
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
           "/test1/file"
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


   public void testAnyOtherUnitTest()
   {
      //check for a functioning URL port
      System.out.println("Extra Unit Test Start");
      SecureRandom random = new SecureRandom();
      UrlValidator urlVal = new UrlValidator(null, null, UrlValidator.ALLOW_ALL_SCHEMES);
      int RandomNumber;
      //change this to 10000 before submission
      for(int i = 0;i<10000;i++)
      {
         RandomNumber = random.nextInt(10000) + 1;
         //System.out.println("http://www.google.com:"+ RandomNumber);
         System.out.println(urlVal.isValid("http://www.google.com:"+ RandomNumber)) ;
      }
   }
}