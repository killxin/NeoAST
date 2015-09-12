package hello;

public class CodeG {
	public static void main(String[] args) {
		String[] s = { "Annotation", "ArrayAccess", "ArrayCreation",
				"ArrayInitializer", "BooleanLiteral", "CastExpression",
				"CharacterLiteral", "ClassInstanceCreation",
				"ConditionalExpression", "FieldAccess", "InfixExpression",
				"InstanceofExpression", "Name", "NullLiteral", "NumberLiteral",
				"ParenthesizedExpression", "PostfixExpression",
				"PrefixExpression", "StringLiteral", "SuperFieldAccess",
				"SuperMethodInvocation", "ThisExpression", "TypeLiteral",
				"VariableDeclarationExpression" };
		for (String t : s) {
			System.out.println("else if (express instanceof "+t+") {\nSystem.out.println(\""+t+
					":\");");
//			System.out.println(t+" assign = ("+t+") express;\n\n} ");
		}
	}
}
