import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ArraySorting {

    public static void main(String[] args) {

        System.out.println(" ");

        // Scanner to get user input for number of integers
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Please enter the number of integers in the array: ");
            int arrayLength = scanner.nextInt();
            int array[] = new int[arrayLength];

            // Scanner to get user input for the integers in the array
            System.out.println("Please enter the integers in the array:");
            for (int i = 0; i < arrayLength; i++) {
                array[i] = scanner.nextInt();
            }

            // Scanner to get user input for the sort or selection sort choice
            System.out.println(
                    "Choose which method do you want to sort the array with: Sort (Enter 1), Selection Sort (Enter 2) or Insertion Sort (Enter 3)");
            try (Scanner scanner2 = new Scanner(System.in)) {
                int choice = scanner2.nextInt();

                if (choice == 1) {
                    System.out.println("--You have chosen the Sort method--");
                }

                else if (choice == 2) {
                    System.out.println("--You have chosen the Selection Sort method--");
                }

                else if (choice == 3) {
                    System.out.println("--You have chosen the Insertion Sort method--");
                }

                else {
                    System.out.println(
                            "ERROR: Please enter 1 (Sort), 2 (Selection Sort) or 3 (Insertion Sort) as a choice");
                    System.exit(0);
                }

                // Scanner to get user input for the ascending or descending order choice
                System.out.println(
                        "Choose in what order you want to sort the array: Ascending (Enter 1) or Descending (Enter 2)");
                int order = scanner.nextInt();

                if (order == 1) {
                    System.out.println("--You have chosen the Ascending order--");
                }

                else if (order == 2) {
                    System.out.println("--You have chosen the Descending order--");
                }

                else {
                    System.out.println("ERROR: Please enter 1 (Ascending) or 2 (Descending) as an order");
                    System.exit(0);
                }

                // Print the unsorted array
                System.out.println(" ");
                System.out.println("***CONCLUSION***");
                System.out.println("Unsorted Array:");
                for (int m = 0; m < array.length; m++) {
                    System.out.print(array[m] + " ");
                }
                System.out.println();

                // Print the sorting outputs
                if (choice == 1) {
                    if (order == 1) {
                        ArraysAscending(array);
                    } else if (order == 2) {
                        ArraysDescending(array);
                    }
                } else if (choice == 2) {
                    if (order == 1) {
                        ArrayAscending2(array);
                    } else if (order == 2) {
                        ArrayDescending2(array);
                    }
                }

                else if (choice == 3) {
                    if (order == 1) {
                        ArrayAscending3(array);
                    } else if (order == 2) {
                        ArrayDescending3(array);
                    }
                    System.out.println(" ");
                }
            }
        }
    }

    // ***** CODES *****

    // Sorted Array in Ascending Order by Sort method
    public static void ArraysAscending(int[] array) {
        int temp = 0;
        for (int m = 0; m < array.length; m++) {
            for (int n = m + 1; n < array.length; n++) {
                if (array[n] < array[m]) {
                    temp = array[m];
                    array[m] = array[n];
                    array[n] = temp;
                }
            }
        }
        System.out.println("Sorted Array in Ascending Order by Sort method:");
        for (int m = 0; m < array.length; m++) {
            System.out.print(array[m] + " ");
        }
        System.out.println();
    }

    // Sorted Array in Descending Order by Sort method
    public static void ArraysDescending(int[] array) {
        int temp = 0;
        for (int m = 0; m < array.length; m++) {
            for (int n = m + 1; n < array.length; n++) {
                if (array[n] > array[m]) {
                    temp = array[m];
                    array[m] = array[n];
                    array[n] = temp;
                }
            }
        }
        System.out.println("Sorted Array in Descending Order by Sort method:");
        for (int m = 0; m < array.length; m++) {
            System.out.print(array[m] + " ");
        }
        System.out.println();
    }

    // Sorted Array in Ascending Order by Selection Sort method
    public static void ArrayAscending2(int[] array) {
        int n = array.length;
        for (int step = 0; step < n - 1; step++) {
            int min_idx = step;
            for (int i = step + 1; i < n; i++) {
                if (array[i] < array[min_idx]) {
                    min_idx = i;
                }
            }
            int temp = array[step];
            array[step] = array[min_idx];
            array[min_idx] = temp;
        }
        System.out.println("Sorted Array in Ascending Order by Selection Sort method:");
        for (int m = 0; m < array.length; m++) {
            System.out.print(array[m] + " ");
        }
        System.out.println();
    }

    // Sorted Array in Descending Order by Selection Sort method
    public static void ArrayDescending2(int[] array) {
        int n = array.length;
        for (int step = 0; step < n - 1; step++) {
            int max_idx = step;
            for (int i = step + 1; i < n; i++) {
                if (array[i] > array[max_idx]) {
                    max_idx = i;
                }
            }
            int temp = array[step];
            array[step] = array[max_idx];
            array[max_idx] = temp;
        }
        System.out.println("Sorted Array in Descending Order by Selection Sort method:");
        for (int m = 0; m < array.length; m++) {
            System.out.print(array[m] + " ");
        }
        System.out.println();
    }

    // Sorted Array in Ascending Order by Insertion Sort method
    public static void ArrayAscending3(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int j = i - 1;

            // Move the elements that are greater than key to one index further
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
        System.out.println("Sorted Array in Ascending Order by Insertion Sort method:");
        for (int m = 0; m < array.length; m++) {
            System.out.print(array[m] + " ");
        }
        System.out.println();
    }

    // Sorted Array in Descending Order by Insertion Sort method
    public static void ArrayDescending3(int[] array) {
        int n = array.length;
        for (int i = 1; i < n; i++) {
            int key = array[i];
            int j = i - 1;

            // Move the elements that are less than key to one index further
            while (j >= 0 && array[j] < key) {
                array[j + 1] = array[j];
                j = j - 1;
            }
            array[j + 1] = key;
        }
        System.out.println("Sorted Array in Descending Order by Insertion Sort method:");
        for (int m = 0; m < array.length; m++) {
            System.out.print(array[m] + " ");
        }
        System.out.println();

    }
}