package RBTree;

public class RedBlackTree<Key extends Comparable<Key>> {	
		private static int counter = 0;
		private static RedBlackTree.Node<String> root;

		public static class Node<Key extends Comparable<Key>> { //changed to static 
			
			  Key key;  		  
			  Node<String> parent;
			  Node<String> leftChild;
			  Node<String> rightChild;
			  boolean isRed; 
			  int color; // 0 is red, 1 is black
			  
			  public Node(Key data){
				  this.key = data;
				  leftChild = null;
				  rightChild = null;
			  }		
			  
			  public int compareTo(Node<Key> n){ 	//this < that  <0
			 		return key.compareTo(n.key);  	//this > that  >0
			  }
			  
			  public boolean isLeaf(){
				  if (this.equals(root) && this.leftChild == null && this.rightChild == null) return true;
				  if (this.equals(root)) return false;
				  if (this.leftChild == null && this.rightChild == null){
					  return true;
				  }
				  return false;
			  }
			  public String toString()
			  {
				  return (String)key;
			  }
		}

		 public boolean isLeaf(RedBlackTree.Node<String> n){
			 return n.isLeaf();
		  }
		
		public interface Visitor<Key extends Comparable<Key>> {
			/**
			This method is called at each node.
			@param n the visited node
			*/
			void visit(Node<Key> n);  
		}
		
		public String getRoot()
		{
			return (String)root.key;
		}
		
		public void visit(Node<Key> n){
			System.out.println(n.key);
		}
		
		public void printTree(){  //preorder: visit, go left, go right
			RedBlackTree.Node<String> currentNode = root;	
			printTree(currentNode);
		}
		
		public void printTree(RedBlackTree.Node<String> node){
			System.out.print(node+" ");
			if (node.leftChild != null){
				printTree(node.leftChild);
			}
			if(node.rightChild != null){
				printTree(node.rightChild);
			}
		}
		
		// place a new node in the RB tree with data the parameter and color it red. 
		public void addNode(String data){  	//this < that < 0 or this > that > 0
			RedBlackTree.Node<String> val = new RedBlackTree.Node<String>(data);
			val.isRed = true;
			val.color = 0; 
			if(root == null)
			{
				root = val;
			}
			else 
			{
				RedBlackTree.Node<String> currentNode = root;
				while(val.parent == null) // keep going until node is inserted into the tree, if it is inserted into the tree, then there will be a parent for the node
				{
					if(currentNode.rightChild == null && currentNode.compareTo(val) < 0) // at a leaf node and val is needed to be inserted as the right child
					{
						currentNode.rightChild = val; // setting the right child to val instead of null
						val.parent = currentNode; // sets the parent of val to a node, instead of null. ALso breaks out of loop
					}
					else if(currentNode.leftChild == null && currentNode.compareTo(val) > 0) // at a leaf node and val is needed to be inserted as the left child
					{
						currentNode.leftChild = val; // setting the left child to val instead of null
						val.parent = currentNode; // sets the parent of val to a node, instead of null. ALso breaks out of loop
					}
					else if(currentNode.compareTo(val) < 0) // interior nodes, keep going
					{
						currentNode = currentNode.rightChild;
					}
					else if(currentNode.compareTo(val) > 0) // interior nodes, keep going
					{
						currentNode = currentNode.leftChild;
					}
					counter++;
				}
			}
			fixTree(val); // recursively fix the tree to searching in tree O(log n)
		}	

		public void insert(String data){
			addNode(data);	
		}
		
		public RedBlackTree.Node<String> lookup(String k){ 
			RedBlackTree.Node<String> val = new RedBlackTree.Node<String>(k); // Node implements compareable interface, so strings will be compared. String already has a good compareTo method, no need to rewrite
			RedBlackTree.Node<String> currentNode = root;
			while(currentNode != null) // keep going until node is inserted into the tree, if it is inserted into the tree, then there will be a parent for the node
			{
				if(currentNode.compareTo(val) == 0)
					return currentNode;
				else if(currentNode.compareTo(val) < 0) // interior nodes, keep going
				{
					currentNode = currentNode.rightChild;
				}
				else if(currentNode.compareTo(val) > 0) // interior nodes, keep going
				{
					currentNode = currentNode.leftChild;
				}
			}
			return null;
		}
	 	
		
		public RedBlackTree.Node<String> getSibling(RedBlackTree.Node<String> n)
		{  
			if(n.parent == null)	// corner case of root
				return null;
			if(n.parent.rightChild == n)	// return the other sibling, will return null if there is no sibling
				return n.parent.leftChild;
			else
				return n.parent.rightChild;
		}
		
		
		public RedBlackTree.Node<String> getAunt(RedBlackTree.Node<String> n)
		{
			if(n.parent == null)
				return null;
			else 
			{
				return getSibling(n.parent); // reuse code already written
			}
		}
		
		public RedBlackTree.Node<String> getGrandparent(RedBlackTree.Node<String> n)
		{
			return n.parent.parent;	
		}
		
		public void rotateLeft(RedBlackTree.Node<String> n)
		{
			RedBlackTree.Node<String> makeNewLeftChild = getGrandparent(n); // the x part
			RedBlackTree.Node<String> makeNewParent = n.parent; // the y part

			// this is all just moving the pointers around, I did it according to the
			// pictures showed in class. It's hard to explain in words, but easy to show in picture format
			
			// move the pointers around
			makeNewLeftChild.rightChild = makeNewParent.leftChild; 
			if(makeNewParent.leftChild != null)
				makeNewParent.leftChild.parent = makeNewLeftChild;
			makeNewParent.parent = makeNewLeftChild.parent;
			if(makeNewLeftChild.parent == null)
				root = makeNewParent;
			else if(makeNewLeftChild == makeNewLeftChild.parent.leftChild)
				makeNewLeftChild.parent.leftChild = makeNewParent;
			else
				makeNewLeftChild.parent.rightChild = makeNewParent;
			makeNewParent.leftChild = makeNewLeftChild;
			makeNewLeftChild.parent = makeNewParent;
		}
		
		public void rotateRight(RedBlackTree.Node<String> n) //WORKS CORRECTLY
		{
			RedBlackTree.Node<String> rightSideToAdd = n.parent.rightChild; // the B part
			RedBlackTree.Node<String> makeNewParent = n.parent; // the x part
			RedBlackTree.Node<String> makeNewRightChild = getGrandparent(n); // the y part
			
			makeNewRightChild.leftChild = rightSideToAdd;
			if(rightSideToAdd != null)
				rightSideToAdd.parent = makeNewRightChild;
			makeNewParent.parent = makeNewRightChild.parent;
			if(n.parent.parent == null)
				root = makeNewParent;
			else if(n.parent.parent == n.parent.parent.leftChild)
				n.parent.parent.leftChild = makeNewParent;
			else
				n.parent.parent.rightChild = makeNewParent;
			makeNewParent.rightChild = makeNewRightChild;
			makeNewRightChild.parent = makeNewParent;
			
		}
		
		public void printTreeInOrder(RedBlackTree.Node<String> current)
		{
			if(current.leftChild != null)
				printTreeInOrder(current.leftChild);
			System.out.print(current.key+" ");
			if(current.rightChild != null)
				printTreeInOrder(current.rightChild);
		}
		
		public void fixTree(RedBlackTree.Node<String> current) 
		{
			if(current == root)
			{
				current.color = 1;
				current.isRed = false;
				return;
			}
			else if(current.parent.color == 1) // if parent is black, and child is red, then black height doesnt change, so quit
				return;
			if(current.color == 0 && current.parent.color == 0) //if the node and its parent's color is red and parent is red
			{
				RedBlackTree.Node<String> aunt = getAunt(current); // need to get the aunt
				if(aunt == null || aunt.color == 1)
				{
					if(current == current.parent.rightChild && current.parent.parent.leftChild == current.parent) // case 1
					{
						RedBlackTree.Node<String> fixer = current.parent;
						rotateRight(current);
						fixTree(fixer);
					}
					else if(current == current.parent.leftChild && current.parent.parent.rightChild == current.parent) // case 2
					{
						RedBlackTree.Node<String> fixer = current.parent;
						rotateLeft(current);
						fixTree(fixer);
					}
					else if(getGrandparent(current).leftChild == current.parent && current == current.parent.leftChild) // case 3
					{
						current.parent.color = 1;
						current.parent.parent.color = 0;
						current.parent.parent.isRed = true;
						if(getGrandparent(current.parent)!=null)
							rotateRight(current);
						else
							rotateRight(current);
						return;
					}
					else if(getGrandparent(current).rightChild == current.parent && current == current.parent.rightChild) // case 4
					{
						current.parent.color = 1;
						current.parent.parent.color = 0;
						current.parent.parent.isRed = true;
						if(getGrandparent(current.parent)!=null)
							rotateLeft(current);
						else
							rotateLeft(current);
						return;
					}
				}
				else if(aunt.color == 0) // change the color of the parent and aunt and node (push blackness down the tree)
				{
					current.parent.color = 1;
					aunt.color = 1;
					current.parent.parent.color = 0;
					current.parent.parent.isRed = true;
					fixTree(current.parent.parent);
				}
			}
		}
		
		public boolean isEmpty(RedBlackTree.Node<String> n){
			if (n.key == null){
				return true;
			}
			return false;
		}
		 
		public boolean isLeftChild(RedBlackTree.Node<String> parent, RedBlackTree.Node<String> child)
		{
			if (child.compareTo(parent) < 0 ) {//child is less than parent
				return true;
			}
			return false;
		}

		public void preOrderVisit(Visitor<String> v) {
		   	preOrderVisit(root, v);
		}
		 
		 
		private static void preOrderVisit(RedBlackTree.Node<String> n, Visitor<String> v) {
		  	if (n == null) {
		  		return;
		  	}
		  	v.visit(n);
		  	preOrderVisit(n.leftChild, v);
		  	preOrderVisit(n.rightChild, v);
		}

		public void removeTree() 
		{
			root = null;
		}	
	}

