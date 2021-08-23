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

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.lagovistatech.template.Syntax;
import com.lagovistatech.template.SyntaxMatch;
import com.lagovistatech.template.SyntaxMatch.SyntaxType;

public class XmlSyntaxImp implements Syntax {
	// (?:REGEX) makes a non-capturing group of REGEX 
	public static String identifierRegex = "([\\w](?:[\\w\\s]*[\\w])*)";
	public static String openBlockRegex = "[Bb][Ll][Oo][Cc][Kk]";
	public static String endBlockRegex = Pattern.quote("/") + openBlockRegex;
	public static String oneOrMoreSpacesRegex = "\\s*";

	private static Pattern varaiblePattern = Pattern.compile(
			Pattern.quote("{") + 
			oneOrMoreSpacesRegex + 
			identifierRegex + 
			oneOrMoreSpacesRegex + 
			Pattern.quote("}")
		);
	public SyntaxMatch findVariable(String hayStack) {
		Matcher matcher = varaiblePattern.matcher(hayStack);
		if (!matcher.find())
			return null;

		MatchResult result = matcher.toMatchResult();
		return new SyntaxMatchImp(result.start(), result.end(), result.group(1).trim(), SyntaxType.VARIABLE);
	}

	private static Pattern startBlockPattern = Pattern.compile(
			Pattern.quote("<!--") +
			oneOrMoreSpacesRegex +
			openBlockRegex +
			oneOrMoreSpacesRegex +
			identifierRegex + 
			oneOrMoreSpacesRegex +
			Pattern.quote("-->")
		);
	public SyntaxMatch findStartBlock(String hayStack) {
		Matcher matcher = startBlockPattern.matcher(hayStack);
		if (!matcher.find())
			return null;

		MatchResult result = matcher.toMatchResult();
		return new SyntaxMatchImp(result.start(), result.end(), result.group(1).trim(), SyntaxType.START);
	}

	private static Pattern identifierPattern = Pattern.compile(identifierRegex);
	public SyntaxMatch findEndBlock(String hayStack, String identifier) throws Exception {
		if(!identifierPattern.matcher(identifier).find())
			throw new Exception("The identifier '" + identifier + "' is invalid!");

		Pattern endBlockPattern = Pattern.compile(
				Pattern.quote("<!--") +
				oneOrMoreSpacesRegex +
				endBlockRegex +
				oneOrMoreSpacesRegex +
				"(" + Pattern.quote(identifier.trim()) + ")" +
				oneOrMoreSpacesRegex +
				Pattern.quote("-->")
			);

		Matcher matcher = endBlockPattern.matcher(hayStack);
		if (!matcher.find())
			return null;

		MatchResult result = matcher.toMatchResult();
		return new SyntaxMatchImp(result.start(), result.end(), result.group(1).trim(), SyntaxType.END);		
	}
}
