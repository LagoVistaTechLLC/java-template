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
 * Blocks represent a section of content that can be excluded (if not touched
 * at time of parse), and or repeated many times inside the template.  Each
 * block has has children, which can also be other blocks. 
 */
public interface BlockNode extends Node {
	/**
	 * @return Identifier for the current block.
	 */
	String getName();

	/**
	 * When a block has been touched, it will generate a static node that will
	 * be inserted before the block that was parsed.
	 * 
	 * @param name Name of blocks to touch.
	 * @return Count of touched blocks.
	 */
	int touch(String name);
	/**
	 * This will create a new static node before the top most block being parsed
	 * and populate it with the generated content.  If the parsed node or any 
	 * children have previously generated content, it will be brought forward
	 * so that it is not generated again.
	 * 
	 * @param name
	 * @return
	 */
	int parse(String name) throws Exception;
	
	/**
	 * @return The blocks child node.
	 */
	Node getChild();
	/**
	 * @param value Set the blocks child node.
	 */
	void setChild(Node value);
	
	/**
	 * @return True if the block has been touched.
	 */
	boolean getIsTouched();
	/**
	 * @param value Set the block as touched.
	 */
	void setIsTouched(boolean value);
}
