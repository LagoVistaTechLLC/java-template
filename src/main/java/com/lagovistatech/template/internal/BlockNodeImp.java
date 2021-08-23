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

import com.lagovistatech.template.BlockNode;
import com.lagovistatech.template.Node;

public class BlockNodeImp extends NodeImp implements BlockNode {
	public BlockNodeImp(String name) {
		this.name = name;
	}
	
	private String name = "";
	public String getName() { return name; }

	private Node child = null;
	public Node getChild() { return child; }
	public void setChild(Node value) { child = value; }

	private boolean isTouched = false;
	public boolean getIsTouched() { return isTouched; }
	public void setIsTouched(boolean value) { isTouched = value; }

	public int set(String name, String value) {
		int ret = 0;
		
		if(getChild() != null)
			ret += getChild().set(name, value);
		if(getNext() != null)
			ret += getNext().set(name, value);
		
		return ret;
	}
	public int touch(String name) {
		int ret = 0;
		
		if(getName().equals(name))
			setIsTouched(true);
		
		if(getChild() != null)
			ret += getChild().touch(name);
		if(getNext() != null)
			ret += getNext().touch(name);
		
		return ret;
	}
	public int parse(String name) throws Exception {
		int ret = 0;
		
		if(getChild() != null) {
			if(!(getName().equals(name) && getIsTouched()))
				// not to be parsed, pass along
				ret += getChild().parse(name);
			else {
				// to be parsed
				ret++;
				String newContent = ((NodeImp) getChild()).parsing();
				StaticNodeImp newNode = new StaticNodeImp(newContent, true);
								
				// insert before
				Node oldPrevious = getPrevious();
				if(oldPrevious == null)
					throw new Exception("Block '" + getName() + "' being parsed does not have a previous!");
				
				oldPrevious.setNext(newNode);
				newNode.setPrevious(oldPrevious);
				newNode.setNext(this);
				setPrevious(newNode);
				
				setIsTouched(false);
			}
		}
			
		if(getNext() != null)
			ret += getNext().parse(name);
		
		return ret;
	}
	
	protected String parsing() {
		String ret = "";
		
		if(getIsTouched() && getChild() != null) {
			ret += ((NodeImp) getChild()).parsing();
			setIsTouched(false);
		}

		if(getNext() != null)
			ret += ((NodeImp) getNext()).parsing();

		return ret;
	}
	
	public String debug(int depth) {
		String indent = "";
		for(int cnt = 0; cnt < depth; cnt++)
			indent += "  ";

		String ret = indent + "Block:";
		if(this.isTouched)
			ret += " (Touched)";
		if(this.name == null)
			ret += " null";
		else
			ret += " \"" + this.name.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r") + "\"";
		ret += System.lineSeparator();
		
		if(this.child != null)
			ret += this.child.debug(depth + 1);
		if(this.getNext() != null)
			ret += this.getNext().debug(depth);
		
		return ret;
	}
}
