package org.darksoft.android.lib.widget;

import java.util.ArrayList;

/**
 * A class to represent a group element for a {@link android.widget.ExpandableListView ExpandableListView}
 * group item.
 */
public class ExpandListGroup {
 
	private String Name;
	private ArrayList<ExpandListChild> Items;
	
	/**
	 * Get the name of the item.
	 * @return The item name.
	 */
	public String getName() {
		return Name;
	}
	
	/**
	 * Set the name of the item.
	 * @param Name A {@link java.lang.String String} object with the name.
	 */
	public void setName(String name) {
		this.Name = name;
	}
	
	/**
	 * Get the list of child items of this object.
	 * @return A {@link java.util.ArrayList ArrayList} of {@link ExpandListChild} objects.
	 */
	public ArrayList<ExpandListChild> getItems() {
		return Items;
	}
	
	/**
	 * Set a {@link java.util.ArrayList ArrayList} with child items for the
	 * {@link android.widget.ExpandableListView ExpandableListView}.
	 * @param Items A {@link java.util.ArrayList ArrayList} of {@link ExpandListChild}
	 */
	public void setItems(ArrayList<ExpandListChild> Items) {
		this.Items = Items;
	}
		
}


