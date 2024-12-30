/*
 * Created on Feb 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author srini
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
import java.io.*;


/*
 *  implementation of Knuth-Morris-Pratt String search algorithm
 *
 * 
 */


public class KMP {
    
    public static int KMPSearch(String text,String pattern) throws IOException {
	
	//System.out.print("Text to search : "+text);
	
	int n = text.length();
	
	System.out.print("Pattern to search for : "+pattern);
	
	int m = pattern.length();
	int valid = 0;
	int comparisons = 0;
    
	int[] shift = KMPShift(pattern);
	
	//System.out.print("Shifts : ");
	//for (int i = 0; i < m; i++)
	    //System.out.print(shift[i]+ "  ");
	//System.out.println();
	int i = 0; // i is the current shift
	int j = 0; // j is the position in pattern that we are currently matching
	
	while (i + m <= n) {
	    //System.out.println("Trying shift " + i);
	    //System.out.println(text);
	    //for (int k = 0; k < i; k++) //System.out.print(" ");
	    //System.out.println(pattern);
	    
	    /*
	     * This while loop is the "real" way to do this
	     */
	    /*
	    while ((j < m) && (text.charAt(i+j) == pattern.charAt(j)) )
		j++;
	    */
	    
	    /*
	     * This while loop is the "demo" way to do it
	     * It shows the characters being examined, and it counts the
	     * comparisons being made
	     */
	    boolean keepLooping = true;
	    while (keepLooping) {
		if (j == m) keepLooping = false;
		else {
		    comparisons++;
		    //System.out.println("Examining the " + text.charAt(i+j) + " in position " + (i+j));
		    if (text.charAt(i+j) != pattern.charAt(j)) {
			keepLooping = false;
			//System.out.println("Mismatch");
		    } // if
		    else j++;
		} // else
	    } // while
	    if (j == m) {
		//System.out.println("Pattern found at shift " + i);
		valid++;
	    }
	    // now increase the shift by the largest safe amount
	    if (j == 0) // first characters didn't match - increase shift by 1
		i = i + 1;
	    else {
		// pattern[0 .. j-1] matched text[i .. i+j-1]
		i = i + shift[j-1]; // increase i by the pre-computed amount
		j = Math.max(j - shift[j-1],0);
				    // set j to the first position in pattern
				    // that needs to be checked with the new shift
	    } // else
	} // while
	
	
	//System.out.println("\nSearch completed");
	System.out.println("Text length : " + n + "    Pattern length : " + m);
	System.out.println("Number of matches : " + valid);
	System.out.println("Number of comparisons : " + comparisons);
	return valid;
    } //main(String[])
    
    static int[] KMPShift(String p){
	/*
	 * KMPShift basically applies the KMP algorithm to finding
	 * prefixes of p inside p itself.  It uses exactly the same
	 * technique for skipping over irrelevant shifts.  This seems like
	 * magic (how can we compute the shifts with an algorithm that needs to
	 * know the shifts in order to work?) until we realize that the shift value
	 * for every j is determined using the shift value for j-1.  We compute them
	 * in order, and the information we need for each one is already there. 
	 */
	 
	 /*
	  * When the method returns, s[j] is the amount by which the shift can be
	  * increased, if the last match occurs when we are comparing pattern[j]
	  * to a character in the text string.
	  */
	int m = p.length();
	int[] s = new int[m];
	s[0] = 1;  // if the first characters match and the second ones do not
		   // (i.e. position 0 is the only match) then we can only shift
		   // by 1
	int i = 1; // 1 is the default shift
	int j = 0;
	while (i + j < m) {
	    if (p.charAt(i+j) == p.charAt(j)) {
		// The left side is the "text" and the right side is the "pattern" 
		// As long as characters keep matching, we can't increase the shift.
		s[i+j] = i;
		j++;
	    } // if
	    else {
		// mismatch found at position j in "pattern", i+j in "text"
		if (j == 0) {
		    // The character in position i in the "text" is different from the 
		    // character in position 0 in the "pattern", so we can shift past it
		    s[i] = i+1;
		    i++;
		} // if
		else {
		    // the character in position i in the "text" is different from the
		    // character in position j in the "pattern", but the previous
		    // characters in the "pattern" have matched.
		    // We increase i by the previously computed shift value
		    i = i + s[j-1];
		    // and decrease j by the same amount, to keep it aligned
		    j = Math.max(j - s[j-1],0);
		} // else
	    } // else
	} // while
	return s;
    } // KMPShift(String)


} // class KMP
