#include <iostream>
#include "BST.h"

using namespace std;

int main ()
{	
/* Question 1
	int num;
	BST myTree;
	
	for(int i=0; i<10; i++){
		cout<< "Insert 10 number " << i+1 << " here:";
		cin >> num;
		myTree.insert(num);
		
	}
	cout <<endl;
	myTree.displayBST();
	*/
	
BST myTree;
	myTree.insert (40);
	
	myTree.insert (30);
	myTree.displayBST ();
	myTree.insert (60);

	myTree.displayBST ();
	
	std::cout << std::boolalpha << myTree.search(34);
		
	return 0;
	
}

