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
package com.lagovistatech.template.internal.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Paths;

import com.lagovistatech.template.Node;
import com.lagovistatech.template.internal.BlockNodeImp;
import com.lagovistatech.template.internal.NodeFactory;
import com.lagovistatech.template.internal.StaticNodeImp;
import com.lagovistatech.template.internal.VariableNodeImp;
import com.lagovistatech.template.internal.XmlSyntaxImp;

public class NodeFactoryTest {
	//	Empty
	@Test
	public void TemplateEmpty() {
		try {
			NodeFactory nf = new NodeFactory();
			XmlSyntaxImp syntax = new XmlSyntaxImp();
			
			String template = Files.readString(Paths.get("src", "test", "resources", "NodeFactoryTests", "TemplateEmpty.txt"));
			Node node = nf.createNode(template, syntax);
			
			StaticNodeImp staticNode = (StaticNodeImp) node;
			if(staticNode.getIsParsed()) {
				fail("Node should not be parsed!");
				return;
			}
			
			if(staticNode.getNext() != null) {
				fail("Node has a next!");
				return;
			}
			
			assertEquals(staticNode.getValue(), "");
		}
		catch(Exception ex) {
			fail(ex.toString());
		}
	}
	//	Static			
	@Test
	public void TemplateStatic() {
		try {
			NodeFactory nf = new NodeFactory();
			XmlSyntaxImp syntax = new XmlSyntaxImp();
			
			String template = Files.readString(Paths.get("src", "test", "resources", "NodeFactoryTests", "TemplateStatic.txt"));
			Node node = nf.createNode(template, syntax);
			
			StaticNodeImp staticNode = (StaticNodeImp) node;
			if(staticNode.getIsParsed()) {
				fail("Node should not be parsed!");
				return;
			}
			
			if(staticNode.getNext() != null) {
				fail("Node has a next!");
				return;
			}
			
			assertEquals(staticNode.getValue(), "This is some static text!");
		}
		catch(Exception ex) {
			fail(ex.toString());
		}
	}
	//	Variable			
	@Test
	public void TemplateVariable() {
		try {
			NodeFactory nf = new NodeFactory();
			XmlSyntaxImp syntax = new XmlSyntaxImp();
			
			String template = Files.readString(Paths.get("src", "test", "resources", "NodeFactoryTests", "TemplateVariable.txt"));
			Node node = nf.createNode(template, syntax);
			
			VariableNodeImp varNode = (VariableNodeImp) node;
			if(varNode.getNext() != null) {
				fail("Node has a next!");
				return;
			}
			if(varNode.getValue() != null) {
				fail("Node has a value!");
				return;				
			}

			assertEquals(varNode.getName(), "Hello World");
		}
		catch(Exception ex) {
			fail(ex.toString());
		}
	}
	//	Block			
	@Test
	public void TemplateBlock() {
		try {
			NodeFactory nf = new NodeFactory();
			XmlSyntaxImp syntax = new XmlSyntaxImp();
			
			String template = Files.readString(Paths.get("src", "test", "resources", "NodeFactoryTests", "TemplateBlock.txt"));
			Node node = nf.createNode(template, syntax);
			
			BlockNodeImp blockNode = (BlockNodeImp) node;
			if(blockNode.getNext() != null) {
				fail("Node has a next!");
				return;
			}
			
			if(blockNode.getChild() == null) {
				fail("Node does not have a child!");
				return;
			}
			if(!StaticNodeImp.class.isInstance(blockNode.getChild())) {
				fail("Child node is not static!");
				return;
			}
			if(!((StaticNodeImp) blockNode.getChild()).getValue().equals("")) {
				fail("Child node is not empty!");
				return;
			}

			assertEquals(blockNode.getName(), "Empty Block");
		}
		catch(Exception ex) {
			fail(ex.toString());
		}
	}	

	//	Static Variable Static
	@Test
	public void TemplateStaticVariableStatic() {
		try {
			NodeFactory nf = new NodeFactory();
			XmlSyntaxImp syntax = new XmlSyntaxImp();
			
			String template = Files.readString(Paths.get("src", "test", "resources", "NodeFactoryTests", "TemplateStaticVariableStatic.txt"));
			Node node = nf.createNode(template, syntax);
			
			StaticNodeImp first = (StaticNodeImp) node;
			if(!first.getValue().equals("Static 321")) {
				fail("First node value not as expected!");
				return;
			}
			
			VariableNodeImp second = (VariableNodeImp) first.getNext();
			if(!second.getName().equals("Some Variable")) {
				fail("Second node name not as expected!");
				return;
			}
			
			StaticNodeImp third = (StaticNodeImp) second.getNext();
			assertEquals("123 citatS", third.getValue());
			
		}
		catch(Exception ex) {
			fail(ex.toString());
		}
	}
	//	Static Variable Variable
	@Test
	public void TemplateStaticVariableVariable() {
		try {
			NodeFactory nf = new NodeFactory();
			XmlSyntaxImp syntax = new XmlSyntaxImp();
			
			String template = Files.readString(Paths.get("src", "test", "resources", "NodeFactoryTests", "TemplateStaticVariableVariable.txt"));
			Node node = nf.createNode(template, syntax);
			
			StaticNodeImp first = (StaticNodeImp) node;
			if(!first.getValue().equals("Static 321")) {
				fail("First node value not as expected!");
				return;
			}
			
			VariableNodeImp second = (VariableNodeImp) first.getNext();
			if(!second.getName().equals("Some Variable")) {
				fail("Second node name not as expected!");
				return;
			}
			
			VariableNodeImp third = (VariableNodeImp) second.getNext();
			assertEquals("Next Variable", third.getName());
			
		}
		catch(Exception ex) {
			fail(ex.toString());
		}
	}
	//	Static Variable Block
	@Test
	public void TemplateStaticVariableBlock() {
		try {
			NodeFactory nf = new NodeFactory();
			XmlSyntaxImp syntax = new XmlSyntaxImp();
			
			String template = Files.readString(Paths.get("src", "test", "resources", "NodeFactoryTests", "TemplateStaticVariableBlock.txt"));
			Node node = nf.createNode(template, syntax);
			
			StaticNodeImp first = (StaticNodeImp) node;
			if(!first.getValue().equals("Static 321")) {
				fail("First node value not as expected!");
				return;
			}
			
			VariableNodeImp second = (VariableNodeImp) first.getNext();
			if(!second.getName().equals("Some Variable")) {
				fail("Second node name not as expected!");
				return;
			}
			
			BlockNodeImp third = (BlockNodeImp) second.getNext();
			assertEquals("Next Block", third.getName());
			
		}
		catch(Exception ex) {
			fail(ex.toString());
		}
	}

	// Static Block Static
	@Test
	public void TemplateStaticBlockStatic() {
		try {
			NodeFactory nf = new NodeFactory();
			XmlSyntaxImp syntax = new XmlSyntaxImp();
			
			String template = Files.readString(Paths.get("src", "test", "resources", "NodeFactoryTests", "TemplateStaticVariableStatic.txt"));
			Node node = nf.createNode(template, syntax);
			
			StaticNodeImp first = (StaticNodeImp) node;
			if(!first.getValue().equals("Static 321")) {
				fail("First node value not as expected!");
				return;
			}
			
			VariableNodeImp second = (VariableNodeImp) first.getNext();
			if(!second.getName().equals("Some Variable")) {
				fail("Second node name not as expected!");
				return;
			}
			
			StaticNodeImp third = (StaticNodeImp) second.getNext();
			assertEquals("123 citatS", third.getValue());
			
		}
		catch(Exception ex) {
			fail(ex.toString());
		}
	}
}