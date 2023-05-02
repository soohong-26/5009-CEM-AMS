#include <iostream>
#include "BST.h"

int main ()
{
	BST myTree;
	myTree.insert (40);
	
	myTree.insert (30);
	myTree.displayBST ();
	myTree.insert (60);
//	myTree.remove (40);
	myTree.displayBST ();
	
	std::cout << std::boolalpha << myTree.search(34);
		
	return 0;
	
}

