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

import static org.junit.Assert.*;

import org.junit.Test;

import com.lagovistatech.template.SyntaxMatch;
import com.lagovistatech.template.internal.XmlSyntaxImp;

public class XmlSyntaxImpTest {
	// Variables ///////////////////////////////////////////////////////////////
	@Test
	public void findVariable_WithoutSpaces() {
		String content = " Before {Keyword} After ";
		//                0123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findVariable(content);
		
		assertTrue(match.getStart() == 8 && match.getEnd() == 17 && match.getIdentifier().equals("Keyword"));
	}
	@Test
	public void findVariable_SpaceInside() {
		String content = " Before {Space Inside} After ";
		//                0123456789012345678901234567890

		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findVariable(content);
				
		assertTrue(match.getStart() == 8 && match.getEnd() == 22 && match.getIdentifier().equals("Space Inside"));
	}
	@Test
	public void findVariable_SpaceBefore() {
		String content = " Before { Keyword} After ";
		//                0123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findVariable(content);
				
		assertTrue(match.getStart() == 8 && match.getEnd() == 18 && match.getIdentifier().equals("Keyword"));
	}
	@Test
	public void findVariable_SpaceAfter() {
		String content = " Before {Keyword } After ";
		//                0123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findVariable(content);
				
		assertTrue(match.getStart() == 8 && match.getEnd() == 18 && match.getIdentifier().equals("Keyword"));
	}
	@Test
	public void findVariable_Invalid() {
		String content = " Before { Key!word } After ";
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findVariable(content);
		
		assertNull(match);
	}
	@Test
	public void findVariable_NoKeyword() {
		String content = " Before { } After ";
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findVariable(content);
		
		assertNull(match);
	}
	@Test
	public void findVariable_Nonexistant() {
		String content = " Before After ";
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findVariable(content);
		
		assertNull(match);
	}

	// Start Block /////////////////////////////////////////////////////////////
	@Test
	public void findStartBlock_UppderWithoutSpaces() {
		String content = " Before <!-- BLOCK Keyword --> After ";
		//                01234567890123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findStartBlock(content);
		
		assertTrue(match.getStart() == 8 && match.getEnd() == 30 && match.getIdentifier().equals("Keyword"));
	}
	@Test
	public void findStartBlock_LowerWithoutSpaces() {
		String content = " Before <!-- block Keyword --> After ";
		//                01234567890123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findStartBlock(content);
		
		assertTrue(match.getStart() == 8 && match.getEnd() == 30 && match.getIdentifier().equals("Keyword"));
	}
	@Test
	public void findStartBlock_UppderSpacesInside() {
		String content = " Before <!-- BLOCK Key word --> After ";
		//                01234567890123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findStartBlock(content);
		
		assertTrue(match.getStart() == 8 && match.getEnd() == 31 && match.getIdentifier().equals("Key word"));
	}
	@Test
	public void findStartBlock_LowerSpacesInside() {
		String content = " Before <!-- block Key word --> After ";
		//                01234567890123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findStartBlock(content);
		
		assertTrue(match.getStart() == 8 && match.getEnd() == 31 && match.getIdentifier().equals("Key word"));
	}
	@Test
	public void findStartBlock_Invalid() {
		String content = " Before <!-- BLOCK Key!word --> After ";
		//                01234567890123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findStartBlock(content);
		
		assertNull(match);
	}
	@Test
	public void findStartBlock_NoKeyword() {
		String content = " Before <!-- block --> After ";
		//                01234567890123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findStartBlock(content);
		
		assertNull(match);
	}
	@Test
	public void findStartBlock_NonExistant() {
		String content = " Before After ";
		//                01234567890123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = syntax.findStartBlock(content);
		
		assertNull(match);
	}
	
	// End Block ///////////////////////////////////////////////////////////////
	@Test
	public void findEndBlock_Uppercase() {
		String content = " Before <!-- /BLOCK Keyword --> After ";
		//                01234567890123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = null;
		try { match = syntax.findEndBlock(content, "Keyword"); }
		catch(Exception ex) {
			fail(ex.toString());
			return;
		}
		
		assertTrue(match.getStart() == 8 && match.getEnd() == 31 && match.getIdentifier().equals("Keyword"));
	}
	@Test
	public void findEndBlock_Lowercase() {
		String content = " Before <!-- /block Keyword --> After ";
		//                01234567890123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = null;
		try { match = syntax.findEndBlock(content, "Keyword"); }
		catch(Exception ex) {
			fail(ex.toString());
			return;
		}
		
		assertTrue(match.getStart() == 8 && match.getEnd() == 31 && match.getIdentifier().equals("Keyword"));
	}
	@Test
	public void findEndBlock_InvalidKeyword() {
		String content = " Before <!-- /block Keyword --> After ";
		//                01234567890123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		try { syntax.findEndBlock(content, "Key!word"); }
		catch(Exception ex) {
			assertTrue(ex.toString().contains("The identifier 'Key!word' is invalid!"));
		}
	}
	@Test
	public void findEndBlock_BadTagKeyword() {
		String content = " Before <!-- /block Ke!yword --> After ";
		//                01234567890123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = null;
		try { match = syntax.findEndBlock(content, "Keyword"); }
		catch(Exception ex) {
			fail(ex.toString());
			return;
		}
		
		assertNull(match);
	}
	@Test
	public void findEndBlock_BadBlockTag() {
		String content = " Before <!-- / block Keyword --> After ";
		//                01234567890123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = null;
		try { match = syntax.findEndBlock(content, "Keyword"); }
		catch(Exception ex) {
			fail(ex.toString());
			return;
		}
		
		assertNull(match);
	}
	@Test
	public void findEndBlock_NoTag() {
		String content = " Before No Tag After ";
		//                01234567890123456789012345678901234567890
		
		XmlSyntaxImp syntax = new XmlSyntaxImp();
		SyntaxMatch match = null;
		try { match = syntax.findEndBlock(content, "Keyword"); }
		catch(Exception ex) {
			fail(ex.toString());
			return;
		}
		
		assertNull(match);
	}
}
