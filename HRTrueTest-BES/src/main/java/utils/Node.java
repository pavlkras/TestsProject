package main.java.utils;

import java.util.ArrayList;
import java.util.List;

public class Node<T> {
	private T data;
	private Node<T> parent;
	private List<Node<T>> children;
	
	public Node(T data, Node<T> parent) {
		super();
		this.data = data;
		this.parent = parent;
	}
	
	public void addChildren(T child){
		if (children == null)
			children = new ArrayList<>();
		Node<T> childNode = new Node<T>(child, this);
		children.add(childNode);
	}

	public T getData() {
		return data;
	}

	public Node<T> getParent() {
		return parent;
	}

	public List<Node<T>> getChildren() {
		return children;
	}
	
	public boolean hasChild() {
		return children != null;
	}
}
