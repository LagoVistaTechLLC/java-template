/*

	Copyright (C) 2021 Lago Vista Technologies LLC

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as
    published by the Free Software Foundation, either version 3 of the
    License, or (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <https://www.gnu.org/licenses/>.
	
*/
package com.lagovistatech.template;

/**
 * Documents are composed of many nodes being either static, variables, 
 * or blocks.  All nodes can have a next and a parent.  The first node does not 
 * have a parent, and that is the node that document will keep track of.  
 * 
 * Only block have children nodes.  Children nodes generate content when parsed 
 * if they have been touched.  Generated content will be inserted in the 
 * document before the element that was asked to be parsed.  Generated content
 * will not continue to be duplicated on subsequent parses to eliminate the 
 * duplication of content when parsing a parent block many times.  
 */
public interface Node {
	/**
	 * @return The next node in the document.
	 */
	Node getNext();
	/**
	 * @param value Sets the next node in the document.
	 */
	void setNext(Node value);
	/**
	 * @return The previous/parent node in the document.
	 */
	Node getPrevious();
	/**
	 * @param value Sets the previous/parent node in the document.
	 */
	void setPrevious(Node value);

	/**
	 * Set the variables identified by name with the specified values.
	 * 
	 * @param name Template variable keywords to set the value of.
	 * @param value Value to set template variables to.
	 * @return The count of variable set.
	 */
	int set(String name, String value);
	/**
	 * Touches the template blocks identified by name so when parsed they will
	 * output content.  If a block is not touched before parsing or generating
	 * it will produce no content.
	 * 
	 * @param name Name of the blocks to touch for parsing.
	 * @return Count of blocks that were touched.
	 */
	int touch(String name);
	/**
	 * Parses a block inserting a new static before the block that was asked to 
	 * be parsed.  Statics that were generated will not repeat on subsequent
	 * parses, but be moved to before the block that was requested being parsed.
	 * 
	 * @param name Name of the blocks to parse.
	 * @return Count of blocks that were parsed.
	 */
	int parse(String name) throws Exception;
	/**
	 * Creates a YAML styled representation of the node tree.
	 * 
	 * @param depth Depth of node from parent node starting at 0
	 * @return YAML string representing the node(s)
	 */	
	String debug(int depth);
}
