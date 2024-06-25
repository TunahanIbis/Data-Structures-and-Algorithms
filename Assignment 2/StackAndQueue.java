import java.util.Scanner;

public class StackAndQueue {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Scanner to get user input for the stack or queue choice
        System.out.println("");
        System.out.println("Do you want to work with a Stack (Enter 1) or Queue (Enter 2)?");

        int stackOrQueueChoice = scanner.nextInt();
        scanner.nextLine();

        if (stackOrQueueChoice == 1) {
            System.out.println("--You've chosen the Stack--");
            ResizingArrayStack myStack = new ResizingArrayStack();
            performStackOperations(scanner, myStack);

        } else if (stackOrQueueChoice == 2) {
            System.out.println("--You've chosen the Queue--");
            ResizingArrayQueue myQueue = new ResizingArrayQueue();
            performQueueOperations(scanner, myQueue);

        } else {
            System.out.println("ERROR: Please enter 1 (Stack) or 2 (Queue) as a choice");
        }

        scanner.close();
    }

    // ******STACK AND QUEUE CODES******
    public static void performStackOperations(Scanner scanner, ResizingArrayStack stack) {
        boolean performOperations = true;

        while (performOperations) {
            System.out.println("Choose the operation: Push (Enter 1) or Pop (Enter 2) or Exit (Enter 3)");
            int operation = scanner.nextInt();

            switch (operation) {
                case 1:
                    System.out.println("Enter the string to push:");
                    String itemToPush = scanner.next();
                    stack.push(itemToPush);
                    printStack(stack);
                    break;
                case 2:
                    try {
                        String poppedItem = stack.pop();
                        System.out.println("Popped from stack: " + poppedItem);
                        printStack(stack);
                    } catch (IllegalStateException e) {
                        System.out.println("Stack is empty.");
                    }
                    break;
                case 3:
                    performOperations = false;
                    break;
                default:
                    System.out.println("ERROR: Please enter 1 (Push), 2 (Pop) or 3 (Exit) as a choice");
            }
        }
    }

    // Method for printing the current/updated stack
    public static void printStack(ResizingArrayStack stack) {
        if (!stack.isEmpty()) {
            System.out.println("Stack after the operation:");
            for (int i = stack.size - 1; i >= 0; i--) {
                System.out.print(stack.array[i] + " ");
            }
            System.out.println();
        } else {
            System.out.println("Stack is empty.");
        }
    }

    // Class to update the old variables and resizing the stack
    public static class ResizingArrayStack {
        private String[] array;
        private int size;

        public ResizingArrayStack() {
            array = new String[1];
            size = 0;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        private void resize(int newCapacity) {
            String[] newItems = new String[newCapacity];
            for (int i = 0; i < size; i++) {
                newItems[i] = array[i];
            }
            array = newItems;
        }

        // Pop method that removes an item from the top of the stack
        public String pop() {
            if (isEmpty()) {
                throw new IllegalStateException("Stack is empty.");
            }
            String item = array[--size]; // Get the top element and decrease the size
            array[size] = null; // Remove the top element by nullifying
            if (size > 0 && size == array.length / 4) { // Resize down the array if it's at a quarter of capacity
                resize(array.length / 2);
            }
            return item;
        }

        // Push method that adds an item to the top of the stack
        public void push(String item) {
            if (size == array.length) {
                resize(2 * array.length); // Double the array size if it's full
            }
            array[size++] = item;
        }
    }

    // Method for the Queue operations with the data coming from the user input
    public static void performQueueOperations(Scanner scanner, ResizingArrayQueue queue) {
        boolean performOperations = true;

        while (performOperations) {
            System.out.println("Choose the operation: Enqueue (Enter 1) or Dequeue (Enter 2) or Exit (Enter 3)");
            int operation = scanner.nextInt();

            switch (operation) {
                case 1:
                    System.out.println("Enter the string to enqueue:");
                    String itemToEnqueue = scanner.next();
                    queue.enqueue(itemToEnqueue);
                    printQueue(queue);
                    break;
                case 2:
                    try {
                        String dequeuedItem = queue.dequeue();
                        System.out.println("Dequeued from queue: " + dequeuedItem);
                        printQueue(queue);
                    } catch (IllegalStateException e) {
                        System.out.println("Queue is empty.");
                    }
                    break;
                case 3:
                    performOperations = false;
                    break;
                default:
                    System.out.println("ERROR: Please enter 1 (Enqueue), 2 (Dequeue) or 3 (Exit) as a choice");
            }
        }
    }

    // Method for printing the current/updated queue
    // Method for printing the current/updated queue
public static void printQueue(ResizingArrayQueue queue) {
    if (!queue.isEmpty()) {
        System.out.println("Queue after the operation:");
        for (int i = 0; i < queue.size; i++) {
            System.out.print(queue.array[i] + " ");
        }
        System.out.println();
    } else {
        System.out.println("Queue is empty.");
    }
}


    // Class to update the old variables and resizing the queue
    public static class ResizingArrayQueue {
        private String[] array;
        private int size;
    
        public ResizingArrayQueue() {
            array = new String[1];
            size = 0;
        }
    
        public boolean isEmpty() {
            return size == 0;
        }
    
        private void resize(int newCapacity) {
            String[] newItems = new String[newCapacity];
            for (int i = 0; i < size; i++) {
                newItems[i] = array[i];
            }
            array = newItems;
        }
    
        // Enqueue method that adds an element at the end of the queue
        public void enqueue(String item) {
            if (size == array.length) {
                resize(2 * array.length); // Double the array size if it's full
            }
            array[size++] = item;
        }
    
        // Dequeue method that removes an element from the top of the queue
        public String dequeue() {
            if (isEmpty()) {
                throw new IllegalStateException("Queue is empty.");
            }
            String item = array[0];
            for (int i = 1; i < size; i++) {
                array[i - 1] = array[i];
            }
            array[--size] = null; // Nullify the last element
            if (size > 0 && size == array.length / 4) {
                resize(array.length / 2); // Resize down the array if it's at a quarter of capacity
            }
            return item;
        }
    }
    
    
}
