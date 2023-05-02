#include "BST.h"
#include <queue>


//Initiate an empty Binary Search tree
BST::BST()
{
    root = NULL;
    numOfItems = 0;
}

//Return the number of nodes in Binary Search tree
int BST::size()
{
    return numOfItems;
}


//Search for the node that store data. Return true if found,
//Otherwise, return false
bool BST::search (int data)
{
    if (root == NULL)
        return false;
    TreeNode *cur = root;
    while (cur->item != data) {
       if (data < cur->item && cur->leftChild != NULL) 
   	    cur = cur->leftChild;

	 else if (data > cur->item && cur->rightChild != NULL) 
	    cur = cur->rightChild;

	 else
	    return false;
    }
    return true;
}


//insert data to the BST at the correct subtree so that it
//remains sorted
void BST::insert (int data) 
{
    TreeNode *newNode = new TreeNode;	//create a new
    if (newNode == NULL)			//node to store
        return ;			//data
    
	newNode->item = data;
    newNode->leftChild = NULL;
    newNode->rightChild = NULL;

    if (root == NULL) 		//if BST is empty	
        root = newNode;		//insert as root node
	else { //insert to non-empty BST
	    TreeNode *cur = root;	//pointer to current node
	    TreeNode *par = NULL;	//pointer to parent node
	    
		while (cur != NULL) {	//traverse until the leaf
		 par = cur;
 		 if (data < cur->item)
		     cur = cur->leftChild; 
		 else if (data > cur->item)
 		     cur = cur->rightChild;
 		 else
 		    return;
 		    
	    }
	    if (data < par->item)
			par->leftChild = newNode;
	    else
			par->rightChild = newNode;
	}
    numOfItems++;
    return ;
}

//remove node in the BST that contains the data. The BST
//sorting order still remain. Return true if successful.
void BST::remove (int data)
{ 
    if (root == NULL)	//empty BST
       return ;
    TreeNode *cur = root;	//pointer to current node
    TreeNode *parent_lp = NULL;	//pointer to track current node was
    TreeNode *parent_rp = NULL;	//originated from left/right subtree
    while (cur->item != data) {
          if (data < cur->item && cur->leftChild != NULL) {
             parent_lp = cur;
             parent_rp = NULL;
             cur = cur->leftChild;
          }
          else if (data > cur->item && cur->rightChild != NULL) {
             parent_rp = cur;
             parent_lp = NULL;
             cur = cur->rightChild;
          }
          else
             return ;
    }
	//Node to delete has no children
    if (cur->leftChild == NULL && cur->rightChild == NULL) {
        if (cur == root)	//if node to delete is root
            root = NULL;
        else {
	       //if node to delete is from parent left
             if (parent_lp != NULL) 
	 	   parent_lp->leftChild = NULL;
	      //if node to delete is from parent right      
	       if (parent_rp != NULL)
                 parent_rp->rightChild = NULL;
        }
        delete cur;
    }

    //Node to delete has left child only
    else if (cur->leftChild != NULL && cur->rightChild == NULL) {
         if (cur == root)  //if node to delete is root
             root = cur->leftChild;
         else {
             if (parent_lp != NULL)
                 parent_lp->leftChild = cur->leftChild;
             if (parent_rp != NULL)
                 parent_rp->rightChild = cur->leftChild;
             delete cur;
         }
    }
    //Node to delete has right child only
    else if (cur->leftChild == NULL && cur->rightChild != NULL) {
         if (cur == root)
             root = cur->rightChild;
         else {
             if (parent_lp != NULL)
                 parent_lp->leftChild = cur->rightChild;
             if (parent_rp != NULL)
                 parent_rp->rightChild = cur->rightChild;
             delete cur;
         }
    }

    else { //Node to delete has both left and right child
        TreeNode *delNode = cur; 
        cur = cur->rightChild;
        TreeNode *par = delNode;
        while (cur->leftChild != NULL) {
            par = cur;
            cur = cur->leftChild;
        }
        delNode->item = cur->item;// replace node
        if (par==delNode)   
                par->rightChild = cur->rightChild;  
        else{
        	    par->leftChild = cur->rightChild;    //successor's node has subtree or no child
		}
                
        delete cur;
    }
    numOfItems--;
    return ;
}



void BST::displayBST ()
{
    inOrder (root);     
    std::cout << std::endl;
}


void BST::inOrder (TreeNode *node)	{
	if (node != NULL) { 
	 	inOrder (node->leftChild);
		std::cout << node->item << " ";
		inOrder (node->rightChild);
	}
}

void BST::levelOrder() {	
    if (root == NULL) 
	    return;
  
    // Create an empty queue 
    std::queue<TreeNode *> q;      
    
    // to store front element of queue    
    TreeNode *curr;
    q.push(root);
  
  
   while (!q.empty())    {
        curr = q.front();
        q.pop();
        std::cout << curr->item << " ";  
        // pushing left child            
        if(curr->leftChild)
            q.push(curr->leftChild);
              
        // pushing right child 
        if(curr->rightChild)
          	q.push(curr->rightChild);
              
        
    }
    
}
