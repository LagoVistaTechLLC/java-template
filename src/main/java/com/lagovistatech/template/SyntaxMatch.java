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
 * The syntax class methods will return the next match for each of the node 
 * types it can search for.
 */
public interface SyntaxMatch {
	public enum SyntaxType {
		VARIABLE,
		START,
		END
	}

	/**
	 * @return The starting position of the node.
	 */
	int getStart();
	/**
	 * @return The ending position of the node.
	 */
	int getEnd();
	/**
	 * @return The identifier assigned to the node.
	 */
	String getIdentifier();
	/**
	 * @return The syntax type matched.
	 */
	SyntaxType getSyntaxType();
}
