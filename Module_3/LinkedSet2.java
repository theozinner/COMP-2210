import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides an implementation of the Set interface.
 * A doubly-linked list is used as the underlying data structure.
 * Although not required by the interface, this linked list is
 * maintained in ascending natural order. In those methods that
 * take a LinkedSet as a parameter, this order is used to increase
 * efficiency.
 *
 * @author Dean Hendrix (dh@auburn.edu)
 * @author Theo Zinner (tvz0001@auburn.edu)

 * @version 2018-10-19
 *
 */
public class LinkedSet<T extends Comparable<? super T>> implements Set<T> {

    //////////////////////////////////////////////////////////
    // Do not change the following three fields in any way. //
    //////////////////////////////////////////////////////////

    /** References to the first and last node of the list. */
   Node front;
   Node rear;

    /** The number of nodes in the list. */    
   int size;


    /////////////////////////////////////////////////////////
    // Do not change the following constructor in any way. //
    /////////////////////////////////////////////////////////

    /**
    linked set constructor.
     */
   public LinkedSet() {
      front = null;
      rear = null;
      size = 0;
   }


    //////////////////////////////////////////////////
    // Public interface and class-specific methods. //
    //////////////////////////////////////////////////

    ///////////////////////////////////////
    // DO NOT CHANGE THE TOSTRING METHOD //
    ///////////////////////////////////////
    /**
     * Return a string representation of this LinkedSet.
     *
     * @return a string representation of this LinkedSet
     */
   @Override
   public String toString() {
      if (isEmpty()) {
         return "[]";
      }
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (T element : this) {
         result.append(element + ", ");
      }
      result.delete(result.length() - 2, result.length());
      result.append("]");
      return result.toString();
   }


    ///////////////////////////////////
    // DO NOT CHANGE THE SIZE METHOD //
    ///////////////////////////////////
    /**
     * Returns the current size of this collection.
     *
     * @return  the number of elements in this collection.
     */
   public int size() {
      return size;
   }

    //////////////////////////////////////
    // DO NOT CHANGE THE ISEMPTY METHOD //
    //////////////////////////////////////
    /**
     * Tests to see if this collection is empty.
     *
     * @return  true if this collection contains no elements, false otherwise.
     */
   public boolean isEmpty() {
      return (size == 0);
   }


    /**
     * Ensures the collection contains the specified element. Neither duplicate
     * nor null values are allowed. This method ensures that the elements in the
     * linked list are maintained in ascending natural order.
     *
     *   1) First check the size to create the first element.
     *   2) Then check the rear and the front to see if they need to be added there.
     *         This way if the input is already sorted it will be linear time complexity.
     *   3) Finaly iterate through the linked nodes to find the node after and before. Then add inbetween.
     *
     * @param  element  The element whose presence is to be ensured.
     * @return true if collection is changed, false otherwise.
     */
     
   public boolean add(T element) { 
      if (element == null) {
         return false;
      }
      Node n = new Node(element);
      if (isEmpty()) { //first node
         front = n;
         rear = n;
      }
      else if (n.element.compareTo(rear.element) > 0) { //rear
         rear.next = n;
         n.prev = rear;
         rear = n;
      }
      else if (n.element.compareTo(front.element) < 0) { //front
         front.prev = n;
         n.next = front;
         front = n;
      }
      else { //middle
         Node post = front;
         while ((post.element.compareTo(n.element) < 0)) {
            post = post.next;
         }
         if (n.element.compareTo(post.element) == 0) {
            return false;
         }
         Node ante = post.prev;
         ante.next = n;
         post.prev = n;
         n.prev = ante;
         n.next = post;
      }
      size++; 
      return true;
   }

    /**
     * Ensures the collection does not contain the specified element.
     * If the specified element is present, this method removes it
     * from the collection. This method, consistent with add, ensures
     * that the elements in the linked lists are maintained in ascending
     * natural order.
     *
     *   1) First check the size and possibly return false.
     *   2) Then check the rear and the front to see if they need to be removed there.
     *         This way if the input is already sorted it will be linear time complexity.
     *   3) Finaly iterate through the linked nodes to find the node after and before. Then remove inbetween.
     *   4) Remember to subtract from size.
     *
     * @param   element  The element to be removed.
     * @return  true if collection is changed, false otherwise.
     */
   public boolean remove(T element) {
      if (element == null) { //null
         return false;
      }
      if (isEmpty()) { //empty
         return false;
      }
      if (size == 1) {
         if (element.compareTo(front.element) == 0 ) {
            front = null; //size 1
            rear = null;
            size--;
            return true;
         }
         return false;
      }
      if (element.compareTo(front.element) == 0) { //front
         front = front.next;
         front.prev = null;
         size--;
         return true;
      }
      if (element.compareTo(rear.element) == 0) { //rear
         rear = rear.prev;
         rear.next = null;
         size--;
         return true;
      }
      Node n = front;
      while (n.next != null) { //middle
         if (n.element.compareTo(element) == 0) {
            Node ante = n.prev;
            Node post = n.next;
            ante.next = post;
            post.prev = ante;
            size--;
            return true;  
         }
         n = n.next;
      }
      return false;
   }


    /**
     * Searches for specified element in this collection.
     *
     *   1) First check the size and possibly return false.
     *   2) Then check the front on size 1.
     *   2) Finaly iterate through the linked nodes to find the node.
     *
     * @param   element  The element whose presence in this collection is to be tested.
     * @return  true if this collection contains the specified element, false otherwise.
     */
   public boolean contains(T element) {
      if (element == null) { //null
         return false;
      }
      if (size == 0) {
         return false;
      }
      Node n = front;
      if (size == 1) {
         if (n.element.compareTo(element) == 0) {
            return true;
         }
         return false;
      }
      while (n != null) { //middle
         if (n.element.compareTo(element) == 0) {
            return true;
         }
         n = n.next;
      }
      return false;
   }


    /**
     * Tests for equality between this set and the parameter set.
     * Returns true if this set contains exactly the same elements
     * as the parameter set, regardless of order.
     *
     *   1) Check if size is equal. If not they cannot be equal.
     *   2) First put parameter into linkedSet. this should be n^2 / 2.
     *   3) Then use the linked set method equals. This method is linear.
     *
     * @return  true if this set contains exactly the same elements as
     *               the parameter set, false otherwise
     */
   public boolean equals(Set<T> s) {
      if ((s.size() != this.size)) {
         return false;
      }
      Iterator<T> itr = s.iterator();
      LinkedSet output = new LinkedSet();
      while (itr.hasNext()) {
         output.add(itr.next());
      }
      return equals(output);
   }


    /**
     * Tests for equality between this set and the parameter set.
     * Returns true if this set contains exactly the same elements
     * as the parameter set, regardless of order.
     *
     *   1) Check if size is equal. If not they cannot be equal.
     *   2) Make two nodes starting at each linked set
     *   3) Iterator through each node and check for equality.
     *
     * @return  true if this set contains exactly the same elements as
     *               the parameter set, false otherwise
     */
   public boolean equals(LinkedSet<T> s) {
      if ((s.size != this.size)) {
         return false;
      }
      Node n = this.front;
      Node m = s.front;
      while (n != null) {
         if (n.element.compareTo(m.element) != 0) {
            return false;
         }
         n = n.next;
         m = m.next;
      }
      return true;
   }


    /**
     * Returns a set that is the union of this set and the parameter set.
     *
     *   1) If the parameter empty return this.
     *   2) First put parameter into linkedSet. this should be n^2 / 2.
     *   3) Then use the linked set method union. This method is linear M + N.
     *
     * @return  a set that contains all the elements of this set and the parameter set
     */
   public Set<T> union(Set<T> s) {
      if (s == null) {
         return this;
      }
      Iterator<T> itr = s.iterator();
      LinkedSet output = new LinkedSet();
      while (itr.hasNext()) {
         output.add(itr.next());
      }
      return union(output);
   }


    /**
     * Returns a set that is the union of this set and the parameter set.
     *
     *   1) If the parameter empty return this.
     *   2) Create nodes for each linked set.
     *   3) Compare element in n and m then add the smallest and move to the next node. Repeat.
     *   4) If one set has no more elements add all the elements from the other set.
     *   5) This method does use add but by adding to the end it is linear.
     *
     * @return  a set that contains all the elements of this set and the parameter set
     */
   public Set<T> union(LinkedSet<T> s) {
      if (s == null) {
         return this;
      }
      Node n = this.front;
      Node m = s.front;
      LinkedSet output = new LinkedSet();
      while ((n != null) && (m != null)) {
         int cmp = n.element.compareTo(m.element);
         if (cmp <= 0) {
            output.add(n.element);
            n = n.next;
         }
         else {
            output.add(m.element);
            m = m.next;
         }  
      }
      while ((n != null) && (m == null)) {
         output.add(n.element);
         n = n.next;
      }
      while ((n == null) && (m != null)) {
         output.add(m.element);
         m = m.next;
      }
      return output;
   }


    /**
     * Returns a set that is the intersection of this set and the parameter set.
     *
     *   1) If the parameter empty return null set.
     *   2) First put parameter into linkedSet. this should be n^2 / 2.
     *   3) Then use the linked set method intersection. This method is linear M + N.
     *
     * @return  a set that contains elements that are in both this set and the parameter set
     */
   public Set<T> intersection(Set<T> s) {
      LinkedSet output = new LinkedSet();
      if (s == null) {
         return this;
      }
      Iterator<T> itr = s.iterator();
      while (itr.hasNext()) {
         output.add(itr.next());
      }
      return intersection(output);
   }

    /**
     * Returns a set that is the intersection of this set and
     * the parameter set.
     *
     *   1) If the parameter empty return null set.
     *   2) Create nodes for each linked set.
     *   3) Compare element in n and m. If they are equal add. Otherwise increment the smaller of the two.
     *   4) If one set has no more elements there are no more intersecting elements.
     *   5) This method does use add but by adding to the end it is linear because it always adds to the rear.
     *
     * @return  a set that contains elements that are in both
     *            this set and the parameter set
     */
   public Set<T> intersection(LinkedSet<T> s) {
      LinkedSet output = new LinkedSet();
      if (s.size == 0) {
         return output;
      }
      Node n = this.front;
      Node m = s.front;
      while ((n != null) && (m != null)) {
         int cmp = n.element.compareTo(m.element);
         if (cmp == 0) {
            output.add(n.element);
            n = n.next;
            m = m.next;
         }
         if (cmp < 0) {
            n = n.next;
         }
         if (cmp > 0) {
            m = m.next;
         }
      }
      return output;
   }



    /**
     * Returns a set that is the complement of this set and the parameter set.
     *
     *   1) If the parameter empty return this set.
     *   2) Create iterator and check if either set has the same element. If not add to the output.
     *   3) This method does use add but by adding to the end it is linear because it always adds to the rear.
     * @return  a set that contains elements that are in this set but not the parameter set
     */
   public Set<T> complement(Set<T> s) {
      if (s == null) {
         return this;
      }
      LinkedSet output = new LinkedSet();
      Node n = this.front;
      while (n != null) {
         Iterator<T> itr = s.iterator();
         boolean eq = false;
         while (itr.hasNext()) {
            int cmp = n.element.compareTo(itr.next());
            if (cmp == 0) {
               eq = true;
               break;
            }
         }
         if (!eq) {
            output.add(n.element);
         }
         n = n.next;
      }
      return output;
   }


    /**
     * Returns a set that is the complement of this set and
     * the parameter set.
     *
     *   1) If the parameter empty return this set.
     *   2) Create nodes for each linked set.
     *   3) Compare element in n and m. If they are equal do not add. Otherwise increment the smaller of the two and add it.
     *   4) If one set has no more add the rest from the other.
     *   5) This method does use add but by adding to the end it is linear because it always adds to the rear.
     *
     * @return  a set that contains elements that are in this
     *            set but not the parameter set
     */
   public Set<T> complement(LinkedSet<T> s) {
      if (s == null) {
         return this;
      }
      
      LinkedSet output = new LinkedSet();
      Node n = this.front;
      Node m = s.front;
      while ((n != null) && (m != null)) {
         int cmp = n.element.compareTo(m.element);
         if (cmp == 0) {
            n = n.next;
            m = m.next;
         }
         if (cmp < 0) {
            output.add(n.element); //add to rear
            n = n.next;
         }
         if (cmp > 0) {
            output.add(m.element); //add to rear
            m = m.next;
         }
      }
      while ((n != null) && (m == null)) {
         output.add(n.element);
         n = n.next;
      }
      return output;
   }

    /**
     * Returns an iterator over the elements in this LinkedSet.
     * Elements are returned in ascending natural order.
     *
     * @return  an iterator over the elements in this LinkedSet
     */
   public Iterator<T> iterator() {
      return new LinkedIterator();
   }


    /**
     * Returns an iterator over the elements in this LinkedSet.
     * Elements are returned in descending natural order.
     *
     * @return  an iterator over the elements in this LinkedSet
     */
   public Iterator<T> descendingIterator() {
      return new LinkedIteratorR();
   }


    /**
     * Returns an iterator over the members of the power set
     * of this LinkedSet. No specific order can be assumed.
     *
     * @return  an iterator over members of the power set
     */
   public Iterator<Set<T>> powerSetIterator() {  
      if (q > (int) Math.pow(2, size) - 1) {
         q = 0;
      }
      return new PowerIterator();
   }
   
    //////////////////////////////
    // Private utility fields.  //
    //////////////////////////////
   private int q = 0;


    //////////////////////////////
    // Private utility methods. //
    //////////////////////////////
       
    // Feel free to add as many private methods as you need.

    ////////////////////
    // Nested classes //
    ////////////////////
    
    
   private class LinkedIterator
      implements Iterator<T> {
      private Node current = front;
      
      public boolean hasNext() {
         return current != null;
      }
      
      public void remove() {
      }
      
      public T next() throws NoSuchElementException {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         T result = current.element;
         current = current.next;
         return result;
      }
   }
      
   private class LinkedIteratorR
      implements Iterator<T> {
      private Node current = rear;
      
      public boolean hasNext() {
         return current != null;
      }
      
      public void remove() {
      }
      
      public T next() throws NoSuchElementException {
         if (!hasNext()) {
            throw new NoSuchElementException();
         }   
         T result = current.element;
         current = current.prev;
         return result;
      }
   }

   private class PowerIterator
      implements Iterator<Set<T>> {
      int n = (int) Math.pow(2, size) - 1;

      public boolean hasNext() throws NoSuchElementException {
         if (((size > 0) && (q <= n)) || (q == 0)) {
            return true;
         }
         else {
            return false;
         }
      }
   
      public LinkedSet next() throws NoSuchElementException {
         LinkedSet result = new LinkedSet();
         if (!hasNext()) {
            throw new NoSuchElementException();
         }
         if (q == 0) {
            q++;
            return result;
         }

         Iterator<T> itr = iterator();
         String bin = Integer.toBinaryString(q);
         for (int i = bin.length() - 1; i >= 0; i--) {
            T thing = itr.next();
            char p = bin.charAt(i);
            if (p == '1') {
               result.add(thing);
            }
         }
         q++;
         return result;
      }
   }
   

      

    //////////////////////////////////////////////
    // DO NOT CHANGE THE NODE CLASS IN ANY WAY. //
    //////////////////////////////////////////////

    /**
     * Defines a node class for a doubly-linked list.
     */
   class Node {
        /** the value stored in this node. */
      T element;
        /** a reference to the node after this node. */
      Node next;
        /** a reference to the node before this node. */
      Node prev;

        /**
         * Instantiate an empty node.
         */
      public Node() {
         element = null;
         next = null;
         prev = null;
      }

        /**
         * Instantiate a node that containts element
         * and with no node before or after it.
         */
      public Node(T e) {
         element = e;
         next = null;
         prev = null;
      }
   }
}