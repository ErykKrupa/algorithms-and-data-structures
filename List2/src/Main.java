import java.io.*;
import java.util.*;

public class Main {
  private static Sort algorithm = null;
  private static Order order = Order.ASCEND;
  private static int length;
  private static int counter = -1;
  private static String fileName;
  private static int tests;
  private static boolean typeFlag = false;
  private static boolean orderFlag = false;
  private static boolean statFlag = false;

  private static boolean increaseCounter() {
    counter++;
    return counter >= length;
  }

  private static void inputValues() {
    List<Integer> list = new LinkedList<>();
    int amount;
    Scanner scanner = new Scanner(System.in);
    counter = 0;
    System.out.print("Enter amount of numbers to sort: ");
    try {
      amount = scanner.nextInt();
      if (amount < 1) {
        throw new IllegalArgumentException("Should be positive integer.");
      }
    } catch (InputMismatchException | IllegalArgumentException ex) {
      System.err.println("Should be positive integer.");
      return;
    }
    System.out.print("Enter numbers to sort: ");
    while (counter < amount) {
      try {
        list.add(scanner.nextInt());
        counter++;
      } catch (InputMismatchException ex) {
        System.err.println(scanner.next() + " is not integer.");
      }
    }

    algorithm.sort(list, order, false);
    for (int i = 0; i < list.size() - 1; i++) {
      if (algorithm.compare(list.get(i + 1), list.get(i)) > 0) {
        System.err.println("Sequence unsorted!");
        return;
      }
    }
    System.out.println("Sequence with " + list.size() + " elements:");
    for (int i = 0; i < list.size() - 1; i++) {
      System.out.print(list.get(i) + ", ");
    }
    System.out.print(list.get(list.size() - 1));
  }

  private static void generateValues() {
    String headline = ";;;;Comparision;;;;;;Swaps;;;;;;Time[ms]\n" +
            "Data size;;Selection;Insertion;Heap;Quick;Modified Quick;" +
            ";Selection;Insertion;Heap;Quick;Modified Quick;" +
            ";Selection;Insertion;Heap;Quick;Modified Quick;\n";
    double timeStart = System.nanoTime();
    Random random = new Random();
    Sort[] algorithms = new Sort[5];
    algorithms[0] = new SelectionSort();
    algorithms[1] = new InsertionSort();
    algorithms[2] = new HeapSort();
    algorithms[3] = new QuickSort();
    algorithms[4] = new ModifiedQuickSort();
    long[][] comparisonsSum = new long[5][100];
    long[][] swapsSum = new long[5][100];
    double[][] timeSum = new double[5][100];
    PrintWriter dataWriter;
    PrintWriter summaryWriter;
    try {
      dataWriter =
          new PrintWriter(
              new BufferedWriter(new FileWriter(tests + "_" + fileName + "_data.csv", true)));
    } catch (IOException ex) {
      ex.printStackTrace();
      return;
    }
    dataWriter.println(headline);
    for (int k = 0; k < tests; k++) {
      System.out.println(k + 1 + "/" + tests);
      dataWriter.println("k = " + (k + 1) + ":");
      for (int n = 100; n <= 10_000; n += 100) {

        ArrayList<ArrayList<Integer>> sequences = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
          sequences.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
          int randomInt = random.nextInt(200_001) - 100_000;
          for (int j = 0; j < 5; j++) {
            sequences.get(j).add(randomInt);
          }
        }
        for (int i = 0; i < 5; i++) {
          algorithms[i].sort(sequences.get(i), order, true);
          comparisonsSum[i][(n / 100) - 1] += algorithms[i].comparisons;
          swapsSum[i][(n / 100) - 1] += algorithms[i].swaps;
          timeSum[i][(n / 100) - 1] += algorithms[i].getTime();
        }
        dataWriter.print(n + ";;");
        for (int i = 0; i < 5; i++) {
          dataWriter.printf("%d;", algorithms[i].comparisons);
        }
        dataWriter.print(";");
        for (int i = 0; i < 5; i++) {
          dataWriter.printf("%d;", algorithms[i].swaps);
        }
        dataWriter.print(";");
        for (int i = 0; i < 5; i++) {
          dataWriter.printf("%f;", algorithms[i].getTime());
        }
        dataWriter.println("");
      }
      dataWriter.println("");
    }
    try {
      summaryWriter =
          new PrintWriter(
              new BufferedWriter(new FileWriter(tests + "_" + fileName + "_summary.csv", true)));
    } catch (IOException ex) {
      ex.printStackTrace();
      return;
    }
    dataWriter.println(headline);
    summaryWriter.println("Sums: ");
    for (int i = 0; i < 100; i++) {
      summaryWriter.print((i + 1) * 100 + ";;");
      for (int j = 0; j < 5; j++) {
        summaryWriter.printf("%d;", comparisonsSum[j][i]);
      }
      summaryWriter.print(";");
      for (int j = 0; j < 5; j++) {
        summaryWriter.printf("%d;", swapsSum[j][i]);
      }
      summaryWriter.print(";");
      for (int j = 0; j < 5; j++) {
        summaryWriter.printf("%f;", timeSum[j][i]);
      }
      summaryWriter.println("");
    }
    summaryWriter.println("");
    summaryWriter.println("Average values: ");
    for (int i = 0; i < 100; i++) {
      summaryWriter.print((i + 1) * 100 + ";;");
      for (int j = 0; j < 5; j++) {
        summaryWriter.printf("%d;", comparisonsSum[j][i] / tests);
      }
      summaryWriter.print(";");
      for (int j = 0; j < 5; j++) {
        summaryWriter.printf("%d;", swapsSum[j][i] / tests);
      }
      summaryWriter.print(";");
      for (int j = 0; j < 5; j++) {
        summaryWriter.printf("%f;", timeSum[j][i] / tests);
      }
      summaryWriter.println("");
    }
    dataWriter.close();
    summaryWriter.close();
    double timeStop = System.nanoTime();
    System.out.printf("%f seconds\n", ((timeStop - timeStart) / 1_000_000_000.0));
  }

  public static void main(String[] args) {
    length = args.length;
    if (increaseCounter()) {
      System.err.println("No parameters");
      return;
    }
    while (!(typeFlag && orderFlag && statFlag)) {
      if (!typeFlag && (args[counter].equals("--type") || args[counter].equals("-t"))) {
        if (increaseCounter()) {
          System.err.println("No enough parameters");
          return;
        }
        switch (args[counter]) {
          case "select":
          case "s":
            algorithm = new SelectionSort();
            break;
          case "insert":
          case "i":
            algorithm = new InsertionSort();
            break;
          case "heap":
          case "h":
            algorithm = new HeapSort();
            break;
          case "quick":
          case "q":
            algorithm = new QuickSort();
            break;
          case "modifiedquick":
          case "mquick":
          case "mq":
            algorithm = new ModifiedQuickSort();
            break;
          default:
            System.err.println("Incorrect parameter: " + args[counter]);
            return;
        }
        typeFlag = true;
        if (increaseCounter()) {
          break;
        }
      } else if (!orderFlag
          && (args[counter].equals("-d")
              || args[counter].equals("-a")
              || args[counter].equals("--asc")
              || args[counter].equals("--desc"))) {
        order = args[counter].equals("--asc") || args[counter].equals("-a") ? Order.ASCEND : Order.DESCEND;
        orderFlag = true;
        if (increaseCounter()) {
          break;
        }
      } else if (!statFlag && (args[counter].equals("--stat") || args[counter].equals("-s"))) {
        if (increaseCounter()) {
          System.err.println("No enough parameters");
          return;
        }
        fileName = args[counter];
        if (increaseCounter()) {
          System.err.println("No enough parameters");
          return;
        }
        try {
          tests = Integer.parseInt(args[counter]);
          if (tests < 1) {
            throw new IllegalArgumentException();
          }
        } catch (IllegalArgumentException ex) {
          System.err.println("Incorrect parameter: " + args[counter]);
          return;
        }
        statFlag = true;
        if (increaseCounter()) {
          break;
        }
      } else {
        System.err.println("Incorrect parameter: " + args[counter]);
        return;
      }
    }
    if (statFlag && !(typeFlag)) {
      generateValues();
    } else if (typeFlag && orderFlag && !statFlag) {
      inputValues();
    } else {
      System.err.println("Incorrect batch of parameters");
    }
  }
}
