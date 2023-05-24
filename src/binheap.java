public class binheap {
    int size;
    int[] heap;
    int comparisons;

    public binheap() {
        this.size = 0;
        this.heap = new int[1];
        this.comparisons = 0;
    }

    public void insert(int key) {
        heap[size] = key;
        siftUp(size);
        size++;

        if (size == heap.length)
            System.arraycopy(heap, 0, new int[heap.length * 2], 0, heap.length);
    }

    public void deleteMin() {
        if (size == 0) {
            System.out.println("false");
            return;
        }
        
        System.out.println("true: " + heap[0]);
        heap[0] = heap[size - 1];
        size--;

        siftDown(0);
    }

    public void printElements() {
        if (size == 0) {
            System.out.println("empty");
            return;
        }
        for (int i = 0; i < size; i++)
            System.out.print(heap[i] + (i != size - 1 ? ", " : ""));
        System.out.println();
    }
    
    public void printMin() { System.out.println("MIN: " + (size == 0 ? "none" : heap[0])); }

    public void printComparisons() { System.out.println("COMPARISONS: " + comparisons); }

    private void siftUp(int index) {
        if (index == 0 || heap[index] == heap[(index - 1) / 2]) return;
        comparisons++;
        if (heap[index] < heap[(index - 1) / 2]) {
            int temp = heap[index];
            heap[index] = heap[(index - 1) / 2];
            heap[(index - 1) / 2] = temp;
            siftUp((index - 1) / 2);
        }
    }

    private void siftDown(int index) {
        int rightChildIndex = 2 * index + 2;
        int leftChildIndex = 2 * index + 1;
        int minIndex = index;

        if (index == 0 && size >= 3) {
            comparisons++;
            if (heap[leftChildIndex] <= heap[rightChildIndex])
                minIndex = leftChildIndex;
            else
                minIndex = rightChildIndex;
        } else {
            if (leftChildIndex < size) {
                comparisons++;
                if (heap[leftChildIndex] < heap[minIndex])
                    minIndex = leftChildIndex;
            }

            if (rightChildIndex < size) {
                comparisons++;
                if (heap[rightChildIndex] < heap[minIndex])
                    minIndex = rightChildIndex;
            }
        }

        if (minIndex != index) {
            int temp = heap[index];
            heap[index] = heap[minIndex];
            heap[minIndex] = temp;
            siftDown(minIndex);
        }
    }
}