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
 * Syntax is used when loading a template.  When parsing the template elements,
 * it is used to determine where the text for a node begins, ends, and the 
 * identifier assigned to that mode. For block termination (end block) you will
 * need to provide the identifier for the end block tag you are looking for.
 */
public interface Syntax {
	/**
	 * This will search the hay stack for the first instance of a variable and
	 * provide a SyntaxMatch if found. Otherwise it will return null if not 
	 * found.
	 * 
	 * @param hayStack Text to search for next variable in.
	 * @return SyntaxMatch of next match; if no match, returns null.
	 */
	SyntaxMatch findVariable(String hayStack);
	/**
	 * This will search the hay stack for the first instance of a block start 
	 * and provide a SyntaxMatch if found. Otherwise it will return null if not 
	 * found.
	 * 
	 * @param hayStack Text to search for next block start in.
	 * @return SyntaxMatch of next match; if no match, returns null.
	 */
	SyntaxMatch findStartBlock(String hayStack);
	/**
	 * This will search the hay stack for the first instance of a block end with
	 * the specified identifier and provide a SyntaxMatch if found. Otherwise it 
	 * will return null if not found.
	 * 
	 * @param hayStack Text to search for next block end in.
	 * @return SyntaxMatch of next match; if no match, returns null.
	 */
	SyntaxMatch findEndBlock(String hayStack, String identifier) throws Exception;
}
