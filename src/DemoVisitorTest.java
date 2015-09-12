/*
 * Reference:
 * http://help.eclipse.org/juno/index.jsp?topic=/org.eclipse.jdt.doc.isv/
 * reference/api/org/eclipse/jdt/core/dom/WhileStatement.html
 */

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.Annotation;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.AssertStatement;
import org.eclipse.jdt.core.dom.Assignment;
import org.eclipse.jdt.core.dom.Block;
import org.eclipse.jdt.core.dom.BooleanLiteral;
import org.eclipse.jdt.core.dom.BreakStatement;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.CharacterLiteral;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ConditionalExpression;
import org.eclipse.jdt.core.dom.ConstructorInvocation;
import org.eclipse.jdt.core.dom.ContinueStatement;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EmptyStatement;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ExpressionStatement;
import org.eclipse.jdt.core.dom.FieldAccess;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.ForStatement;
import org.eclipse.jdt.core.dom.IfStatement;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.InfixExpression;
import org.eclipse.jdt.core.dom.InstanceofExpression;
import org.eclipse.jdt.core.dom.LabeledStatement;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.NullLiteral;
import org.eclipse.jdt.core.dom.NumberLiteral;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.ParenthesizedExpression;
import org.eclipse.jdt.core.dom.PostfixExpression;
import org.eclipse.jdt.core.dom.PrefixExpression;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.Statement;
import org.eclipse.jdt.core.dom.StringLiteral;
import org.eclipse.jdt.core.dom.SuperConstructorInvocation;
import org.eclipse.jdt.core.dom.SuperFieldAccess;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;
import org.eclipse.jdt.core.dom.SwitchCase;
import org.eclipse.jdt.core.dom.SwitchStatement;
import org.eclipse.jdt.core.dom.SynchronizedStatement;
import org.eclipse.jdt.core.dom.ThisExpression;
import org.eclipse.jdt.core.dom.ThrowStatement;
import org.eclipse.jdt.core.dom.TryStatement;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclarationStatement;
import org.eclipse.jdt.core.dom.TypeLiteral;
import org.eclipse.jdt.core.dom.VariableDeclarationExpression;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;
import org.eclipse.jdt.core.dom.WhileStatement;

public class DemoVisitorTest {

	public DemoVisitorTest(String path) {
		CompilationUnit result = JdtAstUtil.getCompilationUnit(path);

		showpackage(result);
		showimport(result);
		showtypes(result);

		// DemoVisitor visitor = new DemoVisitor();
		// comp.accept(visitor);
	}

	private void showpackage(CompilationUnit result) {
		// show package declarations in order
		PackageDeclaration packageDc = result.getPackage();
		System.out.println("package:");
		System.out.println(packageDc.getName());
		System.out.println();
	}

	private void showimport(CompilationUnit result) {
		// show import declarations in order
		@SuppressWarnings("rawtypes")
		List importList = result.imports();
		System.out.println("import:");
		for (Object obj : importList) {
			ImportDeclaration importDec = (ImportDeclaration) obj;
			System.out.println(importDec.getName());
		}
		System.out.println();
	}

	private void showtypes(CompilationUnit result) {
		// show class name
		@SuppressWarnings("rawtypes")
		List types = result.types();
		System.out.println("types:");
		TypeDeclaration typeDec = (TypeDeclaration) types.get(0);
		System.out.println("className:" + typeDec.getName());
		System.out.println();

		showfields(typeDec);
		showmethods(typeDec);
	}

	private void showfields(TypeDeclaration typeDec) {
		// show fields
		FieldDeclaration fieldDec[] = typeDec.getFields();
		System.out.println("Fields:");
		for (FieldDeclaration field : fieldDec) {
			System.out.println("Field fragment:" + field.fragments());
			System.out.println("Field type:" + field.getType());
		}
		System.out.println();
	}

	private void showmethods(TypeDeclaration typeDec) {
		// show methods
		MethodDeclaration methodDec[] = typeDec.getMethods();
		System.out.println("Method:");
		for (MethodDeclaration method : methodDec) {
			// get method name
			SimpleName methodName = method.getName();
			System.out.println("method name:" + methodName);

			// get method parameters
			@SuppressWarnings("rawtypes")
			List param = method.parameters();
			System.out.println("method parameters:" + param);

			// get method return type
			Type returnType = method.getReturnType2();
			System.out.println("method return type:" + returnType);

			showbody(method);
			System.out.println();
		}
	}

	private void showbody(MethodDeclaration method) {
		// get method body
		Block body = method.getBody();

		showstatements(body);
	}

	@SuppressWarnings("rawtypes")
	private void showstatements(Block body) {
		List statements = body.statements(); // get the statements of the method
												// body
		Iterator iter = statements.iterator();
		while (iter.hasNext()) {
			// get each statement
			Statement stmt = (Statement) iter.next();
			System.out.print("//" + stmt);
			if (stmt instanceof ExpressionStatement) {
				System.out.println("ExpressionStatement:");
				ExpressionStatement expressstmt = (ExpressionStatement) stmt;
				Expression express = expressstmt.getExpression();

				showexpressions(express);

			} else if (stmt instanceof IfStatement) {
				System.out.println("IfStatement:");
				IfStatement ifstmt = (IfStatement) stmt;
				InfixExpression wex = (InfixExpression) ifstmt.getExpression();
				System.out.println("if-LHS:" + wex.getLeftOperand() + "; ");
				System.out.println("if-op:" + wex.getOperator() + "; ");
				System.out.println("if-RHS:" + wex.getRightOperand());

			} else if (stmt instanceof VariableDeclarationStatement) {
				System.out.println("VariableDeclarationStatement:");
				VariableDeclarationStatement var = (VariableDeclarationStatement) stmt;
				System.out.println("Type of variable:" + var.getType());
				System.out.println("Name of variable:" + var.fragments());

			} else if (stmt instanceof ReturnStatement) {
				System.out.println("ReturnStatement:");
				ReturnStatement rtstmt = (ReturnStatement) stmt;
				System.out.println("return:" + rtstmt.getExpression());

			} else if (stmt instanceof AssertStatement) {
				System.out.println("AssertStatement:");
				AssertStatement atstmt = (AssertStatement) stmt;
				System.out.println("assert:" + atstmt.getExpression());

			} else if (stmt instanceof Block) {
				System.out.println("Block:");
				Block blk = (Block) stmt;

				showstatements(blk);

			} else if (stmt instanceof BreakStatement) {
				System.out.println("BreakStatement:");
				BreakStatement bkstmt = (BreakStatement) stmt;
				System.out.println("break:" + bkstmt.getLabel());

			} else if (stmt instanceof ConstructorInvocation) {
				System.out.println("ConstructorInvocation:");

			} else if (stmt instanceof ContinueStatement) {
				System.out.println("ContinueStatement:");
				ContinueStatement ctstmt = (ContinueStatement) stmt;
				System.out.println("continue:" + ctstmt.getLabel());

			} else if (stmt instanceof DoStatement) {
				System.out.println("DoStatement:");
				DoStatement dostmt = (DoStatement) stmt;
				Expression express = dostmt.getExpression();

				showstatements((Block) dostmt.getBody());
				showexpressions(express);

			} else if (stmt instanceof EmptyStatement) {
				System.out.println("EmptyStatement:");

			} else if (stmt instanceof ForStatement) {
				System.out.println("ForStatement:");
				ForStatement forstmt = (ForStatement) stmt;
				List initializers = forstmt.initializers();
				Expression expression = forstmt.getExpression();
				List updaters = forstmt.updaters();

				showstatements((Block) forstmt.getBody());
				Iterator iiter = initializers.iterator();
				while (iiter.hasNext()) {
					Expression exp = (Expression) iiter.next();
					showexpressions(exp);
				}
				showexpressions(expression);
				Iterator uiter = updaters.iterator();
				while (uiter.hasNext()) {
					Expression exp = (Expression) uiter.next();
					showexpressions(exp);
				}

			} else if (stmt instanceof LabeledStatement) {
				System.out.println("LabeledStatement:");
				LabeledStatement lbstmt = (LabeledStatement) stmt;

				System.out.println("lable:" + lbstmt.getLabel());
				showstatements((Block) lbstmt.getBody());

			} else if (stmt instanceof SuperConstructorInvocation) {
				System.out.println("SuperConstructorInvocation:");

			} else if (stmt instanceof SwitchCase) {
				System.out.println("SwitchCase:");

			} else if (stmt instanceof SwitchStatement) {
				System.out.println("SwitchStatement:");

			} else if (stmt instanceof SynchronizedStatement) {
				System.out.println("SynchronizedStatement:");

			} else if (stmt instanceof ThrowStatement) {
				System.out.println("ThrowStatement:");

			} else if (stmt instanceof TryStatement) {
				System.out.println("TryStatement:");

			} else if (stmt instanceof TypeDeclarationStatement) {
				System.out.println("TypeDeclarationStatement:");

			} else if (stmt instanceof WhileStatement) {
				System.out.println("WhileStatement:");
				WhileStatement wlstmt = (WhileStatement) stmt;
				Expression express = wlstmt.getExpression();

				showstatements((Block) wlstmt.getBody());
				showexpressions(express);
			}
		}
	}

	private void showexpressions(Expression express) {
		System.out.println("/*" + express + "*/");
		if (express instanceof Assignment) {
			System.out.println("Assignment:");
			Assignment assign = (Assignment) express;
			System.out.println("LHS:" + assign.getLeftHandSide() + "; ");
			System.out.println("Op:" + assign.getOperator() + "; ");
			System.out.println("RHS:" + assign.getRightHandSide());

		} else if (express instanceof MethodInvocation) {
			System.out.println("MethodInvocation:");
			MethodInvocation mi = (MethodInvocation) express;
			System.out.println("invocation name:" + mi.getName());
			System.out.println("invocation exp:" + mi.getExpression());
			System.out.println("invocation arg:" + mi.arguments());

		} else if (express instanceof Annotation) {
			System.out.println("Annotation:");

		} else if (express instanceof ArrayAccess) {
			System.out.println("ArrayAccess:");

		} else if (express instanceof ArrayCreation) {
			System.out.println("ArrayCreation:");

		} else if (express instanceof ArrayInitializer) {
			System.out.println("ArrayInitializer:");

		} else if (express instanceof BooleanLiteral) {
			System.out.println("BooleanLiteral:");

		} else if (express instanceof CastExpression) {
			System.out.println("CastExpression:");

		} else if (express instanceof CharacterLiteral) {
			System.out.println("CharacterLiteral:");

		} else if (express instanceof ClassInstanceCreation) {
			System.out.println("ClassInstanceCreation:");

		} else if (express instanceof ConditionalExpression) {
			System.out.println("ConditionalExpression:");

		} else if (express instanceof FieldAccess) {
			System.out.println("FieldAccess:");

		} else if (express instanceof InfixExpression) {
			System.out.println("InfixExpression:");

		} else if (express instanceof InstanceofExpression) {
			System.out.println("InstanceofExpression:");

		} else if (express instanceof Name) {
			System.out.println("Name:");

		} else if (express instanceof NullLiteral) {
			System.out.println("NullLiteral:");

		} else if (express instanceof NumberLiteral) {
			System.out.println("NumberLiteral:");

		} else if (express instanceof ParenthesizedExpression) {
			System.out.println("ParenthesizedExpression:");

		} else if (express instanceof PostfixExpression) {
			System.out.println("PostfixExpression:");

		} else if (express instanceof PrefixExpression) {
			System.out.println("PrefixExpression:");

		} else if (express instanceof StringLiteral) {
			System.out.println("StringLiteral:");

		} else if (express instanceof SuperFieldAccess) {
			System.out.println("SuperFieldAccess:");

		} else if (express instanceof SuperMethodInvocation) {
			System.out.println("SuperMethodInvocation:");

		} else if (express instanceof ThisExpression) {
			System.out.println("ThisExpression:");

		} else if (express instanceof TypeLiteral) {
			System.out.println("TypeLiteral:");

		} else if (express instanceof VariableDeclarationExpression) {
			System.out.println("VariableDeclarationExpression:");

		}
	}
}