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
 * Static nodes can either represent static content that will never change, or
 * it will be content that has been generated from a previous blocks parse.
 * When a block is parsed, the already parsed static nodes will be removed from
 * all child blocks and included in the new static block moved before the node
 * that was parsed.
 */
public interface StaticNode extends Node {
	/**
	 * @return Content for this node.
	 */
	String getValue();
	
	/**
	 * @return True indicates this is static content generated from a prior 
	 * parse.
	 */
	boolean getIsParsed();
}
