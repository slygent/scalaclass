package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton 1")
      assert(!contains(s1, 2), "Singleton 2")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }

  test("intersection of two sets") {
    new TestSets {
      val t = union(s1, s2)
      val s = intersect(s1, t)
      assert(contains(s, 1), "Intersect 1")
      assert(!contains(s, 2), "Intersect 2")
      assert(!contains(s, 3), "Intersect 3")
    }
  }

  test("diff of two sets") {
    new TestSets {
      val t = union(s1, s2)
      val s = diff(t, s1)
      assert(!contains(s, 1), "Diff 1")
      assert(contains(s, 2), "Diff 2")
      assert(!contains(s, 3), "Diff 3")
    }
  }

  test("udacity forum") {
    val evenNums = ((x: Int)=> x % 2 == 0)
    val t2 = filter(x => true, x => evenNums(x) || x == 3)
      
    assert(exists(t2, (x=> x== 2))) //tests to see if there exist an even number
    assert(exists(t2, (x=> x == 3))) //tests to see if the number 3 exists
    assert(!exists(t2, (x=> x == 67))) //test to see if an odd number DOESN't exists

    assert(contains(t2, 3), "contains a value 3") //test to see if it "contains" 3
    assert(contains(t2, 54), "contains an even number")  //tests to see if it "contains" an even number
    assert(!contains(t2, 109), "doesn't contain an odd number")
  }

  test("subset") {
    new TestSets {
      def p(x: Int): Boolean = x % 2 == 0
      val t = union(s1, s2)
      val s = filter(t, p)
      assert(!contains(s, 1), "Filter 1")
      assert(contains(s, 2), "Filter 2")
      assert(!contains(s, 3), "Filter 3")
    }
  }

  test("forall") {
    new TestSets {
      def p(x: Int): Boolean = x % 2 == 0
      def s: Set = x => x % 4 == 0
      assert(forall(s, p))
    }
  }

  test("exists") {
    new TestSets {
      def p(x: Int): Boolean = x % 2 == 0
      def s: Set = x => x % 4 == 0
      assert(exists(s, p))
      assert(exists(s, p))
    }
  }


  test("map") {
    new TestSets {
      def f(x: Int): Int = x*x
      val t = union(s1, s2)
      val s = map(t, f)
      assert(contains(t, 1), "Map contains 1")
      assert(contains(t, 2), "Map contains 2")
      assert(contains(s, 1), "Map 1")
      assert(!contains(s, 2), "Map 2")
      assert(contains(s, 4), "Map 3")
    }
  }



}
