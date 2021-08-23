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

import java.nio.file.Path;

import com.lagovistatech.template.Document;
import com.lagovistatech.template.Node;
import com.lagovistatech.template.Syntax;

public class DocumentImp implements Document {
	private Node rootNode = null;

	public void load(Path filePath, Syntax syntax) throws Exception {
		String contents = java.nio.file.Files.readString(filePath);
		load(contents, syntax);
	}
	public void load(String contents, Syntax syntax) throws Exception {
		NodeFactory nf = new NodeFactory();
		
		rootNode = new StaticNodeImp("", false);
		rootNode.setNext(nf.createNode(contents, syntax));
		rootNode.getNext().setPrevious(rootNode);
	}
	
	public int set(String name, String value) { return rootNode.set(name, value); }
	public int touch(String name) { return rootNode.touch(name); }
	public int parse(String name) throws Exception { return rootNode.parse(name); }

	public String generate() {
		return ((StaticNodeImp) rootNode).parsing();
	}
	public String debug() {
		return rootNode.debug(0);
	}
}
