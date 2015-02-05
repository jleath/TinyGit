import list.EquationList;

public class Calculator {
    private EquationList equations;

    /**
     * TASK 2: ADDING WITH BIT OPERATIONS
     * add() is a method which computes the sum of two integers x and y using 
     * only bitwise operators.
     * @param x is an integer which is one of two addends
     * @param y is an integer which is the other of the two addends
     * @return the sum of x and y
     **/
    public int add(int x, int y) {
        int sum = 0;
        int carry = 0;
        int mask = 1;
        // for each bit in x and y
        for (int i = 0; i < 32; i++) {
            // push the current bit all the way to the right
            int maskedX = x >> i;
            int maskedY = y >> i;
            // remove all but the rightmost bit
            maskedX = mask & maskedX;
            maskedY = mask & maskedY;

            int result = 0;
            // add the rightmost bits
            if ((maskedX & maskedY) == 1) {
                result = 0 | carry;
                carry = 1;
            } else if ((maskedX | maskedY) == 1) {
                result = 1;
                if (carry == 1) {
                    result = 0;
                    carry = 1;
                }
            } else {
                result = 0;
                if (carry == 1) {
                    result = 1;
                    carry = 0;
                }
            }
            // push result back to the proper position
            result = result << i;
            sum = sum | result;
        }
        return sum;
    }

    /**
     * TASK 3: MULTIPLYING WITH BIT OPERATIONS
     * multiply() is a method which computes the product of two integers x and 
     * y using only bitwise operators.
     * @param x is an integer which is one of the two numbers to multiply
     * @param y is an integer which is the other of the two numbers to multiply
     * @return the product of x and y
     **/
    public int multiply(int x, int y) {
        int result = x;
        int multiplier = y;
        boolean negative_result = false;
        if (y < 0) {
            if (x > 0)
                negative_result = true;
            y = ~(y-1);
        }
        int mults_done = 1;
        while (multiplier >= 2) {
            result = result << 1;
            multiplier = multiplier / 2;
            mults_done = add(mults_done, mults_done);
        }
        while (mults_done != y) {
            result = add(result, x);
            mults_done = add(mults_done, 1);
        }
        if (negative_result)
            result = ~result + 1;
        return result;
    }

    /**
     * TASK 5A: CALCULATOR HISTORY - IMPLEMENTING THE HISTORY DATA STRUCTURE
     * saveEquation() updates calculator history by storing the equation and 
     * the corresponding result.
     * Note: You only need to save equations, not other commands.  See spec for 
     * details.
     * @param equation is a String representation of the equation, ex. "1 + 2"
     * @param result is an integer corresponding to the result of the equation
     **/
    public void saveEquation(String equation, int result) {
        if (equations == null) {
            equations = new EquationList(equation, result, null);
        } else {
            equations = new EquationList(equation, result, equations);
        }
    }

    /**
     * TASK 5B: CALCULATOR HISTORY - PRINT HISTORY HELPER METHODS
     * printAllHistory() prints each equation (and its corresponding result), 
     * most recent equation first with one equation per line.  Please print in 
     * the following format:
     * Ex   "1 + 2 = 3"
     **/
    public void printAllHistory() {
        if (equations == null)
            return;
        printHistory(equations.length());
    }

    /**
     * TASK 5B: CALCULATOR HISTORY - PRINT HISTORY HELPER METHODS
     * printHistory() prints each equation (and its corresponding result), 
     * most recent equation first with one equation per line.  A maximum of n 
     * equations should be printed out.  Please print in the following format:
     * Ex   "1 + 2 = 3"
     **/
    public void printHistory(int n) {
        if (equations == null)
            return;
        if (n > equations.length()) {
            printAllHistory();
            return;
        }
        int eqs_printed = 0;
        EquationList ptr = equations;
        while (eqs_printed < n) {
            System.out.println(ptr.equation + " = " + ptr.result);
            eqs_printed += 1;
            ptr = ptr.next;
        }
    }    

    /**
     * TASK 6: CLEAR AND UNDO
     * undoEquation() removes the most recent equation we saved to our history.
    **/
    public void undoEquation() {
        if (equations == null) {
            System.out.println("There are no equations to undo.");
            return;
        }
        equations = equations.next;
    }

    /**
     * TASK 6: CLEAR AND UNDO
     * clearHistory() removes all entries in our history.
     **/
    public void clearHistory() {
        equations = null;
    }

    /**
     * TASK 7: ADVANCED CALCULATOR HISTORY COMMANDS
     * cumulativeSum() computes the sum over the result of each equation in our 
     * history.
     * @return the sum of all of the results in history
     **/
    public int cumulativeSum() {
        int result = 0;
        EquationList ptr = equations;
        while (ptr != null) {
            result += ptr.result;
            ptr = ptr.next;
        }
        return result;
    }

    /**
     * TASK 7: ADVANCED CALCULATOR HISTORY COMMANDS
     * cumulativeProduct() computes the product over the result of each equation 
     * in history.
     * @return the product of all of the results in history
     **/
    public int cumulativeProduct() {
        int result = 1;
        EquationList ptr = equations;
        while (ptr != null) {
            result *= ptr.result;
            ptr = ptr.next;
        }
        return result;
    }
}