package com.antlr;

import roboBaseVisitor.class;
import ExpressionNode.class;
import java.util.function.Function;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

class BuildAstVisitor extends roboBaseVisitor<ExpressionNode>
{
	@Override
	public ExpressionNode VisitCompileUnit(roboParser.CompileUnitContext context)
	{
		return Vist(context.expr());
	}

	@Override
	public ExpressionNode VisitNumberExpr(roboParser.NumberExprContext context)
	{
		return new NumberNode()
		{
			Double Value = Double.Parse(context.value.Text, NumberStyles.AllowDecimalPoint | NumberStyles.AllowExponent);
		};
	}
}
