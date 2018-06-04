/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import junit.framework.TestCase;

import java.util.Random;

/**
 * Performs Validation Test for url validations.
 *
 * @version $Revision: 1739358 $
 */
public class UrlValidatorTest extends TestCase {

   private final boolean printStatus = false;
   private final boolean printIndex = false;//print index that indicates current scheme,host,port,path, query test were using.

   public UrlValidatorTest(String testName) {
      super(testName);
   }

   @Override
protected void setUp() {
      for (int index = 0; index < testPartsIndex.length - 1; index++) {
         testPartsIndex[index] = 0;
      }
   }

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



   public void testIsValidScheme() {
      if (printStatus) {
         System.out.print("\n testIsValidScheme() ");
      }
      String[] schemes = {"http", "gopher"};
      //UrlValidator urlVal = new UrlValidator(schemes,false,false,false);
      UrlValidator urlVal = new UrlValidator(schemes, 0);
      for (int sIndex = 0; sIndex < testScheme.length; sIndex++) {
         ResultPair testPair = testScheme[sIndex];
         boolean result = urlVal.isValidScheme(testPair.item);
         assertEquals(testPair.item, testPair.valid, result);
         if (printStatus) {
            if (result == testPair.valid) {
               System.out.print('.');
            } else {
               System.out.print('X');
            }
         }
      }
      if (printStatus) {
         System.out.println();
      }

   }

   /**
    * Create set of tests by taking the testUrlXXX arrays and
    * running through all possible permutations of their combinations.
    *
    * @param testObjects Used to create a url.
    */
   public void testIsValid(Object[] testObjects, long allowAllSchemes) {
	      UrlValidator urlVal = new UrlValidator(null, null, allowAllSchemes);
	      //UrlValidator urlVal = new UrlValidator(null, allowAllSchemes);
      assertTrue(urlVal.isValid("http://www.google.com"));
      assertTrue(urlVal.isValid("http://www.google.com/"));
      int statusPerLine = 60;
      int printed = 0;
      if (printIndex)  {
         statusPerLine = 6;
      }
      do {
          StringBuilder testBuffer = new StringBuilder();
         boolean expected = true;
         for (int testPartsIndexIndex = 0; testPartsIndexIndex < testPartsIndex.length; ++testPartsIndexIndex) {
            int index = testPartsIndex[testPartsIndexIndex];
            ResultPair[] part = (ResultPair[]) testObjects[testPartsIndexIndex];
            testBuffer.append(part[index].item);
            expected &= part[index].valid;
         }
         String url = testBuffer.toString();
         boolean result = urlVal.isValid(url);
         if(result == true)
        	 System.out.println(url);
         assertEquals(url, expected, result);
         if (printStatus) {
            if (printIndex) {
               System.out.print(testPartsIndextoString());
            } else {
               if (result == expected) {
                  System.out.print('.');
               } else {
                  System.out.print('X');
               }
            }
            printed++;
            if (printed == statusPerLine) {
               System.out.println();
               printed = 0;
            }
         }
      } while (incrementTestPartsIndex(testPartsIndex, testObjects));
      if (printStatus) {
         System.out.println();
      }
   }

   public void testValidator202() {
       String[] schemes = {"http","https"};
       UrlValidator urlValidator = new UrlValidator(schemes, UrlValidator.NO_FRAGMENTS);
       urlValidator.isValid("http://www.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.logoworks.comwww.log");
   }

   public void testValidator204() {
       String[] schemes = {"http","https"};
       UrlValidator urlValidator = new UrlValidator(schemes);
       assertTrue(urlValidator.isValid("http://tech.yahoo.com/rc/desktops/102;_ylt=Ao8yevQHlZ4On0O3ZJGXLEQFLZA5"));
   }

   static boolean incrementTestPartsIndex(int[] testPartsIndex, Object[] testParts) {
      boolean carry = true;  //add 1 to lowest order part.
      boolean maxIndex = true;
      for (int testPartsIndexIndex = testPartsIndex.length - 1; testPartsIndexIndex >= 0; --testPartsIndexIndex) {
         int index = testPartsIndex[testPartsIndexIndex];
         ResultPair[] part = (ResultPair[]) testParts[testPartsIndexIndex];
         if (carry) {
            if (index < part.length - 1) {
               index++;
               testPartsIndex[testPartsIndexIndex] = index;
               carry = false;
            } else {
               testPartsIndex[testPartsIndexIndex] = 0;
               carry = true;
            }
         }
         maxIndex &= (index == (part.length - 1));
      }


      return (!maxIndex);
   }

   private String testPartsIndextoString() {
       StringBuilder carryMsg = new StringBuilder("{");
      for (int testPartsIndexIndex = 0; testPartsIndexIndex < testPartsIndex.length; ++testPartsIndexIndex) {
         carryMsg.append(testPartsIndex[testPartsIndexIndex]);
         if (testPartsIndexIndex < testPartsIndex.length - 1) {
            carryMsg.append(',');
         } else {
            carryMsg.append('}');
         }
      }
      return carryMsg.toString();

   }

   public void testValidateUrl() {
      assertTrue(true);
   }

   /**
    * Only used to debug the unit tests.
    * @param argv
    */
   public static void main(String[] argv) {

	   UrlValidatorTest fct = new UrlValidatorTest("url test");
      fct.setUp();
      fct.testIsValid();
      fct.testIsValidScheme();
   }
   //-------------------- Test data for creating a composite URL
   /**
    * The data given below approximates the 4 parts of a URL
    * <scheme>://<authority><path>?<query> except that the port number
    * is broken out of authority to increase the number of permutations.
    * A complete URL is composed of a scheme+authority+port+path+query,
    * all of which must be individually valid for the entire URL to be considered
    * valid.
    */
   ResultPair[] testUrlScheme = {new ResultPair("http://", true),
                               new ResultPair("ftp://", true),
                               new ResultPair("h3t://", true),
                               new ResultPair("3ht://", false),
                               new ResultPair("http:/", false),
                               new ResultPair("http:", false),
                               new ResultPair("http/", false),
                               new ResultPair("://", false),
                               new ResultPair("", true)};

   ResultPair[] testUrlAuthority = {new ResultPair("www.google.com", true),
                                  new ResultPair("go.com", true),
                                  new ResultPair("go.au", true),
                                  new ResultPair("0.0.0.0", true),
                                  new ResultPair("255.255.255.256", true),
                                  new ResultPair("256.256.256.256", false)
   };
   ResultPair[] testUrlPort = {new ResultPair(":80", true),
                             new ResultPair(":65535", true),
                             new ResultPair(":0", true),
                             new ResultPair("", true),
                             new ResultPair(":-1", false),
                            new ResultPair(":65636",false),
                             new ResultPair(":65a", false)
   };
   ResultPair[] testPath = {new ResultPair("/test1", true),
                          new ResultPair("/t123", true),
                          new ResultPair("/$23", true),
                          new ResultPair("/..", false),
                          new ResultPair("/../", false),
                          new ResultPair("/test1/", true),
                          new ResultPair("", true),
                          new ResultPair("/test1/file", true),
                          new ResultPair("/..//file", false),
                          new ResultPair("/test1//file", false)
   };
   //Test allow2slash, noFragment
   ResultPair[] testUrlPathOptions = {new ResultPair("/test1", true),
                                    new ResultPair("/t123", true),
                                    new ResultPair("/$23", true),
                                    new ResultPair("/..", false),
                                    new ResultPair("/../", false),
                                    new ResultPair("/test1/", true),
                                    new ResultPair("/#", false),
                                    new ResultPair("", true),
                                    new ResultPair("/test1/file", true),
                                    new ResultPair("/t123/file", true),
                                    new ResultPair("/$23/file", true),
                                    new ResultPair("/../file", false),
                                    new ResultPair("/..//file", false),
                                    new ResultPair("/test1//file", true),
                                    new ResultPair("/#/file", false)
   };

   ResultPair[] testUrlQuery = {new ResultPair("?action=view", true),
                              new ResultPair("?action=edit&mode=up", true),
                              new ResultPair("", true)
   };

   Object[] testUrlParts = {testUrlScheme, testUrlAuthority, testUrlPort, testPath, testUrlQuery};
   Object[] testUrlPartsOptions = {testUrlScheme, testUrlAuthority, testUrlPort, testUrlPathOptions, testUrlQuery};
   int[] testPartsIndex = {0, 0, 0, 0, 0};

   //---------------- Test data for individual url parts ----------------
   ResultPair[] testScheme = {new ResultPair("http", true),
                            new ResultPair("ftp", false),
                            new ResultPair("httpd", false),
                            new ResultPair("telnet", false)};


}