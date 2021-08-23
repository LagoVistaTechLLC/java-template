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

import com.lagovistatech.Helpers;
import com.lagovistatech.template.Document;
import com.lagovistatech.template.DocumentFactory;
import com.lagovistatech.template.SyntaxFactory;
import com.lagovistatech.template.SyntaxFactory.Styles;

public class DocumentImpTest {
	@Test
	public void DocumentXmlTest() {
		try {			
			Document doc = DocumentFactory.instanciate();
			doc.load(
 					Helpers.readResourceAsString(getClass(), "/DocumentXmlTest.txt"),
					SyntaxFactory.Instantiate(Styles.XML)
				);
		    
			doc.set("Contact Name", "John Doe");
			doc.set("Phone", "281-555-1212");
			doc.set("Cell", "281-555-1313");
			doc.touch("Contact");
			doc.parse("Contact");
			
			doc.set("Contact Name", "Jane Doe");
			doc.set("Phone", "281-555-1414");
			doc.set("Cell", "281-555-1515");
			doc.touch("Contact");
			doc.parse("Contact");
			
			doc.set("Customer Name", "Doe and Deer Hunting");
			doc.set("Phone", "281-555-1616");
			doc.set("Cell", "281-555-1717");
			doc.touch("Customer");
			doc.touch("Has Children");
			doc.parse("Customer");
			
		    doc.set("Contact Name", "Elvis Presley");
			doc.set("Phone", "832-555-1212");
			doc.set("Cell", "832-555-1313");
			doc.touch("Contact");
			doc.parse("Contact");
			
		    doc.set("Contact Name", "Lisa Presley");
			doc.set("Phone", "832-555-1414");
			doc.set("Cell", "832-555-1515");
			doc.touch("Contact");
			doc.parse("Contact");
			
		    doc.set("Customer Name", "Music Incorporated");
			doc.set("Phone", "832-555-1616");
			doc.set("Cell", "832-555-1717");
			doc.touch("Customer");
			doc.touch("Has Children");
			doc.parse("Customer");
			
		    String out = doc.generate();
		    out = out.replaceAll("\\s+", " ");
		    
		    String expected = "<html> <head> <title>Page Title</title> </head> <body> <h1>Customer List</h1> <ul> <li> <b>Doe and Deer Hunting</b> 281-555-1616 / 281-555-1717 <ul> <li><i>John Doe</i> 281-555-1212 / 281-555-1313</li> <li><i>Jane Doe</i> 281-555-1414 / 281-555-1515</li> </ul> </li> <li> <b>Music Incorporated</b> 832-555-1616 / 832-555-1717 <ul> <li><i>Elvis Presley</i> 832-555-1212 / 832-555-1313</li> <li><i>Lisa Presley</i> 832-555-1414 / 832-555-1515</li> </ul> </li> </ul> </body> </html>";
		    assertEquals(expected, out);
		}
		catch(Exception ex) {
			ex.printStackTrace();
			fail(ex.toString());
		}
	}
}
