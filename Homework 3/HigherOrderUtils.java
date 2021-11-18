import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

public class HigherOrderUtils {

    static interface NamedBiFunction<T, U, R> extends BiFunction<T, U, R> {
        String name();
    }

    /**
     * Adds two Doubles
     */
    public static NamedBiFunction<Double, Double, Double> add = new NamedBiFunction<Double, Double, Double>() {

        @Override
        public Double apply(Double t, Double u) {
            return t + u;
        }

        @Override
        public String name() {
            return "add";
        }
    };

    /**
     * Subtracts two Doubles
     */
    public static NamedBiFunction<Double, Double, Double> subtract = new NamedBiFunction<Double, Double, Double>() {

        @Override
        public Double apply(Double t, Double u) {
            return t - u;
        };

        @Override
        public String name() {
            return "diff";
        };
    };

    /**
     * Multiplies two Doubles
     */
    public static NamedBiFunction<Double, Double, Double> multiply = new NamedBiFunction<Double, Double, Double>() {

        @Override
        public Double apply(Double t, Double u) {
            return t * u;
        };

        @Override
        public String name() {
            return "mult";
        };
    };

    /**
     * Divides two Doubles
     */
    public static NamedBiFunction<Double, Double, Double> divide = new NamedBiFunction<Double, Double, Double>() {

        @Override
        public Double apply(Double t, Double u) {
            if (u == 0)
                throw new ArithmeticException("Attempted Division By Zero.");

            return t / u;
        };

        @Override
        public String name() {
            return "div";
        };
    };

    /**
     * Applies a given list of bifunctions -- functions that take two arguments of a
     * certain type and produce a single instance of that type -- to a list of
     * arguments of that type. The functions are applied in an iterative manner, and
     * the result of each function is stored in the list in an iterative manner as
     * well, to be used by the next bifunction in the next iteration. For example,
     * given List<Double> args = Arrays.asList(1d, 1d, 3d, 0d, 4d), and
     * List<NamedBiFunction<Double, Double, Double>> bfs = [add, multiply, add,
     * divide], 2 CSE 216 : Programming Abstractions Homework III Submission
     * Deadline: Nov 28, 2021 <code>zip(args, bfs)</code> will proceed iteratively
     * as follows: - index 0: the result of add(1,1) is stored in args[1] to yield
     * args = [1,2,3,0,4] - index 1: the result of multiply(2,3) is stored in
     * args[2] to yield args = [1,2,6,0,4] - index 2: the result of add(6,0) is
     * stored in args[3] to yield args = [1,2,6,6,4] - index 3: the result of
     * divide(6,4) is stored in args[4] to yield args = [1,2,6,6,1.5]
     *
     * @param args:        the arguments over which <code>bifunctions</code> will be
     *                     applied.
     * @param bifunctions: the list of bifunctions that will be applied on
     *                     <code>args</code>.
     * @param <T>:         the type parameter of the arguments (e.g., Integer,
     *                     Double)
     * @return the item in the last index of <code>args</code>, which has the final
     *         result of all the bifunctions being applied in sequence.
     */
    public static <T> T zip(List<T> args, List<NamedBiFunction<T, T, T>> bifunctions) {
        /*
         * Applies the BiFunction at index i to the i and i + 1 element. We store the
         * result of the operation in the i + 1 element and then move on to that result.
         */
        IntStream.range(0, bifunctions.size())
                .forEach(i -> args.set(i + 1, bifunctions.get(i).apply(args.get(i), args.get(i + 1))));

        // Return the last element with the stored solution.
        return args.get(args.size() - 1);
    }

    static class FunctionComposition<T, U, R> {

        BiFunction<T, U, R> composition(BiFunction f, BiFunction g) {

        }

    }

    public static void main(String[] args) {
        List<Double> argz = Arrays.asList(1d, 1d, 3d, 0d, 4d);
        List<NamedBiFunction<Double, Double, Double>> biFunctions = Arrays.asList(add, multiply, add, divide);

        System.out.println(zip(argz, biFunctions));
    }
}
