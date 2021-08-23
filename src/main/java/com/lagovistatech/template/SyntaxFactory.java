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

import java.util.HashMap;

import com.lagovistatech.Factory;
import com.lagovistatech.template.internal.CSyntaxImp;
import com.lagovistatech.template.internal.XmlSyntaxImp;

public class SyntaxFactory implements Factory<Syntax> {
	public enum Styles {
		C,
		XML
	}
	
	private static HashMap<Styles, SyntaxFactory> factoryCache = new HashMap<Styles, SyntaxFactory>();
	private static HashMap<Styles, Syntax> syntaxCache = new HashMap<Styles, Syntax>();
	public static Syntax Instantiate(Styles style) throws Exception {
		if(!factoryCache.containsKey(style))
			factoryCache.put(style, new SyntaxFactory(style));
		
		return factoryCache.get(style).create();
	}
	
	public SyntaxFactory(Styles style) throws Exception {
		if(!syntaxCache.containsKey(style)) {		
			switch(style) {
				case XML:
					syntaxCache.put(style, new XmlSyntaxImp());
					break;
				case C:
					syntaxCache.put(style, new CSyntaxImp());
					break;
				default:
					throw new Exception("Invalid Style!");
			}
		}
		
		myStyle = style;
	}
	
	private Styles myStyle;
	public Syntax create() {
		return syntaxCache.get(myStyle);
	}
}
