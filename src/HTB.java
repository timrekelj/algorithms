public class HTB {
    private final int multiplier;
    private final int const1;
    private final int const2;
    private int tableSize;

    private int collisions;
    private boolean isColliding;
    private int currentCollisions;

    private HTBElement[] table;

    public HTB(int p, int m, int c1, int c2) {
        this.multiplier = p;
        this.const1 = c1;
        this.const2 = c2;

        this.table = new HTBElement[m];
        this.tableSize = 0;

        this.collisions = 0;
        this.isColliding = false;
        this.currentCollisions = 0;
    }

    public void insert(int value) {
        if (tableSize != 0 && containsCollisions(value)) return;

        int hash = hash(value);
        table[hash] = new HTBElement(hash, value);

        tableSize++;
        isColliding = false;
        currentCollisions = 0;
    }

    public boolean find(int key) {
        int hash = hash(key);

        for (HTBElement element : table)
            if (element != null && element.hashEquals(hash))
                return true;

        return false;
    }

    public void delete(int key) {
        if (!find(key))
            return;

        table[hash(key)] = null;
    }

    public void printKeys() {
        for (int i = 0; i < table.length; i++)
            if (table[i] != null)
                System.out.println(i + ": " + table[i].value);
    }

    public void printCollisions() {
        System.out.println(collisions);
    }

    private int hash(int key) {
        if (isColliding)
            return (((key * multiplier) % table.length) + (const1 * currentCollisions) + (const2 * currentCollisions * currentCollisions)) % table.length;
        return (key * multiplier) % table.length;
    }

    private boolean containsCollisions(int key) {
        if (find(key)) {
            if (table[hash(key)].value == key) {
                collisions -= currentCollisions;
                return true;
            }
            if (currentCollisions == table.length) resize();
            else {
                isColliding = true;
                currentCollisions++;
                collisions++;
            }
            containsCollisions(key);
        }
        return false;
    }

    private void resize() {
        HTB newTable = new HTB(multiplier, table.length * 2 + 1, const1, const2);
        newTable.collisions = collisions;

        for (HTBElement hashElement : table) {
            if(hashElement != null) {
                newTable.insert(hashElement.value);
            }
        }

        table = newTable.table;
        tableSize = 0;
        currentCollisions = 0;
        collisions = newTable.collisions;
        isColliding = false;
    }
}

class HTBElement {
    public int hash;
    public int value;

    public HTBElement(int hash, int value) {
        this.hash = hash;
        this.value = value;
    }

    public boolean hashEquals(int hash) {
        return this.hash == hash;
    }
}