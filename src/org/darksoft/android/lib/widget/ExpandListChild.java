package org.darksoft.android.lib.widget;

/**
 * A class to represent a child element for a {@link android.widget.ExpandableListView ExpandableListView}
 * child item.
 */
public class ExpandListChild {

	private String Name;
	private String Tag;

	/**
	 * Get the name of the item.
	 * @return The item name.
	 */
	public String getName() {
		return Name;
	}
	
	/**
	 * Set the name of the item.
	 * @param Name Name of the item.
	 */
	public void setName(String Name) {
		this.Name = Name;
	}
	
	/**
	 * Get the tag of the item.
	 * @return The item tag.
	 */
	public String getTag() {
		return Tag;
	}
	
	/**
	 * Set the name of the tag.
	 * @param Tag Tag of the item.
	 */
	public void setTag(String Tag) {
		this.Tag = Tag;
	}
}


