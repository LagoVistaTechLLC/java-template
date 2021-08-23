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

import com.lagovistatech.template.VariableNode;

public class VariableNodeImp extends NodeImp implements VariableNode {
	public VariableNodeImp(String name) {
		this.name = name;
	}

	private String name = null;
	public String getName() { return name; }

	private String value = null;
	public void setValue(String value) { this.value = value; }
	public String getValue() { return value; }
	
	public int set(String name, String value) {
		int ret = 0;
		if(getName().equals(name)) {
			setValue(value);
			ret++;
		}
		
		if(getNext() != null)
			ret += getNext().set(name, value);
		
		return ret;
	}
	public int touch(String name) {
		int ret = 0;
		
		if(getNext() != null)
			ret += getNext().touch(name);
		
		return ret;
	}
	public int parse(String name) throws Exception {
		int ret = 0;
		
		if(getNext() != null)
			ret += getNext().parse(name);
		
		return ret;
	}
	
	protected String parsing() { 
		String ret = getValue();
		
		if(getNext() != null)
			ret += ((NodeImp) getNext()).parsing();
		
		return ret;
	}
	
	public String debug(int depth) {
		String indent = "";
		for(int cnt = 0; cnt < depth; cnt++)
			indent += "  ";

		String ret = indent + "Variable:";
		ret += " \"" + NodeImp.debugEscape(this.name) + "\"";
		if(this.value == null)
			ret += " null";
		else
			ret += " \"" + NodeImp.debugEscape(this.value) + "\"";
		ret += System.lineSeparator();
		
		if(this.getNext() != null)
			ret += this.getNext().debug(depth);
		
		return ret;
	}
}
