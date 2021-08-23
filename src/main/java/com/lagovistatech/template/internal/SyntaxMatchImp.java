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
package com.lagovistatech.template.internal;

import com.lagovistatech.template.SyntaxMatch;

public class SyntaxMatchImp implements SyntaxMatch {
	public SyntaxMatchImp(int start, int end, String identifier, SyntaxType syntaxType) {
		myStart = start;
		myEnd = end;
		myIdentifier = identifier;
		myType = syntaxType;
	}
	
	private int myStart;
	public int getStart() { return myStart; }

	private int myEnd;
	public int getEnd() { return myEnd; }

	private String myIdentifier;
	public String getIdentifier() { return myIdentifier; }
	
	public SyntaxType myType;
	public SyntaxType getSyntaxType() { return myType; }

}
