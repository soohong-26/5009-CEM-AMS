#include <iostream>


#ifndef BST_H
#define BST_H

struct TreeNode {
	int item;
	TreeNode *leftChild;
	TreeNode *rightChild;
};

class BST {
	private:	
		TreeNode *root;	//pointer to the root node
		int numOfItems;	//number of node in the tree
		void inOrder (TreeNode *node);
		void levelOrder() ;
		       
       
	public :
		BST ();	
		int size ();	
		bool search (int data);
		void insert (int data);
		void remove (int data);		
		void displayBST ();
	
};


#endif
