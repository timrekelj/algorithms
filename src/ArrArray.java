public class ArrArray {
    private ArrArrayElement[][] arrArr;
    private int size;
    private int[] isFull;

    public ArrArray() {
        this.size = 1;
        this.arrArr = new ArrArrayElement[size][];
        this.isFull = new int[size];
        isFull[0] = 0;
    }
    public void insert(int value) {
        ArrArrayElement[] tempEl = new ArrArrayElement[1];
        tempEl[0] = new ArrArrayElement(value);

        for (int i = 0; i < size; i++) {
            if (arrArr[i] == null) {
                arrArr[i] = new ArrArrayElement[(int)Math.pow(2,i)];
            }

            // if it exists
            int[] exists = exists(tempEl[0]);
            if (exists[0] != -1) {
                arrArr[exists[0]][exists[1]].count++;
                return;
            }
            // if it is empty
            else if (isFull[i] == 0) {
                fill(tempEl[0], i);
                return;
            }

            for (int j = 0; j < arrArr[i].length; j++) {
                if (arrArr[i][j].isDeleted) {
                    arrArr[i][j] = tempEl[0];
                    arrArr[i][j].isDeleted = false;
                    quickSort(arrArr[i], 0, arrArr[i].length - 1);
                    return;
                }
            }

            if (i == size - 1) {
                grow(tempEl);
                return;
            }
        }
    }
    public void delete(int value) {
        int[] exists = exists(new ArrArrayElement(value));
        if (exists[0] == -1) {
            System.out.println("false");
            return;
        }
        if (arrArr[exists[0]][exists[1]].count > 1) {
            arrArr[exists[0]][exists[1]].count--;
            System.out.println("true");
            return;
        }
        arrArr[exists[0]][exists[1]].isDeleted = true;
        System.out.println("true");

        for (int i = exists[0]; i >= 0; i--) {
            for (int j = 0; j < arrArr[i].length; j++) {
                if (arrArr[i][j] != null && !arrArr[i][j].isDeleted)
                    return;
            }
            isFull[i] = 0;
            arrArr[i] = null;
        }
    }
    private void grow(ArrArrayElement[] el) {
        ArrArrayElement[][] tempArr = new ArrArrayElement[size + 1][];
        int[] tempFull = new int[size + 1];

        System.arraycopy(arrArr, 0, tempArr, 0, arrArr.length);
        arrArr = tempArr;

        System.arraycopy(isFull, 0, tempFull, 0, isFull.length);
        isFull = tempFull;

        arrArr[size] = new ArrArrayElement[(int)Math.pow(2,size)];
        size++;

        insert(el[0].value);
    }
    private void fill(ArrArrayElement el, int index) {
        int count = 0;
        if (index == 0) {
            arrArr[0][0] = el;
            isFull[0] = 1;
            return;
        }
        for (int i = index - 1; i >= 0; i--) {
            System.arraycopy(arrArr[i], 0, arrArr[index], count, arrArr[i].length);
            arrArr[i] = new ArrArrayElement[(int)Math.pow(2,i)];
            isFull[i] = 0;
            count += arrArr[i].length;
        }
        arrArr[index][count] = el;
        isFull[index] = 1;
        quickSort(arrArr[index], 0, arrArr[index].length - 1);
    }
    private void quickSort(ArrArrayElement[] arr, int left, int right) {
        if (left >= right)
            return;

        int pivot = partition(arr, left, right);
        quickSort(arr, left, pivot - 1);
        quickSort(arr, pivot + 1, right);
    }
    private int partition(ArrArrayElement[] arr, int left, int right) {
        int pivot = arr[right].value;
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (arr[j].value < pivot) {
                i++;
                ArrArrayElement temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        ArrArrayElement temp = arr[i + 1];
        arr[i + 1] = arr[right];
        arr[right] = temp;
        return i + 1;
    }
    private int[] exists(ArrArrayElement el) {
        for (int i = 0; i < this.size; i++) {
            // if it is empty
            if (isFull[i] == 0)
                continue;

            // if it is min or max
            if (el.value == arrArr[i][0].value && !arrArr[i][0].isDeleted)
                return new int[] {i, 0};

            if (el.value == arrArr[i][arrArr[i].length-1].value && !arrArr[i][arrArr[i].length-1].isDeleted)
                return new int[] {i, arrArr[i].length - 1};

            // if it is in the middle
            if (el.value > arrArr[i][0].value || el.value < arrArr[i][arrArr[i].length - 1].value) {
                int left = 0;
                int right = arrArr[i].length - 1;
                int mid = (left + right) / 2;
                for (int j = 0; j < arrArr[i].length; j++) {
                    if (arrArr[i][mid].value == el.value && !arrArr[i][mid].isDeleted) {
                        return new int[] {i, mid};
                    } else if (arrArr[i][mid].value < el.value) {
                        left = mid + 1;
                    } else {
                        right = mid - 1;
                    }
                    mid = (left + right) / 2;
                }
            }
        }
        return new int[] {-1, -1};
    }
    public void printOut() {
        if (arrArr[0] == null) {
            System.out.println("empty");
            return;
        }

        for (int i = 0; i < size; i++) {
            if (isFull[i] == 0) {
                System.out.println("A_" + i + ": ...");
                continue;
            }
            System.out.print("A_" + i + ":");

            for (int j = 0; j < arrArr[i].length; j++) {
                if (arrArr[i][j].isDeleted) {
                    System.out.print(" " + "x" + ((j == arrArr[i].length - 1) ? "" : ","));
                    continue;
                }
                System.out.print(" " + arrArr[i][j].value + "/" + arrArr[i][j].count + ((j == arrArr[i].length - 1) ? "" : ","));
            }
            System.out.println();
        }
    }
    public void find(int value) {
        if (exists(new ArrArrayElement(value))[0] != -1)
            System.out.println("true");
        else
            System.out.println("false");
    }
}

class ArrArrayElement {
    public int value;
    public int count;
    public boolean isDeleted;

    public ArrArrayElement(int e) {
        this.value = e;
        this.count = 1;
        this.isDeleted = false;
    }
}