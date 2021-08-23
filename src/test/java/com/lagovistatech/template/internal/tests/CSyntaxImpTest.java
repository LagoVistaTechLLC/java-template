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
import com.lagovistatech.template.internal.CSyntaxImp;

public class CSyntaxImpTest {
	// Variable
	@Test
	public void findVariable_Valid() {
		String content = " Before __Keyword__ After ";
		//                0123456789012345678901234567890
		
		CSyntaxImp syntax = new CSyntaxImp();
		SyntaxMatch match = syntax.findVariable(content);
				
		assertTrue(match.getStart() == 8 && match.getEnd() == 19 && match.getIdentifier().equals("Keyword"));
	}
	@Test
	public void findVariable_MultipleSpaced() {
		String content = " Before __column_java_type__ get__column_name_camel__(); After ";
		
		CSyntaxImp syntax = new CSyntaxImp();
		SyntaxMatch match = syntax.findVariable(content);
				
		assertTrue(match.getStart() == 8 && match.getEnd() == 28 && match.getIdentifier().equals("column_java_type"));
	}
	@Test
	public void findVariable_MultipleNoSpace() {
		String content = " Before __column_java_type__get__column_name_camel__(); After ";
		
		CSyntaxImp syntax = new CSyntaxImp();
		SyntaxMatch match = syntax.findVariable(content);
				
		assertTrue(match.getStart() == 8 && match.getEnd() == 28 && match.getIdentifier().equals("column_java_type"));
	}
	@Test
	public void findVariable_Invalid() {
		String content = " Before __Keyw!ord__ After ";
		//                0123456789012345678901234567890
		
		CSyntaxImp syntax = new CSyntaxImp();
		SyntaxMatch match = syntax.findVariable(content);
		
		assertNull(match);
	}	
	// Start Block
	@Test
	public void findStartBlock_Valid() {
		String content = " Before // start Keyword\r\nAfter ";
		//                0123456789012345678901234567890
		
		CSyntaxImp syntax = new CSyntaxImp();
		SyntaxMatch match = syntax.findStartBlock(content);
		
		assertTrue(match.getStart() == 8 && match.getEnd() == 24 && match.getIdentifier().equals("Keyword"));
	}
	
	// End Block
	@Test
	public void findEndBlock_Valid() {
		String content = " Before // end Keyword\r\nAfter ";
		//                0123456789012345678901234567890
		
		CSyntaxImp syntax = new CSyntaxImp();
		SyntaxMatch match = null;
		try { match = syntax.findEndBlock(content, "Keyword"); }
		catch(Exception ex) {
			fail(ex.toString());
			return;
		}
		
		assertTrue(match.getStart() == 8 && match.getEnd() == 22 && match.getIdentifier().equals("Keyword"));
	}
}
