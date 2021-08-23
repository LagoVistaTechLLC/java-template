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
import com.lagovistatech.template.Syntax;
import com.lagovistatech.template.SyntaxMatch;

public class NodeFactory {
	public Node createNode(String contents, Syntax syntax) throws Exception {
		SyntaxMatch varMatch = syntax.findVariable(contents);
		SyntaxMatch blockMatch = syntax.findStartBlock(contents);

		Node ret = null;
		String remainder = "";
		if(varMatch != null && varMatch.getStart() == 0) {
			// create variable
			VariableNodeImp node = new VariableNodeImp(varMatch.getIdentifier());

			ret = node;
			remainder = contents.substring(varMatch.getEnd());
		} else if(blockMatch != null && blockMatch.getStart() == 0) {
			// create block
			SyntaxMatch endMatch = syntax.findEndBlock(contents, blockMatch.getIdentifier());
			if(endMatch == null)
				throw new Exception("Could not find end block for identifier '" + blockMatch.getIdentifier() + "'!");
			
			BlockNodeImp node = new BlockNodeImp(blockMatch.getIdentifier());
			String childContents = contents.substring(blockMatch.getEnd(), endMatch.getStart());
			node.setChild(createNode(childContents, syntax));
			
			ret = node;
			remainder = contents.substring(endMatch.getEnd());
		} else {
			// create static

			// determine next
			SyntaxMatch nextMatch = null;
			if(varMatch != null && blockMatch == null)
				nextMatch = varMatch;
			else if(varMatch == null && blockMatch != null)
				nextMatch = blockMatch;
			else if(varMatch != null && blockMatch != null) {
				if(varMatch.getStart() < blockMatch.getStart())
					nextMatch = varMatch;
				else
					nextMatch = blockMatch;
			}
			
			if(nextMatch != null) {
				String staticContent = contents.substring(0, nextMatch.getStart());
				StaticNodeImp node = new StaticNodeImp(staticContent, false);

				ret = node;
				remainder = contents.substring(nextMatch.getStart());
			} else {
				StaticNodeImp node = new StaticNodeImp(contents, false);
				ret = node;
			}
		}
		
		if(remainder.length() > 0) {
			Node remainderNode = createNode(remainder, syntax); 
			ret.setNext(remainderNode);
			remainderNode.setPrevious(ret);
		}

		return ret;		
	}
}
