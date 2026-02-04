import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class Interface_Tests {

    private static final double TOLERANCE = 0.001;

    // ==========================================
    // TEST 1: MONSTER CLASS (The Class with Height/Weight/Age)
    // ==========================================
    @Test
    public void testMonsterClass() {
        // Test Constructors and Getters
        Monster m1 = new Monster(10, 20, 5); // ht, wt, age
        assertEquals(10, m1.getHeight(), "Monster height should be 10");
        assertEquals(20, m1.getWeight(), "Monster weight should be 20");
        assertEquals(5, m1.getAge(), "Monster age should be 5");

        // Test Setters
        m1.setHeight(15);
        m1.setWeight(25);
        m1.setAge(6);
        assertEquals(15, m1.getHeight());
        assertEquals(25, m1.getWeight());
        assertEquals(6, m1.getAge());

        // Test clone
        Monster m2 = (Monster) m1.clone();
        assertEquals(m1.getHeight(), m2.getHeight(), "Clone should match height");
        assertEquals(m1.getWeight(), m2.getWeight(), "Clone should match weight");
        assertEquals(m1.getAge(), m2.getAge(), "Clone should match age");
        assertNotSame(m1, m2, "Clone should return a new object memory address");

        // Test compareTo Hierarchy: Height > Weight > Age
        // Case 1: Different Height
        Monster small = new Monster(1, 100, 100);
        Monster big = new Monster(5, 1, 1);
        assertTrue(big.compareTo(small) > 0, "Monster with bigger Height should be >");
        assertTrue(small.compareTo(big) < 0, "Monster with smaller Height should be <");

        // Case 2: Same Height, Different Weight
        Monster heavy = new Monster(10, 50, 1);
        Monster light = new Monster(10, 20, 100);
        assertTrue(heavy.compareTo(light) > 0, "Same Height: Bigger Weight should be >");

        // Case 3: Same Height, Same Weight, Different Age
        Monster old = new Monster(10, 20, 50);
        Monster young = new Monster(10, 20, 5);
        assertTrue(old.compareTo(young) > 0, "Same H/W: Older Age should be >");

        // Case 4: All Same
        Monster twin = new Monster(10, 20, 50);
        assertEquals(0, old.compareTo(twin), "Identical monsters should return 0");
    }

    // ==========================================
    // TEST 2: SKELETON (Implements Monster_i Interface)
    // ==========================================
    @Test
    public void testSkeletonClass() {
        // We test this assuming the class implements 'Monster_i'

        Skeleton s1 = new Skeleton("Bones", 10);
        Skeleton s2 = new Skeleton("Skully", 5);
        Skeleton s3 = new Skeleton("Bones", 10); // duplicate logic check

        // Check if Skeleton actually implements Monster_i
        assertTrue(s1 instanceof Monster_i, "Skeleton class must implement Monster_i interface");

        // Test Accessors
        assertEquals("Bones", s1.getName(), "Skeleton name incorrect");
        assertEquals(10, s1.getHowBig(), "Skeleton size incorrect");

        // Test namesTheSame
        assertTrue(s1.namesTheSame(s3), "namesTheSame should return true for matching names");
        assertFalse(s1.namesTheSame(s2), "namesTheSame should return false for diff names");

        // Test isBigger (Logic: this.howBig > other.howBig)
        assertTrue(s1.isBigger(s2), "Size 10 should be bigger than Size 5");
        assertFalse(s2.isBigger(s1), "Size 5 should not be bigger than Size 10");
        assertFalse(s1.isBigger(s3), "Size 10 should not be bigger than Size 10");

        // Test isSmaller (Logic: this.howBig < other.howBig)
        assertTrue(s2.isSmaller(s1), "Size 5 should be smaller than Size 10");
        assertFalse(s1.isSmaller(s2), "Size 10 should not be smaller than Size 5");
    }

    // ==========================================
    // TEST 3: ROMAN NUMERAL (Comparable)
    // ==========================================
    @Test
    public void testRomanNumeralClass() {
        // Test Integer -> Roman Constructor
        RomanNumeral r1 = new RomanNumeral(10);
        assertTrue(r1.toString().contains("X"), "10 should be X");

        RomanNumeral r2 = new RomanNumeral(14);
        assertTrue(r2.toString().contains("XIV"), "14 should be XIV");

        // Test String -> Integer Constructor
        RomanNumeral r3 = new RomanNumeral("V");
        assertEquals(5, r3.getNumber(), "V should be 5");

        RomanNumeral r4 = new RomanNumeral("LXXVII"); // 77
        assertEquals(77, r4.getNumber(), "LXXVII should be 77");

        // Test Setters Syncing
        r1.setNumber(20);
        assertTrue(r1.toString().contains("XX"), "setNumber(20) did not update roman string");

        r1.setRoman("III");
        assertEquals(3, r1.getNumber(), "setRoman(III) did not update number");

        // Test compareTo
        RomanNumeral ten = new RomanNumeral(10);
        RomanNumeral five = new RomanNumeral(5);
        RomanNumeral twenty = new RomanNumeral(20);

        assertTrue(ten.compareTo(five) > 0, "10 should be > 5");
        assertTrue(ten.compareTo(twenty) < 0, "10 should be < 20");
        assertEquals(0, ten.compareTo(new RomanNumeral("X")), "10 should equal X");
    }

    // ==========================================
    // TEST 4: WORD (Comparable Vowel Sorting)
    // ==========================================
    @Test
    public void testWordClass() {
        // Logic: Sort by vowel count first, then alphabetical

        // Case 1: Different Vowel Counts
        Word oneVowel = new Word("dog"); // 1 vowel (o)
        Word realTwo = new Word("boat"); // 2 vowels (o, a)

        // "dog" (1) vs "boat" (2) -> dog should be < boat
        assertTrue(oneVowel.compareTo(realTwo) < 0, "1 vowel should be < 2 vowels");
        assertTrue(realTwo.compareTo(oneVowel) > 0, "2 vowels should be > 1 vowel");

        // Case 2: Same Vowel Count, Different Alphabet
        Word w1 = new Word("apple"); // 2 vowels (a, e)
        Word w2 = new Word("zebra"); // 2 vowels (e, a)
        // 'a'pple comes before 'z'ebra, so apple < zebra
        assertTrue(w1.compareTo(w2) < 0, "Same vowels: apple should be < zebra");
        assertTrue(w2.compareTo(w1) > 0, "Same vowels: zebra should be > apple");

        // Case 3: Equality
        Word w3 = new Word("apple");
        assertEquals(0, w1.compareTo(w3), "Identical words should return 0");
    }
}