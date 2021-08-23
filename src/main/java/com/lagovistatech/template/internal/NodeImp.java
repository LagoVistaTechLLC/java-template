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

import com.lagovistatech.template.Node;

public abstract class NodeImp implements Node {	
	private Node next;
	public Node getNext() { return next; }
	public void setNext(Node value) { next = value; }
	
	private Node previous;
	public Node getPrevious() { return previous; }
	public void setPrevious(Node value) { previous = value; }
	
	protected abstract String parsing();

	protected static String debugEscape(String value) {
		return value.replace("\"", "\\\"").replace("\t", "\\t").replace("\n", "\\n").replace("\r", "\\r");
	}
}